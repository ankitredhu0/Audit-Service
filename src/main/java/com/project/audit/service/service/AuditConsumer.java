package com.project.audit.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.audit.service.model.AuditMessage;
import com.project.audit.service.repository.AuditRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class AuditConsumer {

    private static final Logger logger = LoggerFactory.getLogger(AuditConsumer.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private AuditRepository databaseService;

    @KafkaListener(topics = "${spring.kafka.audit.topic}", groupId = "${spring.kafka.consumer.audit.groupId}", containerFactory = "auctionAuditKafkaListenerContainerFactory")
    public void consumeAuctionAuditMessage(@Payload String message) {
        try {

            AuditMessage auditMessage = objectMapper.readValue(message, AuditMessage.class);

            logger.info("Message Received into kafka, saving it to DB");

            databaseService.saveAudit(auditMessage);

        } catch (JsonProcessingException ex) {
            logger.error("Error in consuming message: {}", ex.getLocalizedMessage());
        }
    }
}
