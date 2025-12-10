/*    */ package com.funcom.tcg.client.ui.character;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.peeler.BananaPeel;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.ui.BPeelWindow;
/*    */ import com.funcom.tcg.client.ui.PeelWindowEvent;
/*    */ import com.funcom.tcg.client.ui.TcgUI;
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BContainer;
/*    */ import com.jmex.bui.BWindow;
/*    */ import com.jmex.bui.Log;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class CharacterInfoWindow
/*    */   extends BPeelWindow
/*    */ {
/*    */   public CharacterInfoWindow(String windowName, BananaPeel bananaPeel, ResourceManager resourceManager) {
/* 20 */     super(windowName, bananaPeel);
/*    */     
/* 22 */     Map<String, PeelWindowEvent[]> eventsWireMap = (Map)new HashMap<String, PeelWindowEvent>();
/* 23 */     eventsWireMap.put("button_accept", new PeelWindowEvent[] { new PeelWindowEvent("buttonOkPressed", PeelWindowEvent.PeelEventType.ACTION) });
/* 24 */     wireEvents(eventsWireMap, this);
/* 25 */     findComponent((BContainer)this, "button_accept").setTooltipText(TcgGame.getLocalizedText("tooltips.info.ok", new String[0]));
/*    */   }
/*    */ 
/*    */   
/*    */   public void buttonOkPressed(BPeelWindow window, BComponent component) {
/* 30 */     if (this._root != null) {
/* 31 */       TcgUI.getUISoundPlayer().play("ClickForward");
/* 32 */       this._root.removeWindow((BWindow)this);
/*    */     } else {
/* 34 */       Log.log.warning("Unmanaged window dismissed: " + this + ".");
/* 35 */       Thread.dumpStack();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\character\CharacterInfoWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */