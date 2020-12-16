package com.godmonth.status.executor.impl.analysis;

import lombok.Data;

@Data
public class BeanModelRestriction<V> {

    private String restrictionProperty;

    private V restrictionValue;

}
