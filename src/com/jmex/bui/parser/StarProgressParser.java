/*    */ package com.jmex.bui.parser;
/*    */ 
/*    */ import com.jmex.bui.BStyleSheet;
/*    */ import com.jmex.bui.bss.BStyleSheetParser;
/*    */ import com.jmex.bui.bss.BStyleSheetParsingUtil;
/*    */ import com.jmex.bui.property.IconProperty;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StarProgressParser
/*    */   implements BStyleSheetParser
/*    */ {
/*    */   public Object parse(String name, ArrayList args, BStyleSheet styleSheet) {
/* 16 */     if (name.equals("full_icon") || name.equals("empty_icon")) {
/* 17 */       return handleIcon(args);
/*    */     }
/* 19 */     throw new IllegalArgumentException("Unknown property '" + name + "'");
/*    */   }
/*    */   
/*    */   private Object handleIcon(ArrayList<String> args) {
/* 23 */     IconProperty iprop = new ImageExtractableIconProperty();
/* 24 */     iprop.type = args.get(0);
/* 25 */     if (iprop.type.equals("image")) {
/* 26 */       iprop.ipath = args.get(1);
/* 27 */     } else if (iprop.type.equals("blank")) {
/* 28 */       iprop.width = BStyleSheetParsingUtil.parseInt(args.get(1));
/* 29 */       iprop.height = BStyleSheetParsingUtil.parseInt(args.get(2));
/*    */     } else {
/* 31 */       throw new IllegalArgumentException("Unknown icon type: '" + iprop.type + "'");
/*    */     } 
/* 33 */     return iprop;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canParse(String name) {
/* 38 */     return (name.equals("full_icon") || name.equals("empty_icon"));
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\parser\StarProgressParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */