package com.project.audit.service.repository;

import com.project.audit.service.entity.AuditDto;
import com.project.audit.service.model.AuditMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Objects;
import java.util.function.Consumer;

@Repository
public class AuditRepositoryImpl implements AuditRepository {
    public static final int MAX_RETRIES = 2;
    private final Logger logger = LoggerFactory.getLogger(AuditRepositoryImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    private static <V> void setIfNotNull(V value, Consumer<V> setter) {
        if (Objects.nonNull(value)) {
            setter.accept(value);
        }
    }

    @Override
    @Transactional
    public void saveAudit(AuditMessage message) {
        int retries = MAX_RETRIES;
        do {
            try {
                AuditDto existingRecord = entityManager.find(AuditDto.class, message.getItemId());
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
    public AuditDto setFields(AuditDto auditDto, AuditMessage incomingAuditMessage) {

        if (auditDto == null) {
            auditDto = AuditDto.builder().itemId(incomingAuditMessage.getItemId()).build();
        }

        setIfNotNull(incomingAuditMessage.getAuctionId(), auditDto::setAuctionId);
        setIfNotNull(incomingAuditMessage.getItemName(), auditDto::setItemName);
        setIfNotNull(incomingAuditMessage.getItemCategory(), auditDto::setItemCategory);
        setIfNotNull(incomingAuditMessage.getCondition(), auditDto::setCondition);
        setIfNotNull(incomingAuditMessage.getAuctionState(), auditDto::setAuctionState);
        setIfNotNull(incomingAuditMessage.getBasePrice(), auditDto::setBasePrice);
        setIfNotNull(incomingAuditMessage.getAuctionSlot(), auditDto::setAuctionSlot);
        setIfNotNull(incomingAuditMessage.getBuyingYear(), auditDto::setBuyingYear);
        setIfNotNull(incomingAuditMessage.getMediaUrl(), auditDto::setMediaUrl);
        setIfNotNull(incomingAuditMessage.getDescription(), auditDto::setDescription);
        setIfNotNull(incomingAuditMessage.getWinningAmount(), auditDto::setWinningAmount);
        setIfNotNull(incomingAuditMessage.getWinner(), auditDto::setWinner);
        setIfNotNull(incomingAuditMessage.getMinimumPrice(), auditDto::setMinimumPrice);

        return auditDto;
    }
}
