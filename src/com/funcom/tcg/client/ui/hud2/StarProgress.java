/*     */ package com.funcom.tcg.client.ui.hud2;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BStyleSheet;
/*     */ import com.jmex.bui.icon.BIcon;
/*     */ import com.jmex.bui.icon.ImageIcon;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.layout.HGroupLayout;
/*     */ import com.jmex.bui.layout.Justification;
/*     */ import com.jmex.bui.layout.Policy;
/*     */ import com.jmex.bui.util.Dimension;
/*     */ 
/*     */ public class StarProgress extends BContainer {
/*  16 */   private static final Dimension PREFERRED_SIZE = new Dimension(66, 22);
/*     */   private static final String STYLE_DEFAULT = "starprogress";
/*  18 */   private static final float[] DEFAULT_THRESHOLDS = new float[] { 0.12F, 0.25F, 0.5F };
/*     */   private BLabel star1;
/*     */   private BLabel star2;
/*     */   private BLabel star3;
/*     */   private BIcon fullIcon;
/*     */   private BIcon emptyIcon;
/*     */   private float value;
/*     */   private float[] thresholds;
/*     */   
/*     */   public StarProgress() {
/*  28 */     super((BLayoutManager)new HGroupLayout(Justification.CENTER, Policy.STRETCH));
/*  29 */     initialize();
/*     */   }
/*     */   
/*     */   public StarProgress(BImage fullIcon, BImage emptyIcon) {
/*  33 */     super((BLayoutManager)new HGroupLayout(Justification.CENTER, Policy.STRETCH));
/*  34 */     this.fullIcon = (BIcon)new ImageIcon(fullIcon);
/*  35 */     this.emptyIcon = (BIcon)new ImageIcon(emptyIcon);
/*  36 */     initialize();
/*     */   }
/*     */   
/*     */   private void initialize() {
/*  40 */     this.value = 0.0F;
/*  41 */     this.thresholds = DEFAULT_THRESHOLDS;
/*  42 */     initializeLabels();
/*  43 */     setPreferredSize(PREFERRED_SIZE.width, PREFERRED_SIZE.height);
/*     */   }
/*     */   
/*     */   private void initializeLabels() {
/*  47 */     layoutComponents();
/*  48 */     updateLabels();
/*     */   }
/*     */   
/*     */   public float getValue() {
/*  52 */     return this.value;
/*     */   }
/*     */   
/*     */   public void setValue(float value) {
/*  56 */     if (value < 0.0F || value > 1.0F)
/*  57 */       throw new IllegalArgumentException("Value out of range, acceptable range 0.0 - 1.0"); 
/*  58 */     this.value = value;
/*  59 */     updateLabels();
/*     */   }
/*     */   
/*     */   private void updateLabels() {
/*  63 */     this.star1.setIcon(this.emptyIcon);
/*  64 */     this.star2.setIcon(this.emptyIcon);
/*  65 */     this.star3.setIcon(this.emptyIcon);
/*     */     
/*  67 */     if (this.value > this.thresholds[0])
/*  68 */       this.star1.setIcon(this.fullIcon); 
/*  69 */     if (this.value > this.thresholds[1])
/*  70 */       this.star2.setIcon(this.fullIcon); 
/*  71 */     if (this.value > this.thresholds[2])
/*  72 */       this.star3.setIcon(this.fullIcon); 
/*     */   }
/*     */   
/*     */   private void layoutComponents() {
/*  76 */     this.star1 = createLabel();
/*  77 */     add((BComponent)this.star1);
/*  78 */     this.star2 = createLabel();
/*  79 */     add((BComponent)this.star2);
/*  80 */     this.star3 = createLabel();
/*  81 */     add((BComponent)this.star3);
/*     */   }
/*     */   
/*     */   public void setThresholds(float[] thresholds) {
/*  85 */     System.arraycopy(thresholds, 0, this.thresholds, 0, 3);
/*     */   }
/*     */ 
/*     */   
/*     */   private BLabel createLabel() {
/*  90 */     return new BLabel(this.fullIcon);
/*     */   }
/*     */   
/*     */   protected String getDefaultStyleClass() {
/*  94 */     return "starprogress";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void wasAdded() {
/*  99 */     super.wasAdded();
/* 100 */     updateLabels();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configureStyle(BStyleSheet style) {
/* 105 */     super.configureStyle(style);
/*     */     
/* 107 */     BIcon icon = (BIcon)style.findProperty((BComponent)this, null, "full_icon", true);
/* 108 */     if (icon != null) {
/* 109 */       this.fullIcon = icon;
/*     */     }
/* 111 */     icon = (BIcon)style.findProperty((BComponent)this, null, "empty_icon", true);
/* 112 */     if (icon != null)
/* 113 */       this.emptyIcon = icon; 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\StarProgress.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */