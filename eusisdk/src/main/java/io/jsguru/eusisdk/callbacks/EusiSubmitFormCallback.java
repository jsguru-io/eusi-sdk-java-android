package io.jsguru.eusisdk.callbacks;

/**
 * Callback to be used with asynchronous calls in {@link io.jsguru.eusisdk.EusiClient} to submit form
 * <p>
 * Implement {@link #onSuccess(String, String)} to receive response object if request was successful
 * <p>
 * Implement {@link #onFailure(String, String)} to receive error message if request was unsuccessful
 *
 * @author Petar Suvajac (petars38@gmail.com / petar.suvajac@jsguru.io)
 * @version 1.0
 */

public abstract class EusiSubmitFormCallback extends EusiCallback {
    /**
     * Called if submit form call in EusiClient was successful
     * @param response Delivery API response
     * @param formId Id of submitted form
     */
    public abstract void onSuccess(String response, String formId);

    /**
     * Called if submit form call in EusiClient was unsuccessful
     * @param message Error message
     * @param validation Validation for form
     */
    public abstract void onFailure(String message, String validation);
}
