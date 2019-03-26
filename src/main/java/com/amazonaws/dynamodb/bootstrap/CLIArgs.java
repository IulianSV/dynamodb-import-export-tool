package com.amazonaws.dynamodb.bootstrap;/*
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

import com.beust.jcommander.Parameter;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * This class contains the parameters to input when executing the program from
 * command line.
 */
public class CLIArgs extends CommandLineArgs {



    @Parameter(names = "--sourceFields", description = "Source fields comma separated. The number of source fields must match the number of destination fields. The first source field will be migrated to the ")
    public List<String> sourceFields = Lists.newArrayList();

    @Parameter(names = "--destinationFields", description = "Destination fields")
    public List<String> destinationFields = Lists.newArrayList();

    public List<String> getSourceFields() {
        return sourceFields;
    }

    public List<String> getDestinationFields() {
        return destinationFields;
    }
}
