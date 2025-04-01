# Confluent Cloud Catalog Search Client

A Java client library for interacting with the Confluent Cloud Catalog Search Service.

## Features

- Simple gRPC client for the Catalog Search Service
- Support for entity search with pagination
- Automatic resource management with AutoCloseable
- Configurable channel settings

## Installation

Add the following to your `WORKSPACE` file:

```python
maven_install(
    artifacts = [
        "io.confluent.cloud:catalog-search-client:1.0.0",
    ],
    repositories = [
        "https://packages.confluent.io/maven/",
    ],
)
```

## Usage

Here's a simple example of how to use the client:

```java
try (CatalogSearchGrpcClient client = new CatalogSearchGrpcClient("localhost", 8080)) {
    CatalogSearchGrpcClient.SearchResult result = client.searchEntities("schema", 10, 0);
    System.out.printf("Found %d entities (total: %d)%n", 
        result.getEntities().size(), result.getTotal());
    
    for (Entity entity : result.getEntities()) {
        System.out.printf("Entity ID: %s, Type: %s%n", entity.getId(), entity.getType());
    }
}
```

## Building

The project uses Bazel for building. To build the project:

```bash
bazel build //...
```

## License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.

## Contributing

Please read CONTRIBUTING.md for details on our code of conduct and the process for submitting pull requests.