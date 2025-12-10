/*     */ package com.funcom.gameengine.model.token;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.model.chunks.ChunkNode;
/*     */ import com.funcom.gameengine.model.chunks.ChunkWorldNode;
/*     */ import com.funcom.gameengine.model.factories.LoadException;
/*     */ import com.funcom.gameengine.spatial.LineNode;
/*     */ import com.jme.scene.state.BlendState;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import java.awt.Dimension;
/*     */ import java.util.List;
/*     */ import org.jdom.DataConversionException;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ 
/*     */ public class NormalChunkLoaderStrategy implements ChunkLoaderStrategy {
/*     */   private ChunkWorldNode chunkWorldNode;
/*     */   private ChunkFetcherStrategy chunkFetcherStrategy;
/*     */   
/*     */   public NormalChunkLoaderStrategy(ChunkWorldNode chunkWorldNode, ChunkFetcherStrategy chunkFetcherStrategy) {
/*  22 */     this.chunkWorldNode = chunkWorldNode;
/*  23 */     this.chunkFetcherStrategy = chunkFetcherStrategy;
/*     */   }
/*     */   
/*     */   public void process(ChunkLoaderToken chunkLoaderToken, WorldCoordinate worldCoordinate) {
/*  27 */     ChunkNode chunkNode = this.chunkFetcherStrategy.createChunkNode(worldCoordinate);
/*  28 */     chunkNode.setChunkWorldNode(this.chunkWorldNode);
/*     */     
/*  30 */     BlendState as = DisplaySystem.getDisplaySystem().getRenderer().createBlendState();
/*  31 */     as.setEnabled(true);
/*  32 */     as.setBlendEnabled(true);
/*  33 */     chunkNode.setRenderState((RenderState)as);
/*     */     
/*  35 */     Document document = this.chunkFetcherStrategy.getBinaryChunkDocument(this.chunkWorldNode.getChunkWorldInfo(), chunkNode.getChunkNumber());
/*     */     
/*  37 */     Element root = document.getRootElement();
/*     */     
/*  39 */     Dimension size = getMapSize(root);
/*  40 */     WorldCoordinate extent = (new WorldCoordinate(worldCoordinate)).addTiles(size.width, size.height);
/*  41 */     LineNode lineRoot = new LineNode(worldCoordinate, extent);
/*  42 */     chunkNode.setLineRoot(lineRoot);
/*  43 */     chunkNode.setSize(size.width, size.height);
/*     */     
/*     */     try {
/*  46 */       String mapId = chunkNode.getChunkWorldNode().getChunkWorldInfo().getMapId();
/*     */       
/*  48 */       long startNanos = System.nanoTime();
/*  49 */       long tempNanos = startNanos;
/*  50 */       List<Element> tileElements = root.getChildren("tile");
/*  51 */       for (Element tileElement : tileElements) {
/*  52 */         chunkLoaderToken.processTileElement(tileElement, (TokenTargetNode)chunkNode, worldCoordinate.getTileCoord());
/*     */       }
/*  54 */       long tileNanos = System.nanoTime() - tempNanos;
/*     */       
/*  56 */       tempNanos = System.nanoTime();
/*  57 */       List<Element> staticElements = root.getChildren("static-object");
/*  58 */       for (Element staticElement : staticElements) {
/*  59 */         chunkLoaderToken.processStaticObjectElement(staticElement, mapId, (TokenTargetNode)chunkNode, worldCoordinate.getTileCoord());
/*     */       }
/*  61 */       long staticNanos = System.nanoTime() - tempNanos;
/*     */       
/*  63 */       List<Element> interactiveElements = root.getChildren("interactible-object");
/*  64 */       for (Element interactiveElement : interactiveElements) {
/*  65 */         chunkLoaderToken.processInteractibleObjectElement(interactiveElement, mapId, (TokenTargetNode)chunkNode, worldCoordinate.getTileCoord());
/*     */       }
/*     */       
/*  68 */       tempNanos = System.nanoTime();
/*  69 */       List<Element> meshElements = root.getChildren("mesh-object");
/*  70 */       for (Element meshElement : meshElements) {
/*  71 */         chunkLoaderToken.processMeshObjectElement(meshElement, mapId, (TokenTargetNode)chunkNode, worldCoordinate.getTileCoord());
/*     */       }
/*  73 */       long meshNanos = System.nanoTime() - tempNanos;
/*     */       
/*  75 */       tempNanos = System.nanoTime();
/*  76 */       List<Element> collisionNodeElements = root.getChildren("collision-node");
/*  77 */       for (Element collisionElement : collisionNodeElements) {
/*  78 */         chunkLoaderToken.processCollisionNodeElement(collisionElement, mapId, (TokenTargetNode)chunkNode, worldCoordinate.getTileCoord());
/*     */       }
/*     */       
/*  81 */       List<Element> collisionElements = root.getChildren("collision-line");
/*  82 */       for (Element collisionElement : collisionElements) {
/*  83 */         chunkLoaderToken.processCollisionLineElement(collisionElement, (TokenTargetNode)chunkNode, lineRoot, worldCoordinate.getTileCoord());
/*     */       }
/*  85 */       long collNanos = System.nanoTime() - tempNanos;
/*     */       
/*  87 */       List<Element> waterLineElements = root.getChildren("water-line");
/*  88 */       for (Element waterLineElement : waterLineElements) {
/*  89 */         chunkLoaderToken.processWaterLineElement(waterLineElement, (TokenTargetNode)chunkNode, worldCoordinate.getTileCoord());
/*     */       }
/*     */       
/*  92 */       List<Element> waterPondElements = root.getChildren("water-pond");
/*  93 */       for (Element waterPondElement : waterPondElements) {
/*  94 */         chunkLoaderToken.processWaterPondElement(waterPondElement, (TokenTargetNode)chunkNode, worldCoordinate.getTileCoord());
/*     */       }
/*     */       
/*  97 */       tempNanos = System.nanoTime();
/*  98 */       List<Element> decalElements = root.getChildren("decal");
/*  99 */       for (Element decalElement : decalElements) {
/* 100 */         chunkLoaderToken.processDecalElement(decalElement, mapId, (TokenTargetNode)chunkNode, worldCoordinate.getTileCoord());
/*     */       }
/* 102 */       long decalNanos = System.nanoTime() - tempNanos;
/*     */       
/* 104 */       List<Element> areaElements = root.getChildren("area");
/* 105 */       for (Element areaElement : areaElements) {
/* 106 */         chunkLoaderToken.processAreaElement(areaElement, (TokenTargetNode)chunkNode, worldCoordinate.getTileCoord());
/*     */       }
/*     */       
/* 109 */       List<Element> spawnElements = root.getChildren("spawn-point");
/* 110 */       for (Element spawnElement : spawnElements) {
/* 111 */         chunkLoaderToken.processSpawnPointElement(spawnElement, mapId, (TokenTargetNode)chunkNode, worldCoordinate.getTileCoord());
/*     */       }
/*     */       
/* 114 */       List<Element> vendorElements = root.getChildren("vendor");
/* 115 */       for (Element vendorElement : vendorElements) {
/* 116 */         chunkLoaderToken.processVendorElement(vendorElement, mapId, (TokenTargetNode)chunkNode, worldCoordinate.getTileCoord());
/*     */       }
/*     */       
/* 119 */       List<Element> patrolElements = root.getChildren("patrol");
/* 120 */       for (Element patrolElement : patrolElements) {
/* 121 */         chunkLoaderToken.processPatrolElement(patrolElement, mapId, (TokenTargetNode)chunkNode, lineRoot, worldCoordinate.getTileCoord());
/*     */       }
/*     */       
/* 124 */       List<Element> particleElements = root.getChildren("particles");
/* 125 */       for (Element particleElement : particleElements) {
/* 126 */         chunkLoaderToken.processParticleElement(particleElement, mapId, (TokenTargetNode)chunkNode, worldCoordinate.getTileCoord());
/*     */       }
/*     */       
/* 129 */       List<Element> checkpointElements = root.getChildren("check_point");
/* 130 */       for (Element checkpointElement : checkpointElements) {
/* 131 */         chunkLoaderToken.processCheckPoints(checkpointElement, chunkNode, worldCoordinate.getTileCoord());
/*     */       }
/*     */       
/* 134 */       tempNanos = System.nanoTime();
/* 135 */       List<Element> questGoToPropElements = root.getChildren("quest-goto-prop");
/* 136 */       for (Element element : questGoToPropElements) {
/* 137 */         chunkLoaderToken.processQuestGoToPropElements(element, chunkNode, worldCoordinate.getTileCoord());
/*     */       }
/* 139 */       long questNanos = System.nanoTime() - tempNanos;
/*     */       
/* 141 */       long endNanos = System.nanoTime();
/* 142 */       if (endNanos - startNanos > 100000000L) {
/* 143 */         System.out.println("excessive chunk loading " + (endNanos - startNanos));
/*     */       }
/* 145 */     } catch (LoadException e) {
/* 146 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 149 */     TokenRegister.instance().addToken(new AddChunkToWorldToken(chunkNode, this.chunkWorldNode));
/*     */   }
/*     */   
/*     */   private Dimension getMapSize(Element root) {
/* 153 */     Element infoElement = root.getChild("info");
/* 154 */     Element chunkSizeElement = infoElement.getChild("chunk-size");
/*     */     try {
/* 156 */       return new Dimension(chunkSizeElement.getAttribute("width").getIntValue(), chunkSizeElement.getAttribute("height").getIntValue());
/*     */     }
/* 158 */     catch (DataConversionException e) {
/* 159 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\NormalChunkLoaderStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */