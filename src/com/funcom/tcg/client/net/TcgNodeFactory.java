/*    */ package com.funcom.tcg.client.net;
/*    */ 
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.rpgengine2.loader.RpgLoader;
/*    */ import com.funcom.tcg.client.net.creaturebuilders.CreatureBuilder;
/*    */ import com.funcom.tcg.client.net.creaturebuilders.MonsterBuilder;
/*    */ import com.funcom.tcg.client.net.creaturebuilders.QuestGiverBuilder;
/*    */ import com.funcom.tcg.client.net.creaturebuilders.QuestInteractibleBuilder;
/*    */ import com.funcom.tcg.client.net.creaturebuilders.VendorBuilder;
/*    */ import com.funcom.tcg.net.message.CreatureCreationMessage;
/*    */ import com.funcom.tcg.net.message.VendorCreationMessage;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class TcgNodeFactory
/*    */ {
/* 17 */   private Set<CreatureBuilder> creatureBuilders = new HashSet<CreatureBuilder>();
/*    */ 
/*    */   
/*    */   public PropNode createForType(CreatureCreationMessage message) {
/* 21 */     for (CreatureBuilder creatureBuilder : this.creatureBuilders) {
/* 22 */       if (creatureBuilder.isBuildable(message.getFaction()))
/* 23 */         return creatureBuilder.build(message); 
/* 24 */     }  throw new IllegalStateException("Don't know how to build: " + message.getType());
/*    */   }
/*    */   
/*    */   public PropNode createForType(VendorCreationMessage vendorCreationMessage) {
/* 28 */     VendorBuilder vendorBuilder = new VendorBuilder();
/* 29 */     return vendorBuilder.build(vendorCreationMessage);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void addDefaultBuilders(TcgNodeFactory instance, RpgLoader rpgLoader) {
/* 34 */     instance.creatureBuilders.add(new MonsterBuilder(rpgLoader.getMonsterManager()));
/* 35 */     instance.creatureBuilders.add(new QuestInteractibleBuilder(rpgLoader.getMonsterManager()));
/* 36 */     instance.creatureBuilders.add(new QuestGiverBuilder(rpgLoader.getMonsterManager(), rpgLoader.getQuestManager()));
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\TcgNodeFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */