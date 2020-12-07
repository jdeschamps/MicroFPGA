## Installing and building MicroFPGA



1. Download and install [Git](https://git-scm.com/downloads).

2. Using the newly installed git console, clone the MicroFPGA repository in the folder of your choice:

   ```bash
   $ cd /path/to/folder/
   $ git clone https://github.com/jdeschamps/MicroFPGA.git
   ```

3. Follow the instructions on [getting started with the Au FPGA](https://alchitry.com/blogs/tutorials/getting-started-with-the-au) or [getting started with the Cu FPGA](https://alchitry.com/blogs/tutorials/getting-started-with-the-cu), this involves in particular installing either Vivado (Au) or IceCube2 (Cu). Make sure to install [Alchitry labs](https://alchitry.com/pages/alchitry-labs).

4. Start Alchitry Labs and in the "Settings" menu, set the location of the compiling tools (Vivado or IceCube2).

5. In "Project", open the MicroFPGA project (*.alp file corresponding to the [Au](https://github.com/jdeschamps/MicroFPGA/tree/master/Au_firmware) or [Cu](https://github.com/jdeschamps/MicroFPGA/tree/master/Cu_firmware) firmware) that was cloned in step 2. 

6. Click on the hammer icon to build the project. It can take few minutes.

7. Once the project has finished, upload it to your board with the plain arrow (Flash).

