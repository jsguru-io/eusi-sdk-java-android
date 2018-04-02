package io.jsguru.eusisdk.exceptions;

/**
 * Known exceptions thrown in synchronous calls to submit form to Eusi Delivery API
 *
 * @author Petar Suvajac (petars38@gmail.com / petar.suvajac@jsguru.io)
 * @version 1.0
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
