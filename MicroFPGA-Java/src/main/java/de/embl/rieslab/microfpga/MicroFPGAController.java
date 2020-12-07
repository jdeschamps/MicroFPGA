package de.embl.rieslab.microfpga;

import java.util.ArrayList;

import de.embl.rieslab.microfpga.devices.AnalogInput;
import de.embl.rieslab.microfpga.devices.DeviceFactory;
import de.embl.rieslab.microfpga.devices.LaserTrigger;
import de.embl.rieslab.microfpga.devices.PWM;
import de.embl.rieslab.microfpga.devices.Servo;
import de.embl.rieslab.microfpga.devices.TTL;
import de.embl.rieslab.microfpga.regint.RegisterInterface;

public class MicroFPGAController {

	public final static int MAX_TTL = 5;
	public final static int MAX_PWM = 5;
	public final static int MAX_LASER = 8;
	public final static int MAX_SERVO = 7;
	public final static int MAX_AI = 8;
	
	public final static int ADDR_MODE = 0;
	public static final int ADDR_DURA = ADDR_MODE+MAX_LASER;
	public static final int ADDR_SEQ = ADDR_DURA+MAX_LASER;
	public static final int ADDR_TTL = ADDR_SEQ+MAX_LASER;
	public static final int ADDR_SERVO = ADDR_TTL+MAX_TTL;
	public static final int ADDR_PWM = ADDR_SERVO+MAX_SERVO;
	public static final int ADDR_ANALOG_INPUT = ADDR_PWM+MAX_PWM;
	
	public static final int ADDR_VERSION = 100;
	public static final int ADDR_ID = 101;
	
	public static final int ERROR_UNKNOWN_COMMAND = 11206655;

	public static final int ID_AU = 79;
	public static final int ID_CU = 29;
	public static final int CURRENT_VERSION = 2;
	
	private ArrayList<TTL> ttls_;
	private ArrayList<PWM> pwms_;
	private ArrayList<LaserTrigger> lasers_;
	private ArrayList<Servo> servos_;
	private ArrayList<AnalogInput> ais_;
	
	private boolean connected_;
	
	private final RegisterInterface regint_;
	private final int id_;
	private final int version_;
	
	public MicroFPGAController(int nLasers, int nTTLs, int nServos, int nPWMs, int nAIs) throws Exception {

		regint_ = new RegisterInterface();

		// attempts to connect to the interface
		connected_ = regint_.connect();
		
		if(connected_){
			version_  = regint_.read(ADDR_VERSION);
			id_ = regint_.read(ADDR_ID);
			
			if(CURRENT_VERSION == version_ && ( (id_ == ID_AU) || (id_ == ID_CU) ) ) {
				DeviceFactory factory = new DeviceFactory(regint_);
				
				ttls_ = new ArrayList<TTL>();
				pwms_ = new ArrayList<PWM>();
				lasers_ = new ArrayList<LaserTrigger>();
				servos_ = new ArrayList<Servo>();
				ais_ = new ArrayList<AnalogInput>();
		
				for(int i=0;i<nTTLs;i++) {
					ttls_.add(factory.getTTL());
				}
				for(int i=0;i<nPWMs;i++) {
					pwms_.add(factory.getPWM());
				}
				for(int i=0;i<nLasers;i++) {
					lasers_.add(factory.getLaser());
				}
				for(int i=0;i<nServos;i++) {
					servos_.add(factory.getServo());
				}

				if ((id_ == ID_AU)) {
					for (int i = 0; i < nAIs; i++) {
						ais_.add(factory.getAI());
					}
				}
			} else {
				regint_.disconnect();
				
				if(CURRENT_VERSION != version_) {
					throw new Exception("Incorrect firmware version ("+version_+"), expected version 2.");
				}
				if(( (id_ != ID_AU) && (id_ != ID_CU) ) ) {
					throw new Exception("Unknown device id ("+id_+"), expected version 49 (Cu) or 79 (Au).");
				}
			}
		} else {
			throw new Exception("Could not find device or could not connect.");
		}
	}

