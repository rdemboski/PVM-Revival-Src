/*     */ package com.funcom.rpgengine2;
/*     */ 
/*     */ import com.funcom.util.SizeCheckedArrayList;
/*     */ import java.util.List;
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
/*     */ public class Stat
/*     */   implements Cloneable
/*     */ {
/*     */   private static final float FIXED_POINT_SCALE = 100.0F;
/*     */   protected Short id;
/*     */   protected int base;
/*     */   protected int modifier;
/*     */   private Stat max;
/*     */   private Stat min;
/*     */   private List<StatListener> listeners;
/*     */   
/*     */   public Stat() {
/*  28 */     this.id = Short.valueOf((short)-1);
/*  29 */     this.base = -1;
/*     */   }
/*     */   
/*     */   public Stat(Short id, int base) {
/*  33 */     this.id = id;
/*  34 */     this.base = base;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stat(Stat source) {
/*  41 */     this.id = source.getId();
/*  42 */     this.base = source.getBase();
/*  43 */     this.modifier = source.getModifier();
/*  44 */     this.max = source.getMax();
/*  45 */     this.min = source.getMin();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stat clone() {
/*     */     try {
/*  55 */       return (Stat)super.clone();
/*  56 */     } catch (CloneNotSupportedException ignore) {
/*  57 */       throw new RuntimeException(ignore);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setId(Short id) {
/*  62 */     this.id = id;
/*     */   }
/*     */   
/*     */   public int getBase() {
/*  66 */     return limitBase();
/*     */   }
/*     */   
/*     */   private int limitBase() {
/*  70 */     int mod = getModifier();
/*  71 */     int sum = this.base + mod;
/*  72 */     int limitBase = this.base;
/*     */     
/*  74 */     if (this.max != null) {
/*     */       
/*  76 */       int maxSum = this.max.getSum();
/*  77 */       if (sum > maxSum) {
/*  78 */         limitBase = maxSum - mod;
/*     */       }
/*     */     } 
/*  81 */     if (this.min != null) {
/*     */       
/*  83 */       int minSum = this.min.getSum();
/*  84 */       if (sum < minSum) {
/*  85 */         limitBase = minSum - mod;
/*     */       }
/*     */     } 
/*  88 */     return limitBase;
/*     */   }
/*     */   
/*     */   public void set(Stat srcStat) {
/*  92 */     if (!getId().equals(srcStat.getId())) {
/*  93 */       throw new IllegalArgumentException("stat id mismatch");
/*     */     }
/*     */     
/*  96 */     int oldBase = getBase();
/*  97 */     int oldModifier = getModifier();
/*     */     
/*  99 */     this.base = srcStat.getBase();
/* 100 */     this.modifier = srcStat.getModifier();
/*     */     
/* 102 */     fireChanged(oldBase, oldModifier);
/*     */   }
/*     */   
/*     */   public void setBase(int base) {
/* 106 */     int oldBase = getBase();
/* 107 */     int mod = getModifier();
/* 108 */     int newSum = base + mod;
/*     */     
/* 110 */     if (this.max != null && newSum > this.max.getSum()) {
/*     */       
/* 112 */       this.base = this.max.getSum() - mod;
/* 113 */     } else if (this.min != null && newSum < this.min.getSum()) {
/*     */       
/* 115 */       this.base = this.min.getSum() - mod;
/*     */     } else {
/* 117 */       this.base = base;
/*     */     } 
/*     */     
/* 120 */     fireChanged(oldBase, getModifier());
/*     */   }
/*     */   
/*     */   public void addBase(int toAdd) {
/* 124 */     int oldBase = getBase();
/* 125 */     int mod = getModifier();
/* 126 */     int newSum = oldBase + mod + toAdd;
/*     */     
/* 128 */     if (this.max != null && newSum > this.max.getSum()) {
/*     */       
/* 130 */       this.base = this.max.getSum() - mod;
/* 131 */     } else if (this.min != null && newSum < this.min.getSum()) {
/*     */       
/* 133 */       this.base = this.min.getSum() - mod;
/*     */     } else {
/* 135 */       this.base += toAdd;
/*     */     } 
/*     */     
/* 138 */     fireChanged(oldBase, getModifier());
/*     */   }
/*     */   
/*     */   public int getModifier() {
/* 142 */     return this.modifier;
/*     */   }
/*     */   
/*     */   public void setModifier(int modifier) {
/* 146 */     int oldMod = getModifier();
/*     */     
/* 148 */     this.modifier = modifier;
/*     */     
/* 150 */     fireChanged(getBase(), oldMod);
/*     */   }
/*     */   
/*     */   public void addModifier(int toAdd) {
/* 154 */     int oldMod = getModifier();
/*     */     
/* 156 */     this.modifier += toAdd;
/*     */     
/* 158 */     fireChanged(getBase(), oldMod);
/*     */   }
/*     */   
/*     */   private void fireChanged(int oldBase, int oldModifier) {
/* 162 */     if (this.listeners != null && !this.listeners.isEmpty()) {
/* 163 */       int size = this.listeners.size();
/* 164 */       for (int i = 0; i < size; i++) {
/* 165 */         ((StatListener)this.listeners.get(i)).statChanged(this, oldBase, oldModifier);
/*     */       }
/*     */     } 
/*     */   }
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
/*     */   public Short getId() {
/* 181 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSum() {
/* 190 */     int ret = getBase() + getModifier();
/*     */     
/* 192 */     if (this.min != null) {
/* 193 */       int minSum = this.min.getSum();
/* 194 */       if (ret < minSum) {
/* 195 */         return minSum;
/*     */       }
/*     */     } 
/*     */     
/* 199 */     if (this.max != null) {
/* 200 */       int maxSum = this.max.getSum();
/* 201 */       if (ret > maxSum) {
/* 202 */         return maxSum;
/*     */       }
/*     */     } 
/*     */     
/* 206 */     return ret;
/*     */   }
/*     */   
/*     */   public float getSumAsFloat() {
/* 210 */     int intSum = getSum();
/* 211 */     return intToFloat(intSum);
/*     */   }
/*     */   
/*     */   public static float intToFloat(int intSum) {
/* 215 */     return intSum / 100.0F;
/*     */   }
/*     */   
/*     */   public static int floatToInt(float floatSum) {
/* 219 */     return (int)(floatSum * 100.0F);
/*     */   }
/*     */   
/*     */   public int getByPersistence(Persistence persistence) {
/* 223 */     switch (persistence) {
/*     */ 
/*     */ 
/*     */       
/*     */       case BASE:
/* 228 */         return getBase();
/*     */       
/*     */       case SUM:
/* 231 */         return getSum();
/*     */       
/*     */       case MODIFIER:
/* 234 */         return getModifier();
/*     */     } 
/*     */     
/* 237 */     throw new IllegalArgumentException("unknown persistence type: type=" + persistence);
/*     */   }
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
/*     */   public String toString() {
/* 256 */     StringBuffer sb = new StringBuffer();
/* 257 */     sb.append("[id=").append(this.id).append(",rawbase=").append(this.base).append(",rawmodifier=").append(this.modifier).append(",minId=").append((this.min != null) ? this.min.getId() : "none").append(",maxId=").append((this.max != null) ? this.max.getId() : "none").append(",listenersCount=").append((this.listeners != null) ? this.listeners.size() : 0).append("]");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 266 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public Stat getMax() {
/* 271 */     return this.max;
/*     */   }
/*     */   
/*     */   public Stat getMin() {
/* 275 */     return this.min;
/*     */   }
/*     */   
/*     */   public void setMax(Stat max) {
/* 279 */     this.max = max;
/*     */   }
/*     */   
/*     */   public void setMin(Stat min) {
/* 283 */     this.min = min;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMaxBase(int maxBase) {
/* 288 */     if (this.max != null)
/* 289 */       this.max.setBase(maxBase); 
/*     */   }
/*     */   
/*     */   public int getMaxBase() {
/* 293 */     if (this.max != null)
/* 294 */       return this.max.getBase(); 
/* 295 */     return -1;
/*     */   }
/*     */   
/*     */   public void setMaxModifier(int maxModifier) {
/* 299 */     if (this.max != null)
/* 300 */       this.max.setModifier(maxModifier); 
/*     */   }
/*     */   
/*     */   public int getMaxModifier() {
/* 304 */     if (this.max != null)
/* 305 */       return this.max.getModifier(); 
/* 306 */     return -1;
/*     */   }
/*     */   
/*     */   public void setMinBase(int minBase) {
/* 310 */     if (this.min != null)
/* 311 */       this.min.setBase(minBase); 
/*     */   }
/*     */   
/*     */   public int getMinBase() {
/* 315 */     if (this.min != null)
/* 316 */       return this.min.getBase(); 
/* 317 */     return -1;
/*     */   }
/*     */   
/*     */   public void setMinModifier(int minModifier) {
/* 321 */     if (this.min != null)
/* 322 */       this.min.setModifier(minModifier); 
/*     */   }
/*     */   public int getMinModifier() {
/* 325 */     if (this.min != null)
/* 326 */       return this.min.getModifier(); 
/* 327 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 332 */     int oldBase = getBase();
/* 333 */     int oldMod = getModifier();
/*     */     
/* 335 */     this.base = 0;
/* 336 */     this.modifier = 0;
/*     */     
/* 338 */     fireChanged(oldBase, oldMod);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addStatListener(StatListener listener) {
/* 343 */     if (this.listeners == null) {
/* 344 */       this.listeners = (List<StatListener>)new SizeCheckedArrayList(2, "Stat.listeners", 16);
/*     */     }
/* 346 */     this.listeners.add(listener);
/*     */   }
/*     */   
/*     */   public void removeStatListener(StatListener listener) {
/* 350 */     if (this.listeners != null) {
/* 351 */       this.listeners.remove(listener);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 357 */     if (this == o) return true; 
/* 358 */     if (o == null || !(o instanceof Stat)) return false;
/*     */     
/* 360 */     Stat stat = (Stat)o;
/*     */     
/* 362 */     if (this.base != stat.getBase()) return false; 
/* 363 */     if (this.modifier != stat.getModifier()) return false; 
/* 364 */     if (!this.id.equals(stat.getId())) return false;
/*     */     
/* 366 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 371 */     return this.id.hashCode();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\Stat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */