# Application examples



In the Ries lab, several microscopes are equipped with MicroFPGA together with [Micro-Manager](https://micro-manager.org/) and [htSMLM](https://github.com/jdeschamps/htSMLM). In this section, we detail some aspect of our usage.



## FPGA on the microscopes

|       Signal        |   Electronics   |                            Device                            |       Use       |
| :-----------------: | :-------------: | :----------------------------------------------------------: | :-------------: |
| Camera trigger (in) |      None       |                  Hamamatsu ORCA-Flash4.0 V2                  |     Trigger     |
| Camera trigger (in) |  SCB 5V->3.3V   |      Photometrics Evolve 512<br />Andor iXon Ultra 897       |     Trigger     |
| Laser trigger (out) |      None       | Toptica iChrome MLE <br />Toptica iBeamSmart<br />Omicron LightHUB<br />Custom [LaserEngine](https://github.com/ries-lab/LaserEngine) |     Trigger     |
|     Servo (out)     |      None       | Sail winch servos (e.g.: [1](https://github.com/ries-lab/RiesPieces/tree/master/Microscopy/Filter_wheel), [2](https://github.com/ries-lab/RiesPieces/tree/master/Microscopy/Flip_mount) and [3](https://github.com/ries-lab/RiesPieces/tree/master/Microscopy/Linear_stage)) |  Move elements  |
|      PWM (out)      |      None       | Custom [LaserEngine](https://github.com/ries-lab/LaserEngine) |   Laser power   |
|      PWM (out)      | SCB PWM->Analog |                             AOM                              |   Laser power   |
|      TTL (out)      |      None       |        Owis KSHM 40<br />Custom bright-field LED ring        | In/Out, On/Off  |
|     Analog (in)     |    ACB -> 1V    | Custom [focus-stabilization](https://github.com/ries-lab/RiesPieces/tree/master/Microscopy/Focus-locking)<br />Custom [laser power meter](https://github.com/ries-lab/RiesPieces/tree/master/Electronics/Powermeter) | Reading signals |

SCB: signal conversion board (see [electronics](electronics.md))

ACB: analog conversion board (see [electronics](electronics.md))



## GUI

Our microscopes are controlled through [Micro-Manager](https://micro-manager.org/) and a custom [EMU](https://github.com/jdeschamps/EMU) plugin: [htSMLM](https://github.com/jdeschamps/htSMLM). Here is an overview of the mapping of the FPGA signals (device properties) in Micro-Manager to the htSMLM functionalities. Refer to the [htSMLM guide](https://github.com/jdeschamps/htSMLM/tree/master/guide) for more details, in particular the [descriptions](https://github.com/jdeschamps/htSMLM/blob/master/guide/using-htsmlm.md) of each panel.

|                       Signal                        |                        htSMLM panels                         |
| :-------------------------------------------------: | :----------------------------------------------------------: |
| Laser trigger<br />(mode, pulse duration, sequence) | Laser trigger panels<br />Activation laser panel<br />Activation script panel |
|                        Servo                        | Filters panel<br />Additional filters panel<br />Controls panel |
|                         PWM                         |                     Laser control panels                     |
|                         TTL                         |                        Controls panel                        |
|                       Analog                        |               QPD panel<br />Powermeter panel                |

Together, the GUI and MicroFPGA allows intuitive control of the microscope and automated localization microscopy.