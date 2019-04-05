package com.damoo.samples;

import com.microsoft.azure.functions.annotation.*;

import java.util.Optional;

import com.damoo.samples.models.QueueObj;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Service Topic Trigger.
 */
public class ServiceBusTopicProducerFunc {
    /**
     * This function will be invoked when a new message is received at the Service Bus Topic.
     */
    @FunctionName("ServiceBusTopicProducerFunc")
    public void run(
        @HttpTrigger(name = "request", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<QueueObj>> request,
        @ServiceBusTopicOutput(
            name = "queueMessage",
            topicName = "inTopic",
            subscriptionName = "inSub",
            connection = "ServiceBusConStr"
        )
        OutputBinding<QueueObj> queueMessage,
        final ExecutionContext context
    ) {

        queueMessage.setValue(request.getBody().get());

        context.getLogger().info("Java Service Bus Topic trigger function executed.");
    }
}