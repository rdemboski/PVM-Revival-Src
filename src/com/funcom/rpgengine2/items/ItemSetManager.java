/*    */ package com.funcom.rpgengine2.items;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemSetManager
/*    */ {
/* 10 */   private Map<ItemSetIdentifier, ItemDescription> itemSetMap = new HashMap<ItemSetIdentifier, ItemDescription>();
/*    */   
/*    */   public void addItemSetModifier(String setId, int numItem, ItemDescription bonus) {
/* 13 */     this.itemSetMap.put(new ItemSetIdentifier(setId, numItem), bonus);
/*    */   }
/*    */   
/*    */   public Item getItemForSet(String setId, int numItems) {
/* 17 */     ItemSetIdentifier ident = new ItemSetIdentifier(setId, numItems);
/* 18 */     ItemDescription itemDesc = this.itemSetMap.get(ident);
/* 19 */     if (itemDesc != null) {
/* 20 */       return itemDesc.createInstance();
/*    */     }
/* 22 */     return null;
/*    */   }
/*    */   
/*    */   public void clear() {
/* 26 */     this.itemSetMap.clear();
/*    */   }
/*    */   
/*    */   private class ItemSetIdentifier {
/*    */     private String setId;
/*    */     private int numItems;
/*    */     
/*    */     private ItemSetIdentifier(String setId, int numItems) {
/* 34 */       this.setId = setId;
/* 35 */       this.numItems = numItems;
/*    */     }
/*    */     
/*    */     public String getSetId() {
/* 39 */       return this.setId;
/*    */     }
/*    */     
/*    */     public int getNumItems() {
/* 43 */       return this.numItems;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean equals(Object o) {
/* 48 */       if (this == o) return true; 
/* 49 */       if (o == null || getClass() != o.getClass()) return false;
/*    */       
/* 51 */       ItemSetIdentifier that = (ItemSetIdentifier)o;
/*    */       
/* 53 */       if (this.numItems != that.numItems) return false; 
/* 54 */       if ((this.setId != null) ? !this.setId.equals(that.setId) : (that.setId != null)) return false;
/*    */       
/* 56 */       return true;
/*    */     }
/*    */ 
/*    */     
/*    */     public int hashCode() {
/* 61 */       int result = (this.setId != null) ? this.setId.hashCode() : 0;
/* 62 */       result = 31 * result + this.numItems;
/* 63 */       return result;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\items\ItemSetManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */