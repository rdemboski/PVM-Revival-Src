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
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.ImmunityEffectMessage;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.Callable;
/*    */ 
/*    */ public class ImmunityEffectProcessor
/*    */   implements MessageProcessor
/*    */ {
/* 26 */   private Reference<Sound> immuneSound = new Reference();
/*    */   
/*    */   public ImmunityEffectProcessor() {
/* 29 */     ResourceManager resourceManager = TcgGame.getResourceManager();
/* 30 */     ExtProperties config = (ExtProperties)resourceManager.getResource(ExtProperties.class, "configuration/audio/audioconfig.properties");
/* 31 */     String immuneResource = config.getProperty("global.sound.immune");
/* 32 */     if (immuneResource != null) {
/* 33 */       if (LoadingManager.USE) {
/* 34 */         LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundCreateCallable(this.immuneSound, immuneResource));
/*    */       } else {
/*    */         
/* 37 */         this.immuneSound.set(SoundSystemFactory.getSoundSystem().getSound(immuneResource));
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 44 */     ImmunityEffectMessage immunityEffectMessage = (ImmunityEffectMessage)message;
/* 45 */     LocalClientPlayer playerModel = MainGameState.getPlayerModel();
/* 46 */     PropNode node = TcgGame.getMonsterRegister().getPropNode(Integer.valueOf(immunityEffectMessage.getTargetId()));
/* 47 */     if (node != null) {
/* 48 */       int hitCounter = playerModel.incrementAndGetImmunityHitCounter();
/* 49 */       if (hitCounter % 2 == 1) {
/* 50 */         node.processImmunityText(TcgGame.getLocalizedText("immunity.change.pet", new String[0]), false);
/*    */       } else {
/* 52 */         node.processImmunityText(TcgGame.getLocalizedText("immunity.no.damage", new String[0]), true);
/*    */       } 
/* 54 */       playImmuneSound();
/*    */     } 
/*    */   }
/*    */   
/*    */   private void playImmuneSound() {
/* 59 */     if (this.immuneSound != null && this.immuneSound.get() != null) {
/* 60 */       if (LoadingManager.USE) {
/* 61 */         LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundPlayCallable(this.immuneSound));
/*    */       } else {
/* 63 */         ((Sound)this.immuneSound.get()).play();
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 69 */     return 240;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\ImmunityEffectProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */