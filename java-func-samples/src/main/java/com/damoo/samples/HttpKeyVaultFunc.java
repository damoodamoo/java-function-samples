package com.damoo.samples;

import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with HTTP Trigger, getting value from Key Vault
 */
public class HttpKeyVaultFunc {
    /**
     * New Key Vault 'binding' test
     */
    @FunctionName("HttpKeyVaultFunc")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        // using the key vault binding stuff in preview
        String secret = System.getenv("MyKVSecret");

        return request.createResponseBuilder(HttpStatus.OK).body(secret).build();        
    }
}
