package ru.avn.sharding.shard.auditfile.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.avn.sharding.beans.AuditFileType;
import ru.avn.sharding.shard.ShardEndpoint;
import ru.avn.sharding.shard.ShardEndpointResolver;
import ru.avn.sharding.shard.ShardingConfiguration;
import ru.avn.sharding.shard.ShardingFunction;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuditFileTypeShardEndpointResolver implements ShardEndpointResolver<Collection<AuditFileType>> {

    private final ShardingFunction<AuditFileType> shardingFunction;

    private final ShardingConfiguration shardingConfiguration;

    @Override
    public List<ShardEndpoint> resolve(Collection<AuditFileType> input) {

        if (CollectionUtils.isEmpty(input)) {
            return shardingConfiguration.getAllShards();
        }

        return input.stream()
                .map(shardingFunction::apply)
                .map(shardingConfiguration::getShardByIndex)
                .collect(Collectors.toList());
    }
}
