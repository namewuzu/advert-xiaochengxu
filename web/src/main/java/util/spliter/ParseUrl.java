package util.spliter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import sun.misc.BASE64Decoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 解密加密过的字符串
 * Created by lihan on 2017/7/29.
 */
public class ParseUrl {
    private Map<String, String> dict1;
    private Map<String, String[]> dict2;
    
    // 获得16进制数，该数用来分割字符串
    public Map<String,String> getHex(String param1){
        dict1 = new HashMap<String, String>();
        String cstr =  param1.substring(4);//str
        String[] splitStr = param1.substring(0,4).split("");
        String hex = "";
        for (int i=3; i >= 0; i--){
            hex = hex + splitStr[i];
        }
        dict1.put("str", cstr);
        dict1.put("hex", hex);
        return dict1;
    }
    // 获取正确的字符串,解析16进制数
    public Map<String, String[]> getDecimal(String param1){
        dict2 = new HashMap<String, String[]>();
        // loc2是用来分割字符串的索引标识，转换16进制
        String loc2 = String.valueOf(Integer.parseInt(param1,16));
        String[] pre = loc2.substring(0,2).split("");//dict1.put("loc2", loc2.substring(0,2));
        String[] tail = loc2.substring(2).split("");
        dict2.put("pre", pre);
        dict2.put("tail", tail);
        return dict2;
    }
    // 分割字符串
    public String substr(String param1, String[] param2) {
        String loc3 = param1.substring(0, Integer.parseInt(param2[0]));//param2 = pu.getDec(pa2).get("pre")
        String loc4 = param1.substring(Integer.parseInt(param2[0]), Integer.parseInt(param2[0])+Integer.parseInt(param2[1]));
        return loc3 + param1.substring(Integer.parseInt(param2[0])).replace(loc4, "");
    }
    // 获取分割的位置
    public String[] getPosition(String param1, String[] param2){
        param2[0] = String.valueOf(param1.length() - Integer.parseInt(param2[0]) - Integer.parseInt(param2[1]));
        return param2;
    }

    public static String getVedioLink(String baseUrl) throws Exception {
        ParseUrl pu = new ParseUrl();
        String code = parsehtml(baseUrl);
        Map<String, String> dict2 = pu.getHex(code);
        Map<String,String[]> dict3 = pu.getDecimal(dict2.get("hex"));
        String str4 = pu.substr(dict2.get("str"), dict3.get("pre"));
        BASE64Decoder base64 = new BASE64Decoder();
        byte[] url = base64.decodeBuffer(pu.substr(str4, pu.getPosition(str4, dict3.get("tail"))));
        // 视频真实的url地址
        System.out.println(new String(url));
        return new String(url);
    }

    // 根据url请求html页面
    public static String clawer2(String myurl) throws Exception{
        URL urlmy = new URL(myurl);
        HttpURLConnection con = (HttpURLConnection) urlmy.openConnection();
        con.setFollowRedirects(true);
        con.setInstanceFollowRedirects(false);
        con.connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
        String s = "";
        StringBuffer sb = new StringBuffer("");
        while ((s = br.readLine()) != null) {
            sb.append(s+"\r\n");
        }
        return sb.toString();
    }

    // 根据url请求html页面
    public static String clawerHearder(String myurl,Map<String,String> head) throws Exception{
        URL urlmy = new URL(myurl);
        HttpURLConnection con = (HttpURLConnection) urlmy.openConnection();
        for (Map.Entry<String, String> entry : head.entrySet()) {
            con.setRequestProperty(entry.getKey(),entry.getValue());
        }
        con.setFollowRedirects(true);
        con.setInstanceFollowRedirects(false);
        con.connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
        String s = "";
        StringBuffer sb = new StringBuffer("");
        while ((s = br.readLine()) != null) {
            sb.append(s+"\r\n");
        }
        return sb.toString();
    }

    // 通过Jsoup包来解析列表html页面
    public static String parseListPage(String url) throws Exception {
        String baseUrl = "https://www.meipai.com";
        String htmlContent = clawer2(url);
        //System.out.println(htmlContent);
        //使用jSoup解析里头的内容
        //就像操作html doc文档对象一样操作网页中的元素
        Document doc = Jsoup.parse(htmlContent);
        //Element span = doc.select("ul:[id=mediasList").get(0);
        Elements elements = doc.select("li[class=pr no-select loading  J_media_list_item]");
        Iterator it = elements.iterator();
        while(it.hasNext()) {
            try{
                Element element = (Element)it.next();
                //视频首页图片
                Node vedioImgNode= element.child(1);//图片节点
                String vedioImgUrl = vedioImgNode.attr("src");//视频图片
                String vedioTitle = vedioImgNode.attr("alt");//视频标题
                System.out.println("视频图片="+vedioImgUrl);
                System.out.println("视频标题="+vedioTitle);

                //视频详情页面信息
                Node vedioPageNode = element.child(2).childNode(3);
                String vedioPageUrl = baseUrl+vedioPageNode.attr("href");//视频页面地址
                System.out.println("视频页面地址="+vedioPageUrl);

                //用户信息节点
                Node userNode= element.child(3);
                Node userImgNode = userNode.childNode(1).childNode(1);
                String userImg = userImgNode.attr("src");//用户图片
                Node userNameNode  = userNode.childNode(3).childNode(1);
                String userName = userNameNode.attr("title");//用户昵称

                System.out.println("用户图片="+userImg);
                //System.out.println("用户昵称="+userName);

                //获取视频链接地址
                String vedioUrl = getVedioLink(vedioPageUrl);//视频地址
                System.out.println("视频地址="+vedioUrl);
            }catch (Exception e){
                continue;
            }

        }
        return null;
    }

    // 通过Jsoup包来解析详情页面html页面
    public static String parsehtml(String url) throws Exception {
        String htmlContent = clawer2(url);
        //System.out.println(htmlContent);
        //使用jSoup解析里头的内容
        //就像操作html doc文档对象一样操作网页中的元素
        Document doc = Jsoup.parse(htmlContent);
        //Element span = doc.select("meta:[property=og:video:url").get(0);
        Element span = doc.select("meta[property=og:video:url]").get(0);
        System.out.println(span.attr("content"));
        return span.attr("content");// 获取加密后的视频url地址
    }

    // 程序入口
    public static void main(String[] args) throws Exception {
        /*ParseUrl pu = new ParseUrl();
        // 获取html中的加密字符串
        String code = parsehtml("https://www.meipai.com/media/1116294726");
        Map<String, String> dict2 = pu.getHex(code);
        Map<String,String[]> dict3 = pu.getDecimal(dict2.get("hex"));
        String str4 = pu.substr(dict2.get("str"), dict3.get("pre"));
        BASE64Decoder base64 = new BASE64Decoder();
        byte[] url = base64.decodeBuffer(pu.substr(str4, pu.getPosition(str4, dict3.get("tail"))));
        // 视频真实的url地址
        System.out.println(new String(url));*/
        parseListPage("https://www.meipai.com/square/474?p=5");
    }
}