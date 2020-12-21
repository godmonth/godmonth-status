package status.analysis.impl;

import status.analysis.intf.ModelAnalysis;
import jodd.bean.BeanUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.function.Predicate;

/**
 * @param <MODEL>
 */
public class SimpleBeanModelAnalysis<MODEL> implements ModelAnalysis<MODEL> {
    @Setter
    protected Class<MODEL> modelClass;

    @Setter
    @Getter
    protected String statusPropertyName;

    @Setter
    protected List<Predicate<MODEL>> predicateList;

    @Override
    public void validate(MODEL model) {
        Validate.isTrue(modelClass.equals(model.getClass()), "modeClass mismatched,expected:%s,actual:%s", modelClass, model.getClass());
        if (predicateList != null) {
            for (Predicate<MODEL> modelPredicate : predicateList) {
                modelPredicate.test(model);
            }
        }
    }

    @Override
    public <STATUS> STATUS getStatus(MODEL model) {
        return BeanUtil.silent.getProperty(model, statusPropertyName);
    }

    @Override
    public <STATUS> void setStatus(MODEL model, STATUS value) {
        BeanUtil.silent.setProperty(model, statusPropertyName, value);
    }


}
