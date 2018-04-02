package io.jsguru.eusisdk.callbacks;

import io.jsguru.eusisdk.models.taxonomy.EusiTaxonomyResponse;

/**
 * Callback to be used with asynchronous calls in {@link io.jsguru.eusisdk.EusiClient} to get taxonomy
 * <p>
 * Implement {@link #onSuccess(EusiTaxonomyResponse)} to receive response object if request was successful
 * <p>
 * Implement {@link #onFailure(String)} to receive error message if request was unsuccessful
 *
 * @author Petar Suvajac (petars38@gmail.com / petar.suvajac@jsguru.io)
 * @version 1.0
 */

public abstract class EusiTaxonomyCallback extends EusiCallback {
    /**
     * Called if get taxonomy call in EusiClient was successful
     * @param response EusiTaxonomyResponse object
     *
     * @see EusiTaxonomyResponse
     */
    public abstract void onSuccess(EusiTaxonomyResponse response);

    /**
     * Called if get taxonomy call in EusiClient was unsuccessful
     * @param message Error message
     */
    public abstract void onFailure(String message);
}
