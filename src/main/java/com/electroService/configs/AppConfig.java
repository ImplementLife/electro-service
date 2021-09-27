package com.electroService.configs;

import com.electroService.services.TelegramService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.jmx.ParentAwareNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource;
import org.springframework.jmx.export.naming.ObjectNamingStrategy;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.UUID;

@Configuration
public class AppConfig extends HikariConfig {
    // for @Value
//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }

    /*@Bean
    public static PropertySourcesPlaceholderConfigurer properties(){
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources = new ClassPathResource[] { new ClassPathResource( "foo.properties" ) };
        pspc.setLocations(resources);
        pspc.setIgnoreUnresolvablePlaceholders(true);
        return pspc;
    }*/

    private static TelegramService telegramService;

    @Bean
    public static TelegramService getTelegramService() {
        if (telegramService == null) {
            telegramService = new TelegramService();
            telegramService.telegramBotInit();
        }
        return telegramService;
    }
//    @Bean
//    public DataSource dataSource() throws SQLException {
//        HikariDataSource dataSource = new HikariDataSource(this);
//        dataSource.setPoolName("dataSource_" + UUID.randomUUID().toString());
//        return dataSource;
//    }
//
//    @Bean
//    @ConditionalOnMissingBean(value = ObjectNamingStrategy.class, search = SearchStrategy.CURRENT)
//    public ParentAwareNamingStrategy objectNamingStrategy() {
//        ParentAwareNamingStrategy namingStrategy = new ParentAwareNamingStrategy(new AnnotationJmxAttributeSource());
//        namingStrategy.setDefaultDomain("domain_" + UUID.randomUUID().toString());
//        return namingStrategy;
//    }

//    @Bean
//    public <T extends DefaultEntity> DefaultRestServiceImpl<T>
//    get(@Autowired @Qualifier() JpaRepository<T, Long> repository) {
//        DefaultRestServiceImpl<T> service = new DefaultRestServiceImpl<>();
//        service.setDao(repository);
//        return service;
//    }

}
