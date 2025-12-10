package com.funcom.server.common;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Queue;

public interface GameIOHandler {
  void addMessageListener(MessageListener paramMessageListener);
  
  void removeMessageListener(MessageListener paramMessageListener);
  
  void send(Message paramMessage) throws InterruptedException;
  
  boolean isOutputQueueFull();
  
  Queue<Message> getInputMessages();
  
  void start() throws IOException;
  
  void stop();
  
  void stopNow();
  
  int getThreadPriority();
  
  void setThreadPriority(int paramInt);
  
  int getOutPutQueueSize();
  
  SocketAddress getTargetAddres();
  
  InetAddress getInetAddress();
  
  int getTargetPort();
  
  boolean isConnected();
  
  public static abstract class MessageAdapter implements MessageListener {
    public void messageReceived(GameIOHandler gameIOHandler, Message message) {}
    
    public void messageSent(GameIOHandler gameIOHandler, Message message) {}
  }
  
  public static interface MessageListener {
    void messageReceived(GameIOHandler param1GameIOHandler, Message param1Message);
    
    void messageSent(GameIOHandler param1GameIOHandler, Message param1Message);
  }
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\common\GameIOHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */