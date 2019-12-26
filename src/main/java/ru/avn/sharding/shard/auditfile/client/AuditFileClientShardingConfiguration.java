package ru.avn.sharding.shard.auditfile.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.avn.sharding.shard.ShardEndpoint;
import ru.avn.sharding.shard.ShardingConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "audit.file.api.endpoint")
public class AuditFileClientShardingConfiguration implements ShardingConfiguration {

    private Map<Integer, ShardEndpoint> shards;

    private List<ShardEndpoint> allShardEndpoints;

    @Override
    public ShardEndpoint getShardByIndex(int index) {
        return shards.computeIfAbsent(index, i -> {
            throw new RuntimeException("Shard is not found by index:" + i);
        });
    }

    @Override
    public List<ShardEndpoint> getAllShards() {
        return allShardEndpoints;
    }

    @Override
    public int getNumberOfShards() {
        return shards.size();
    }

    public void setShards(Map<Integer, ShardEndpoint> shards) {
        this.shards = shards;
        this.allShardEndpoints = Collections.unmodifiableList(new ArrayList<>(shards.values()));
    }
}
