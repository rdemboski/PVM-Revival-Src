/*    */ package com.funcom.gameengine.conanchat.handlers;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.packets2.ChatMessage;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReflectiveCallMessageHandler
/*    */   extends AbstractConanChatMessageHandler
/*    */ {
/*    */   private Object handlerObject;
/*    */   private Method method;
/*    */   
/*    */   public ReflectiveCallMessageHandler(short myType, String methodName, Object handlerObject) {
/* 40 */     super(myType);
/* 41 */     this.handlerObject = handlerObject;
/* 42 */     findMethod(methodName);
/*    */   }
/*    */   
/*    */   public ReflectiveCallMessageHandler(short myType, String methodName, Object handlerObject, ConanChatMessageHandler next) {
/* 46 */     super(myType, next);
/* 47 */     this.handlerObject = handlerObject;
/* 48 */     findMethod(methodName);
/*    */   }
/*    */   
/*    */   private void findMethod(String methodName) {
/*    */     try {
/* 53 */       this.method = this.handlerObject.getClass().getMethod(methodName, new Class[] { ChatMessage.class });
/* 54 */     } catch (NoSuchMethodException e) {
/* 55 */       throw new IllegalStateException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void process(ChatMessage message) {
/*    */     try {
/* 62 */       this.method.invoke(this.handlerObject, new Object[] { message });
/* 63 */     } catch (IllegalAccessException e) {
/* 64 */       throw new IllegalStateException(e);
/* 65 */     } catch (InvocationTargetException e) {
/* 66 */       throw new IllegalStateException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\handlers\ReflectiveCallMessageHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */