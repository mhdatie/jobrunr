package org.jobrunr.tests.e2e;

import org.jobrunr.storage.StorageProvider;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class MongoDBGsonE2ETest extends AbstractE2EGsonTest {

    private static Network network = Network.newNetwork();

    @Container
    private static GenericContainer mongoContainer = new MongoDBContainer("mongo:4.2.8")
            .withNetwork(network)
            .withNetworkAliases("mongo")
            .withExposedPorts(27017);

    @Container
    private static MongoDBGsonBackgroundJobContainer backgroundJobServer = new MongoDBGsonBackgroundJobContainer(mongoContainer, network);

    @Override
    protected StorageProvider getStorageProviderForClient() {
        return backgroundJobServer.getStorageProviderForClient();
    }

    @Override
    protected AbstractBackgroundJobContainer backgroundJobServer() {
        return backgroundJobServer;
    }
}
