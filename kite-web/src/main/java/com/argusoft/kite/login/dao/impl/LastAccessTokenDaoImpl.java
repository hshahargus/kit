/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argusoft.kite.login.dao.impl;

import com.argusoft.kite.database.common.impl.BaseAbstractGenericDao;
import com.argusoft.kite.login.dao.LastAccessTokenDao;
import com.argusoft.kite.login.model.LastAccessTokenDetailEntity;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hshah
 */
@Repository
public class LastAccessTokenDaoImpl
        extends BaseAbstractGenericDao<LastAccessTokenDetailEntity, Long>
        implements LastAccessTokenDao {


}
