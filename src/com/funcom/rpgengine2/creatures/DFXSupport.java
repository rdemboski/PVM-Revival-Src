/*    */ package com.funcom.rpgengine2.creatures;
/*    */ 
/*    */ import com.funcom.commons.dfx.DireEffect;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DFXSupport
/*    */   implements RpgQueryableSupport, RpgUpdateable
/*    */ {
/* 13 */   private static final List<DireEffect> EMPTY = Collections.unmodifiableList(new ArrayList<DireEffect>(0));
/* 14 */   private static final int[] UPDATE_PRIORITIES = new int[] { 499999 };
/*    */   
/*    */   private List<DireEffect> dfxs;
/*    */   
/*    */   private boolean dataChanged;
/* 19 */   private List<DireEffect> dfxViewList = EMPTY;
/*    */   
/*    */   public DFXSupport() {
/* 22 */     this.dfxs = new ArrayList<DireEffect>();
/*    */   }
/*    */   
/*    */   public void add(DireEffect dfx) {
/* 26 */     if (!dfx.isFinished()) {
/* 27 */       if (!this.dfxs.contains(dfx)) {
/* 28 */         this.dfxs.add(dfx);
/* 29 */         this.dataChanged = true;
/*    */       } else {
/* 31 */         throw new IllegalArgumentException("the input dire effect is already added");
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public int getNumberOfRunningDFX() {
/* 37 */     return this.dfxs.size();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<DireEffect> getDfxViewList() {
/* 46 */     return this.dfxViewList;
/*    */   }
/*    */   
/*    */   public int[] getUpdatePriorities() {
/* 50 */     return UPDATE_PRIORITIES;
/*    */   }
/*    */   
/*    */   public void update(int priority, long updateMillis) {
/* 54 */     double updateSeconds = updateMillis / 1000.0D;
/* 55 */     int size = this.dfxs.size();
/*    */     
/*    */     int i;
/* 58 */     for (i = 0; i < size; i++) {
/* 59 */       ((DireEffect)this.dfxs.get(i)).update(updateSeconds);
/*    */     }
/*    */ 
/*    */     
/* 63 */     for (i = size - 1; i >= 0; i--) {
/* 64 */       if (((DireEffect)this.dfxs.get(i)).isFinished()) {
/* 65 */         this.dfxs.remove(i);
/* 66 */         this.dataChanged = true;
/*    */       } 
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 72 */     if (this.dataChanged) {
/* 73 */       this.dataChanged = false;
/*    */       
/* 75 */       if (this.dfxs.isEmpty()) {
/* 76 */         this.dfxViewList = EMPTY;
/*    */       } else {
/* 78 */         size = this.dfxs.size();
/* 79 */         ArrayList<DireEffect> tmp = new ArrayList<DireEffect>(size);
/* 80 */         for (int j = 0; j < size; j++) {
/* 81 */           tmp.add(this.dfxs.get(j));
/*    */         }
/* 83 */         this.dfxViewList = Collections.unmodifiableList(tmp);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void clear() {
/* 92 */     int size = this.dfxs.size();
/* 93 */     for (int i = 0; i < size; i++) {
/* 94 */       DireEffect effect = this.dfxs.get(i);
/* 95 */       effect.forceFinish();
/*    */     } 
/* 97 */     this.dfxs.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\DFXSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */