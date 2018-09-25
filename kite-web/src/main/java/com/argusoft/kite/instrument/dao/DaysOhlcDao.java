/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argusoft.kite.instrument.dao;

import com.argusoft.kite.database.common.GenericDao;
import com.argusoft.kite.instrument.model.DaysOHLC;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hshah
 */
public interface DaysOhlcDao extends GenericDao<DaysOHLC, Long> {

    public List<DaysOHLC> retrieveByDate(Date date) throws ParseException;

}
