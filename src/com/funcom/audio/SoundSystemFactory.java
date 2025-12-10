/*    */ package com.funcom.audio;
/*    */ 
/*    */ import com.funcom.audio.dummy.DummySoundSystem;
/*    */ import com.funcom.audio.fmod.FModSoundSystem;
/*    */ import java.lang.reflect.InvocationHandler;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.lang.reflect.Proxy;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SoundSystemFactory
/*    */ {
/* 23 */   private static SoundSystem soundSystem = (SoundSystem)new FModSoundSystem();
/* 24 */   private static DummySoundSystem dummySoundSystem = new DummySoundSystem(); private static SoundSystem proxy;
/*    */   static {
/* 26 */     InvocationHandler invocationHandler = new SoundInvocationHandler();
/* 27 */     proxy = (SoundSystem)Proxy.newProxyInstance(SoundSystemFactory.class.getClassLoader(), new Class[] { SoundSystem.class }, invocationHandler);
/*    */ 
/*    */ 
/*    */     
/* 31 */     if (System.getProperty("sound.mute") != null && System.getProperty("sound.mute").equalsIgnoreCase("true"))
/* 32 */       proxy.setMute(true); 
/*    */   }
/*    */   private static boolean dummyEnabled;
/*    */   
/*    */   public static SoundSystem getSoundSystem() {
/* 37 */     return proxy;
/*    */   }
/*    */   
/*    */   public static void setDummyEnabled(boolean dummyEnabled) {
/* 41 */     SoundSystemFactory.dummyEnabled = dummyEnabled;
/*    */   }
/*    */   
/*    */   private static class SoundInvocationHandler implements InvocationHandler { private SoundInvocationHandler() {}
/*    */     
/*    */     public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/*    */       DummySoundSystem dummySoundSystem = null;
/* 48 */       if (!SoundSystemFactory.dummyEnabled) {
/* 49 */         SoundSystem callTarget = SoundSystemFactory.soundSystem;
/*    */       } else {
/* 51 */         dummySoundSystem = SoundSystemFactory.dummySoundSystem;
/*    */       } 
/*    */       try {
/* 54 */         return method.invoke(dummySoundSystem, args);
/* 55 */       } catch (InvocationTargetException e) {
/* 56 */         throw e.getCause();
/*    */       } 
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\audio\SoundSystemFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */