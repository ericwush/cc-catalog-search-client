package io.confluent.catalog.search.client;

import io.confluent.catalog.search.service.proto.v1.CatalogSearchGrpc;
import io.confluent.catalog.search.service.proto.v1.Entity;
import io.confluent.catalog.search.service.proto.v1.SearchEntitiesRequest;
import io.confluent.catalog.search.service.proto.v1.SearchEntitiesResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A gRPC client for interacting with the Catalog Search Service.
 */
public class CatalogSearchGrpcClient implements AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(CatalogSearchGrpcClient.class);
    private static final int DEFAULT_SHUTDOWN_TIMEOUT_SECONDS = 5;

    private final ManagedChannel channel;
    private final CatalogSearchGrpc.CatalogSearchBlockingStub blockingStub;

    /**
     * Constructs a new client instance.
     *
     * @param host the host of the gRPC server
     * @param port the port of the gRPC server
     */
    public CatalogSearchGrpcClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext());
    }

    /**
     * Constructs a new client instance with a custom channel builder.
     *
     * @param channelBuilder the channel builder to use
     */
    public CatalogSearchGrpcClient(ManagedChannelBuilder<?> channelBuilder) {
        channel = channelBuilder.build();
        blockingStub = CatalogSearchGrpc.newBlockingStub(channel);
        log.info("Created new CatalogSearchGrpcClient");
    }

    /**
     * Searches for entities in the catalog.
     *
     * @param query  the search query
     * @param limit  maximum number of results to return
     * @param offset starting position for pagination
     * @return list of matching entities
     */
    public SearchResult searchEntities(String query, int limit, int offset) {
        log.debug("Searching entities with query: {}, limit: {}, offset: {}", query, limit, offset);
        
        SearchEntitiesRequest request = SearchEntitiesRequest.newBuilder()
            .setQuery(query)
            .setLimit(limit)
            .setOffset(offset)
            .build();

        SearchEntitiesResponse response = blockingStub.searchEntities(request);
        log.debug("Received {} entities", response.getEntitiesList().size());
        
        return new SearchResult(response.getEntitiesList(), response.getTotal());
    }

    @Override
    public void close() throws Exception {
        if (channel != null && !channel.isShutdown()) {
            channel.shutdown().awaitTermination(DEFAULT_SHUTDOWN_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            log.info("Shut down CatalogSearchGrpcClient");
        }
    }

    /**
     * Represents the result of a search operation.
     */
    public static class SearchResult {
        private final List<Entity> entities;
        private final int total;

        public SearchResult(List<Entity> entities, int total) {
            this.entities = entities;
            this.total = total;
        }

        public List<Entity> getEntities() {
            return entities;
        }

        public int getTotal() {
            return total;
        }
    }
}