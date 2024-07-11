package ru.cft.template.config;


import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class MessageConfig {
    public MessageSource commonMessageSource() {
        ReloadableResourceBundleMessageSource commonMessageSource = new ReloadableResourceBundleMessageSource();
        commonMessageSource.setBasenames(
                "classpath:/messages/validationmessage",
                "classpath:/messages/regexpattern"
        );
        commonMessageSource.setDefaultEncoding("UTF-8");
        commonMessageSource.setCacheSeconds(3);
        commonMessageSource.setAlwaysUseMessageFormat(true);
        commonMessageSource.setUseCodeAsDefaultMessage(true);
        commonMessageSource.setFallbackToSystemLocale(true);
        return commonMessageSource;
    }

    @Bean
    @Primary
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(commonMessageSource());
        return bean;
    }
}
