/*     */ package com.funcom.gameengine.resourcemanager.loadingmanager;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.model.chunks.ChunkNode;
/*     */ import com.funcom.gameengine.model.chunks.ChunkWorldNode;
/*     */ import com.funcom.gameengine.model.factories.LoadException;
/*     */ import com.funcom.gameengine.model.factories.XmlMk6Tags;
/*     */ import com.funcom.gameengine.model.token.ChunkFetcherStrategy;
/*     */ import com.funcom.gameengine.model.token.ChunkLoaderToken;
/*     */ import com.funcom.gameengine.model.token.TokenTargetNode;
/*     */ import com.funcom.gameengine.spatial.LineNode;
/*     */ import com.jme.scene.state.BlendState;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Point;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Future;
/*     */ import org.jdom.DataConversionException;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkLMToken
/*     */   extends LoadingManagerToken
/*     */   implements XmlMk6Tags
/*     */ {
/*  34 */   private ChunkLoaderToken chunkLoaderToken = null;
/*  35 */   private WorldCoordinate worldCoordinate = null;
/*     */   private ChunkWorldNode chunkWorldNode;
/*     */   private ChunkFetcherStrategy chunkFetcherStrategy;
/*  38 */   private Future<Document> LoadDocumentFuture = null;
/*  39 */   private ChunkNode chunkNode = null;
/*     */ 
/*     */   
/*     */   public ChunkLMToken(ChunkLoaderToken chunkLoaderToken, WorldCoordinate worldCoordinate, ChunkWorldNode chunkWorldNode, ChunkFetcherStrategy chunkFetcherStrategy) {
/*  43 */     this.chunkLoaderToken = chunkLoaderToken;
/*  44 */     this.worldCoordinate = worldCoordinate;
/*  45 */     this.chunkWorldNode = chunkWorldNode;
/*  46 */     this.chunkFetcherStrategy = chunkFetcherStrategy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processRequestAssets() throws Exception {
/*  51 */     this.chunkNode = this.chunkFetcherStrategy.createChunkNode(this.worldCoordinate);
/*  52 */     this.chunkNode.setChunkWorldNode(this.chunkWorldNode);
/*     */     
/*  54 */     BlendState as = DisplaySystem.getDisplaySystem().getRenderer().createBlendState();
/*  55 */     as.setEnabled(true);
/*  56 */     as.setBlendEnabled(true);
/*  57 */     this.chunkNode.setRenderState((RenderState)as);
/*     */     
/*  59 */     Callable<Document> callable = new LoadDocumentCallable(this.chunkNode, this.chunkFetcherStrategy, this.chunkWorldNode);
/*  60 */     this.LoadDocumentFuture = (Future)LoadingManager.INSTANCE.submitCallable(callable);
/*     */     
/*  62 */     return true;
/*     */   }
/*     */   
/*     */   public boolean processWaitingAssets() throws Exception {
/*  66 */     return (this.LoadDocumentFuture == null || this.LoadDocumentFuture.isDone());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processGame() throws Exception {
/*  72 */     if (this.LoadDocumentFuture != null && !this.LoadDocumentFuture.isCancelled()) {
/*  73 */       this.chunkWorldNode.addChunk(this.chunkNode);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/*  79 */       LoadDocumentCallable c = new LoadDocumentCallable(this.chunkNode, this.chunkFetcherStrategy, this.chunkWorldNode);
/*  80 */       c.call();
/*  81 */       this.chunkWorldNode.addChunk(this.chunkNode);
/*     */     } 
/*     */ 
/*     */     
/*  85 */     this.LoadDocumentFuture = null;
/*     */     
/*  87 */     return true;
/*     */   }
/*     */   
/*     */   private Dimension getMapSize(Element root) {
/*  91 */     Element infoElement = root.getChild("info");
/*  92 */     Element chunkSizeElement = infoElement.getChild("chunk-size");
/*     */     try {
/*  94 */       return new Dimension(chunkSizeElement.getAttribute("width").getIntValue(), chunkSizeElement.getAttribute("height").getIntValue());
/*     */     }
/*  96 */     catch (DataConversionException e) {
/*  97 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public class LoadDocumentCallable implements Callable {
/*     */     ChunkFetcherStrategy chunkFetcherStrategy;
/* 103 */     ChunkNode chunkNode = null;
/*     */     ChunkWorldNode chunkWorldNode;
/*     */     
/*     */     public LoadDocumentCallable(ChunkNode chunkNode, ChunkFetcherStrategy chunkFetcherStrategy, ChunkWorldNode chunkWorldNode) {
/* 107 */       this.chunkNode = chunkNode;
/* 108 */       this.chunkFetcherStrategy = chunkFetcherStrategy;
/* 109 */       this.chunkWorldNode = chunkWorldNode;
/*     */     }
/*     */ 
/*     */     
/*     */     public Document call() {
/* 114 */       Document document = this.chunkFetcherStrategy.getBinaryChunkDocument(this.chunkWorldNode.getChunkWorldInfo(), this.chunkNode.getChunkNumber());
/*     */ 
/*     */       
/* 117 */       Element root = document.getRootElement();
/*     */       
/* 119 */       Dimension size = ChunkLMToken.this.getMapSize(root);
/* 120 */       WorldCoordinate extent = (new WorldCoordinate(ChunkLMToken.this.worldCoordinate)).addTiles(size.width, size.height);
/* 121 */       LineNode lineRoot = new LineNode(ChunkLMToken.this.worldCoordinate, extent);
/* 122 */       this.chunkNode.setLineRoot(lineRoot);
/* 123 */       this.chunkNode.setSize(size.width, size.height);
/*     */       
/*     */       try {
/* 126 */         String mapId = this.chunkNode.getChunkWorldNode().getChunkWorldInfo().getMapId();
/*     */         
/* 128 */         List<Element> tileElements = root.getChildren("tile");
/* 129 */         if (tileElements.size() > 0) {
/*     */           
/*     */           try {
/*     */ 
/*     */ 
/*     */             
/* 135 */             for (Element tileElement : tileElements) {
/* 136 */               processTileElement(tileElement, (TokenTargetNode)this.chunkNode, ChunkLMToken.this.worldCoordinate.getTileCoord());
/*     */             }
/*     */           }
/* 139 */           catch (Exception e) {
/* 140 */             System.out.printf("ChunkLMToken - call: error while loading tile element (%s).\n", new Object[] { e.toString() });
/*     */           } 
/*     */         }
/*     */         
/* 144 */         List<Element> staticElements = root.getChildren("static-object");
/* 145 */         for (Element staticElement : staticElements) {
/* 146 */           ChunkLMToken.this.chunkLoaderToken.processStaticObjectElement(staticElement, mapId, (TokenTargetNode)this.chunkNode, ChunkLMToken.this.worldCoordinate.getTileCoord());
/*     */         }
/*     */         
/* 149 */         List<Element> interactiveElements = root.getChildren("interactible-object");
/* 150 */         for (Element interactiveElement : interactiveElements) {
/* 151 */           ChunkLMToken.this.chunkLoaderToken.processInteractibleObjectElement(interactiveElement, mapId, (TokenTargetNode)this.chunkNode, ChunkLMToken.this.worldCoordinate.getTileCoord());
/*     */         }
/*     */         
/* 154 */         List<Element> meshElements = root.getChildren("mesh-object");
/* 155 */         for (Element meshElement : meshElements) {
/* 156 */           ChunkLMToken.this.chunkLoaderToken.processMeshObjectElement(meshElement, mapId, (TokenTargetNode)this.chunkNode, ChunkLMToken.this.worldCoordinate.getTileCoord());
/*     */         }
/*     */         
/*     */         try {
/* 160 */           List<Element> mergedMeshElements = root.getChildren("merged-mesh-object");
/* 161 */           int nMaxElement = mergedMeshElements.size();
/* 162 */           for (int nElement = 0; nElement < nMaxElement; nElement++) {
/* 163 */             Element mergedMeshElement = mergedMeshElements.get(nElement);
/* 164 */             ArrayList<Element> arr = new ArrayList<Element>();
/* 165 */             int nNumSiblings = mergedMeshElement.getAttribute("siblings").getIntValue();
/* 166 */             for (int nSibling = 0; nSibling < nNumSiblings && nElement < nMaxElement; nSibling++, nElement++) {
/* 167 */               arr.add(mergedMeshElements.get(nElement));
/*     */             }
/* 169 */             ChunkLMToken.this.chunkLoaderToken.processMergedMeshObjectElement(arr, mapId, (TokenTargetNode)this.chunkNode, ChunkLMToken.this.worldCoordinate.getTileCoord());
/*     */           } 
/* 171 */         } catch (DataConversionException ee) {
/* 172 */           throw new LoadException(ee);
/*     */         } 
/*     */         
/* 175 */         List<Element> collisionNodeElements = root.getChildren("collision-node");
/* 176 */         for (Element collisionElement : collisionNodeElements) {
/* 177 */           ChunkLMToken.this.chunkLoaderToken.processCollisionNodeElement(collisionElement, mapId, (TokenTargetNode)this.chunkNode, ChunkLMToken.this.worldCoordinate.getTileCoord());
/*     */         }
/*     */         
/* 180 */         List<Element> collisionElements = root.getChildren("collision-line");
/* 181 */         for (Element collisionElement : collisionElements) {
/* 182 */           ChunkLMToken.this.chunkLoaderToken.processCollisionLineElement(collisionElement, (TokenTargetNode)this.chunkNode, lineRoot, ChunkLMToken.this.worldCoordinate.getTileCoord());
/*     */         }
/*     */         
/* 185 */         List<Element> waterLineElements = root.getChildren("water-line");
/* 186 */         for (Element waterLineElement : waterLineElements) {
/* 187 */           ChunkLMToken.this.chunkLoaderToken.processWaterLineElement(waterLineElement, (TokenTargetNode)this.chunkNode, ChunkLMToken.this.worldCoordinate.getTileCoord());
/*     */         }
/*     */         
/* 190 */         List<Element> waterPondElements = root.getChildren("water-pond");
/* 191 */         for (Element waterPondElement : waterPondElements) {
/* 192 */           ChunkLMToken.this.chunkLoaderToken.processWaterPondElement(waterPondElement, (TokenTargetNode)this.chunkNode, ChunkLMToken.this.worldCoordinate.getTileCoord());
/*     */         }
/*     */         
/* 195 */         List<Element> decalElements = root.getChildren("decal");
/* 196 */         for (Element decalElement : decalElements) {
/* 197 */           ChunkLMToken.this.chunkLoaderToken.processDecalElement(decalElement, mapId, (TokenTargetNode)this.chunkNode, ChunkLMToken.this.worldCoordinate.getTileCoord());
/*     */         }
/*     */         
/* 200 */         List<Element> areaElements = root.getChildren("area");
/* 201 */         for (Element areaElement : areaElements) {
/* 202 */           ChunkLMToken.this.chunkLoaderToken.processAreaElement(areaElement, (TokenTargetNode)this.chunkNode, ChunkLMToken.this.worldCoordinate.getTileCoord());
/*     */         }
/*     */         
/* 205 */         List<Element> spawnElements = root.getChildren("spawn-point");
/* 206 */         for (Element spawnElement : spawnElements) {
/* 207 */           ChunkLMToken.this.chunkLoaderToken.processSpawnPointElement(spawnElement, mapId, (TokenTargetNode)this.chunkNode, ChunkLMToken.this.worldCoordinate.getTileCoord());
/*     */         }
/*     */         
/* 210 */         List<Element> vendorElements = root.getChildren("vendor");
/* 211 */         for (Element vendorElement : vendorElements) {
/* 212 */           ChunkLMToken.this.chunkLoaderToken.processVendorElement(vendorElement, mapId, (TokenTargetNode)this.chunkNode, ChunkLMToken.this.worldCoordinate.getTileCoord());
/*     */         }
/*     */         
/* 215 */         List<Element> patrolElements = root.getChildren("patrol");
/* 216 */         for (Element patrolElement : patrolElements) {
/* 217 */           ChunkLMToken.this.chunkLoaderToken.processPatrolElement(patrolElement, mapId, (TokenTargetNode)this.chunkNode, lineRoot, ChunkLMToken.this.worldCoordinate.getTileCoord());
/*     */         }
/*     */         
/* 220 */         List<Element> particleElements = root.getChildren("particles");
/* 221 */         for (Element particleElement : particleElements) {
/* 222 */           ChunkLMToken.this.chunkLoaderToken.processParticleElement(particleElement, mapId, (TokenTargetNode)this.chunkNode, ChunkLMToken.this.worldCoordinate.getTileCoord());
/*     */         }
/*     */         
/* 225 */         List<Element> checkpointElements = root.getChildren("check_point");
/* 226 */         for (Element checkpointElement : checkpointElements) {
/* 227 */           ChunkLMToken.this.chunkLoaderToken.processCheckPoints(checkpointElement, this.chunkNode, ChunkLMToken.this.worldCoordinate.getTileCoord());
/*     */         }
/*     */         
/* 230 */         List<Element> questGoToPropElements = root.getChildren("quest-goto-prop");
/* 231 */         for (Element element : questGoToPropElements) {
/* 232 */           ChunkLMToken.this.chunkLoaderToken.processQuestGoToPropElements(element, this.chunkNode, ChunkLMToken.this.worldCoordinate.getTileCoord());
/*     */         }
/*     */       }
/* 235 */       catch (LoadException e) {
/* 236 */         throw new RuntimeException(e);
/*     */       } 
/*     */       
/* 239 */       return document;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void processTileElement(Element tileElement, TokenTargetNode tokenTargetNode, Point tileCoord) throws Exception {
/*     */       int x, y;
/*     */       try {
/* 247 */         x = tileElement.getAttribute("x").getIntValue();
/* 248 */         y = tileElement.getAttribute("y").getIntValue();
/* 249 */       } catch (DataConversionException e) {
/* 250 */         throw new RuntimeException(e);
/*     */       } 
/*     */       
/* 253 */       Element layersElement = tileElement.getChild("layers");
/* 254 */       if (layersElement != null) {
/* 255 */         String backgroundResourceName = layersElement.getAttributeValue("background");
/* 256 */         String layer2 = layersElement.getAttributeValue("layer2");
/* 257 */         String layer3 = layersElement.getAttributeValue("layer3");
/* 258 */         String layer4 = layersElement.getAttributeValue("layer4");
/*     */ 
/*     */         
/* 261 */         if (!backgroundResourceName.isEmpty() || !layer2.isEmpty() || !layer3.isEmpty() || !layer4.isEmpty())
/*     */         {
/* 263 */           if (backgroundResourceName.lastIndexOf("transparent_base.png") == -1 || !layer2.isEmpty() || !layer3.isEmpty() || !layer4.isEmpty()) {
/* 264 */             LoadingManager.INSTANCE.submitByDistance(new CreateClientLayeredTextureTileLMToken(x, y, backgroundResourceName, layer2, layer3, layer4, tokenTargetNode, tileCoord, this.chunkFetcherStrategy.resourceGetter, -1), tileCoord.x + x, tileCoord.y + y, tokenTargetNode);
/*     */           
/*     */           }
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 271 */         int id = Integer.parseInt(tileElement.getAttributeValue("reftileid"));
/* 272 */         LoadingManager.INSTANCE.submitByDistance(new CreateClientLayeredTextureTileLMToken(x, y, "", "", "", "", tokenTargetNode, tileCoord, this.chunkFetcherStrategy.resourceGetter, id), tileCoord.x + x, tileCoord.y + y, tokenTargetNode);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\ChunkLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */