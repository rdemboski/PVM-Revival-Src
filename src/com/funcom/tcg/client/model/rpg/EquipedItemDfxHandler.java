/*    */ package com.funcom.tcg.client.model.rpg;
/*    */ 
/*    */ import com.funcom.commons.dfx.DireEffect;
/*    */ import com.funcom.commons.dfx.DireEffectDescription;
/*    */ import com.funcom.commons.dfx.NoSuchDFXException;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.rpgengine2.combat.UsageParams;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class EquipedItemDfxHandler
/*    */   implements EquipChangeListener {
/*    */   private PropNode playerNode;
/* 15 */   private Map<Integer, DireEffect> effects = new HashMap<Integer, DireEffect>();
/*    */   
/*    */   public EquipedItemDfxHandler(PropNode playerNode) {
/* 18 */     this.playerNode = playerNode;
/*    */   }
/*    */ 
/*    */   
/*    */   public void itemEquipped(ClientEquipDoll clientEquipDoll, int placementId, ClientItem newItem, ClientItem oldItem) {
/* 23 */     DireEffect currentEffect = this.effects.get(Integer.valueOf(placementId));
/* 24 */     if (currentEffect != null) {
/* 25 */       currentEffect.forceFinish();
/* 26 */       this.effects.put(Integer.valueOf(placementId), null);
/*    */     } 
/*    */     
/* 29 */     if (newItem != null) {
/* 30 */       ClientItemVisual itemVisual = TcgGame.getVisualRegistry().getItemVisualForClassID(newItem.getVisualId());
/* 31 */       if (itemVisual == null)
/*    */         return; 
/*    */       try {
/* 34 */         if (itemVisual.getAlwaysOnDfxPath().isEmpty())
/*    */           return; 
/* 36 */         DireEffectDescription alwaysOnDfxDescription = this.playerNode.getEffectDescriptionFactory().getDireEffectDescription(itemVisual.getAlwaysOnDfxPath(), false);
/* 37 */         if (!alwaysOnDfxDescription.isEmpty()) {
/* 38 */           DireEffect dfx = alwaysOnDfxDescription.createInstance(this.playerNode, UsageParams.EMPTY_PARAMS);
/* 39 */           this.playerNode.addDfx(dfx);
/* 40 */           this.effects.put(Integer.valueOf(placementId), dfx);
/*    */         } 
/* 42 */       } catch (NoSuchDFXException e) {}
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void itemUnequipped(ClientEquipDoll clientEquipDoll, int placementId, ClientItem oldItem) {
/* 50 */     DireEffect currentEffect = this.effects.get(Integer.valueOf(placementId));
/* 51 */     if (currentEffect != null)
/* 52 */       currentEffect.forceFinish(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\rpg\EquipedItemDfxHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */