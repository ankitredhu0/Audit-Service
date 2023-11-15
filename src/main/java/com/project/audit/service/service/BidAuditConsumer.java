package com.project.audit.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.audit.service.model.BidAuditMessage;
import com.project.audit.service.repository.BidAuditRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class BidAuditConsumer {

    private static final Logger logger = LoggerFactory.getLogger(BidAuditConsumer.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private BidAuditRepository bidDbService;

    @KafkaListener(topics = "${spring.kafka.bid.audit.topic}", groupId = "${spring.kafka.consumer.bid.groupId}", containerFactory = "bidAuditKafkaListenerContainerFactory")
    public void consumeAuctionAuditMessage(@Payload String message) {
        try {

            BidAuditMessage bidAuditMessage = objectMapper.readValue(message, BidAuditMessage.class);

            logger.info("Message Received into kafka, saving it to DB");

            bidDbService.saveAudit(bidAuditMessage);

        } catch (JsonProcessingException ex) {
            logger.error("Error in consuming message: {}", ex.getLocalizedMessage());
        }
    }
}
