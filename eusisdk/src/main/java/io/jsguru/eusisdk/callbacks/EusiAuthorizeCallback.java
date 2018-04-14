package io.jsguru.eusisdk.callbacks;

/**
 * Callback to be used with authorise asynchronous call in {@link io.jsguru.eusisdk.EusiClient}
 * <p>
 * Implement {@link #onSuccess(String)} to receive authorization token if request was successful
 * <p>
 * Implement {@link #onFailure(String)} to receive error message if request was unsuccessful
 *
 *
 * @author Petar Suvajac (petars38@gmail.com / petar.suvajac@jsguru.io)
 * @version 1.0
 */

public abstract class EusiAuthorizeCallback extends EusiCallback {
    /**
     * Called if auth call in EusiClient was successful
     * @param authToken Authorization token
     *
     * @see io.jsguru.eusisdk.EusiClient
     */
    public abstract void onSuccess(String authToken);

    /**
     * Called if auth call in EusiClient was unsuccessful
     * @param message Error message
     */
    public abstract void onFailure(String message);
}
