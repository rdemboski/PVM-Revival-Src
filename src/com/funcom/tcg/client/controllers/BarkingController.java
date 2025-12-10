/*    */ package com.funcom.tcg.client.controllers;
/*    */ 
/*    */ import com.funcom.commons.localization.JavaLocalization;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.rpgengine2.speach.SpeachContext;
/*    */ import com.funcom.rpgengine2.speach.SpeachMapping;
/*    */ import com.funcom.server.common.ServerConstants;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.jme.scene.Controller;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BarkingController
/*    */   extends Controller
/*    */ {
/*    */   private PropNode propNode;
/*    */   private SpeachMapping speachMapping;
/*    */   private double barkTime;
/*    */   
/*    */   public BarkingController(PropNode propNode, SpeachMapping speachMapping) {
/* 21 */     this.propNode = propNode;
/* 22 */     this.speachMapping = speachMapping;
/* 23 */     this.barkTime = (ServerConstants.RAND.nextInt(speachMapping.getMinBarkTime()) + ServerConstants.RAND.nextInt(speachMapping.getBarkInterval()));
/*    */   }
/*    */ 
/*    */   
/*    */   public void update(float time) {
/* 28 */     this.barkTime -= time;
/* 29 */     if (this.barkTime < 0.0D) {
/* 30 */       String key = this.speachMapping.getRandomSpeachForContext(SpeachContext.BARK);
/* 31 */       if (key != null) {
/* 32 */         String text = JavaLocalization.getInstance().getLocalizedRPGText(key);
/* 33 */         MainGameState.getChatUIController().createChatBubble(text, text, this.propNode);
/*    */       } 
/* 35 */       this.barkTime = (this.speachMapping.getMinBarkTime() + ServerConstants.RAND.nextInt(this.speachMapping.getBarkInterval()));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\controllers\BarkingController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */