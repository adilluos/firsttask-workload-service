//package com.adilzhan.workload.config;
//
//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.jms.annotation.EnableJms;
//
//@Configuration
//@Profile("!aws")
//@EnableJms
//public class ActiveMqConfig {
//    public static final String WORKLOAD_QUEUE = "workload.updates";
//
//    @Bean
//    public ActiveMQConnectionFactory activeMQConnectionFactory(
//            @Value("${app.activemq.broker-url}") String brokerUrl
//    ) {
//        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
//        factory.setBrokerURL(brokerUrl);
//        return factory;
//    }
//}
