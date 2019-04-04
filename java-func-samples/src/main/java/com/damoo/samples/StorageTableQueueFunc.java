package com.damoo.samples;

import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.damoo.samples.models.HttpInputObj;
import com.damoo.samples.models.QueueObj;
import com.damoo.samples.models.StorageTableObj;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with HTTP Trigger, pushing to Storage Queue and Table
 */
public class StorageTableQueueFunc {
    /**
     * Accepts a custom object from Http Post
     */
    @FunctionName("StorageTableQueueFunc")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<HttpInputObj>> req,
            @TableOutput(name = "table", connection = "StorageAccount", dataType = "", tableName = "%StorageTableName%") OutputBinding<StorageTableObj> tableOutput,
            @QueueOutput(name = "queue", connection = "StorageAccount", dataType = "", queueName = "%StorageQueueName%") OutputBinding<QueueObj> queueOutput,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // get object
        HttpInputObj input = req.getBody().get();
            
        // table
        StorageTableObj tObj = new StorageTableObj(UUID.randomUUID().toString(), "demo", input.name, input.favouriteFruit);
        tableOutput.setValue(tObj);

        // storage queue
        QueueObj qObj = new QueueObj(input.name, input.favouriteFruit);
        queueOutput.setValue(qObj);

        // return a 202
        return req.createResponseBuilder(HttpStatus.ACCEPTED).build();
    }
}
