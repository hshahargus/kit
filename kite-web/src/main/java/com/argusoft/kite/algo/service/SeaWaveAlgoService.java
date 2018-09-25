package com.argusoft.kite.algo.service;

import com.argusoft.kite.algo.dto.SeaWaveAlgoDto;
import com.zerodhatech.models.Order;
import com.zerodhatech.models.Tick;

/**
 *
 * @author hshah
 */
public interface SeaWaveAlgoService {

    public void subcribeToken(SeaWaveAlgoDto seaWaveAlgoDto);

    public void updateTokenPrice(Tick tick);

    public void onOrderExecute(Order order);
}
