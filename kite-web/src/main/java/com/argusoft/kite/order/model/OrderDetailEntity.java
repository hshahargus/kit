/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argusoft.kite.order.model;

import com.zerodhatech.models.Order;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Harshit
 */
@Entity
@Table(name = "order_detail")
public class OrderDetailEntity extends Order {

    @Id
    @Basic(optional = false)
    @Column
    public String getOrderId() {
        return orderId;
    }

    @Column
    public String getExchangeOrderId() {
        return exchangeOrderId;
    }

    @Column
    public String getDisclosedQuantity() {
        return disclosedQuantity;
    }

    @Column
    public String getValidity() {
        return validity;
    }

    @Column
    public String getTradingSymbol() {
        return tradingSymbol;
    }

    @Column
    public String getOrderVariety() {
        return orderVariety;
    }

    @Column
    public String getUserId() {
        return userId;
    }

    @Column
    public String getOrderType() {
        return orderType;
    }

    @Column
    public String getTriggerPrice() {
        return triggerPrice;
    }

    @Column
    public String getStatusMessage() {
        return statusMessage;
    }

    @Column
    public String getPrice() {
        return price;
    }

    @Column
    public String getStatus() {
        return status;
    }

    @Column
    public String getProduct() {
        return product;
    }

    @Column
    public String getAccountId() {
        return accountId;
    }

    @Column
    public String getExchange() {
        return exchange;
    }

    @Column
    public String getSymbol() {
        return symbol;
    }

    @Column
    public String getPendingQuantity() {
        return pendingQuantity;
    }

    @Column
    public String getAveragePrice() {
        return averagePrice;
    }

    @Column
    public String getTransactionType() {
        return transactionType;
    }

    @Column
    public String getFilledQuantity() {
        return filledQuantity;
    }

    @Column
    public String getQuantity() {
        return quantity;
    }

    @Column
    public String getParentOrderId() {
        return parentOrderId;
    }

    @Column
    public String getTag() {
        return tag;
    }

    @Column
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getOrderTimestamp() {
        return orderTimestamp;
    }

    @Column
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getExchangeTimestamp() {
        return exchangeTimestamp;
    }

    public void setExchangeOrderId(String exchangeOrderId) {
        this.exchangeOrderId = exchangeOrderId;
    }

    public void setDisclosedQuantity(String disclosedQuantity) {
        this.disclosedQuantity = disclosedQuantity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public void setTradingSymbol(String tradingSymbol) {
        this.tradingSymbol = tradingSymbol;
    }

    public void setOrderVariety(String orderVariety) {
        this.orderVariety = orderVariety;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public void setTriggerPrice(String triggerPrice) {
        this.triggerPrice = triggerPrice;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setPendingQuantity(String pendingQuantity) {
        this.pendingQuantity = pendingQuantity;
    }

    public void setAveragePrice(String averagePrice) {
        this.averagePrice = averagePrice;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setFilledQuantity(String filledQuantity) {
        this.filledQuantity = filledQuantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setParentOrderId(String parentOrderId) {
        this.parentOrderId = parentOrderId;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setOrderTimestamp(Date orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
    }

    public void setExchangeTimestamp(Date exchangeTimestamp) {
        this.exchangeTimestamp = exchangeTimestamp;
    }

    public OrderDetailEntity() {
    }

    public OrderDetailEntity(Order order) {
        this.accountId = order.accountId;
        this.averagePrice = order.averagePrice;
        this.disclosedQuantity = order.disclosedQuantity;
        this.exchange = order.exchange;
        this.exchangeOrderId = order.exchangeOrderId;
        this.exchangeTimestamp = order.exchangeTimestamp;
        this.filledQuantity = order.filledQuantity;
        this.orderId = order.orderId;
        this.orderTimestamp = order.orderTimestamp;
        this.orderType = order.orderType;
        this.orderVariety = order.orderVariety;
        this.parentOrderId = order.parentOrderId;
        this.pendingQuantity = order.pendingQuantity;
        this.price = order.price;
        this.product = order.product;
        this.quantity = order.quantity;
        this.status = order.status;
        this.statusMessage = order.statusMessage;
        this.symbol = order.symbol;
        this.tag = order.tag;
        this.transactionType = order.transactionType;
        this.tradingSymbol = order.tradingSymbol;
        this.triggerPrice = order.triggerPrice;
        this.userId = order.userId;
        this.validity = order.validity;

    }

}
