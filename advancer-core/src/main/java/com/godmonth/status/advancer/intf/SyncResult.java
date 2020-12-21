package com.godmonth.status.advancer.intf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 同步结果
 *
 * @param <SYMBOL>
 * @author shenyue
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SyncResult<MODEL, SYMBOL> {
    private MODEL model;

    private SYMBOL symbol;

    private Object value;

    /**
     * @param model
     */
    public SyncResult(MODEL model) {
        this.model = model;
    }

    /**
     * @param model
     * @param symbol
     */
    public SyncResult(MODEL model, SYMBOL symbol) {
        this.model = model;
        this.symbol = symbol;
    }

}
