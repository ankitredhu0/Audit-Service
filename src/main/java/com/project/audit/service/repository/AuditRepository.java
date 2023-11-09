package com.project.audit.service.repository;


import com.project.audit.service.model.AuditMessage;

public interface AuditRepository {
    void saveAudit(AuditMessage message);

}
