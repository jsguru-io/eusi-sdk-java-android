package io.jsguru.eusisdk;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.jsguru.eusisdk.exceptions.EusiException;

/**
 * Class is entry point to Eusi SDK and holds references to all EusiClient
 * Used to create EusiClient objects and hold references to them
 *
 * @author Petar Suvajac
 * @version 1.0
 * @see EusiClient
 *
 * Created by Petar Suvajac on 3/8/2018
 * Contact: petars38@gmail.com / petar.suvajac@jsguru.io
 */

public class Eusi {
    // Main Eusi delivery API url
    private String host;

    // API Version
    private String apiVersion;

    // Map of EusiClients with Bucket Id as key
    private HashMap<String, EusiClient> clientsMap;

    /**
     * Constructor
     * @param host Eusi delivery API url
     * @param apiVersion Api Version
     * @throws EusiException in case of unsupported API version
     *
     * @see ApiVersion
     */
    public Eusi(@NonNull String host, @NonNull String apiVersion) throws EusiException {
        while (host.endsWith("/"))
            host = host.substring(0, host.length() - 2);
        this.host = host;

        List<String> apiVersions = ApiVersion.getApiVersions();
        if(!apiVersions.contains(apiVersion))
            throw new EusiException(EusiException.Error.ERROR_CODE_API_VERSION_NOT_SUPPORTED, EusiException.Error.ERROR_API_VERSION_NOT_SUPPORTED);
        this.apiVersion = apiVersion;
        clientsMap = new HashMap<>();
    }

    /**
     * @return Eusi delivery API url
     */
    public String getHost() {
        return host;
    }

    /**
     * @return Eusi API version used
     */
    public String getApiVersion() {
        return apiVersion;
    }

    /**
     * Creates EusiClient with one bucket
     * @param bucketID Bucket Id
     * @param bucketSecret Bucket Secret
     * @return EusiClient
     *
     * @see EusiClient
     */
    public EusiClient createClient(String bucketID, String bucketSecret){
        EusiClient client = new EusiClient(this, bucketID, bucketSecret);
        clientsMap.put(bucketID, client);
        return client;
    }

    /**
     * Returns client with requested bucket by Bucket Id
     * @param bucketID Bucket Id
     * @return EusiClient if created, else null
     *
     * @see EusiClient
     */
    public EusiClient getClient(String bucketID){
        return clientsMap.get(bucketID);
    }


    /**
     *  Class contains list of supported API versions
     */
    public static class ApiVersion{
        // API version 1
        public static final String V1 = "v1";

        private ApiVersion(){}

        static List<String> getApiVersions(){
            List<String> apiVersions = new ArrayList<>();
            apiVersions.add(V1);
            return apiVersions;
        }
    }
}
