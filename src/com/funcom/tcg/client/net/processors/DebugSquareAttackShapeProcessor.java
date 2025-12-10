/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.commons.Vector2d;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.props.Prop;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.gameengine.view.RepresentationalNode;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.DebugSquareAttackShapeMessage;
/*    */ import com.jme.bounding.BoundingBox;
/*    */ import com.jme.bounding.BoundingVolume;
/*    */ import com.jme.scene.Controller;
/*    */ import com.jme.scene.Line;
/*    */ import com.jme.scene.Spatial;
/*    */ import com.jme.util.geom.BufferUtils;
/*    */ import java.nio.FloatBuffer;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DebugSquareAttackShapeProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 31 */     DebugSquareAttackShapeMessage debugMessage = (DebugSquareAttackShapeMessage)message;
/*    */ 
/*    */     
/* 34 */     WorldCoordinate playerPos = debugMessage.getPosition();
/*    */     
/* 36 */     Prop prop = new Prop("Attack shape", playerPos);
/* 37 */     PropNode propNode = new PropNode(prop, 3, "?", TcgGame.getDireEffectDescriptionFactory());
/*    */     
/* 39 */     propNode.addController(new DeletionController((RepresentationalNode)propNode));
/*    */     
/* 41 */     FloatBuffer vertices = BufferUtils.createVector3Buffer(4);
/* 42 */     FloatBuffer normals = BufferUtils.createVector3Buffer(4);
/* 43 */     Vector2d[] localCorners = debugMessage.getLocalCorners();
/*    */     
/* 45 */     putCoord(vertices, (float)localCorners[0].getX(), 0.0F, (float)localCorners[0].getY());
/* 46 */     putCoord(vertices, (float)localCorners[1].getX(), 0.0F, (float)localCorners[1].getY());
/* 47 */     putCoord(vertices, (float)localCorners[2].getX(), 0.0F, (float)localCorners[2].getY());
/* 48 */     putCoord(vertices, (float)localCorners[3].getX(), 0.0F, (float)localCorners[3].getY());
/* 49 */     putCoord(normals, 0.0F, 1.0F, 0.0F);
/* 50 */     putCoord(normals, 0.0F, 1.0F, 0.0F);
/* 51 */     putCoord(normals, 0.0F, 1.0F, 0.0F);
/* 52 */     putCoord(normals, 0.0F, 1.0F, 0.0F);
/*    */     
/* 54 */     Line spatial = new Line("TriggerShape");
/* 55 */     spatial.setMode(Line.Mode.Loop);
/* 56 */     spatial.reconstruct(vertices, normals, null, null);
/* 57 */     propNode.attachRepresentation((Spatial)spatial);
/*    */     
/* 59 */     propNode.setModelBound((BoundingVolume)new BoundingBox());
/* 60 */     propNode.updateModelBound();
/* 61 */     propNode.updateRenderState();
/*    */     
/* 63 */     MainGameState.getWorld().addObject((RepresentationalNode)propNode);
/*    */   }
/*    */   
/*    */   private void putCoord(FloatBuffer buffer, float x, float y, float z) {
/* 67 */     buffer.put(x);
/* 68 */     buffer.put(y);
/* 69 */     buffer.put(z);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 74 */     return 243;
/*    */   }
/*    */   
/*    */   private class DeletionController extends Controller {
/* 78 */     private float currentTime = 0.0F;
/*    */     private static final float killTime = 5.0F;
/*    */     private RepresentationalNode rep;
/*    */     
/*    */     private DeletionController(RepresentationalNode rep) {
/* 83 */       this.rep = rep;
/*    */     }
/*    */ 
/*    */     
/*    */     public void update(float time) {
/* 88 */       this.currentTime += time;
/* 89 */       if (this.currentTime > 5.0F) {
/* 90 */         killme();
/*    */       }
/*    */     }
/*    */     
/*    */     private void killme() {
/* 95 */       this.rep.removeFromParent();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\DebugSquareAttackShapeProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */