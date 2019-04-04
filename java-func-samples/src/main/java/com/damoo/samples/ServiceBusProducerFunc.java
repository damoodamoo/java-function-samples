package com.damoo.samples;

import com.microsoft.azure.functions.annotation.*;

import java.util.Optional;

import com.damoo.samples.models.QueueObj;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Http Trigger, pushing to Service Bus
 */
public class ServiceBusProducerFunc {
    /**
     * Accepts a custom object from the http post and pushes to Service Bus
     */
    @FunctionName("ServiceBusProducerFunc")
    public void run(
            @HttpTrigger(name = "request", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<QueueObj>> request,
            @ServiceBusQueueOutput(name = "msg", queueName = "%SBInputQueue%", connection = "ServiceBusConStr") OutputBinding<QueueObj> message,
            final ExecutionContext context
    ) {      

        Optional<QueueObj> r = request.getBody();
        message.setValue(r.get());
        
        context.getLogger().info("Java Service Bus Queue trigger function executed.");        
    }
}
