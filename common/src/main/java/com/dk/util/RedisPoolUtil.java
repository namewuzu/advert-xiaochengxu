//  ==================================================================
//  文件名:RedisPoolUtil 
//  简要功能描述:
//
//  Created by endy on 2015-03-27.
//  Copyright (c) 2015-2018年 深圳奇迹科技有限公司. All rights reserved.
//  ==================================================================
package com.dk.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.io.*;
import java.util.List;
import java.util.Set;


/**
 * 正常全部ac ap点数据信息缓存
 * 
 * @author endy liu
 *
 */
public class RedisPoolUtil {
	// Redis服务器IP
	private static String ADDR = ConfigUtils
			.getProperty(Constants.REDIS_SERVER_IP);
	// Redis的端口号
	private static int PORT = StringUtils.isBlank(ConfigUtils
			.getProperty(Constants.REDIS_SERVER_PORT)) ? 6379 : Integer
			.valueOf(ConfigUtils.getProperty(Constants.REDIS_SERVER_PORT));
	// Redis的密码
	private static String PASS = StringUtils.isBlank(ConfigUtils
			.getProperty(Constants.REDIS_SERVER_PASS)) ? "5vwJ4AghYNH&W4RWejfieo69C2ZPp%r@" :
				ConfigUtils.getProperty(Constants.REDIS_SERVER_PASS);
	
	
	// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static int MAX_ACTIVE = StringUtils.isBlank(ConfigUtils
			.getProperty(Constants.REDIS_MAX_ACTIVE)) ? 5 : Integer
			.valueOf(ConfigUtils.getProperty(Constants.REDIS_MAX_ACTIVE));
	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = StringUtils.isBlank(ConfigUtils
			.getProperty(Constants.REDIS_POOL_MAXIDLE)) ? 1 : Integer
			.valueOf(ConfigUtils.getProperty(Constants.REDIS_POOL_MAXIDLE));

	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static int MAX_WAIT = 10000;

	private static int TIMEOUT = 10000;

	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = true;

	private static JedisPool jedisPool = null;

	/**
	 * 初始化Redis连接池
	 */
	static {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxActive(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWait(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT,PASS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	public synchronized static Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public static void returnResource(final Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}

	private static Log _log = LogFactory.getLog(RedisPoolUtil.class);

	/**
	 * 将存储key 序列化对象 及过期时间缓存进redis中
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 */
	public static void put(String key, Serializable value, int seconds) {
		Jedis jedis = getJedis();
		try {
			byte[] bytes = serialize(value);
			jedis.setex(key.getBytes(), seconds, bytes);
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
			jedisPool.returnResource(jedis);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}


	/**
	 * 将序列化对象缓存入redis中
	 * 
	 * @param key
	 * @param value
	 */
	public static void put(String key, Serializable value) {
		Jedis jedis = getJedis();
		try {
			byte[] bytes = serialize(value);
			jedis.set(key.getBytes(), bytes);
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 将数据key value seconds 缓存进redis缓存中
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 */
	public static void putString(String key, String value, int seconds) {
		Jedis jedis = getJedis();
		try {
			jedis.setex(key, seconds, value);
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

//	/**
//	 * 往redis加入字符串值
//	 *
//	 * @param key
//	 * @param value
//	 */
//	public static void putString(String key, String value) {
//		Jedis jedis = getJedis();
//		try {
//			jedis.set(key, value);
//		} catch (Exception e) {
//			_log.error(e.getMessage(), e);
//		} finally {
//			jedisPool.returnResource(jedis);
//		}
//	}

	/**
	 * 往redis加入字符串值
	 *
	 * @param key
	 * @param value
	 */
	public static boolean putString(String key, String value) {
		Jedis jedis = getJedis();
		try {
			jedis.set(key, value);
			return true;
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
			return false;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 根据指定的key值获取redis缓存中的对象信息值
	 * 
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		Object object = null;
		Jedis jedis = getJedis();
		try {
			byte[] objbytes = jedis.get(key.getBytes());
			if (objbytes != null && objbytes.length > 0) {
				object = unserialize(objbytes);
			}
			return object;
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return null;
	}

	/**
	 * 根据指定的key获取redis缓存中的字符串数据value
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key) throws Exception {
		Jedis jedis = getJedis();
		try {
			String value = null;
			value = jedis.get(key);
			return value;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	/**
	 * 根据指定的key获取redis缓存中的字符串数据value
	 *
	 * @param key
	 * @return
	 */
	public static boolean exists(String key)  {
		Jedis jedis = getJedis();
		try {
			return jedis.exists(key);
		}catch (Exception e) {
			_log.error(e.getMessage(), e);
		} finally {
			jedisPool.returnResource(jedis);
		}
		return  false;
	}
	/**
	 * 删除redis指定key值的value
	 * 
	 * @param key
	 */
	public static void remove(String key) {
		Jedis jedis = getJedis();
		try {
			jedis.del(key);
			Set<String> keys = jedis.keys(key);
			if (keys != null && keys.size() != 0) {
				jedis.del(keys.toArray(new String[keys.size()]));
			}
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 序列化存储的对象
	 * 
	 * @param object
	 * @return
	 */
	private static byte[] serialize(Object object) {
		byte[] bytes = null;
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			bytes = baos.toByteArray();
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
		return bytes;
	}

	/**
	 * 反序列化对象
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object unserialize(byte[] bytes) {
		Object object = null;
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			if (bytes != null) {
				bais = new ByteArrayInputStream(bytes);
				ois = new ObjectInputStream(bais);
				object = ois.readObject();
			}
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
		return object;
	}

	/**
	 * 追加字符串
	 * @param key
	 * @param value
	 */
	public static void appendString(String key, String value) {
		Jedis jedis = getJedis();
		try {
			if(jedis.exists(key)){
				jedis.append(key,value);
			}else{
				jedis.set(key,value);
			}
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 增加订阅者
	 * @param listener
	 * @throws Exception
     */
	public static void psubscribe(JedisPubSub listener) throws Exception {
		Jedis jedis = getJedis();
		try {
			jedis.psubscribe(listener, "__key*__:*");
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 设置 list
	 * @param <T>
	 * @param key
	 */
	public static  <T> void setList(String key ,List<T> list){
		Jedis jedis = getJedis();
		try {
			jedis.set(serialize(key),serialize(list));
		} finally{
			jedisPool.returnResource(jedis);
		}
	}

	public static  <T> void setListEx(String key ,int second,List<T> list){
		Jedis jedis = getJedis();
		try {
			jedis.setex(serialize(key),second,serialize(list));
		} finally{
			jedisPool.returnResource(jedis);
		}
	}
	/**
	 * 获取list
	 * @param <T>
	 * @param key
	 * @return list
	 */
	public static <T> List<T> getList(String key){
		Jedis jedis = getJedis();
		try {
			byte[] in = jedis.get(serialize(key));
			if(in==null){
				return  null;
			}
			List<T> list =null;
			list = (List<T>) unserialize(in);
			return list;
		}catch (Exception e) {
			_log.error(e.getMessage(), e);
			return  null;
		} finally{
			jedisPool.returnResource(jedis);
		}
	}
}
