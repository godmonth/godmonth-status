package com.godmonth.status.builder.advancer;

import com.godmonth.status.advancer.intf.AdvancedResult;
import com.godmonth.status.advancer.intf.StatusAdvancer;
import com.godmonth.status.annotations.AdvancerBindingAnnotation;
import com.godmonth.status.annotations.ModelBinding;
import com.godmonth.status.builder.domain.SampleModel;
import com.godmonth.status.builder.domain.SampleStatus;

/**
 * <p></p >
 *
 * @author shenyue
 */
@ModelBinding(SampleModel.class)
@AdvancerBindingAnnotation(statusClass = SampleStatus.class, statusValue = "CREATED")
public class PayAdvancer implements StatusAdvancer {

    @Override
    public AdvancedResult advance(Object o, Object instruction, Object message) {
        return null;
    }
}