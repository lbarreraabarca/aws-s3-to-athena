package com.data.factory.models;

import com.data.factory.exceptions.EnvironmentException;
import lombok.Getter;

@Getter
public class EnvironmentVariables {
    private String AWSKeyId;
    private String AWSSecretAccessKey;
    private String encodedInput;

    public EnvironmentVariables() throws EnvironmentException {
        this.AWSKeyId = this.validateAWSKeyId();
        this.AWSSecretAccessKey = this.validateAWSSecretAccessKey();
        this.encodedInput = this.validateEncodedInput();
    }

    public String validateAWSKeyId() throws EnvironmentException {
        String temp = System.getenv("ENV_AWS_KEY_ID");
        if (temp == null || temp.isEmpty()) throw new EnvironmentException("ENV_AWS_KEY_ID cannot be null or empty.");
        return temp;
    }

    public String validateAWSSecretAccessKey() throws EnvironmentException {
        String temp = System.getenv("ENV_AWS_ACCESS_KEY");
        if (temp == null || temp.isEmpty()) throw new EnvironmentException("ENV_AWS_ACCESS_KEY cannot be null or empty.");
        return temp;
    }

    public String validateEncodedInput() throws EnvironmentException {
        String temp = System.getenv("ENV_INPUT_CONTRACT");
        if (temp == null || temp.isEmpty()) throw new EnvironmentException("ENV_INPUT_CONTRACT cannot be null or empty.");
        return temp;
    }
}
