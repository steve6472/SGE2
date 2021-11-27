/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 10. 2. 2018
* Project: SCR2
*
***********************/

package steve6472.sge.main.networking;

import java.net.InetAddress;

public class ConnectedClient
{
	private InetAddress ip;
	private int port;

	public ConnectedClient(InetAddress ip, int port)
	{
		this.ip = ip;
		this.port = port;
	}

	public int getPort()
	{
		return port;
	}
	
	public InetAddress getIp()
	{
		return ip;
	}
	
	public void setIp(InetAddress ip)
	{
		this.ip = ip;
	}
	
	public void setPort(int port)
	{
		this.port = port;
	}

}
