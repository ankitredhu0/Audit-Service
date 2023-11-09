package com.project.audit.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditMessage {


    private Long itemId;
    private String itemCategory;
    private String itemName;
    private String description;
    private String mediaUrl;
    private Timestamp auctionSlot;
    private Long basePrice;
    private String condition;
    private String buyingYear;
    private String auctionState;
    private Long auctionId;
    private Long minimumPrice;
    private Long winningAmount;
    private Long winner;

}
