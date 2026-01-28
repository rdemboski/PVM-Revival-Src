/*    */ package com.jmex.bui.parser;
/*    */ 
/*    */ import com.jmex.bui.BStyleSheet;
/*    */ import com.jmex.bui.bss.BStyleSheetParser;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImageParser
/*    */   implements BStyleSheetParser
/*    */ {
@Override
  public Object parse(String name, ArrayList args, BStyleSheet styleSheet) {
    if (name.equals("image")) {
      return new ImageProperty((String) args.get(0));
    }
    throw new IllegalArgumentException("Unknown property '" + name + "'");
  }

  @Override
  public boolean canParse(String name) {
    return name.equals("image");
  }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\parser\ImageParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */