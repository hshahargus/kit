/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argusoft.kite.ticker.model;

import com.zerodhatech.models.Tick;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.Data;

/**
 *
 * @author Harshit
 */
@Entity
@Table(name = "tick_detail")
@Data
public class TickDetailEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column
    private long instrumentToken;
    @Column
    private double lastTradedPrice;
    @Column
    private double highPrice;
    @Column
    private double lowPrice;
    @Column
    private double openPrice;
    @Column
    private double closePrice;
    @Column
    private double change;
    @Column
    private double lastTradedQuantity;
    @Column
    private double averageTradePrice;
    @Column
    private double volumeTradedToday;
    @Column
    private double totalBuyQuantity;
    @Column
    private double totalSellQuantity;
    @Column
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date lastTradedTime;
    @Column
    private double oi;
    @Column
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date tickTimestamp;

    public TickDetailEntity(Tick tick) {
        this.instrumentToken = tick.getInstrumentToken();
        this.lastTradedPrice = tick.getLastTradedPrice();
        this.highPrice = tick.getHighPrice();
        this.lowPrice = tick.getLowPrice();
        this.openPrice = tick.getOpenPrice();
        this.closePrice = tick.getClosePrice();
        this.change = tick.getChange();
        this.lastTradedQuantity = tick.getLastTradedQuantity();
        this.averageTradePrice = tick.getAverageTradePrice();
        this.volumeTradedToday = tick.getVolumeTradedToday();
        this.totalBuyQuantity = tick.getTotalBuyQuantity();
        this.totalSellQuantity = tick.getTotalSellQuantity();
        this.lastTradedTime = tick.getLastTradedTime();
        this.oi = tick.getOi();
        this.tickTimestamp = tick.getTickTimestamp();
    }

}
