package com.tomoncle.app;

import com.tomoncle.config.springboot.EnableSpringBootConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(
        scanBasePackageClasses = EnableSpringBootConfig.class,
        scanBasePackages = {Application.SCAN_PACKAGES}
)
public class Application {
    static final String SCAN_PACKAGES = "com.tomoncle.app";
    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        ApplicationContext application = SpringApplication.run(Application.class, args);
        if (logger.isDebugEnabled()) {
            String[] beanDefinitionNames = application.getBeanDefinitionNames();
            for (String beanName : beanDefinitionNames) {
                logger.debug(beanName);
            }
        }
    }

}
