package com.thoughtworks.beachhack.service;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.http.AmazonHttpClient;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.regions.DefaultAwsRegionProviderChain;

import java.io.ByteArrayInputStream;
import java.net.URI;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ElasticSearchClient {

    private static final String ELASTIC_SEARCH_SERVICE_NAME = "es";
    private static final String REGION = new DefaultAwsRegionProviderChain().getRegion();
    private static final String ELASTIC_SEARCH_ENDPOINT = System.getenv("ELASTIC_SEARCH_ENDPOINT");
    private static final AWSCredentials CREDENTIALS = new DefaultAWSCredentialsProviderChain().getCredentials();

    public void sendDocumentToElasticSearch(String indexName, String id, String payload) {
        final String requestEndpoint = String.format("%s/%s/%s", ELASTIC_SEARCH_ENDPOINT, indexName, id);

        final Request<Void> request = new DefaultRequest<>(ELASTIC_SEARCH_SERVICE_NAME);
        request.setContent(new ByteArrayInputStream(payload.getBytes(UTF_8)));
        request.setEndpoint(URI.create(requestEndpoint));
        request.setHttpMethod(HttpMethodName.POST);

        final AWS4Signer signer = new AWS4Signer();
        signer.setRegionName(REGION);
        signer.setServiceName(ELASTIC_SEARCH_SERVICE_NAME);
        signer.sign(request, CREDENTIALS);

        AmazonHttpClient httpClient = new AmazonHttpClient(new ClientConfiguration());
        httpClient.requestExecutionBuilder()
                .request(request)
                .execute();
    }

}
