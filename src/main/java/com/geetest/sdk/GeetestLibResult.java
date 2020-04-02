/**
 * @(#)Result.JAVA, 2020年04月02日.
 *
 * Copyright 2020 GEETEST, Inc. All rights reserved.
 * GEETEST PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.geetest.sdk;

/**
 * sdk lib包的返回结果信息。
 *
 * @author liuquan@geetest.com
 */
public class GeetestLibResult {
    /**
     * 成功失败的标识码，1表示成功，0表示失败
     */
    private int status;

    /**
     * 返回数据，json格式
     */
    private String data;

    /**
     * 备注信息，如异常信息等
     */
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        if (msg == null || msg.isEmpty()) {
            return;
        }
        if (this.getMsg() == null || this.getMsg().isEmpty()) {
            this.msg = msg;
        } else {
            this.msg += "\n" + msg;
        }

    }

    @Override
    public String toString() {
        return "GeetestLibResult{" + "status=" + status + ", data='" + data
            + '\'' + ", msg='" + msg + '\'' + '}';
    }
}
