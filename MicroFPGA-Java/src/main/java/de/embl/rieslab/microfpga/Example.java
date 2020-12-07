package de.embl.rieslab.microfpga;


import de.embl.rieslab.microfpga.devices.LaserTrigger;

/**
 * 
 * @author Joran Deschamps
 *
 */
public class Example {
	
	public static void main(String[] args) {
		
		// creates a MicroFPGA controller
		int num_lasers = 3;
		int num_ttl = 2;
		int num_servos = 3;
		int num_pwm = 1;
		int num_ai = 2; // will only work with Au FPGA

		try {
			// connects to the FPGA
			MicroFPGAController controller = new MicroFPGAController(num_lasers, num_ttl, num_servos, num_pwm, num_ai);

			// prints ID (Au or Cu)
			System.out.println("Connected to " + controller.getID());

			/*
			 * All signals can be accessed using the controller getters and setters. Channel
			 * indexing starts at 0: if num_ttl = 2, then there are TTL 0 and TTL 1.
			 */

			// gets current Servo 1 state (if the FPGA was powered up then default values
			// are 0)
			int servo_id = 1;
			System.out.println("Current Servo " + servo_id + " position: " + controller.getServoState(servo_id));

			// moves Servo 1 to new position
			int new_position = 35412;
			boolean b = controller.setServoState(servo_id, new_position);
			if (!b) {
				System.out.println("Failed to write state to Servo " + servo_id);
			}

			// gets current Servo 1 state
			System.out.println("Current Servo " + servo_id + " position: " + controller.getServoState(servo_id));

			/*
			 * For lasers, the parameters can be changed individually...
			 */
			int laser_id = 2;
			int new_mode = LaserTrigger.Mode.MODE_RISING;
			int new_duration = 2000; // us
			int new_sequence = LaserTrigger.formatSequence("1010101010101010"); // binary string of length 16

			b = controller.setLaserModeState(laser_id, new_mode);
			if (!b) {
				System.out.println("Failed to write mode to Laser " + laser_id);
			}
			System.out.println("Current Laser " + laser_id + " mode: " + controller.getLaserModeState(laser_id));

			controller.setLaserDurationState(laser_id, new_duration);
			System.out.println("Current Laser " + laser_id + " duration: " + controller.getLaserDurationState(laser_id));

			controller.setLaserSequenceState(laser_id, new_sequence);
			System.out.println("Current Laser " + laser_id + " sequence: " + controller.getLaserSequenceState(laser_id));

			/*
			 * ... or in bulk
			 */
			new_mode = LaserTrigger.Mode.MODE_CAMERA;
			new_duration = 30; // us
			new_sequence = LaserTrigger.formatSequence("1100110011001100"); // binary string of length 16
			controller.setLaserState(laser_id, new_mode, new_duration, new_sequence);
			int[] state = controller.getLaserState(laser_id);
			System.out.println("Current Laser " + laser_id + " state: " + state[0] + ", " + state[1] + ", " + state[2]);

			// disconnects from the port
			controller.disconnect();
			System.out.println("Disconnected");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
