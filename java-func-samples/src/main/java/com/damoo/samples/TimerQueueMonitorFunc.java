package com.damoo.samples;

import java.time.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.servicebus.management.ManagementClient;
import com.microsoft.azure.servicebus.management.MessageCountDetails;
import com.microsoft.azure.servicebus.management.QueueRuntimeInfo;
import com.microsoft.azure.servicebus.primitives.ConnectionStringBuilder;
import com.microsoft.azure.servicebus.primitives.ServiceBusException;
import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.TelemetryConfiguration;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Timer trigger, logging message details to App Insights
 */
public class TimerQueueMonitorFunc {
    /**
     * This function will be invoked periodically according to the specified
     * schedule.
     * 
     * @throws InterruptedException
     * @throws ServiceBusException
     */
    @FunctionName("TimerQueueMonitorFunc")
    public void run(@TimerTrigger(name = "timerInfo", schedule = "5 * * * * *") String timerInfo,
            final ExecutionContext context) throws ServiceBusException, InterruptedException {
        context.getLogger().info("Checking queues at: " + LocalDateTime.now());

        String telemetryKey = System.getenv("APPINSIGHTS_INSTRUMENTATIONKEY");
        String serviceBusConStr = System.getenv("ServiceBusConStr");
        String queueName1 = System.getenv("SBInputQueue");
        String queueName2 = System.getenv("SBOutputQueue");
        
       // for more configuration options you can use the ApplicationInsights.xml file
       // - see https://docs.microsoft.com/en-gb/azure/azure-monitor/app/java-get-started#3-add-an-applicationinsightsxml-file
        TelemetryConfiguration.getActive().setInstrumentationKey(telemetryKey);
        TelemetryClient teleClient = new TelemetryClient();
        
        // check queue 1  
        ManagementClient manageClt = new ManagementClient(new ConnectionStringBuilder(serviceBusConStr, queueName1));
        QueueRuntimeInfo info = manageClt.getQueueRuntimeInfo(queueName1);
        MessageCountDetails msgCount = info.getMessageCountDetails();
        teleClient.trackMetric(queueName1 + "-Active-Length", msgCount.getActiveMessageCount());
        teleClient.trackMetric(queueName1 + "-Deadletter-Length", msgCount.getDeadLetterMessageCount());
        teleClient.trackMetric(queueName1 + "-Scheduled-Length", msgCount.getScheduledMessageCount());

        // check queue 2
        manageClt = new ManagementClient(new ConnectionStringBuilder(serviceBusConStr, queueName2));
        info = manageClt.getQueueRuntimeInfo(queueName2);
        msgCount = info.getMessageCountDetails();
        teleClient.trackMetric(queueName2 + "-Active-Length", msgCount.getActiveMessageCount());
        teleClient.trackMetric(queueName2 + "-Deadletter-Length", msgCount.getDeadLetterMessageCount());
        teleClient.trackMetric(queueName2 + "-Scheduled-Length", msgCount.getScheduledMessageCount());

       // teleClient.flush();
    }
}
