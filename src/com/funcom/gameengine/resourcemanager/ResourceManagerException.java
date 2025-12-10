/*    */ package com.funcom.gameengine.resourcemanager;
/*    */ 
/*    */ public class ResourceManagerException
/*    */   extends RuntimeException {
/*    */   public ResourceManagerException(String message) {
/*  6 */     super(message);
/*    */   }
/*    */   
/*    */   public ResourceManagerException(Throwable cause) {
/* 10 */     super(cause);
/*    */   }
/*    */   
/*    */   public ResourceManagerException(String message, Throwable cause) {
/* 14 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public ResourceManagerException(ManagedResource<?> resource) {
/* 18 */     super("Couldn't find loader for class: " + resource.getResourceType() + " while trying to load: " + resource.getName());
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\ResourceManagerException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */