package com.wzl.udpchat;

import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JOptionPane;

import com.wzl.udpchat.client.core.BroadcastServerProxy;
import com.wzl.udpchat.client.view.ChatFrame;

public class Client {
	public static void main(String[] args) throws Exception {
		DatagramSocket clientSocket = new DatagramSocket();
		
		String userName = JOptionPane.showInputDialog("Enter your name");
		String ipSerStr = JOptionPane.showInputDialog("Enter the server's address");
		
//		String ipStrSer = "10.0.0.169";
		InetAddress ipServer = InetAddress.getByName(ipSerStr);
		
		
//		This constructor is used for testing on different host.
		BroadcastServerProxy broadcastServerProxy = 
				new BroadcastServerProxy(
						ipServer, 
						9000,
						clientSocket,
						userName);
		
		
//		This constructor is used for testing on the same host.
		
//		BroadcastServerProxy broadcastServerProxy = 
//				new BroadcastServerProxy(
//						InetAddress.getLocalHost(), 
//						9000,
//						clientSocket,
//						userName);
		
		broadcastServerProxy.join();
		
		//build chatfram
		ChatFrame frame = new ChatFrame(userName, broadcastServerProxy);
		frame.setVisible(true);	
	}

}
