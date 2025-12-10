package com.funcom.tcg.client.net.creaturerefresher;

import com.funcom.server.common.Message;

public interface CreatureRefresher {
  void refresh(Message paramMessage);
  
  boolean isRefreshable(short paramShort);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\creaturerefresher\CreatureRefresher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */