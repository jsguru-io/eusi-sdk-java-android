package io.jsguru.eusisdk.exceptions;

import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Superclass for known Eusi exceptions
 *
 * @author Petar Suvajac (petars38@gmail.com / petar.suvajac@jsguru.io)
 * @version 1.0
 */

public class EusiException extends Exception {

    private int errorCode;
    private String errorMessage;

    public static class Error{
        public static final int ERROR_CODE_API_VERSION_NOT_SUPPORTED = 404;
        public static final String ERROR_API_VERSION_NOT_SUPPORTED = "This api version isn't supported";
    }


    public EusiException(int errorCode, String errorMessage){
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    private EusiException(){
        super();
    }
    private EusiException(String message) {
        super(message);
    }
    private EusiException(String message, Throwable cause) {
        super(message, cause);
    }
    private EusiException(Throwable cause) {
        super(cause);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private EusiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    public int getErrorCode(){
        return errorCode;
    }
    public String getErrorMessage(){
        return errorMessage;
    }
}
