/**
 * Program manages networking between hosts and the sending and receiving of data
 * 
 * @author Will W
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
	private InetAddress srvAddr; // address of the other player's computer
	private int port; // port number to connect to
	private byte[] data; // stores the data for input

	/**
	 * @param a String representing an IP address
	 * 
	 *          Initiates a connection to another player's computer using a random
	 *          TCP port. Stores the other player's IP address and uses it to
	 *          connect
	 * 
	 * @throws UnknownHostException
	 * @throws SocketException
	 */
	public Networking(String addr) throws UnknownHostException, SocketException {
		Random r = new Random();
		port = (int) ((r.nextDouble() * 100) + 1024);
		gameDataSocket = new DatagramSocket(port);
		byte[] ipAddr = new byte[4];
		Scanner scr = new Scanner(addr);
		scr.useDelimiter(" ");
		for (int i = 0; i < 4; i++) {
			String temp = scr.next();
			Integer n = Integer.parseInt(temp);
			ipAddr[i] = n.byteValue();
		}
		srvAddr = InetAddress.getByAddress(ipAddr);
		gameDataSocket.connect(srvAddr, port);
	}

	/**
	 * 
	 * @param int representing an input
	 * 
	 *            Sends a packet with data to the other player's computer. Converts
	 *            an int representing an in-game action to byte, encapsulates it in
	 *            a Datagram packet, and sends it to the other player.
	 * 
	 * @throws IOException
	 */
	public void encodeData(Integer num) throws IOException {
		data = new byte[1];
		data[0] = num.byteValue();
		dataPacket = new DatagramPacket(data, 1, srvAddr, port);
		gameDataSocket.send(dataPacket);
	}

	/**
	 * Decodes data from a packet to interpreted as player action. Receives a packet
	 * from the connected socket, and converts the byte data to an Integer
	 * 
	 * @return an int representing a player's action
	 * @throws IOException
	 */
	public int decodeData() throws IOException {
		DatagramPacket p = new DatagramPacket(data, 1, srvAddr, port);
		gameDataSocket.receive(p);
		return p.getData()[0];
	}

	/**
	 * @param x coordinate
	 * @param y coordinate
	 * 
	 *          Operates similar to encode data, but users two doubles to convert to
	 *          bytes, and sends it to the other player
	 * 
	 */
	public void sendFireball(double x, double y) {
		data = new byte[2];
		data[0] = (byte) x;
		data[1] = (byte) y;
		dataPacket = new DatagramPacket(data, 2, srvAddr, port);
	}

	/**
	 * Recives a datagram packet sent from the above method through a socket, and
	 * converts the data to an array, and returns the array
	 * 
	 * @return an array of doubles
	 * @throws IOException
	 */
	public double[] recieveFireball() throws IOException {
		DatagramPacket p = new DatagramPacket(new byte[2], 2, srvAddr, port);
		gameDataSocket.receive(p);
		double[] pos = new double[2];
		pos[0] = p.getData()[0];
		pos[1] = p.getData()[1];
		return pos;
	}

}
