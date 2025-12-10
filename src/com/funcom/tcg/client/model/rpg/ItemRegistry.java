/*    */ package com.funcom.tcg.client.model.rpg;
/*    */ 
/*    */ import com.funcom.rpgengine2.SkillId;
/*    */ import com.funcom.rpgengine2.items.DefaultItemFactory;
/*    */ import com.funcom.rpgengine2.items.ItemDescription;
/*    */ import com.funcom.rpgengine2.items.ItemType;
/*    */ import com.funcom.rpgengine2.loader.RpgLoader;
/*    */ import com.funcom.tcg.client.ui.errorreporting.ErrorWindowCreator;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class ItemRegistry {
/*    */   private RpgLoader rpgLoader;
/*    */   private Map<SkillId, ClientItem> items;
/*    */   
/*    */   public ItemRegistry(RpgLoader rpgLoader) {
/* 20 */     this.rpgLoader = rpgLoader;
/* 21 */     this.items = new HashMap<SkillId, ClientItem>();
/*    */   }
/*    */   
/*    */   public void reload() {
/* 25 */     this.items.clear();
/*    */     
/* 27 */     readData();
/*    */   }
/*    */ 
/*    */   
/*    */   public void readData() {
/* 32 */     List<ItemDescription> itemList = this.rpgLoader.getItemManager().getAll();
/* 33 */     for (ItemDescription item : itemList) {
/* 34 */       ClientItem clientItem = new ClientItem(item);
/* 35 */       this.items.put(new SkillId(clientItem.getClassId(), clientItem.getTier()), clientItem);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ClientItem getItemForClassID(String classId, int tier) {
/* 47 */     ClientItem item = this.items.get(new SkillId(classId, tier));
/* 48 */     if (item == null) {
/* 49 */       ItemDescription description = new ItemDescription(classId, Integer.valueOf(tier), DefaultItemFactory.INSTANCE);
/* 50 */       description.setName(classId);
/*    */       
/* 52 */       description.setItemType(ItemType.UNKNOWN);
/* 53 */       item = new ClientItem(description);
/*    */       
/* 55 */       ErrorWindowCreator.instance().createErrorMessage("RPG Data Error", "No rpg data exists for item: " + classId + " tier: " + tier);
/*    */     } 
/* 57 */     return item;
/*    */   }
/*    */   
/*    */   public Set<ClientItem> getBackReferences(String ref, int tier) {
/* 61 */     Set<ClientItem> ret = new HashSet<ClientItem>();
/* 62 */     Set<String> refs = this.rpgLoader.getItemManager().getBackReferences(ref);
/* 63 */     if (refs != null)
/* 64 */       for (String val : refs) {
/* 65 */         ret.add(getItemForClassID(val, tier));
/*    */       } 
/* 67 */     return ret;
/*    */   }
/*    */   
/*    */   public String itemReferencesAs(String itemId) {
/* 71 */     return this.rpgLoader.getItemManager().itemReferencesAs(itemId);
/*    */   }
/*    */   
/*    */   public boolean containsClassID(String classID, int tier) {
/* 75 */     return this.items.containsKey(new SkillId(classID, tier));
/*    */   }
/*    */   
/*    */   public Set<SkillId> getAllIds() {
/* 79 */     return this.items.keySet();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\rpg\ItemRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */