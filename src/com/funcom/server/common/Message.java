package com.funcom.server.common;

import java.nio.ByteBuffer;

public interface Message {
  short getMessageType();
  
  Message toMessage(ByteBuffer paramByteBuffer);
  
  int getSerializedSize();
  
  ByteBuffer serialize(ByteBuffer paramByteBuffer);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\common\Message.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */