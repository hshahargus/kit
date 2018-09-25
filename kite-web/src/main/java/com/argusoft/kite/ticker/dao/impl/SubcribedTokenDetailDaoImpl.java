/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argusoft.kite.ticker.dao.impl;

import com.argusoft.kite.database.common.impl.BaseAbstractGenericDao;
import com.argusoft.kite.ticker.dao.SubcribedTokenDetailDao;
import com.argusoft.kite.ticker.model.SubcribedTokensDetailEntity;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hshah
 */
@Repository
public class SubcribedTokenDetailDaoImpl
        extends BaseAbstractGenericDao<SubcribedTokensDetailEntity, Long>
        implements SubcribedTokenDetailDao {


}
