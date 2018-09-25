package com.argusoft.kite.ticker.service;

import com.argusoft.kite.ticker.dto.TokenDetailDto;
import java.util.List;

/**
 *
 * @author hshah
 */
public interface TickerService {

    public void configure();

    public void subscribe(List<TokenDetailDto> ftokens);

    public void unSubscribe(List<TokenDetailDto> ftokens);

}
