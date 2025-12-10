/*     */ package com.funcom.tcg.client.view.modular.part;
/*     */ 
/*     */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*     */ import com.funcom.rpgengine2.items.PlayerDescription;
/*     */ import com.funcom.tcg.client.model.rpg.VisualRegistry;
/*     */ import com.funcom.tcg.rpg.ClientDescriptionType;
/*     */ import com.funcom.tcg.rpg.ClientDescriptionVisual;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class HeadPart
/*     */   implements ModularDescription.Part
/*     */ {
/*     */   private final VisualRegistry visualRegistry;
/*     */   private final PlayerDescription playerDescription;
/*     */   
/*     */   public HeadPart(PlayerDescription playerDescription, VisualRegistry visualRegistry) {
/*  19 */     this.playerDescription = playerDescription;
/*  20 */     this.visualRegistry = visualRegistry;
/*     */   }
/*     */   
/*     */   public String getPartName() {
/*  24 */     return "head";
/*     */   }
/*     */   
/*     */   public boolean isVisible() {
/*  28 */     return true;
/*     */   }
/*     */   
/*     */   public String getMeshPath() {
/*  32 */     ClientDescriptionVisual faceVisual = this.visualRegistry.getDescriptionVisualForClassID(ClientDescriptionType.FACE, this.playerDescription.getFaceId());
/*     */ 
/*     */     
/*  35 */     if (faceVisual == null) {
/*  36 */       throw new IllegalStateException("No visual available for defined face: " + this.playerDescription.getFaceId());
/*     */     }
/*     */     
/*  39 */     return faceVisual.getMeshPath(this.playerDescription.getGender());
/*     */   }
/*     */   
/*     */   public List<ModularDescription.TexturePart> getTextureParts() {
/*  43 */     List<ModularDescription.TexturePart> result = new LinkedList<ModularDescription.TexturePart>();
/*     */     
/*  45 */     ClientDescriptionVisual faceVisual = this.visualRegistry.getDescriptionVisualForClassID(ClientDescriptionType.FACE, this.playerDescription.getFaceId());
/*     */ 
/*     */     
/*  48 */     if (faceVisual.getMaleTexturePath() != null && !faceVisual.getMaleTexturePath().isEmpty()) {
/*  49 */       result.add(new FaceTexturePart(faceVisual));
/*     */     } else {
/*     */       
/*  52 */       result.add(new ModularDescription.TexturePart() {
/*     */             public String getTextureMap() {
/*  54 */               return "skin";
/*     */             }
/*     */             
/*     */             public boolean isTransparent() {
/*  58 */               return false;
/*     */             }
/*     */             
/*     */             public int getTextureLayerCount() {
/*  62 */               return 1;
/*     */             }
/*     */             
/*     */             public List<String> getTextureLayers() {
/*  66 */               ClientDescriptionVisual skinVisual = HeadPart.this.visualRegistry.getDescriptionVisualForClassID(ClientDescriptionType.SKINCOLOR, HeadPart.this.playerDescription.getSkinColorId());
/*     */               
/*  68 */               return Arrays.asList(new String[] { skinVisual.getTexturePath(HeadPart.access$100(this.this$0).getGender()) });
/*     */             }
/*     */ 
/*     */             
/*     */             public ModularDescription.TextureLoaderDescription getTextureLoaderDescription() {
/*  73 */               return null;
/*     */             }
/*     */           });
/*     */       
/*  77 */       result.add(new ModularDescription.TexturePart() {
/*     */             public String getTextureMap() {
/*  79 */               return "eyes";
/*     */             }
/*     */             
/*     */             public boolean isTransparent() {
/*  83 */               return false;
/*     */             }
/*     */             
/*     */             public int getTextureLayerCount() {
/*  87 */               return 1;
/*     */             }
/*     */             
/*     */             public List<String> getTextureLayers() {
/*  91 */               ClientDescriptionVisual eyeVisual = HeadPart.this.visualRegistry.getDescriptionVisualForClassID(ClientDescriptionType.EYECOLOR, HeadPart.this.playerDescription.getEyeColorId());
/*     */               
/*  93 */               return Arrays.asList(new String[] { eyeVisual.getTexturePath(HeadPart.access$100(this.this$0).getGender()) });
/*     */             }
/*     */ 
/*     */             
/*     */             public ModularDescription.TextureLoaderDescription getTextureLoaderDescription() {
/*  98 */               return null;
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 103 */     return result;
/*     */   }
/*     */   
/*     */   private class FaceTexturePart implements ModularDescription.TexturePart {
/*     */     private ClientDescriptionVisual faceVisual;
/*     */     
/*     */     private FaceTexturePart(ClientDescriptionVisual faceVisual) {
/* 110 */       this.faceVisual = faceVisual;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getTextureMap() {
/* 115 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isTransparent() {
/* 120 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getTextureLayerCount() {
/* 125 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public ModularDescription.TextureLoaderDescription getTextureLoaderDescription() {
/* 130 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<String> getTextureLayers() {
/* 135 */       return Arrays.asList(new String[] { this.faceVisual.getTexturePath(HeadPart.access$100(this.this$0).getGender()) });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\view\modular\part\HeadPart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */