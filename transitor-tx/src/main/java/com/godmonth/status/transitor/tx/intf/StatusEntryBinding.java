package com.godmonth.status.transitor.tx.intf;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 状态进入
 *
 * @author shenyue
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusEntryBinding<STATUS> {
    private STATUS previousStatus;
    private StatusEntry statusEntry;

}
