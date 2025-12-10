/*    */ package com.funcom.tcg.client.net;
/*    */ 
/*    */ import com.funcom.rpgengine2.loader.RpgLoader;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.net.creaturerefresher.CreatureRefresher;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TcgRefreshFactory
/*    */ {
/* 18 */   private Set<CreatureRefresher> creatureRefreshers = new HashSet<CreatureRefresher>();
/*    */ 
/*    */   
/*    */   public void refreshForType(Message message) {
/* 22 */     for (CreatureRefresher creatureRefresher : this.creatureRefreshers) {
/* 23 */       if (creatureRefresher.isRefreshable(message.getMessageType())) {
/* 24 */         creatureRefresher.refresh(message); return;
/*    */       } 
/*    */     } 
/* 27 */     throw new IllegalStateException("Don't know how to build: " + message.getMessageType());
/*    */   }
/*    */   public static void addDefaultRefreshers(TcgRefreshFactory instance, RpgLoader rpgLoader) {
/* 30 */     instance.creatureRefreshers.add(MainGameState.getMapWindow());
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\TcgRefreshFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */