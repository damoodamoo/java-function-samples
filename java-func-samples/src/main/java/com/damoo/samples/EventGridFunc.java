package com.damoo.samples;

import com.microsoft.azure.functions.annotation.*;

import com.damoo.samples.models.EventObj;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Event Grid Trigger.
 */
public class EventGridFunc {
    /**
     * Functions fired from event grid, dumps output to Cosmos DB collection
     */
    @FunctionName("EventGridFunc")
    public void run(
            @EventGridTrigger(dataType = "", name = "evt") EventObj evt,
            @CosmosDBOutput(
                name = "cosmosOutput",
                connectionStringSetting = "CosmosConStr",
                createIfNotExists = true,
                databaseName = "%DatabaseName%",
                collectionName = "%EventsCollectionName%"
            ) OutputBinding<EventObj> outputObj,
            final ExecutionContext context
    ) {      

        outputObj.setValue(evt);
        
        context.getLogger().info(evt.topic);        
    }
}
