package com.godmonth.status.transitor.tx.impl.jpa;

import com.godmonth.status.transitor.tx.impl.Merger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class JpaTxStatusTransitorImpl<MODEL> implements Merger<MODEL> {

    @Autowired
    private EntityManager entityManager;

    @Override
    public MODEL mergeInTx(MODEL model) {
        return entityManager.merge(model);
    }
}
