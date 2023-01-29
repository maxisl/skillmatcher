/*
package restapi.config

import org.springframework.boot.web.servlet.MultipartConfigFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.servlet.MultipartConfigElement

import org.springframework.util.unit.DataSize

@Configuration
class MultipartConfig {

    @Bean
    fun multipartConfigElement(): MultipartConfigElement {
        val factory = MultipartConfigFactory()
        factory.setMaxFileSize(DataSize.ofBytes(1024 * 1024))        // 1024KB
        factory.setMaxRequestSize(DataSize.ofBytes(1024 * 1024))     // 1024KB
        return factory.createMultipartConfig()
    }
}

*/
