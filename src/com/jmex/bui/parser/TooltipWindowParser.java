/*    */ package com.jmex.bui.parser;
/*    */ 
/*    */ import com.jmex.bui.BStyleSheet;
/*    */ import com.jmex.bui.bss.BStyleSheetParser;
/*    */ import com.jmex.bui.bss.BStyleSheetParsingUtil;
/*    */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*    */ import com.jmex.bui.property.BackgroundProperty;
/*    */ import com.jmex.bui.util.Insets;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TooltipWindowParser
/*    */   implements BStyleSheetParser
/*    */ {
/*    */   public Object parse(String name, ArrayList<String> args, BStyleSheet styleSheet) {
/* 18 */     if (name.equals("health-progress-player") || name.equals("health-progress-monster"))
/* 19 */       return new ImageProperty(args.get(0)); 
/* 20 */     if (name.equals("boss-lvl-below") || name.equals("boss-lvl-above") || name.equals("boss-lvl-same") || name.equals("miniboss-lvl-below") || name.equals("miniboss-lvl-above") || name.equals("miniboss-lvl-same") || name.equals("mob-lvl-below") || name.equals("mob-lvl-above") || name.equals("mob-lvl-same") || name.equals("player-image"))
/*    */     {
/*    */ 
/*    */       
/* 24 */       return parseBackground(args);
/*    */     }
/* 26 */     throw new IllegalArgumentException("Unknown property '" + name + "'");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canParse(String name) {
/* 31 */     return (name.equals("health-progress-player") || name.equals("health-progress-monster") || name.equals("boss-lvl-below") || name.equals("boss-lvl-above") || name.equals("boss-lvl-same") || name.equals("miniboss-lvl-below") || name.equals("miniboss-lvl-above") || name.equals("miniboss-lvl-same") || name.equals("mob-lvl-below") || name.equals("mob-lvl-above") || name.equals("mob-lvl-same") || name.equals("player-image"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Object parseBackground(ArrayList<String> args) {
/* 39 */     BackgroundProperty bprop = new ImageExtractableBackgroundProperty();
/* 40 */     bprop.type = args.get(0);
/* 41 */     if (bprop.type.equals("solid")) {
/* 42 */       bprop.color = BStyleSheetParsingUtil.parseColor(args.get(1));
/* 43 */     } else if (bprop.type.equals("image")) {
/* 44 */       bprop.ipath = args.get(1);
/* 45 */       if (args.size() > 2) {
/* 46 */         String scaleModeStr = args.get(2);
/* 47 */         ImageBackgroundMode scaleMode = ImageBackgroundMode.fromStylesheetAttributeString(scaleModeStr);
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 52 */         bprop.scaleMode = scaleMode;
/* 53 */         if (bprop.scaleMode == ImageBackgroundMode.FRAME_XY && args.size() > 3) {
/* 54 */           bprop.frame = new Insets();
/* 55 */           bprop.frame.top = BStyleSheetParsingUtil.parseInt(args.get(3));
/* 56 */           bprop.frame.right = (args.size() > 4) ? BStyleSheetParsingUtil.parseInt(args.get(4)) : bprop.frame.top;
/*    */           
/* 58 */           bprop.frame.bottom = (args.size() > 5) ? BStyleSheetParsingUtil.parseInt(args.get(5)) : bprop.frame.top;
/*    */           
/* 60 */           bprop.frame.left = (args.size() > 6) ? BStyleSheetParsingUtil.parseInt(args.get(6)) : bprop.frame.right;
/*    */         }
/*    */       
/*    */       } 
/* 64 */     } else if (!bprop.type.equals("blank")) {
/*    */ 
/*    */ 
/*    */       
/* 68 */       throw new IllegalArgumentException("Unknown background type: '" + bprop.type + "'");
/*    */     } 
/*    */     
/* 71 */     return bprop;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\parser\TooltipWindowParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */