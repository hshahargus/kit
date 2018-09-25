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
public class DoubleUpAlgoDto {

    private Long token;
    private String mode;
    private Double diff;
    private Double trailingPoint;
    private Integer baseQty;
    private Double modificationTrigerPrice;
    private Integer orderQty;
    private Double orderExecutePrice;
    private Double orderCriteriaFulfillPrice;
    private Integer lastOrderQty;
    private Integer currentQty;
    private String lastOrderId;
    private boolean isFirstOrderExecuted;
    private Double orderPrice;
    
}
