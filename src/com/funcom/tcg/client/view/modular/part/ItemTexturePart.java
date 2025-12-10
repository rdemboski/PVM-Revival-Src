/*    */ package com.funcom.tcg.client.view.modular.part;
/*    */ 
/*    */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*    */ import com.funcom.rpgengine2.items.PlayerDescription;
/*    */ import com.funcom.tcg.client.model.rpg.ClientItemVisual;
/*    */ import java.util.Arrays;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ItemTexturePart
/*    */   implements ModularDescription.TexturePart
/*    */ {
/*    */   private ClientItemVisual itemVisual;
/*    */   private PlayerDescription.Gender gender;
/*    */   
/*    */   public ItemTexturePart(ClientItemVisual itemVisual, PlayerDescription.Gender gender) {
/* 17 */     this.itemVisual = itemVisual;
/* 18 */     this.gender = gender;
/*    */   }
/*    */   
/*    */   public String getTextureMap() {
/* 22 */     return null;
/*    */   }
/*    */   
/*    */   public boolean isTransparent() {
/* 26 */     return (this.itemVisual != null && this.itemVisual.isTransparent());
/*    */   }
/*    */   
/*    */   public int getTextureLayerCount() {
/* 30 */     return (this.itemVisual == null) ? 0 : 1;
/*    */   }
/*    */   
/*    */   public List<String> getTextureLayers() {
/* 34 */     if (this.itemVisual == null)
/* 35 */       return new LinkedList<String>(); 
/* 36 */     if (this.gender == PlayerDescription.Gender.FEMALE)
/* 37 */       return Arrays.asList(new String[] { this.itemVisual.getFemaleTexturePath() }); 
/* 38 */     if (this.gender == PlayerDescription.Gender.MALE) {
/* 39 */       return Arrays.asList(new String[] { this.itemVisual.getMaleTexturePath() });
/*    */     }
/* 41 */     throw new IllegalStateException("Unknown gender");
/*    */   }
/*    */ 
/*    */   
/*    */   public ModularDescription.TextureLoaderDescription getTextureLoaderDescription() {
/* 46 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\view\modular\part\ItemTexturePart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */