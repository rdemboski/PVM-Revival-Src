/*    */ package com.funcom.tcg.client.ui.hud2;
/*    */ 
/*    */ import com.funcom.commons.jme.bui.HighlightedButton;
/*    */ import com.funcom.gameengine.jme.text.HTMLView2;
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.tcg.client.ui.TCGToolTipManager;
/*    */ import com.jmex.bui.BComponent;
/*    */ 
/*    */ public class HudhightlightedButton
/*    */   extends HighlightedButton {
/*    */   private TCGToolTipManager tooltipManager;
/*    */   private ResourceManager resourceManager;
/*    */   private String tooltipText;
/*    */   
/*    */   public HudhightlightedButton(TCGToolTipManager tooltipManager, String tooltipText, ResourceManager resourceManager) {
/* 16 */     this.tooltipManager = tooltipManager;
/* 17 */     this.tooltipText = tooltipText;
/* 18 */     this.resourceManager = resourceManager;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTooltipText() {
/* 23 */     return this.tooltipManager.getHudHtml(this.tooltipText);
/*    */   }
/*    */ 
/*    */   
/*    */   protected BComponent createTooltipComponent(String tiptext) {
/* 28 */     return (BComponent)new HTMLView2(tiptext, this.resourceManager);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\HudhightlightedButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */