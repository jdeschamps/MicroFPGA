# Changing the baud rate

The baud rate is a central parameter of serial communication and corresponds to the rate at which information is transferred between the computer and the FPGA. If the wrong baud rate is used for attempted communication, the communication will fail.

The default baud rate of MicroFPGA is **100000000**. Some programs, such as Micro-Manager version 1.4, are not capable of such a high baud rate and the FPGA must then be configured for slower baud rates. If used, the Java and Python libraries also need to be updated.

### Changing the FPGA baud rate

In [au_top.luc](https://github.com/jdeschamps/MicroFPGA/blob/master/Au_firmware/source/au_top.luc) (line 106), change the value "100000000" to the relevant baud rate to your application (here for instance with 9600):

```verilog
uart_rx rx (#BAUD(9600), #CLK_FREQ(100000000)); // serial receiver
uart_tx tx (#BAUD(9600), #CLK_FREQ(100000000)); // serial transmitter
```

Compile the firmware and update the FPGA (see [Installing and building MicroFPGA](installing_microfpga.md) for a walkthrough).

### Changing the baud rate in the Java and Python libraries

In the Java [register interface](https://github.com/jdeschamps/MicroFPGA/blob/master/MicroFPGA-Java/src/main/java/de/embl/rieslab/microfpga/regint/RegisterInterface.java) (line 28), change "100000000" to "9600" (example baud rate):

```java
serialPort_.setComPortParameters(1000000, 8, 1, 0);
```

In the Python [register interface](https://github.com/jdeschamps/MicroFPGA/blob/master/MicroFPGA-Py/microfpga/regint.py) (line 9), change "100000000" to "9600" (example baud rate):

```python
if self._device is not None:
	self._serial = serial.Serial(self._device, 9600, timeout=1)
```

