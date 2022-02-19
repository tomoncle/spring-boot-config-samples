/*
 * Copyright 2018 tomoncle
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tomoncle.app.fm;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static freemarker.template.Configuration.VERSION_2_3_31;

/**
 * @author tomoncle
 */
@RestController
@SpringBootApplication
public class Application {
    private static Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Configuration configuration() {
        Configuration configuration = new Configuration(VERSION_2_3_31);
        configuration.setClassForTemplateLoading(Application.class, "/templates/");
        return configuration;
    }

    @SneakyThrows
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getTemplate() {
        Template template = configuration().getTemplate("mail.html");
        Map<String, Object> map = new HashMap<>();
        map.put("username", "tom");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        logger.info(text);
        return text;
    }

    @SneakyThrows
    @RequestMapping(value = "/get2", method = RequestMethod.GET)
    public String getTemplate2() {
        String sourceCode = PrettyFileUtils.read("/templates/mail.html");
        Map<String, Object> map = new HashMap<>();
        map.put("username", "jack");
        Template template = new Template("mail.html", sourceCode, new Configuration(VERSION_2_3_31));
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        logger.info(text);
        return text;
    }
}

