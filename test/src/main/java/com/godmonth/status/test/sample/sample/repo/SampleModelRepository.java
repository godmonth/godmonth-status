package com.godmonth.status.test.sample.sample.repo;

import com.godmonth.status.test.sample.sample.domain.SampleModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleModelRepository extends CrudRepository<SampleModel, Long> {


}
