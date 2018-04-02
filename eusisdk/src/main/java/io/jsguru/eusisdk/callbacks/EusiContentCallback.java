package io.jsguru.eusisdk.callbacks;

import io.jsguru.eusisdk.models.content.EusiContentResponse;

/**
 * Callback to be used with asynchronous calls in {@link io.jsguru.eusisdk.EusiClient} to get content
 * <p>
 * Implement {@link #onSuccess(EusiContentResponse)} to receive response object if request was successful
 * <p>
 * Implement {@link #onFailure(String)} to receive error message if request was unsuccessful
 *
 * @author Petar Suvajac (petars38@gmail.com / petar.suvajac@jsguru.io)
 * @version 1.0
 */

public abstract class EusiContentCallback extends EusiCallback {
    /**
     * Called if get content call in EusiClient was successful
     * @param response EusiContentResponse object
     *
     * @see EusiContentResponse
     */
    public abstract void onSuccess(EusiContentResponse response);

    /**
     * Called if get content call in EusiClient was unsuccessful
     * @param message Error message
     */
    public abstract void onFailure(String message);
}
