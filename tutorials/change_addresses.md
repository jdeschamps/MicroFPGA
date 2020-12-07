# Changing addresses

Addresses in the MicroFPGA [register Interface](register_interface.md) are defined in the [top file](https://github.com/jdeschamps/MicroFPGA/blob/master/Au_firmware/source/au_top.luc) of the Alchitry Labs project. In order to modify them in the firmware, change the address constants (line 87 to line 93):

```verilog
// number of signals
const NUM_LASERS = 10;
const NUM_TTL = 5;
const NUM_PWM = 5;
const NUM_SERVOS = 7;
const NUM_ANALOG = 8;

// addresses
const ADDR_MODE = 0; 
const ADDR_DUR = NUM_LASERS; 
const ADDR_SEQ = ADDR_DUR+NUM_LASERS;
const ADDR_TTL = ADDR_SEQ+NUM_LASERS; 
const ADDR_SERVOS = ADDR_TTL+NUM_TTL; 
const ADDR_PWM = ADDR_SERVOS+NUM_SERVOS;
const ADDR_AI = ADDR_PWM+NUM_PWM; // Au specific

const ADDR_VERSION = 100;
const ADDR_ID = 101;
```

Since all the addresses follow each other and are dependent on the number of signals, the if-else conditions (lines 167 to 217) are valid in the current implementation. After changes, this might not be the case. Make sure there is no overlap between the different signal addresses, and that the fixed addresses (version and id) are not defined for one of the signals. Finally, compile the firmware and upload it to the FPGA (see [build and update the FPGA firmware](installing_microfpga.md) for a walkthrough).

Note that if you are using [Micro-Manager](using_micro-manager.md), the [Java](using_java.md) or the [Python](using_python.md) libraries, you will have to modify the addresses in the user code as well. The addresses are defined in a similar fashion in the following files:

- [Micro-Manager device adapter](https://github.com/jdeschamps/MicroFPGA/blob/master/Device_Adapter/MicroFPGA.cpp): line 43 to 49.
- [Python library](https://github.com/jdeschamps/MicroFPGA/blob/master/MicroFPGA-Py/microfpga/signals.py): line 10 to 16.
- [Java library](https://github.com/jdeschamps/MicroFPGA/blob/master/MicroFPGA-Java/src/main/java/de/embl/rieslab/microfpga/MicroFPGAController.java): line 21 to 27.

