package com.damoo.samples;
import com.damoo.samples.models.CosmosObj;
import com.microsoft.azure.functions.annotation.*;

import com.microsoft.azure.functions.*;

/**
 * Azure Function with Cosmos DB trigger and Cosmos output
 */
public class CosmosFunc {
    /**
     * This function will be invoked when there are inserts or updates in the specified database and collection.
     */
    @FunctionName("CosmosFunc")
    public void run(
        @CosmosDBTrigger(
            name = "items",
            databaseName = "%DatabaseName%",
            collectionName = "%InputCollectionName%",
            leaseCollectionName="%LeaseCollectionName%",
            connectionStringSetting = "CosmosConStr",
            createLeaseCollectionIfNotExists = true
        )
        CosmosObj[] items,
        @CosmosDBOutput(
            name = "cosmosOutput",
            connectionStringSetting = "CosmosConStr",
            createIfNotExists = true,
            databaseName = "%DatabaseName%",
            collectionName = "%OutputCollectionName%"
        ) OutputBinding<CosmosObj[]> outputObjs,
        final ExecutionContext context
    ) {

        // loop items and add arbritrary value, just because we can...
        for(CosmosObj obj : items){
            obj.name = obj.name + "--UPDATED";
        }
        
        outputObjs.setValue(items);

        context.getLogger().info("Java Cosmos DB trigger function executed.");
        context.getLogger().info("Documents count: " + items.length);
    }
}
