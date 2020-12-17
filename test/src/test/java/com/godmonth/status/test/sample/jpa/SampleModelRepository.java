package com.godmonth.status.test.sample.jpa;

import com.godmonth.status.test.sample.SampleModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleModelRepository extends CrudRepository<SampleModel, Long> {


}
