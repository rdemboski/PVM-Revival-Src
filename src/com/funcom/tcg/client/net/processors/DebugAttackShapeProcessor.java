/*     */ package com.funcom.tcg.client.net.processors;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.gameengine.view.RepresentationalNode;
/*     */ import com.funcom.server.common.GameIOHandler;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.net.CreatureData;
/*     */ import com.funcom.tcg.client.net.MessageProcessor;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.net.message.DebugAttackShapeMessage;
/*     */ import com.jme.bounding.BoundingBox;
/*     */ import com.jme.bounding.BoundingVolume;
/*     */ import com.jme.curve.CatmullRomCurve;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.scene.Controller;
/*     */ import com.jme.scene.Line;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.shape.Disk;
/*     */ import com.jme.util.geom.BufferUtils;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DebugAttackShapeProcessor
/*     */   implements MessageProcessor
/*     */ {
/*  32 */   private static final Vector3f[] lineNormals = new Vector3f[] { Vector3f.UNIT_Y, Vector3f.UNIT_Y };
/*  33 */   private static final FloatBuffer normalBuffer = BufferUtils.createFloatBuffer(lineNormals);
/*  34 */   private ColorRGBA color = ColorRGBA.white;
/*     */ 
/*     */   
/*     */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/*  38 */     DebugAttackShapeMessage debugMessage = (DebugAttackShapeMessage)message;
/*     */     
/*  40 */     double startAngle = debugMessage.getAngleStart();
/*  41 */     double endAngle = debugMessage.getAngleEnd();
/*  42 */     double distance = debugMessage.getDistance();
/*  43 */     double offsetX = debugMessage.getOffsetX();
/*  44 */     double offsetY = debugMessage.getOffsetY();
/*     */ 
/*     */     
/*  47 */     WorldCoordinate playerPos = debugMessage.getPosition();
/*  48 */     WorldCoordinate offset = new WorldCoordinate(0, 0, offsetX, offsetY, MainGameState.getPlayerModel().getPosition().getMapId(), playerPos.getInstanceReference());
/*     */     
/*  50 */     offset.rotate(MainGameState.getPlayerModel().getRotation());
/*     */     
/*  52 */     WorldCoordinate wc = playerPos.clone().add(offset);
/*     */     
/*  54 */     Prop prop = new Prop("Attack shape", wc);
/*  55 */     PropNode propNode = new PropNode(prop, 3, "?", TcgGame.getDireEffectDescriptionFactory());
/*     */     
/*  57 */     propNode.addController(new DeletionController((RepresentationalNode)propNode));
/*     */ 
/*     */     
/*  60 */     if (startAngle != endAngle) {
/*  61 */       attachRepresentation(startAngle, endAngle, distance, (RepresentationalNode)propNode);
/*     */     } else {
/*  63 */       Disk disk = new Disk("disk", 10, 10, (float)distance);
/*  64 */       disk.setSolidColor(this.color);
/*  65 */       disk.rotateUpTo(new Vector3f(0.0F, 0.0F, 1.0F));
/*  66 */       propNode.attachRepresentation((Spatial)disk);
/*     */     } 
/*  68 */     propNode.setModelBound((BoundingVolume)new BoundingBox());
/*  69 */     propNode.updateModelBound();
/*  70 */     propNode.updateRenderState();
/*     */     
/*  72 */     MainGameState.getWorld().addObject((RepresentationalNode)propNode);
/*     */   }
/*     */   private void attachRepresentation(double startAngle, double endAngle, double distance, RepresentationalNode rep) {
/*     */     Vector3f[] vectorList;
/*  76 */     WorldCoordinate playerPos = MainGameState.getPlayerModel().getPosition();
/*  77 */     WorldCoordinate distanceVector = new WorldCoordinate(0, 0, distance, 0.0D, MainGameState.getPlayerModel().getPosition().getMapId(), playerPos.getInstanceReference());
/*     */     
/*  79 */     WorldCoordinate vector1 = distanceVector.clone();
/*  80 */     vector1.rotate(startAngle);
/*  81 */     WorldCoordinate vector2 = distanceVector.clone();
/*  82 */     vector2.rotate(endAngle);
/*     */     
/*  84 */     WorldCoordinate vector3 = distanceVector.clone();
/*  85 */     WorldCoordinate vector4 = distanceVector.clone();
/*     */     
/*  87 */     if (startAngle < endAngle) {
/*  88 */       double partialAngle = (endAngle + 6.2831854820251465D - startAngle + 6.2831854820251465D) / 3.0D;
/*  89 */       vector3.rotate(startAngle + partialAngle);
/*  90 */       vector4.rotate(startAngle + partialAngle * 2.0D);
/*  91 */       vectorList = new Vector3f[] { new Vector3f(vector1.getX().floatValue(), 0.0F, vector1.getY().floatValue()), new Vector3f(vector3.getX().floatValue(), 0.0F, vector3.getY().floatValue()), new Vector3f(vector4.getX().floatValue(), 0.0F, vector4.getY().floatValue()), new Vector3f(vector2.getX().floatValue(), 0.0F, vector2.getY().floatValue()) };
/*     */     
/*     */     }
/*     */     else {
/*     */       
/*  96 */       double partialAngle = (6.2831854820251465D - startAngle + 6.2831854820251465D - endAngle + 6.2831854820251465D) / 4.0D;
/*  97 */       vector3.rotate(startAngle + partialAngle);
/*  98 */       vector4.rotate(startAngle + partialAngle * 2.0D);
/*  99 */       WorldCoordinate vector5 = distanceVector.clone();
/* 100 */       vector5.rotate(startAngle + partialAngle * 3.0D);
/* 101 */       vectorList = new Vector3f[] { new Vector3f(vector1.getX().floatValue(), 0.0F, vector1.getY().floatValue()), new Vector3f(vector3.getX().floatValue(), 0.0F, vector3.getY().floatValue()), new Vector3f(vector4.getX().floatValue(), 0.0F, vector4.getY().floatValue()), new Vector3f(vector5.getX().floatValue(), 0.0F, vector5.getY().floatValue()), new Vector3f(vector2.getX().floatValue(), 0.0F, vector2.getY().floatValue()) };
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     Line line1 = new Line("line1");
/* 109 */     FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(6);
/*     */     
/* 111 */     BufferUtils.setInBuffer(new Vector3f(vector1.getX().floatValue(), 0.0F, vector1.getY().floatValue()), vertexBuffer, 1);
/*     */ 
/*     */ 
/*     */     
/* 115 */     line1.reconstruct(vertexBuffer, normalBuffer, null, null);
/*     */     
/* 117 */     Line line2 = new Line("line2");
/* 118 */     FloatBuffer vertexBuffer2 = BufferUtils.createFloatBuffer(6);
/* 119 */     BufferUtils.setInBuffer(new Vector3f(vector2.getX().floatValue(), 0.0F, vector2.getY().floatValue()), vertexBuffer2, 1);
/*     */ 
/*     */ 
/*     */     
/* 123 */     line2.reconstruct(vertexBuffer2, normalBuffer, null, null);
/*     */ 
/*     */     
/* 126 */     CatmullRomCurve curve = new CatmullRomCurve("Attack shape", vectorList);
/*     */     
/* 128 */     line1.setSolidColor(this.color);
/* 129 */     line2.setSolidColor(this.color);
/* 130 */     curve.setSolidColor(this.color);
/*     */     
/* 132 */     rep.attachRepresentation((Spatial)line1);
/* 133 */     rep.attachRepresentation((Spatial)line2);
/* 134 */     rep.attachRepresentation((Spatial)curve);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getMessageType() {
/* 139 */     return 98;
/*     */   }
/*     */   
/*     */   private class DeletionController extends Controller {
/* 143 */     private float currentTime = 0.0F;
/*     */     private static final float killTime = 5.0F;
/*     */     private RepresentationalNode rep;
/*     */     
/*     */     private DeletionController(RepresentationalNode rep) {
/* 148 */       this.rep = rep;
/*     */     }
/*     */ 
/*     */     
/*     */     public void update(float time) {
/* 153 */       this.currentTime += time;
/* 154 */       if (this.currentTime > 5.0F) {
/* 155 */         killme();
/*     */       }
/*     */     }
/*     */     
/*     */     private void killme() {
/* 160 */       this.rep.removeFromParent();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\DebugAttackShapeProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */