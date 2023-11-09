package com.project.audit.service.configs;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.RoundRobinAssignor;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class AuditConsumerConfig {

        @Value("${spring.kafka.bootstrap-servers}")
        private String bootstrapServerConfig;

        @Value("${spring.kafka.consumer.audit.groupId}")
        private String auctionGroupId;

        @Bean
        public ConsumerFactory<String, String> auctionAuditConsumerFactory() {
            Map<String, Object> consumerConfig = new HashMap<>();
            consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG,auctionGroupId);
            consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
            consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerConfig);
            consumerConfig.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, RoundRobinAssignor.class.getName());

            return new DefaultKafkaConsumerFactory<>(consumerConfig);
        }

        @Bean("auctionAuditKafkaListenerContainerFactory")
        public ConcurrentKafkaListenerContainerFactory<String, String> auctionAuditKafkaListenerContainerFactory() {
            ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(auctionAuditConsumerFactory());
            return factory;
        }

}
