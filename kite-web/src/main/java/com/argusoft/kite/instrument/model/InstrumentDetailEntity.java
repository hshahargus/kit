/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argusoft.kite.instrument.model;

import com.zerodhatech.models.Instrument;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Harshit
 */
@Entity
@Table(name = "Instrument_detail")
public class InstrumentDetailEntity extends Instrument {

    @Id
    @Basic(optional = false)
    @Column(name = "instrument_token")
    public long getInstrument_token() {
        return instrument_token;
    }

    @Column
    public long getExchange_token() {
        return exchange_token;
    }

    @Column
    public String getTradingsymbol() {
        return tradingsymbol;
    }

    @Column
    public String getName() {
        return name;
    }

    @Column
    public double getLast_price() {
        return last_price;
    }

    @Column
    public double getTick_size() {
        return tick_size;
    }

    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    @Override
    public Date getExpiry() {
        return expiry;
    }


    @Column
    public String getInstrument_type() {
        return instrument_type;
    }

    @Column
    public String getSegment() {
        return segment;
    }

    @Column
    public String getExchange() {
        return exchange;
    }

    @Column
    public String getStrike() {
        return strike;
    }

    @Column
    public int getLot_size() {
        return lot_size;
    }

    public InstrumentDetailEntity() {
    }

    public InstrumentDetailEntity(Instrument instrument) {
        this.exchange = instrument.exchange;
        this.exchange_token = instrument.exchange_token;
        this.expiry = instrument.expiry;
        this.instrument_token = instrument.instrument_token;
        this.instrument_type = instrument.instrument_type;
        this.last_price = instrument.last_price;
        this.lot_size = instrument.lot_size;
        this.name = instrument.name;
        this.segment = instrument.segment;
        this.strike = instrument.strike;
        this.tick_size = instrument.tick_size;
        this.tradingsymbol = instrument.tradingsymbol;
    }

}
