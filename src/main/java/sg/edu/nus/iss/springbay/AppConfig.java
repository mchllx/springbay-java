package sg.edu.nus.iss.springbay;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import sg.edu.nus.iss.springbay.services.ProductService;

@Configuration
public class AppConfig {

    private Logger logger = Logger.getLogger(AppConfig.class.getName());
    
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    @Value("${spring.redis.username}")
    private String redisUser;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Value("${spring.redis.database}")
    private Integer redisDatabase;

    @Bean(Utils.BEAN_REDIS)
    public RedisTemplate<String, String> createRedisTemplate() {
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
            logger.info("\t Host running on" + redisHost);
            logger.info("\t Port running on" + redisPort);
            logger.info("\t Database running on" + redisDatabase);
        
        config.setHostName(redisHost);
        config.setPort(redisPort);

        config.setDatabase(redisDatabase);
        if (!redisUser.isEmpty()) {
            config.setUsername(redisUser);
        }
        if (!redisPassword.isEmpty()) {
            config.setPassword(redisPassword);
        }
        
        JedisClientConfiguration jedisClient = JedisClientConfiguration
            .builder().build();

        JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();

        logger.info("\t jedisClient running on" + jedisClient);
        logger.info("\t jedisFac running on" + jedisFac);

        // Create template with the client
        final RedisTemplate<String, String> template = new RedisTemplate<>();

        template.setConnectionFactory(jedisFac);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        return template;
    }
}