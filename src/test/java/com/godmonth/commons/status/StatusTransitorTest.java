package com.godmonth.commons.status;

import java.util.Collections;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.godmonth.commons.status.impl.transitor.TransientStatusTransitor;
import com.godmonth.commons.status.impl.transitor.TransitionConfig;
import com.godmonth.commons.status.impl.transitor.TransitionConfigMapFactory;
import com.godmonth.commons.status.impl.transitor.TriggerKey;
import com.godmonth.commons.status.intf.TriggerBehavior;
import com.godmonth.commons.status.sample.Activity;
import com.godmonth.commons.status.sample.ActivityStatus;
import com.godmonth.commons.status.sample.ActivityStatusMachine;
import com.godmonth.commons.status.sample.ActivityTrigger;

public class StatusTransitorTest {
	private TransientStatusTransitor<ActivityTrigger, ActivityStatus, Activity, IllegalStateException> statusTransitor = createTransitor();

	public static TransientStatusTransitor<ActivityTrigger, ActivityStatus, Activity, IllegalStateException> createTransitor() {
		Map<TriggerKey<ActivityStatus, ActivityTrigger>, TransitionConfig<ActivityStatus, ActivityTrigger, Activity, IllegalStateException>> transitionConfigMap = TransitionConfigMapFactory
				.createByStatusMachine(ActivityStatusMachine.INSTANCE,
						Collections.singletonMap(ActivityStatus.ENROLLED, StatusTransitorTest::statusEntry),
						Collections.singletonMap(ActivityStatus.CREATED, StatusTransitorTest::statusExit));
		TransientStatusTransitor<ActivityTrigger, ActivityStatus, Activity, IllegalStateException> statusTransitor = new TransientStatusTransitor<>();
		statusTransitor.setTransitionConfigMap(transitionConfigMap);
		statusTransitor.setStatusPropertyName("status");
		return statusTransitor;
	}

	public static void statusEntry(Activity activity) {
		System.out.println("statusEntry," + activity.getStatus());
	}

	public static void statusExit(Activity activity) {
		System.out.println("statusExit," + activity.getStatus());
	}

	@Test
	public void enroll() {
		Activity model = new Activity();
		model.setStatus(ActivityStatus.CREATED);
		Activity activity = statusTransitor.transit(model, new TriggerBehavior<>(ActivityTrigger.ENROLL));
		Assert.assertEquals(activity.getStatus(), ActivityStatus.ENROLLED);
	}

	@Test
	public void jump() {
		Activity model = new Activity();
		model.setStatus(ActivityStatus.CREATED);
		Activity activity = statusTransitor.transit(model, new TriggerBehavior<>(ActivityTrigger.JUMP));
		Assert.assertEquals(activity.getStatus(), ActivityStatus.CHOSEN);
	}

	@Test
	public void testFailure() {
		Activity model = new Activity();
		model.setStatus(ActivityStatus.CREATED);
		try {
			statusTransitor.transit(model, new TriggerBehavior<>(ActivityTrigger.BAD));
			Assert.fail();
		} catch (IllegalArgumentException e) {
		}
	}
}
