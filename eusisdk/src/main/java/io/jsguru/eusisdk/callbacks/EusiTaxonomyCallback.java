package io.jsguru.eusisdk.callbacks;

import io.jsguru.eusisdk.models.taxonomy.EusiTaxonomyResponse;

/**
 * Created by Petar Suvajac on 3/14/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

public abstract class EusiTaxonomyCallback extends EusiCallback {
    public abstract void onSuccess(EusiTaxonomyResponse response);
    public abstract void onFailure(String message);
}
