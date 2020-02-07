package com.lyg.blog.pojo.vo;

import lombok.Getter;

import java.io.Serializable;

/**
 * Created by winggonLee on 2020/2/6
 */
@Getter
public class ResponseDomain<T> implements Serializable {
    private static final long serialVersionUID = -2473299945839141385L;

    private T data;

    private String timestamp;

    private int status = 999;

    private String message;

    private String other = null;

    /**
     * set from Aspect.doAround
     * @param timestamp
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp + "ms";
    }

    public ResponseDomain<T> setSuccess() {
        status = 200;
        return this;
    }

    public ResponseDomain<T> setData(T data) {
        this.data = data;
        return this;
    }

    public ResponseDomain<T> setStatus(int status) {
        this.status = status;
        return this;
    }

    public ResponseDomain<T> setMessage(String message) {
        this.message = message;
        return this;
    }

}