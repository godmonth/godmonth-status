package status.advancer.impl;

import status.advancer.intf.AdvancedResult;
import status.test.sample.domain.SampleModel;
import status.test.sample.domain.SampleStatus;
import status.test.sample.domain.SampleTrigger;
import status.transitor.tx.intf.TriggerBehavior;

public class PayAdvancer extends AbstractAdvancer<SampleModel, Void, SampleTrigger> {
    {
        availableStatus = SampleStatus.CREATED;
    }

    @Override
    public AdvancedResult<SampleModel, SampleTrigger> advance(SampleModel model, Void instruction, Object message)
            throws IllegalStateException {
        System.out.println("advanced");
        return new AdvancedResult<>(new TriggerBehavior<>(SampleTrigger.PAY));
    }

}
