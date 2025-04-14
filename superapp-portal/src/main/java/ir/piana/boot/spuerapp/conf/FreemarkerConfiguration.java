package ir.piana.boot.spuerapp.conf;

import freemarker.template.TemplateModelException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class FreemarkerConfiguration implements WebMvcConfigurer {
    @Autowired
    private freemarker.template.Configuration configuration;

    @PostConstruct
    public void configuration() throws TemplateModelException {
        // add globe variable
        this.configuration.setSharedVariable("app", "Spring Boot");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}
