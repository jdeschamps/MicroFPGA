package de.embl.rieslab.microfpga.devices;

import de.embl.rieslab.microfpga.MicroFPGAController;
import de.embl.rieslab.microfpga.regint.RegisterInterface;

public class LaserTrigger {

	private final int id_;
	private final Mode mode_;
	private final Duration duration_;
	private final Sequence sequence_;
	
	protected LaserTrigger(int id, RegisterInterface regint) {
		id_ = id;

		mode_ = new Mode(id_, regint);
		duration_ = new Duration(id_, regint);
		sequence_ = new Sequence(id_, regint);
	}

	public boolean setMode(int value) {
		return mode_.setState(value);
	}
	
	public int getMode() {
		return mode_.getState();
	}
	
	public boolean setDuration(int value) {
		return duration_.setState(value);
	}
	
	public int getDuration() {
		return duration_.getState();
	}
	
	public boolean setSequence(int value) {
		return sequence_.setState(value);
	}
	
	public int getSequence() {
		return sequence_.getState();
	}
	
	/**
	 * Takes a binary string composed of 16 characters, either 0 or 1, and converts 
	 * it to integer. 
	 * 
	 * @param s Binary string composed of 0 or 1 and of length 16.
	 * @return Integer value, or -1 if the string is not a binary string or not of length 16.
	 */
	public static int formatSequence(String s) {
		if(!isBits(s) || s.length() != 16){
			return -1;
		}

		return Integer.parseInt(s, 2);
	}
	
	public static boolean isBits(String s){	
		for(int i=0;i<s.length();i++){
			if(!s.substring(i, i+1).equals("0") && !s.substring(i, i+1).equals("1") ){
				return false;
			}
		}
		return true;
	}
	
	public class Mode extends Signal{

		public static final int MAX = 4;
		public static final int MIN = 0;

		public static final int MODE_OFF = 0;
		public static final int MODE_ON = 1;
		public static final int MODE_RISING = 2;
		public static final int MODE_FALLING = 3;
		public static final int MODE_CAMERA = 4;
		
		protected Mode(int id, RegisterInterface regint) {
			super(id, Direction.OUTPUT, regint);
		}

		@Override
		public boolean isValueAllowed(int i) {
			return (i>= MIN && i<=MAX);
		}

		@Override
		public int getBaseAddress() {
			return MicroFPGAController.ADDR_MODE;
		}
	}
	
	public class Duration extends Signal{

		public static final int MAX = 65535;
		public static final int MIN = 0;
		
		protected Duration(int id, RegisterInterface regint) {
			super(id, Direction.OUTPUT, regint);
		}

		@Override
		public boolean isValueAllowed(int i) {
			return (i>= MIN && i<=MAX);
		}

		@Override
		public int getBaseAddress() {
			return MicroFPGAController.ADDR_DURA;
		}
	}
	
	public class Sequence extends Signal{

		public static final int MAX = 65535;
		public static final int MIN = 0;
		
		protected Sequence(int id, RegisterInterface regint) {
			super(id, Direction.OUTPUT, regint);
		}

		@Override
		public boolean isValueAllowed(int i) {
			return (i>= MIN && i<=MAX);
		}

		@Override
		public int getBaseAddress() {
			return MicroFPGAController.ADDR_SEQ;
		}
	}
}
