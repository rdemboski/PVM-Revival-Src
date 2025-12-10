/*    */ package com.funcom.tcg.client.view.modular.part;
/*    */ 
/*    */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*    */ import com.funcom.rpgengine2.items.PlayerDescription;
/*    */ import com.funcom.tcg.client.model.rpg.ClientItemVisual;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ItemPart
/*    */   implements ModularDescription.Part {
/*    */   private final String partName;
/*    */   private final ClientItemVisual itemVisual;
/*    */   private final PlayerDescription.Gender gender;
/*    */   
/*    */   public ItemPart(String partName, ClientItemVisual itemVisual, PlayerDescription.Gender gender) {
/* 16 */     this.partName = partName;
/* 17 */     this.gender = gender;
/* 18 */     this.itemVisual = itemVisual;
/*    */   }
/*    */   
/*    */   public String getPartName() {
/* 22 */     return this.partName;
/*    */   }
/*    */   
/*    */   public boolean isVisible() {
/* 26 */     return true;
/*    */   }
/*    */   
/*    */   public String getMeshPath() {
/* 30 */     if (this.itemVisual == null)
/* 31 */       return null; 
/* 32 */     if (this.gender == PlayerDescription.Gender.FEMALE)
/* 33 */       return this.itemVisual.getFemaleMeshPath(); 
/* 34 */     if (this.gender == PlayerDescription.Gender.MALE) {
/* 35 */       return this.itemVisual.getMaleMeshPath();
/*    */     }
/* 37 */     throw new IllegalStateException("Unknown gender");
/*    */   }
/*    */   
/*    */   public List<ModularDescription.TexturePart> getTextureParts() {
/* 41 */     List<ModularDescription.TexturePart> result = new LinkedList<ModularDescription.TexturePart>();
/* 42 */     result.add(new ItemTexturePart(this.itemVisual, this.gender));
/* 43 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\view\modular\part\ItemPart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */