package lottery.domain.support.ids;

import lottery.common.Constants;
import lottery.domain.support.ids.policy.RandomNumeric;
import lottery.domain.support.ids.policy.ShortCode;
import lottery.domain.support.ids.policy.SnowFlake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class IdContext {

    @Bean
    public Map<Constants.Ids, IIDGenerator> idGenerator(SnowFlake snowFlake, ShortCode shortCode, RandomNumeric randomNumeric) {
       Map<Constants.Ids, IIDGenerator> idGeneratorMap = new HashMap<>();
       idGeneratorMap.put(Constants.Ids.RandomNumeric, randomNumeric);
       idGeneratorMap.put(Constants.Ids.SnowFlake, snowFlake);
       idGeneratorMap.put(Constants.Ids.ShortCode, shortCode);
       return idGeneratorMap;
    }

}
