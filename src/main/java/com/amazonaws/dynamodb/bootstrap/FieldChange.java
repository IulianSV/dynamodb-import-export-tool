package com.amazonaws.dynamodb.bootstrap;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.Map;
import java.util.function.Function;

public class FieldChange implements Convertible {

    private final String fieldName;
    private final String newFieldName;
    private final Function<AttributeValue, AttributeValue> function;

    public FieldChange(String fieldName, String newFieldName, Function<AttributeValue, AttributeValue> function) {
        this.fieldName = fieldName;
        this.newFieldName = newFieldName;
        this.function = function;
    }

    public FieldChange(String fieldName, String newFieldName) {
        this.fieldName = fieldName;
        this.newFieldName = newFieldName;
        this.function = Function.identity();
    }

    public FieldChange(String fieldName, Function<AttributeValue, AttributeValue> function) {
        this.fieldName = fieldName;
        this.newFieldName = fieldName;
        this.function = function;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getNewFieldName() {
        return newFieldName;
    }

    public Function<AttributeValue, AttributeValue> getFunction() {
        return function;
    }

    public boolean isChangeFieldName() {
        return !fieldName.equals(newFieldName);
    }

    @Override
    public void convert(Map<String, AttributeValue> itemValues) {
        AttributeValue oldValue = itemValues.get(getFieldName());
        if (oldValue == null) {
            return;
        }
        itemValues.put(getNewFieldName(), getFunction().apply(oldValue));
        if (isChangeFieldName()) {
            itemValues.remove(getFieldName());
        }
    }
}
