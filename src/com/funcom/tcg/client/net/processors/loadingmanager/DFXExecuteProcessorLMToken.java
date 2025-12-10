/*     */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*     */ 
/*     */ import com.funcom.commons.dfx.DireEffect;
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
/*     */ import com.funcom.gameengine.model.command.Command;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.creatures.RpgCreatureConstants;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.command.ExecuteDFXCommand;
/*     */ import com.funcom.tcg.client.controllers.InterpolationController;
/*     */ import com.funcom.tcg.client.model.PropNodeRegister;
/*     */ import com.funcom.tcg.client.net.GameOperationState;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Future;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DFXExecuteProcessorLMToken
/*     */   extends LoadingManagerToken
/*     */ {
/*  31 */   int id = -1;
/*  32 */   String dfxScript = "";
/*  33 */   float time = 0.0F;
/*  34 */   int creatureType = -1;
/*  35 */   protected Future<DireEffect> EffectFuture = null;
/*     */ 
/*     */ 
/*     */   
/*     */   PropNode propNode;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DFXExecuteProcessorLMToken(int id, String dfxScript, float time, int creatureType) {
/*  45 */     this.propNode = null;
/*     */     this.creatureType = creatureType;
/*     */     this.id = id;
/*     */     this.dfxScript = dfxScript;
/*     */     this.time = time;
/*     */   }
/*     */   public boolean processRequestAssets() throws Exception {
/*  52 */     if (this.creatureType == RpgCreatureConstants.Type.PLAYER_CREATURE_TYPE_ID.getTypeId()) {
/*  53 */       PropNodeRegister propNodeRegister = TcgGame.getPropNodeRegister();
/*  54 */       this.propNode = propNodeRegister.getPropNode(Integer.valueOf(this.id));
/*     */     } else {
/*  56 */       PropNodeRegister propNodeRegister = TcgGame.getMonsterRegister();
/*  57 */       this.propNode = propNodeRegister.getPropNode(Integer.valueOf(this.id));
/*     */     } 
/*     */     
/*  60 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processWaitingAssets() throws Exception {
/*  65 */     return (this.EffectFuture == null || this.EffectFuture.isDone());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processGame() throws Exception {
/*  71 */     if (this.propNode != null && 
/*  72 */       !this.dfxScript.isEmpty()) {
/*     */       
/*  74 */       DireEffect effect = null;
/*  75 */       if (this.EffectFuture != null && !this.EffectFuture.isCancelled()) {
/*  76 */         effect = this.EffectFuture.get();
/*     */       } else {
/*     */         
/*  79 */         DireEffectDescription effectDescription = this.propNode.getEffectDescriptionFactory().getDireEffectDescription(this.dfxScript, false);
/*  80 */         effect = effectDescription.createInstance(this.propNode, UsageParams.EMPTY_PARAMS);
/*     */       } 
/*  82 */       this.EffectFuture = null;
/*     */       
/*  84 */       if (effect != null) {
/*  85 */         ExecuteDFXCommand executeDFXCommand = new ExecuteDFXCommand(this.propNode, effect, this.time);
/*  86 */         if (executeDFXCommand.isFinished()) {
/*  87 */           executeDFXCommand.update(0.0F);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/*  92 */           InterpolationController cont = GameOperationState.findInterpolationController(this.propNode);
/*  93 */           if (cont != null) {
/*  94 */             cont.stop();
/*     */           }
/*     */ 
/*     */           
/*  98 */           ((Creature)this.propNode.getProp()).immediateCommand((Command)executeDFXCommand);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 104 */     return true;
/*     */   }
/*     */   
/*     */   class LoadEffectCallable
/*     */     implements Callable {
/* 109 */     PropNode propNode = null;
/* 110 */     String dfxScript = "";
/*     */     LoadEffectCallable(PropNode propNode, String dfxScript) {
/* 112 */       this.propNode = propNode;
/* 113 */       this.dfxScript = dfxScript;
/*     */     }
/*     */ 
/*     */     
/*     */     public DireEffect call() {
/* 118 */       DireEffect effect = null;
/*     */       try {
/* 120 */         DireEffectDescription effectDescription = this.propNode.getEffectDescriptionFactory().getDireEffectDescription(this.dfxScript, false);
/* 121 */         effectDescription.createInstance(this.propNode, UsageParams.EMPTY_PARAMS);
/*     */       }
/* 123 */       catch (Exception e) {
/*     */         
/* 125 */         LoadingManager.INSTANCE.sendCrash(e);
/*     */       } 
/*     */       
/* 128 */       return effect;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\DFXExecuteProcessorLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */