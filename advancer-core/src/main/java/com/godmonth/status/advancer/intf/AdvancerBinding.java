package com.godmonth.status.advancer.intf;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvancerBinding {

    protected Object key;

    protected StatusAdvancer2 statusAdvancer;

}
