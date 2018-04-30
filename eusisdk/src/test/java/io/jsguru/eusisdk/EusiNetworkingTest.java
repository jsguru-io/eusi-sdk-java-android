package io.jsguru.eusisdk;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Petar Suvajac (petars38@gmail.com / petar.suvajac@jsguru.io)
 * @version 1.0
 */
public class EusiNetworkingTest {

    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HEADER_VALUE_CONTENT_TYPE_JSON = "application/json";

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String HEADER_VALUE_AUTHORIZATION = "someAuthToken";

    @Test
    public void get() throws IOException, InterruptedException {
        // get server response string from resources
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("delivery_api_content_all.json");
        final String str = TestUtil.convertStreamToString(in);

        // mock server
        MockWebServer server = new MockWebServer();
        MockResponse mockResponse = new MockResponse();
        mockResponse.setBody(str);
        mockResponse.addHeader(HEADER_CONTENT_TYPE, HEADER_VALUE_CONTENT_TYPE_JSON);
        mockResponse.addHeader(HEADER_AUTHORIZATION, HEADER_VALUE_AUTHORIZATION);
        server.enqueue(mockResponse);
        server.start();

        // create eusi networking object
        EusiNetworking eusiNetworking = new EusiNetworking();
        Hashtable<String, String> headers = new Hashtable<>();
        headers.put(HEADER_CONTENT_TYPE, HEADER_VALUE_CONTENT_TYPE_JSON);
        headers.put(HEADER_AUTHORIZATION, HEADER_VALUE_AUTHORIZATION);

        // get response
        String url = server.url("/").toString();
        String response = eusiNetworking.get(url, headers, null);
        RecordedRequest recordedRequest = server.takeRequest();

        // assert response and headers
        assertNotNull(response);
        assertEquals(str, response);
        assertEquals(url, recordedRequest.getRequestUrl().toString());
        assertNotNull(recordedRequest.getHeader(HEADER_AUTHORIZATION));
        assertEquals(HEADER_VALUE_CONTENT_TYPE_JSON, recordedRequest.getHeader(HEADER_CONTENT_TYPE));
    }


    @Test
    public void post() throws IOException, InterruptedException {
        // get request and response strings from resources
        InputStream in1 = this.getClass().getClassLoader().getResourceAsStream("register_user_info.json");
        final String str1 = TestUtil.convertStreamToString(in1);
        InputStream in2 = this.getClass().getClassLoader().getResourceAsStream("delivery_api_register_user.json");
        final String str2 = TestUtil.convertStreamToString(in2);

        // mock server
        MockWebServer server = new MockWebServer();
        MockResponse mockResponse = new MockResponse();
        mockResponse.setBody(str2);
        mockResponse.addHeader(HEADER_CONTENT_TYPE, HEADER_VALUE_CONTENT_TYPE_JSON);
        mockResponse.addHeader(HEADER_AUTHORIZATION, HEADER_VALUE_AUTHORIZATION);
        server.enqueue(mockResponse);
        server.start();

        // create eusi networking object
        EusiNetworking eusiNetworking = new EusiNetworking();
        Hashtable<String, String> headers = new Hashtable<>();
        headers.put(HEADER_CONTENT_TYPE, HEADER_VALUE_CONTENT_TYPE_JSON);
        headers.put(HEADER_AUTHORIZATION, HEADER_VALUE_AUTHORIZATION);

        // get response
        String url = server.url("/").toString();
        String response = eusiNetworking.post(url, headers, str1, null);
        RecordedRequest recordedRequest = server.takeRequest();

        // assert response and headers
        assertNotNull(response);
        assertEquals(str2, response);
        assertEquals(url, recordedRequest.getRequestUrl().toString());
        assertEquals(str1, recordedRequest.getBody().readUtf8());
        assertNotNull(recordedRequest.getHeader(HEADER_AUTHORIZATION));
        assertTrue(recordedRequest.getHeader(HEADER_CONTENT_TYPE).toLowerCase().contains(HEADER_VALUE_CONTENT_TYPE_JSON.toLowerCase()));
    }

    @Test
    public void haveError() {
        InputStream in1 = this.getClass().getClassLoader().getResourceAsStream("delivery_api_content_all.json");
        final String responseOk = TestUtil.convertStreamToString(in1);

        InputStream in2 = this.getClass().getClassLoader().getResourceAsStream("delivery_api_error.json");
        final String responseError = TestUtil.convertStreamToString(in2);

        assertFalse(EusiNetworking.haveError(responseOk));
        assertTrue(EusiNetworking.haveError(responseError));
    }

    @Test
    public void getErrorCode() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("delivery_api_error.json");
        final String responseError = TestUtil.convertStreamToString(in);

        assertNotEquals(-1, EusiNetworking.getErrorCode(responseError));
    }

    @Test
    public void getErrorMessage() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("delivery_api_error.json");
        final String responseError = TestUtil.convertStreamToString(in);

        assertNotNull(EusiNetworking.getErrorMessage(responseError));
    }

    @Test
    public void getValidationMessage() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("delivery_api_error_validation.json");
        final String responseError = TestUtil.convertStreamToString(in);

        assertNotNull(EusiNetworking.getErrorMessage(responseError));
    }

    @Test
    public void checkForErrorsInResponse(){
        InputStream in1 = this.getClass().getClassLoader().getResourceAsStream("delivery_api_content_all.json");
        final String responseOk = TestUtil.convertStreamToString(in1);

        InputStream in2 = this.getClass().getClassLoader().getResourceAsStream("delivery_api_error_validation.json");
        final String responseError = TestUtil.convertStreamToString(in2);

        // responseOk
        if(!EusiNetworking.haveError(responseOk))
            assertFalse(EusiNetworking.haveError(responseOk));
        else
            assertTrue(EusiNetworking.haveError(responseOk));

        // responseError
        if(EusiNetworking.haveError(responseError)){
            assertTrue(EusiNetworking.haveError(responseError));

            assertNotEquals(-1, EusiNetworking.getErrorCode(responseError));
            assertNotNull(EusiNetworking.getErrorMessage(responseError));
            assertNotNull(EusiNetworking.getValidationMessage(responseError));
        }
    }
}