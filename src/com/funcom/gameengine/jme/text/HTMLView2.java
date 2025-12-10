/*    */ package com.funcom.gameengine.jme.text;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.jmex.bui.text.HTMLView;
/*    */ 
/*    */ public class HTMLView2
/*    */   extends HTMLView {
/*    */   public HTMLView2(String contents, ResourceManager resourceManager) {
/*  9 */     this._kit = new HTMLEditorKitCustom(resourceManager);
/*    */     
/* 11 */     setContents(contents);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\jme\text\HTMLView2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */