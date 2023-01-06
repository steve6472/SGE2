/**********************
* Created by steve6472
* On date: 10. 2. 2018
* Project: SCR2
*
***********************/

package steve6472.sge.main.networking;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class ConnectedClient
{
	private final InetSocketAddress address;

	public ConnectedClient(InetAddress ip, int port)
	{
		address = new InetSocketAddress(ip, port);
	}

	public int getPort()
	{
		return address.getPort();
	}
	
	public InetAddress getIp()
	{
		return address.getAddress();
	}

	public InetSocketAddress getAddress()
	{
		return address;
	}
}
