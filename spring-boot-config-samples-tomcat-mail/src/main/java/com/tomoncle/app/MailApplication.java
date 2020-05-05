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

package com.tomoncle.app;

import com.tomoncle.config.springboot.mail.SmtpMailSender;
import com.tomoncle.config.springboot.mail.ThymeleafConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Main Class
 *
 * @author : tomoncle
 * @version : v1.0
 * @since : 19-5-14 下午9:47
 */
@RestController
@SpringBootApplication
public class MailApplication {
    private static Logger logger = LogManager.getLogger(MailApplication.class);

    private final SmtpMailSender smtpMailSender;

    private final ThymeleafConfiguration configuration;

    public MailApplication(SmtpMailSender smtpMailSender, ThymeleafConfiguration configuration) {
        this.smtpMailSender = smtpMailSender;
        this.configuration = configuration;
    }

    public static void main(String[] args) {
        ApplicationContext application = SpringApplication.run(MailApplication.class, args);
        if (logger.isDebugEnabled()) {
            String[] beanDefinitionNames = application.getBeanDefinitionNames();
            for (String beanName : beanDefinitionNames) {
                logger.debug(beanName);
            }
        }
    }


    /**
     * send mail
     *
     * @param username send user
     * @param mail     receive user
     * @return OK
     */
    @PostMapping("/sendMail")
    public String sendMail(@RequestParam("username") String username, @RequestParam("mail") String mail) {
        Map<String, Object> model = new HashMap<>();
        model.put("username", username);
        String text = configuration.mergeTemplateIntoString("mail", model);
        System.out.println(text);
        smtpMailSender.sendTextMail(mail, "test", text);
        return "ok";
    }


}
