package com.amazonaws.dynamodb.bootstrap;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

public class DynamoDBMigrationConsumer extends DynamoDBConsumer {

    private final List<Convertible> mappings;

    public DynamoDBMigrationConsumer(AmazonDynamoDB client, String tableName, List<Convertible> mappings, double rateLimit, ExecutorService exec) {
        super(client, tableName, rateLimit, exec);
        this.mappings = Optional.ofNullable(mappings).orElse(Lists.newArrayList());
    }

    @Override
    public List<Convertible> getMappings() {
        return mappings;
    }
}
