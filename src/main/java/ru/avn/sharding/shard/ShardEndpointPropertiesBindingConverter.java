package ru.avn.sharding.shard;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class ShardEndpointPropertiesBindingConverter implements Converter<String, ShardEndpoint> {

    @Override
    public ShardEndpoint convert(String source) {
        return new ShardEndpoint(source);
    }
}
