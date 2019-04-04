package com.damoo.samples;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Function with Azure Blob trigger and blob output
 */
public class BlobTriggerOutput {
    /**
     * This function will be invoked when a new or updated blob is detected at the specified path. The blob contents are provided as input to this function.
     * Input blob gets copied to output blob
     */
    @FunctionName("BlobTriggerOutput")
    @StorageAccount("StorageAccount")
    public void run(
        @BlobTrigger(name = "input", path = "dump1/{name}", dataType = "binary") byte[] inContent,
        @BlobOutput(name = "output", path = "dump2/{name}", dataType = "binary") OutputBinding<byte[]> outContent,
        @BindingName("name") String name,
        final ExecutionContext context
    ) {
        outContent.setValue(inContent);

        context.getLogger().info("Java Blob trigger function processed a blob. Name: " + name + "\n  Size: " + inContent.length + " Bytes");
    }
}
