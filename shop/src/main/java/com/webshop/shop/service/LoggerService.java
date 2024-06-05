package com.webshop.shop.service;

public interface LoggerService {
    void debug(String message);

    void info(String message);

    void warn(String message);

    void error(String message);

    void error(String message, Throwable throwable);
}
