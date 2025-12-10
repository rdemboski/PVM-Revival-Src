/*    */ package com.funcom.tcg.client.ui.pets3;
/*    */ 
/*    */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*    */ import com.funcom.tcg.client.model.rpg.PetEventsListener;
/*    */ import com.funcom.tcg.client.ui.TCGToolTipManager;
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BLabel;
/*    */ import com.jmex.bui.background.BBackground;
/*    */ import com.jmex.bui.background.ImageBackground;
/*    */ import com.jmex.bui.dragndrop.BDragListener;
/*    */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*    */ import com.jmex.bui.event.ComponentListener;
/*    */ import com.jmex.bui.icon.BIcon;
/*    */ import com.jmex.bui.icon.ImageIcon;
/*    */ import com.jmex.bui.util.Rectangle;
/*    */ 
/*    */ 
/*    */ public class PetWindowButton
/*    */   extends AbstractPetButton
/*    */ {
/*    */   private static final String OVERLAY_STYLE = "overlay-shadow";
/*    */   private BLabel overlayImage;
/*    */   private boolean overlayVisible = false;
/*    */   
/*    */   public PetWindowButton(PetWindowButtonModel model, TCGToolTipManager tooltipManager, boolean subscriber) {
/* 27 */     super(model, tooltipManager, subscriber);
/* 28 */     this.overlayImage = new BLabel("");
/* 29 */     this.overlayImage.setStyleClass("overlay-shadow");
/* 30 */     add((BComponent)this.overlayImage, new Rectangle());
/*    */   }
/*    */   
/*    */   public void setOverlayVisible(boolean overlayVisible) {
/* 34 */     this.overlayVisible = overlayVisible;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void updateSizes() {
/* 39 */     super.updateSizes();
/* 40 */     if (this.overlayVisible) {
/* 41 */       this.absoluteButChangeableLayout.setConstraints((BComponent)this.overlayImage, new Rectangle(-(getInsets()).left, -(getInsets()).bottom, this._width, this._height));
/*    */     }
/*    */     
/* 44 */     this.overlayImage.setVisible(this.overlayVisible);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void addListeners() {
/* 50 */     addListener((ComponentListener)new BDragListener((BComponent)this, new DragPetContent(this.model.getPet()), new BDragListener.IconRequest()
/*    */           {
/*    */             public BIcon getIcon()
/*    */             {
/* 54 */               return (PetWindowButton.this._enabled && PetWindowButton.this.model.getPet().hasPlayerPet() && PetWindowButton.this.model.getIcon() != null) ? (BIcon)new ImageIcon(PetWindowButton.this.model.getIcon()) : null;
/*    */             }
/*    */           }));
/*    */     
/* 58 */     this.model.getPet().addPetEventsListener(new PetEventsListener()
/*    */         {
/*    */           public void newSkillAcquired(ClientPet clientPet, ClientItem aSkill) {}
/*    */ 
/*    */ 
/*    */ 
/*    */           
/*    */           public void skillLost(ClientPet clientPet, ClientItem aSkill) {}
/*    */ 
/*    */ 
/*    */ 
/*    */           
/*    */           public void selectedSkillsChanged(ClientPet clientPet) {}
/*    */ 
/*    */ 
/*    */ 
/*    */           
/*    */           public void levelChanged(ClientPet clientPet, int lastLevel) {
/* 76 */             PetWindowButton.this.levelIconLabel.setText(String.valueOf(PetWindowButton.this.model.getLevel()));
/* 77 */             if (PetWindowButton.this.model.getLevel() >= 40) {
/* 78 */               PetWindowButton.this.xpBar.setVisible((PetWindowButton.this.model.getLevel() < 45));
/* 79 */               ImageBackground imageBackground = new ImageBackground(ImageBackgroundMode.SCALE_XY, PetWindowButton.this.model.getIcon());
/* 80 */               PetWindowButton.this.petIconLabel.setBackground(0, (BBackground)imageBackground);
/* 81 */               PetWindowButton.this.setStyleClass("pet-button-epic");
/* 82 */               if (PetWindowButton.this.style != null) {
/* 83 */                 PetWindowButton.this.configureStyle(PetWindowButton.this.style);
/* 84 */                 PetWindowButton.this.invalidate();
/* 85 */                 PetWindowButton.this.layout();
/*    */               } 
/*    */             } 
/*    */           }
/*    */ 
/*    */           
/*    */           public void expChanged(ClientPet clientPet) {
/* 92 */             float progress = PetWindowButton.this.model.getCurrentXp();
/*    */             
/* 94 */             progress = (progress < 0.0F) ? 0.0F : ((progress > 1.0F) ? 1.0F : progress);
/* 95 */             PetWindowButton.this.xpBar.setProgress(progress);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\pets3\PetWindowButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */