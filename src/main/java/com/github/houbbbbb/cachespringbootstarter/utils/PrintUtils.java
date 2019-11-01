package com.github.houbbbbb.cachespringbootstarter.utils;


public class PrintUtils {
    public static void println(String key, Object value) {
        StringBuilder sb = new StringBuilder();
        sb.append(key).append(" : ").append(value);
        System.out.println(sb.toString());
    }
}
