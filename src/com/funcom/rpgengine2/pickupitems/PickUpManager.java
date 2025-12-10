/*    */ package com.funcom.rpgengine2.pickupitems;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.TreeMap;
/*    */ 
/*    */ public class PickUpManager {
/*  9 */   private Map<String, PickupType> typesByName = new HashMap<String, PickupType>();
/* 10 */   private Map<String, AbstractPickUpDescription> descriptionsById = new HashMap<String, AbstractPickUpDescription>();
/*    */ 
/*    */   
/* 13 */   private HashMap<String, TreeMap<Integer, AbstractPickUpDescription>> descriptionsBySplittableGroupId = new HashMap<String, TreeMap<Integer, AbstractPickUpDescription>>();
/*    */   
/*    */   public AbstractPickUpDescription getDescription(String id) {
/* 16 */     return this.descriptionsById.get(id);
/*    */   }
/*    */   
/*    */   public boolean hasDescription(String id) {
/* 20 */     return this.descriptionsById.containsKey(id);
/*    */   }
/*    */   
/*    */   public void addDescription(AbstractPickUpDescription description) {
/* 24 */     this.descriptionsById.put(description.getId(), description);
/*    */     
/* 26 */     if (!description.getSplitGroupID().isEmpty()) {
/* 27 */       if (!this.descriptionsBySplittableGroupId.containsKey(description.getSplitGroupID())) {
/* 28 */         this.descriptionsBySplittableGroupId.put(description.getSplitGroupID(), new TreeMap<Integer, AbstractPickUpDescription>());
/*    */       }
/*    */       
/* 31 */       ((TreeMap<Integer, AbstractPickUpDescription>)this.descriptionsBySplittableGroupId.get(description.getSplitGroupID())).put(Integer.valueOf(description.getAmount()), description);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void clearData() {
/* 36 */     this.descriptionsById.clear();
/* 37 */     this.descriptionsBySplittableGroupId.clear();
/*    */   }
/*    */   
/*    */   public TreeMap<Integer, AbstractPickUpDescription> getDescriptionsBySplittableGroupId(String groupID) {
/* 41 */     return this.descriptionsBySplittableGroupId.get(groupID);
/*    */   }
/*    */   
/*    */   public Collection<TreeMap<Integer, AbstractPickUpDescription>> getSplittablePickupItems() {
/* 45 */     return this.descriptionsBySplittableGroupId.values();
/*    */   }
/*    */   
/*    */   public PickupType getType(String name) {
/* 49 */     PickupType type = this.typesByName.get(name);
/* 50 */     if (type != null) {
/* 51 */       return type;
/*    */     }
/* 53 */     throw new IllegalArgumentException("unknown pick up type: type=" + name);
/*    */   }
/*    */   
/*    */   public void putType(PickupType type) {
/* 57 */     if (this.typesByName.containsKey(type.getName())) {
/* 58 */       throw new IllegalArgumentException("pick up type already exists: typeName=" + type.getName());
/*    */     }
/*    */     
/* 61 */     this.typesByName.put(type.getName(), type);
/*    */   }
/*    */   
/*    */   public Collection<AbstractPickUpDescription> getAll() {
/* 65 */     return this.descriptionsById.values();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\pickupitems\PickUpManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */