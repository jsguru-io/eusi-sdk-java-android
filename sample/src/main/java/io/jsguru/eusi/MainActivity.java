package io.jsguru.eusi;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import io.jsguru.eusisdk.Eusi;
import io.jsguru.eusisdk.EusiClient;
import io.jsguru.eusisdk.EusiQuery;
import io.jsguru.eusisdk.exceptions.EusiException;
import io.jsguru.eusisdk.exceptions.EusiSubmitFormException;

public class MainActivity extends AppCompatActivity {

    private Eusi eusi;
    private EusiClient eusiClient;
    private String eusiHost;
    private String eusiBucketID;
    private String eusiBucketSecret;
    private String eusiAuthToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eusiHost = "https://delivery.stg.eusi.cloud";
        eusiBucketID = "22520a82-b3c6-441b-9383-c5e4781c077c";
        eusiBucketSecret = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJidWNrZXRfaWQiOiIyMjUyMGE4Mi1iM2M2LTQ0MWItOTM4My1jNWU0NzgxYzA3N2MiLCJpZCI6IjFmYzIzN2RkLTJkM2ItNDM0Ni1iYWQ5LWEzNmMwODNlZmJmYSIsInRpbWVzdGFtcCI6MTUyMDUxNjgxOTgwM30.c_w4kuk9e78rKF_ZzOGW2wX7IklN7JLAtsikgk9Pwug";

        try {
            eusi = new Eusi(eusiHost, Eusi.ApiVersion.V1);
            eusiClient = eusi.createClient(eusiBucketID, eusiBucketSecret);

            eusiClient.setAuthToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJidWNrZXRfaWQiOiIyMjUyMGE4Mi1iM2M2LTQ0MWItOTM4My1jNWU0NzgxYzA3N2MiLCJidWNrZXRfa2V5X2lkIjoiMWZjMjM3ZGQtMmQzYi00MzQ2LWJhZDktYTM2YzA4M2VmYmZhIiwidHlwZSI6Im9wZW4ifQ.PWzaUH-EVLEvHWQCKQ0DalFEd705yw8tE_klYRH-Ir0");
            eusiClient.setUserToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJidWNrZXRfaWQiOiIyMjUyMGE4Mi1iM2M2LTQ0MWItOTM4My1jNWU0NzgxYzA3N2MiLCJ0eXBlIjoibWVtYmVyc2hpcCIsImJ1Y2tldF9tZW1iZXJfaWQiOiIwMmYzNzRmNi0yZDZhLTRkM2QtYTBlYy1jNjM5MzdhMTI1ODMiLCJzY29wZXMiOiJtZW1iZXJzaGlwIn0.NIE7MqwBqFKA5nZjxkJPJiT6AviZWjoJPgCVb8s9USI");


            EusiQuery query = new EusiQuery.Builder()
                    .withName("cont0101")
//                    .withElement("", "")
//                    .withType("image")
//                    .withTaxonomy("all")
                    .build();


//            eusiClient.getContentAsync(query,true, new EusiContentCallback() {
//                @Override
//                public void onSuccess(EusiContentResponse response) {
//                    Log.e("TAG+++", "getContentAsync(): response: " + response.getResponseString());
//                    Log.e("TAG+++", "getContentAsync(): type: "
//                            + response.getContentList().get(0).getContent().get(0).getType());
//
//                }
//
//                @Override
//                public void onFailure(String message) {
//                    Log.e("TAG+++", "getContentAsync(): errorMessage: " + message);
//                }
//            });



//            eusiClient.getTaxonomyAsync("3833762c-77d2-4ecd-849f-af1164637a60", new EusiTaxonomyCallback() {
//                @Override
//                public void onSuccess(EusiTaxonomyResponse response) {
//                    Log.e("TAG+++", "getTaxonomyAsync(): response: " + response.getResponseString());
//
//                    Log.e("TAG+++", "getTaxonomyAsync(): response: " + response);
//                }
//
//                @Override
//                public void onFailure(String message) {
//                    Log.e("TAG+++", "getTaxonomyAsync(): errorMessage: " + message);
//                }
//            });

//            String formID = "63176253-7e66-4fca-b3db-7d35a2bb6711";
//            String fromString = "{ \"FFg\" : \"valueSample02\" }";
//            Log.d("TAG+++", "fromString: " + fromString);
//            try {
//                eusiClient.submitFormAsync(formID, new JSONObject(fromString), new EusiSubmitFormCallback() {
//
//                    @Override
//                    public void onSuccess(String response, String formID) {
//                        Log.e("TAG+++", "getFormAsync(): formID: " + formID + " | response: " + response);
//                        Log.e("TAG+++", "getFormAsync(): formID: " + formID + " | response: " + response);
//                    }
//
//                    @Override
//                    public void onFailure(String message, String validation) {
//                        Log.e("TAG+++", "getFormAsync(): errorMessage: " + message);
//                        Log.e("TAG+++", "getFormAsync(): validationFailed: " + validation);
//                    }
//                });
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
////            final String formID = "a2243937-c7b7-4d39-871e-d4cbaa3af2ff";
//            final String formID02 = "63176253-7e66-4fca-b3db-7d35a2bb6711";
//            eusiClient.getFormAsync(formID02, new EusiFormCallback() {
//                @Override
//                public void onSuccess(EusiFormResponse response) {
//                    Log.e("TAG+++", "getFormAsync(): formID: " + formID02 + " | response: " + response.getResponseString());
//
//                    JSONObject object = new JSONObject();
//                    try {
////                        object.put("Text field", "txt1");
////                        object.put("Email field", "mail@something.com");
////                        object.put("Date field", Calendar.getInstance().getTimeInMillis());
////                        object.put("List field", "[\"value01\", \"value02\"]");
//
//                        object.put("FFg+", "sdasfasdsa");
//                        object.put("mailmail", "mail@gmail.com");
//                        object.put("dd", "" + Calendar.getInstance().getTimeInMillis());
//                        object.put("ddt", "" + Calendar.getInstance().getTimeInMillis());
//                        object.put("tf", true);
//                        object.put("urls", "https://lol.com");
//
//                        object.put("jjj", "val01");
//
//
//                        eusiClient.submitFormAsync(formID02, object, new EusiSubmitFormCallback() {
//                            @Override
//                            public void onSuccess(String response, String formId) {
//                                Log.e("TAG+++", "submitFormAsync(): response: " + response );
//                                Log.e("TAG+++", "submitFormAsync(): response: " + response );
//                            }
//
//                            @Override
//                            public void onFailure(String message, String validation) {
//                                Log.e("TAG+++", "submitFormAsync(): error: " + message + " | validation: " + validation);
//                                Log.e("TAG+++", "submitFormAsync(): error: " + message + " | validation: " + validation);
//                            }
//                        });
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(String message) {
//                    Log.e("TAG+++", "getFormAsync(): errorMessage: " + message);
//                }
//            });


//            eusiClient.authorizeAsync(new EusiAuthorizeCallback() {
//                                          @Override
//                                          public void onSuccess(String response, String authToken) {
//                                              Log.e("TAG+++", "authorizeAsync(): response: " + response);
//                                              //
//                                              eusiClient.registerAsync("fn", "ln", "x@email.com", "pass123", new EusiRegisterCallback() {
//                                                  @Override
//                                                  public void onSuccess(String response, String userToken) {
//                                                      Log.e("TAG+++", "registerAsync(): response: " + response);
//                                                      Log.e("TAG+++", "registerAsync(): userToken: " + response);
//                                                      eusiClient.getContentAsync(false, new EusiContentCallback() {
//                                                          @Override
//                                                          public void onSuccess(String response) {
//                                                              Log.e("TAG+++", "getContentAsync(): response: " + response);
//                                                          }
//
//                                                          @Override
//                                                          public void onFailure(String message) {
//                                                              Log.e("TAG+++", "getContentAsync(): errorMessage: " + message);
//
//                                                          }
//                                                      });
//                                                  }
//
//                                                  @Override
//                                                  public void onFailure(String message) {
//                                                      Log.e("TAG+++", "registerAsync(): errorMessage: " + message);
//                                                  }
//                                              });
//                                          }
//
//                                          @Override
//                                          public void onFailure(String message) {
//                                              Log.e("TAG+++", "authorizeAsync(): errorMessage: " + message);
//
//                                          }
//                                      });



            new AsyncTask<EusiClient, Void, String>(){
                @Override
                protected String doInBackground(EusiClient... eusiClients) {
                    EusiClient client = eusiClients[0];
//                    String resultAuth = null;
//                    try {
//                        Log.d("TAG+++", "resultAuth START");
//                        resultAuth = client.authorize();
//                        Log.d("TAG+++", "resultAuth: " + resultAuth);
//                    } catch (EusiAuthException  e) {
//                        Log.e("TAG+++", "EusiAuthException.getMessage(): " + e.getMessage());
//                        Log.e("TAG+++", "EusiAuthException.getLocalizedMessage: " + e.getLocalizedMessage());
//                        Log.e("TAG+++", "EusiAuthException.getCause: " + e.getCause());
//                        Log.e("TAG+++", "EusiAuthException.getStackTrace: " + e.getStackTrace());
//                        Log.e("TAG+++", "EusiAuthException.getErrorCode(): " + e.getErrorCode());
//                        Log.e("TAG+++", "EusiAuthException.getErrorMessage(): " + e.getErrorMessage());
//
//                        e.printStackTrace();
//                    } catch (IOException e){
//                        Log.e("TAG+++", "IOException.getMessage(): " + e.getMessage());
//                        Log.e("TAG+++", "IOException.getLocalizedMessage: " + e.getLocalizedMessage());
//                        Log.e("TAG+++", "IOException.getCause: " + e.getCause());
//                        Log.e("TAG+++", "IOException.getStackTrace: " + e.getStackTrace());
//
//                        e.printStackTrace();
//                    }
//                    Log.d("TAG+++", "resultAuth END");
//
//                    String resultRegister = null;
//                    try {
//                        Log.d("TAG+++", "resultRegister START");
//                        resultRegister = client.register("firstName01", "lastName01", "email01@gmail.com", "pass01");
//                        Log.d("TAG+++", "resultRegister: " + resultRegister);
//                    } catch (EusiAuthException  e) {
//                        Log.e("TAG+++", "EusiAuthException.getMessage(): " + e.getMessage());
//                        Log.e("TAG+++", "EusiAuthException.getLocalizedMessage: " + e.getLocalizedMessage());
//                        Log.e("TAG+++", "EusiAuthException.getCause: " + e.getCause());
//                        Log.e("TAG+++", "EusiAuthException.getErrorCode(): " + e.getErrorCode());
//                        Log.e("TAG+++", "EusiAuthException.getErrorMessage(): " + e.getErrorMessage());
//
//                        e.printStackTrace();
//                    } catch (IOException e){
//                        Log.e("TAG+++", "IOException.getMessage(): " + e.getMessage());
//                        Log.e("TAG+++", "IOException.getLocalizedMessage: " + e.getLocalizedMessage());
//                        Log.e("TAG+++", "IOException.getCause: " + e.getCause());
//
//                        e.printStackTrace();
//                    }
//                    Log.d("TAG+++", "resultRegister END");


//                    String resultLogIn = null;
//                    try {
//                        Log.d("TAG+++", "resultLogIn START");
//                        resultLogIn = client.logIn("email01@gmail.com", "pass01");
//                        Log.d("TAG+++", "resultLogIn: " + resultLogIn);
//                    } catch (EusiAuthException  e) {
//                        Log.e("TAG+++", "EusiAuthException.getMessage(): " + e.getMessage());
//                        Log.e("TAG+++", "EusiAuthException.getLocalizedMessage: " + e.getLocalizedMessage());
//                        Log.e("TAG+++", "EusiAuthException.getCause: " + e.getCause());
//                        Log.e("TAG+++", "EusiAuthException.getErrorCode(): " + e.getErrorCode());
//                        Log.e("TAG+++", "EusiAuthException.getErrorMessage(): " + e.getErrorMessage());
//
//                        e.printStackTrace();
//                    } catch (IOException e){
//                        Log.e("TAG+++", "IOException.getMessage(): " + e.getMessage());
//                        Log.e("TAG+++", "IOException.getLocalizedMessage: " + e.getLocalizedMessage());
//                        Log.e("TAG+++", "IOException.getCause: " + e.getCause());
//
//                        e.printStackTrace();
//                    }
//                    Log.d("TAG+++", "resultLogIn END");

                    client.setAuthToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJidWNrZXRfaWQiOiIyMjUyMGE4Mi1iM2M2LTQ0MWItOTM4My1jNWU0NzgxYzA3N2MiLCJidWNrZXRfa2V5X2lkIjoiMWZjMjM3ZGQtMmQzYi00MzQ2LWJhZDktYTM2YzA4M2VmYmZhIiwidHlwZSI6Im9wZW4ifQ.PWzaUH-EVLEvHWQCKQ0DalFEd705yw8tE_klYRH-Ir0");
                    client.setUserToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJidWNrZXRfaWQiOiIyMjUyMGE4Mi1iM2M2LTQ0MWItOTM4My1jNWU0NzgxYzA3N2MiLCJ0eXBlIjoibWVtYmVyc2hpcCIsImJ1Y2tldF9tZW1iZXJfaWQiOiIwMmYzNzRmNi0yZDZhLTRkM2QtYTBlYy1jNjM5MzdhMTI1ODMiLCJzY29wZXMiOiJtZW1iZXJzaGlwIn0.NIE7MqwBqFKA5nZjxkJPJiT6AviZWjoJPgCVb8s9USI");
//                    String resultContent = null;
//                    try {
//                        Log.d("TAG+++", "resultContent START");
//                        boolean membersContent = true;
//                        Log.d("TAG+++", "Include members content: " + membersContent);
//                        resultContent = client.getContent(membersContent);
//                        Log.d("TAG+++", "resultContent: " + resultContent);
//                    } catch (EusiContentException e) {
//                        Log.e("TAG+++", "EusiAuthException.getMessage(): " + e.getMessage());
//                        Log.e("TAG+++", "EusiAuthException.getLocalizedMessage: " + e.getLocalizedMessage());
//                        Log.e("TAG+++", "EusiAuthException.getCause: " + e.getCause());
//                        Log.e("TAG+++", "EusiAuthException.getErrorCode(): " + e.getErrorCode());
//                        Log.e("TAG+++", "EusiAuthException.getErrorMessage(): " + e.getErrorMessage());
//
//                        e.printStackTrace();
//                    } catch (IOException e){
//                        Log.e("TAG+++", "IOException.getMessage(): " + e.getMessage());
//                        Log.e("TAG+++", "IOException.getLocalizedMessage: " + e.getLocalizedMessage());
//                        Log.e("TAG+++", "IOException.getCause: " + e.getCause());
//
//                        e.printStackTrace();
//                    }
//                    Log.d("TAG+++", "resultContent END");

//                    String resultForm = null;
//                    try {
//                        Log.d("TAG+++", "resultForm START");
//                        String formID = "sampleform02";
//                        Log.d("TAG+++", "formID: " + formID);
//                        resultForm = client.getForm(formID);
//                        Log.d("TAG+++", "resultForm: " + resultForm);
//                    } catch (EusiContentException e) {
//                        Log.e("TAG+++", "EusiAuthException.getMessage(): " + e.getMessage());
//                        Log.e("TAG+++", "EusiAuthException.getLocalizedMessage: " + e.getLocalizedMessage());
//                        Log.e("TAG+++", "EusiAuthException.getCause: " + e.getCause());
//                        Log.e("TAG+++", "EusiAuthException.getErrorCode(): " + e.getErrorCode());
//                        Log.e("TAG+++", "EusiAuthException.getErrorMessage(): " + e.getErrorMessage());
//
//                        e.printStackTrace();
//                    } catch (IOException e){
//                        Log.e("TAG+++", "IOException.getMessage(): " + e.getMessage());
//                        Log.e("TAG+++", "IOException.getLocalizedMessage: " + e.getLocalizedMessage());
//                        Log.e("TAG+++", "IOException.getCause: " + e.getCause());
//
//                        e.printStackTrace();
//                    }
//                    Log.d("TAG+++", "resultForm END");
//
                    try {
                        Log.d("TAG+++", "resultFormPost START");
                        String formID = "63176253-7e66-4fca-b3db-7d35a2bb6711";
                        Log.d("TAG+++", "formID: " + formID);
                        JSONObject object = new JSONObject();
                            object.put("FFg", "sdasfasdsa");
                            object.put("mailmail", "mail@gmail.com");
                            object.put("dd", "" + Calendar.getInstance().getTimeInMillis());
                            object.put("ddt", "" + Calendar.getInstance().getTimeInMillis());
                            object.put("tf", true);
                            object.put("urls", "https://lol.com");


                        boolean res = client.submitForm(formID, object);

                        Log.d("TAG+++", "submitForm(): " + res);



                    } catch (IOException e){
                        Log.e("TAG+++", "IOException.getMessage(): " + e.getMessage());
                        Log.e("TAG+++", "IOException.getLocalizedMessage: " + e.getLocalizedMessage());
                        Log.e("TAG+++", "IOException.getCause: " + e.getCause());

                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    catch (EusiSubmitFormException e){
                        Log.d("TAG+++", "EusiSubmitFormException: " + e.getErrorMessage());
                        e.printStackTrace();
                    }
                    Log.d("TAG+++", "resultFormPost END");
//
//                    String resultTaxonomy = null;
//                    try {
//                        Log.d("TAG+++", "resultTaxonomy START");
//                        String taxonomyID = "all";
//                        Log.d("TAG+++", "taxonomyID: " + taxonomyID);
//                        resultTaxonomy = client.getTaxonomy(taxonomyID);
//                        Log.d("TAG+++", "resultTaxonomy: " + resultTaxonomy);
//                    } catch (EusiContentException e) {
//                        Log.e("TAG+++", "EusiAuthException.getMessage(): " + e.getMessage());
//                        Log.e("TAG+++", "EusiAuthException.getLocalizedMessage: " + e.getLocalizedMessage());
//                        Log.e("TAG+++", "EusiAuthException.getCause: " + e.getCause());
//                        Log.e("TAG+++", "EusiAuthException.getErrorCode(): " + e.getErrorCode());
//                        Log.e("TAG+++", "EusiAuthException.getErrorMessage(): " + e.getErrorMessage());
//
//                        e.printStackTrace();
//                    } catch (IOException e){
//                        Log.e("TAG+++", "IOException.getMessage(): " + e.getMessage());
//                        Log.e("TAG+++", "IOException.getLocalizedMessage: " + e.getLocalizedMessage());
//                        Log.e("TAG+++", "IOException.getCause: " + e.getCause());
//
//                        e.printStackTrace();
//                    }
//                    Log.d("TAG+++", "resultTaxonomy END");

//                    try {
//                        EusiTaxonomyResponse response = eusiClient.getTaxonomy("3833762c-77d2-4ecd-849f-af1164637a60");
//                        Log.e("TAG+++", "EusiTaxonomyResponse: " + response.getResponseString());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (EusiContentException e) {
//                        e.printStackTrace();
//                    }


                    return null;
                }

                @Override
                protected void onPostExecute(String s) {
                    Log.e("TAG+++", "Async done");
                    super.onPostExecute(s);
                }
            }.execute(eusiClient);

        } catch (EusiException e) {
            e.printStackTrace();
        }

    }
}
