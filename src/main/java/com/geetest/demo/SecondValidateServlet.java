package com.geetest.demo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.geetest.sdk.GeetestLib;
import com.geetest.sdk.GeetestLibResult;

/**
 * 二次验证接口，POST请求
 */
public class SecondValidateServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        GeetestLib gtLib = new GeetestLib(GeetestConfig.GEETEST_ID, GeetestConfig.GEETEST_KEY);
        String challenge = request.getParameter(GeetestLib.GEETEST_CHALLENGE);
        String validate = request.getParameter(GeetestLib.GEETEST_VALIDATE);
        String seccode = request.getParameter(GeetestLib.GEETEST_SECCODE);
        PrintWriter out = response.getWriter();
        int status = 0;
        String userId = "";
        try {
            // 从session中获取一次验证状态码和userId
            status = (Integer) request.getSession().getAttribute(GeetestLib.GEETEST_SERVER_STATUS_SESSION_KEY);
            userId = (String) request.getSession().getAttribute("userId");
        } catch (Exception e) {
            out.println("{\"result\":\"fail\",\"msg\":\"session取key发生异常\"}");
            return;
        }
        Map<String, String> paramMap = new HashMap<String, String>();
        // 自定义参数,可选择添加
        paramMap.put("user_id", userId); // 网站用户id
        paramMap.put("client_type", "web"); // web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        paramMap.put("ip_address", "127.0.0.1"); // 传输用户请求验证时所携带的IP
        GeetestLibResult result = null;
        if (status == 1) {
            result = gtLib.successValidate(challenge, validate, seccode, paramMap);
        } else {
            result = gtLib.failValidate(challenge, validate, seccode);
        }
        if (result.getStatus() == 1) {
            out.println(String.format("{\"result\":\"success\",\"version\":\"%s\"}", GeetestLib.VERSION));
        } else {
            out.println(String.format("{\"result\":\"fail\",\"version\":\"%s\",\"msg\":\"%s\"}", GeetestLib.VERSION, result.getMsg()));
        }
        out.println(result.getData());
    }
}
