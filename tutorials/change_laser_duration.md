## Change laser duration step

Currently, the laser pulse duration is in microseconds. In order to change the step size, refer to the [laser_trigger.luc file](https://github.com/jdeschamps/MicroFPGA/blob/master/Au_firmware/source/laser_trigger.luc) of the firmware (line 35):

```verilog
const NM_CYCLES = 100; // convert to ~us
```

Since the Alchitry FPGAs have an internal clock of frequency 100MHz, each clock cycle takes about 1/100 us. If we wanted to have a step size for the pulse duration of 100 ns, then line 35 becomes:

```verilog
const NM_CYCLES = 10;
```

The firmware then needs to be compiled and updated on the FPGA.

## Change laser duration maximum

The laser pulse duration is encoded by a 16 bits value. The maximum value is therefore 65535 us pulse length by default. However, the register interface allows values up to 32 bits to be transferred. Therefore, the code can be easily modified to accommodate the whole bit depth, with a new maximum of 429'496'7295. If you follow the previous section with NM_CYCLES=1, this leads to a maximum pulse length of about 43 seconds.

1. In au_top.luc (line 102), change the bit depth to 32:

   ```verilog
   dff duration[NUM_LASERS][32];
   ```

2. In au_top.luc (line 157), change the bit depth to 32:

   ```verilog
   duration.d[reg.regOut.address-ADDR_DUR] = reg.regOut.data[31:0]; 
   ```
   
3. In laser_trigger.luc (line 23), change the bit depth to 32:

   ```verilog
   input dura[32],
   ```

Now, the code on the user side should be changed to accept higher values, for instance:

- [Micro-Manager](https://github.com/jdeschamps/MicroFPGA/blob/master/Device_Adapter/MicroFPGA.cpp): line 477, replace 65535 by 4294967295.
- [Java](https://github.com/jdeschamps/MicroFPGA/blob/master/MicroFPGA-Java/src/main/java/de/embl/rieslab/microfpga/devices/LaserTrigger.java): line 97, idem.
- [Python](https://github.com/jdeschamps/MicroFPGA/blob/master/MicroFPGA-Py/microfpga/signals.py): line 26, idem.

**Important note**: the error code for MicroFPGA is defined in au_top.luc (line 82):

```verilog
const ERROR_UNKNOW_COMMAND = 11206655; 
```

When changing any parameter from 16 bits to 32 bits, reading out the parameter value while it is equal to 11206655 will cause the Micro-Manager device adapter or the Java and Python libraries to produce an error. Indeed, this value will be interpreted as an wrong address request.
