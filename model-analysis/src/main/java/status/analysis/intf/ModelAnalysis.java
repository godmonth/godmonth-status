package status.analysis.intf;

/**
 * 升级，去掉status泛型
 *
 * @param <MODEL>
 */
public interface ModelAnalysis<MODEL> {
    void validate(MODEL model);

    <STATUS> STATUS getStatus(MODEL model);

    <STATUS> void setStatus(MODEL model, STATUS value);

    String getStatusPropertyName();
}
