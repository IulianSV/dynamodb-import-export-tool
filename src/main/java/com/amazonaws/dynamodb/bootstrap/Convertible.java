package com.amazonaws.dynamodb.bootstrap;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.Map;

public interface Convertible {

    void convert(Map<String, AttributeValue> itemValues);
}
