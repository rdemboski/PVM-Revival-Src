/*     */ package com.funcom.tcg.client.ui.hud2;
import com.funcom.commons.localization.JavaLocalization;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import com.funcom.gameengine.model.props.UpdateListener;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.rpgengine2.speach.SpeachContext;
/*     */ import com.funcom.rpgengine2.speach.SpeachMapping;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.AbstractTcgWindow;
/*     */ import com.funcom.tcg.client.ui.BScrollPaneTcg;
import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.funcom.tcg.client.ui.CloseWindowListener;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.inventory.DragItemContent;
import com.funcom.tcg.client.ui.inventory.Inventory;
/*     */ import com.funcom.tcg.client.ui.vendor.PriceDescContainer;
import com.funcom.tcg.client.ui.vendor.SellInventoryItem;
import com.funcom.tcg.client.ui.vendor.TcgGridLayout;
import com.funcom.tcg.client.ui.vendor.VendorItemButton;
/*     */ import com.funcom.tcg.client.ui.vendor.VendorItemList;
/*     */ import com.funcom.tcg.client.ui.vendor.VendorItemListBuy;
/*     */ import com.funcom.tcg.client.ui.vendor.VendorModel;
/*     */ import com.funcom.tcg.client.ui.vendor.VendorModelItem;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.dragndrop.BDropEvent;
/*     */ import com.jmex.bui.dragndrop.BDropListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.layout.BorderLayout;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ 
/*     */ public class VendorWindow extends AbstractTcgWindow {
/*     */   private static final int WINDOW_WIDTH = 400;
/*     */   private static final int WINDOW_HEIGHT = 600;
/*     */   private VendorModel model;
/*     */   private PriceDescContainer playerCurrencyContainer;
/*     */   private VendorItemList activeList;
/*     */   
/*     */   public VendorWindow(VendorModel model, InteractibleProp vendorProp, ResourceManager resourceManager) {
/*  39 */     super(resourceManager);
/*  40 */     this.model = model;
/*  41 */     this._style = BuiUtils.createMergedClassStyleSheets(VendorWindow.class, new BananaResourceProvider(resourceManager));
/*     */ 
/*     */     
/*  44 */     setBounds(0, 110, 400, 600);
/*  45 */     setTitle(model.getName());
/*     */     
/*  47 */     this.changeListener = new VendorChangeListener();
/*  48 */     model.addChangeListener(this.changeListener);
/*     */     
/*  50 */     this.closeListener = new CloseWindowListener(vendorProp, VendorWindow.class);
/*  51 */     vendorProp.addUpdateListener((UpdateListener)this.closeListener);
/*     */     
/*  53 */     this.itemContainer = createItemContainer();
/*  54 */     BContainer priceCon = createPriceDescContainer();
/*     */     
/*  56 */     this.activeList = (VendorItemList)new VendorItemListBuy(this.itemContainer, model);
/*     */     
/*  58 */     this.itemScroll = new BScrollPaneTcg((BComponent)this.itemContainer, true, 92);
/*     */     
/*  60 */     getClientArea().add((BComponent)this.itemScroll, new Rectangle(0, 60, 370, 460));
/*  61 */     getClientArea().add((BComponent)priceCon, new Rectangle(0, 10, 360, 50));
/*     */     
/*  63 */     this.listener = new AbstractHudModel.ChangeListenerAdapter()
/*     */       {
/*     */         public void petPickedUp()
/*     */         {
/*  67 */           VendorWindow.this.updateActiveList();
/*     */         }
/*     */       };
/*  70 */     MainGameState.getHudModel().addChangeListener(this.listener);
/*     */     
/*  72 */     this.activeList.containerSelected();
/*     */     
/*  74 */     addDefaultCloseButton(AbstractTcgWindow.CloseButtonPosition.TOP_RIGHT);
/*     */   }
/*     */   private BScrollPaneTcg itemScroll; private CloseWindowListener closeListener; private VendorChangeListener changeListener; public static final String STYLE_CLASS = "vendorwindow"; private BContainer itemContainer; private AbstractHudModel.ChangeListenerAdapter listener;
/*     */   
/*     */   public void dismiss() {
/*  79 */     MainGameState.getHudModel().removeChangeListener(this.listener);
/*  80 */     super.dismiss();
/*     */   }
/*     */   
/*     */   private void updateActiveList() {
/*  84 */     this.activeList.updateAvailableItems();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setModel(VendorModel model) {
/*  89 */     PropNode node = TcgGame.getVendorRegister().getPropNode(Integer.valueOf(this.model.getCreatureId()));
/*  90 */     if (node != null) {
/*  91 */       InteractibleProp oldProp = (InteractibleProp)node.getProp();
/*  92 */       if (oldProp != null) {
/*  93 */         oldProp.removeUpdateListener((UpdateListener)this.closeListener);
/*  94 */         this.closeListener = null;
/*     */       } 
/*     */     } 
/*     */     
/*  98 */     if (this.model != null) {
/*  99 */       this.model.disposeModel();
/*     */     }
/* 101 */     this.model = model;
/* 102 */     setTitle(model.getName());
/* 103 */     model.addChangeListener(this.changeListener);
/* 104 */     this.activeList = (VendorItemList)new VendorItemListBuy(this.itemContainer, model);
/* 105 */     this.activeList.containerSelected();
/*     */ 
/*     */     
/* 108 */     InteractibleProp newProp = (InteractibleProp)TcgGame.getVendorRegister().getPropNode(Integer.valueOf(model.getCreatureId())).getProp();
/*     */     
/* 110 */     if (newProp != null) {
/* 111 */       this.closeListener = new CloseWindowListener(newProp, VendorWindow.class);
/* 112 */       newProp.addUpdateListener((UpdateListener)this.closeListener);
/*     */     } 
/*     */     
/* 115 */     if (this.playerCurrencyContainer != null) {
/* 116 */       this.playerCurrencyContainer.setPriceDescSet(model.getPlayerCurrency());
/*     */     }
/*     */   }
/*     */   
/*     */   public VendorModel getModel() {
/* 121 */     return this.model;
/*     */   }
/*     */   
/*     */   private BContainer createItemContainer() {
/* 125 */     BContainer itemContainer = new BContainer((BLayoutManager)new TcgGridLayout(1, 5, 6, 0));
/* 126 */     itemContainer.setStyleClass("vendorwindow.itemlist");
/* 127 */     itemContainer.addListener((ComponentListener)new VendorBDropListener());
/* 128 */     return itemContainer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 133 */     super.setVisible(visible);
/* 134 */     if (!visible) {
/* 135 */       this.itemScroll.getVerticalScrollBar().getModel().setValue(0);
/*     */       
/* 137 */       PropNode node = TcgGame.getVendorRegister().getPropNode(Integer.valueOf(this.model.getCreatureId()));
/* 138 */       if (node != null) {
/* 139 */         Creature vendorProp = (Creature)node.getProp();
/* 140 */         if (vendorProp != null) {
/* 141 */           vendorProp.removeUpdateListener((UpdateListener)this.closeListener);
/* 142 */           SpeachMapping speachMapping = TcgGame.getRpgLoader().getSpeachManager().getSpeachDescritpionForNpc(vendorProp.getMonsterId());
/*     */           
/* 144 */           if (speachMapping != null) {
/* 145 */             String key = speachMapping.getRandomSpeachForContext(SpeachContext.VENDOR_CLOSE);
/* 146 */             if (key != null) {
/* 147 */               String text = JavaLocalization.getInstance().getLocalizedRPGText(key);
/* 148 */               MainGameState.getChatUIController().createChatBubble(text, text, node);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 154 */       this.model.removeChangeListener(this.changeListener);
/* 155 */       this.model.disposeModel();
/*     */     } 
/*     */   }
/*     */   
/*     */   private BContainer createPriceDescContainer() {
/* 160 */     BContainer bContainer = new BContainer((BLayoutManager)new BorderLayout());
/* 161 */     this.playerCurrencyContainer = new PriceDescContainer(this.model.getPlayerCurrency());
/* 162 */     bContainer.add((BComponent)this.playerCurrencyContainer, BorderLayout.WEST);
/* 163 */     return bContainer;
/*     */   }
/*     */   private class VendorChangeListener implements VendorModel.ChangeListener { private VendorChangeListener() {}
/*     */     
/*     */     public void vendorItemsChanged() {
/* 168 */       VendorWindow.this.activeList.vendorItemsChanged();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void buyItemHoverEnter(VendorModelItem item) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void buyItem(boolean buy, VendorItemButton item) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void playerCurrencyChanged(String classId, int amount) {
/* 182 */       VendorWindow.this.playerCurrencyContainer.setPriceDescSet(VendorWindow.this.model.getPlayerCurrency());
/* 183 */       VendorWindow.this.activeList.currencyChanged(classId, amount);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void itemSold(VendorModelItem vendorModelItem) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void newItemInInventory(String classId, int tier) {
/* 193 */       VendorWindow.this.activeList.updateAvailableItems();
/* 194 */       TcgUI.getUISoundPlayer().play("TransactionBuying");
/*     */     }
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
/*     */     public void sellItem(boolean sell, SellInventoryItem item) {} }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void clientItemDropped(ClientItem clientItem, int inventoryId, int slotId) {
/* 214 */     if (clientItem == null || isCurrency(clientItem)) {
/*     */       return;
/*     */     }
/*     */     
/* 218 */     this.model.sellItem(inventoryId, Inventory.TYPE_INVENTORY, slotId);
/*     */   }
/*     */   
/*     */   private boolean isCurrency(ClientItem clientItem) {
/* 222 */     return (clientItem.getValueClassId() == null || clientItem.getValueClassId().isEmpty());
/*     */   }
/*     */   private class VendorBDropListener extends BDropListener { private VendorBDropListener() {}
/*     */     
/*     */     protected void drop(BDropEvent bDropEvent) {
/* 227 */       if (bDropEvent.getDragEvent().getDraggedObject() instanceof DragItemContent) {
/* 228 */         DragItemContent dragItemContent = (DragItemContent)bDropEvent.getDragEvent().getDraggedObject();
/* 229 */         VendorWindow.this.clientItemDropped((ClientItem)dragItemContent.getItem(), dragItemContent.getInventoryId(), dragItemContent.getSlotId());
/*     */       } 
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\VendorWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */