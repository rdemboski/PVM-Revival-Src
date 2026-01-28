/*     */ package com.funcom.tcg.client.ui.character;
/*     */ 
/*     */ import com.funcom.tcg.client.ui.Localizer;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import java.util.Map;
/*     */ 
/*     */ class StatComponent
/*     */   extends BContainer
/*     */ {
/*     */   private final InfoMode infoMode;
/*     */   private boolean showStatDiff;
/*     */   
/*     */   StatComponent(String styleClass, String statName, InfoMode infoMode, String nameStyleClass, String valueStyleClass, boolean showStatDiff, String statPosStyle, String statNegStyle) {
/*  19 */     super((BLayoutManager)new AbsoluteLayout());
/*  20 */     this.infoMode = infoMode;
/*  21 */     this.showStatDiff = showStatDiff;
/*  22 */     setStyleClass("stat-info-container");
/*     */     
/*  24 */     BLabel statIconLbl = new BLabel("", styleClass);
/*  25 */     add((BComponent)statIconLbl, infoMode.iconBounds);
/*     */     
/*  27 */     if (infoMode.nameBounds != null) {
/*  28 */       BLabel statNameLbl = new BLabel(statName, nameStyleClass);
/*  29 */       add((BComponent)statNameLbl, infoMode.nameBounds);
/*     */     } 
/*     */     
/*  32 */     this.statValueLbl = new BLabel("", valueStyleClass);
/*  33 */     add((BComponent)this.statValueLbl, infoMode.valueBounds);
/*  34 */     if (showStatDiff) {
/*  35 */       this.statChangePositiveLbl = new BLabel("", statPosStyle);
/*  36 */       add((BComponent)this.statChangePositiveLbl, infoMode.diffValueBounds);
/*  37 */       this.statChangeNegativeLbl = new BLabel("", statNegStyle);
/*  38 */       add((BComponent)this.statChangeNegativeLbl, infoMode.diffValueBounds);
/*     */     } 
/*     */   }
/*     */   private BLabel statValueLbl; private BLabel statChangePositiveLbl; private BLabel statChangeNegativeLbl;
/*     */   
/*     */   public void setVisible(boolean visible) {
/*  44 */     super.setVisible(visible);
/*  45 */     if (!visible) {
/*  46 */       if (this.statChangeNegativeLbl != null)
/*  47 */         this.statChangeNegativeLbl.setText(""); 
/*  48 */       if (this.statChangePositiveLbl != null)
/*  49 */         this.statChangePositiveLbl.setText(""); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setValues(int currentValue, int whatIfEquippedValue) {
/*  54 */     int diff = whatIfEquippedValue - currentValue;
/*  55 */     this.statValueLbl.setText(Integer.toString(currentValue));
/*     */     
/*  57 */     if (this.showStatDiff) {
/*  58 */       this.statChangePositiveLbl.setVisible(false);
/*  59 */       this.statChangeNegativeLbl.setVisible(false);
/*     */       
/*  61 */       String changeValue = this.infoMode.getChangeText(currentValue, whatIfEquippedValue);
/*  62 */       if (diff > 0) {
/*  63 */         this.statChangePositiveLbl.setText(changeValue);
/*  64 */         this.statChangePositiveLbl.setVisible(true);
/*  65 */       } else if (diff < 0) {
/*  66 */         this.statChangeNegativeLbl.setText(changeValue);
/*  67 */         this.statChangeNegativeLbl.setVisible(true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static class Builder
/*     */   {
/*     */     private final BContainer targetContainer;
/*     */     private final Localizer localizer;
/*     */     private final Map<Object, StatComponent> statComponents;
/*     */     private final StatComponent.InfoMode mode;
/*     */     private Rectangle bounds;
/*     */     private String styleClass;
/*     */     private String textKey;
/*     */     private Object[] mappingIds;
/*  82 */     private String nameStyleClass = "stat-name";
/*  83 */     private String valueStyleClass = "stat-value";
/*     */     private boolean showStatDiff = true;
/*  85 */     private String statPosStyle = "stat-diff-positive-value";
/*  86 */     private String statNegStyle = "stat-diff-negative-value";
/*     */     
/*     */     public Builder(BContainer targetContainer, Localizer localizer, Map<Object, StatComponent> statComponents, StatComponent.InfoMode mode) {
/*  89 */       this.targetContainer = targetContainer;
/*  90 */       this.localizer = localizer;
/*  91 */       this.statComponents = statComponents;
/*  92 */       this.mode = mode;
/*     */     }
/*     */     
/*     */     public Builder withBound(int x, int y, int width, int height) {
/*  96 */       this.bounds = new Rectangle(x, y, width, height);
/*  97 */       return this;
/*     */     }
/*     */     
/*     */     public Builder withIconStyle(String styleClass) {
/* 101 */       this.styleClass = styleClass;
/* 102 */       return this;
/*     */     }
/*     */     
/*     */     public Builder withNameStyleClass(String nameStyleClass) {
/* 106 */       this.nameStyleClass = nameStyleClass;
/* 107 */       return this;
/*     */     }
/*     */     
/*     */     public Builder withValueStyleClass(String valueStyleClass) {
/* 111 */       this.valueStyleClass = valueStyleClass;
/* 112 */       return this;
/*     */     }
/*     */     
/*     */     public Builder withStatNegStyle(String statNegStyle) {
/* 116 */       this.statNegStyle = statNegStyle;
/* 117 */       return this;
/*     */     }
/*     */     
/*     */     public Builder withStatPosStyle(String statPosStyle) {
/* 121 */       this.statPosStyle = statPosStyle;
/* 122 */       return this;
/*     */     }
/*     */     
/*     */     public Builder withoutStatDiffs() {
/* 126 */       this.showStatDiff = false;
/* 127 */       return this;
/*     */     }
/*     */     
/*     */     public Builder withTextKey(String textKey) {
/* 131 */       this.textKey = textKey;
/* 132 */       return this;
/*     */     }
/*     */     
/*     */     public Builder mappedTo(Object... mappingIds) {
/* 136 */       this.mappingIds = mappingIds;
/* 137 */       return this;
/*     */     }
/*     */     
/*     */     public void addIt() {
/* 141 */       String statName = this.localizer.getLocalizedText(getClass(), this.textKey, new String[0]);
/* 142 */       StatComponent component = new StatComponent(this.styleClass, statName, this.mode, this.nameStyleClass, this.valueStyleClass, this.showStatDiff, this.statPosStyle, this.statNegStyle);
/* 143 */       this.targetContainer.add((BComponent)component, this.bounds);
/*     */       
/* 145 */       for (Object mappingId : this.mappingIds)
/* 146 */         this.statComponents.put(mappingId, component); 
/*     */     }
/*     */   }
/*     */   
/*     */   enum InfoMode
/*     */   {
/* 152 */     ITEM_STAT(new Rectangle(0, 44, 58, 46), new Rectangle(47, 44, 133, 46), new Rectangle(0, 24, 58, 26), new Rectangle(0, 4, 58, 26))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       String getChangeText(int currentValue, int whatIfEquippedValue)
/*     */       {
/* 160 */         int diff = whatIfEquippedValue - currentValue;
/*     */         
/* 162 */         if (diff > 0)
/* 163 */           return "+" + diff; 
/* 164 */         if (diff < 0) {
/* 165 */           return Integer.toString(diff);
/*     */         }
/*     */         
/* 168 */         return "";
/*     */       } },
/* 170 */     CHARACTER_STAT(new Rectangle(0, 44, 58, 46), null, new Rectangle(0, 24, 58, 26), new Rectangle(0, 4, 58, 26))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       String getChangeText(int currentValue, int whatIfEquippedValue)
/*     */       {
/* 178 */         int diff = whatIfEquippedValue - currentValue;
/*     */         
/* 180 */         if (diff != 0) {
/* 181 */           return "(" + Integer.toString(whatIfEquippedValue) + ")";
/*     */         }
/*     */         
/* 184 */         return ""; } },
/* 185 */     LEVEL_STAT(new Rectangle(0, 0, 58, 46), null, new Rectangle(0, 9, 58, 26), new Rectangle(0, 4, 58, 26))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       String getChangeText(int currentValue, int whatIfEquippedValue)
/*     */       {
/* 193 */         return "";
/*     */       } };
/*     */     
/*     */     final Rectangle iconBounds;
/*     */     final Rectangle nameBounds;
/*     */     final Rectangle valueBounds;
/*     */     final Rectangle diffValueBounds;
/*     */     
/*     */     InfoMode(Rectangle iconBounds, Rectangle nameBounds, Rectangle valueBounds, Rectangle diffValueBounds) {
/* 202 */       this.iconBounds = iconBounds;
/* 203 */       this.nameBounds = nameBounds;
/* 204 */       this.valueBounds = valueBounds;
/* 205 */       this.diffValueBounds = diffValueBounds;
/*     */     }
/*     */     
/*     */     abstract String getChangeText(int param1Int1, int param1Int2);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\character\StatComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */