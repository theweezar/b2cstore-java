package com.ecom.b2cstore.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.template.TemplateLocation;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

@Configuration
public class ThymeleafCustomConfiguration {

    private static final Log logger = LogFactory.getLog(ThymeleafCustomConfiguration.class);

    private final ThymeleafProperties properties;

    private final ApplicationContext applicationContext;

    public ThymeleafCustomConfiguration(ThymeleafProperties properties, ApplicationContext applicationContext) {
        this.properties = properties;
        this.applicationContext = applicationContext;
        checkTemplateLocationExists();
    }

    private void checkTemplateLocationExists() {
        boolean checkTemplateLocation = this.properties.isCheckTemplateLocation();
        if (checkTemplateLocation) {
            TemplateLocation location = new TemplateLocation(this.properties.getPrefix());
            if (!location.exists(this.applicationContext)) {
                logger.warn("Cannot find template location: " + location
                        + " (please add some templates, check your Thymeleaf configuration, or set spring.thymeleaf."
                        + "check-template-location=false)");
            }
        }
    }

    @Bean
    SpringResourceTemplateResolver defaultTemplateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(this.applicationContext);
        resolver.setPrefix(this.properties.getPrefix());
        resolver.setSuffix(this.properties.getSuffix());
        resolver.setTemplateMode(this.properties.getMode());
        if (this.properties.getEncoding() != null) {
            resolver.setCharacterEncoding(this.properties.getEncoding().name());
        }
        resolver.setCacheable(false); // Disable cache for development
        Integer order = this.properties.getTemplateResolverOrder();
        if (order != null) {
            resolver.setOrder(order);
        }
        resolver.setCheckExistence(this.properties.isCheckTemplate());
        return resolver;
    }
}
