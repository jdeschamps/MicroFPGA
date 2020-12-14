# Wiring

Before setting up the FPGA board on your microscope, make sure to plan properly the different inputs/outputs required. Here is a rough outline of the step needed to set-up MicroFPGA physically:

1. List the MicroFPGA signals that you intend on using.

2. List the upstream/downstream devices that correspond to these signals.

3. Make sure that you know the voltage requirements of the devices. For instance, pay attention to the following:

   - Camera signal: EMCCD typically have a 5 V trigger output, while sCMOS cameras have a 3.3 V one.
   - Servomotor: does the servomotor signal corresponds to the common PWM with 20 ms period and pulses between 1 ms and 2 ms.
   - Analog signal input: what are the voltage limits of this signal.
   - Analog signal output: what voltage is necessary.

   Note that in general 3.3 V TTL signals do not need to be scaled up to 5 V and should work directly. That should be the case for instance for the laser trigger and servomotors. 

4. Depending on the previous points, you might need to build or order the [complementary electronics](electronics.md) or equivalent systems. For a reminder:

   - SCB:
     - When building or ordering the board, you can choose a different voltage for the third channel (not 3.3 V or 5 V) by changing the resistors, see [this page](electronics.md).
     - The camera signal can be converted from 5 V to 3.3. V before the FPGA board.
     - A PWM signal can be converted to an analog signal with a maximum voltage of 3.3 V, 5 V, or your third voltage (see first bullet point).
   - ACB:
     - 0-10 V or 0-5 V analog signals can be converted to 0-1 V before the FPGA board.

   Refer to [the electronics section](electronics.md) for how to configure the complementary boards.

5. If you use servomotors, especially heavier duty ones, you will need to power them with 5 V. We discourage powering the servos from the FPGA boards themselves. Therefore use an external 5 V power supply, solder it to the FPGA board and make a derivation to the servomotors. We find it easier to have an intermediary [servomotor distribution board](https://github.com/ries-lab/RiesPieces/tree/master/Electronics/Servo_distribution) that can be placed away from the FPGA and have short-length connections to the servos.

6. Solder pins rows on the Br break out board on the [pins corresponding to the signals you are interested in](pins_br.md). We advise using male headers to insure more stability.

7. Wire the complementary electronics with your microscope devices and test the board outputs that are supposed to go to the FPGA board. Make sure that they do not exceed 3.3 V for the digital signals and 1 V for the analogs.

8. Wire the inputs to the FPGA board.

9. Power the board and set-up the computer control.

10. Wire the FPGA board outputs to the complementary boards and measure the outputs. Play with the parameters to insure that the signals are correct.

11. Measure the direct outputs from the FPGA board to the microscope devices.

