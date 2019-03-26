package com.amazonaws.dynamodb.bootstrap;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.Map;
import java.util.function.Supplier;

public class NewField implements Convertible {

    private final String fieldName;
    private final Supplier<AttributeValue> supplier;

    public NewField(String fieldName, Supplier<AttributeValue> supplier) {
        this.fieldName = fieldName;
        this.supplier = supplier;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Supplier<AttributeValue> getSupplier() {
        return supplier;
    }

    @Override
    public void convert(Map<String, AttributeValue> itemValues) {
        itemValues.put(getFieldName(), getSupplier().get());
    }
}
