package com.funcom.server.common;

import java.nio.ByteBuffer;

public interface MessageFactory {
  Message toMessage(ByteBuffer paramByteBuffer) throws UnknownMessageTypeException;
  
  ByteBuffer toBuffer(Message paramMessage);
  
  double getIncomingMessageTypeStats(short paramShort);
  
  double getOutgoingMessageTypeStats(short paramShort);
  
  void setLogMessageStats(boolean paramBoolean);
  
  void clearMessageCaches();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\common\MessageFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */