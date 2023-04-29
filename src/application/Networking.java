package application;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;

public class Networking {

	private DatagramSocket gameDataSocket;
	private DatagramPacket dataPacket;
	private Inet4Address hostAddr;
	private InetAddress srvAddr;
	private int port;
	private byte[] data;

	public Networking(/*boolean hostGame, String addr*/) throws UnknownHostException, SocketException {
		hostAddr = (Inet4Address) Inet4Address.getLocalHost();
		Random r = new Random();
		port = (int) (r.nextDouble() + 1024);
		gameDataSocket = new DatagramSocket(port);
		byte[] ipAddr = new byte[4];
		ipAddr[0] = (byte) 192;
		ipAddr[0] = (byte) 168;
		ipAddr[0] = (byte) 4;
		ipAddr[0] = (byte) 3;
		srvAddr = InetAddress.getByAddress(ipAddr);
		//gameDataSocket.connect(InetAddress.getByName(hostname), port);
		gameDataSocket.connect(srvAddr, port);
	}

	public void encodeData(Integer num) throws IOException {
		data = new byte[1];
		data[0] = num.byteValue();
		dataPacket = new DatagramPacket(data, 1, srvAddr, port);
		gameDataSocket.send(dataPacket);
	}

	@SuppressWarnings("null")
	public int decodeData() throws IOException {
		DatagramPacket p = new DatagramPacket(data, 1, srvAddr, port);
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
