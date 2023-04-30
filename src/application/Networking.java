/**
 * Program manages networking between hosts and the sending and receiving of data
 * 
 * 
 */

package application;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;

public class Networking {

	private DatagramSocket gameDataSocket; // socket for sending data
	private DatagramPacket dataPacket; // packet to be used to send data
	private Inet4Address hostAddr; // host ip address
	private InetAddress srvAddr; // address of the other player's computer
	private int port; // port number to connect to
	private byte[] data; // stores the data for input

	/**
	 * @param integers representing an ip address Constructor takes in numbers
	 *                 representing an ip address of the other player's computer,
	 *                 then makes a connection to the other player.
	 * 
	 * @throws UnknownHostException
	 * @throws SocketException
	 */
	public Networking(String addr) throws UnknownHostException, SocketException {
		hostAddr = (Inet4Address) Inet4Address.getLocalHost();
		Random r = new Random();
		port = (int) ((r.nextDouble() * 100) + 1024);
		gameDataSocket = new DatagramSocket(port);
		byte[] ipAddr = new byte[4];
		Scanner scr = new Scanner(addr);
		scr.useDelimiter(".");
		for (int i = 0; i < 4; i++) {
			Integer n = Integer.parseInt(scr.next());
			ipAddr[i] = n.byteValue();
		}
		System.out.println(ipAddr);
		srvAddr = InetAddress.getByAddress(ipAddr);
		// gameDataSocket.connect(InetAddress.getByName(hostname), port);
		gameDataSocket.connect(srvAddr, port);
	}
	/**
	 * 
	 * @param int representing an input
	 * Sends a packet with a int converted to byte to the other player,
	 * @throws IOException
	 */
	public void encodeData(Integer num) throws IOException {
		data = new byte[1];
		data[0] = num.byteValue();
		dataPacket = new DatagramPacket(data, 1, srvAddr, port);
		gameDataSocket.send(dataPacket);
	}
	
	/**
	 * Decodes data from a packet to interpreted as player movment
	 * @return an int representing a player's movment
	 * @throws IOException
	 */
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
