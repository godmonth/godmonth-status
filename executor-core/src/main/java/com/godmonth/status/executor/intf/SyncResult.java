package com.godmonth.status.executor.intf;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 同步结果
 *
 * @param <SYMBOL>
 * @author shenyue
 */
@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class SyncResult<MODEL, SYMBOL> extends com.godmonth.status.advancer.intf.SyncResult<MODEL, SYMBOL> {

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

    /**
     * @param model
     * @param symbol
     */
    @Builder
    public SyncResult(MODEL model, SYMBOL symbol, Object value) {
        this.model = model;
        this.symbol = symbol;
        this.value = value;
    }
}
