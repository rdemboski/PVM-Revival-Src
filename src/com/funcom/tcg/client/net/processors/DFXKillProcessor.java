/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.model.command.Command;
/*    */ import com.funcom.gameengine.model.props.Creature;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.rpgengine2.creatures.RpgCreatureConstants;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.command.ExecuteDFXCommand;
/*    */ import com.funcom.tcg.client.model.PropNodeRegister;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.net.message.DFXKillMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DFXKillProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/*    */     PropNode propNode;
/* 26 */     DFXKillMessage dfxKillMessage = (DFXKillMessage)message;
/*    */ 
/*    */     
/* 29 */     int creatureType = dfxKillMessage.getCreatureType();
/*    */     
/* 31 */     if (creatureType == RpgCreatureConstants.Type.PLAYER_CREATURE_TYPE_ID.getTypeId()) {
/* 32 */       PropNodeRegister propNodeRegister = TcgGame.getPropNodeRegister();
/* 33 */       propNode = propNodeRegister.getPropNode(Integer.valueOf(dfxKillMessage.getCreatureId()));
/*    */     } else {
/* 35 */       PropNodeRegister propNodeRegister = TcgGame.getMonsterRegister();
/* 36 */       propNode = propNodeRegister.getPropNode(Integer.valueOf(dfxKillMessage.getCreatureId()));
/*    */     } 
/*    */ 
/*    */     
/* 40 */     if (propNode != null) {
/* 41 */       boolean killed = false;
/* 42 */       Command currentCommand = ((Creature)propNode.getProp()).getCurrentCommand();
/* 43 */       if (currentCommand instanceof ExecuteDFXCommand)
/*    */       {
/* 45 */         if (((ExecuteDFXCommand)currentCommand).getDireEffectId().equalsIgnoreCase(dfxKillMessage.getDfxScript())) {
/* 46 */           ((ExecuteDFXCommand)currentCommand).forceFinish();
/* 47 */           killed = true;
/*    */         } 
/*    */       }
/*    */       
/* 51 */       if (!killed) {
/* 52 */         propNode.killDfx(dfxKillMessage.getDfxScript());
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 59 */     return 227;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\DFXKillProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */