package com.argusoft.kite.ticker.controller;

import com.argusoft.kite.login.service.LoginService;
import com.argusoft.kite.ticker.dto.TokenDetailDto;
import com.argusoft.kite.ticker.service.TickerService;
import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hshah
 */
@RestController
@RequestMapping("/api/ticker")
public class TickerController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private TickerService tickerService;
    @Autowired
    private KiteConnect kiteSdk;

    @RequestMapping(value = "/subcribe", method = RequestMethod.PUT)
    public void subscribe(@RequestBody List<TokenDetailDto> tokenDetailDto) throws KiteException {
        tickerService.subscribe(tokenDetailDto);
    }

    @PostConstruct
    public void fillKiteSdkFromDb() throws KiteException, IOException {
        kiteSdk.setUserId("YI9119");
        loginService.fillKiteSdkFromDB();
//        instrumentService.insertAllInstrumentDetail();
    }


}
