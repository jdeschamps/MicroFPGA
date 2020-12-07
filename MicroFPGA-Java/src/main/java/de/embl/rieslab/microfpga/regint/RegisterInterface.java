package de.embl.rieslab.microfpga.regint;

import com.fazecast.jSerialComm.SerialPort;

/*
 * This class was inspired by the RegisterInterface written by Alchitry (ex EmbeddedMicro):
 * https://alchitry.com/blogs/tutorials/register-interface
 */
public class RegisterInterface {
	
	private static String ALCHITRY = "Alchitry";
	
	SerialPort serialPort_;
	
	public boolean connect() {
		SerialPort[] portsList = SerialPort.getCommPorts();

		// connects to the first Alchitry board we find
		for(SerialPort port: portsList) {
			if(port.getPortDescription() != null && port.getPortDescription().contains(ALCHITRY)) {
				serialPort_ = port;
				break;
			}
		}

		boolean b = serialPort_.openPort();
		
		serialPort_.setComPortParameters(1000000, 8, 1, 0);
		serialPort_.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 1000, 0);
		
		return b;
	}

	public boolean disconnect() {
		return serialPort_.closePort();
	}


	public boolean write(int address, int data){
		byte[] buff = new byte[9];
		buff[0] = (byte) (1 << 7);
		buff[1] = (byte) (address & 0xff);
		buff[2] = (byte) ((address >> 8) & 0xff);
		buff[3] = (byte) ((address >> 16) & 0xff);
		buff[4] = (byte) ((address >> 24) & 0xff);
		buff[5] = (byte) (data & 0xff);
		buff[6] = (byte) ((data >> 8) & 0xff);
		buff[7] = (byte) ((data >> 16) & 0xff);
		buff[8] = (byte) ((data >> 24) & 0xff);
		
		int ret = serialPort_.writeBytes(buff, buff.length);
		
		return ret != -1;
	}

	public int read(int address){
		byte[] buff = new byte[5];
		buff[0] = (byte) (0 << 7);
		buff[1] = (byte) (address & 0xff);
		buff[2] = (byte) ((address >> 8) & 0xff);
		buff[3] = (byte) ((address >> 16) & 0xff);
		buff[4] = (byte) ((address >> 24) & 0xff);

		int ret = serialPort_.writeBytes(buff, buff.length);
		if(ret == -1)
			return ret;
		
		ret = serialPort_.readBytes(buff, buff.length);
		if(ret == -1)
			return ret;
		
		return (buff[0] & 0xff) | (buff[1] & 0xff) << 8 | (buff[2] & 0xff) << 16 | (buff[3] & 0xff) << 24;
	}
}
