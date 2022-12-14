package org.shtrudell.client.net;

public class DataOperationException extends RuntimeException{
    public DataOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataOperationException(String message) {
        super(message);
    }
}
