package com.godmonth.status.transitor.bean.impl;

import com.godmonth.status.transitor.bean.intf.BeanStatusTransitor;
import com.godmonth.status.transitor.bean.intf.StatusEntry;
import com.godmonth.status.transitor.bean.intf.StatusExit;
import com.godmonth.status.transitor.core.intf.StatusTransitor;
import jodd.bean.BeanUtil;
import lombok.Setter;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

public class BeanStatusTransitorImpl<MODEL, STATUS, TRIGGER> implements BeanStatusTransitor<MODEL, TRIGGER> {

    private static final Logger logger = LoggerFactory.getLogger(BeanStatusTransitorImpl.class);

    @Setter
    protected String statusPropertyName;

    @Setter
    private StatusTransitor<STATUS, TRIGGER> statusTransitor;

    @Setter
    private Map<STATUS, StatusExit<MODEL>> statusExitMap = Collections.emptyMap();

    @Setter
    private Map<STATUS, StatusEntry<MODEL>> statusEntryMap = Collections.emptyMap();

    @Override
    public MODEL transit(MODEL model, TRIGGER trigger) {
        STATUS nextStatus = beforeChange(model, trigger);

        MODEL mergedModel = mergeModel(model, nextStatus);

        afterChange(mergedModel, nextStatus);

        return mergedModel;
    }

    protected MODEL mergeModel(MODEL model, STATUS nextStatus) {
        BeanUtil.silent.setProperty(model, statusPropertyName, nextStatus);
        return model;
    }

    protected STATUS beforeChange(MODEL model, TRIGGER trigger) {
        STATUS status = BeanUtil.silent.getProperty(model, statusPropertyName);
        Validate.notNull(status, "status is null");

        STATUS nextStatus = statusTransitor.transit(status, trigger);
        Validate.notNull(nextStatus, "nextStatus is null");

        if (statusExitMap.get(status) != null) {
            statusExitMap.get(status).previousStatusExit(model);
        }
        return nextStatus;
    }

    protected void afterChange(MODEL model, STATUS status) {
        if (statusEntryMap.get(status) != null) {
            statusEntryMap.get(status).nextStatusEntry(model);
        }
    }

}
