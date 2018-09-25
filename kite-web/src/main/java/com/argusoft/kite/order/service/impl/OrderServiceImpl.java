package com.argusoft.kite.order.service.impl;

import com.argusoft.kite.instrument.model.InstrumentDetailEntity;
import com.argusoft.kite.order.constant.OrderConstantUtil;
import com.argusoft.kite.order.dao.OrderDetailDao;
import com.argusoft.kite.order.model.OrderDetailEntity;
import com.argusoft.kite.order.service.OrderService;
import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Order;
import com.zerodhatech.models.OrderParams;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kvithlani
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private KiteConnect kiteSdk;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Override
    public Order placeRegularOrder(OrderConstantUtil.TransactionType transactionType,
            OrderConstantUtil.OrderType orderType,
            int quantity, OrderConstantUtil.Product product,
            double price, double triggerPrice, InstrumentDetailEntity instrumentDetailEntity) {

        try {
            OrderParams orderParams = new OrderParams();
            orderParams.exchange = instrumentDetailEntity.getExchange();
            orderParams.tradingsymbol = instrumentDetailEntity.getTradingsymbol();
            orderParams.transactionType = transactionType.getValue();
            orderParams.orderType = orderType.getValue();
            orderParams.quantity = quantity;
            orderParams.price = price;
            orderParams.product = product.getValue();
            orderParams.validity = OrderConstantUtil.validity.DAY.getValue();
            orderParams.disclosedQuantity = 0;
            orderParams.triggerPrice = triggerPrice;

            Order order = kiteSdk.placeOrder(orderParams, OrderConstantUtil.variety.REGULAR.getValue());
            return order;
        } catch (KiteException ex) {
            System.out.println("Ex Code: " + ex.code);
            System.out.println("Ex message: " + ex.message);
            Logger.getLogger(OrderServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.code == 429 || ex.code == 503 || ex.code == 504) {
                return placeRegularOrder(transactionType, orderType, quantity, product, price, triggerPrice, instrumentDetailEntity);
            }
        } catch (JSONException ex) {
            Logger.getLogger(OrderServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OrderServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Order modifyRegularOrder(String orderId,
            OrderConstantUtil.TransactionType transactionType, OrderConstantUtil.OrderType orderType,
            int quantity, OrderConstantUtil.Product product, double price, double triggerPrice, InstrumentDetailEntity instrumentDetailEntity) {
        try {
            OrderParams orderParams = new OrderParams();
            orderParams.exchange = instrumentDetailEntity.getExchange();
            orderParams.tradingsymbol = instrumentDetailEntity.getTradingsymbol();
            orderParams.transactionType = transactionType.getValue();
            orderParams.orderType = orderType.getValue();
            orderParams.quantity = quantity;
            orderParams.price = price;
            orderParams.product = product.getValue();
            orderParams.validity = OrderConstantUtil.validity.DAY.getValue();
            orderParams.disclosedQuantity = 0;
            orderParams.triggerPrice = triggerPrice;
            Order order = kiteSdk.modifyOrder(orderId, orderParams, OrderConstantUtil.variety.REGULAR.getValue());
            OrderDetailEntity orderDetailEntity = new OrderDetailEntity(order);
            orderDetailDao.create(orderDetailEntity);
            return order;
        } catch (JSONException ex) {
            Logger.getLogger(OrderServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KiteException ex) {
            Logger.getLogger(OrderServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.code == 429 || ex.code == 503 || ex.code == 504) {
                return modifyRegularOrder(orderId, transactionType, orderType, quantity, product, price, triggerPrice, instrumentDetailEntity);
            }
        } catch (IOException ex) {
            Logger.getLogger(OrderServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Order cancelRegularOrder(String orderId) {
        try {
            Order order = kiteSdk.cancelOrder(orderId, OrderConstantUtil.variety.REGULAR.getValue());
            OrderDetailEntity orderDetailEntity = new OrderDetailEntity(order);
            orderDetailDao.create(orderDetailEntity);
            return order;
        } catch (KiteException ex) {
            Logger.getLogger(OrderServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.code == 429 || ex.code == 503 || ex.code == 504) {
                return cancelRegularOrder(orderId);
            }
        } catch (JSONException ex) {
            Logger.getLogger(OrderServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OrderServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void createOrder(Order order) {
        OrderDetailEntity orderDetailEntity = new OrderDetailEntity(order);
        orderDetailDao.create(orderDetailEntity);
    }

}
