package com.argusoft.kite.algo.service.impl;

import com.argusoft.kite.algo.dto.DoubleUpAlgoDto;
import com.argusoft.kite.algo.service.DoubleUpAlgoService;
import com.argusoft.kite.instrument.model.InstrumentDetailEntity;
import com.argusoft.kite.instrument.service.InstrumentService;
import com.argusoft.kite.order.constant.OrderConstantUtil;
import com.argusoft.kite.order.constant.OrderConstantUtil.TransactionType;
import com.argusoft.kite.order.service.OrderService;
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
public class DoubleUpAlgoServiceImpl implements DoubleUpAlgoService {

    @Autowired
    private InstrumentService instrumentService;

    @Autowired
    private OrderService orderService;

    private Map<Long, DoubleUpAlgoDto> tokenToDoubleUpAlgoDtoMap = new HashMap<>();
    private Map<String, Long> tradingSymbolToTokenId = new HashMap<>();

    @Override
    public void subcribeToken(DoubleUpAlgoDto doubleUpAlgoDto) {
        try {
            TransactionType transactionType = findBuySellModeForInstrument(doubleUpAlgoDto.getToken());
            InstrumentDetailEntity instrumentDetailEntity = instrumentService.getInstrumentDetailEntityByTokenId(doubleUpAlgoDto.getToken());
            Order order = orderService.placeRegularOrder(transactionType, OrderConstantUtil.OrderType.MARKET,
                    doubleUpAlgoDto.getBaseQty(), OrderConstantUtil.Product.MIS, 0, 0, instrumentDetailEntity);
            doubleUpAlgoDto.setLastOrderId(order.orderId);
            doubleUpAlgoDto.setFirstOrderExecuted(false);
            tradingSymbolToTokenId.put(instrumentDetailEntity.tradingsymbol, instrumentDetailEntity.getInstrument_token());
            tokenToDoubleUpAlgoDtoMap.put(instrumentDetailEntity.getInstrument_token(), doubleUpAlgoDto);

        } catch (JSONException ex) {
            Logger.getLogger(DoubleUpAlgoServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DoubleUpAlgoServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KiteException ex) {
            Logger.getLogger(DoubleUpAlgoServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateTokenPrice(Tick tick) {
        DoubleUpAlgoDto doubleUpAlgoDto = tokenToDoubleUpAlgoDtoMap.get(tick.getInstrumentToken());
        if (doubleUpAlgoDto != null) {
            if (doubleUpAlgoDto.getMode().equals(TransactionType.BUY.getValue())) {
                if (doubleUpAlgoDto.getModificationTrigerPrice() <= tick.getLastTradedPrice()) {
                    System.out.println("BUY ::: doubleUpAlgoDto.getModificationTrigerPrice() ::" + doubleUpAlgoDto.getModificationTrigerPrice() + "  tick.getLastTradedPrice() :: " + tick.getLastTradedPrice());
                    if ((doubleUpAlgoDto.getOrderCriteriaFulfillPrice() + doubleUpAlgoDto.getDiff()) <= tick.getLastTradedPrice()) {
                        System.out.println("BUY order fulfilment ::: doubleUpAlgoDto.getOrderCriteriaFulfillPrice() ::" + doubleUpAlgoDto.getOrderCriteriaFulfillPrice() + "  tick.getLastTradedPrice() :: " + tick.getLastTradedPrice());
                        System.out.println("doubleUpAlgoDto.getOrderQty() :: " + doubleUpAlgoDto.getOrderQty() + "  doubleUpAlgoDto.getBaseQty() :: " + doubleUpAlgoDto.getBaseQty());
                        doubleUpAlgoDto.setLastOrderQty(doubleUpAlgoDto.getCurrentQty() + doubleUpAlgoDto.getBaseQty());
                    }

                    doubleUpAlgoDto.setModificationTrigerPrice(tick.getLastTradedPrice() + doubleUpAlgoDto.getTrailingPoint());
                    System.out.println("BUY :: doubleUpAlgoDto.setModificationTrigerPrice :: " + doubleUpAlgoDto.getModificationTrigerPrice());
                    System.out.println("BUY :: doubleUpAlgoDto.getLastOrderQty :: " + doubleUpAlgoDto.getLastOrderQty());
                    InstrumentDetailEntity instrumentDetailEntity = instrumentService.getInstrumentDetailEntityByTokenId(doubleUpAlgoDto.getToken());
                    Order order = orderService.modifyRegularOrder(doubleUpAlgoDto.getLastOrderId(), OrderConstantUtil.TransactionType.SELL, OrderConstantUtil.OrderType.SLM,
                            doubleUpAlgoDto.getLastOrderQty(), OrderConstantUtil.Product.MIS, 0, tick.getLastTradedPrice() - doubleUpAlgoDto.getDiff(), instrumentDetailEntity);
                }
            } else {
                if (doubleUpAlgoDto.getModificationTrigerPrice() >= tick.getLastTradedPrice()) {
                    System.out.println("SELL ::: doubleUpAlgoDto.getModificationTrigerPrice() ::" + doubleUpAlgoDto.getModificationTrigerPrice() + "  tick.getLastTradedPrice() :: " + tick.getLastTradedPrice());

                    if ((doubleUpAlgoDto.getOrderCriteriaFulfillPrice() - doubleUpAlgoDto.getDiff()) >= tick.getLastTradedPrice()) {
                        System.out.println("SELL order fulfilment ::: doubleUpAlgoDto.getOrderCriteriaFulfillPrice() ::" + doubleUpAlgoDto.getOrderCriteriaFulfillPrice() + "  tick.getLastTradedPrice() :: " + tick.getLastTradedPrice());
                        System.out.println("doubleUpAlgoDto.getOrderQty() :: " + doubleUpAlgoDto.getOrderQty() + "  doubleUpAlgoDto.getBaseQty() :: " + doubleUpAlgoDto.getBaseQty());

                        doubleUpAlgoDto.setLastOrderQty(doubleUpAlgoDto.getCurrentQty() + doubleUpAlgoDto.getBaseQty());
                    }
                    doubleUpAlgoDto.setModificationTrigerPrice(tick.getLastTradedPrice() - doubleUpAlgoDto.getTrailingPoint());
                    System.out.println("doubleUpAlgoDto.setModificationTrigerPrice :: " + doubleUpAlgoDto.getModificationTrigerPrice());
                    System.out.println("SELL :: doubleUpAlgoDto.setModificationTrigerPrice :: " + doubleUpAlgoDto.getModificationTrigerPrice());
                    System.out.println("SELL :: doubleUpAlgoDto.getLastOrderQty :: " + doubleUpAlgoDto.getLastOrderQty());
                    InstrumentDetailEntity instrumentDetailEntity = instrumentService.getInstrumentDetailEntityByTokenId(doubleUpAlgoDto.getToken());
                    Order order = orderService.modifyRegularOrder(doubleUpAlgoDto.getLastOrderId(), OrderConstantUtil.TransactionType.BUY, OrderConstantUtil.OrderType.SLM,
                            doubleUpAlgoDto.getLastOrderQty(), OrderConstantUtil.Product.MIS, 0, tick.getLastTradedPrice() + doubleUpAlgoDto.getDiff(), instrumentDetailEntity);
                }
            }
        }
    }

    @Override
    public void onOrderExecute(Order order) {
        DoubleUpAlgoDto doubleUpAlgoDto = tokenToDoubleUpAlgoDtoMap.get(tradingSymbolToTokenId.get(order.tradingSymbol));
        if (order.status.equals(OrderConstantUtil.OrderStatus.COMPLETE) && order.orderId.equals(doubleUpAlgoDto.getLastOrderId())) {
            doubleUpAlgoDto.setOrderExecutePrice(Double.parseDouble(order.averagePrice));
            doubleUpAlgoDto.setOrderCriteriaFulfillPrice(doubleUpAlgoDto.getOrderExecutePrice());

            if (!doubleUpAlgoDto.isFirstOrderExecuted()) {
                if (order.transactionType.equals(OrderConstantUtil.TransactionType.BUY.getValue())) {
                    doubleUpAlgoDto.setModificationTrigerPrice(doubleUpAlgoDto.getOrderExecutePrice() + doubleUpAlgoDto.getTrailingPoint());
                    InstrumentDetailEntity instrumentDetailEntity = instrumentService.getInstrumentDetailEntityByTokenId(doubleUpAlgoDto.getToken());
                    double price = doubleUpAlgoDto.getOrderExecutePrice() - doubleUpAlgoDto.getDiff();
                    doubleUpAlgoDto.setFirstOrderExecuted(true);
                    doubleUpAlgoDto.setMode(OrderConstantUtil.TransactionType.BUY.getValue());
                    doubleUpAlgoDto.setOrderPrice(price);
                    System.out.println("ORDER BUY F :: doubleUpAlgoDto.getBaseQty() :: " + doubleUpAlgoDto.getBaseQty());
                    doubleUpAlgoDto.setLastOrderQty(doubleUpAlgoDto.getBaseQty() * 3);
                    doubleUpAlgoDto.setCurrentQty(doubleUpAlgoDto.getBaseQty());
                    System.out.println("ORDER BUY F :: doubleUpAlgoDto.getLastOrderQty() :: " + doubleUpAlgoDto.getLastOrderQty());
                    Order newOrder = orderService.placeRegularOrder(OrderConstantUtil.TransactionType.SELL, OrderConstantUtil.OrderType.SLM,
                            doubleUpAlgoDto.getBaseQty() * 3, OrderConstantUtil.Product.MIS, 0, price, instrumentDetailEntity);
                    doubleUpAlgoDto.setLastOrderId(newOrder.orderId);
                } else {
                    doubleUpAlgoDto.setModificationTrigerPrice(doubleUpAlgoDto.getOrderExecutePrice() - doubleUpAlgoDto.getTrailingPoint());

                    InstrumentDetailEntity instrumentDetailEntity = instrumentService.getInstrumentDetailEntityByTokenId(doubleUpAlgoDto.getToken());
                    double price = doubleUpAlgoDto.getOrderExecutePrice() + doubleUpAlgoDto.getDiff();
                    doubleUpAlgoDto.setFirstOrderExecuted(true);
                    doubleUpAlgoDto.setMode(OrderConstantUtil.TransactionType.SELL.getValue());
                    doubleUpAlgoDto.setOrderPrice(price);
                    doubleUpAlgoDto.setCurrentQty(doubleUpAlgoDto.getBaseQty());
                    System.out.println("ORDER SELL F :: doubleUpAlgoDto.getBaseQty() :: " + doubleUpAlgoDto.getBaseQty());
                    doubleUpAlgoDto.setLastOrderQty(doubleUpAlgoDto.getBaseQty() * 3);
                    System.out.println("ORDER SELL F :: doubleUpAlgoDto.getLastOrderQty() :: " + doubleUpAlgoDto.getLastOrderQty());
                    Order newOrder = orderService.placeRegularOrder(OrderConstantUtil.TransactionType.BUY, OrderConstantUtil.OrderType.SLM,
                            doubleUpAlgoDto.getBaseQty() * 3, OrderConstantUtil.Product.MIS, 0, price, instrumentDetailEntity);
                    doubleUpAlgoDto.setLastOrderId(newOrder.orderId);
                }
            } else {
                if (order.transactionType.equals(OrderConstantUtil.TransactionType.BUY.getValue())) {
                    doubleUpAlgoDto.setModificationTrigerPrice(doubleUpAlgoDto.getOrderExecutePrice() + doubleUpAlgoDto.getTrailingPoint());
                    InstrumentDetailEntity instrumentDetailEntity = instrumentService.getInstrumentDetailEntityByTokenId(doubleUpAlgoDto.getToken());
                    double price = doubleUpAlgoDto.getOrderExecutePrice() - doubleUpAlgoDto.getDiff();
                    doubleUpAlgoDto.setMode(OrderConstantUtil.TransactionType.BUY.getValue());
                    doubleUpAlgoDto.setOrderPrice(price);
                    System.out.println("ORDER BUY ::: doubleUpAlgoDto.getLastOrderQty() :: " + doubleUpAlgoDto.getLastOrderQty() + "doubleUpAlgoDto.getOrderQty() :: " + doubleUpAlgoDto.getOrderQty());
                    Integer updatedLastQty = (doubleUpAlgoDto.getLastOrderQty() - doubleUpAlgoDto.getCurrentQty()) * 3;
                    doubleUpAlgoDto.setCurrentQty(doubleUpAlgoDto.getLastOrderQty() - doubleUpAlgoDto.getCurrentQty());
                    doubleUpAlgoDto.setLastOrderQty(updatedLastQty);

                    Order newOrder = orderService.placeRegularOrder(OrderConstantUtil.TransactionType.SELL, OrderConstantUtil.OrderType.SLM,
                            doubleUpAlgoDto.getLastOrderQty(), OrderConstantUtil.Product.MIS, 0, price, instrumentDetailEntity);
                    doubleUpAlgoDto.setLastOrderId(newOrder.orderId);
                } else {
                    doubleUpAlgoDto.setModificationTrigerPrice(doubleUpAlgoDto.getOrderExecutePrice() - doubleUpAlgoDto.getTrailingPoint());
                    InstrumentDetailEntity instrumentDetailEntity = instrumentService.getInstrumentDetailEntityByTokenId(doubleUpAlgoDto.getToken());
                    double price = doubleUpAlgoDto.getOrderExecutePrice() + doubleUpAlgoDto.getDiff();
                    doubleUpAlgoDto.setMode(OrderConstantUtil.TransactionType.SELL.getValue());
                    doubleUpAlgoDto.setOrderPrice(price);
                    System.out.println("ORDER SELL ::: doubleUpAlgoDto.getLastOrderQty() :: " + doubleUpAlgoDto.getLastOrderQty() + "doubleUpAlgoDto.getOrderQty() :: " + doubleUpAlgoDto.getOrderQty());
                    Integer updatedLastQty = (doubleUpAlgoDto.getLastOrderQty() - doubleUpAlgoDto.getCurrentQty()) * 3;
                    doubleUpAlgoDto.setCurrentQty(doubleUpAlgoDto.getLastOrderQty() - doubleUpAlgoDto.getCurrentQty());
                    doubleUpAlgoDto.setLastOrderQty(updatedLastQty);
                    Order newOrder = orderService.placeRegularOrder(OrderConstantUtil.TransactionType.BUY, OrderConstantUtil.OrderType.SLM,
                            doubleUpAlgoDto.getLastOrderQty(), OrderConstantUtil.Product.MIS, 0, price, instrumentDetailEntity);
                    doubleUpAlgoDto.setLastOrderId(newOrder.orderId);
                }
            }
            doubleUpAlgoDto.setOrderQty(Integer.parseInt(order.quantity));
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
