/*     */ package com.funcom.tcg.client.ui.inventory;
/*     */ 
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*     */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*     */ import com.funcom.tcg.client.ui.BScrollPaneTcg;
/*     */ import com.funcom.tcg.client.ui.BWindowTcg;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.hud2.VendorWindow;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BScrollPane;
/*     */ import com.jmex.bui.Spacer;
/*     */ import com.jmex.bui.dragndrop.BDragListener;
/*     */ import com.jmex.bui.dragndrop.BDropEvent;
/*     */ import com.jmex.bui.dragndrop.BDropListener;
/*     */ import com.jmex.bui.event.BEvent;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.event.EventListener;
/*     */ import com.jmex.bui.event.MouseEvent;
/*     */ import com.jmex.bui.event.MouseListener;
/*     */ import com.jmex.bui.icon.BIcon;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.layout.GroupLayout;
/*     */ import com.jmex.bui.layout.HGroupLayout;
/*     */ import com.jmex.bui.layout.Justification;
/*     */ import com.jmex.bui.layout.Policy;
/*     */ import com.jmex.bui.layout.TableLayout;
/*     */ 
/*     */ 
/*     */ public class InventoryWindow
/*     */   extends BWindowTcg
/*     */ {
/*     */   @Deprecated
/*     */   private static final int DEFAULT_SLOT_COUNT = 20;
/*     */   public static final String DEFAULT_STYLE = "inventorywindow.container";
/*     */   public static final String EXTRA_SLOTS_STYLE = "inventorywindow.container.extraslots";
/*     */   private static final int WINDOW_WIDTH = 385;
/*     */   private static final int WINDOW_HEIGHT = 464;
/*     */   
/*     */   public InventoryWindow(Inventory inventory, LocalClientPlayer clientPlayer) {
/*  43 */     super(TcgGame.getLocalizedText("inventorywindow.title", new String[0]));
/*     */     
/*  45 */     setSize(385, 464);
/*  46 */     this.inventory = inventory;
/*  47 */     this.clientPlayer = clientPlayer;
/*  48 */     this.myInventoryListener = new InventoryWindowUpdater(this);
/*     */     
/*  50 */     inventory.addChangeListener(this.myInventoryListener);
/*  51 */     layoutGui();
/*  52 */     synchronizeWithInventory();
/*     */   }
/*     */   private Inventory.ChangeListener myInventoryListener; private BContainer slotsContainer; private LocalClientPlayer clientPlayer; private Inventory inventory; private BScrollPane itemScroll;
/*     */   private void layoutGui() {
/*  56 */     this.clientArea.add((BComponent)createTabsContainer());
/*  57 */     this.clientArea.add((BComponent)createSlotsContainer());
/*     */   }
/*     */   
/*     */   private BContainer createTabsContainer() {
/*  61 */     BContainer tabsContainer = new BContainer((BLayoutManager)new HGroupLayout(Justification.LEFT, Policy.CONSTRAIN));
/*     */     
/*  63 */     tabsContainer.add((BComponent)new Spacer(15, -1), new GroupLayout.Constraints(true));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     createTrashCan(tabsContainer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     return tabsContainer;
/*     */   }
/*     */ 
/*     */   
/*     */   private void createTrashCan(BContainer tabsContainer) {
/* 103 */     tabsContainer.add((BComponent)new Spacer(15, -1), new GroupLayout.Constraints(true));
/* 104 */     BLabel trascCanButton = new BLabel("");
/* 105 */     trascCanButton.addListener((ComponentListener)new BDropListener() {
/*     */           protected void drop(BDropEvent bDropEvent) {
/* 107 */             if (bDropEvent.getDragEvent().getDraggedObject() instanceof DragItemContent) {
/* 108 */               DragItemContent itemButtonContent = (DragItemContent)bDropEvent.getDragEvent().getDraggedObject();
/*     */               
/* 110 */               InventoryWindow.this.clientPlayer.removeItem(InventoryWindow.this.inventory.getId(), Inventory.TYPE_INVENTORY, itemButtonContent.getSlotId());
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 115 */     trascCanButton.setPreferredSize(42, 52);
/* 116 */     trascCanButton.setEnabled(false);
/* 117 */     trascCanButton.setStyleClass("trashcan");
/* 118 */     tabsContainer.add((BComponent)trascCanButton, new GroupLayout.Constraints(true));
/*     */   }
/*     */   
/*     */   private BContainer createSlotsContainer() {
/* 122 */     TableLayout tableLayout = new TableLayout(4, 1, 1);
/* 123 */     tableLayout.setHorizontalAlignment(TableLayout.CENTER);
/* 124 */     tableLayout.setVerticalAlignment(TableLayout.CENTER);
/* 125 */     BContainer container = new BContainer((BLayoutManager)tableLayout);
/*     */     
/* 127 */     this.slotsContainer = new BContainer((BLayoutManager)tableLayout);
/* 128 */     this.slotsContainer.setStyleClass("inventorywindow.container");
/*     */     
/* 130 */     this.itemScroll = (BScrollPane)new BScrollPaneTcg((BComponent)this.slotsContainer, false);
/* 131 */     this.itemScroll.setPreferredSize(345, 360);
/* 132 */     container.add((BComponent)this.itemScroll);
/*     */     
/* 134 */     for (int i = 0; i < ((ListInventory)this.inventory).getSlotCount(); i++) {
/* 135 */       addSlotButton(i);
/*     */     }
/* 137 */     return container;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addSlotButton(int i) {
/* 142 */     ItemButton b = new ItemButton(i);
/* 143 */     addDefaultListeners(i, b);
/* 144 */     b.setPreferredSize(64, 64);
/*     */     
/* 146 */     if (i >= 20) {
/* 147 */       b.setStyleClass("inventorywindow.container.extraslots");
/*     */     }
/* 149 */     this.slotsContainer.add((BComponent)b);
/*     */   }
/*     */   
/*     */   private void addDefaultListeners(int i, final ItemButton b) {
/* 153 */     b.addListener((ComponentListener)new BDragListener((BComponent)b, new DragItemContent(b, this.inventory, i), new BDragListener.IconRequest() {
/*     */             public BIcon getIcon() {
/* 155 */               if (b.getItem() != null)
/* 156 */                 return TcgUI.getIconProvider().getIconForItem(b.getItem()); 
/* 157 */               return null;
/*     */             }
/*     */           }));
/*     */     
/* 161 */     b.addListener((ComponentListener)new ItemMouseListener(b, this.inventory, this.clientPlayer));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dismiss() {
/* 167 */     this.inventory.removeChangeListener(this.myInventoryListener);
/* 168 */     super.dismiss();
/*     */   }
/*     */   
/*     */   private void synchronizeWithInventory() {
/* 172 */     int index = -1;
/* 173 */     for (InventoryItem item : this.inventory) {
/* 174 */       index++;
/* 175 */       if (index > this.slotsContainer.getComponentCount() - 1) {
/* 176 */         addSlotButton(index);
/*     */       }
/* 178 */       ItemButton button = (ItemButton)this.slotsContainer.getComponent(index);
/* 179 */       button.setItem(item);
/*     */     } 
/*     */ 
/*     */     
/* 183 */     if (this.slotsContainer.getComponentCount() <= 20) {
/* 184 */       this.itemScroll.getVerticalScrollBar().setVisible(false);
/*     */     } else {
/* 186 */       this.itemScroll.getVerticalScrollBar().setVisible(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class InventoryWindowUpdater implements Inventory.ChangeListener {
/*     */     private InventoryWindow inventoryWindow;
/*     */     
/*     */     private InventoryWindowUpdater(InventoryWindow inventoryWindow) {
/* 194 */       this.inventoryWindow = inventoryWindow;
/*     */     }
/*     */ 
/*     */     
/*     */     public void slotChanged(Inventory inventory, int slotId, InventoryItem oldItem, InventoryItem newItem) {
/* 199 */       this.inventoryWindow.synchronizeWithInventory();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ItemMouseListener implements MouseListener, EventListener {
/*     */     private ItemButton button;
/*     */     private Inventory inventory;
/*     */     private LocalClientPlayer clientPlayer;
/* 207 */     private int clickCount = 0;
/*     */     private boolean pressed;
/*     */     private boolean armed;
/*     */     private boolean dragged;
/*     */     private int pressedX;
/*     */     private int pressedY;
/*     */     private static final int DRAG_LIMIT = 5;
/*     */     
/*     */     public ItemMouseListener(ItemButton button, Inventory inventory, LocalClientPlayer clientPlayer) {
/* 216 */       this.button = button;
/* 217 */       this.inventory = inventory;
/* 218 */       this.clientPlayer = clientPlayer;
/*     */     }
/*     */     
/*     */     private void buttonClicked(MouseEvent event) {
/* 222 */       InventoryItem item = this.button.getItem();
/* 223 */       if (item != null) {
/* 224 */         if (event.getButton() == 0) {
/* 225 */           this.clickCount++;
/* 226 */           if (this.clickCount == 2) {
/* 227 */             this.button.getItem().use((ClientPlayer)this.clientPlayer, this.inventory.getId(), Inventory.TYPE_INVENTORY, this.button.getId(), this.clientPlayer.getRotation(), 0.0D);
/*     */ 
/*     */ 
/*     */             
/* 231 */             this.clickCount = 0;
/*     */           } 
/* 233 */         } else if (event.getButton() == 1) {
/* 234 */           if (TcgUI.isWindowOpen(VendorWindow.class)) {
/* 235 */             this.clientPlayer.sellItem(this.inventory.getId(), Inventory.TYPE_INVENTORY, this.button.getId());
/*     */           } else {
/* 237 */             this.button.getItem().use((ClientPlayer)this.clientPlayer, this.inventory.getId(), Inventory.TYPE_INVENTORY, this.button.getId(), this.clientPlayer.getRotation(), 0.0D);
/*     */           } 
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void mousePressed(MouseEvent event) {
/* 247 */       this.pressedX = event.getX();
/* 248 */       this.pressedY = event.getY();
/* 249 */       this.pressed = true;
/* 250 */       this.armed = true;
/*     */     }
/*     */     
/*     */     public void mouseReleased(MouseEvent event) {
/* 254 */       if (this.armed && this.pressed && !this.dragged) {
/* 255 */         buttonClicked(event);
/* 256 */         this.armed = false;
/*     */       } 
/* 258 */       this.pressed = false;
/* 259 */       this.dragged = false;
/*     */     }
/*     */     
/*     */     public void mouseEntered(MouseEvent event) {
/* 263 */       this.armed = this.pressed;
/*     */     }
/*     */     
/*     */     public void mouseExited(MouseEvent event) {
/* 267 */       this.armed = false;
/* 268 */       this.clickCount = 0;
/*     */     }
/*     */     
/*     */     public void eventDispatched(BEvent event) {
/* 272 */       if (event instanceof MouseEvent) {
/* 273 */         MouseEvent e = (MouseEvent)event;
/* 274 */         if (e.getType() == 5 && this.pressed) {
/* 275 */           int dragDistance = Math.abs(e.getX() - this.pressedX) + Math.abs(e.getY() - this.pressedY);
/* 276 */           if (dragDistance > 5)
/* 277 */             this.dragged = true; 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\inventory\InventoryWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */