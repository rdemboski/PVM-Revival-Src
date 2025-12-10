/*    */ package com.funcom.tcg.client.ui;
/*    */ 
/*    */ import com.funcom.audio.Sound;
/*    */ import com.funcom.audio.SoundSystemFactory;
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.Reference;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.SoundCreateCallable;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.SoundPlayCallable;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.Callable;
/*    */ import org.jdom.Document;
/*    */ import org.jdom.Element;
/*    */ import org.jdom.filter.ElementFilter;
/*    */ import org.jdom.filter.Filter;
/*    */ 
/*    */ public class UISoundPlayer {
/*    */   private static final String GUI_SOUND_CONFIG = "ui/soundconfig.xml";
/*    */   private static final String SOUND_TAG = "Sound";
/* 22 */   private final Map<String, Reference<Sound>> sounds = new HashMap<String, Reference<Sound>>();
/*    */   private final ResourceManager resourceManager;
/*    */   
/*    */   public UISoundPlayer(ResourceManager resourceManager) {
/* 26 */     this.resourceManager = resourceManager;
/* 27 */     readConfig();
/*    */   }
/*    */   
/*    */   private void readConfig() {
/* 31 */     Document document = (Document)this.resourceManager.getManagedResource(Document.class, "ui/soundconfig.xml").getResource();
/* 32 */     Iterator<Element> soundElement = document.getDescendants((Filter)new ElementFilter("Sound"));
/* 33 */     while (soundElement.hasNext()) {
/* 34 */       Element element = soundElement.next();
/* 35 */       String resource = element.getChildTextTrim("Resource");
/* 36 */       Reference<Sound> sound = new Reference();
/*    */       
/* 38 */       if (LoadingManager.USE && LoadingManager.THREADED_SOUND) {
/* 39 */         LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundCreateCallable(sound, resource));
/*    */       } else {
/*    */         
/* 42 */         sound.set(SoundSystemFactory.getSoundSystem().getSound(resource));
/*    */       } 
/* 44 */       String name = element.getChildTextTrim("Name");
/* 45 */       if (sound != null) {
/* 46 */         this.sounds.put(name, sound);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public void play(String soundName) {
/* 52 */     Reference<Sound> sound = this.sounds.get(soundName);
/* 53 */     if (sound != null)
/* 54 */       if (LoadingManager.USE && LoadingManager.THREADED_SOUND) {
/* 55 */         LoadingManager.INSTANCE.submitSoundCallable((Callable)new SoundPlayCallable(sound));
/*    */       }
/* 57 */       else if (sound.get() != null) {
/* 58 */         ((Sound)sound.get()).play();
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\UISoundPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */