/*    */ package com.jmex.bui.dragndrop;
/*    */ 
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.event.BEvent;
/*    */ import com.jmex.bui.event.EventListener;
/*    */ import com.jmex.bui.event.MouseEvent;
/*    */ import com.jmex.bui.icon.BIcon;
/*    */ 
/*    */ public class BDragListener
/*    */   implements EventListener {
/*    */   private Object dragObject;
/*    */   private IconRequest iconRequest;
/*    */   private BComponent source;
/*    */   
/*    */   public BDragListener(BComponent source, Object dragObject) {
/* 16 */     this(source, dragObject, (BIcon)null);
/*    */   }
/*    */   
/*    */   public BDragListener(BComponent source, Object dragObject, BIcon iconRequest) {
/* 20 */     this(source, dragObject, new SimpleIconRequest(iconRequest, null));
/*    */   }
/*    */   
/*    */   public BDragListener(BComponent source, Object dragObject, IconRequest iconRequest) {
/* 24 */     this.source = source;
/* 25 */     this.dragObject = dragObject;
/* 26 */     this.iconRequest = iconRequest;
/*    */   }
/*    */   
/*    */   public void eventDispatched(BEvent event) {
/* 30 */     if (event instanceof MouseEvent && this.source.isEnabled()) {
/* 31 */       MouseEvent e = (MouseEvent)event;
/* 32 */       if (e.getType() == 2) {
/* 33 */         BDragNDrop dnd = BDragNDrop.instance();
/* 34 */         if (this.iconRequest.getIcon() != null) {
/* 35 */           dnd.setDragIconDisplacement(-this.iconRequest.getIcon().getWidth() / 2, -this.iconRequest.getIcon().getHeight() / 2);
/* 36 */           dnd.setPotentialDrag(this.source, this.dragObject, this.iconRequest.getIcon());
/*    */         } 
/* 38 */       } else if (e.getType() == 4) {
/* 39 */         BDragNDrop dnd = BDragNDrop.instance();
/* 40 */         if (this.iconRequest.getIcon() != null) {
/* 41 */           dnd.setDragIconDisplacement(-this.iconRequest.getIcon().getWidth() / 2, -this.iconRequest.getIcon().getHeight() / 2);
/* 42 */           dnd.setPotentialDrag(this.source, this.dragObject, this.iconRequest.getIcon());
/*    */         } 
/* 44 */       } else if (e.getType() == 3) {
/* 45 */         BDragNDrop.instance().removePotentialDrag(this.source);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private static class SimpleIconRequest
/*    */     implements IconRequest
/*    */   {
/*    */     private BIcon icon;
/*    */ 
/*    */     
/*    */     private SimpleIconRequest(BIcon icon) {
/* 58 */       this.icon = icon;
/*    */     }
/*    */     
/*    */     public BIcon getIcon() {
/* 62 */       return this.icon;
/*    */     }
/*    */   }
/*    */   
/*    */   public static interface IconRequest {
/*    */     BIcon getIcon();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\dragndrop\BDragListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */