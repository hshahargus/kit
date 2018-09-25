/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argusoft.kite.instrument.model;

import com.zerodhatech.models.Quote;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Harshit
 */
@Entity
@Table(name = "days_ohlc")
public class DaysOHLC {

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    public double volumeTradedToday;
    public double lastTradedQuantity;
    public Date lastTradedTime;
    public double change;
    public double oi;
    public double sellQuantity;
    public double lastPrice;
    public double buyQuantity;
    public long instrumentToken;
    public Date timestamp;
    public double averagePrice;
    public double oiDayHigh;
    public double oiDayLow;
    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    public double high;

    @Column
    public double low;

    @Column
    public double close;

    @Column
    public double open;

    @Column
    public double getVolumeTradedToday() {
        return volumeTradedToday;
    }

    @Column
    public double getLastTradedQuantity() {
        return lastTradedQuantity;
    }

    @Column
    public Date getLastTradedTime() {
        return lastTradedTime;
    }

    @Column
    public double getChange() {
        return change;
    }

    @Column
    public double getOi() {
        return oi;
    }

    @Column
    public double getSellQuantity() {
        return sellQuantity;
    }

    @Column
    public double getLastPrice() {
        return lastPrice;
    }

    @Column
    public double getBuyQuantity() {
        return buyQuantity;
    }

    @Column
    public long getInstrumentToken() {
        return instrumentToken;
    }

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    public Date getTimestamp() {
        return timestamp;
    }

    @Column
    public double getAveragePrice() {
        return averagePrice;
    }

    @Column
    public double getOiDayHigh() {
        return oiDayHigh;
    }

    @Column
    public double getOiDayLow() {
        return oiDayLow;
    }

    public void setVolumeTradedToday(double volumeTradedToday) {
        this.volumeTradedToday = volumeTradedToday;
    }

    public void setLastTradedQuantity(double lastTradedQuantity) {
        this.lastTradedQuantity = lastTradedQuantity;
    }

    public void setLastTradedTime(Date lastTradedTime) {
        this.lastTradedTime = lastTradedTime;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public void setOi(double oi) {
        this.oi = oi;
    }

    public void setSellQuantity(double sellQuantity) {
        this.sellQuantity = sellQuantity;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public void setBuyQuantity(double buyQuantity) {
        this.buyQuantity = buyQuantity;
    }

    public void setInstrumentToken(long instrumentToken) {
        this.instrumentToken = instrumentToken;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public void setOiDayHigh(double oiDayHigh) {
        this.oiDayHigh = oiDayHigh;
    }

    public void setOiDayLow(double oiDayLow) {
        this.oiDayLow = oiDayLow;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public DaysOHLC() {
    }

    public DaysOHLC(Quote quote) {
        this.averagePrice = quote.averagePrice;
        this.buyQuantity = quote.buyQuantity;
        this.change = quote.change;
        this.close = quote.ohlc.close;
        this.date = new Date();
        this.high = quote.ohlc.high;
        this.instrumentToken = quote.instrumentToken;
        this.lastPrice = quote.lastPrice;
        this.lastTradedQuantity = quote.lastTradedQuantity;
        this.lastTradedTime = quote.lastTradedTime;
        this.low = quote.ohlc.low;
        this.oi = quote.oi;
        this.oiDayHigh = quote.oiDayHigh;
        this.oiDayLow = quote.oiDayLow;
        this.open = quote.ohlc.open;
        this.sellQuantity = quote.sellQuantity;
        this.timestamp = quote.timestamp;
        this.volumeTradedToday = quote.volumeTradedToday;
    }

}
