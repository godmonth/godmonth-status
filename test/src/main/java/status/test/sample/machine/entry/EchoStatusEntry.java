package status.test.sample.machine.entry;

import status.test.sample.domain.SampleModel;
import status.transitor.tx.intf.StatusEntry;
import status.transitor.tx.intf.TransitedResult;

/**
 * <p></p >
 *
 * @author shenyue
 */
public class EchoStatusEntry implements StatusEntry<SampleModel, Void> {
    @Override
    public void nextStatusEntry(TransitedResult<SampleModel, Void> transitedResult) {
        System.out.println(transitedResult.getModel());
    }
}
