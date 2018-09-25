/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argusoft.kite.instrument.dao.impl;

import com.argusoft.kite.database.common.impl.BaseAbstractGenericDao;
import com.argusoft.kite.instrument.dao.DaysOhlcDao;
import com.argusoft.kite.instrument.model.DaysOHLC;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hshah
 */
@Repository
public class DaysOhlcDaoImpl
        extends BaseAbstractGenericDao<DaysOHLC, Long>
        implements DaysOhlcDao {

    @Override
    public List<DaysOHLC> retrieveByDate(Date date) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-YYYY");

        date = formatter.parse(formatter.format(date));

        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq("date", date));
        return criteria.list();
    }

}
