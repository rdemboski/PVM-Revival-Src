package com.funcom.gameengine.conanchat.datatypes;

import java.nio.ByteBuffer;

public interface Datatype {
  ByteBuffer toByteBuffer(ByteBuffer paramByteBuffer);
  
  void readValue(ByteBuffer paramByteBuffer);
  
  Endianess getEndianess();
  
  void setEndianess(Endianess paramEndianess);
  
  int getSizeInBytes();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\datatypes\Datatype.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */