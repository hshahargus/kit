package com.argusoft.kite.instrument.service;

import com.argusoft.kite.instrument.model.InstrumentDetailEntity;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Quote;
import com.zerodhatech.models.Tick;
import java.io.IOException;
import java.util.Map;
import org.json.JSONException;

/**
 *
 * @author hshah
 */
public interface InstrumentService {

    public void insertAllInstrumentDetail();

    public void insertDaysOHLC();

    /**
     *
     * @param tokenId
     * @return
     */
    public InstrumentDetailEntity getInstrumentDetailEntityByTokenId(long tokenId);

    public Map<String, Quote> getQuote(String[] token) throws JSONException, IOException, KiteException;

    public void createTick(Tick tick);

    public void test();
}
