package io.jsguru.eusisdk.callbacks;

/**
 * Callback to be used with asynchronous calls in {@link io.jsguru.eusisdk.EusiClient} to log in user
 * <p>
 * Implement {@link #onSuccess(String, String)} to receive response and user token if request was successful
 * <p>
 * Implement {@link #onFailure(String)} to receive error message if request was unsuccessful
 *
 * @author Petar Suvajac (petars38@gmail.com / petar.suvajac@jsguru.io)
 * @version 1.0
 */

public abstract class EusiLogInCallback extends EusiCallback {
    /**
     * Called if log in was successful
     * @param response Delivery API response
     * @param userToken User token
     */
    public abstract void onSuccess(String response, String userToken);

    /**
     * Called if log in was unsuccessful
     * @param message Error message
     */
    public abstract void onFailure(String message);
}
