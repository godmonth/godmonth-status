package com.godmonth.status.test.sample2.advancer;

import com.godmonth.status.advancer.impl.AbstractAdvancer;
import com.godmonth.status.advancer.intf.AdvancedResult;
import com.godmonth.status.test.sample.domain.SampleModel;
import com.godmonth.status.test.sample.domain.SampleStatus;
import com.godmonth.status.test.sample.machine.trigger.SampleTrigger;
import com.godmonth.status.transitor.tx.intf.TriggerBehavior;
import org.springframework.stereotype.Component;

@SampleAdvancerBinding
@Component
public class PayAdvancer extends AbstractAdvancer<SampleModel, String, SampleTrigger> {
    {
        availableStatus = SampleStatus.CREATED;
    }
    public PayAdvancer(){
        System.out.println("ok");
    }
    @Override
    public AdvancedResult<SampleModel, SampleTrigger> advance(SampleModel model, String instruction, Object message)
            throws IllegalStateException {
        System.out.println("advanced");
        if ("eee".equals(instruction) && "fff".equals(message)) {
            return new AdvancedResult<>(new TriggerBehavior<>(SampleTrigger.PAY));
        }
        return null;

    }

}
