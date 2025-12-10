/*    */ package com.funcom.tcg.client.ui.errorreporting;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.peeler.BananaPeel;
/*    */ import com.funcom.tcg.client.ui.BPeelWindow;
/*    */ import com.funcom.tcg.client.ui.PeelWindowEvent;
/*    */ import com.funcom.tcg.client.ui.TcgUI;
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BContainer;
/*    */ import com.jmex.bui.BLabel;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ErrorWindow
/*    */   extends BPeelWindow
/*    */ {
/*    */   public ErrorWindow(String title, String errorMessage, BananaPeel bananaPeel, ResourceManager resourceManager) {
/* 20 */     super(title, bananaPeel);
/*    */     
/* 22 */     createComponents(errorMessage);
/*    */   }
/*    */   
/*    */   private void createComponents(String errorMessage) {
/* 26 */     BLabel windowText = (BLabel)findComponent((BContainer)this, "text_description");
/* 27 */     windowText.setText(errorMessage);
/*    */     
/* 29 */     BLabel windowHeader = (BLabel)findComponent((BContainer)this, "text_header");
/* 30 */     windowHeader.setText(getName());
/*    */     
/* 32 */     Map<String, PeelWindowEvent[]> eventsWireMap = (Map)new HashMap<String, PeelWindowEvent>();
/* 33 */     eventsWireMap.put("button_accept", new PeelWindowEvent[] { new PeelWindowEvent("buttonOkPressed", PeelWindowEvent.PeelEventType.ACTION) });
/* 34 */     wireEvents(eventsWireMap, this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void buttonOkPressed(BPeelWindow window, BComponent component) {
/* 39 */     TcgUI.getUISoundPlayer().play("ClickForward");
/* 40 */     dismiss();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\errorreporting\ErrorWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */