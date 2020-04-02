package com.geetest.demo;

/**
 * 配置文件，可合理选择properties等方式自行设计
 */
public class GeetestConfig {

    /**
     * 填入自己在极验官网申请的账号id和key
     */
    public static final String geetestId = "48a6ebac4ebc6642d68c217fca33eb4d";

    public static final String geetestKey = "4f1c085290bec5afdc54df73535fc361";

    /**
     * 调试开关，是否输出调试日志
     */
    public static final boolean isDebug = true;

    /**
     * 以下字段值与前端交互，基本无需改动。
     * 极验验证API服务状态Session Key
     */
    public static final String GEETEST_SERVER_STATUS_SESSION_KEY = "gt_server_status";

    /**
     * 极验二次验证表单传参字段 chllenge
     */
    public static final String GEETEST_CHALLENGE = "geetest_challenge";

    /**
     * 极验二次验证表单传参字段 validate
     */
    public static final String GEETEST_VALIDATE = "geetest_validate";

    /**
     * 极验二次验证表单传参字段 seccode
     */
    public static final String GEETEST_SECCODE = "geetest_seccode";

}
