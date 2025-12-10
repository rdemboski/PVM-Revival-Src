package com.funcom.tcg.client.net;

import com.funcom.server.common.GameIOHandler;
import com.funcom.tcg.client.ui.inventory.Inventory;
import com.funcom.tcg.net.PlayerStartConfig;
import java.security.PublicKey;

public interface NetworkHandlerState {
  void setGameIOHandler(GameIOHandler paramGameIOHandler) throws StateException;
  
  void login(String paramString1, String paramString2, PlayerStartConfig paramPlayerStartConfig, LoginAnswer paramLoginAnswer) throws StateException;
  
  void login(String paramString1, String paramString2, LoginAnswer paramLoginAnswer, String paramString3, int paramInt, PublicKey paramPublicKey) throws StateException;
  
  void update(float paramFloat) throws StateException;
  
  void updateByTimeLimit(long paramLong);
  
  @Deprecated
  void registerForInventoryUpdate(Inventory paramInventory) throws StateException;
  
  @Deprecated
  void unregisterForInventoryUpdate(Inventory paramInventory) throws StateException;
  
  @Deprecated
  void unregisterAllForInventoryUpdate() throws StateException;
  
  void setNetworkStateMachine(NetworkStateMachine paramNetworkStateMachine);
  
  NetworkStateMachine getNetworkStateMachine();
  
  GameIOHandler getIoHandler();
  
  String getStateName();
  
  @Deprecated
  void addResponseCollectedPetsProcessor(ResponseCollectedPetsProcessor paramResponseCollectedPetsProcessor);
  
  void removeResponseCollectedPetsProcessor(ResponseCollectedPetsProcessor paramResponseCollectedPetsProcessor);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\NetworkHandlerState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */