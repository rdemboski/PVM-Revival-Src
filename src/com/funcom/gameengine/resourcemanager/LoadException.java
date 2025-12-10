/*    */ package com.funcom.gameengine.resourcemanager;
/*    */ 
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class LoadException extends Exception {
/*    */   public LoadException(ResourceManager resourceManager, ManagedResource<?> managedResource, Throwable cause) {
/*  7 */     this(resourceManager.getResourceRoots(), managedResource.getName(), cause);
/*    */   }
/*    */ 
/*    */   
/*    */   public LoadException(ResourceManager resourceManager, ManagedResource<?> managedResource, String reason) {
/* 12 */     super("Error in processing resource: " + managedResource.getName() + ", resource paths are: " + resourceManager.getResourceRoots() + " reason=" + reason);
/*    */   }
/*    */   
/*    */   public LoadException(Collection<String> resourceRoots, String resourcePath, Throwable cause) {
/* 16 */     super("Error in processing resource: " + resourcePath + ", resource paths are: " + resourceRoots, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\LoadException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */