package com.godmonth.status.test.sample2.entry;

import com.godmonth.status.annotations.StatusEntryBinding;
import com.godmonth.status.test.sample.domain.SampleModel;
import com.godmonth.status.test.sample.domain.SampleStatus;
import com.godmonth.status.transitor.tx.intf.StatusEntry;
import com.godmonth.status.transitor.tx.intf.TransitedResult;

/**
 * <p></p >
 *
 * @author shenyue
 */
@StatusEntryBinding(statusClass = SampleStatus.class, statusValue = "PAID")
public class EchoStatusEntry2 implements StatusEntry<SampleModel, Void> {
    @Override
    public void nextStatusEntry(TransitedResult<SampleModel, Void> transitedResult) {
        System.out.println(transitedResult.getModel());
    }
}
