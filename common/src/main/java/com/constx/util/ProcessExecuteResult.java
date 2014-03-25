package com.constx.util;

public class ProcessExecuteResult {
    private int code;
    private String out;
    private String err;

    public int getCode() {
        return code;
    }

    public ProcessExecuteResult setCode(int code) {
        this.code = code;
        return this;
    }

    public String getOut() {
        return out;
    }

    public ProcessExecuteResult setOut(String out) {
        this.out = out;
        return this;
    }

    public String getErr() {
        return err;
    }

    public ProcessExecuteResult setErr(String err) {
        this.err = err;
        return this;
    }
}
