package com.godmonth.status.builder.advancer;

import com.godmonth.status.advancer.intf.AdvancerBinding;
import com.godmonth.status.advancer.intf.StatusAdvancer;

import java.util.ArrayList;
import java.util.List;

/**
 * <p></p >
 *
 * @author shenyue
 */
public class OldAdvancerConverter {

    public static List<AdvancerBinding> convert2Binding(List<StatusAdvancer> advancerList) {
        List<AdvancerBinding> advancerBindingList = new ArrayList<>();
        for (StatusAdvancer statusAdvancer : advancerList) {
            AdvancerBinding advancerBinding = new AdvancerBinding();
            advancerBinding.setKey(statusAdvancer.getKey());
            advancerBinding.setStatusAdvancer(statusAdvancer);
            advancerBindingList.add(advancerBinding);
        }
        return advancerBindingList;
    }

}
