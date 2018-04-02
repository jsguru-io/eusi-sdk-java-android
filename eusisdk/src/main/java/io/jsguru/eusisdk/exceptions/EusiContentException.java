package io.jsguru.eusisdk.exceptions;

/**
 * Known exceptions thrown in synchronous calls to get content from Eusi Delivery API
 *
 * @author Petar Suvajac (petars38@gmail.com / petar.suvajac@jsguru.io)
 * @version 1.0
 */

public class EusiContentException extends EusiException {

    public static class ErrorContent{
        public static final int ERROR_CODE_NOT_AUTHORIZED = 403;
        public static final String ERROR_NOT_AUTHORIZED = "403 Forbidden: Please authorize first to be able to access this content";
    }

    public EusiContentException(int errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
