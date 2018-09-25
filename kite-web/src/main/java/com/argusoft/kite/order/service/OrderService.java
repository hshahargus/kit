package com.argusoft.kite.order.service;

import com.argusoft.kite.instrument.model.InstrumentDetailEntity;
import com.argusoft.kite.order.constant.OrderConstantUtil.OrderType;
import com.argusoft.kite.order.constant.OrderConstantUtil.Product;
import com.argusoft.kite.order.constant.OrderConstantUtil.TransactionType;
import com.zerodhatech.models.Order;

/**
 *
 * @author hshah
 */
public interface OrderService {

    public Order placeRegularOrder(TransactionType transactionType,
            OrderType orderType,
            int quantity,
            Product product,
            double price,
            double triggerPrice, InstrumentDetailEntity instrumentDetailEntity);

    public Order modifyRegularOrder(String orderId, TransactionType transactionType,
            OrderType orderType,
            int quantity,
            Product product,
            double price,
            double triggerPrice, InstrumentDetailEntity instrumentDetailEntity);

    public Order cancelRegularOrder(String orderId);

    public void createOrder(Order order);

}
