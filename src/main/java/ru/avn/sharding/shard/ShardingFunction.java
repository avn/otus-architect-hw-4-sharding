package ru.avn.sharding.shard;

@FunctionalInterface
public interface ShardingFunction<T> {

    int apply(T input);

}
