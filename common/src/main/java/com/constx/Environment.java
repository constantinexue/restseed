package com.constx;

import org.apache.commons.lang3.StringUtils;

import java.lang.management.ManagementFactory;

public class Environment {
    
    private static Environment current;
    
    private Profile profile;
    
    private String version;

    private int processId;
    
    private Environment() {
    }

    public static Environment config(Profile profile) {
        return config("0.1.0", profile);
    }
    
    public static Environment config(String version, Profile profile) {
        current = new Environment();
        current.profile = profile;
        current.version = version;
        current.processId = Integer.parseInt(StringUtils.split(ManagementFactory.getRuntimeMXBean().getName(), "@")[0]);
        return current;
    }
    
    public static Environment current() {
        return current;
    }
    
    public String version() {
        return version;
    }
    
    public String profile() {
        return profile.name();
    }

    public int processId() {
        return processId;
    }
    
    public boolean isDevelopment() {
        return profile == Profile.DEVELOPMENT;
    }
    
    public boolean isProduction() {
        return profile == Profile.PRODUCTION;
    }
    
    public static enum Profile {
        DEVELOPMENT,
        PRODUCTION
    }
}
