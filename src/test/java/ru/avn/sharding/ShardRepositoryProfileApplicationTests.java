package ru.avn.sharding;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(ProfileName.SHARD_REPOSITORY)
@SpringBootTest
class ShardRepositoryProfileApplicationTests {

    @Test
    void contextLoads() {


    }

}
