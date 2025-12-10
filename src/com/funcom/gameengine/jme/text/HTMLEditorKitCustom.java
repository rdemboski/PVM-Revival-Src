/*    */ package com.funcom.gameengine.jme.text;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import javax.swing.text.Element;
/*    */ import javax.swing.text.StyleConstants;
/*    */ import javax.swing.text.View;
/*    */ import javax.swing.text.ViewFactory;
/*    */ import javax.swing.text.html.HTML;
/*    */ import javax.swing.text.html.HTMLEditorKit;
/*    */ 
/*    */ public class HTMLEditorKitCustom
/*    */   extends HTMLEditorKit {
/*    */   private ResourceManager resourceManager;
/*    */   
/*    */   public HTMLEditorKitCustom(ResourceManager resourceManager) {
/* 16 */     this.resourceManager = resourceManager;
/*    */   }
/*    */   
/*    */   public ViewFactory getViewFactory() {
/* 20 */     return new HTMLFactoryX(this.resourceManager);
/*    */   }
/*    */   
/*    */   public static class HTMLFactoryX extends HTMLEditorKit.HTMLFactory implements ViewFactory {
/*    */     private ResourceManager resourceManager;
/*    */     
/*    */     public HTMLFactoryX(ResourceManager resourceManager) {
/* 27 */       this.resourceManager = resourceManager;
/*    */     }
/*    */     
/*    */     public View create(Element elem) {
/* 31 */       Object o = elem.getAttributes().getAttribute(StyleConstants.NameAttribute);
/* 32 */       if (o instanceof HTML.Tag) {
/* 33 */         HTML.Tag kind = (HTML.Tag)o;
/* 34 */         if (kind == HTML.Tag.IMG)
/* 35 */           return new ImageViewCustom(elem, this.resourceManager); 
/*    */       } 
/* 37 */       return super.create(elem);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\jme\text\HTMLEditorKitCustom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */