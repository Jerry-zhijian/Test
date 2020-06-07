package c.b.s.common.logging.configuration;

import c.b.s.common.logging.LoggingUtil;
import c.b.s.common.logging.domain.ImportRecordRepository;
import c.b.s.common.logging.domain.LoginLogRepository;
import c.b.s.common.logging.domain.OperationLogRepository;
import c.b.s.common.logging.infrastructure.ImportRecordRepositoryImpl;
import c.b.s.common.logging.infrastructure.LoginLogRepositoryImpl;
import c.b.s.common.logging.infrastructure.OperationLogRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by guiqingqing on 2018/8/11.
 */
@Configuration
public class RepositoryConfiguration {
    @Bean
    public OperationLogRepository operationLogRepository() {
        return new OperationLogRepositoryImpl();
    }

    @Bean
    public LoginLogRepository loginLogRepository() {
        return new LoginLogRepositoryImpl();
    }

    @Bean
    public ImportRecordRepository importRecordRepository() {
        return new ImportRecordRepositoryImpl();
    }

    @Bean
    public LoggingUtil loggingUtil() {
        return new LoggingUtil();
    }
}