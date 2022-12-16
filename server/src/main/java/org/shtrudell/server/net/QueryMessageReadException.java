package org.shtrudell.server.net;

public class QueryMessageReadException extends RuntimeException{

    public QueryMessageReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public QueryMessageReadException(String message) {
        super(message);
    }
}