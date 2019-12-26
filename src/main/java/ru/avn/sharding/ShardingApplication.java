package ru.avn.sharding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.projection.CollectionAwareProjectionFactory;
import org.springframework.data.projection.ProjectionFactory;
import ru.avn.sharding.shard.auditfile.client.AuditFileClientShardingConfiguration;

@SpringBootApplication(exclude = {WebFluxAutoConfiguration.class})
public class ShardingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingApplication.class, args);
    }

    @Bean
    ProjectionFactory projectionFactory(AuditFileClientShardingConfiguration auditFileClientShardingConfiguration) {
        System.out.println(auditFileClientShardingConfiguration.getAllShards());
        return new CollectionAwareProjectionFactory();
    }

}
