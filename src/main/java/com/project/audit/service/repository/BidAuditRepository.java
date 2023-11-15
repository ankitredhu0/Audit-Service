package com.project.audit.service.repository;

import com.project.audit.service.model.BidAuditMessage;

public interface BidAuditRepository {

    void saveAudit(BidAuditMessage message);
}
