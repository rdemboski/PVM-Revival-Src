/*    */ package com.funcom.tcg.client.ui.hud2;
/*    */ 
/*    */ import com.funcom.gameengine.Updated;
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.BuiUtils;
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BWindow;
/*    */ import com.jmex.bui.layout.AbsoluteLayout;
/*    */ import com.jmex.bui.layout.BLayoutManager;
/*    */ import com.jmex.bui.util.Rectangle;
/*    */ 
/*    */ public class ResourceDownloadWindow
/*    */   extends BWindow implements Updated {
/*    */   private ResourceDownloadComponent comp;
/*    */   
/*    */   public ResourceDownloadWindow(ResourceManager resourceManager) {
/* 19 */     super(null, (BLayoutManager)new AbsoluteLayout());
/*    */     
/* 21 */     this._style = BuiUtils.createMergedClassStyleSheets(ResourceDownloadWindow.class, new BananaResourceProvider(resourceManager));
/*    */     
/* 23 */     this.comp = new ResourceDownloadComponent();
/* 24 */     add((BComponent)this.comp, new Rectangle(0, 0, 88, 24));
/* 25 */     setSize(64, 24);
/*    */   }
/*    */ 
/*    */   
/*    */   public void dismiss() {
/* 30 */     MainGameState.getInstance().removeFromUpdateList(this);
/* 31 */     super.dismiss();
/*    */   }
/*    */ 
/*    */   
/*    */   public void update(float time) {
/* 36 */     this.comp.update(time);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\ResourceDownloadWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */