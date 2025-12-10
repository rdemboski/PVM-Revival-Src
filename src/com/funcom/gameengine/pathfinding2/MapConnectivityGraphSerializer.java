/*     */ package com.funcom.gameengine.pathfinding2;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public final class MapConnectivityGraphSerializer {
/*  16 */   private static final Logger LOGGER = Logger.getLogger(MapConnectivityGraphSerializer.class);
/*     */ 
/*     */   
/*     */   public static final int VERSION = 1;
/*     */ 
/*     */   
/*     */   public static void save(MapConnectivityGraph mapConnectivityGraph, OutputStream outputStream) throws IOException {
/*  23 */     DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
/*     */ 
/*     */     
/*  26 */     dataOutputStream.writeInt(1);
/*  27 */     if (LOGGER.isDebugEnabled()) {
/*  28 */       LOGGER.debug("Version written: 1");
/*     */     }
/*     */     
/*  31 */     dataOutputStream.writeInt(mapConnectivityGraph.getSize());
/*  32 */     if (LOGGER.isDebugEnabled()) {
/*  33 */       LOGGER.debug("Node count written: " + mapConnectivityGraph.getSize());
/*     */     }
/*     */     
/*  36 */     int totalNumberOfPortals = 0;
/*  37 */     for (MapConnectionNode node : mapConnectivityGraph.getAllNodes()) {
/*  38 */       if (LOGGER.isDebugEnabled()) {
/*  39 */         LOGGER.debug("Writing node: " + node);
/*     */       }
/*  41 */       dataOutputStream.writeUTF(node.getMapName());
/*  42 */       totalNumberOfPortals += node.getPortalDefinitions().size();
/*     */     } 
/*     */ 
/*     */     
/*  46 */     dataOutputStream.writeInt(totalNumberOfPortals);
/*  47 */     LOGGER.debug("Total number of portal definitions written: " + totalNumberOfPortals);
/*     */ 
/*     */     
/*  50 */     for (MapConnectionNode node : mapConnectivityGraph.getAllNodes()) {
/*  51 */       Collection<PortalDefinition> portalDefinitions = node.getPortalDefinitions();
/*     */ 
/*     */       
/*  54 */       for (PortalDefinition portalDefinition : portalDefinitions) {
/*  55 */         dataOutputStream.writeUTF(portalDefinition.getId());
/*  56 */         dataOutputStream.writeUTF(portalDefinition.getPortalFilename());
/*  57 */         List<WorldCoordinate> positions = portalDefinition.getPositions();
/*  58 */         writeWorldCoordinate(dataOutputStream, portalDefinition.getDestination());
/*  59 */         dataOutputStream.writeInt(positions.size());
/*  60 */         for (WorldCoordinate position : positions) {
/*  61 */           writeWorldCoordinate(dataOutputStream, position);
/*     */         }
/*  63 */         if (LOGGER.isDebugEnabled()) {
/*  64 */           LOGGER.debug("Portal written: " + portalDefinition);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  69 */     if (LOGGER.isInfoEnabled())
/*  70 */       LOGGER.info("Map connectivity graph: '" + mapConnectivityGraph + "'stored to outputStream: '" + outputStream + "'"); 
/*     */   }
/*     */   
/*     */   private static void writeWorldCoordinate(DataOutputStream dataOutputStream, WorldCoordinate worldCoordinate) throws IOException {
/*  74 */     dataOutputStream.writeInt(worldCoordinate.getTileX());
/*  75 */     dataOutputStream.writeInt(worldCoordinate.getTileY());
/*  76 */     dataOutputStream.writeDouble(worldCoordinate.getTileOffX());
/*  77 */     dataOutputStream.writeDouble(worldCoordinate.getTileOffY());
/*  78 */     dataOutputStream.writeUTF(worldCoordinate.getMapId());
/*  79 */     dataOutputStream.writeInt(worldCoordinate.getInstanceReference());
/*     */   }
/*     */   
/*     */   public static MapConnectivityGraph load(InputStream inputStream) throws IOException {
/*  83 */     DataInputStream dataInputStream = new DataInputStream(inputStream);
/*     */     
/*  85 */     int version = dataInputStream.readInt();
/*  86 */     if (version != 1) {
/*  87 */       throw new IOException("Loading pathgraph version differs, version is: '1' but pathgraph file has: '" + version + "'");
/*     */     }
/*     */     
/*  90 */     int nodeCount = dataInputStream.readInt();
/*  91 */     if (LOGGER.isDebugEnabled()) {
/*  92 */       LOGGER.debug("Node count: " + nodeCount);
/*     */     }
/*  94 */     Map<String, MapConnectionNode> nodes = new HashMap<String, MapConnectionNode>(nodeCount);
/*  95 */     for (int i = 0; i < nodeCount; i++) {
/*  96 */       String mapName = dataInputStream.readUTF();
/*  97 */       MapConnectionNode node = new MapConnectionNode(mapName);
/*  98 */       if (nodes.put(mapName, node) != null) {
/*  99 */         throw new IllegalStateException("Overwriting map node, ie. double definition of map node: " + mapName);
/*     */       }
/*     */     } 
/* 102 */     if (LOGGER.isDebugEnabled()) {
/* 103 */       LOGGER.debug("Nodes loaded: " + nodes.size());
/*     */     }
/*     */     
/* 106 */     int portalDefinitions = dataInputStream.readInt();
/* 107 */     if (LOGGER.isDebugEnabled()) {
/* 108 */       LOGGER.debug("Total portal definition count: " + portalDefinitions);
/*     */     }
/* 110 */     for (int j = 0; j < portalDefinitions; j++) {
/* 111 */       String portalId = dataInputStream.readUTF();
/* 112 */       String portalFilename = dataInputStream.readUTF();
/* 113 */       WorldCoordinate destination = readWorldCoordinate(dataInputStream);
/* 114 */       int numberOfPositions = dataInputStream.readInt();
/* 115 */       for (int k = 0; k < numberOfPositions; k++) {
/* 116 */         WorldCoordinate position = readWorldCoordinate(dataInputStream);
/* 117 */         MapConnectionNode portalsNode = nodes.get(position.getMapId());
/* 118 */         MapConnectionNode portalTargetNode = nodes.get(destination.getMapId());
/* 119 */         portalsNode.addNeighbor(portalTargetNode);
/* 120 */         PortalDefinition portalDefinition = new PortalDefinition(portalId, position, destination, portalFilename);
/* 121 */         portalsNode.definePortal(portalTargetNode, portalDefinition);
/* 122 */         if (LOGGER.isDebugEnabled()) {
/* 123 */           LOGGER.debug("Portal defined: " + portalDefinition);
/*     */         }
/*     */       } 
/*     */     } 
/* 127 */     MapConnectivityGraph connectivityGraph = new MapConnectivityGraph(nodes);
/*     */     
/* 129 */     if (LOGGER.isInfoEnabled()) {
/* 130 */       LOGGER.info("Map connectivity graph built: " + connectivityGraph);
/*     */     }
/* 132 */     return connectivityGraph;
/*     */   }
/*     */   
/*     */   private static WorldCoordinate readWorldCoordinate(DataInputStream dataInputStream) throws IOException {
/* 136 */     int tileX = dataInputStream.readInt();
/* 137 */     int tileY = dataInputStream.readInt();
/* 138 */     double tileOffX = dataInputStream.readDouble();
/* 139 */     double tileOffY = dataInputStream.readDouble();
/* 140 */     String mapId = dataInputStream.readUTF();
/* 141 */     int instanceRef = dataInputStream.readInt();
/* 142 */     return new WorldCoordinate(tileX, tileY, tileOffX, tileOffY, mapId, instanceRef);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\pathfinding2\MapConnectivityGraphSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */