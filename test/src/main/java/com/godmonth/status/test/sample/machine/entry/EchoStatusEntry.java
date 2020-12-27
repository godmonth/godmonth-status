package com.godmonth.status.test.sample.machine.entry;

import com.godmonth.status.test.sample.domain.SampleModel;
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
        System.out.println("echo:" + transitedResult.getModel());
    }
}
