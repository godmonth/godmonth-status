package com.godmonth.commons.status;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.godmonth.commons.status.impl.executor.DefaultOrderExecutor;
import com.godmonth.commons.status.impl.transitor.TransientStatusTransitor;
import com.godmonth.commons.status.intf.OrderExecutor;
import com.godmonth.commons.status.intf.StatusAdvancer;
import com.godmonth.commons.status.intf.SyncResult;
import com.godmonth.commons.status.sample.Activity;
import com.godmonth.commons.status.sample.ActivityInstruction;
import com.godmonth.commons.status.sample.ActivityStatus;
import com.godmonth.commons.status.sample.ActivityTrigger;
import com.godmonth.commons.status.sample.advancer.EnrollAdvancer;

public class OrderExecutorTest {

	private OrderExecutor<Activity, ActivityInstruction, IllegalStateException> orderExecutor;

	@BeforeClass
	public void prepare() {

		TransientStatusTransitor<ActivityTrigger, ActivityStatus, Activity, IllegalStateException> statusTransitor = StatusTransitorTest
				.createTransitor ();

		Map<Object, StatusAdvancer<Activity, ActivityInstruction, ActivityTrigger, IllegalStateException>> advancerMappings = new HashMap<>();

		EnrollAdvancer enrollAdvancer = new EnrollAdvancer();

		advancerMappings.put(enrollAdvancer.getKey(), enrollAdvancer);

		DefaultOrderExecutor<Activity, ActivityInstruction, ActivityTrigger, IllegalStateException> defaultOrderExecutor = new DefaultOrderExecutor<>();
		defaultOrderExecutor.setStatusPropertyName("status");
		defaultOrderExecutor.setAdvancerMappings(advancerMappings);
		defaultOrderExecutor.setStatusTransitor(statusTransitor);
		orderExecutor = defaultOrderExecutor;
	}

	@Test
	public void test() {
		Activity activity = new Activity();
		activity.setStatus(ActivityStatus.CREATED);

		SyncResult<Activity, ?> execute = orderExecutor.execute(activity, null, null);
		System.out.println(execute.getModel());
	}

}
