package c.b.s.common.fileupload.configuration;

import c.b.s.common.fileupload.FileService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by guiqingqing on 2018/8/30.
 */
@Configuration
public class FileUploadConfiguration {
    @Bean
    public FileService fileService() {
        return new FileService();
    }
}