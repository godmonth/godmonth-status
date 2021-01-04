package com.godmonth.status.test.sample.machine.advancer2;

import com.godmonth.status.advancer.intf.AdvanceRequest;
import com.godmonth.status.advancer.intf.AdvancedResult;
import com.godmonth.status.advancer.intf.StatusAdvancer2;
import com.godmonth.status.annotations.AdvancerBindingAnnotation;
import com.godmonth.status.annotations.ModelBinding;
import com.godmonth.status.test.sample.domain.SampleModel;
import com.godmonth.status.test.sample.domain.SampleStatus;
import com.godmonth.status.test.sample.machine.trigger.SampleTrigger;
import com.godmonth.status.test.sample.repo.SampleModelRepository;
import com.godmonth.status.transitor.tx.intf.TriggerBehavior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@ModelBinding(SampleModel.class)
@AdvancerBindingAnnotation(statusClass = SampleStatus.class, statusValue = "CREATED")
public class PayAdvancer implements StatusAdvancer2<SampleModel, String, SampleTrigger> {
    @Autowired
    private SampleModelRepository sampleModelRepository;

    @Value("${aaa.bbb}")
    private String value;

    @Override
    public AdvancedResult<SampleModel, SampleTrigger> advance(AdvanceRequest<SampleModel, String> advanceRequest)
            throws IllegalStateException {
        System.out.println(value);
        if ("eee".equals(advanceRequest.getInstruction()) && "fff".equals(advanceRequest.getMessage())) {
            return new AdvancedResult<>(new TriggerBehavior<>(SampleTrigger.PAY));
        }
        return null;

    }

}
