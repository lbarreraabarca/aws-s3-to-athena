package com.data.factory.adapters;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.data.factory.exceptions.DWHBridgeException;
import com.data.factory.exceptions.ObjectStorageException;
import com.data.factory.ports.DWHBridge;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.services.athena.AthenaClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.athena.model.AthenaException;
import software.amazon.awssdk.services.athena.model.QueryExecutionContext;
import software.amazon.awssdk.services.athena.model.ResultConfiguration;
import software.amazon.awssdk.services.athena.model.StartQueryExecutionRequest;
import software.amazon.awssdk.services.athena.model.StartQueryExecutionResponse;


public class AthenaOperator implements DWHBridge {

    private final String accessKeyID;
    private final String secretAccessKey;

    private final static String INVALID_ARGUMENT = "%s cannot be null or empty.";

    public AthenaOperator(String accessKeyID, String secretAccessKey) throws ObjectStorageException {
        if( accessKeyID == null || accessKeyID.isEmpty()) throw new ObjectStorageException(String.format(INVALID_ARGUMENT, "accessKeyID"));
        if( secretAccessKey == null || secretAccessKey.isEmpty()) throw new ObjectStorageException(String.format(INVALID_ARGUMENT, "secretAccessKey"));

        this.accessKeyID = accessKeyID;
        this.secretAccessKey = secretAccessKey;
    }


    private AWSCredentials getCredentials() {
        return new BasicAWSCredentials(this.accessKeyID, this.secretAccessKey);
    }

    private AthenaClient getClient() {
        return AthenaClient.builder()
                .region(Region.US_WEST_2)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }

    @Override
    public String runStatement(String databaseName, String statement) throws DWHBridgeException {
        try {
            QueryExecutionContext queryExecutionContext = QueryExecutionContext.builder()
                    .database(databaseName)
                    .build();

            ResultConfiguration resultConfiguration = ResultConfiguration.builder()
                    .outputLocation("bkt-athena-tmp-36a99c23-5c8d")
                    .build();

            StartQueryExecutionRequest startQueryExecutionRequest = StartQueryExecutionRequest.builder()
                    .queryExecutionContext(queryExecutionContext)
                    .queryString(statement)
                    .resultConfiguration(resultConfiguration).build();

            StartQueryExecutionResponse startQueryExecutionResponse = this.getClient().startQueryExecution(startQueryExecutionRequest);
            return startQueryExecutionResponse.queryExecutionId();

        } catch (AthenaException e) {
            throw new DWHBridgeException(String.format("%s %s", e.getClass(), e.getMessage()));
        }
    }
}
