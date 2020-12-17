package com.godmonth.status.test.executor;

import com.godmonth.status.test.sample.SampleModel;
import com.godmonth.status.transitor.tx.intf.StatusEntry;
import com.godmonth.status.transitor.tx.intf.TransitedResult;

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
