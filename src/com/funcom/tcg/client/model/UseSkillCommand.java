/*    */ package com.funcom.tcg.client.model;
/*    */ 
/*    */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*    */ import com.funcom.commons.dfx.NoSuchDFXException;
/*    */ import com.funcom.gameengine.model.command.Command;
/*    */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*    */ import com.funcom.tcg.client.net.GameOperationState;
/*    */ import com.funcom.tcg.client.net.NetworkHandler;
/*    */ 
/*    */ public class UseSkillCommand
/*    */   extends Command
/*    */ {
/*    */   public static final String COMMAND_NAME = "use-item";
/*    */   private boolean started;
/*    */   private int slotId;
/*    */   private double duration;
/*    */   private DistanceCalculator distanceCalculator;
/*    */   private float timePassed;
/*    */   private ClientPlayer clientPlayer;
/*    */   
/*    */   public UseSkillCommand(int slotId, ClientPlayer clientPlayer, DireEffectDescriptionFactory direEffectDescriptionFactory, DistanceCalculator distanceCalculator) {
/* 23 */     this.slotId = slotId;
/* 24 */     this.clientPlayer = clientPlayer;
/* 25 */     this.distanceCalculator = distanceCalculator;
/* 26 */     this.started = false;
/* 27 */     this.timePassed = 0.0F;
/* 28 */     this.duration = 0.0D;
/*    */     
/* 30 */     if (this.clientPlayer.getActivePet() != null) {
/* 31 */       ClientItem item = clientPlayer.getActivePet().getSkillAt(slotId);
/* 32 */       if (item != null && item.isReady() && clientPlayer.checkGlobalCooldown(item.getGlobalCooldown()) && item.getManaCost(clientPlayer.getStatSupport().getStatById((short)13).getBase()) <= clientPlayer.getStatSum(Short.valueOf((short)14)).intValue()) {
/*    */         
/* 34 */         String script = item.getDFXScript();
/*    */         
/* 36 */         if (!script.isEmpty()) {
/*    */           try {
/* 38 */             this.duration = direEffectDescriptionFactory.getDireEffectDescription(script, false).getDuration();
/* 39 */           } catch (NoSuchDFXException e) {
/* 40 */             throw new RuntimeException(e);
/*    */           } 
/*    */         } else {
/* 43 */           this.duration = 0.0D;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getName() {
/* 50 */     return "use-item";
/*    */   }
/*    */   
/*    */   public boolean isFinished() {
/* 54 */     return (this.timePassed >= this.duration);
/*    */   }
/*    */   
/*    */   public void update(float time) {
/* 58 */     if (!this.started && this.duration > 0.0D) {
/*    */       
/* 60 */       GameOperationState currentState = (GameOperationState)NetworkHandler.instance().getCurrentState();
/* 61 */       currentState.forceBroadcast();
/* 62 */       this.clientPlayer.useSkillbarItem(this.slotId, this.distanceCalculator.getDistance());
/* 63 */       this.started = true;
/*    */     } 
/* 65 */     this.timePassed += time;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\UseSkillCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */