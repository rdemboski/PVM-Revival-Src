/*    */ package com.funcom.gameengine.resourcemanager;
/*    */ 
/*    */ public class ResourceTypeKey
/*    */ {
/*    */   private String name;
/*    */   private Class type;
/*    */   
/*    */   public ResourceTypeKey(String name, Class type) {
/*  9 */     if (name == null)
/* 10 */       throw new IllegalArgumentException("name = null"); 
/* 11 */     if (type == null) {
/* 12 */       throw new IllegalArgumentException("type = null");
/*    */     }
/* 14 */     this.name = name;
/* 15 */     this.type = type;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 20 */     int result = this.name.hashCode();
/* 21 */     result = 31 * result + this.type.hashCode();
/* 22 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 27 */     if (this == o) return true; 
/* 28 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 30 */     ResourceTypeKey that = (ResourceTypeKey)o;
/*    */     
/* 32 */     return (this.name.equals(that.name) && this.type.equals(that.type));
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\ResourceTypeKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */