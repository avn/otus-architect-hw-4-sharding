package ru.avn.sharding;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@ActiveProfiles(ProfileName.SHARD_PROXY)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class ShardProxyProfileApplicationTests {

    @Test
    void contextLoads() {


    }

}
