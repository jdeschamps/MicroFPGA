# Register interface

The communication interface is based on the [Register interface tutorial](https://alchitry.com/blogs/tutorials/register-interface) from Alchitry. Each [MicroFPGA signal](microfpga.md) has an address used for reading/writing its value.

For 8 lasers, 5 TTL, 7 servomotors, 5 PWM:

|           Parameter           |         Address         |        Values        |
| :---------------------------: | :---------------------: | :------------------: |
|          Laser mode           |   laser number (0-7)    |         0-4          |
|        Laser duration         |  8+ laser number (0-7)  |       0-65535        |
|        Laser sequence         | 16+ laser number (0-7)  |       0-65535        |
|           TTL state           |  24+ TTL number (0-4)   |         0-1          |
|        Servo position         | 29+ servo number (0-6)  |       0-65535        |
|              PWM              |  36+ PWM number (0-4)   |        0-255         |
| Analog signal <br>(READ only) | 41+ Analog number (0-7) | 0-65535 (READ only)  |
|    Version <br>(READ only)    |           100           |    2 (READ only)     |
|      ID<br />(READ only)      |           101           | 29 or 79 (READ only) |

The addresses are defined in the [top file of the Alchitry Labs project](https://github.com/jdeschamps/MicroFPGA/blob/master/Au_firmware/source/au_top.luc). A read request is answered by 4 bytes representing the queried value. Note that unknown addresses will cause the FPGA to answer **11206655** (error code).

Examples of the register interface implementations can be found here:

- **C++**: in the [Micro-Manager device adapter](https://github.com/jdeschamps/MicroFPGA/blob/master/Device_Adapter/MicroFPGA.cpp), line 308, 325 and 338.
- **Python**: in the [MicroFPGA-Py library](https://github.com/jdeschamps/MicroFPGA/blob/master/MicroFPGA-Py/microfpga/regint.py), line 39 and 57.
- **Java**: in the [MicroFPGA-Java library](https://github.com/jdeschamps/MicroFPGA/blob/master/MicroFPGA-Java/src/main/java/de/embl/rieslab/microfpga/regint/RegisterInterface.java), line 66 and 96.



