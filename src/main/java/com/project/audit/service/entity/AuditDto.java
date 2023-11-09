package com.project.audit.service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Builder
@Table(name = "auction_pipeline")
public class AuditDto {

    @Id
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_category")
    private String itemCategory;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "description")
    private String description;

    @Column(name = "media_url")
    private String mediaUrl;

    @Column(name = "auction_slot")
    private Timestamp auctionSlot;

    @Column(name = "base_price")
    private Long basePrice;

    @Column(name = "condition")
    private String condition;

    @Column(name = "buying_year")
    private String buyingYear;

    @Column(name = "auction_state")
    private String auctionState;

    @Column(name = "auction_id")
    private Long auctionId;

    @Column(name = "minimum_price")
    private Long minimumPrice;

    @Column(name = "winning_amount")
    private Long winningAmount;

    @Column(name = "winner")
    private Long winner;

}
