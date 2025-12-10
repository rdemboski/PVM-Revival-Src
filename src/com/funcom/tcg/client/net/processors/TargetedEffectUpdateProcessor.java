/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.model.props.Prop;
/*    */ import com.funcom.gameengine.view.RepresentationalNode;
/*    */ import com.funcom.gameengine.view.TargetedEffectNode;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.TargetedEffectUpdateMessage;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TargetedEffectUpdateProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 28 */     TargetedEffectUpdateMessage targetedEffectUpdateMessage = (TargetedEffectUpdateMessage)message;
/*    */     
/* 30 */     List<TargetedEffectUpdateMessage.NewTargetedEffectData> targetedEffectDatas = targetedEffectUpdateMessage.getNewDataList();
/* 31 */     List<TargetedEffectNode> propNodeList = TcgGame.getTargetEffectRegister();
/*    */ 
/*    */     
/* 34 */     clearTargetEffects(propNodeList, targetedEffectUpdateMessage);
/*    */ 
/*    */     
/* 37 */     for (TargetedEffectUpdateMessage.NewTargetedEffectData targetedEffectData : targetedEffectDatas) {
/* 38 */       Prop prop = new Prop("Targeted_Effect_" + targetedEffectData.getTargetedEffectId(), targetedEffectData.getPos());
/* 39 */       TargetedEffectNode propNode = new TargetedEffectNode(prop, 3, "", TcgGame.getDireEffectDescriptionFactory(), targetedEffectData.getId());
/* 40 */       propNode.setAngle(targetedEffectData.getAngle());
/* 41 */       propNode.playDfx(targetedEffectData.getTargetedEffectId());
/* 42 */       MainGameState.getWorld().addObject((RepresentationalNode)propNode);
/* 43 */       propNodeList.add(propNode);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void clearTargetEffects(List<TargetedEffectNode> propNodeList, TargetedEffectUpdateMessage targetedEffectUpdateMessage) {
/* 48 */     List<Integer> integerList = targetedEffectUpdateMessage.getRemovedList();
/* 49 */     for (Integer integer : integerList) {
/* 50 */       Iterator<TargetedEffectNode> iterator = propNodeList.iterator();
/* 51 */       while (iterator.hasNext()) {
/* 52 */         TargetedEffectNode node = iterator.next();
/* 53 */         if (node.getId() == integer.intValue()) {
/* 54 */           node.removeFromParent();
/* 55 */           iterator.remove();
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 62 */     return 228;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\TargetedEffectUpdateProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */