# Azure Functions: Java Samples
This repo contains a number of basic samples of Functions written in Java to get you started. 

It also contains a few basic unit tests and an integration test to exercise some basic Functions and give you some jumping-off points.

It includes...

| Function | Trigger | Input | Output Binding(s) |
|--|--|--|--|
| **HttpFunc** | HttpTrigger | HttpRequestMessage | - |
| **BlobTriggerOutput** | BlobTrigger | byte[] from trigger | Blob |
| **CosmosFunc** | CosmosTrigger | POJO from trigger | CosmosOutput|
| **ServiceBusProducerFunc** | HttpTrigger | POJO from Http Post | ServiceBusOutput |
| **ServiceBusConsumerFunc** | ServiceBusTrigger | POJO from trigger | ServiceBusOutput |
| **StorageTableQueue** | HttpTrigger | POJO from Http | StroageOutput / TableOutput
| **EventGridFunc** | EventGridTrigger | POJO from event grid trigger | CosmosOutput |
| **HttpKeyVaultFunc** | HttpTrigger | - | (gets arbritrary value from Key Vault)

> **NOTE** - currently the `EventGridFunc` fails when calling it when deployed to Azure. See note in comment.

## Local Settings
The following local / app settings are used: *(local storage and Cosmos emulators used in dev)*
```JSON
{
  "IsEncrypted": false,
  "Values": {
    "AzureWebJobsStorage": "UseDevelopmentStorage=true",
    "FUNCTIONS_WORKER_RUNTIME": "java",
    "MyKVSecret": "@Microsoft.KeyVault(SecretUri=uri-to-a-secret-value)",
    "CosmosConStr": "AccountEndpoint=https://localhost:8081/;AccountKey=C2y6yDjf5/R+ob0N8A7Cgv30VRDJIWEHLM+4QDU5DE2nQ9nDuVTqobD4b8mGGyPMbIZnqyMsEcaGQy67XIw/Jw==",
    "DatabaseName": "my-db",
    "InputCollectionName": "my-col",
    "OutputCollectionName": "my-col-output",
    "LeaseCollectionName": "my-lease",
    "EventsCollectionName": "my-events",
    "ServiceBusConStr": "service-bus-con-string-here",
    "SBInputQueue": "in-queue",
    "SBOutputQueue": "out-queue",
    "StorageAccount": "UseDevelopmentStorage=true",
    "StorageTableName": "outtable",
    "StorageQueueName": "outqueue"
  }
}
```