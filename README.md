## Multi-People Chat Room 

In this project, I intended to design a two-people chat room system based on User
Datagram Protocol(UDP) and Java Socket Programming, which includes a server
and two client. This system enables transmitting the messages to each other in this
chatroom and each of them would receive and see their messages in the Graphical
User Interface (GUI) chatting window.


### Components:

· 1 server, which has a MessageBroadcastService to handle the message.

· 2 clients, each of them has a BroadcastServiceProxy to process the message.

### Message Format

```bash
* Message Format -
* 1 - JOIN, 2 - MESSAGE, 3 - LEAVE
* {

* command: 2,

* from:"client A",

* message:"Hello, how are you doing?"

* }
```

### Specification
1. Run the server on a host. Get the IP address of the host which runs the server.
2. Run the client on a different host. Input the name, input the server's IP address.
3. Repeat the step 2 if you want to run multiple clients. You should have two clients at least to chat together. 

### Input

![image](https://github.com/wzli1214/MultiPeopleChatRoom/blob/master/images/input.png)

### Chat Screens

![image](https://github.com/wzli1214/MultiPeopleChatRoom/blob/master/images/chatScreen.png)

### Chat Between Different Hosts Example

![image](https://github.com/wzli1214/MultiPeopleChatRoom/blob/master/images/chatBetw2hosts.png)

### Architecture
![image](https://github.com/wzli1214/MultiPeopleChatRoom/blob/master/images/architecture.png)

### Join Process & Send Process & Leave Process

![image](https://github.com/wzli1214/MultiPeopleChatRoom/blob/master/images/3processes.png)





