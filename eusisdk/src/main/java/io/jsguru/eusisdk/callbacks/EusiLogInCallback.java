package io.jsguru.eusisdk.callbacks;

/**
 * Created by Petar Suvajac on 3/13/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

public abstract class EusiLogInCallback extends EusiCallback {
    public abstract void onSuccess(String response, String userToken);
    public abstract void onFailure(String message);
}
