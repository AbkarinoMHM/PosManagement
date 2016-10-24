package com.ebe.common;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by saado on 10/24/2016.
 * General logger based on slf4j specifications
 */
@Component
public class GeneralLogger {

    private org.slf4j.Logger logger = LoggerFactory.getLogger("EbePosManagement");

    public static void debug(Class clazz, String msg) {
        LoggerFactory.getLogger(clazz).debug("Application Debugger: " + msg);
    }

    public static void info(Class clazz, String msg) {
        LoggerFactory.getLogger(clazz).info("Application Debugger: " + msg);
    }

    public static void error(Class clazz, String msg) {
        LoggerFactory.getLogger(clazz).error("Application Debugger: " + msg);
    }

}
