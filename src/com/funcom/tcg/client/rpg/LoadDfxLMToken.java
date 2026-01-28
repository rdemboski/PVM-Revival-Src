/*    */ package com.funcom.tcg.client.rpg;
/*    */ 
/*    */ import com.funcom.commons.dfx.DireEffectDescription;
/*    */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*    */ import com.funcom.commons.dfx.NoSuchDFXException;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.rpgengine2.items.ItemDescription;
/*    */ import com.funcom.rpgengine2.loader.ConfigErrors;
/*    */ import java.util.concurrent.Callable;
/*    */ import java.util.concurrent.Future;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LoadDfxLMToken
/*    */   extends LoadingManagerToken
/*    */ {
/*    */   private DireEffectDescriptionFactory dfxDescriptionFactory;
/*    */   private final ConfigErrors configErrors;
/*    */   private String dFXScript;
/*    */   private ItemDescription itemDescription;
/*    */   private boolean impact;
/* 23 */   private Future LoadDfxFuture = null;
/*    */   
/*    */   public LoadDfxLMToken(String dFXScript, ItemDescription itemDescription, boolean impact, DireEffectDescriptionFactory dfxDescriptionFactory, ConfigErrors configErrors) {
/* 26 */     this.dFXScript = dFXScript;
/* 27 */     this.itemDescription = itemDescription;
/* 28 */     this.impact = impact;
/* 29 */     this.dfxDescriptionFactory = dfxDescriptionFactory;
/* 30 */     this.configErrors = configErrors;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean processRequestAssets() throws Exception {
/* 35 */     Callable<DireEffectDescription> callable = new LoadDFXCallable(this.dFXScript, this.itemDescription, this.impact);
/* 36 */     this.LoadDfxFuture = (Future)LoadingManager.INSTANCE.submitCallable(callable);
/*    */     
/* 38 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean processWaitingAssets() throws Exception {
/* 43 */     return (this.LoadDfxFuture == null || this.LoadDfxFuture.isDone());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean processGame() throws Exception {
/*    */     DireEffectDescription dfx;
/* 49 */     if (this.LoadDfxFuture != null && !this.LoadDfxFuture.isCancelled()) {
/* 50 */       dfx = (DireEffectDescription)this.LoadDfxFuture.get();
/*    */     } else {
/* 52 */       Callable<DireEffectDescription> callable = new LoadDFXCallable(this.dFXScript, this.itemDescription, this.impact);
/* 53 */       dfx = callable.call();
/*    */     } 
/* 55 */     this.LoadDfxFuture = null;
/* 56 */     if (dfx != null) {
/* 57 */       if (this.impact) {
/* 58 */         this.itemDescription.setImpactDfxDescription(dfx);
/*    */       } else {
/* 60 */         this.itemDescription.setDfxDescription(dfx);
/*    */       } 
/*    */     }
/*    */     
/* 64 */     return true;
/*    */   }
/*    */   
/*    */   private class LoadDFXCallable implements Callable<DireEffectDescription> {
/*    */     private String dFXScript;
/*    */     private ItemDescription itemDescription;
/*    */     private boolean impact;
/*    */     
/*    */     private LoadDFXCallable(String dFXScript, ItemDescription itemDescription, boolean impact) {
/* 73 */       this.dFXScript = dFXScript;
/* 74 */       this.itemDescription = itemDescription;
/* 75 */       this.impact = impact;
/*    */     }
/*    */ 
/*    */     
/*    */     public DireEffectDescription call() throws Exception {
/*    */       DireEffectDescription dfxDescription;
/*    */       try {
/* 82 */         if (this.impact && this.dFXScript.isEmpty())
/* 83 */         { dfxDescription = DireEffectDescriptionFactory.EMPTY_DFX; }
/*    */         else
/* 85 */         { dfxDescription = LoadDfxLMToken.this.dfxDescriptionFactory.getDireEffectDescription(this.dFXScript, this.impact); } 
/* 86 */       } catch (NoSuchDFXException e) {
/* 87 */         LoadDfxLMToken.this.configErrors.addError("Missing DFX", "itemId=" + this.itemDescription.getId() + " dfxScript=" + e.getDFXScript());
/* 88 */         if (this.impact) {
/* 89 */           dfxDescription = DireEffectDescriptionFactory.EMPTY_DFX;
/*    */         } else {
/* 91 */           dfxDescription = LoadDfxLMToken.this.dfxDescriptionFactory.getDireEffectDescription("", false);
/*    */         } 
/* 93 */       }  return dfxDescription;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\rpg\LoadDfxLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */