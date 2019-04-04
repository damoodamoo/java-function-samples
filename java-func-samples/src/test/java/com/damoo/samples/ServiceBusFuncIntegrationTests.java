package com.damoo.samples;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

import com.microsoft.azure.servicebus.*;
import com.microsoft.azure.servicebus.management.*;
import com.microsoft.azure.servicebus.primitives.ConnectionStringBuilder;

/**
 * Integration test for Http + Service Bus Functions. 
 * Run manually when needed to ensure http / queue 1 / queue 2 are hooked up correctly
 * All other times, comment out @test attribute to prevent it being run on mvn verify
 */
public class ServiceBusFuncIntegrationTests {

    //@Test
    public void functionChainingTest() throws Exception {
       
        String connectionString = "service-bus-connection-string-here";
        String inQueueName = "in-queue";
        String outQueueName = "out-queue";
        String producerFuncUrl = "https://your-function-here.azurewebsites.net/api/ServiceBusProducerFunc";
        String funcKey = "func-key-here";
        int numItems = 100; // if higher than 1000 need to update clean queue method below

        // clear out queues
        ClearQueue(connectionString, inQueueName);
        ClearQueue(connectionString, outQueueName);

        CloseableHttpClient httpTextClient = HttpClientBuilder.create().build();
        URIBuilder builder;

        // POST a bunch of http
        try {            
            builder = new URIBuilder(producerFuncUrl);

            // Prepare the URI for the REST API method.
            URI uri = builder.build();

            for(int i = 0; i<numItems; i++){
                HttpPost request = new HttpPost(uri);

                // Request headers.
                request.setHeader("Content-Type", "application/json");
                request.setHeader("x-functions-key", funcKey);

                HttpEntity requestEntity = new StringEntity("{'name': 'david', 'favouriteFruit': 'bananas'}");
                request.setEntity(requestEntity);
                HttpResponse response = httpTextClient.execute(request);
                int status = response.getStatusLine().getStatusCode();
                assertEquals(204, status);
            }
        }catch(Exception e){
            
        }

        // wait a bit...
        Thread.sleep(2000);        

        // check queue 2
        QueueClient getQ = new QueueClient(new ConnectionStringBuilder(connectionString, outQueueName), ReceiveMode.RECEIVEANDDELETE);
        ManagementClient manageClt = new ManagementClient(new ConnectionStringBuilder(connectionString, outQueueName));
        QueueRuntimeInfo info = manageClt.getQueueRuntimeInfo(outQueueName);
        long msgCount = info.getMessageCountDetails().getActiveMessageCount();
        assertEquals(numItems, msgCount);       
    }

    private void ClearQueue(String connectionString, String queueName) throws Exception
    {
        // other than deleting + recreating the queue, it seems this is the best way to clear it :/ 
        QueueClient getQ = new QueueClient(new ConnectionStringBuilder(connectionString, queueName),
        ReceiveMode.RECEIVEANDDELETE);
        getQ.setPrefetchCount(1000);
        getQ.registerMessageHandler(new IMessageHandler() {
            @Override
            public CompletableFuture<Void> onMessageAsync(IMessage message) {
                return null;
            }

            @Override
            public void notifyException(Throwable exception, ExceptionPhase phase) {}
        });
        Thread.sleep(5000);
        getQ.close();
    }
}
