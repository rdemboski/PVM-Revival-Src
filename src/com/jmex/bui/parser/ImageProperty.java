/*    */ package com.jmex.bui.parser;
/*    */ 
/*    */ import com.jmex.bui.property.Property;
/*    */ import com.jmex.bui.provider.ResourceProvider;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class ImageProperty
/*    */   extends Property
/*    */   implements ImageExtractable
/*    */ {
/*    */   private String image;
/*    */   
/*    */   public ImageProperty(String image) {
/* 17 */     this.image = image;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object resolve(ResourceProvider rsrcprov) {
/*    */     try {
/* 23 */       return rsrcprov.loadImage(this.image);
/* 24 */     } catch (IOException ioe) {
/* 25 */       System.err.println("Failed to load icon image '" + this.image + "': " + ioe);
/* 26 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getImages() {
/* 32 */     ArrayList<String> list = new ArrayList<String>();
/* 33 */     list.add(this.image);
/* 34 */     return list;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\parser\ImageProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */