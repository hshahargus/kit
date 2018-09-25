/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argusoft.kite.order.dao.impl;

import com.argusoft.kite.database.common.impl.BaseAbstractGenericDao;
import com.argusoft.kite.order.dao.OrderDetailDao;
import com.argusoft.kite.order.model.OrderDetailEntity;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hshah
 */
@Repository
public class OrderDaoImpl
        extends BaseAbstractGenericDao<OrderDetailEntity, String>
        implements OrderDetailDao {

}
