package com.wzl.udpchat.client.view;

import java.util.Date;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.wzl.udpchat.client.core.BroadcastServerProxy;
import com.wzl.udpchat.client.core.ChatListener;

public class ChatFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	JTextArea textArea = new JTextArea();
	
	JTextField textField = new JTextField();
	JButton btnSend = new JButton("Send");
	
	BroadcastServerProxy broadcastServerProxy;
	
	public ChatFrame(String title, BroadcastServerProxy broadcastServerProxy) {
		setTitle(title);
		this.broadcastServerProxy = broadcastServerProxy;
		this.broadcastServerProxy.setChatListener(new ChatMessageListener());
		prepareFrame();
		
	}
	
	private void prepareFrame() {
		Container content = getContentPane();
		
		Box south = Box.createHorizontalBox();
		south.add(textField);
		south.add(btnSend);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		content.add(scrollPane, "Center");
		content.add(south, "South");
		
		setBounds(100, 100, 300, 300);
		setResizable(false);
		addWindowListener(new WindowHandler());
		btnSend.addActionListener(new SendHandler());	
	}
	
	//When the window's status changes by virtue of being opened, closed, 
	//activated or deactivated, iconified or deiconified, the relevant method 
	//in the listener object is invoked, and the WindowEvent is passed to it.
	class WindowHandler extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			broadcastServerProxy.leave();
			dispose();
		}
	}
	
	//SendHandler class can perceive the action of send.
	class SendHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			broadcastServerProxy.send(textField.getText());
			//While sending the text, clear the textField
			textField.setText("");
			
		}
	}
	
	class ChatMessageListener implements ChatListener {
		
		public void onJoin(String userName) {
			String message = userName + " joined";
			String appendedText = textArea.getText() + "\n" + message;
			textArea.setEditable(true);

			textArea.setText(appendedText);
			
			textArea.setEditable(false);

			
		}
		
		public void onLeave(String userName) {
			

			String message = userName + " left";
			String appendedText = textArea.getText() + "\n" + message;
			
			textArea.setEditable(true);
	
			textArea.setText(appendedText);
			
			textArea.setEditable(false);

		}
		
		public void onMessage(String userName, String message) {
			//retrieve the current time
			SimpleDateFormat currTime = new SimpleDateFormat();
			currTime.applyPattern("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			
			String strMessage = currTime.format(date) + " " + userName + ":" + message;
			
			//
			textArea.setEditable(true);
			
			String appendedText = textArea.getText() + "\n" + strMessage;
			textArea.setText(appendedText);
			
			//set the text to the new line, when the text exceeds the window boundary
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
			
			//history messages in the textArea in the chatFrame cannot be revised
			textArea.setEditable(false);
		}
		
	}
}
