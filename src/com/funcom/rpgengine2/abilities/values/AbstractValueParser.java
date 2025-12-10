/*    */ package com.funcom.rpgengine2.abilities.values;
/*    */ 
/*    */ import com.funcom.rpgengine2.loader.RpgLoader;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractValueParser
/*    */   implements ValueParser
/*    */ {
/* 12 */   protected Map<String, ValueParser> groupParsers = new HashMap<String, ValueParser>();
/*    */ 
/*    */   
/*    */   public Value parse(String str, RpgLoader loader) {
/* 16 */     if (str == null || str.length() == 0) {
/* 17 */       return null;
/*    */     }
/*    */     
/* 20 */     Value ret = parseValue(str);
/*    */     
/* 22 */     if (ret != null) {
/* 23 */       return ret;
/*    */     }
/*    */     
/* 26 */     int sepIndex = str.indexOf('.');
/* 27 */     String groupId = str;
/* 28 */     String value = "";
/* 29 */     if (sepIndex != -1) {
/* 30 */       groupId = str.substring(0, sepIndex);
/* 31 */       value = str.substring(sepIndex + 1);
/*    */     } 
/*    */     
/* 34 */     ValueParser groupParser = this.groupParsers.get(groupId);
/* 35 */     if (groupParser == null) {
/* 36 */       throw new RuntimeException("value group not found: groupId=" + groupId + " path=" + str);
/*    */     }
/*    */     
/* 39 */     return groupParser.parse(value, loader);
/*    */   }
/*    */   
/*    */   protected abstract Value parseValue(String paramString);
/*    */   
/*    */   public void addGroup(String groupId, ValueParser parser) {
/* 45 */     this.groupParsers.put(groupId, parser);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\values\AbstractValueParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */