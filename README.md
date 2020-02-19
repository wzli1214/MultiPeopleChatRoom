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

* Message Format -

* 1 - JOIN, 2 - MESSAGE, 3 - LEAVE

*

* {

* command: 2,

* from:"client A",

* message:"Hello, how are you doing?"

* }

