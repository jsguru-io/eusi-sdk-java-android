package io.jsguru.eusisdk;

import org.junit.Test;

import io.jsguru.eusisdk.models.content.EusiContent;
import io.jsguru.eusisdk.models.content.EusiContentResponse;
import io.jsguru.eusisdk.models.content.EusiContentType;
import okhttp3.mockwebserver.MockWebServer;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void sample() throws Exception {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.url("real url");
    }

    @Test
    public void checkNullInContent() throws Exception {
        String eusiHost = "https://delivery.stg.eusi.cloud";
        String eusiBucketID = "22520a82-b3c6-441b-9383-c5e4781c077c";
        String eusiBucketSecret = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJidWNrZXRfaWQiOiIyMjUyMGE4Mi1iM2M2LTQ0MWItOTM4My1jNWU0NzgxYzA3N2MiLCJpZCI6IjFmYzIzN2RkLTJkM2ItNDM0Ni1iYWQ5LWEzNmMwODNlZmJmYSIsInRpbWVzdGFtcCI6MTUyMDUxNjgxOTgwM30.c_w4kuk9e78rKF_ZzOGW2wX7IklN7JLAtsikgk9Pwug";

        Eusi eusi = new Eusi(eusiHost, Eusi.ApiVersion.V1);
        EusiClient client = eusi.createClient(eusiBucketID, eusiBucketSecret);
        client.authorize();

        EusiQuery query = new EusiQuery.Builder()
                .withName("My Name")
//                    .withElement("", "")
//                    .withType("image")
//                    .withTaxonomy("all")
                .build();
        EusiContentResponse responseString = client.getContent(query, true);
        assertNotNull(responseString);

        EusiContentResponse response = (new EusiParser()).parseContent(responseString);
        assertNotNull(response);

        for(EusiContent content: response.getContentList()){
            assertNotNull(content);

            for(EusiContentType picker: content.getContent()){
                assertNotNull(picker);
            }
        }
    }
}