package status.test.sample.machine.advancer;

import status.advancer.impl.AbstractAdvancer;
import status.advancer.intf.AdvancedResult;
import status.test.sample.domain.SampleModel;
import status.test.sample.domain.SampleStatus;
import status.test.sample.machine.trigger.SampleTrigger;

public class CheckAdvancer extends AbstractAdvancer<SampleModel, String, SampleTrigger> {
    {
        availableStatus = SampleStatus.PAID;
    }

    @Override
    public AdvancedResult<SampleModel, SampleTrigger> advance(SampleModel model, String instruction, Object message)
            throws IllegalStateException {
        return null;
    }

}
