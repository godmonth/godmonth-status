package com.godmonth.status.executor.impl;

import com.godmonth.status.advancer.intf.AdvanceRequest;
import com.godmonth.status.advancer.intf.AdvancedResult;
import com.godmonth.status.advancer.intf.AdvancerBinding;
import com.godmonth.status.advancer.intf.StatusAdvancer2;
import com.godmonth.status.advancer.intf.SyncResult;
import com.godmonth.status.analysis.intf.ModelAnalysis;
import com.godmonth.status.executor.intf.OrderExecutor;
import com.godmonth.status.transitor.tx.intf.TxStatusTransitor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DefaultOrderExecutor<MODEL, INST, TRIGGER> implements OrderExecutor<MODEL, INST> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultOrderExecutor.class);

    @Setter
    private Function<Object, StatusAdvancer2> advancerFunctions;

    @Setter
    private TxStatusTransitor<MODEL, TRIGGER> txStatusTransitor;

    @Builder.Default
    @Setter
    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Setter
    private ModelAnalysis<MODEL> modelAnalysis;

    public static Function<Object, StatusAdvancer2> convert(List<AdvancerBinding> advancerBindings) {
        Map<Object, StatusAdvancer2> advancerMap = new HashMap<>();
        for (AdvancerBinding advancerBinding : advancerBindings) {
            advancerMap.put(advancerBinding.getKey(), advancerBinding.getStatusAdvancer());
        }
        return advancerMap::get;
    }

    public void setAdvancerBindingList(List<AdvancerBinding> advancerBindingList) {
        this.advancerFunctions = convert(advancerBindingList);
    }


    @Override
    public Future<SyncResult<MODEL, ?>> executeAsync(final MODEL model, final INST instruction, final Object message) {
        modelAnalysis.validate(model);
        return executorService.submit(new Callable<SyncResult<MODEL, ?>>() {

            @Override
            public SyncResult<MODEL, ?> call() {
                return execute(model, instruction, message);
            }
        });
    }

    @Override
    public SyncResult<MODEL, ?> execute(MODEL model, INST instruction, Object message) {
        modelAnalysis.validate(model);
        AdvancedResult<MODEL, TRIGGER> advancedResult = null;
        while (true) {
            Object status = modelAnalysis.getStatus(model);
            logger.trace("status:{}", status);

            StatusAdvancer2<MODEL, INST, TRIGGER> advancer = null;
            if (instruction != null) {
                advancer = advancerFunctions.apply(Pair.of(status, instruction));
            }
            if (advancer == null) {
                advancer = advancerFunctions.apply(status);
            }
            logger.trace("advancer:{}", advancer);
            if (advancer == null) {
                return new SyncResult<MODEL, Object>(model);
            }
            AdvanceRequest<MODEL, INST> advanceRequest = AdvanceRequest.<MODEL, INST>builder().model(model).instruction(instruction).message(message).build();
            advancedResult = advancer.advance(advanceRequest);
            logger.trace("advancedResult:{}", advancedResult);
            if (advancedResult == null) {
                return new SyncResult<MODEL, Object>(model);
            }
            if (advancedResult.getTriggerBehavior() != null) {
                model = txStatusTransitor.transit(model, advancedResult.getTriggerBehavior());
                Validate.notNull(model, "transtied model is null.");
                if (advancedResult.isDropInstruction()) {
                    instruction = null;
                    message = null;
                }
                switch (advancedResult.getNextOperation()) {
                    case ADVANCE:
                        advancedResult = null;
                        continue;
                    case ASYNC_ADVANCE:
                        logger.debug("executeAsync");
                        executeAsync(model, instruction, message);
                    case PAUSE:
                }
            }
            break;
        }
        SyncResult<MODEL, ?> syncResult = advancedResult.getSyncResult();
        if (syncResult == null) {
            return new SyncResult<MODEL, Object>(model);
        } else {
            if (syncResult.getModel() == null) {
                syncResult.setModel(model);
            }
            return syncResult;
        }
    }

    public static class DefaultOrderExecutorBuilder<MODEL, INST, TRIGGER> {
        private Function<Object, StatusAdvancer2> advancerFunctions;

        public DefaultOrderExecutorBuilder advancerBindingList(List<AdvancerBinding> advancerBindingList) {
            this.advancerFunctions = convert(advancerBindingList);
            return this;
        }

    }

}
