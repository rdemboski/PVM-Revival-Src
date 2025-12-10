package com.funcom.tcg.client.net;

import java.net.InetSocketAddress;

public interface LoginAnswer {
  void loginSuccesful(int paramInt);
  
  void loginFailed(String paramString);
  
  void timeout();
  
  void retryDifferentServer(InetSocketAddress paramInetSocketAddress);
  
  void loginFailedNeedCharacter();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\LoginAnswer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */