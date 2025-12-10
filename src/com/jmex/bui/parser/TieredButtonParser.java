/*    */ package com.jmex.bui.parser;
/*    */ 
/*    */ import com.jmex.bui.BStyleSheet;
/*    */ import com.jmex.bui.bss.BStyleSheetParser;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TieredButtonParser
/*    */   implements BStyleSheetParser
/*    */ {
/*    */   public Object parse(String name, ArrayList<String> args, BStyleSheet styleSheet) {
/* 14 */     if (name.equals("empty_star") || name.equals("filled_star")) {
/* 15 */       return new ImageProperty(args.get(0));
/*    */     }
/* 17 */     throw new IllegalArgumentException("Unknown property '" + name + "'");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canParse(String name) {
/* 22 */     return (name.equals("empty_star") || name.equals("filled_star"));
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\parser\TieredButtonParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */