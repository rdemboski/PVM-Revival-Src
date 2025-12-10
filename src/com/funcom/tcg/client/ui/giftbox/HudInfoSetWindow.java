/*     */ package com.funcom.tcg.client.ui.giftbox;
/*     */ 
/*     */ import com.funcom.commons.jme.bui.PartiallyNotInteractive;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.rpgengine2.items.ItemType;
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.funcom.tcg.client.ui.hud2.ItemInfoModelImpl;
/*     */ import com.jme.input.MouseInput;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.event.BEvent;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.layout.Justification;
/*     */ import com.jmex.bui.layout.Policy;
/*     */ import com.jmex.bui.layout.VGroupLayout;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ 
/*     */ public class HudInfoSetWindow extends BWindow implements PartiallyNotInteractive {
/*     */   private static final int DEFAULT_WINDOW_HEIGHT = 400;
/*     */   private final ModelComponentComparator componentComparator;
/*     */   protected final ResourceManager resourceManager;
/*     */   protected final Collection<HudInfoModel> infoModels;
/*     */   private VGroupLayout layout;
/*     */   private static final int MAX_VISIBLE_COMPONENTS = 5;
/*     */   
/*     */   public HudInfoSetWindow(String name, HudInfoSetModel model, ResourceManager resourceManager) {
/*  32 */     super(name, null, null);
/*  33 */     BananaResourceProvider buiResourceProvider = new BananaResourceProvider(resourceManager);
/*  34 */     this._style = BuiUtils.createMergedClassStyleSheets(getClass(), buiResourceProvider);
/*     */     
/*  36 */     this.resourceManager = resourceManager;
/*     */     
/*  38 */     this.componentComparator = new ModelComponentComparator();
/*     */     
/*  40 */     setSize(DisplaySystem.getDisplaySystem().getWidth(), 400);
/*  41 */     this.layout = new VGroupLayout(Justification.TOP, Policy.NONE);
/*  42 */     this.layout.setOffAxisJustification(Justification.LEFT);
/*  43 */     setLayoutManager((BLayoutManager)this.layout);
/*     */     
/*  45 */     this.infoModels = model.getInfoModels();
/*  46 */     updateInfoComponents();
/*     */   }
/*     */   
/*     */   public void setAlignment(Justification horizontalJustification, Justification verticalJustification) {
/*  50 */     this.layout.setJustification(verticalJustification);
/*  51 */     this.layout.setOffAxisJustification(horizontalJustification);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateInfoComponents() {
/*  56 */     for (HudInfoModel infoModel : this.infoModels) {
/*  57 */       if (!hasInfoComponent(infoModel)) {
/*  58 */         addComponent(infoModel);
/*     */       }
/*     */     } 
/*     */     
/*  62 */     BComponent[] children = (BComponent[])this._children.toArray((Object[])new BComponent[this._children.size()]);
/*  63 */     for (BComponent child : children) {
/*  64 */       if (child instanceof HudInfoComponent) {
/*  65 */         HudInfoModel modelInGui = ((HudInfoComponent)child).getModel();
/*  66 */         if (!this.infoModels.contains(modelInGui)) {
/*  67 */           remove(child);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean hasInfoComponent(HudInfoModel infoModel) {
/*  74 */     if (!this._children.isEmpty()) {
/*  75 */       for (BComponent child : this._children) {
/*  76 */         if (child instanceof HudInfoComponent && (
/*  77 */           (HudInfoComponent)child).getModel() == infoModel) {
/*  78 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHit() {
/*  88 */     int mx = MouseInput.get().getXAbsolute();
/*  89 */     int my = MouseInput.get().getYAbsolute();
/*  90 */     BComponent comp = getHitComponent(mx, my);
/*  91 */     return (comp != null && comp != this && comp.isEnabled());
/*     */   }
/*     */ 
/*     */   
/*     */   public BComponent getHitComponent(int mx, int my) {
/*  96 */     BComponent hitComponent = super.getHitComponent(mx, my);
/*  97 */     if (hitComponent != this) {
/*  98 */       return hitComponent;
/*     */     }
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void layout() {
/* 105 */     Collections.sort(this._children, this.componentComparator);
/* 106 */     super.layout();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean dispatchEvent(BEvent event) {
/* 111 */     return super.dispatchEvent(event);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(Renderer renderer) {
/* 116 */     updateInfoComponents();
/* 117 */     int i = 0;
/* 118 */     for (BComponent child : this._children) {
/* 119 */       if (i < 5) {
/* 120 */         child.setVisible(true);
/* 121 */         ((HudInfoComponent)child).update();
/*     */       } else {
/* 123 */         child.setVisible(false);
/*     */       } 
/* 125 */       i++;
/*     */     } 
/* 127 */     super.render(renderer);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getDefaultStyleClass() {
/* 132 */     return "hudwindow";
/*     */   }
/*     */   
/*     */   private void addComponent(HudInfoModel infoModel) {
/* 136 */     if (infoModel instanceof ItemInfoModelImpl) {
/* 137 */       ItemInfoModelImpl itemInfo = (ItemInfoModelImpl)infoModel;
/* 138 */       if (itemInfo.getItem().getItemType().equals(ItemType.CRYSTAL)) {
/*     */         return;
/*     */       }
/*     */     } 
/* 142 */     add(createComponent(infoModel));
/*     */   }
/*     */   
/*     */   protected BComponent createComponent(HudInfoModel infoModel) {
/* 146 */     return (BComponent)new SimpleInfoComponent(infoModel, this.resourceManager);
/*     */   }
/*     */   
/*     */   private static class ModelComponentComparator
/*     */     implements Comparator<BComponent> {
/*     */     public int compare(BComponent o1, BComponent o2) {
/* 152 */       HudInfoModel model1 = ((HudInfoComponent)o1).getModel();
/* 153 */       HudInfoModel model2 = ((HudInfoComponent)o2).getModel();
/* 154 */       return model1.getPriority() - model2.getPriority();
/*     */     }
/*     */     
/*     */     private ModelComponentComparator() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\giftbox\HudInfoSetWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */