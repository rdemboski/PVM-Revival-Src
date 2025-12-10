/*    */ package com.funcom.gameengine.resourcemanager.loaders.texture;
/*    */ 
/*    */ import com.jme.image.Texture;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextureSetup
/*    */ {
/*    */   private final Map<Object, Object> params;
/*    */   
/*    */   public TextureSetup(Map<Object, Object> params) {
/* 14 */     this.params = params;
/*    */   }
/*    */   
/*    */   public void setup(Texture texture) {
/* 18 */     texture.setMinificationFilter(getMinFilter());
/* 19 */     texture.setMagnificationFilter(getMagFilter());
/* 20 */     texture.setApply(getApplyMode());
/* 21 */     texture.setWrap(getWrapMode());
/*    */   }
/*    */   
/*    */   private Texture.ApplyMode getApplyMode() {
/* 25 */     return get(Texture.ApplyMode.class, Texture.ApplyMode.Modulate);
/*    */   }
/*    */   
/*    */   private Texture.WrapMode getWrapMode() {
/* 29 */     return get(Texture.WrapMode.class, Texture.WrapMode.EdgeClamp);
/*    */   }
/*    */   
/*    */   public Texture.MagnificationFilter getMagFilter() {
/* 33 */     return get(Texture.MagnificationFilter.class, Texture.MagnificationFilter.Bilinear);
/*    */   }
/*    */   
/*    */   public Texture.MinificationFilter getMinFilter() {
/* 37 */     return get(Texture.MinificationFilter.class, Texture.MinificationFilter.BilinearNearestMipMap);
/*    */   }
/*    */   
/*    */   protected <E> E get(Object key, E defaultValue) {
/* 41 */     E ret = (E)this.params.get(key);
/* 42 */     if (ret == null) {
/* 43 */       return defaultValue;
/*    */     }
/* 45 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\texture\TextureSetup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */