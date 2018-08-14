package com.nicommons.exception;

/**
 * ftp异常类
 *
 * @author feng
 * @date 2018-08-14 20:05
 **/
public class FtpException extends Exception{
    private static final long serialVersionUID = 1125602589533840257L;

    public FtpException(){
    }

    public FtpException(String msg){
        super(msg);
    }

    public FtpException(Throwable throwable){
        super(throwable);
    }

    public FtpException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
