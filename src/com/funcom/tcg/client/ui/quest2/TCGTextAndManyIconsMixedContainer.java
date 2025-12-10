/*     */ package com.funcom.tcg.client.ui.quest2;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.TextWithIcons;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.layout.DimenInfo;
/*     */ import com.jmex.bui.layout.GroupLayout;
/*     */ import com.jmex.bui.util.Dimension;
/*     */ import com.jmex.bui.util.Insets;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ 
/*     */ public class TCGTextAndManyIconsMixedContainer
/*     */   extends BContainer
/*     */   implements MissionsContainer.IconDetailPair
/*     */ {
/*     */   private BackgroundIconLabel primaryIcon;
/*     */   private TextWithIcons textWithIcons;
/*  21 */   int pos = 0;
/*     */   private ResourceManager resourceManager;
/*     */   private MyLayoutManager groupLayout;
/*     */   
/*     */   public TCGTextAndManyIconsMixedContainer(ResourceManager resourceManager) {
/*  26 */     this.resourceManager = resourceManager;
/*  27 */     this.groupLayout = new MyLayoutManager();
/*  28 */     setLayoutManager((BLayoutManager)this.groupLayout);
/*     */   }
/*     */   
/*     */   public void update(MissionObjective mission) {
/*  32 */     update(mission.getObjectiveIconPath(), mission.getObjectiveText());
/*     */   }
/*     */   
/*     */   public void update(String iconPath, String textWithIcons) {
/*  36 */     clear();
/*  37 */     if (iconPath != null && iconPath.length() != 0) {
/*  38 */       this.primaryIcon = new BackgroundIconLabel(this.resourceManager, iconPath);
/*  39 */       add((BComponent)this.primaryIcon);
/*     */     } else {
/*  41 */       add(new BComponent());
/*     */     } 
/*  43 */     this.textWithIcons = new TextWithIcons(textWithIcons);
/*     */     
/*  45 */     while (this.textWithIcons.hasNext()) {
/*  46 */       if (this.textWithIcons.getNextType() == TextWithIcons.TokenType.IMAGE) {
/*  47 */         add((BComponent)new BackgroundIconLabel(this.resourceManager, this.textWithIcons.getNext())); continue;
/*  48 */       }  if (this.textWithIcons.getNextType() == TextWithIcons.TokenType.TEXT) {
/*  49 */         splitAndAdd(this.textWithIcons.getNext());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void splitAndAdd(String next) {
/*  55 */     String[] strings = next.split(" ");
/*  56 */     for (String string : strings) {
/*  57 */       add((BComponent)new BLabel(string));
/*     */     }
/*     */   }
/*     */   
/*     */   public void clear() {
/*  62 */     removeAll();
/*     */   }
/*     */   
/*     */   private class MyLayoutManager extends GroupLayout {
/*     */     private MyLayoutManager() {}
/*     */     
/*     */     public Dimension computePreferredSize(BContainer target, int whint, int hhint) {
/*  69 */       DimenInfo info = computeDimens(target, true, whint, hhint);
/*  70 */       Dimension dims = new Dimension();
/*  71 */       dims.width = info.totwid;
/*  72 */       dims.width += (info.count - 1) * this._gap;
/*  73 */       dims.height = info.maxhei / 2;
/*  74 */       return dims;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void layoutContainer(BContainer target) {
/*  80 */       Rectangle b = target.getBounds();
/*  81 */       Insets insets = target.getInsets();
/*  82 */       b.width -= insets.getHorizontal();
/*  83 */       b.height -= insets.getVertical();
/*  84 */       DimenInfo info = computeDimens(target, true, b.width, b.height);
/*  85 */       int nk = target.getComponentCount();
/*     */ 
/*     */       
/*  88 */       if (nk == 0) {
/*     */         return;
/*     */       }
/*     */       
/*  92 */       int totgap = this._gap * (info.count - 1);
/*     */       
/*  94 */       int totwid = info.totwid + totgap;
/*     */       
/*  96 */       boolean _tooWide = (totwid > b.width);
/*  97 */       int sx = insets.left;
/*  98 */       int linenr = 0;
/*  99 */       int startComp = 0;
/*     */       
/* 101 */       BComponent child = target.getComponent(startComp);
/*     */       
/* 103 */       while (info.dimens[startComp] != null) {
/*     */ 
/*     */         
/* 106 */         child.setBounds(sx, insets.bottom + b.height - b.height + (b.height - (info.dimens[0]).height) / 2, (info.dimens[startComp]).width, (info.dimens[startComp]).height);
/*     */         
/* 108 */         sx += child.getWidth();
/* 109 */         startComp++;
/* 110 */         if (startComp < nk) {
/* 111 */           child = target.getComponent(startComp);
/* 112 */           if (!(child instanceof BackgroundIconLabel)) {
/*     */             break;
/*     */           }
/*     */           continue;
/*     */         } 
/*     */         break;
/*     */       } 
/* 119 */       int left = sx;
/*     */       
/* 121 */       for (int i = startComp; i < nk; i++) {
/*     */         
/* 123 */         if (info.dimens[i] != null) {
/*     */           int sy;
/*     */ 
/*     */           
/* 127 */           if (_tooWide)
/* 128 */           { if (sx + (info.dimens[i]).width > b.width) {
/* 129 */               sx = left;
/* 130 */               linenr++;
/*     */             } 
/* 132 */             if (linenr == 0)
/* 133 */             { sy = insets.bottom + b.height - b.height / 2 + (b.height / 2 - (info.dimens[i]).height) / 2; }
/* 134 */             else { sy = insets.bottom + b.height - b.height + (b.height / 2 - (info.dimens[i]).height) / 2; }
/*     */              }
/* 136 */           else { sy = insets.bottom + (b.height - (info.dimens[i]).height) / 2; }
/*     */           
/* 138 */           child = target.getComponent(i);
/* 139 */           child.setBounds(sx, sy, (info.dimens[i]).width, (info.dimens[i]).height);
/* 140 */           sx += child.getWidth() + this._gap;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\quest2\TCGTextAndManyIconsMixedContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */