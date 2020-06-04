package com.dk.controller.push;

import com.dk.entity.Push;
import com.dk.entity.ResultBody;
import com.dk.service.PushServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by wuzu on 2019/5/8.
 */
@Controller
@RequestMapping("/push")
public class PushController {
    private Logger log = LoggerFactory.getLogger(PushController.class);
    @Autowired
    private PushServiceI pushServiceI;

    /**
     * 新增消息通知信息
     * @param push
     * @return
     */
    @RequestMapping("/pushFormId")
    @ResponseBody
    public ResultBody pushFormId(Push push) {
        ResultBody result = new ResultBody<>("提交推送表单成功",200);
        try {
            pushServiceI.pushFormId(push);
        } catch (Exception e) {
            log.error("提交推送表单失败", e);
            return new ResultBody("提交推送表单失败");
        }
        return  result;
    }
}
