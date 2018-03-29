package io.jsguru.eusisdk.exceptions;

/**
 * Created by Petar Suvajac on 3/8/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

public class EusiAuthException  extends EusiException{

    public static class ErrorAuth{
        public static final int ERROR_CODE_WRONG_BUCKET_SECRET = 403;
        public static final String ERROR_WRONG_BUCKET_SECRET = "The bucket secret is wrong";

        public static final int ERROR_CODE_NOT_AUTHORISED = 401;
        public static final String ERROR_NOT_AUTHORISED = "401 Unauthorized: Please authorise first";
    }

    public EusiAuthException(int errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

}
