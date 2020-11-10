package com.godmonth.status.executor.impl;

import lombok.Data;

@Data
public class BeanModelRestriction<V> {

    private String restrictionProperty;

    private V restrictionValue;

}
