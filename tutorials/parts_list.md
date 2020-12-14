# Parts list

Here, we outline a simple part list with the main physical components needed to run MicroFPGA on a microscope.

- Alchitry board: [Au](https://alchitry.com/products/alchitry-au-fpga-development-board) or [Cu](https://alchitry.com/products/alchitry-cu-fpga-development-board), now sold on SparkFun. The Au board features a more powerful FPGA and analog read-out channels, as opposed to the Cu.
- Alchitry [Br break-out board](https://alchitry.com/collections/all/products/alchitry-br): allows easier access to the pins.
- If you need to scale down the inputs to the board to 3.3 V, scale up the board outputs or want to generate an analog signal, you will need to build or order the [signal conversion board](electronics.md).
- If you want to measure analog inputs bigger than 1 V, you will need to scale them down to 1 V by using the [current conversion board](electronics.md).
- If you intend on driving servomotors, we suggest using a 5 V external power supply soldered to the + and - of the FPGA board and to the power inputs of the servomotors.

