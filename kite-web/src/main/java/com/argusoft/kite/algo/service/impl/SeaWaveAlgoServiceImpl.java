package com.argusoft.kite.algo.service.impl;

import com.argusoft.kite.algo.dto.SeaWaveAlgoDto;
import com.argusoft.kite.algo.service.SeaWaveAlgoService;
import com.argusoft.kite.instrument.model.InstrumentDetailEntity;
import com.argusoft.kite.instrument.service.InstrumentService;
import com.argusoft.kite.order.constant.OrderConstantUtil;
import com.argusoft.kite.order.constant.OrderConstantUtil.TransactionType;
import com.argusoft.kite.order.service.OrderService;
import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Order;
import com.zerodhatech.models.Quote;
import com.zerodhatech.models.Tick;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.transaction.Transactional;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kvithlani
 */
@Service
@Transactional
public class SeaWaveAlgoServiceImpl implements SeaWaveAlgoService {

    @Autowired
    private KiteConnect kiteSdk;

    @Autowired
    private InstrumentService instrumentService;

    @Autowired
    private OrderService orderService;

    private Map<Long, SeaWaveAlgoDto> tokenToSeaWaveAlgoDtoMap = new HashMap<>();
    private Map<String, Long> tradingSymbolToTokenId = new HashMap<>();

    @Override
    public void subcribeToken(SeaWaveAlgoDto seaWaveAlgoDto) {
        try {
            TransactionType transactionType = findBuySellModeForInstrument(seaWaveAlgoDto.getToken());
            InstrumentDetailEntity instrumentDetailEntity = instrumentService.getInstrumentDetailEntityByTokenId(seaWaveAlgoDto.getToken());
            Order order = orderService.placeRegularOrder(transactionType, OrderConstantUtil.OrderType.MARKET,
                    seaWaveAlgoDto.getQuantity(), OrderConstantUtil.Product.MIS, 0, 0, instrumentDetailEntity);
            seaWaveAlgoDto.setLastOrderId(order.orderId);
            seaWaveAlgoDto.setFirstOrderExecuted(false);
            tradingSymbolToTokenId.put(instrumentDetailEntity.tradingsymbol, instrumentDetailEntity.getInstrument_token());
            tokenToSeaWaveAlgoDtoMap.put(instrumentDetailEntity.getInstrument_token(), seaWaveAlgoDto);

        } catch (JSONException ex) {
            Logger.getLogger(SeaWaveAlgoServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SeaWaveAlgoServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KiteException ex) {
            Logger.getLogger(SeaWaveAlgoServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateTokenPrice(Tick tick) {
        SeaWaveAlgoDto seaWaveAlgoDto = tokenToSeaWaveAlgoDtoMap.get(tick.getInstrumentToken());
        if (seaWaveAlgoDto != null) {
            if (seaWaveAlgoDto.getMode().equals(TransactionType.BUY.getValue())) {
                if (!seaWaveAlgoDto.isFirstDiffCover()) {
                    System.out.println("seaWaveAlgoDto.getFirstDiff() ::: " + seaWaveAlgoDto.getFirstDiff());
                    System.out.println("seaWaveAlgoDto.getOrderExecutePrice() ::: " + seaWaveAlgoDto.getOrderExecutePrice());
                    if ((seaWaveAlgoDto.getFirstDiff() + seaWaveAlgoDto.getOrderExecutePrice()) <= tick.getLastTradedPrice()) {
                        seaWaveAlgoDto.setOrderExecutePrice(seaWaveAlgoDto.getFirstDiff() + seaWaveAlgoDto.getOrderExecutePrice());
                        seaWaveAlgoDto.setOrderPrice(seaWaveAlgoDto.getFirstDiff() + seaWaveAlgoDto.getOrderPrice());
                        InstrumentDetailEntity instrumentDetailEntity = instrumentService.getInstrumentDetailEntityByTokenId(seaWaveAlgoDto.getToken());
                        Order order = orderService.modifyRegularOrder(seaWaveAlgoDto.getLastOrderId(), OrderConstantUtil.TransactionType.SELL, OrderConstantUtil.OrderType.SLM,
                                seaWaveAlgoDto.getQuantity() * 2, OrderConstantUtil.Product.MIS, 0, seaWaveAlgoDto.getOrderPrice(), instrumentDetailEntity);
                        seaWaveAlgoDto.setFirstDiffCover(true);
                    }
                } else {
                    if ((seaWaveAlgoDto.getSecondDiff() + seaWaveAlgoDto.getOrderExecutePrice()) <= tick.getLastTradedPrice()) {
                        seaWaveAlgoDto.setOrderExecutePrice(seaWaveAlgoDto.getSecondDiff() + seaWaveAlgoDto.getOrderExecutePrice());
                        seaWaveAlgoDto.setOrderPrice(seaWaveAlgoDto.getSecondDiff() + seaWaveAlgoDto.getOrderPrice());
                        InstrumentDetailEntity instrumentDetailEntity = instrumentService.getInstrumentDetailEntityByTokenId(seaWaveAlgoDto.getToken());
                        Order order = orderService.modifyRegularOrder(seaWaveAlgoDto.getLastOrderId(), OrderConstantUtil.TransactionType.SELL, OrderConstantUtil.OrderType.SLM,
                                seaWaveAlgoDto.getQuantity() * 2, OrderConstantUtil.Product.MIS, 0, seaWaveAlgoDto.getOrderPrice(), instrumentDetailEntity);
                        seaWaveAlgoDto.setFirstDiffCover(true);
                    }
                }
            } else {
                if (!seaWaveAlgoDto.isFirstDiffCover()) {
                    if ((seaWaveAlgoDto.getOrderExecutePrice() - seaWaveAlgoDto.getFirstDiff()) >= tick.getLastTradedPrice()) {
                        seaWaveAlgoDto.setOrderExecutePrice(seaWaveAlgoDto.getOrderExecutePrice() - seaWaveAlgoDto.getFirstDiff());
                        seaWaveAlgoDto.setOrderPrice(seaWaveAlgoDto.getOrderPrice() - seaWaveAlgoDto.getFirstDiff());
                        InstrumentDetailEntity instrumentDetailEntity = instrumentService.getInstrumentDetailEntityByTokenId(seaWaveAlgoDto.getToken());
                        Order order = orderService.modifyRegularOrder(seaWaveAlgoDto.getLastOrderId(), OrderConstantUtil.TransactionType.SELL, OrderConstantUtil.OrderType.SLM,
                                seaWaveAlgoDto.getQuantity() * 2, OrderConstantUtil.Product.MIS, 0, seaWaveAlgoDto.getOrderPrice(), instrumentDetailEntity);
                        seaWaveAlgoDto.setFirstDiffCover(true);
                    }
                } else {
                    if ((seaWaveAlgoDto.getOrderExecutePrice() - seaWaveAlgoDto.getSecondDiff()) >= tick.getLastTradedPrice()) {
                        seaWaveAlgoDto.setOrderExecutePrice(seaWaveAlgoDto.getOrderExecutePrice() - seaWaveAlgoDto.getSecondDiff());
                        seaWaveAlgoDto.setOrderPrice(seaWaveAlgoDto.getOrderPrice() - seaWaveAlgoDto.getSecondDiff());
                        InstrumentDetailEntity instrumentDetailEntity = instrumentService.getInstrumentDetailEntityByTokenId(seaWaveAlgoDto.getToken());
                        Order order = orderService.modifyRegularOrder(seaWaveAlgoDto.getLastOrderId(), OrderConstantUtil.TransactionType.SELL, OrderConstantUtil.OrderType.SLM,
                                seaWaveAlgoDto.getQuantity() * 2, OrderConstantUtil.Product.MIS, 0, seaWaveAlgoDto.getOrderPrice(), instrumentDetailEntity);
                        seaWaveAlgoDto.setFirstDiffCover(true);
                    }
                }
            }
        }
    }

    @Override
    public void onOrderExecute(Order order) {
        SeaWaveAlgoDto seaWaveAlgoDto = tokenToSeaWaveAlgoDtoMap.get(tradingSymbolToTokenId.get(order.tradingSymbol));
        if (order.status.equals(OrderConstantUtil.OrderStatus.COMPLETE) && order.orderId.equals(seaWaveAlgoDto.getLastOrderId())) {
            seaWaveAlgoDto.setOrderExecutePrice(Double.parseDouble(order.averagePrice));
            if (!seaWaveAlgoDto.isFirstOrderExecuted()) {
                if (order.transactionType.equals(OrderConstantUtil.TransactionType.BUY.getValue())) {
                    InstrumentDetailEntity instrumentDetailEntity = instrumentService.getInstrumentDetailEntityByTokenId(seaWaveAlgoDto.getToken());
                    double price = getPrice(TransactionType.SELL, Double.parseDouble(order.averagePrice), seaWaveAlgoDto.getSellPriceNumber());
                    seaWaveAlgoDto.setFirstOrderExecuted(true);
                    seaWaveAlgoDto.setMode(OrderConstantUtil.TransactionType.BUY.getValue());
                    seaWaveAlgoDto.setOrderPrice(price);
                    seaWaveAlgoDto.setFirstDiffCover(false);
                    Order newOrder = orderService.placeRegularOrder(OrderConstantUtil.TransactionType.SELL, OrderConstantUtil.OrderType.SLM,
                            seaWaveAlgoDto.getQuantity() * 2, OrderConstantUtil.Product.MIS, 0, price, instrumentDetailEntity);
                    seaWaveAlgoDto.setLastOrderId(newOrder.orderId);
                } else {
                    InstrumentDetailEntity instrumentDetailEntity = instrumentService.getInstrumentDetailEntityByTokenId(seaWaveAlgoDto.getToken());
                    double price = getPrice(TransactionType.BUY, Double.parseDouble(order.averagePrice), seaWaveAlgoDto.getBuyPriceNumber());
                    seaWaveAlgoDto.setFirstOrderExecuted(true);
                    seaWaveAlgoDto.setMode(OrderConstantUtil.TransactionType.SELL.getValue());
                    seaWaveAlgoDto.setOrderPrice(price);
                    seaWaveAlgoDto.setFirstDiffCover(false);
                    Order newOrder = orderService.placeRegularOrder(OrderConstantUtil.TransactionType.BUY, OrderConstantUtil.OrderType.SLM,
                            seaWaveAlgoDto.getQuantity() * 2, OrderConstantUtil.Product.MIS, 0, price, instrumentDetailEntity);
                    seaWaveAlgoDto.setLastOrderId(newOrder.orderId);
                }
            } else {
                if (order.transactionType.equals(OrderConstantUtil.TransactionType.BUY.getValue())) {
                    InstrumentDetailEntity instrumentDetailEntity = instrumentService.getInstrumentDetailEntityByTokenId(seaWaveAlgoDto.getToken());
                    double price = getPrice(TransactionType.SELL, Double.parseDouble(order.averagePrice) - seaWaveAlgoDto.getFirstDiff(), seaWaveAlgoDto.getSellPriceNumber());
                    seaWaveAlgoDto.setFirstOrderExecuted(true);
                    seaWaveAlgoDto.setMode(OrderConstantUtil.TransactionType.BUY.getValue());
                    seaWaveAlgoDto.setOrderPrice(price);
                    seaWaveAlgoDto.setFirstDiffCover(false);
                    Order newOrder = orderService.placeRegularOrder(OrderConstantUtil.TransactionType.SELL, OrderConstantUtil.OrderType.SLM,
                            seaWaveAlgoDto.getQuantity() * 2, OrderConstantUtil.Product.MIS, 0, price, instrumentDetailEntity);
                    seaWaveAlgoDto.setLastOrderId(newOrder.orderId);

                } else {
                    InstrumentDetailEntity instrumentDetailEntity = instrumentService.getInstrumentDetailEntityByTokenId(seaWaveAlgoDto.getToken());
                    double price = getPrice(TransactionType.BUY, Double.parseDouble(order.averagePrice) + seaWaveAlgoDto.getFirstDiff(), seaWaveAlgoDto.getBuyPriceNumber());
                    seaWaveAlgoDto.setFirstOrderExecuted(true);
                    seaWaveAlgoDto.setMode(OrderConstantUtil.TransactionType.SELL.getValue());
                    seaWaveAlgoDto.setOrderPrice(price);
                    seaWaveAlgoDto.setFirstDiffCover(false);

                    Order newOrder = orderService.placeRegularOrder(OrderConstantUtil.TransactionType.BUY, OrderConstantUtil.OrderType.SLM,
                            seaWaveAlgoDto.getQuantity() * 2, OrderConstantUtil.Product.MIS, 0, price, instrumentDetailEntity);
                    seaWaveAlgoDto.setLastOrderId(newOrder.orderId);

                }
            }
        }
    }

    private TransactionType findBuySellModeForInstrument(Long token) throws JSONException, IOException, IOException, KiteException {
        InstrumentDetailEntity instrumentDetailEntity = instrumentService.getInstrumentDetailEntityByTokenId(token);
        String[] instrument = new String[1];
        instrument[0] = String.valueOf(instrumentDetailEntity.instrument_token);
        Map<String, Quote> quoteMap = instrumentService.getQuote(instrument);
        for (Map.Entry<String, Quote> entry : quoteMap.entrySet()) {
            Quote value = entry.getValue();
            if (value.ohlc.open >= value.lastPrice) {
                return OrderConstantUtil.TransactionType.SELL;
            } else {
                return OrderConstantUtil.TransactionType.BUY;
            }
        }
        return null;
    }

    public double getPrice(TransactionType transactionType, double price, String numberToFind) {
        System.out.println("transactionType :: " + transactionType);
        System.out.println("price :: " + price);
        System.out.println("numberToFind :: " + numberToFind);
        Long priceInLong = (long) (price * 100l);
        String[] split = numberToFind.split(",");
        while (true) {
            for (String string : split) {
                Integer d = (int) (Double.parseDouble(string) * 100);
                int length = d.toString().length();
                String moduloNumberString = "1";
                for (int i = 0; i < length; i++) {
                    moduloNumberString += "0";
                }
                Integer moduloNumber = Integer.parseInt(moduloNumberString);
                if ((int) (priceInLong % moduloNumber) == d) {
                    return (double) priceInLong / 100;
                }
            }
            if (transactionType == TransactionType.BUY) {
                priceInLong++;
            } else {
                priceInLong--;
            }
        }
    }

    @PostConstruct
    public void initIt() throws Exception {

    }

    @PreDestroy
    public void cleanUp() throws Exception {
        System.out.println("Spring Container is destroy! Customer clean up");
    }

}
