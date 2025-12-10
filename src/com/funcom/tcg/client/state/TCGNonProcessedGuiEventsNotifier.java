/*    */ package com.funcom.tcg.client.state;
/*    */ 
/*    */ import com.funcom.commons.jme.bui.IrregularWindow;
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BWindow;
/*    */ import com.jmex.bui.BuiSystem;
/*    */ import com.jmex.bui.event.BEvent;
/*    */ import com.jmex.bui.event.MouseEvent;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TCGNonProcessedGuiEventsNotifier
/*    */   implements IrregularWindow.NonProcessedEventsNotifier
/*    */ {
/*    */   private TCGGameControlsController gameControlsController;
/*    */   private BComponent lastHoverComponent;
/*    */   
/*    */   public TCGNonProcessedGuiEventsNotifier(TCGGameControlsController gameControlsController) {
/* 20 */     if (gameControlsController == null)
/* 21 */       throw new IllegalArgumentException("gameControlsController = null"); 
/* 22 */     this.gameControlsController = gameControlsController;
/*    */   }
/*    */   
/*    */   public void didNotProcessTheEvent(IrregularWindow irregularWindow, MouseEvent mouseEvent) {
/* 26 */     List<BWindow> windows = BuiSystem.getRootNode().getAllWindows();
/* 27 */     boolean dispatchedToGui = false;
/*    */     
/* 29 */     for (BWindow window : windows) {
/*    */       
/* 31 */       if (window.equals(irregularWindow)) {
/*    */         continue;
/*    */       }
/* 34 */       BComponent hitComponent = componentHit(window, mouseEvent);
/* 35 */       updateHoverOnLastComponent(hitComponent, mouseEvent);
/*    */       
/* 37 */       if (hitComponent != null && !hitComponent.equals(this.lastHoverComponent)) {
/* 38 */         dispatchEnterEvent(hitComponent, mouseEvent);
/*    */       }
/* 40 */       this.lastHoverComponent = hitComponent;
/* 41 */       if (hitComponent == null) {
/*    */         continue;
/*    */       }
/* 44 */       dispatchedToGui = hitComponent.dispatchEvent((BEvent)mouseEvent);
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 49 */     if (!dispatchedToGui)
/* 50 */       this.gameControlsController.didNotProcessTheEvent(irregularWindow, mouseEvent); 
/*    */   }
/*    */   
/*    */   private void updateHoverOnLastComponent(BComponent hitComponent, MouseEvent mouseEvent) {
/* 54 */     if (this.lastHoverComponent != null && !this.lastHoverComponent.equals(hitComponent))
/* 55 */       dispatchExitEvent(this.lastHoverComponent, mouseEvent); 
/*    */   }
/*    */   
/*    */   private void dispatchEnterEvent(BComponent hitComponent, MouseEvent mouseEvent) {
/* 59 */     if (hitComponent.isHoverEnabled()) {
/* 60 */       MouseEvent enteredEvent = new MouseEvent(this, mouseEvent.getWhen(), mouseEvent.getModifiers(), 2, mouseEvent.getButton(), mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getDelta());
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
/* 71 */       hitComponent.dispatchEvent((BEvent)enteredEvent);
/* 72 */       System.out.println("ENTER on: " + hitComponent.getName());
/*    */     } 
/*    */   }
/*    */   
/*    */   private void dispatchExitEvent(BComponent hitComponent, MouseEvent mouseEvent) {
/* 77 */     if (hitComponent.isHoverEnabled()) {
/* 78 */       MouseEvent exitEvent = new MouseEvent(this, mouseEvent.getWhen(), mouseEvent.getModifiers(), 3, mouseEvent.getButton(), mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getDelta());
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
/* 89 */       hitComponent.dispatchEvent((BEvent)exitEvent);
/* 90 */       System.out.println("EXIT on: " + hitComponent.getName());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private BComponent componentHit(BWindow window, MouseEvent mouseEvent) {
/* 96 */     return window.getHitComponent(mouseEvent.getX(), mouseEvent.getY());
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\state\TCGNonProcessedGuiEventsNotifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */