package ru.avn.sharding.shard;

import java.util.List;

public interface ShardEndpointResolver<T> {

    List<ShardEndpoint> resolve(T input);

}
