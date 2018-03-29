package io.jsguru.eusisdk.callbacks;


/**
 * Created by Petar Suvajac on 3/27/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

public abstract class EusiSubmitFormCallback extends EusiCallback {
    public abstract void onSuccess(String response, String formId);
    public abstract void onFailure(String message, String validation);
}
