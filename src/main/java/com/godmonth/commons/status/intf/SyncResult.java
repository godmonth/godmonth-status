package com.godmonth.commons.status.intf;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 同步结果
 * 
 * @author shenyue
 *
 * @param <SYMBOL>
 */
public class SyncResult<MODEL, SYMBOL> {
	private MODEL model;

	private SYMBOL symbol;

	private Object value;

	public SyncResult() {
	}

	public SyncResult(MODEL model) {
		this.model = model;
	}

	public SyncResult(MODEL model, SYMBOL symbol) {
		this.model = model;
		this.symbol = symbol;
	}

	public SyncResult(MODEL model, SYMBOL symbol, Object value) {
		this.model = model;
		this.symbol = symbol;
		this.value = value;
	}

	public SYMBOL getSymbol() {
		return symbol;
	}

	public void setSymbol(SYMBOL symbol) {
		this.symbol = symbol;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public MODEL getModel() {
		return model;
	}

	public void setModel(MODEL model) {
		this.model = model;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("symbol", this.symbol)
				.append("model", this.model).append("value", this.value).toString();
	}

}
