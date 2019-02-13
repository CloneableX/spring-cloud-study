package com.clo.scs.common.domain.result;

/**
 * @author XuHong
 * @date 2019年02月01日 15:12
 */
public class SingleResult<T> extends Result {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> SingleResult<T> buildSuccess(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setCode(Code.Success);
        result.setData(data);
        return result;
    }

    public static <T> SingleResult<T> buildFailure() {
        SingleResult<T> result = new SingleResult<>();
        result.setCode(Code.Error);
        return result;
    }

    public static <T> SingleResult<T> buildFailure(Code code, String message) {
        SingleResult<T> result = new SingleResult<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
