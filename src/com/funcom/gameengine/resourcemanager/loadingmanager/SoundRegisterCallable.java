/*    */ package com.funcom.gameengine.resourcemanager.loadingmanager;
/*    */ 
/*    */ import com.funcom.audio.Sound;
/*    */ import com.funcom.gameengine.view.RepresentationalNode;
/*    */ import java.util.concurrent.Callable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SoundRegisterCallable
/*    */   implements Callable
/*    */ {
/* 16 */   Reference<Sound> snd = null;
/* 17 */   RepresentationalNode representationalNode = null;
/*    */   public SoundRegisterCallable(Reference<Sound> snd, RepresentationalNode representationalNode) {
/* 19 */     this.snd = snd;
/* 20 */     this.representationalNode = representationalNode;
/*    */   }
/*    */   public Reference<Sound> call() {
/* 23 */     if (this.snd.get() != null && this.representationalNode != null)
/* 24 */       this.representationalNode.registerSound(this.snd.get()); 
/* 25 */     return this.snd;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\SoundRegisterCallable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */