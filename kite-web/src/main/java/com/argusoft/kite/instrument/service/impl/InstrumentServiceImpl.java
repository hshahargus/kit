package com.argusoft.kite.instrument.service.impl;

import com.argusoft.kite.instrument.constant.InstrumentConstantUtil;
import static com.argusoft.kite.instrument.constant.InstrumentConstantUtil.numberQuoteGet;
import com.argusoft.kite.instrument.dao.DaysOhlcDao;
import com.argusoft.kite.instrument.dao.InstrumentDetailDao;
import com.argusoft.kite.instrument.model.DaysOHLC;
import com.argusoft.kite.instrument.model.InstrumentDetailEntity;
import com.argusoft.kite.instrument.service.InstrumentService;
import com.argusoft.kite.ticker.dao.KpDatalDao;
import com.argusoft.kite.ticker.dao.TickDetailDao;
import com.argusoft.kite.ticker.model.KPDataEntity;
import com.argusoft.kite.ticker.model.TickDetailEntity;
import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Instrument;
import com.zerodhatech.models.Quote;
import com.zerodhatech.models.Tick;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class InstrumentServiceImpl implements InstrumentService {

    @Autowired
    private KiteConnect kiteSdk;

    @Autowired
    private InstrumentDetailDao instrumentDetailDao;
    @Autowired
    private DaysOhlcDao daysOhlcDao;

    @Autowired
    private TickDetailDao tickDetailDao;
    @Autowired
    private KpDatalDao kpDatalDao;

    @Override
    public void insertAllInstrumentDetail() {
        instrumentDetailDao.deleteAll();
        try {
            List<InstrumentDetailEntity> instrumentDetailEntitys = new LinkedList<>();
            List<Instrument> instruments = kiteSdk.getInstruments();

            for (Instrument instrument : instruments) {
                instrumentDetailEntitys.add(new InstrumentDetailEntity(instrument));
            }
            instrumentDetailDao.createOrUpdateAll(instrumentDetailEntitys);
        } catch (KiteException ex) {
            Logger.getLogger(InstrumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InstrumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void insertDaysOHLC() {
        List<InstrumentDetailEntity> instrumentDetailEntitys = instrumentDetailDao.retrieveAll();
        List<DaysOHLC> daysOHLCs = null;
        List<DaysOHLC> newOrUpdatedOHLC = new LinkedList<>();
        try {
            daysOHLCs = daysOhlcDao.retrieveByDate(new Date());
        } catch (ParseException ex) {
            Logger.getLogger(InstrumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        Map<Long, DaysOHLC> tokenToDaysOHLC = new HashMap<>();
        for (DaysOHLC daysOHLC : daysOHLCs) {
            tokenToDaysOHLC.put(daysOHLC.instrumentToken, daysOHLC);
        }

        String[] token = new String[numberQuoteGet];
        int i = 0;
        for (InstrumentDetailEntity instrumentDetailEntity : instrumentDetailEntitys) {
            if (i < numberQuoteGet) {
//                token[i++] = String.valueOf(instrumentDetailEntity.getExchange() + ":" + instrumentDetailEntity.getTradingsymbol());
                token[i++] = String.valueOf(instrumentDetailEntity.getInstrument_token());
            }

            if (i == 500) {
                System.out.println(":::: 1 ::::::");
                try {
//            List<Holding> holdings = kiteSdk.getHoldings();
                    Map<String, Quote> ohlc = getQuote(token);
                    for (Map.Entry<String, Quote> entry : ohlc.entrySet()) {

                        String key = entry.getKey();
                        Quote value = entry.getValue();
                        DaysOHLC daysOHLC = new DaysOHLC(value);
                        DaysOHLC oldDaysOHLC = tokenToDaysOHLC.get(daysOHLC.getInstrumentToken());
                        if (oldDaysOHLC != null) {
                            daysOHLC.setId(oldDaysOHLC.getId());
                        }
                        newOrUpdatedOHLC.add(daysOHLC);
                    }
                } catch (JSONException ex) {
                    Logger.getLogger(InstrumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(InstrumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (KiteException ex) {
                    Logger.getLogger(InstrumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                i = 0;
                token = new String[numberQuoteGet];
            }

        }
        daysOhlcDao.createOrUpdateAll(newOrUpdatedOHLC);

    }

    public Map<String, Quote> getQuote(String[] token) throws JSONException, IOException, KiteException {
        try {
//            List<Holding> holdings = kiteSdk.getHoldings();
            return kiteSdk.getQuote(token);
        } catch (KiteException ex) {
            if (ex.code == 429) {
                return getQuote(token);
            } else {
                throw ex;
            }
        }
    }

    @Override
    public InstrumentDetailEntity getInstrumentDetailEntityByTokenId(long tokenId) {
        InstrumentDetailEntity instrumentDetailEntity = InstrumentConstantUtil.tokenToInstrumentDetailEntity.get(tokenId);
        if (instrumentDetailEntity == null) {
            instrumentDetailEntity = instrumentDetailDao.retrieveByTokenId(tokenId);
            InstrumentConstantUtil.tokenToInstrumentDetailEntity.put(tokenId, instrumentDetailEntity);
        }

        return instrumentDetailEntity;
    }

    public void createTick(Tick tick) {
        TickDetailEntity tickDetailEntity = new TickDetailEntity(tick);
//        System.out.println("tickDetailEntity ::: " + tickDetailEntity.getLastTradedPrice());
        tickDetailDao.create(tickDetailEntity);
    }

    private int mode = 0; // 0 none , 1 buy mode , 2 sell mode
    private double v = .01;
    private double prev = -1;
    private double bsPrice = 0;
    private double dv = 0;
    private double high = 0;
    private double low = 0;

    private double tProfit = 0;
    private int turnover = 0;

    void handleNewPrice(double price) {
        if (prev <= 0) {
            prev = price;
            bsPrice = price;
            dv = bsPrice * v / 100.0;
            //  System.out.println("DV: " + dv);
            //return;
        } else {
            if (mode == 0) { // no mode
                if ((price - bsPrice) > dv) { // enter into buy mode 
                    mode = 1;
                    bsPrice = price;
                    high = price;
                    // System.out.println("buy X: " + bsPrice);
                    turnover+=1;
                }

                if ((bsPrice - price) > dv) {
                    mode = 2;
                    bsPrice = price;
                    low = price;
                    //System.out.println("Sell X: " + bsPrice);
                    turnover+=1;
                }
            } else if (mode == 1) { // buy mode
                if (high < price) {
                    high = price;
                }
                //if (((high - price) > dv && price > bsPrice + dv ) || (bsPrice - price) > dv) { // convert to sell mode
                if ( bsPrice - price > dv/2 ||  price - bsPrice > dv ) { // convert to sell mode
                    mode = 2;
                    tProfit += price - bsPrice;
                    bsPrice = price;
                    low = price;
                    //System.out.println("Sell 2X : " + bsPrice + " TP&L:" + tProfit);
                    turnover+=2;
                }

            } else { // sell mode
                if (low > price) {
                    low = price;
                }

                //if (((price - low) > dv && price < bsPrice - dv ) || (price - bsPrice) > dv) { // convert to buy mode
                 if (   bsPrice - price > dv ||  price - bsPrice > dv/2 ) { // convert to buy mode
                    mode = 1;
                    tProfit += bsPrice - price;
                    bsPrice = price;
                    high = price;
                    //System.out.println("Buy 2X: " + bsPrice + " TP&L:" + tProfit);
                    turnover+=2;
                }

            }
        }
        profit = tProfit;
    }

    double profit = 0;

    @Override
    public void test() {
        for (int k = 0; k < 50; k++) {

            v = .5;
            
            List<KPDataEntity> kPDataEntitys = kpDatalDao.getData(k);

            for (int j = 0; j < 100; j++) {
                turnover = 0 ;
                mode = 0; // 0 none , 1 buy mode , 2 sell mode
                v += 0.1;
                prev = -1;
                bsPrice = 0;
                dv = 0;
                high = 0;
                low = 0;
                tProfit = 0;

                int i = 0;
                for (KPDataEntity it : kPDataEntitys) {
                    i++;
                    //if (i > 1000 && i < 7000) {
                    handleNewPrice(it.getLastTradedPrice());
                    //}
                }
                if (tProfit > 0) {
                    System.out.println("Trunover: "+turnover+" k : " + k +" DIV% : " + v + "DIV RS : " + dv + " PROFIT : " + tProfit 
                            + " p% " + (int)((tProfit/bsPrice)*100));
                }
            }

//        double tstarted = 0d;
//        double prev = 0d;
//        boolean flag = true;
//
//        boolean isUp = false;
//        for (KPDataEntity it : kPDataEntitys) {
//            if (flag) {
//                //ltp = it.getLastTradedPrice();
//                prev = it.getLastTradedPrice();
//                flag = false;
//                continue;
//            }
//
//            double variance = 0.0025;
//            //for second element
//            if (prev < it.getLastTradedPrice()) {
//
//                if (!isUp) {
//                    if (((tstarted - prev) / it.getLastTradedPrice()) > variance) {
//                        System.out.println("PRICE " + tstarted + " ended " + prev + " LC DOWN:: " + (prev - tstarted) + " TCC" + (prev - it.getLastTradedPrice()));
//                    }
//                    //System.out.println("Down :: " + (prev - tstarted) + " TCC" + (prev - it.getLastTradedPrice()));
//                    tstarted = prev;
//                    isUp = true;
//
//                }
//            } else {
//                if (isUp) {
//                    if (((prev - tstarted) / it.getLastTradedPrice()) > variance) {
//                        System.out.println("PRICE " + prev + " ended " + tstarted + " LC UP :: " + (prev - tstarted) + " TCC" + (prev - it.getLastTradedPrice()));
//                    }
//                    // System.out.println("UP :: " + (prev - tstarted) + " TCC" + (prev - it.getLastTradedPrice()));
//                    tstarted = prev;
//                    isUp = false;
//
//                }
//
//            }
//            prev = it.getLastTradedPrice();
//            //System.out.println("kPDataEntity :: " + it.getLastTradedPrice());
            //      }
        }
    }
}
