package com.godmonth.status.test.sample;

import com.godmonth.status.executor.intf.Status;
import lombok.Data;

@Data
public class SampleModel {
    
    @Status
    private SampleStatus status;

    private String type;


}
