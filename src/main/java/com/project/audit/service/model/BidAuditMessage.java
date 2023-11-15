package com.project.audit.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BidAuditMessage {

    private Long id;
    private Long itemId;
    private Long biddingPrice;
    private Long userId;
}
