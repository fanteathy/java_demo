package me.ele.pts.exception;

import me.ele.rpc.exception.RpcException;

/**
 * Created by joshua on 16/11/14.
 */
public class UserException extends RpcException {
    public UserException(String message) {
        super(message);
    }
}
