package ru.avn.sharding.shard.auditfile.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import ru.avn.sharding.utils.BeanToQueryParametersMapper;
import ru.avn.sharding.beans.AuditFileCreate;
import ru.avn.sharding.beans.AuditFileSearchCriteria;
import ru.avn.sharding.beans.AuditFileType;
import ru.avn.sharding.repositories.entities.AuditFile;
import ru.avn.sharding.shard.ShardEndpointResolver;
import ru.avn.sharding.shard.WebClientShardFactory;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuditFileClientImpl implements AuditFileClient {

    private final WebClientShardFactory webClientShardFactory;

    private final ShardEndpointResolver<Collection<AuditFileType>> shardEndpointResolver;

    @Override
    public List<AuditFile> search(AuditFileSearchCriteria searchCriteria) {
        return shardEndpointResolver.resolve(searchCriteria.getType())
                .stream()
                .map(webClientShardFactory::getWebClient)
                .map(webClient -> webClient.get()
                        .uri(uriBuilder -> uriBuilder.path("/api/v1/audit_files/search")
                                .queryParams(BeanToQueryParametersMapper.map(searchCriteria))
                                .build())
                        .retrieve()
                        .toEntityList(AuditFile.class)
                        .map(HttpEntity::getBody)
                        .timeout(Duration.ofSeconds(5))
                        .doOnError(throwable -> log.error("webclient error: {}", throwable.getMessage()))
                        .onErrorReturn(Collections.emptyList())
                        .flatMapMany(Flux::fromIterable)
                )
                .collect(Collectors.collectingAndThen(Collectors.toList(), Flux::merge))
                .sort(Comparator.comparing(AuditFile::getCreatedAt)
                        .reversed())
                .toStream()
                .collect(Collectors.toList());

    }

    @Override
    public AuditFile save(AuditFileCreate auditFileCreate) {

        return shardEndpointResolver.resolve(Collections.singletonList(auditFileCreate.getType()))
                .stream()
                .map(webClientShardFactory::getWebClient)
                .map(webClient -> webClient.post()
                        .uri("/api/v1/audit_files")
                        .bodyValue(auditFileCreate)
                        .retrieve()
                        .toEntity(AuditFile.class)
                        .timeout(Duration.ofSeconds(10))
                        .block())
                .findFirst()
                .map(HttpEntity::getBody)
                .orElseThrow(RuntimeException::new);
    }
}
