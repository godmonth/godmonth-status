package status.advancer.intf;

/**
 * 状态推进器
 * 
 * @author shenyue
 *
 * @param <MODEL>
 *            MODEL
 */
public interface StatusAdvancer<MODEL, INST, TRIGGER> {
	AdvancedResult<MODEL, TRIGGER> advance(MODEL model, INST instruction, Object message);

	Object getKey();
}
