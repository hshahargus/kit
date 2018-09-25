package com.argusoft.kite.ticker.service.impl;

import com.argusoft.kite.algo.service.DoubleUpAlgoService;
import com.argusoft.kite.instrument.service.InstrumentService;
import com.argusoft.kite.order.service.OrderService;
import static com.argusoft.kite.ticker.Constant.TickerConstantUtil.tokenToPrice;
import static com.argusoft.kite.ticker.Constant.TickerConstantUtil.tokens;
import com.argusoft.kite.ticker.dao.SubcribedTokenDetailDao;
import com.argusoft.kite.ticker.dto.Tiker;
import com.argusoft.kite.ticker.dto.TokenDetailDto;
import com.argusoft.kite.ticker.model.SubcribedTokensDetailEntity;
import com.argusoft.kite.ticker.service.TickerService;
import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Order;
import com.zerodhatech.models.Tick;
import com.zerodhatech.ticker.KiteTicker;
import com.zerodhatech.ticker.OnConnect;
import com.zerodhatech.ticker.OnOrderUpdate;
import com.zerodhatech.ticker.OnTicks;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author kvithlani
 */
@Service
@Transactional
public class TickerServiceImpl implements TickerService {

    @Autowired
    private KiteConnect kiteSdk;

    @Autowired
    private Tiker kiteTicker;

    @Autowired
    private SubcribedTokenDetailDao subcribedTokenDetailDao;

    @Autowired
    private OrderService orderService;
    @Autowired
    private InstrumentService instrumentService;
//    @Autowired
//    private SeaWaveAlgoService seaWaveAlgoService;
    @Autowired
    private DoubleUpAlgoService doubleUpAlgoService;

    @Override
    public void configure() {
        List<SubcribedTokensDetailEntity> tokensDetailEntitys = subcribedTokenDetailDao.retrieveAll();
        if (!CollectionUtils.isEmpty(tokensDetailEntitys)) {
            for (SubcribedTokensDetailEntity tokensDetailEntity : tokensDetailEntitys) {
                tokens.add(tokensDetailEntity.getToken());
            }
        }
        KiteTicker ticker = new KiteTicker(kiteSdk.getAccessToken(), kiteSdk.getApiKey());
        kiteTicker.setKiteTicker(ticker);
        ticker.setOnConnectedListener(new OnConnect() {
            @Override
            public void onConnected() {
                /**
                 * Subscribe ticks for token. By default, all tokens are
                 * subscribed for modeQuote.
                 *
                 */
                ticker.subscribe(tokens);
                ticker.setMode(tokens, KiteTicker.modeFull);

            }
        });

        ticker.setOnTickerArrivalListener(new OnTicks() {
            @Override
            public void onTicks(ArrayList<Tick> ticks) {
                for (Tick tick : ticks) {
//                    System.out.println("tick.getInstrumentToken() :: " + tick.getInstrumentToken());
                    Double price = tokenToPrice.get(tick.getInstrumentToken());
                    double lastTradedPrice = tick.getLastTradedPrice();
                    if (price == null || !price.equals(lastTradedPrice)) {

                        tokenToPrice.put(tick.getInstrumentToken(), lastTradedPrice);
                        doubleUpAlgoService.updateTokenPrice(tick);
                        instrumentService.createTick(tick);
                    }
                }
            }
        });
        ticker.setOnOrderUpdateListener(new OnOrderUpdate() {
            @Override
            public void onOrderUpdate(Order order) {
                System.out.println("order.id :: " + order.orderId + " Status :: " + order.status);
//                orderService.createOrder(order);
                doubleUpAlgoService.onOrderExecute(order);

            }
        });

        ticker.setTryReconnection(true);

        try {
            ticker.setMaximumRetries(1000);
            ticker.setMaximumRetryInterval(5);
        } catch (KiteException ex) {
            Logger.getLogger(TickerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void subscribe(List<TokenDetailDto> ftokens) {
        List<SubcribedTokensDetailEntity> tokensDetailEntitys = new LinkedList<>();
        ArrayList<Long> newToken = new ArrayList<>();
        for (TokenDetailDto token : ftokens) {
            if (!tokens.contains(token.getToken())) {
                SubcribedTokensDetailEntity tokensDetailEntity = new SubcribedTokensDetailEntity();
                tokensDetailEntity.setToken(token.getToken());
                tokensDetailEntitys.add(tokensDetailEntity);
                newToken.add(token.getToken());
            }
        }
        System.out.println("tokens :: " + tokens);
        System.out.println("newToken :: " + newToken);

        if (!CollectionUtils.isEmpty(newToken)) {
            System.out.println("newToken :: " + newToken);
            kiteTicker.getKiteTicker().setMode(newToken, KiteTicker.modeFull);
            kiteTicker.getKiteTicker().subscribe(newToken);
            subcribedTokenDetailDao.createOrUpdateAll(tokensDetailEntitys);
            tokens.addAll(newToken);
        }
    }

    public void unSubscribe(List<TokenDetailDto> ftokens) {
        List<SubcribedTokensDetailEntity> tokensDetailEntitys = new LinkedList<>();
        ArrayList<Long> newToken = new ArrayList<>();
        for (TokenDetailDto token : ftokens) {
            if (tokens.contains(token.getToken())) {
                SubcribedTokensDetailEntity tokensDetailEntity = new SubcribedTokensDetailEntity();
                tokensDetailEntity.setToken(token.getToken());
                tokensDetailEntitys.add(tokensDetailEntity);
                newToken.add(token.getToken());
            }
        }

        if (!CollectionUtils.isEmpty(newToken)) {
            kiteTicker.getKiteTicker().unsubscribe(newToken);
//            subcribedTokenDetailDao.deleteAll(tokensDetailEntitys);
            tokens.removeAll(newToken);
        }

    }

}
