/*    */ package com.funcom.audio.fmod;
/*    */ 
/*    */ import com.funcom.audio.Ear;
/*    */ import com.funcom.audio.SoundSystemException;
/*    */ import com.funcom.audio.Vector3f;
/*    */ import org.jouvieje.FmodEx.Enumerations.FMOD_RESULT;
/*    */ import org.jouvieje.FmodEx.Structures.FMOD_VECTOR;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FModEar
/*    */   implements Ear
/*    */ {
/*    */   private FModSoundSystem soundSystem;
/* 16 */   private Vector3f pos = new Vector3f();
/*    */   private FMOD_VECTOR fmodPos;
/*    */   
/*    */   public FModEar(FModSoundSystem soundSystem) throws SoundSystemException {
/* 20 */     this.soundSystem = soundSystem;
/* 21 */     this.pos = new Vector3f();
/*    */     
/* 23 */     FMOD_VECTOR forward = FMOD_VECTOR.create(0.0F, 0.0F, -1.0F);
/* 24 */     FMOD_VECTOR up = FMOD_VECTOR.create(0.0F, -1.0F, 0.0F);
/*    */ 
/*    */     
/* 27 */     FMOD_RESULT result = soundSystem.getEventSystem().set3DNumListeners(1);
/*    */     
/* 29 */     FModSoundSystem.throwOnError(result);
/*    */     
/* 31 */     result = soundSystem.getEventSystem().set3DListenerAttributes(0, null, null, forward, up);
/*    */     
/* 33 */     FModSoundSystem.throwOnError(result);
/*    */     
/* 35 */     forward.release();
/* 36 */     up.release();
/*    */     
/* 38 */     this.fmodPos = FMOD_VECTOR.create();
/*    */   }
/*    */   
/*    */   public Vector3f getPos() {
/* 42 */     return this.pos;
/*    */   }
/*    */   
/*    */   public void update() {
/* 46 */     this.fmodPos.setXYZ(this.pos.getX(), this.pos.getY(), this.pos.getZ());
/*    */     
/* 48 */     FMOD_RESULT result = this.soundSystem.getEventSystem().set3DListenerAttributes(0, this.fmodPos, null, null, null);
/* 49 */     FModSoundSystem.isOk(result);
/*    */   }
/*    */   
/*    */   protected void finalize() throws Throwable {
/* 53 */     super.finalize();
/* 54 */     this.fmodPos.release();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\audio\fmod\FModEar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */