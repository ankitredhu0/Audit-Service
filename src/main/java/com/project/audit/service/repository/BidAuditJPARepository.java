package com.project.audit.service.repository;

import com.project.audit.service.entity.BidDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BidAuditJPARepository extends JpaRepository<BidDto, Long>, BidAuditRepository{

}
