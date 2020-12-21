package status.test.sample.machine.advancer;

import status.advancer.impl.AbstractAdvancer;
import status.advancer.intf.AdvancedResult;
import status.test.sample.domain.SampleModel;
import status.test.sample.domain.SampleStatus;
import status.test.sample.machine.trigger.SampleTrigger;
import status.transitor.tx.intf.TriggerBehavior;

public class PayAdvancer extends AbstractAdvancer<SampleModel, String, SampleTrigger> {
    {
        availableStatus = SampleStatus.CREATED;
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
