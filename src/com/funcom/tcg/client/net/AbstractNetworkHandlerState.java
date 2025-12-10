/*    */ package com.funcom.tcg.client.net;
/*    */ 
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.tcg.client.ui.inventory.Inventory;
/*    */ import com.funcom.tcg.net.PlayerStartConfig;
/*    */ import java.security.PublicKey;
/*    */ 
/*    */ public abstract class AbstractNetworkHandlerState
/*    */   implements NetworkHandlerState
/*    */ {
/*    */   private GameIOHandler ioHandler;
/*    */   private String stateName;
/*    */   private NetworkStateMachine networkStateMachine;
/*    */   
/*    */   protected AbstractNetworkHandlerState(String stateName) {
/* 16 */     this.stateName = stateName;
/*    */   }
/*    */   
/*    */   public String getStateName() {
/* 20 */     return this.stateName;
/*    */   }
/*    */   
/*    */   public void setGameIOHandler(GameIOHandler ioHandler) {
/* 24 */     this.ioHandler = ioHandler;
/*    */   }
/*    */   
/*    */   public GameIOHandler getIoHandler() {
/* 28 */     return this.ioHandler;
/*    */   }
/*    */   
/*    */   public void setNetworkStateMachine(NetworkStateMachine stateMachine) {
/* 32 */     this.networkStateMachine = stateMachine;
/*    */   }
/*    */   
/*    */   public NetworkStateMachine getNetworkStateMachine() {
/* 36 */     return this.networkStateMachine;
/*    */   }
/*    */ 
/*    */   
/*    */   public void login(String username, String password, PlayerStartConfig playerStartConfig, LoginAnswer loginAnswer) throws StateException {
/* 41 */     throw new StateException("WRONG STATE: " + this.stateName);
/*    */   }
/*    */   
/*    */   public void login(String username, String password, LoginAnswer loginAnswer, String sessionId, int userId, PublicKey publicKey) {
/* 45 */     throw new StateException("WRONG STATE: " + this.stateName);
/*    */   }
/*    */   
/*    */   public void registerForInventoryUpdate(Inventory inventory) {
/* 49 */     throw new StateException("WRONG STATE: " + this.stateName);
/*    */   }
/*    */   
/*    */   public void update(float time) {
/* 53 */     throw new StateException("WRONG STATE: " + this.stateName);
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateByTimeLimit(long nanosLimit) {
/* 58 */     throw new StateException("WRONG STATE: " + this.stateName);
/*    */   }
/*    */   
/*    */   public void unregisterForInventoryUpdate(Inventory inventory) {
/* 62 */     throw new StateException("WRONG STATE: " + this.stateName);
/*    */   }
/*    */ 
/*    */   
/*    */   public void unregisterAllForInventoryUpdate() throws StateException {
/* 67 */     throw new StateException("WRONG STATE: " + this.stateName);
/*    */   }
/*    */   
/*    */   public void addResponseCollectedPetsProcessor(ResponseCollectedPetsProcessor processor) {
/* 71 */     throw new StateException("WRONG STATE: " + this.stateName);
/*    */   }
/*    */   
/*    */   public void removeResponseCollectedPetsProcessor(ResponseCollectedPetsProcessor processor) {
/* 75 */     throw new StateException("WRONG STATE: " + this.stateName);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\AbstractNetworkHandlerState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */