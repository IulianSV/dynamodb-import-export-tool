# DynamoDB Import Export Tool
The DynamoDB Import Export Tool is designed to perform parallel scans on the source table, store scan results in a queue, then consume the queue by writing the items asynchronously to a destination table.

## Requirements ##
* Maven
* JRE 1.10+
* Pre-existing source and destination DynamoDB tables

## Running as an executable

1. Build the library:

```
    mvn clean package -DskipTests
```
> **NOTE**: Tests are failing when migrating to JDK 10. Will add another commit when failing tests will be fixed.

2. This produces the target jar in the target/ directory, to start the replication process:

`java -jar dynamodb-import-export-tool-1.0.2.jar`

`--destinationEndpoint <destination_endpoint>` // the DynamoDB endpoint where the destination table is located.

`--destinationTable <destination_table>` // the destination table to write to.

`--sourceEndpoint <source_endpoint>` // the endpoint where the source table is located.

`--sourceTable <source_table>`// the source table to read from.

`--readThroughputRatio <ratio_in_decimal>` // the ratio of read throughput to consume from the source table.

`--writeThroughputRatio <ratio_in_decimal>` // the ratio of write throughput to consume from the destination table.

`--maxWriteThreads <numWriteThreads> // (Optional, default=128 * Available_Processors)` Maximum number of write threads to create.

`--totalSections <numSections> // (Optional, default=1)` Total number of sections to split the bootstrap into. Each application will only scan and write one section.

`--section <sectionSequence> // (Optional, default=0)` section to read and write. Only will scan this one section of all sections, [0...totalSections-1].

`--consistentScan <boolean> // (Optional, default=false)` indicates whether consistent scan should be used when reading from the source table.

`--sourceFields <source_fields> //(Optional, default=[])` Comma separated field names representing the item field that needs to be renamed under a different name. The first field in source fields will be migrated into the first field in destinationFields. The second with the second and so on...

`--destinationFields <source_fields> //(Optional, default=[])` Comma separated field names representing the item new field that is to be renamed. The first field in source fields will be migrated into the first field in destinationFields. The second with the second and so on...

> **NOTE**: To split the replication process across multiple machines, simply use the totalSections & section command line arguments, where each machine will run one section out of [0 ... totalSections-1].