package io.jsguru.eusisdk.callbacks;

/**
 * Created by Petar Suvajac on 3/12/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

public abstract class EusiAuthorizeCallback extends EusiCallback {
    public abstract void onSuccess(String response, String authToken);
    public abstract void onFailure(String message);
}
