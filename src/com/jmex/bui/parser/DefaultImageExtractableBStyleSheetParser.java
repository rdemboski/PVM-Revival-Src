/*    */ package com.jmex.bui.parser;
/*    */ 
/*    */ import com.jmex.bui.bss.BStyleSheetParsingUtil;
/*    */ import com.jmex.bui.bss.DefaultBStyleSheetParser;
/*    */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*    */ import com.jmex.bui.property.BackgroundProperty;
/*    */ import com.jmex.bui.property.IconProperty;
/*    */ import com.jmex.bui.util.Insets;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultImageExtractableBStyleSheetParser
/*    */   extends DefaultBStyleSheetParser
/*    */ {
/*    */   protected Object parseIcon(ArrayList<String> args) {
/* 18 */     IconProperty iprop = new ImageExtractableIconProperty();
/* 19 */     iprop.type = args.get(0);
/* 20 */     if (iprop.type.equals("image")) {
/* 21 */       iprop.ipath = args.get(1);
/* 22 */     } else if (iprop.type.equals("blank")) {
/* 23 */       iprop.width = BStyleSheetParsingUtil.parseInt(args.get(1));
/* 24 */       iprop.height = BStyleSheetParsingUtil.parseInt(args.get(2));
/*    */     } else {
/* 26 */       throw new IllegalArgumentException("Unknown icon type: '" + iprop.type + "'");
/*    */     } 
/* 28 */     return iprop;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object parseBackground(ArrayList<String> args) {
/* 33 */     BackgroundProperty bprop = new ImageExtractableBackgroundProperty();
/* 34 */     bprop.type = args.get(0);
/* 35 */     if (bprop.type.equals("solid")) {
/* 36 */       bprop.color = BStyleSheetParsingUtil.parseColor(args.get(1));
/* 37 */     } else if (bprop.type.equals("image")) {
/* 38 */       bprop.ipath = args.get(1);
/* 39 */       if (args.size() > 2) {
/* 40 */         String scaleModeStr = args.get(2);
/* 41 */         ImageBackgroundMode scaleMode = ImageBackgroundMode.fromStylesheetAttributeString(scaleModeStr);
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 46 */         bprop.scaleMode = scaleMode;
/* 47 */         if (bprop.scaleMode == ImageBackgroundMode.FRAME_XY && args.size() > 3) {
/* 48 */           bprop.frame = new Insets();
/* 49 */           bprop.frame.top = BStyleSheetParsingUtil.parseInt(args.get(3));
/* 50 */           bprop.frame.right = (args.size() > 4) ? BStyleSheetParsingUtil.parseInt(args.get(4)) : bprop.frame.top;
/*    */           
/* 52 */           bprop.frame.bottom = (args.size() > 5) ? BStyleSheetParsingUtil.parseInt(args.get(5)) : bprop.frame.top;
/*    */           
/* 54 */           bprop.frame.left = (args.size() > 6) ? BStyleSheetParsingUtil.parseInt(args.get(6)) : bprop.frame.right;
/*    */         }
/*    */       
/*    */       } 
/* 58 */     } else if (!bprop.type.equals("blank")) {
/*    */ 
/*    */ 
/*    */       
/* 62 */       throw new IllegalArgumentException("Unknown background type: '" + bprop.type + "'");
/*    */     } 
/*    */     
/* 65 */     return bprop;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\parser\DefaultImageExtractableBStyleSheetParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */