/*     */ package com.funcom.server.common.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IntegerIDMap<V>
/*     */ {
/*     */   private int maxCapacity;
/*     */   private int offset;
/*     */   private V[] values;
/*     */   
/*     */   public IntegerIDMap() {
/*  25 */     this(65535);
/*     */   }
/*     */   
/*     */   public IntegerIDMap(int maxCapacity) {
/*  29 */     this.maxCapacity = maxCapacity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(int key, V value) {
/*  39 */     if (value == null) {
/*  40 */       throw new NullPointerException("value cannot be null");
/*     */     }
/*  42 */     ensureValidIndex(key);
/*  43 */     this.values[key + this.offset] = value;
/*     */   }
/*     */   
/*     */   public void remove(int key) {
/*  47 */     int index = key + this.offset;
/*  48 */     if (index >= 0 && index < this.values.length) {
/*  49 */       this.values[index] = null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensureValidIndex(int key) {
/*  56 */     if (this.values == null) {
/*     */       
/*  58 */       this.values = (V[])new Object[1];
/*  59 */       this.offset = -key;
/*     */     } else {
/*  61 */       int index = key + this.offset;
/*  62 */       boolean keyOutOfBounds = (index < 0 || index >= this.values.length);
/*     */       
/*  64 */       if (keyOutOfBounds) {
/*  65 */         int min = Math.min(key, -this.offset);
/*  66 */         int max = Math.max(key, this.values.length - this.offset - 1);
/*     */         
/*  68 */         int len = max - min + 1;
/*     */         
/*  70 */         if (len > this.maxCapacity) {
/*  71 */           throw new IllegalStateException("max capacity exceeded: max capacity=" + this.maxCapacity + " req.capacity=" + len);
/*     */         }
/*     */         
/*  74 */         Object[] tmp = new Object[len];
/*  75 */         System.arraycopy(this.values, 0, tmp, -this.offset - min, this.values.length);
/*  76 */         this.values = (V[])tmp;
/*  77 */         this.offset = -min;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public V get(int key) {
/*  83 */     if (this.values != null) {
/*  84 */       int index = key + this.offset;
/*  85 */       if (index >= 0 && index < this.values.length) {
/*  86 */         return this.values[index];
/*     */       }
/*     */     } 
/*  89 */     return null;
/*     */   }
/*     */   
/*     */   public boolean contains(int key) {
/*  93 */     return (get(key) != null);
/*     */   }
/*     */   
/*     */   public int getCapacity() {
/*  97 */     if (this.values != null) {
/*  98 */       return this.values.length;
/*     */     }
/*     */     
/* 101 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\commo\\util\IntegerIDMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */