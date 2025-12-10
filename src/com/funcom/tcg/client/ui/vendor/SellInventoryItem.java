/*     */ package com.funcom.tcg.client.ui.vendor;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.NoLocatorException;
/*     */ import com.funcom.rpgengine2.items.ItemType;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BClickthroughLabel;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.background.BBackground;
/*     */ import com.jmex.bui.background.ImageBackground;
/*     */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.event.MouseEvent;
/*     */ import com.jmex.bui.event.MouseListener;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ 
/*     */ public class SellInventoryItem
/*     */   extends BContainer
/*     */ {
/*     */   private boolean toSell;
/*     */   private ClientItem item;
/*     */   private BButton background;
/*     */   private BLabel itemIcon;
/*     */   private BLabel saleStamp;
/*     */   private SellInventoryItem INSTANCE;
/*     */   private VendorModel model;
/*     */   
/*     */   public SellInventoryItem(ClientItem item, boolean toSell, VendorModel model) {
/*  37 */     this.INSTANCE = this;
/*  38 */     setLayoutManager((BLayoutManager)new AbsoluteLayout());
/*  39 */     this.item = item;
/*  40 */     this.toSell = toSell;
/*  41 */     this.model = model;
/*     */     
/*  43 */     setSize(62, 62);
/*     */     
/*  45 */     initComponents();
/*  46 */     initListeners();
/*     */   }
/*     */ 
/*     */   
/*     */   private void initComponents() {
/*  51 */     this.background = new BButton("");
/*  52 */     this.background.setStyleClass("item.background");
/*     */     
/*  54 */     this.itemIcon = (BLabel)new BClickthroughLabel();
/*  55 */     this.itemIcon.setStyleClass("preview_icon");
/*  56 */     String itemIconPath = this.item.getIcon();
/*     */     
/*  58 */     BImage iconImage = null;
/*     */     
/*     */     try {
/*  61 */       iconImage = (BImage)TcgGame.getResourceManager().getResource(BImage.class, itemIconPath);
/*     */     }
/*  63 */     catch (NoLocatorException e) {
/*  64 */       e.printStackTrace();
/*  65 */       throw new RuntimeException("Missing image for destination portal: " + itemIconPath);
/*     */     } 
/*     */     
/*  68 */     ImageBackground imageBackground = new ImageBackground(ImageBackgroundMode.SCALE_XY, iconImage);
/*  69 */     remove((BComponent)this.itemIcon);
/*  70 */     this.itemIcon.setBackground(0, (BBackground)imageBackground);
/*  71 */     this.itemIcon.setBackground(1, (BBackground)imageBackground);
/*     */ 
/*     */ 
/*     */     
/*  75 */     this.saleStamp = (BLabel)new BClickthroughLabel("");
/*  76 */     this.saleStamp.setStyleClass("item.salestamp");
/*  77 */     this.saleStamp.setVisible(this.toSell);
/*     */     
/*  79 */     BClickthroughLabel levelLabel = new BClickthroughLabel("" + this.item.getItemDescription().getLevel());
/*  80 */     levelLabel.setStyleClass("item.level.label");
/*     */     
/*  82 */     add((BComponent)this.background, new Rectangle(0, 0, getWidth(), getHeight()));
/*  83 */     add((BComponent)this.itemIcon, new Rectangle(7, 7, 48, 48));
/*  84 */     add((BComponent)this.saleStamp, new Rectangle(0, 0, getWidth(), getHeight()));
/*  85 */     if (!this.item.getItemType().equals(ItemType.EQUIP_HEAD))
/*  86 */       add((BComponent)levelLabel, new Rectangle(getWidth() - 35, 0, 30, 30)); 
/*     */   }
/*     */   
/*     */   private void initListeners() {
/*  90 */     this.background.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/*  93 */             if (SellInventoryItem.this.item != null) {
/*  94 */               SellInventoryItem.this.toSell = !SellInventoryItem.this.toSell;
/*  95 */               SellInventoryItem.this.saleStamp.setVisible(SellInventoryItem.this.toSell);
/*  96 */               SellInventoryItem.this.model.sellItemToggle(SellInventoryItem.this.toSell, SellInventoryItem.this.INSTANCE);
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 101 */     this.background.addListener((ComponentListener)new MouseListener()
/*     */         {
/*     */           public void mouseEntered(MouseEvent event) {
/* 104 */             SellInventoryItem.this.model.sellHover(SellInventoryItem.this.item);
/*     */           }
/*     */ 
/*     */           
/*     */           public void mouseExited(MouseEvent event) {
/* 109 */             SellInventoryItem.this.model.hoverExit();
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void mousePressed(MouseEvent event) {}
/*     */ 
/*     */ 
/*     */           
/*     */           public void mouseReleased(MouseEvent event) {}
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 126 */     super.setVisible(visible);
/*     */     
/* 128 */     this.saleStamp.setVisible(this.toSell);
/*     */   }
/*     */   
/*     */   public boolean isToSell() {
/* 132 */     return this.toSell;
/*     */   }
/*     */   
/*     */   public void setToSell(boolean toSell) {
/* 136 */     this.toSell = toSell;
/* 137 */     this.saleStamp.setVisible(toSell);
/*     */   }
/*     */   
/*     */   public ClientItem getItem() {
/* 141 */     return this.item;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\vendor\SellInventoryItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */