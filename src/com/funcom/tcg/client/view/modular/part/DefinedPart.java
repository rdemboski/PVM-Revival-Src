/*    */ package com.funcom.tcg.client.view.modular.part;
/*    */ 
/*    */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*    */ import java.util.Arrays;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DefinedPart
/*    */   implements ModularDescription.Part
/*    */ {
/*    */   private String partName;
/*    */   private String meshPath;
/*    */   private String texturePath;
/*    */   private boolean transparent;
/*    */   
/*    */   public DefinedPart(String partName, String meshPath, String texturePath, boolean transparent) {
/* 17 */     this.partName = partName;
/* 18 */     this.meshPath = meshPath;
/* 19 */     this.texturePath = texturePath;
/* 20 */     this.transparent = transparent;
/*    */   }
/*    */   
/*    */   public String getPartName() {
/* 24 */     return this.partName;
/*    */   }
/*    */   
/*    */   public boolean isVisible() {
/* 28 */     return true;
/*    */   }
/*    */   
/*    */   public String getMeshPath() {
/* 32 */     return this.meshPath;
/*    */   }
/*    */   
/*    */   public List<ModularDescription.TexturePart> getTextureParts() {
/* 36 */     List<ModularDescription.TexturePart> result = new LinkedList<ModularDescription.TexturePart>();
/* 37 */     if (this.texturePath != null) {
/* 38 */       result.add(new ModularDescription.TexturePart() {
/*    */             public String getTextureMap() {
/* 40 */               return null;
/*    */             }
/*    */             
/*    */             public boolean isTransparent() {
/* 44 */               return DefinedPart.this.transparent;
/*    */             }
/*    */             
/*    */             public int getTextureLayerCount() {
/* 48 */               return 1;
/*    */             }
/*    */             
/*    */             public List<String> getTextureLayers() {
/* 52 */               return Arrays.asList(new String[] { DefinedPart.this.texturePath });
/*    */             }
/*    */ 
/*    */             
/*    */             public ModularDescription.TextureLoaderDescription getTextureLoaderDescription() {
/* 57 */               return null;
/*    */             }
/*    */           });
/*    */     }
/* 61 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\view\modular\part\DefinedPart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */