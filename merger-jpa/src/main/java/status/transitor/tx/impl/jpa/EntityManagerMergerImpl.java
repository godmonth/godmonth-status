package status.transitor.tx.impl.jpa;

import status.transitor.tx.impl.Merger;
import lombok.Setter;

import javax.persistence.EntityManager;

public class EntityManagerMergerImpl<MODEL> implements Merger<MODEL> {
    @Setter
    private EntityManager entityManager;

    @Override
    public MODEL mergeInTx(MODEL model) {
        return entityManager.merge(model);
    }
}
