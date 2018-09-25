/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argusoft.kite.algo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Harshit
 */
@Data
@NoArgsConstructor
public class SeaWaveAlgoDto {

    private Long token;
    private String mode;
    private Double firstDiff;
    private Double secondDiff;
    private boolean isFirstDiffCover;
    private boolean isFirstOrderExecuted;
    private Double modificationTrigerPrice;
    private Double orderPrice;
    private Double orderExecutePrice;
    private String lastOrderId;
    private Integer quantity;
    private String buyPriceNumber;
    private String sellPriceNumber;

}
