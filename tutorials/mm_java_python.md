# Micro-Manager

1. [Download](https://github.com/jdeschamps/MicroFPGA/releases/tag/v1.0-beta) the compiled device adapter. Note the compiled date, more recent version of Micro-Managers might require to recompile it.
2. Place the compiled device adapter (*mmgr_del_MicroFPGA.dll*) in the installation folder of Micro-Manager.
3. Start Micro-Manager.
4. In the "Devices" menu, choose "Hardware Configuration Wizard...".
5. Create a new configuration or modify an existing one.
6. In the hardware list, find "MicroFPGA" and double click on the device adapter.
7. Select the relevant COMport and enter the baudrate (default is 1000000). Click ok.
8. In the windows that opens, select the signals you want to use. Click next.
9. For each signal, select the number of channels. Click ok.
10. Save the new hardware configuration.
11. In the device property browser, available from the "Devices" menu, you have access to all properties from MicroFPGA.

#### Micro-Manager 1.4.23

Note that Micro-Manager 1.4.23 does not allow selecting the default baud rate. Therefore, refer to the "[Change the communication baudrate](changing_baudrate.md)" tutorial.



# Java

The Java library is provided as a Maven project. In the following step we show how to import it in Eclipse:

1. Check out [MicroFPGA git repository](installing_microfpga.md) (step 1 and 2).
2. Open Eclipse.
3. In the package explorer, import a new project. If the option is not available because projects already exist in your workspace, right-click and import a new project.
4. In the Maven folder, double-click on "Existing Maven Projects".
5. Click on "Browse" and navigate to the "MicroFPGA-Java" folder in the local git repository. 
6. A "pom.xml" file should be automatically detected. Click on Finish.
7. After few seconds, Eclipse should automatically download the dependencies and set-up the project. The source folder containing the packages should be "src/main/java". If it is not the case, right-click on the project and select "Maven", then "Update Project".
8. Open the Example file in the de.embl.rieslab.microfpga package and run it.
9.  The program allows you to first select the COMport corresponding to your FPGA. It will then connect to the FPGA, change some some parameters and disconnect.

This library was tested with Java 1.8 (jdk) on Windows 10.

# Python 3

1. Install the serial and pyserial packages for Python (for instance using pip).
2. Check out [MicroFPGA git repository](installing_microfpga.md) (step 1 and 2).
3. Open example.py found in the MicroFPGA-Py folder.
4. Run the script. It will automatically detect the FPGA, connect to it, change some parameters and disconnect from it.

This library was tested with Python 3.5 (Anaconda) on Windows 10.

# Labview

A LabView example has been graciously provided by Christian Kieser (Electronic workshop, EMBL). It can be found in the MicroFPGA-LabView folder of the MicroFPGA repository. It is not supported in this repository and is only given as possible reference.