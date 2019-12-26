package ru.avn.sharding.controllers;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.avn.sharding.beans.AuditFileType;
import ru.avn.sharding.repositories.entities.AuditFile;
import ru.avn.sharding.shard.ShardingConfiguration;
import ru.avn.sharding.shard.WebClientShardFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/shards")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ShardInfoController {

    private final ShardingConfiguration shardingConfiguration;

    private final WebClientShardFactory webClientShardFactory;

    @GetMapping
    Object get() {
        return shardingConfiguration.getAllShards()
                .stream()
                .map(shardEndpoint -> Pair.of(shardEndpoint, webClientShardFactory.getWebClient(shardEndpoint)))
                .map(pair -> pair.getSecond()
                        .get()
                        .uri("/api/v1/audit_files/search")
                        .retrieve()
                        .toEntityList(AuditFile.class)
                        .map(entity -> Pair.of(pair.getFirst().getUrl(), Objects.requireNonNull(entity.getBody())
                                .stream()
                                .collect(Collectors.groupingBy(AuditFile::getType, Collectors.counting()))))
                        .timeout(Duration.ofSeconds(10))
                        .doOnError(throwable -> log.error("webclient error: {}", throwable.getMessage()))
                        .onErrorReturn(Pair.of(pair.getFirst().getUrl(), Collections.emptyMap()))
                )
                .collect(Collectors.collectingAndThen(Collectors.toList(), Flux::merge))
                .toStream()
                .collect(Collectors.groupingBy(Pair::getFirst, Collectors.mapping(Pair::getSecond, Collectors.toList())));
    }

    @Getter
    @Data
    public static class AuditFileWithHost {
        private final String host;
        private final Map<AuditFileType, Long> countByType;

        public static AuditFileWithHost of(String host, List<AuditFile> auditFiles) {
            Map<AuditFileType, Long> collect = auditFiles.stream()
                    .collect(Collectors.groupingBy(AuditFile::getType, Collectors.counting()));
            return new AuditFileWithHost(host, collect);
        }
    }


}
