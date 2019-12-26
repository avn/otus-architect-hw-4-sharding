package ru.avn.sharding.shard;

import java.util.List;

public interface ShardingConfiguration {

    ShardEndpoint getShardByIndex(int index);

    List<ShardEndpoint> getAllShards();

    int getNumberOfShards();
}
