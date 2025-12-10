/*     */ package com.funcom.tcg.client.ui.vendor;
/*     */ 
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import com.funcom.gameengine.model.props.UpdateListener;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*     */ import com.funcom.tcg.client.ui.BScrollPaneTcg;
/*     */ import com.funcom.tcg.client.ui.CloseWindowListener;
/*     */ import com.funcom.tcg.client.ui.inventory.DragItemContent;
/*     */ import com.funcom.tcg.client.ui.inventory.Inventory;
/*     */ import com.funcom.tcg.client.ui.tabs.BTabToggleButton;
/*     */ import com.funcom.tcg.client.ui.tabs.BWindowTcgTabbed;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BCustomToggleButton;
/*     */ import com.jmex.bui.GroupListener;
/*     */ import com.jmex.bui.dragndrop.BDropEvent;
/*     */ import com.jmex.bui.dragndrop.BDropListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.layout.BorderLayout;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeprecatedVendorWindow
/*     */   extends BWindowTcgTabbed
/*     */   implements GroupListener
/*     */ {
/*     */   private static final int WINDOW_WIDTH = 500;
/*     */   private static final int WINDOW_HEIGHT = 500;
/*     */   private static final String TAB_BUY = "buy";
/*     */   private static final String TAB_BUYBACK = "buyback";
/*     */   private VendorModel model;
/*     */   private PriceDescContainer playerCurrencyContainer;
/*     */   private VendorItemList activeList;
/*     */   private Map<String, VendorItemList> tabLists;
/*     */   private BScrollPaneTcg itemScroll;
/*     */   private CloseWindowListener closeListener;
/*     */   private VendorChangeListener changeListener;
/*     */   
/*     */   public DeprecatedVendorWindow(VendorModel model, InteractibleProp vendorProp) {
/*  50 */     super(model.getName());
/*  51 */     this.model = model;
/*  52 */     this.changeListener = new VendorChangeListener();
/*  53 */     model.addChangeListener(this.changeListener);
/*  54 */     this.closeListener = new CloseWindowListener(vendorProp, DeprecatedVendorWindow.class);
/*  55 */     vendorProp.addUpdateListener((UpdateListener)this.closeListener);
/*     */     
/*  57 */     setSize(500, 500);
/*     */     
/*  59 */     BContainer itemContainer = createItemContainer();
/*  60 */     BContainer gemContainer = createGemContainer();
/*     */     
/*  62 */     this.itemScroll = new BScrollPaneTcg((BComponent)itemContainer, true);
/*     */     
/*  64 */     getClientArea().setLayoutManager((BLayoutManager)new BorderLayout());
/*  65 */     getClientArea().add((BComponent)this.itemScroll, BorderLayout.CENTER);
/*  66 */     getClientArea().add((BComponent)gemContainer, BorderLayout.SOUTH);
/*     */     
/*  68 */     this.tabLists = new HashMap<String, VendorItemList>();
/*  69 */     this.tabLists.put("buy", new VendorItemListBuy(itemContainer, model));
/*  70 */     this.tabLists.put("buyback", new VendorItemListBuyback(itemContainer, model));
/*     */ 
/*     */     
/*  73 */     addGroupListener(this);
/*     */     
/*  75 */     BTabToggleButton buyButton = addTab("buy", TcgGame.getLocalizedText("vendorwindow.buytab.tooltip", new String[0]));
/*  76 */     BTabToggleButton buyBackButton = addTab("buyback", TcgGame.getLocalizedText("vendorwindow.buybacktab.tooltip", new String[0]));
/*     */     
/*  78 */     buyButton.getLabel().setStyleClass("vendorwindow.tabs.buy");
/*  79 */     buyBackButton.getLabel().setStyleClass("vendorwindow.tabs.buyback");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModel(VendorModel model) {
/*  85 */     InteractibleProp oldProp = (InteractibleProp)TcgGame.getVendorRegister().getPropNode(Integer.valueOf(this.model.getCreatureId())).getProp();
/*  86 */     if (oldProp != null) {
/*  87 */       oldProp.removeUpdateListener((UpdateListener)this.closeListener);
/*  88 */       this.closeListener = null;
/*     */     } 
/*     */     
/*  91 */     if (this.model != null) {
/*  92 */       this.model.disposeModel();
/*     */     }
/*  94 */     this.model = model;
/*  95 */     this.activeList.containerSelected();
/*     */ 
/*     */     
/*  98 */     InteractibleProp newProp = (InteractibleProp)TcgGame.getVendorRegister().getPropNode(Integer.valueOf(model.getCreatureId())).getProp();
/*  99 */     if (newProp != null) {
/* 100 */       this.closeListener = new CloseWindowListener(newProp, DeprecatedVendorWindow.class);
/* 101 */       newProp.addUpdateListener((UpdateListener)this.closeListener);
/*     */     } 
/*     */   }
/*     */   
/*     */   public VendorModel getModel() {
/* 106 */     return this.model;
/*     */   }
/*     */   
/*     */   private BContainer createItemContainer() {
/* 110 */     BContainer itemContainer = new BContainer((BLayoutManager)new TcgGridLayout(2, 4, 4, 4));
/* 111 */     itemContainer.setStyleClass("vendorwindow.itemlist");
/* 112 */     itemContainer.addListener((ComponentListener)new VendorBDropListener());
/* 113 */     return itemContainer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void dismiss() {
/* 118 */     super.dismiss();
/* 119 */     InteractibleProp vendorProp = (InteractibleProp)TcgGame.getVendorRegister().getPropNode(Integer.valueOf(this.model.getCreatureId())).getProp();
/* 120 */     if (vendorProp != null) {
/* 121 */       vendorProp.removeUpdateListener((UpdateListener)this.closeListener);
/*     */     }
/* 123 */     this.model.removeChangeListener(this.changeListener);
/* 124 */     this.model.disposeModel();
/*     */   }
/*     */   
/*     */   private BContainer createGemContainer() {
/* 128 */     BContainer gemContainer = new BContainer((BLayoutManager)new BorderLayout());
/* 129 */     gemContainer.setStyleClass("vendorwindow.gemline");
/*     */     
/* 131 */     BButton gemButton = new BButton("");
/* 132 */     gemButton.setStyleClass("vendorwindow.gemline.gembag");
/*     */     
/* 134 */     this.playerCurrencyContainer = new PriceDescContainer(this.model.getPlayerCurrency());
/*     */     
/* 136 */     gemContainer.add((BComponent)gemButton, BorderLayout.WEST);
/* 137 */     gemContainer.add((BComponent)this.playerCurrencyContainer, BorderLayout.CENTER);
/*     */     
/* 139 */     return gemContainer;
/*     */   }
/*     */   
/*     */   public void groupSelectionChanged(BCustomToggleButton button) {
/* 143 */     this.activeList = this.tabLists.get(button.getAction());
/* 144 */     this.activeList.containerSelected();
/* 145 */     this.itemScroll.invalidate();
/*     */   }
/*     */ 
/*     */   
/*     */   private class VendorChangeListener
/*     */     implements VendorModel.ChangeListener
/*     */   {
/*     */     private VendorChangeListener() {}
/*     */ 
/*     */     
/*     */     public void vendorItemsChanged() {}
/*     */ 
/*     */     
/*     */     public void buyItem(boolean buy, VendorItemButton item) {}
/*     */ 
/*     */     
/*     */     public void buyItemHoverEnter(VendorModelItem item) {}
/*     */     
/*     */     public void playerCurrencyChanged(String classId, int amount) {
/* 164 */       DeprecatedVendorWindow.this.playerCurrencyContainer.setPriceDescSet(DeprecatedVendorWindow.this.model.getPlayerCurrency());
/* 165 */       DeprecatedVendorWindow.this.activeList.currencyChanged(classId, amount);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void itemSold(VendorModelItem vendorModelItem) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void newItemInInventory(String classId, int tier) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void ItemHoverExit() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void sellItemHoverEnter(ClientItem item) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void sellItem(boolean sell, SellInventoryItem item) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void clientItemDropped(ClientItem clientItem, int inventoryId, int slotId) {
/* 195 */     if (clientItem == null || isCurrency(clientItem)) {
/*     */       return;
/*     */     }
/*     */     
/* 199 */     this.model.sellItem(inventoryId, Inventory.TYPE_INVENTORY, slotId);
/*     */   }
/*     */   
/*     */   private boolean isCurrency(ClientItem clientItem) {
/* 203 */     return (clientItem.getValueClassId() == null || clientItem.getValueClassId().isEmpty());
/*     */   }
/*     */   
/*     */   private class VendorBDropListener
/*     */     extends BDropListener
/*     */   {
/*     */     private VendorBDropListener() {}
/*     */     
/*     */     protected void drop(BDropEvent bDropEvent) {
/* 212 */       if (bDropEvent.getDragEvent().getDraggedObject() instanceof DragItemContent) {
/* 213 */         DragItemContent dragItemContent = (DragItemContent)bDropEvent.getDragEvent().getDraggedObject();
/* 214 */         DeprecatedVendorWindow.this.clientItemDropped((ClientItem)dragItemContent.getItem(), dragItemContent.getInventoryId(), dragItemContent.getSlotId());
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\vendor\DeprecatedVendorWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */