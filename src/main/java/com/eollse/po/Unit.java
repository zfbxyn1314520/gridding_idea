package com.eollse.po;

import java.io.Serializable;

public class Unit implements Serializable {
	private Integer unitId;
	private Integer blockId;
	private String unitName;
	private Integer floorCount;
	private Block block;

	public Unit() {
		super();
		this.block = new Block();
	}

	public Unit(Integer unitId, Integer blockId, String unitName,
			Integer floorCount, Block block) {
		super();
		this.unitId = unitId;
		this.blockId = blockId;
		this.unitName = unitName;
		this.floorCount = floorCount;
		this.block = block;
	}

	@Override
	public String toString() {
		return "Unit [unitId=" + unitId + ", blockId=" + blockId
				+ ", unitName=" + unitName + ", floorCount=" + floorCount
				+ ", block=" + block + "]";
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public Integer getUnitId() {
		return unitId;
	}

	public void setUnitId(Integer unitId) {
		this.unitId = unitId;
	}

	public Integer getBlockId() {
		return blockId;
	}

	public void setBlockId(Integer blockId) {
		this.blockId = blockId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Integer getFloorCount() {
		return floorCount;
	}

	public void setFloorCount(Integer floorCount) {
		this.floorCount = floorCount;
	}

}
