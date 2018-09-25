package com.argusoft.kite.algo.service;

import com.argusoft.kite.algo.dto.DoubleUpAlgoDto;
import com.zerodhatech.models.Order;
import com.zerodhatech.models.Tick;

/**
 *
 * @author hshah
 */
public interface DoubleUpAlgoService {

    public void subcribeToken(DoubleUpAlgoDto doubleUpAlgoDto);

    public void updateTokenPrice(Tick tick);

    public void onOrderExecute(Order order);
}
