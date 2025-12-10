/*    */ package com.funcom.tcg.client.model.rpg;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.rpgengine2.loader.RpgLoader;
/*    */ 
/*    */ public class RegistryFactory {
/*    */   public static PetRegistry createPetRegistry(ResourceManager resourceManager, VisualRegistry visualRegistry, ItemRegistry itemRegistry) {
/*  8 */     PetRegistry petRegistry = new PetRegistry(resourceManager, visualRegistry, itemRegistry);
/*  9 */     petRegistry.readPetDescriptionData();
/* 10 */     petRegistry.readPetSkillsData();
/*    */     
/* 12 */     return petRegistry;
/*    */   }
/*    */   
/*    */   public static ItemRegistry createItemRegistry(RpgLoader rpgLoader) {
/* 16 */     ItemRegistry itemRegistry = new ItemRegistry(rpgLoader);
/* 17 */     itemRegistry.readData();
/*    */     
/* 19 */     return itemRegistry;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\rpg\RegistryFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */