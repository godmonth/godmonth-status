package com.godmonth.status.builder;

import com.godmonth.status.advancer.intf.StatusAdvancer;
import com.godmonth.status.analysis.intf.ModelAnalysis;
import com.godmonth.status.builder.statemachine.definition.intf.StatusDefinition;
import com.godmonth.status.builder.statemachine.definition.intf.TriggerDefinition;
import com.godmonth.status.executor.impl.DefaultOrderExecutor;
import com.godmonth.status.executor.intf.OrderExecutor;
import com.godmonth.status.transitor.core.impl.SimpleStatusTransitor;
import com.godmonth.status.transitor.core.intf.StatusTransitor;
import com.godmonth.status.transitor.tx.impl.Merger;
import com.godmonth.status.transitor.tx.impl.TxStatusTransitorImpl;
import com.godmonth.status.transitor.tx.intf.StatusEntry;
import com.godmonth.status.transitor.tx.intf.TxStatusTransitor;
import com.google.common.collect.Maps;
import jodd.bean.BeanUtil;
import lombok.Builder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.core.io.Resource;
import org.springframework.transaction.support.TransactionOperations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

/**
 * <p></p >
 *
 * @author shenyue
 */
public class ExecutorBuilder {

    public static <KEY, VALUE> Map<KEY, VALUE> list2Map(List<VALUE> sourceList, final String propertyName) {
        Function<VALUE, KEY> function = new Function<VALUE, KEY>() {

            @Override
            public KEY apply(VALUE input) {
                return (KEY) BeanUtil.silent.getProperty(input, propertyName);
            }

        };

        if (CollectionUtils.isNotEmpty(sourceList)) {
            return Maps.uniqueIndex(sourceList, function::apply);
        } else {
            return new HashMap<KEY, VALUE>();
        }
    }

    @Builder(builderMethodName = "resourceBuilder", builderClassName = "ResourceBuilder")
    private static <MODEL, INST> OrderExecutor<MODEL, INST> resourceBuild(Class<MODEL> modelClass, List<StatusAdvancer> statusAdvancers, Resource transitorConfig, Class<? extends StatusDefinition> statusDefinitionClass) {
        return new DefaultOrderExecutor<>();
    }

    @Builder(builderMethodName = "simpleBuilder", builderClassName = "SimpleBuilder")
    private static <MODEL, INST, STATUS> OrderExecutor<MODEL, INST> simpleBuild(List<StatusAdvancer> advancers, Map<STATUS, StatusEntry> entryMap, List<StatusDefinition> statusDefinitions, ModelAnalysis modelAnalysis, ExecutorService executorService, Merger merger, TransactionOperations transactionOperations) {
        DefaultOrderExecutor defaultOrderExecutor = new DefaultOrderExecutor();
        if (executorService != null) {
            defaultOrderExecutor.setExecutorService(executorService);
        }
        defaultOrderExecutor.setModelAnalysis(modelAnalysis);

        TxStatusTransitor txStatusTransitor = TxStatusTransitorImpl.builder().modelMerger(merger).statusEntryMap(entryMap).modelAnalysis(modelAnalysis).statusTransitor(statusTransitor(statusDefinitions)).transactionOperations(transactionOperations).build();
        Map<Object, StatusAdvancer> map = list2Map(advancers, "key");
        defaultOrderExecutor.setAdvancerMappings(map);
        defaultOrderExecutor.setTxStatusTransitor(txStatusTransitor);
        return defaultOrderExecutor;
    }

    private static <STATUS, TRIGGER> StatusTransitor<STATUS, TRIGGER> statusTransitor(List<StatusDefinition> statusDefinitions) {
        Map<STATUS, Map<TRIGGER, STATUS>> statusConfigs = new HashMap<>();
        for (StatusDefinition<STATUS, TRIGGER> statusDefinition : statusDefinitions) {
            Validate.notNull(statusDefinition.getStatus(), "status is null");
            Validate.notNull(statusDefinition.getTriggers(), "triggerDefinitions is null");
            Map<TRIGGER, STATUS> triggerConfig = new HashMap<>();
            for (TriggerDefinition<TRIGGER, STATUS> triggerDefinition : statusDefinition.getTriggers()) {
                Validate.notNull(triggerDefinition.getTrigger(), "trigger is null");
                Validate.notNull(triggerDefinition.getNextStatus(), "nextStatus is null");
                triggerConfig.put(triggerDefinition.getTrigger(), triggerDefinition.getNextStatus());
            }
            statusConfigs.put(statusDefinition.getStatus(), triggerConfig);
        }
        return new SimpleStatusTransitor(statusConfigs);
    }
}
