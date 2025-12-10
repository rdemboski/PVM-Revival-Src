/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.audio.Sound;
/*    */ import com.funcom.audio.SoundSystemFactory;
/*    */ import com.funcom.commons.configuration.ExtProperties;
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.Reference;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.SoundCreateCallable;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.SoundPlayCallable;
/*    */ import com.funcom.gameengine.view.Effects;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.rpgengine2.creatures.RpgCreatureConstants;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.model.PropNodeRegister;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.net.message.StatEffectMessage;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.Callable;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StatEffectProcessor
/*    */   implements MessageProcessor
/*    */ {
/* 35 */   private static final Logger LOG = Logger.getLogger(StatEffectProcessor.class.getName());
/* 36 */   private Reference<Sound> critSound = new Reference();
/*    */   
/*    */   public StatEffectProcessor() {
/* 39 */     ResourceManager resourceManager = TcgGame.getResourceManager();
/* 40 */     ExtProperties config = (ExtProperties)resourceManager.getResource(ExtProperties.class, "configuration/audio/audioconfig.properties");
/* 41 */     String critResource = config.getProperty("global.sound.crit");
/* 42 */     if (critResource != null)
/* 43 */       if (LoadingManager.USE && LoadingManager.THREADED_SOUND) {
/* 44 */         LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundCreateCallable(this.critSound, critResource));
/*    */       } else {
/* 46 */         this.critSound.set(SoundSystemFactory.getSoundSystem().getSound(critResource));
/*    */       }  
/*    */   }
/*    */   
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/*    */     PropNodeRegister propNodeRegister;
/* 52 */     StatEffectMessage statEffectMessage = (StatEffectMessage)message;
/*    */ 
/*    */     
/* 55 */     if (statEffectMessage.getCreatureType() == RpgCreatureConstants.Type.PLAYER_CREATURE_TYPE_ID.getTypeId()) {
/* 56 */       propNodeRegister = TcgGame.getPropNodeRegister();
/*    */     } else {
/* 58 */       propNodeRegister = TcgGame.getMonsterRegister();
/*    */     } 
/* 60 */     PropNode propNode = propNodeRegister.getPropNode(Integer.valueOf(statEffectMessage.getTargetId()));
/*    */     
/* 62 */     if (propNode != null) {
/* 63 */       if (statEffectMessage.getEffectStatId() == 12) {
/* 64 */         propNode.getEffects().tint(Effects.TintMode.FLASH);
/*    */         
/* 66 */         int effectSum = statEffectMessage.getEffectSum();
/*    */         
/* 68 */         if (effectSum < 0) {
/* 69 */           if (statEffectMessage.isCritDone()) {
/* 70 */             propNode.critFloatingText(-effectSum);
/* 71 */             playCritSound();
/*    */           } else {
/* 73 */             propNode.damageFloatingText(-effectSum);
/*    */           } 
/* 75 */         } else if (effectSum > 0) {
/*    */         
/*    */         } 
/*    */       } else {
/* 79 */         LOG.error("unhandled stat effect: statId=" + statEffectMessage.getEffectStatId());
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   private void playCritSound() {
/* 86 */     if (this.critSound != null && this.critSound.get() != null) {
/* 87 */       if (LoadingManager.USE) {
/* 88 */         LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundPlayCallable(this.critSound));
/*    */       } else {
/* 90 */         ((Sound)this.critSound.get()).play();
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 96 */     return 226;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\StatEffectProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */