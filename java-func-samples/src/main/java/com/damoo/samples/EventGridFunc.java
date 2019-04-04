package com.damoo.samples;

import com.microsoft.azure.functions.annotation.*;

import com.damoo.samples.models.EventObj;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Event Grid Trigger.
 */
public class EventGridFunc {
    /**
     * CURRENTLY NOT WORKING WHEN DEPLOYED- "[Error] Executed 'Functions.EventGridFunc' (Failed, Id=93355eed-dac2-410e-b5e1-209ffba9609b)
        Format of the initialization string does not conform to specification starting at index 0."
        - https://github.com/Azure/azure-functions-java-worker/issues/247
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
        
        context.getLogger().info("Java Event Grid trigger function executed.");        
    }
}
