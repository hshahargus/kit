/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argusoft.kite.instrument.dao;

import com.argusoft.kite.database.common.GenericDao;
import com.argusoft.kite.instrument.model.InstrumentDetailEntity;
import java.util.List;

/**
 *
 * @author hshah
 */
public interface InstrumentDetailDao extends GenericDao<InstrumentDetailEntity, String> {

    public void deleteAll();

    public List<InstrumentDetailEntity> retrieveByInstrumentTypeAndSagment(String instrumentType, String sagment);

    public InstrumentDetailEntity retrieveByTokenId(long tokenId);

}
