package application;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Networking {

	private DatagramSocket gameDataSocket;
	private DatagramPacket dataPacket;
	private InetAddress hostAddr;
	private InetAddress srvAddr;
	private int port;
	private byte[] data;

	public Networking(/*boolean hostGame, */String hostname) throws UnknownHostException, SocketException {
		hostAddr = InetAddress.getLocalHost();
		port = 491532;
		gameDataSocket = new DatagramSocket(491532);
		gameDataSocket.connect(InetAddress.getByName(hostname), port);
	}

	public void encodeData(Integer num) throws IOException {
		data = new byte[1];
		data[0] = num.byteValue();
		dataPacket = new DatagramPacket(data, 1, srvAddr, port);
		gameDataSocket.send(dataPacket);
	}

	@SuppressWarnings("null")
	public int decodeData() throws IOException {
		DatagramPacket p = null;
		gameDataSocket.receive(p);
		return p.getData()[0];
	}

	public void sendFireball(double x, double y) {
		data = new byte[2];
		data[0] = (byte) x;
		data[1] = (byte) y;
		dataPacket = new DatagramPacket(data, 2, srvAddr, port);
	}

	@SuppressWarnings("null")
	public double[] recieveFireball() throws IOException {
		DatagramPacket p = null;
		gameDataSocket.receive(p);
		double[] pos = new double[2];
		pos[0] = p.getData()[0];
		pos[1] = p.getData()[1];
		return pos;
	}

}
