package com.damoo.samples;

import com.microsoft.azure.functions.annotation.*;
import com.damoo.samples.models.QueueObj;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Service Bus Trigger.
 */
public class ServiceBusConsumerFunc {
    /**
     * This function will be invoked when a new message is received at the Service Bus Queue.
     * Message is copied to secondary SB Queue
     */
    @FunctionName("ServiceBusConsumerFunc")
    public void run(
            @ServiceBusQueueTrigger(name = "inMessage", queueName = "%SBInputQueue%", connection = "ServiceBusConStr") QueueObj inMessage,
            @ServiceBusQueueOutput(name = "outMessage", queueName = "%SBOutputQueue%", connection = "ServiceBusConStr") OutputBinding<QueueObj> outMessage,
            final ExecutionContext context
    ) {
        outMessage.setValue(inMessage);

        context.getLogger().info("Java Service Bus Queue trigger function executed.");
    }
}
