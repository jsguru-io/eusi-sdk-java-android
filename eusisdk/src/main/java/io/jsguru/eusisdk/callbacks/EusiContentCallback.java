package io.jsguru.eusisdk.callbacks;

import io.jsguru.eusisdk.models.content.EusiContentResponse;

/**
 * Created by Petar Suvajac on 3/13/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

public abstract class EusiContentCallback extends EusiCallback {
    public abstract void onSuccess(EusiContentResponse response);
    public abstract void onFailure(String message);
}
