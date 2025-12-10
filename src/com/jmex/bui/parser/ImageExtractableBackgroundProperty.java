/*    */ package com.jmex.bui.parser;
/*    */ 
/*    */ import com.jmex.bui.property.BackgroundProperty;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImageExtractableBackgroundProperty
/*    */   extends BackgroundProperty
/*    */   implements ImageExtractable
/*    */ {
/*    */   public List<String> getImages() {
/* 14 */     ArrayList<String> list = new ArrayList<String>();
/* 15 */     list.add(this.ipath);
/* 16 */     return list;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\parser\ImageExtractableBackgroundProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */