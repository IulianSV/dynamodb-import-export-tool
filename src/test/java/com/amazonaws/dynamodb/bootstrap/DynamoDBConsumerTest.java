/*
 * Copyright 2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Amazon Software License (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/asl/
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.amazonaws.dynamodb.bootstrap;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import static org.powermock.api.easymock.PowerMock.*;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.amazonaws.dynamodb.bootstrap.constants.BootstrapConstants;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.BatchWriteItemRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

/**
 * Unit tests for DynamoDBConsumerWorker
 * 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(DynamoDBConsumer.class)
@PowerMockIgnore("javax.management.*")
public class DynamoDBConsumerTest {

    private static String tableName = "testTableName";
    private double rateLimit = 12.3;
    private static AmazonDynamoDBClient mockClient;
    private static ThreadPoolExecutor mockThreadPool;

    /**
     * Sets up the initialization of common mocks for the below tests.
     */
    private static void setupMockInitialization() {
        mockClient = createMock(AmazonDynamoDBClient.class);
        mockThreadPool = createMock(ThreadPoolExecutor.class);
    }
    /**
     * Test that a ScanResult splits into the correct number of batches.
     */
    @Test
    public void splitResultIntoBatchesTest() {
        setupMockInitialization();
        final double numItems = 111.0;

        String tableName = "test tableName";

        ScanResult scanResult = new ScanResult();
        List<Map<String, AttributeValue>> items = new LinkedList<Map<String, AttributeValue>>();
        for (int i = 0; i < numItems; i++) {
            Map<String, AttributeValue> sampleScanResult = new HashMap<String, AttributeValue>();
            sampleScanResult.put("key", new AttributeValue("attribute value "
                    + i));
            items.add(sampleScanResult);
        }
        scanResult.setItems(items);

        SegmentedScanResult result = new SegmentedScanResult(scanResult, 0);

        replayAll();
        List<BatchWriteItemRequest> batches = new DynamoDBConsumer(mockClient, tableName, rateLimit, mockThreadPool)
                .splitResultIntoBatches(result.getScanResult(), tableName);
        assertEquals(Math.ceil(numItems / BootstrapConstants.MAX_BATCH_SIZE_WRITE_ITEM),
                batches.size(), 0.0);

        verifyAll();
    }

}