	public void disconnect() {
		connected_ = !regint_.disconnect();
	}
	
	public boolean isConnected() {
		return connected_;
	}
	
	public int getNumberTTLs() {
		return ttls_.size();
	}
	public int getNumberPWMs() {
		return pwms_.size();
	}
	public int getNumberLasers() {
		return lasers_.size();
	}
	public int getNumberServos() {
		return servos_.size();
	}
	public int getNumberAIs() {
		return ais_.size();
	}
	
	public boolean setTTLState(int channel, int state) {
		if(connected_ && channel >= 0 && channel < getNumberTTLs()) {
			return ttls_.get(channel).setState( state == TTL.ON ? TTL.ON : TTL.OFF );
		}
		return false;
	}
	
	public int getTTLState(int channel) {
		if(connected_ && channel >= 0 && channel < getNumberTTLs()) {
			return ttls_.get(channel).getState();
		}
		return -1;
	}
	
	public boolean setPWMState(int channel, int state) {
		if(connected_ && channel >= 0 && channel < getNumberPWMs()) {
			return pwms_.get(channel).setState( state );
		}
		return false;
	}
	
	public int getPWMState(int channel) {
		if(connected_ && channel >= 0 && channel < getNumberPWMs()) {
			return pwms_.get(channel).getState();
		}
		return -1;
	}
	
	public boolean setServoState(int channel, int state) {
		if(connected_ && channel >= 0 && channel < getNumberServos()) {
			return servos_.get(channel).setState( state );
		}
		return false;
	}

	public int getServoState(int channel) {
		if(connected_ && channel >= 0 && channel < getNumberServos()) {
			return servos_.get(channel).getState();
		}
		return -1;
	}

	public boolean setLaserState(int channel, int mode, int duration, int sequence) {
		if(connected_ && channel >= 0 && channel < getNumberLasers()) {
			LaserTrigger lt = lasers_.get(channel);
			
			boolean b;
			b = lt.setMode(mode);
			if(!b)
				return b;
			
			b = lt.setDuration(duration);
			if(!b)
				return b;
			
			b = lt.setSequence(sequence);
			return b;
		}
		return false;
	}
	
	public int[] getLaserState(int channel) {
		if(connected_ && channel >= 0 && channel < getNumberLasers()) {
			LaserTrigger lt = lasers_.get(channel);
			
			return new int[] {lt.getMode(), lt.getDuration(), lt.getSequence()};
		}
		return new int[] {-1, -1, -1};
	}

	public boolean setLaserModeState(int channel, int state) {
		if(connected_ && channel >= 0 && channel < getNumberLasers()) {
			return lasers_.get(channel).setMode(state);
		}
		return false;
	}
	
	public int getLaserModeState(int channel) {
		if(connected_ && channel >= 0 && channel < getNumberLasers()) {
			return lasers_.get(channel).getMode();
		}
		return -1;
	}
	
	public boolean setLaserDurationState(int channel, int state) {
		if(connected_ && channel >= 0 && channel < getNumberLasers()) {
			return lasers_.get(channel).setDuration(state);
		}
		return false;
	}
	
	public int getLaserDurationState(int channel) {
		if(connected_ && channel >= 0 && channel < getNumberLasers()) {
			return lasers_.get(channel).getDuration();
		}
		return -1;
	}
	
	
	public boolean setLaserSequenceState(int channel, int state) {
		if(connected_ && channel >= 0 && channel < getNumberLasers()) {
			return lasers_.get(channel).setSequence(state);
		}
		return false;
	}
	
	public int getLaserSequenceState(int channel) {
		if(connected_ && channel >= 0 && channel < getNumberLasers()) {
			return lasers_.get(channel).getSequence();
		}
		return -1;
	}

	public String getID() {
		if(connected_) {
			return (id_ == ID_AU) ? "Au" : "Cu";
		}
		return "-1";
	}
	
}
