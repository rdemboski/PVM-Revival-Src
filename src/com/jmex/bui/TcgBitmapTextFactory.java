/*     */ package com.jmex.bui;
/*     */ 
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.Text;
/*     */ import com.jme.scene.state.BlendState;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.enumeratedConstants.TextEffect;
/*     */ import com.jmex.bui.text.BText;
/*     */ import com.jmex.bui.text.BTextFactory;
/*     */ import com.jmex.bui.util.Dimension;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class TcgBitmapTextFactory extends BTextFactory {
/*  21 */   private float scale = 1.0F; private float endWidth;
/*     */   protected float _width;
/*     */   
/*     */   public TcgBitmapTextFactory(Texture font, int size) {
/*  25 */     if (font == null) {
/*  26 */       throw new IllegalArgumentException("font = null");
/*     */     }
/*  28 */     this._tstate = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/*  29 */     this._tstate.setEnabled(true);
/*  30 */     this._tstate.setTexture(font);
/*     */     
/*  32 */     this.scale = size / 16.0F;
/*     */     
/*  34 */     this._width = 10.0F * this.scale;
/*  35 */     this._height = 16.0F * this.scale;
/*  36 */     this.endWidth = 6.0F * this.scale;
/*     */ 
/*     */ 
/*     */     
/*  40 */     this._astate = DisplaySystem.getDisplaySystem().getRenderer().createBlendState();
/*  41 */     this._astate.setBlendEnabled(true);
/*  42 */     this._astate.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
/*     */     
/*  44 */     this._astate.setEnabled(true);
/*     */   }
/*     */   protected float _height; protected TextureState _tstate; protected BlendState _astate;
/*     */   
/*     */   public int getHeight() {
/*  49 */     return (int)this._height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BText createText(final String text, final ColorRGBA color, TextEffect effect, int effectSize, ColorRGBA effectColor, boolean useAdvance) {
/*  61 */     final Dimension dims = new Dimension((int)(text.length() * this._width + this.endWidth), (int)this._height);
/*     */ 
/*     */ 
/*     */     
/*  65 */     final Text tgeom = new Text("text", text);
/*  66 */     tgeom.setTextureCombineMode(Spatial.TextureCombineMode.Replace);
/*  67 */     tgeom.setRenderState((RenderState)this._tstate);
/*  68 */     tgeom.setRenderState((RenderState)this._astate);
/*  69 */     tgeom.setTextColor(new ColorRGBA(color));
/*  70 */     tgeom.updateRenderState();
/*  71 */     tgeom.getLocalScale().set(this.scale, this.scale, this.scale);
/*  72 */     tgeom.updateWorldVectors();
/*     */ 
/*     */     
/*  75 */     return new BText() {
/*     */         public int getLength() {
/*  77 */           return text.length();
/*     */         }
/*     */         
/*     */         public Dimension getSize() {
/*  81 */           return dims;
/*     */         }
/*     */ 
/*     */         
/*     */         public int getHitPos(int x, int y) {
/*  86 */           int index = (int)((x - TcgBitmapTextFactory.this.endWidth) / TcgBitmapTextFactory.this._width);
/*  87 */           if (index < 0) {
/*  88 */             index = 0;
/*     */           }
/*  90 */           return index;
/*     */         }
/*     */ 
/*     */         
/*     */         public int getCursorPos(int index) {
/*  95 */           return (int)(TcgBitmapTextFactory.this._width * index);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void wasAdded() {}
/*     */ 
/*     */ 
/*     */         
/*     */         public void wasRemoved() {}
/*     */ 
/*     */ 
/*     */         
/*     */         public void render(Renderer renderer, int x, int y, float alpha) {
/* 109 */           tgeom.setLocalTranslation(new Vector3f(x, y, 0.0F));
/*     */           
/* 111 */           (tgeom.getTextColor()).a = alpha * color.a;
/* 112 */           renderer.draw(tgeom);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void release() {}
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BText[] wrapText(String text, ColorRGBA color, TextEffect effect, int effectSize, ColorRGBA effectColor, int maxWidth) {
/* 132 */     String[] lines = text.split("(\\n\\r|\\n|\\r)");
/* 133 */     ArrayList<BText> bTextLines = new ArrayList<BText>();
/*     */     
/* 135 */     for (int t = 0; t < lines.length; t++) {
/* 136 */       String line = lines[t];
/* 137 */       int maxChars = (int)(maxWidth / this._width);
/*     */ 
/*     */       
/* 140 */       int textLen = line.length();
/* 141 */       if (textLen <= maxChars) {
/*     */         
/* 143 */         if (lines.length == 1) {
/* 144 */           return new BText[] { createText(line, color) };
/*     */         }
/* 146 */         bTextLines.add(createText(line, color));
/*     */       } else {
/* 148 */         wrapSimpleFixedLengthMultiLine(bTextLines, line, color, maxChars, textLen);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 154 */     return bTextLines.<BText>toArray(new BText[bTextLines.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void wrapSimpleFixedLengthMultiLine(List<BText> targetList, String text, ColorRGBA color, int maxChars, int textLen) {
/* 166 */     int length = (textLen + maxChars - 1) / maxChars;
/* 167 */     for (int i = 0; i < length; i++) {
/* 168 */       int endIndex = (i + 1) * maxChars;
/* 169 */       if (endIndex > textLen) {
/* 170 */         endIndex = textLen;
/*     */       }
/* 172 */       targetList.add(createText(text.substring(i * maxChars, endIndex), color));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\TcgBitmapTextFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */