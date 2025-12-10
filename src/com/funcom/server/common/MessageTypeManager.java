/*    */ package com.funcom.server.common;
/*    */ 
/*    */ import com.funcom.commons.IdManager;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MessageTypeManager
/*    */ {
/* 11 */   private static final IdManager ID_MANAGER = new IdManager();
/*    */   
/*    */   static {
/* 14 */     registerIds(BaseMessageType.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void registerIds(Class<? extends BaseMessageType> idClass) {
/*    */     try {
/* 23 */       ID_MANAGER.register(idClass);
/* 24 */     } catch (IllegalAccessException e) {
/* 25 */       throw new RuntimeException("problem registering constants", e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static Short getType(Object nameRepresentation) {
/* 30 */     return ID_MANAGER.getType(nameRepresentation);
/*    */   }
/*    */   
/*    */   public static Object getName(Short id) {
/* 34 */     return ID_MANAGER.getName(id);
/*    */   }
/*    */   
/*    */   public static List<Short> values() {
/* 38 */     return ID_MANAGER.values();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\common\MessageTypeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */