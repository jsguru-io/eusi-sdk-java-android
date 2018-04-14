package io.jsguru.eusisdk.callbacks;

/**
 * Callback to be used with asynchronous calls in {@link io.jsguru.eusisdk.EusiClient} to submit form
 * <p>
 * Implement {@link #onSuccess(String)} to receive form id if request was successful
 * <p>
 * Implement {@link #onFailure(String, String)} to receive error message if request was unsuccessful
 *
 * @author Petar Suvajac (petars38@gmail.com / petar.suvajac@jsguru.io)
 * @version 1.0
 */

public abstract class EusiSubmitFormCallback extends EusiCallback {
    /**
     * Called if submit form call in EusiClient was successful
     * @param formId Id of submitted form
     */
    public abstract void onSuccess(String formId);

    /**
     * Called if submit form call in EusiClient was unsuccessful
     * @param message Error message
     * @param validation Validation for form
     */
    public abstract void onFailure(String message, String validation);
}
