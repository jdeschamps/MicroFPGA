import microfpga.controller as ctrl
import microfpga.signals as sgnl

# creates a MicroFPGA controller
num_lasers = 3;
num_ttl = 2;
num_servos = 3;
num_pwm = 1;
num_ai = 2; # will only work with Au FPGA

controller = ctrl.MicroFPGA(num_lasers, num_ttl, num_servos, num_pwm, num_ai)

# checks if successful
print('Connected to '+controller.get_id())

# All signals can be accessed using the controller getters and setters.
# Channel indexing starts at 0: if num_ttl = 2, then there are TTL 0 and
# TTL 1. 
               
# gets current Servo 1 state (if the FPGA was just recently powered up then default values are 0)
servo_id = 1
print('Current Servo '+str(servo_id)+' position: '+str(controller.get_servo_state(servo_id)));
                
# moves Servo 1 to position 35412
servo_pos = 35412
b = controller.set_servo_state(servo_id, servo_pos);
if not b:
    print('Failed to write position to Servo 1')
                
# gets current Servo 1 state
print('Current Servo '+str(servo_id)+' position: '+str(controller.get_servo_state(servo_id)));
                
# For lasers, the parameters can be changed individually...
laser_id = 2
new_mode = sgnl.LaserTrigger.MODE_RISING;
new_duration = 2000; # us
new_sequence = sgnl.format_sequence('1010101010101010'); # binary string of length 16

controller.set_mode_state(laser_id, new_mode);  
print('Current Laser '+str(laser_id)+' mode state: '+str(controller.get_mode_state(laser_id)));

controller.set_duration_state(laser_id, new_duration);  
print('Current Laser '+str(laser_id)+' duration state: '+str(controller.get_duration_state(laser_id)));

controller.set_sequence_state(laser_id, new_sequence);   
print('Current Laser '+str(laser_id)+' sequence state: '+str(controller.get_sequence_state(laser_id)));             
                
# ... or in bulk
new_mode = sgnl.LaserTrigger.MODE_CAMERA;
new_duration = 30; # us
new_sequence = sgnl.format_sequence('1100110011001100'); # binary string of length 16

controller.set_laser_state(laser_id, new_mode, new_duration, new_sequence);  
vals = controller.get_laser_state(laser_id)  
print('Current Laser '+str(laser_id)+' state: '+str(vals[0])+', '+str(vals[1])+', '+str(vals[2]));
                                
# disconnect
controller.disconnect()
print('Disconnected')