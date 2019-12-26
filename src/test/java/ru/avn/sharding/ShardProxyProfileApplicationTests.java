package ru.avn.sharding;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(ProfileName.SHARD_PROXY)
@SpringBootTest
class ShardProxyProfileApplicationTests {

    @Test
    void contextLoads() {


    }

}
