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
 * 一次验证接口，GET请求
 */
public class FirstRegisterServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
        GeetestLib gtLib = new GeetestLib(GeetestConfig.geetestId,
            GeetestConfig.geetestKey);

        //自定义参数,可选择添加
        String userId = "test";
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("user_id", userId); //网站用户id
        paramMap.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        paramMap.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP

        // 进行一次验证，得到结果
        GeetestLibResult result = gtLib.register(paramMap);
        //将结果状态设置到session中
        request.getSession().setAttribute(
            GeetestConfig.GEETEST_SERVER_STATUS_SESSION_KEY,
            result.getStatus());
        // 将userid设置到session中
        // 注意，此处api1接口存入session，api2会取出使用，格外注意session的存取和分布式环境下的应用场景
        request.getSession().setAttribute("userId", userId);

        PrintWriter out = response.getWriter();

        // 注意，不要更改返回的结构和值类型
        out.println(result.getData());

    }
}
