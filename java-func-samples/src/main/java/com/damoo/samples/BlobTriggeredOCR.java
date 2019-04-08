package com.damoo.samples;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Azure Function with Azure Blob trigger, blob output and Computer Vision generated OCR output
 */
public class BlobTriggeredOCR {
    /**
     * This function will be invoked when a new or updated blob is detected at the specified path. The blob contents are provided as input to this function.
     */
    @FunctionName("BlobTriggeredOCR")
    @StorageAccount("StorageAccount")
    public void run(
        @BlobTrigger(name = "content", path = "samples-workitems/{name}", dataType = "binary") byte[] content,
        @BindingName("name") String name,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Blob trigger function processed a blob. Name: " + name + "\n  Size: " + content.length + " Bytes");

        runSample(context, content);
    }

    private static final String subscriptionKey = "%ComputerVisionSubscriptionKey%";

    private static final String uriBase = "https://westeurope.api.cognitive.microsoft.com/vision/v2.0/ocr";

    public static void runSample(ExecutionContext context, byte[] content) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        try {
            URIBuilder uriBuilder = new URIBuilder(uriBase);

            uriBuilder.setParameter("language", "unk");
            uriBuilder.setParameter("detectOrientation", "true");

            // Request parameters.
            URI uri = uriBuilder.build();
            HttpPost request = new HttpPost(uri);

            // Request headers.
            request.setHeader("Content-Type", "application/octet-stream");
            request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

            // Request body.
            ByteArrayEntity requestEntity = new ByteArrayEntity(content);
            request.setEntity(requestEntity);

            // Call the REST API method and get the response entity.
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                // Format and display the JSON response.
                String jsonString = EntityUtils.toString(entity);
                JSONObject json = new JSONObject(jsonString);
                context.getLogger().info("REST Response:\n");
                context.getLogger().info(json.toString(2));
            }
        } catch (Exception e) {
            // Display error message.
            context.getLogger().info(e.getMessage());
        }
    }

}
