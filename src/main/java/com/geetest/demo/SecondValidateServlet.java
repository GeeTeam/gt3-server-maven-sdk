package com.geetest.demo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import com.geetest.sdk.GeetestLib;
import com.geetest.sdk.GeetestLibResult;

/**
 * 二次验证接口，POST请求
 */
public class SecondValidateServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
        GeetestLib gtLib = new GeetestLib(GeetestConfig.geetestId,
            GeetestConfig.geetestKey);

        String challenge = request
            .getParameter(GeetestConfig.GEETEST_CHALLENGE);
        String validate = request.getParameter(GeetestConfig.GEETEST_VALIDATE);
        String seccode = request.getParameter(GeetestConfig.GEETEST_SECCODE);

        PrintWriter out = response.getWriter();

        int gt_server_status_code = 0;
        String userId = "";
        try {
            // 从session中获取一次验证状态码和userId
            gt_server_status_code = (Integer) request.getSession()
                .getAttribute(GeetestConfig.GEETEST_SERVER_STATUS_SESSION_KEY);
            userId = (String) request.getSession().getAttribute("userId");
        } catch (Exception e) {
            gtLib.gtlog(
                "SecondValidateServlet.doPost()：二次验证validate：session取key发生异常，"
                    + e.toString());
            out.println("{\"status\":\"fail\"}");
            return;
        }

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userId); //网站用户id
        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP

        GeetestLibResult result = null;
        // gt_server_status_code 为1代表一次验证register正常，走正常二次验证模式；为0代表一次验证异常，走failback模式
        if (gt_server_status_code == 1) {
            //gt-server正常，向极验服务器发起二次验证
            result = gtLib.successValidate(challenge, validate, seccode, param);
        } else {
            // gt-server非正常情况，进行failback模式本地验证
            result = gtLib.failValidate(challenge, validate, seccode);
        }

        // // 注意，不要更改返回的结构和值类型
        out.println(result.getData());
    }
}
