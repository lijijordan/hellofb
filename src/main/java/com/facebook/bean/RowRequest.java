package com.facebook.bean;

/**
 * User: liji
 * Date: 18/1/25
 * Time: 下午9:36
 */
public class RowRequest {
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RowRequest{" +
                "data='" + data + '\'' +
                '}';
    }
}
