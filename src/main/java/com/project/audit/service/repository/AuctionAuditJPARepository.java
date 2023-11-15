package com.project.audit.service.repository;

import com.project.audit.service.entity.AuditDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AuctionAuditJPARepository extends JpaRepository<AuditDto, Long>, AuctionAuditRepository {
}
