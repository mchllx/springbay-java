package sg.edu.nus.iss.springbay;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class AppConfig {

    private Logger logger = Logger.getLogger(AppConfig.class.getName());

    //application.properties
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    @Value("${spring.redis.database}")
    private Integer redisDatabase;

    @Value("${spring.redis.username}")
    private String redisUser;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Bean(Utils.BEAN_REDIS)
    public RedisTemplate<String, String> redisTemplateFactory() {

        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort);
        config.setDatabase(redisDatabase);

        if (!redisUser.isEmpty()) {
            config.setUsername(redisUser);
        }
        if (!redisPassword.isEmpty()) {
            config.setPassword(redisPassword);
        }
       
        logger.log(Level.INFO, "Redis database: %d".formatted(redisPort));

        JedisClientConfiguration jedisClient = JedisClientConfiguration
            .builder().build();

        //if indirectly references, inject jedis dependency
        JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();

        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisFac);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());

        return template;
    }
    
}