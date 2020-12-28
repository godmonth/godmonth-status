package com.godmonth.status.test.sample.machine.advancer2;

import com.godmonth.status.advancer.impl.AbstractAdvancer;
import com.godmonth.status.advancer.intf.AdvancedResult;
import com.godmonth.status.test.sample.domain.SampleModel;
import com.godmonth.status.test.sample.domain.SampleStatus;
import com.godmonth.status.test.sample.machine.trigger.SampleTrigger;
import com.godmonth.status.test.sample.repo.SampleModelRepository;
import com.godmonth.status.transitor.tx.intf.TriggerBehavior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@SampleAdvancerBinding
public class PayAdvancer extends AbstractAdvancer<SampleModel, String, SampleTrigger> {
    @Autowired
    private SampleModelRepository sampleModelRepository;
    @Value("${aaa.bbb}")
    private String value;

    {
        availableStatus = SampleStatus.CREATED;
    }


    @Override
    public AdvancedResult<SampleModel, SampleTrigger> advance(SampleModel model, String instruction, Object message)
            throws IllegalStateException {
        System.out.println(value);
        if ("eee".equals(instruction) && "fff".equals(message)) {
            return new AdvancedResult<>(new TriggerBehavior<>(SampleTrigger.PAY));
        }
        return null;

    }

}
