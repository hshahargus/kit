/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argusoft.kite.ticker.dao;

import com.argusoft.kite.database.common.GenericDao;
import com.argusoft.kite.ticker.model.KPDataEntity;
import java.util.List;

/**
 *
 * @author hshah
 */
public interface KpDatalDao extends GenericDao<KPDataEntity, Long> {
   public List<KPDataEntity> getData();

    public List<KPDataEntity> getData(int k);
}
