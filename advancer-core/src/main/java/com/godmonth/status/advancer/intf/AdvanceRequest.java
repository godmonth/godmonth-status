package com.godmonth.status.advancer.intf;

import lombok.Builder;
import lombok.Data;

/**
 * <p></p >
 *
 * @author shenyue
 */
@Builder
@Data
public class AdvanceRequest<MODEL, INST> {
    MODEL model;
    INST instruction;
    Object message;
}
