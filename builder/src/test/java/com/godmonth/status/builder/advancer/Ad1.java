package com.godmonth.status.builder.advancer;

import com.godmonth.status.advancer.impl.AbstractAdvancer;
import com.godmonth.status.advancer.intf.AdvancedResult;
import com.godmonth.status.annotations.Advancer;
import com.godmonth.status.builder.domain.SampleModel;
import com.godmonth.status.builder.domain.SampleStatus;
import com.godmonth.status.builder.domain.SampleTrigger;
import com.godmonth.status.transitor.tx.intf.TriggerBehavior;


/**
 * <p></p >
 *
 * @author shenyue
 */
@Advancer(SampleModel.class)
public class Ad1 extends AbstractAdvancer<SampleModel, Void, SampleTrigger> {
    {
        availableStatus = SampleStatus.CREATED;
    }

    @Override
    public AdvancedResult<SampleModel, SampleTrigger> advance(SampleModel sampleModel, Void instruction, Object message) {
        TriggerBehavior<SampleTrigger, SampleModel> sampleTriggerObjectTriggerBehavior = new TriggerBehavior<>(SampleTrigger.PAY);
        return new AdvancedResult<>(sampleTriggerObjectTriggerBehavior);
    }

    @Override
    public Object getKey() {
        return availableStatus;
    }
}
