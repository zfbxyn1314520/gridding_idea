package com.eollse.po;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Flow implements Serializable {
	private Integer flowId;
	private Integer popId;
	private Integer flowTypeId;
	private String flowCause;
	@DateTimeFormat( pattern = "yyyy年MM月dd日" )
	private Date flowTime;
	private String inflowSite;
	private String outflowSite;
	private Flow_type flowType;
	private Population population;

	public Flow() {
		super();
		this.flowType = new Flow_type();
		this.population = new Population();
	}

	public Flow(Integer flowId, Integer popId, Integer flowTypeId,
			String flowCause, Date flowTime, String inflowSite,
			String outflowSite, Flow_type flowType, Population population) {
		super();
		this.flowId = flowId;
		this.popId = popId;
		this.flowTypeId = flowTypeId;
		this.flowCause = flowCause;
		this.flowTime = flowTime;
		this.inflowSite = inflowSite;
		this.outflowSite = outflowSite;
		this.flowType = flowType;
		this.population = population;
	}
	

	public Flow(Population population) {
		super();
		this.population = population;
	}

	@Override
	public String toString() {
		return "Flow [flowId=" + flowId + ", popId=" + popId + ", flowTypeId="
				+ flowTypeId + ", flowCause=" + flowCause + ", flowTime="
				+ flowTime + ", inflowSite=" + inflowSite + ", outflowSite="
				+ outflowSite + ", flowType=" + flowType + ", population="
				+ population + "]";
	}

	public Flow_type getFlowType() {
		return flowType;
	}

	public void setFlowType(Flow_type flowType) {
		this.flowType = flowType;
	}

	public Population getPopulation() {
		return population;
	}

	public void setPopulation(Population population) {
		this.population = population;
	}

	public Integer getFlowId() {
		return flowId;
	}

	public void setFlowId(Integer flowId) {
		this.flowId = flowId;
	}

	public Integer getPopId() {
		return popId;
	}

	public void setPopId(Integer popId) {
		this.popId = popId;
	}

	public Integer getFlowTypeId() {
		return flowTypeId;
	}

	public void setFlowTypeId(Integer flowTypeId) {
		this.flowTypeId = flowTypeId;
	}

	public String getFlowCause() {
		return flowCause;
	}

	public void setFlowCause(String flowCause) {
		this.flowCause = flowCause;
	}

	public Date getFlowTime() {
		return flowTime;
	}

	public void setFlowTime(Date flowTime) {
		this.flowTime = flowTime;
	}

	public String getInflowSite() {
		return inflowSite;
	}

	public void setInflowSite(String inflowSite) {
		this.inflowSite = inflowSite;
	}

	public String getOutflowSite() {
		return outflowSite;
	}

	public void setOutflowSite(String outflowSite) {
		this.outflowSite = outflowSite;
	}

}
