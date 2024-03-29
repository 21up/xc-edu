package com.xuecheng.manage_cms.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    public static final  String EX_ROUTING_CMS_POSTPAGE="ex_routing_cms_postpage";
    @Bean(EX_ROUTING_CMS_POSTPAGE)
    public Exchange exchange_topics_inform(){
        return ExchangeBuilder.directExchange(EX_ROUTING_CMS_POSTPAGE).durable(true).build();
    }
}
