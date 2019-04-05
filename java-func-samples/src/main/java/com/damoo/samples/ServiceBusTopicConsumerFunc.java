package com.damoo.samples;

import com.microsoft.azure.functions.annotation.*;
import com.damoo.samples.models.QueueObj;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Service Topic Trigger.
 */
public class ServiceBusTopicConsumerFunc {
    /**
     * This function will be invoked when a new message is received at the Service Bus Topic.
     */
    @FunctionName("ServiceBusTopicConsumerFunc")
    public void run(
        @ServiceBusTopicTrigger(
            name = "inMessage",
            topicName = "inTopic",
            subscriptionName = "inSub",
            connection = "ServiceBusConStr"
        )
        QueueObj inObj,
        @ServiceBusTopicOutput(
            name = "outMessage",
            topicName = "outTopic",
            subscriptionName = "outSub",
            connection = "ServiceBusConStr"
        )
        OutputBinding<QueueObj> outObj,
        final ExecutionContext context
    ) {

        outObj.setValue(inObj);

        context.getLogger().info("Java Service Bus Topic trigger function executed.");
    }
}