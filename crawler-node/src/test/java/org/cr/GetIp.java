/**
 * 
 */
package org.cr;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * @author caorong
 * 
 */
public class GetIp {

	/**
	 * @param args
	 * @throws SocketException
	 */
	public static void main(String[] args) throws SocketException {
		Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		while (allNetInterfaces.hasMoreElements()) {
			NetworkInterface netInterface = (NetworkInterface) allNetInterfaces
					.nextElement();
			System.out.println(netInterface.getName());
			Enumeration addresses = netInterface.getInetAddresses();
			while (addresses.hasMoreElements()) {
				ip = (InetAddress) addresses.nextElement();
				if (ip != null && ip instanceof Inet4Address) {
					System.out.println("本机的IP = " + ip.getHostAddress());
				}
			}
		}

		// --------------------------------------------------------
		try {
			System.out.println("本机的IP = " + InetAddress.getLocalHost());
			System.out.println(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
