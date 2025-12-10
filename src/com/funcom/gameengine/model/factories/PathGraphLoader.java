/*    */ package com.funcom.gameengine.model.factories;
/*    */ 
/*    */ import com.funcom.commons.geom.RectangleWC;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.pathfinding2.PathGraphNode;
/*    */ import com.funcom.gameengine.pathfinding2.WorldBoundsGraphNode;
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.DataInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class PathGraphLoader
/*    */ {
/* 19 */   private static final Logger LOGGER = Logger.getLogger(PathGraphLoader.class);
/*    */   
/*    */   public Set<WorldBoundsGraphNode> load(ByteBuffer byteBuffer, String mapId) throws IOException {
/* 22 */     InputStream byteBufferStream = null;
/*    */     try {
/* 24 */       byteBufferStream = new ByteArrayInputStream(byteBuffer.array());
/* 25 */       return load(byteBufferStream, mapId);
/*    */     } finally {
/*    */       try {
/* 28 */         if (byteBufferStream != null) {
/* 29 */           byteBufferStream.close();
/*    */         }
/* 31 */       } catch (IOException e) {
/* 32 */         LOGGER.error("Failed to close stream", e);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public Set<WorldBoundsGraphNode> load(InputStream in, String mapId) throws IOException {
/* 38 */     DataInputStream stream = new DataInputStream(in);
/*    */     try {
/* 40 */       int version = stream.readInt();
/* 41 */       if (version == 1) {
/* 42 */         LOGGER.info("Correct version found: '" + version + "'");
/* 43 */         Set<WorldBoundsGraphNode> returnSet = parseData(stream, mapId);
/* 44 */         LOGGER.info("Data parsed, graph node count: " + mapId);
/* 45 */         return returnSet;
/*    */       } 
/* 47 */       LOGGER.error("Version invalid, expected '1', found: '" + version + "'");
/* 48 */       return new HashSet();
/*    */     } finally {
/*    */       
/*    */       try {
/* 52 */         stream.close();
/* 53 */       } catch (IOException e) {
/* 54 */         LOGGER.error("Failed to close stream", e);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Set<WorldBoundsGraphNode> parseData(DataInputStream stream, String mapId) throws IOException {
/* 65 */     int count = stream.readInt();
/*    */ 
/*    */     
/* 68 */     HashMap<Integer, WorldBoundsGraphNode> map = new HashMap<Integer, WorldBoundsGraphNode>(); int i;
/* 69 */     for (i = 0; i < count; i++) {
/* 70 */       int id = stream.readInt();
/* 71 */       WorldCoordinate origin = readWC(stream, mapId);
/* 72 */       WorldCoordinate extent = readWC(stream, mapId);
/* 73 */       map.put(Integer.valueOf(id), new WorldBoundsGraphNode(new RectangleWC(origin, extent)));
/*    */     } 
/*    */ 
/*    */     
/* 77 */     for (i = 0; i < count; i++) {
/* 78 */       int id = stream.readInt();
/* 79 */       WorldBoundsGraphNode originNode = map.get(Integer.valueOf(id));
/*    */       
/* 81 */       int neighbourCount = stream.readInt();
/* 82 */       for (int j = 0; j < neighbourCount; j++) {
/* 83 */         int neighbourId = stream.readInt();
/* 84 */         WorldBoundsGraphNode node = map.get(Integer.valueOf(neighbourId));
/* 85 */         if (node == null)
/* 86 */           throw new IllegalStateException("Invalidly formed graph: trying to attach unexisting neighbor: nodeId='" + id + "'" + ", neighborId='" + neighbourId + "'"); 
/* 87 */         originNode.addNeighbor((PathGraphNode)node);
/*    */       } 
/*    */     } 
/*    */     
/* 91 */     return new HashSet<WorldBoundsGraphNode>(map.values());
/*    */   }
/*    */   
/*    */   private WorldCoordinate readWC(DataInputStream stream, String mapId) throws IOException {
/* 95 */     int x = stream.readInt();
/* 96 */     int y = stream.readInt();
/* 97 */     double xoff = stream.readDouble();
/* 98 */     double yoff = stream.readDouble();
/* 99 */     return new WorldCoordinate(x, y, xoff, yoff, mapId, 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\factories\PathGraphLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */