/*     */ package com.funcom.gameengine.model.token;
/*     */ 
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.view.TransparentAlphaState;
/*     */ import com.funcom.gameengine.view.water.SinusShoreController;
/*     */ import com.funcom.gameengine.view.water.WaterFactory;
/*     */ import com.funcom.gameengine.view.water.WaterLine;
/*     */ import com.funcom.gameengine.view.water.WaterLineCoordinateSet;
/*     */ import com.funcom.gameengine.view.water.WaterMesh;
/*     */ import com.funcom.gameengine.view.water.WaterOffsetController;
/*     */ import com.jme.bounding.BoundingBox;
/*     */ import com.jme.bounding.BoundingVolume;
/*     */ import com.jme.scene.Controller;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import java.awt.Point;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CreateWaterLineToken
/*     */   extends CreateWaterToken
/*     */ {
/*     */   protected WaterLine waterLine;
/*     */   private float height;
/*     */   private float transparency;
/*     */   private int shoreType;
/*     */   private int textureDistribution;
/*     */   private float baseTextureScale;
/*     */   private float overlayTextureScale;
/*     */   private float shoreTextureScale;
/*     */   private float shoreTextureOffset;
/*     */   private List<WaterLineCoordinateSet> waterLines;
/*     */   private float baseSpeedX;
/*     */   private float baseSpeedY;
/*     */   private float overlaySpeedX;
/*     */   private float overlaySpeedY;
/*     */   private float shoreSpeed;
/*     */   private String baseTexture;
/*     */   private String overlayTexture;
/*     */   private String shoreTexture;
/*     */   protected TokenTargetNode tokenTargetNode;
/*     */   private Point tileCoord;
/*     */   
/*     */   public CreateWaterLineToken(float height, float transparency, int shoreType, int textureDistribution, float baseTextureScale, float overlayTextureScale, float shoreTextureScale, float shoreTextureOffset, List<WaterLineCoordinateSet> waterLines, float baseSpeedX, float baseSpeedY, float overlaySpeedX, float overlaySpeedY, float shoreSpeed, String baseTexture, String overlayTexture, String shoreTexture, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/*  47 */     super(resourceGetter);
/*     */     
/*  49 */     this.height = height;
/*  50 */     this.transparency = transparency;
/*  51 */     this.shoreType = shoreType;
/*  52 */     this.textureDistribution = textureDistribution;
/*  53 */     this.baseTextureScale = baseTextureScale;
/*  54 */     this.overlayTextureScale = overlayTextureScale;
/*  55 */     this.shoreTextureScale = shoreTextureScale;
/*  56 */     this.shoreTextureOffset = shoreTextureOffset;
/*  57 */     this.waterLines = waterLines;
/*  58 */     this.baseSpeedX = baseSpeedX;
/*  59 */     this.baseSpeedY = baseSpeedY;
/*  60 */     this.overlaySpeedX = overlaySpeedX;
/*  61 */     this.overlaySpeedY = overlaySpeedY;
/*  62 */     this.shoreSpeed = shoreSpeed;
/*  63 */     this.baseTexture = baseTexture;
/*  64 */     this.overlayTexture = overlayTexture;
/*  65 */     this.shoreTexture = shoreTexture;
/*  66 */     this.tokenTargetNode = tokenTargetNode;
/*  67 */     this.tileCoord = tileCoord;
/*     */   }
/*     */   
/*     */   public Token.TokenType getTokenType() {
/*  71 */     return Token.TokenType.GAME_THREAD;
/*     */   }
/*     */   
/*     */   protected WaterLine createWaterLine() {
/*  75 */     TextureState textureState = WaterFactory.createTextureState(this.resourceGetter, this.baseTexture, this.overlayTexture, this.shoreTexture);
/*  76 */     WaterLine waterLine = new WaterLine(this.height, this.shoreType, this.textureDistribution, this.baseTextureScale, 0.0F, 0.0F, this.overlayTextureScale, 0.0F, 0.0F, this.shoreTextureScale, this.shoreTextureOffset, this.waterLines);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     waterLine.setRenderState((RenderState)textureState);
/*  83 */     waterLine.setRenderState((RenderState)TransparentAlphaState.get());
/*  84 */     waterLine.setRenderQueueMode(3);
/*     */     
/*  86 */     waterLine.setModelBound((BoundingVolume)new BoundingBox());
/*  87 */     waterLine.updateModelBound();
/*     */     
/*  89 */     WaterFactory.assignWaterShaders((Spatial)waterLine, this.resourceGetter);
/*  90 */     WaterFactory.setWaterTextureTransparency((Spatial)waterLine, this.transparency);
/*     */     
/*  92 */     waterLine.addController((Controller)new SinusShoreController((WaterMesh)waterLine, this.shoreSpeed));
/*  93 */     waterLine.addController((Controller)new WaterOffsetController((WaterMesh)waterLine, this.baseSpeedX, this.baseSpeedY, this.overlaySpeedX, this.overlaySpeedY));
/*     */     
/*  95 */     waterLine.updateWorldVectors();
/*     */     
/*  97 */     return waterLine;
/*     */   }
/*     */   
/*     */   public void process() {
/* 101 */     this.waterLine = createWaterLine();
/* 102 */     this.tokenTargetNode.attachAnimatedChild((Spatial)this.waterLine);
/* 103 */     this.tokenTargetNode.updateRenderState();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\CreateWaterLineToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */