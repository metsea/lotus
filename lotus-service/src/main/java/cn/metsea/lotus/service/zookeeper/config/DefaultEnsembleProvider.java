package cn.metsea.lotus.service.zookeeper.config;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import org.apache.curator.ensemble.EnsembleProvider;

/**
 * Default zookeeper connection provider
 */
public class DefaultEnsembleProvider implements EnsembleProvider {

    private String quorum;

    public DefaultEnsembleProvider(String quorum) {
        checkNotNull(quorum, "the zookeeper config zookeeper.quorum can't be null");
        this.quorum = quorum;
    }

    @Override
    public void start() throws Exception {
        // NOP
    }

    @Override
    public String getConnectionString() {
        return this.quorum;
    }

    @Override
    public void close() throws IOException {
        // NOP
    }
}
