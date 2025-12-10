/*     */ package com.funcom.tcg.client.ui.vendor;
/*     */ 
/*     */ import com.funcom.gameengine.jme.text.HTMLView2;
/*     */ import com.funcom.gameengine.resourcemanager.NoLocatorException;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BClickthroughLabel;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BImage;
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
/*     */ public class VendorItemButton
/*     */   extends BContainer
/*     */ {
/*     */   public static final String STYLESUFFIX_ICON = ".icon";
/*     */   private VendorItemButton INSTANCE;
/*     */   private VendorModelItem item;
/*     */   private VendorModel model;
/*     */   private boolean affordable = true;
/*     */   
/*     */   public VendorItemButton(VendorModelItem vendorItem, VendorModel model) {
/*  33 */     this.INSTANCE = this;
/*  34 */     this.item = vendorItem;
/*  35 */     this.model = model;
/*     */     
/*  37 */     setLayoutManager((BLayoutManager)new AbsoluteLayout());
/*  38 */     setSize(62, 62);
/*     */     
/*  40 */     initComponents();
/*  41 */     initListeners();
/*     */   } private boolean toBuy = false; private BButton background; private BClickthroughLabel amountLabel; private BClickthroughLabel buyStamp; private BClickthroughLabel itemIcon;
/*     */   private void initComponents() {
/*     */     BImage iconImage;
/*  45 */     this.background = new BButton("");
/*  46 */     this.background.setStyleClass("item.background");
/*     */     
/*  48 */     this.itemIcon = new BClickthroughLabel();
/*  49 */     this.itemIcon.setStyleClass(getStyleClass() + ".icon");
/*  50 */     String itemIconPath = this.item.getIcon();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  55 */       iconImage = (BImage)TcgGame.getResourceManager().getResource(BImage.class, itemIconPath);
/*     */     }
/*  57 */     catch (NoLocatorException e) {
/*  58 */       e.printStackTrace();
/*  59 */       throw new RuntimeException("Missing image for destination portal: " + itemIconPath);
/*     */     }
/*  61 */     catch (IllegalArgumentException ex) {
/*  62 */       ex.printStackTrace();
/*  63 */       throw new RuntimeException("illegal argument? icon path: " + itemIconPath + ", item: " + this.item.getName());
/*     */     } 
/*  65 */     ImageBackground imageBackground = new ImageBackground(ImageBackgroundMode.SCALE_XY, iconImage);
/*  66 */     remove((BComponent)this.itemIcon);
/*  67 */     this.itemIcon.setBackground(0, (BBackground)imageBackground);
/*  68 */     this.itemIcon.setBackground(1, (BBackground)imageBackground);
/*     */ 
/*     */     
/*  71 */     this.amountLabel = new BClickthroughLabel(String.valueOf(this.item.getItemAmount()));
/*  72 */     this.amountLabel.setStyleClass("amount.label");
/*     */     
/*  74 */     this.buyStamp = new BClickthroughLabel("");
/*  75 */     this.buyStamp.setStyleClass("item.buystamp");
/*     */     
/*  77 */     add((BComponent)this.background, new Rectangle(0, 0, getWidth(), getHeight()));
/*  78 */     add((BComponent)this.itemIcon, new Rectangle(7, 8, 48, 48));
/*  79 */     add((BComponent)this.amountLabel, new Rectangle(getWidth() - 50, getHeight() - 25, 50, 25));
/*  80 */     add((BComponent)this.buyStamp, new Rectangle(0, 0, getWidth(), getHeight()));
/*     */   }
/*     */ 
/*     */   
/*     */   private void initListeners() {
/*  85 */     this.background.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/*  88 */             if (VendorItemButton.this.item != null && (VendorItemButton.this.affordable || VendorItemButton.this.toBuy)) {
/*  89 */               VendorItemButton.this.toBuy = !VendorItemButton.this.toBuy;
/*  90 */               VendorItemButton.this.buyStamp.setVisible(VendorItemButton.this.toBuy);
/*  91 */               VendorItemButton.this.model.buyItemToggle(VendorItemButton.this.toBuy, VendorItemButton.this.INSTANCE);
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/*  96 */     this.background.addListener((ComponentListener)new MouseListener()
/*     */         {
/*     */           public void mouseEntered(MouseEvent event) {
/*  99 */             VendorItemButton.this.model.buyHover(VendorItemButton.this.item);
/*     */           }
/*     */ 
/*     */           
/*     */           public void mouseExited(MouseEvent event) {
/* 104 */             VendorItemButton.this.model.hoverExit();
/*     */           }
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
/*     */   public void setVisible(boolean visible) {
/* 119 */     super.setVisible(visible);
/* 120 */     this.buyStamp.setVisible(this.toBuy);
/* 121 */     this.amountLabel.setVisible((this.item.getItemAmount() > 1));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAffordable(boolean affordable) {
/* 126 */     if (this.affordable != affordable) {
/* 127 */       this.affordable = affordable;
/*     */     }
/*     */     
/* 130 */     setEnabled((affordable || this.toBuy));
/* 131 */     this.itemIcon.setAlpha((affordable || this.toBuy) ? 1.0F : 0.5F);
/*     */   }
/*     */   
/*     */   public VendorModelItem getItem() {
/* 135 */     return this.item;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/* 140 */     super.setEnabled(enabled);
/* 141 */     this.itemIcon.setAlpha(enabled ? 1.0F : 0.5F);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTooltipText() {
/* 146 */     if (this.item == null) {
/* 147 */       return null;
/*     */     }
/* 149 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected BComponent createTooltipComponent(String tiptext) {
/* 155 */     return (BComponent)new HTMLView2(tiptext, TcgGame.getResourceManager());
/*     */   }
/*     */   
/*     */   public void setToBuy(boolean toBuy) {
/* 159 */     this.toBuy = toBuy;
/*     */     
/* 161 */     this.buyStamp.setVisible(toBuy);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\vendor\VendorItemButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */