package com.constx;


public abstract class Console {
    
    public static void err(String format, Object... args) {
        System.err.println(String.format(format, args));
    }
    
    public static void err(Exception e) {
        System.err.println(String.format("Exception: %s", e));
    }
    
    public static void err(StringBuilder sb) {
        System.err.println(sb);
    }
    
    public static void out(String format, Object... args) {
        System.out.println(String.format(format, args));
    }
    
    public static void out(StringBuilder sb) {
        System.out.println(sb);
    }
}
