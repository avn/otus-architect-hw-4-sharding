package ru.avn.sharding.shard;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebClientShardFactory {

    private final ConcurrentHashMap<ShardEndpoint, WebClient> webClientByEndpoint = new ConcurrentHashMap<>();

    public WebClient getWebClient(ShardEndpoint shardEndpoint) {
        return webClientByEndpoint.computeIfAbsent(shardEndpoint, this::createWebClient);
    }

    private WebClient createWebClient(ShardEndpoint shardEndpoint) {
        return WebClient.builder()
                .defaultHeader("Host", getHost(shardEndpoint))
                .baseUrl(shardEndpoint.getUrl())
                .build();
    }

    private static String getHost(ShardEndpoint shardEndpoint) {
        try {
            URL url = new URL(shardEndpoint.getUrl());
            return url.getHost() + ":" + url.getPort();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
