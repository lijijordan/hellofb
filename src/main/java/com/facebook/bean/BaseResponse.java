package com.facebook.bean;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午2:51
 * To change this template use File | Settings | File Templates.
 */
public class BaseResponse {

    public static final int SUCCESS = 1;
    /**
     * 状态码
     */
    private int code;

    private Object data;

    public BaseResponse(Object data) {
        this.code = SUCCESS;
        this.data = data;
    }

    public BaseResponse(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
