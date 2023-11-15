package com.project.audit.service.repository;

import com.project.audit.service.entity.BidDto;
import com.project.audit.service.model.BidAuditMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Objects;
import java.util.function.Consumer;

@Repository
public class BidAuditRepositoryImpl implements BidAuditRepository{

    public static final int MAX_RETRIES = 2;
    private final Logger logger = LoggerFactory.getLogger(AuctionAuditRepositoryImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    private static <V> void setIfNotNull(V value, Consumer<V> setter) {
        if (Objects.nonNull(value)) {
            setter.accept(value);
        }
    }

    @Override
    @Transactional
    public void saveAudit(BidAuditMessage message) {

        int retries = MAX_RETRIES;
        do {
            try {
                BidDto existingRecord = entityManager.find(BidDto.class, message.getId());
                entityManager.merge(setFields(existingRecord, message));
                entityManager.flush();
                entityManager.close();
                logger.info("Stored message: {} in DB.", message.getItemId());
                retries = 0;
            }
            catch (Exception ex) {
                retries--;
                if (retries > 0)
                    logger.info("Failed to store message. Attempting retry. retries left: {}", retries);
                else
                    logger.error("Exception in savig in DB: {}", ex);
            }
        } while (retries > 0);
    }
    @Transactional
    public BidDto setFields(BidDto bidDto, BidAuditMessage incomingBidAuditMessage) {

        if (bidDto == null) {
            bidDto = BidDto.builder().id(incomingBidAuditMessage.getId()).build();
        }

        setIfNotNull(incomingBidAuditMessage.getBiddingPrice(), bidDto::setBiddingPrice);
        setIfNotNull(incomingBidAuditMessage.getUserId(), bidDto::setUserId);
        setIfNotNull(incomingBidAuditMessage.getItemId(), bidDto::setItemId);

        return bidDto;
    }
}
