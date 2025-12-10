/*    */ package com.funcom.gameengine.resourcemanager;
/*    */ 
/*    */ import java.util.concurrent.Callable;
/*    */ 
/*    */ public class LoadTask<T>
/*    */   implements Callable<ManagedResource<T>> {
/*    */   private ManagedResource<T> resource;
/*    */   private ResourceListener<T> listener;
/*    */   
/*    */   public LoadTask(ManagedResource<T> resource) {
/* 11 */     this(resource, null);
/*    */   }
/*    */   
/*    */   public LoadTask(ManagedResource<T> resource, ResourceListener<T> listener) {
/* 15 */     this.resource = resource;
/* 16 */     this.listener = listener;
/*    */   }
/*    */ 
/*    */   
/*    */   public ManagedResource<T> call() throws LoadException {
/* 21 */     if (!this.resource.isLoaded()) {
/* 22 */       this.resource.load();
/*    */     }
/* 24 */     if (this.listener != null) {
/* 25 */       this.listener.resourceLoaded(this.resource);
/*    */     }
/* 27 */     this.resource.setLoading(false);
/* 28 */     return this.resource;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\LoadTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */