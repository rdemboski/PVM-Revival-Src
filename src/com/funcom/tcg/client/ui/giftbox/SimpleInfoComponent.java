/*     */ package com.funcom.tcg.client.ui.giftbox;
/*     */ 
/*     */ import com.funcom.commons.StringUtils;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.TextWithIcons;
/*     */ import com.funcom.tcg.client.ui.StretchedImageButton;
/*     */ import com.funcom.tcg.client.ui.hud2.HasAlphaValues;
/*     */ import com.jme.math.FastMath;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jmex.bui.BClickthroughLabel;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.event.MouseAdapter;
/*     */ import com.jmex.bui.event.MouseEvent;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.layout.BorderLayout;
/*     */ import com.jmex.bui.layout.HGroupLayout;
/*     */ import com.jmex.bui.layout.Justification;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ public class SimpleInfoComponent
/*     */   extends BContainer
/*     */   implements HudInfoComponent
/*     */ {
/*     */   private final HudInfoModel model;
/*     */   private final ResourceManager resourceManager;
/*     */   private String lastIconPath;
/*     */   private StretchedImageButton imageButton;
/*  32 */   private Integer imageButtonAlignment = BorderLayout.WEST; private BContainer textAndIconsContainer; private TextWithIcons textWithIcons; private float activateEffectCounter; private static final int MAX_COUNTER = 700;
/*     */   private long lastUpdate;
/*     */   
/*     */   public SimpleInfoComponent(HudInfoModel model, ResourceManager resourceManager) {
/*  36 */     this.model = model;
/*  37 */     this.resourceManager = resourceManager;
/*     */     
/*  39 */     setHoverEnabled(true);
/*  40 */     setLayoutManager((BLayoutManager)new BorderLayout(4, 0));
/*     */     
/*  42 */     if (hasAction()) {
/*     */       
/*  44 */       setEnabled(asAction().isActivatable());
/*  45 */       addListener((ComponentListener)new MouseAdapter()
/*     */           {
/*     */             private boolean armed = false;
/*     */             
/*     */             public void mouseReleased(MouseEvent event) {
/*  50 */               if (this.armed && SimpleInfoComponent.this.asAction().isActivatable()) {
/*  51 */                 SimpleInfoComponent.this.asAction().activate();
/*     */               }
/*     */             }
/*     */             
/*     */             public void mouseExited(MouseEvent event) {
/*  56 */               this.armed = false;
/*     */             }
/*     */ 
/*     */             
/*     */             public void mousePressed(MouseEvent event) {
/*  61 */               this.armed = true;
/*     */             }
/*     */           });
/*     */     } 
/*     */ 
/*     */     
/*  67 */     createChildren();
/*  68 */     update();
/*     */   }
/*     */   
/*     */   private HudInfoAction asAction() {
/*  72 */     return (HudInfoAction)this.model;
/*     */   }
/*     */   
/*     */   private boolean hasAction() {
/*  76 */     return this.model instanceof HudInfoAction;
/*     */   }
/*     */   
/*     */   public void setIconButtonBorderAlignment(Integer alignment) {
/*  80 */     this.imageButtonAlignment = alignment;
/*  81 */     remove((BComponent)this.imageButton);
/*  82 */     add((BComponent)this.imageButton, alignment);
/*     */ 
/*     */     
/*  85 */     if (this.imageButtonAlignment == BorderLayout.WEST) {
/*  86 */       this.textAndIconsContainer.setLayoutManager((BLayoutManager)new HGroupLayout(Justification.RIGHT));
/*     */     }
/*  88 */     else if (this.imageButtonAlignment == BorderLayout.EAST) {
/*  89 */       this.textAndIconsContainer.setLayoutManager((BLayoutManager)new HGroupLayout(Justification.LEFT));
/*     */     } else {
/*     */       
/*  92 */       this.textAndIconsContainer.setLayoutManager((BLayoutManager)new HGroupLayout(Justification.CENTER));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void layout() {
/*  98 */     this.imageButton.setVisible((isVisible() && this.imageButton.getImage() != null));
/*     */     
/* 100 */     int returnSize = 0;
/* 101 */     for (BComponent label : this._children) {
/* 102 */       int preSize = label.getHeight();
/* 103 */       if (preSize == 0) {
/* 104 */         preSize = label.getPreferredSize(-1, -1).getHeight();
/*     */       }
/* 106 */       returnSize = Math.max(returnSize, preSize);
/*     */     } 
/*     */     
/* 109 */     this.imageButton.setPreferredSize(returnSize, returnSize);
/*     */     
/* 111 */     for (int t = 0; t < this.textAndIconsContainer.getComponentCount(); t++) {
/* 112 */       BComponent bComponent = this.textAndIconsContainer.getComponent(t);
/* 113 */       if (bComponent instanceof StretchedImageButton) {
/* 114 */         bComponent.setPreferredSize(returnSize, returnSize);
/*     */       }
/*     */     } 
/*     */     
/* 118 */     super.layout();
/*     */   }
/*     */   
/*     */   private void createChildren() {
/* 122 */     this.imageButton = new StretchedImageButton(null);
/*     */     
/* 124 */     this.imageButton.setClickable(false);
/* 125 */     add((BComponent)this.imageButton, this.imageButtonAlignment);
/*     */     
/* 127 */     this.textWithIcons = new TextWithIcons("");
/* 128 */     this.textAndIconsContainer = new BContainer();
/* 129 */     this.textAndIconsContainer.setLayoutManager((BLayoutManager)new HGroupLayout(Justification.RIGHT));
/* 130 */     add((BComponent)this.textAndIconsContainer, BorderLayout.CENTER);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/* 135 */     super.setEnabled(enabled);
/* 136 */     if (!enabled)
/*     */     {
/* 138 */       this._hover = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(Renderer renderer) {
/* 144 */     long now = System.nanoTime();
/* 145 */     super.render(renderer);
/* 146 */     if (this.lastUpdate > 0L && this.activateEffectCounter > 0.0F) {
/*     */       
/* 148 */       int minSize = getHeight();
/* 149 */       if (getWidth() < minSize) {
/* 150 */         minSize = getWidth();
/*     */       }
/* 152 */       int offset = (int)(minSize * 0.71D * FastMath.sin(this.activateEffectCounter * 3.1415927F / 700.0F));
/* 153 */       effectRender(renderer, offset, 0);
/* 154 */       effectRender(renderer, -offset, 0);
/* 155 */       effectRender(renderer, 0, offset);
/* 156 */       effectRender(renderer, 0, -offset);
/*     */       
/* 158 */       long delta = now - this.lastUpdate;
/* 159 */       this.activateEffectCounter -= 1000.0F * (float)delta / (float)TimeUnit.SECONDS.toNanos(1L);
/* 160 */       if (this.activateEffectCounter < 0.005F) {
/* 161 */         this.activateEffectCounter = 0.0F;
/*     */       }
/*     */     } 
/* 164 */     this.lastUpdate = now;
/*     */   }
/*     */   
/*     */   private void effectRender(Renderer renderer, int offsetX, int offsetY) {
/* 168 */     float alpha = this.activateEffectCounter / 700.0F;
/* 169 */     float oldAlpha = getAlpha();
/* 170 */     setAlpha(alpha * oldAlpha);
/*     */     
/* 172 */     this._x -= offsetX;
/* 173 */     this._y -= offsetY;
/* 174 */     super.render(renderer);
/* 175 */     this._x += offsetX;
/* 176 */     this._y += offsetY;
/*     */     
/* 178 */     setAlpha(oldAlpha);
/*     */     
/* 180 */     this.imageButton.setAlpha(1.0F);
/*     */   }
/*     */   
/*     */   public void update() {
/* 184 */     String iconPath = this.model.getIconPath();
/* 185 */     if (!StringUtils.equals(this.lastIconPath, iconPath)) {
/* 186 */       BImage image = null;
/* 187 */       if (iconPath != null) {
/* 188 */         image = (BImage)this.resourceManager.getResource(BImage.class, iconPath);
/* 189 */         invalidate();
/*     */       } 
/* 191 */       this.imageButton.setImage(image);
/*     */       
/* 193 */       this.lastIconPath = iconPath;
/*     */     } 
/*     */     
/* 196 */     String text = this.model.getText();
/* 197 */     if (!StringUtils.equals(this.textWithIcons.getOriginalTextWithIcons(), text)) {
/* 198 */       createAndFillNewTextWithIconsContainer(text);
/* 199 */       invalidate();
/*     */     } 
/*     */     
/* 202 */     if (hasAction()) {
/* 203 */       HudInfoAction modelAction = asAction();
/* 204 */       if (isEnabled() != modelAction.isActivatable()) {
/* 205 */         this.activateEffectCounter = 700.0F;
/* 206 */         setEnabled(modelAction.isActivatable());
/*     */       } 
/* 208 */       updateAlpha();
/*     */     } 
/*     */     
/* 211 */     if (this.model instanceof HasAlphaValues)
/*     */     {
/* 213 */       setAlpha(((HasAlphaValues)this.model).getAlpha());
/*     */     }
/*     */     
/* 216 */     if (isAdded() && isVisible()) validate(); 
/*     */   }
/*     */   
/*     */   private void createAndFillNewTextWithIconsContainer(String newTextWithIcons) {
/* 220 */     this.textAndIconsContainer.removeAll();
/* 221 */     this.textWithIcons = new TextWithIcons(newTextWithIcons);
/* 222 */     while (this.textWithIcons.hasNext()) {
/* 223 */       if (this.textWithIcons.getNextType() == TextWithIcons.TokenType.IMAGE) {
/* 224 */         String iconPath = this.textWithIcons.getNext();
/* 225 */         BImage image = (BImage)this.resourceManager.getResource(BImage.class, iconPath);
/* 226 */         StretchedImageButton nextComponent = new StretchedImageButton(image);
/* 227 */         nextComponent.setClickable(false);
/* 228 */         nextComponent.setVisible(true);
/* 229 */         this.textAndIconsContainer.add((BComponent)nextComponent); continue;
/*     */       } 
/* 231 */       if (this.textWithIcons.getNextType() == TextWithIcons.TokenType.TEXT) {
/* 232 */         BClickthroughLabel nextComponent = new BClickthroughLabel(this.textWithIcons.getNext());
/* 233 */         nextComponent.setFit(BLabel.Fit.TRUNCATE);
/* 234 */         this.textAndIconsContainer.add((BComponent)nextComponent);
/* 235 */         nextComponent.setVisible(true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateAlpha() {
/* 241 */     if (getState() == 1 && isEnabled()) {
/* 242 */       setAlpha(1.0F);
/*     */     } else {
/* 244 */       float alpha = 0.5F;
/* 245 */       if (isEnabled()) {
/* 246 */         alpha += 0.5F * FastMath.sin(3.1415927F * (float)(System.currentTimeMillis() % 1000L) / 1000.0F);
/*     */       }
/* 248 */       setAlpha(alpha);
/*     */       
/* 250 */       this.imageButton.setAlpha(1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BComponent getHitComponent(int mx, int my) {
/* 256 */     if (isVisible() && mx >= this._x && my >= this._y && mx < this._x + this._width && my < this._y + this._height)
/*     */     {
/* 258 */       return (BComponent)this;
/*     */     }
/* 260 */     return null;
/*     */   }
/*     */   
/*     */   public HudInfoModel getModel() {
/* 264 */     return this.model;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\giftbox\SimpleInfoComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */