package io.jsguru.eusisdk.exceptions;

/**
 * Created by Petar Suvajac on 3/27/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

public class EusiSubmitFormException extends EusiException {
    private String validation;

    public EusiSubmitFormException(int errorCode, String errorMessage) {
        this(errorCode, errorMessage, null);
    }

    public EusiSubmitFormException(int errorCode, String errorMessage, String validation) {
        super(errorCode, errorMessage);
        this.validation = validation;
    }

    public String getValidation() {
        return validation;
    }
}
