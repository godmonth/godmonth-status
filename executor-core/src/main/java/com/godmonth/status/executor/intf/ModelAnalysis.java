package com.godmonth.status.executor.intf;

public interface ModelAnalysis<MODEL, STATUS> {
	void validate(MODEL model);

	STATUS getStatus(MODEL model);

}
