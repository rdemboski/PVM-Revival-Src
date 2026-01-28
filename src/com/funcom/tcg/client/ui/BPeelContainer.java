/*    */ package com.funcom.tcg.client.ui;
/*    */ 
/*    */ import com.funcom.peeler.BananaPeel;
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BContainer;
/*    */ import com.jmex.bui.util.Rectangle;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BPeelContainer
/*    */   extends BContainer
/*    */ {
/* 17 */   private static final Logger LOGGER = Logger.getLogger(BPeelWindow.class);
/*    */   private BContainer mainContainer;
/*    */   
/*    */   public BPeelContainer(String windowName, BananaPeel bananaPeel) {
/* 21 */     super(windowName);
/*    */     
/* 23 */     this.mainContainer = (BContainer) bananaPeel.getTopComponents().iterator().next();
/* 24 */     setSize(this.mainContainer.getWidth(), this.mainContainer.getHeight());
/*    */   }
/*    */   
/*    */   public BComponent findComponent(BContainer rootComponent, String componentName) {
/* 28 */     for (int i = 0; i < rootComponent.getComponentCount(); i++) {
/* 29 */       BComponent component = rootComponent.getComponent(i);
/* 30 */       if (componentName.equals(component.getName())) {
/* 31 */         return component;
/*    */       }
/* 33 */       if (component instanceof BContainer) {
/* 34 */         BComponent foundComponent = findComponent((BContainer)component, componentName);
/* 35 */         if (foundComponent != null)
/* 36 */           return foundComponent; 
/*    */       } 
/*    */     } 
/* 39 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void overridePeelerComponent(BComponent newComponent, BComponent placeholder) {
/* 50 */     String style = placeholder.getStyleClass();
/* 51 */     newComponent.setStyleClass(style);
/* 52 */     newComponent.setSize(placeholder.getWidth(), placeholder.getHeight());
/* 53 */     newComponent.setLocation(placeholder.getX(), placeholder.getY());
/* 54 */     placeholder.getParent().add(placeholder.getParent().getComponentIndex(placeholder), newComponent, new Rectangle(placeholder.getX(), placeholder.getY(), placeholder.getWidth(), placeholder.getHeight()));
/*    */     
/* 56 */     placeholder.getParent().remove(placeholder);
/*    */   }
/*    */   
/*    */   public BContainer getMainContainer() {
/* 60 */     return this.mainContainer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\BPeelContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */