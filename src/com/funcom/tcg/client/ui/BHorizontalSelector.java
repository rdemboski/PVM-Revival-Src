/*     */ package com.funcom.tcg.client.ui;
/*     */ 
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.layout.GroupLayout;
/*     */ import com.jmex.bui.layout.HGroupLayout;
/*     */ import com.jmex.bui.layout.Justification;
/*     */ import com.jmex.bui.layout.Policy;
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
/*     */ public class BHorizontalSelector
/*     */   extends BContainer
/*     */ {
/*     */   private static final String STYLE_PREV_BUTTON = "button.prev";
/*     */   private static final String STYLE_NEXT_BUTTON = "button.next";
/*     */   private static final String STYLE_MIDDLE_LABEL = "label.middle";
/*     */   private static final String DEFAULT_STYLE = "horizontal-selector";
/*     */   private BButton prevButton;
/*     */   private BLabel middleText;
/*     */   private BButton nextButton;
/*     */   private SelectorModel model;
/*     */   private SelectorModel.ChangeListener selectorModelListener;
/*     */   private SelectorRenderer renderer;
/*     */   
/*     */   public BHorizontalSelector(SelectorModel model) {
/*  44 */     this(model, new ToStringRenderer());
/*     */   }
/*     */   
/*     */   public BHorizontalSelector(SelectorModel model, SelectorRenderer renderer) {
/*  48 */     setLayoutManager((BLayoutManager)new HGroupLayout(Justification.CENTER, Policy.STRETCH));
/*  49 */     this.renderer = renderer;
/*  50 */     this.model = model;
/*     */     
/*  52 */     this.selectorModelListener = new SelectorModel.ChangeListener()
/*     */       {
/*     */         public void changeEvent(SelectorModel model) {
/*  55 */           BHorizontalSelector.this.updateComponentStates();
/*     */         }
/*     */       };
/*     */     
/*  59 */     layoutComponents();
/*  60 */     initialize();
/*     */   }
/*     */ 
/*     */   
/*     */   private void initialize() {
/*  65 */     this.prevButton.addListener((ComponentListener)new ActionListener() {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/*  67 */             BHorizontalSelector.this.model.previous();
/*     */           }
/*     */         });
/*  70 */     this.nextButton.addListener((ComponentListener)new ActionListener() {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/*  72 */             BHorizontalSelector.this.model.next();
/*     */           }
/*     */         });
/*  75 */     this.model.addChangeListener(this.selectorModelListener);
/*  76 */     this.model.first();
/*     */   }
/*     */   
/*     */   private void updateComponentStates() {
/*  80 */     this.middleText.setText(this.renderer.render(this.model.getCurrent()));
/*  81 */     this.nextButton.setEnabled(this.model.hasNext());
/*  82 */     this.prevButton.setEnabled(this.model.hasPrevious());
/*     */   }
/*     */   
/*     */   private void layoutComponents() {
/*  86 */     this.prevButton = new BButton("");
/*  87 */     this.prevButton.setName("prev-button");
/*  88 */     this.prevButton.setStyleClass("button.prev");
/*  89 */     add((BComponent)this.prevButton, new GroupLayout.Constraints(true));
/*     */     
/*  91 */     this.middleText = new BLabel("");
/*  92 */     this.middleText.setName("middle-text");
/*  93 */     this.middleText.setStyleClass("label.middle");
/*  94 */     add((BComponent)this.middleText, new GroupLayout.Constraints(50));
/*     */     
/*  96 */     this.nextButton = new BButton("");
/*  97 */     this.nextButton.setName("next-button");
/*  98 */     this.nextButton.setStyleClass("button.next");
/*  99 */     add((BComponent)this.nextButton, new GroupLayout.Constraints(true));
/*     */   }
/*     */   
/*     */   public SelectorModel getModel() {
/* 103 */     return this.model;
/*     */   }
/*     */   
/*     */   public SelectorRenderer getRenderer() {
/* 107 */     return this.renderer;
/*     */   }
/*     */   
/*     */   public void setRenderer(SelectorRenderer renderer) {
/* 111 */     this.renderer = renderer;
/*     */   }
/*     */   
/*     */   public void setModel(SelectorModel model) {
/* 115 */     if (model == null) {
/* 116 */       throw new IllegalStateException();
/*     */     }
/* 118 */     this.model.removeChangeListener(this.selectorModelListener);
/* 119 */     this.model = model;
/* 120 */     this.model.addChangeListener(this.selectorModelListener);
/*     */   }
/*     */   
/*     */   protected String getDefaultStyleClass() {
/* 124 */     return "horizontal-selector";
/*     */   }
/*     */   
/*     */   public String getStyleClassPrevButton() {
/* 128 */     return this.prevButton.getStyleClass();
/*     */   }
/*     */   
/*     */   public void setStyleClassPrevButton(String s) {
/* 132 */     this.prevButton.setStyleClass(s);
/*     */   }
/*     */   
/*     */   public String getStyleClassNextButton() {
/* 136 */     return this.nextButton.getStyleClass();
/*     */   }
/*     */   
/*     */   public void setStyleClassNextButton(String s) {
/* 140 */     this.nextButton.setStyleClass(s);
/*     */   }
/*     */   
/*     */   public String getStyleClassMiddleText() {
/* 144 */     return this.middleText.getStyleClass();
/*     */   }
/*     */   
/*     */   public void setStyleClassMiddleText(String s) {
/* 148 */     this.middleText.setStyleClass(s);
/*     */   }
/*     */   
/*     */   public int getSize() {
/* 152 */     return this.model.getSize();
/*     */   }
/*     */   
/*     */   public void setSelectionIndex(int index) {
/* 156 */     this.model.setSelectionIndex(index);
/*     */   }
/*     */   
/*     */   public static class ToStringRenderer
/*     */     implements SelectorRenderer {
/*     */     public String render(Object o) {
/* 162 */       if (o == null)
/* 163 */         return "<NULL>"; 
/* 164 */       return o.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\BHorizontalSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */