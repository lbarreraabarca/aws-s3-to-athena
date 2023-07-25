package com.data.factory.ports;

import com.data.factory.exceptions.DWHBridgeException;

public interface DWHBridge {
    String runStatement(String databaseName, String statement) throws DWHBridgeException;
}
