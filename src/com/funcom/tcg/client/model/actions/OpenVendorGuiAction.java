/*    */ package com.funcom.tcg.client.model.actions;
/*    */ 
/*    */ import com.funcom.gameengine.model.action.AbstractAction;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ import com.funcom.gameengine.resourcemanager.CacheType;
/*    */ import com.funcom.peeler.BananaPeel;
/*    */ import com.funcom.rpgengine2.vendor.VendorItem;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.TcgUI;
/*    */ import com.funcom.tcg.client.ui.hud.GameWindows;
/*    */ import com.funcom.tcg.client.ui.vendor.VendorFullWindow;
/*    */ import com.funcom.tcg.client.ui.vendor.VendorModel;
/*    */ import com.funcom.tcg.client.ui.vendor.VendorModelImpl;
/*    */ import com.funcom.tcg.client.ui.vendor.VendorModelItem;
/*    */ import com.funcom.tcg.client.ui.vendor.VendorModelItemVendorAdapter;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class OpenVendorGuiAction
/*    */   extends AbstractAction
/*    */ {
/*    */   public static final String ACTION_NAME = "open-vendor-gui";
/*    */   private static final String VENDOR_PATH = "gui/peeler/vendor_window.xml";
/*    */   private String vendorName;
/*    */   private List<VendorItem> vendorItems;
/*    */   private List<VendorItem> buybackItems;
/*    */   
/*    */   public OpenVendorGuiAction(String vendorName, List<VendorItem> vendorItems, List<VendorItem> buybackItems) {
/* 30 */     this.vendorName = vendorName;
/* 31 */     this.vendorItems = vendorItems;
/* 32 */     this.buybackItems = buybackItems;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 37 */     return "open-vendor-gui";
/*    */   }
/*    */   
/*    */   public void perform(InteractibleProp invoker) {
/* 41 */     if (!TcgGame.isDueling()) {
/* 42 */       openVendorWindow();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void openVendorWindow() {
/* 49 */     List<VendorModelItem> vendorItems = new LinkedList<VendorModelItem>();
/* 50 */     for (VendorItem vendorItem : this.vendorItems) {
/* 51 */       vendorItems.add(new VendorModelItemVendorAdapter(vendorItem));
/*    */     }
/*    */     
/* 54 */     List<VendorModelItem> buybackItems = new LinkedList<VendorModelItem>();
/* 55 */     for (VendorItem buybackItem : this.buybackItems) {
/* 56 */       buybackItems.add(new VendorModelItemVendorAdapter(buybackItem));
/*    */     }
/* 58 */     VendorModelImpl vendorModelImpl = new VendorModelImpl(vendorItems, buybackItems, this.vendorName, getParent().getId());
/*    */     
/* 60 */     if (TcgUI.isWindowOpen(VendorFullWindow.class)) {
/* 61 */       VendorFullWindow vendorWindow = (VendorFullWindow)TcgUI.getWindowFromClass(VendorFullWindow.class);
/* 62 */       vendorWindow.setModel((VendorModel)vendorModelImpl);
/*    */     } else {
/* 64 */       InteractibleProp vendorProp = (InteractibleProp)TcgGame.getVendorRegister().getPropNode(Integer.valueOf(vendorModelImpl.getCreatureId())).getProp();
/*    */ 
/*    */       
/* 67 */       if (vendorProp != null) {
/* 68 */         VendorFullWindow vendorWindow = MainGameState.getVendorWindow();
/* 69 */         if (vendorWindow == null) {
/* 70 */           BananaPeel bananaPeel = (BananaPeel)TcgGame.getResourceManager().getResource(BananaPeel.class, "gui/peeler/vendor_window.xml", CacheType.NOT_CACHED);
/*    */           
/* 72 */           vendorWindow = new VendorFullWindow("gui/peeler/vendor_window.xml", bananaPeel, TcgGame.getResourceManager(), (VendorModel)vendorModelImpl);
/*    */ 
/*    */           
/* 75 */           MainGameState.setVendorWindow(vendorWindow);
/*    */         } else {
/* 77 */           MainGameState.getVendorWindow().setModel((VendorModel)vendorModelImpl);
/*    */         } 
/* 79 */         MainGameState.getGuiWindowsController().toggleWindow(GameWindows.VENDOR);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\actions\OpenVendorGuiAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */