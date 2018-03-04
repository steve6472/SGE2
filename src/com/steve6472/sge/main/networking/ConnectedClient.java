/**********************
* Created by steve6472 (Mirek Jozefek)
* On date: 10. 2. 2018
* Project: SCR2
*
***********************/

package com.steve6472.sge.main.networking;

import java.io.Serializable;
import java.net.InetAddress;

public class ConnectedClient implements Serializable
{
	private static final long serialVersionUID = 2210549833950901523L;
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
