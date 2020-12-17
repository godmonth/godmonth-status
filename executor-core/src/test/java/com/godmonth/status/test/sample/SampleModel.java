package com.godmonth.status.test.sample;

import com.godmonth.status.annotations.Status;
import lombok.Data;

@Data
public class SampleModel {
    
    @Status
    private SampleStatus status;

    private String type;


}
