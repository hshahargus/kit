/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argusoft.kite.instrument.dao.impl;

import com.argusoft.kite.database.common.impl.BaseAbstractGenericDao;
import com.argusoft.kite.instrument.dao.InstrumentDetailDao;
import com.argusoft.kite.instrument.model.InstrumentDetailEntity;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hshah
 */
@Repository
public class InstrumentDaoImpl
        extends BaseAbstractGenericDao<InstrumentDetailEntity, String>
        implements InstrumentDetailDao {

    @Override
    public void deleteAll() {
        getCurrentSession().createQuery("delete from InstrumentDetailEntity").executeUpdate();
    }

    @Override
    public List<InstrumentDetailEntity> retrieveByInstrumentTypeAndSagment(String instrumentType, String sagment) {
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq("instrument_type", instrumentType));
        criteria.add(Restrictions.eq("segment", sagment));
        return criteria.list();
    }

    @Override
    public InstrumentDetailEntity retrieveByTokenId(long tokenId) {
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq("instrument_token", tokenId));
        return (InstrumentDetailEntity) criteria.uniqueResult();
    }

}
