package com.wzl.udpchat.server.core;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.wzl.udpchat.common.Command;
import com.wzl.udpchat.commons.model.Message;

public class MessageBroadcastService {
	
	DatagramSocket serverDgSocket;
	Set<ClientEndpoint> clients = new HashSet<ClientEndpoint>();
	Gson gson = new Gson();
	
	public MessageBroadcastService(DatagramSocket dgSock) {
		this.serverDgSocket = dgSock;
	}
	
	public void handleMessage(Message message, InetAddress address, int port) {
		if (Command.JOIN == message.getCommand()) {
			ClientEndpoint client = new ClientEndpoint(address, port);
			
			//Firstly, we assume each time the user set up a unique username 
			//since obviously it would be much easier for us to differentiate them by the chat windows. 
			//However, this system would not block the user 
			//try to set up the same username which is existed in the server since this situation 
			//may happen and it should be allowed. Although some of them may have the same usernames, 
			//they would have the different IP addresses and ports, 
			//that is the way how we differentiate two different users. 
			
			if (clients.add(client)) {
				System.out.println("Added; Clients : " + clients);
			}
			
		} else if (Command.LEAVE == message.getCommand()) {
			ClientEndpoint client = new ClientEndpoint(address, port);
			if (clients.remove(new ClientEndpoint(address, port))) {
				System.out.println("Removed; Clients : " + clients);
			}
		}
		
		broadcastMessage(message);
	}
	
	private void broadcastMessage(Message message) {
		String jsonText = gson.toJson(message);
		byte[] data = jsonText.getBytes();
		
		for (ClientEndpoint client : clients) {
			DatagramPacket packet = 
					new DatagramPacket(data, data.length,
							client.getAddress(), client.getPort());
			
			try {
				serverDgSocket.send(packet);
				System.out.println("Send message to client" + client);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	class ClientEndpoint {
		InetAddress address;
		int port;
		
		public ClientEndpoint(InetAddress address, int port) {
			super();
			this.address = address;
			this.port = port;
		}
		
		public InetAddress getAddress() {
			return address;
		}
		
		public void setAddress(InetAddress address) {
			this.address = address;
		}
		
		public int getPort() {
			return port;
		}
		
		public void setPort(int port) {
			this.port = port;
		}
		
		private MessageBroadcastService getOuterType() {
			return MessageBroadcastService.this;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((address == null) ? 0 : address.hashCode());
			result = prime * result + port;
			return result;
			
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			
			if (obj == null)
				return false;
			
			if (getClass() != obj.getClass())
				return false;
			
			ClientEndpoint other = (ClientEndpoint) obj;
			
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			
			if(address == null) {
				if (other.address != null)
					return false;
				
			} else if (!address.equals(other.address))
				return false;
			
			if (port != other.port)
				return false;
			
			return true;
			
		}
		
		@Override 
		public String toString() {
			return "ClientEndpoint [address=" + address + ", port=" + port + "]";
		}
		
	}

}
