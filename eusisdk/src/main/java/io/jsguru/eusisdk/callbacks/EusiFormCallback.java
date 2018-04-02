package io.jsguru.eusisdk.callbacks;

import io.jsguru.eusisdk.models.form.EusiFormResponse;

/**
 * Callback to be used with asynchronous calls in {@link io.jsguru.eusisdk.EusiClient} to get form
 * <p>
 * Implement {@link #onSuccess(EusiFormResponse)} to receive response object if request was successful
 * <p>
 * Implement {@link #onFailure(String)} to receive error message if request was unsuccessful
 *
 * @author Petar Suvajac (petars38@gmail.com / petar.suvajac@jsguru.io)
 * @version 1.0
 */

public abstract class EusiFormCallback extends EusiCallback {
    /**
     * Called if get form call in EusiClient was successful
     * @param response EusiFormResponse object
     *
     * @see EusiFormResponse
     */
    public abstract void onSuccess(EusiFormResponse response);

    /**
     * Called if get form call in EusiClient was unsuccessful
     * @param message Error message
     */
    public abstract void onFailure(String message);
}
