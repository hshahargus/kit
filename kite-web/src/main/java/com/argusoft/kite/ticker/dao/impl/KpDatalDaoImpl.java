/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argusoft.kite.ticker.dao.impl;

import com.argusoft.kite.database.common.impl.BaseAbstractGenericDao;
import com.argusoft.kite.ticker.dao.KpDatalDao;
import com.argusoft.kite.ticker.model.KPDataEntity;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hshah
 */
@Repository
public class KpDatalDaoImpl
        extends BaseAbstractGenericDao<KPDataEntity, Long>
        implements KpDatalDao {

    @Override
    public List<KPDataEntity> getData() {

        Criteria criteria = getCriteria();
        criteria.addOrder(Order.asc("id"));
        return criteria.list();
    }

    @Override
    public List<KPDataEntity> getData(int k) {
        getCurrentSession().createSQLQuery("delete from kp_data").executeUpdate();
        getCurrentSession().createSQLQuery("INSERT INTO public.kp_data(\n"
                + "            id, last_traded_price, last_traded_time)\n"
                + "select id, last_traded_price,tick_timestamp from tick_detail \n"
                + "where tick_timestamp  between '2018-02-12 0:0:0.0' and '2018-02-13 0:0:0.0' \n"
                + "	and instrument_token in ( select distinct instrument_token from tick_detail offset "+k+" limit 1 ) order by id").executeUpdate();
        Criteria criteria = getCriteria();
        criteria.addOrder(Order.asc("id"));
        return criteria.list();
    }

}
