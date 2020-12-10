package com.godmonth.status.transitor.tx.impl.jpa;

import com.godmonth.status.transitor.tx.impl.Merger;
import lombok.Builder;

import javax.persistence.EntityManager;

@Builder
public class EntityManagerMergerImpl<MODEL> implements Merger<MODEL> {
    
    private EntityManager entityManager;

    @Override
    public MODEL mergeInTx(MODEL model) {
        return entityManager.merge(model);
    }
}
