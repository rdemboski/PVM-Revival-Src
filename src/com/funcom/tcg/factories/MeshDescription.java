/*    */ package com.funcom.tcg.factories;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.jdom.Element;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MeshDescription
/*    */ {
/*    */   private static final String ELEMENT_SUBMODEL = "submodel";
/*    */   private static final String ELEMENT_MESH = "mesh";
/*    */   private static final String ELEMENT_SCALE = "scale";
/*    */   private static final String ELEMENT_ANIMATIONS = "animations";
/*    */   private static final String ATTR_ANIMNAME = "idle";
/*    */   private String meshPath;
/*    */   private float scaleBase;
/* 19 */   private List<AnimationDescription> animations = new ArrayList<AnimationDescription>();
/*    */   
/*    */   public MeshDescription(Element element) {
/* 22 */     initializeDescription(element);
/*    */   }
/*    */   
/*    */   private void checkRootElement(Element element, String name) {
/* 26 */     if (element == null)
/* 27 */       throw new IllegalStateException("No Element descriptor set."); 
/* 28 */     if (!element.getName().equals(name))
/* 29 */       throw new IllegalStateException("This Element does not describe artifact: " + name); 
/*    */   }
/*    */   
/*    */   private void initializeDescription(Element element) {
/* 33 */     element = element.getChild("submodel");
/* 34 */     checkRootElement(element, "submodel");
/*    */     
/* 36 */     this.meshPath = element.getChildTextTrim("mesh");
/* 37 */     String scaleString = element.getChildTextTrim("scale");
/*    */     
/* 39 */     this.scaleBase = 1.0F;
/*    */     try {
/* 41 */       this.scaleBase = Float.parseFloat(scaleString);
/* 42 */     } catch (NumberFormatException e) {}
/*    */ 
/*    */ 
/*    */     
/* 46 */     List<Element> animationElements = element.getChild("animations").getChildren();
/* 47 */     for (Element animation : animationElements) {
/* 48 */       String animName = animation.getAttributeValue("idle");
/* 49 */       String animPath = animation.getTextTrim();
/*    */       
/* 51 */       this.animations.add(new AnimationDescription(animName, animPath));
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getMeshPath() {
/* 56 */     return this.meshPath;
/*    */   }
/*    */   
/*    */   public float getScaleBase() {
/* 60 */     return this.scaleBase;
/*    */   }
/*    */   
/*    */   public List<AnimationDescription> getAnimations() {
/* 64 */     return this.animations;
/*    */   }
/*    */   
/*    */   public class AnimationDescription {
/*    */     private String name;
/*    */     private String path;
/*    */     
/*    */     public AnimationDescription(String name, String path) {
/* 72 */       this.name = name;
/* 73 */       this.path = path;
/*    */     }
/*    */     
/*    */     public String getName() {
/* 77 */       return this.name;
/*    */     }
/*    */     
/*    */     public String getPath() {
/* 81 */       return this.path;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\factories\MeshDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */