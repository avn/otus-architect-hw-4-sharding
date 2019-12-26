package ru.avn.sharding.shard.auditfile.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.avn.sharding.beans.AuditFileType;
import ru.avn.sharding.shard.ShardingConfiguration;
import ru.avn.sharding.shard.ShardingFunction;

@Component
public class AuditFileTypeShardingFunction implements ShardingFunction<AuditFileType> {

    private final int numberOfShards;

    public AuditFileTypeShardingFunction(@Autowired ShardingConfiguration shardingConfiguration) {
        this.numberOfShards = shardingConfiguration.getNumberOfShards();
    }

    @Override
    public int apply(AuditFileType input) {
        return input.getValue() % numberOfShards;
    }
}
