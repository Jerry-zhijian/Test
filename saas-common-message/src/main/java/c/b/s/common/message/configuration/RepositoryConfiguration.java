package c.b.s.common.message.configuration;

import c.b.s.common.message.domain.MessageFileRepository;
import c.b.s.common.message.domain.MessageRepository;
import c.b.s.common.message.domain.MessageService;
import c.b.s.common.message.infrastructure.MessageFileRepositoryImpl;
import c.b.s.common.message.infrastructure.MessageRepositoryImpl;
import c.b.s.common.message.interfaces.MessageApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by guiqingqing on 2018/8/11.
 */
@Configuration
public class RepositoryConfiguration {
    @Bean
    public MessageApi messageApi() {
        return new MessageApi();
    }

    @Bean
    public MessageService messageService() {
        return new MessageService();
    }

    @Bean
    public MessageRepository messageRepository() {
        return new MessageRepositoryImpl();
    }

    @Bean
    public MessageFileRepository messageFileRepository() {
        return new MessageFileRepositoryImpl();
    }


}