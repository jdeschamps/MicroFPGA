package de.embl.rieslab.microfpga.devices;

import de.embl.rieslab.microfpga.regint.RegisterInterface;

public abstract class Signal {

	private final Direction dir_;
	private final int id_;
	private final RegisterInterface regint_;
	
	protected Signal(int id, Direction dir, RegisterInterface regint) {
		id_ = id;
		dir_ = dir;
		regint_ = regint;
	}
	
	public Direction getDirection() {
		return dir_;
	}
	
	public int getID() {
		return id_;
	}
	
	public boolean setState(int state) {
		if(getDirection() == Direction.OUTPUT && isValueAllowed(state)) {
			return regint_.write(getBaseAddress()+getID(), state);
		}
		return false;
	}
	
	public int getState() {
		return regint_.read(getBaseAddress() + getID());
	}
	
	public abstract int getBaseAddress();
	
	public abstract boolean isValueAllowed(int i);
	
	protected enum Direction {
		INPUT,
		OUTPUT
	}
}
