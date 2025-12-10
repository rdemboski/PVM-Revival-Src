/*     */ package com.funcom.gameengine.spatial;
/*     */ 
/*     */ import com.funcom.commons.geom.LineWC;
/*     */ import com.funcom.commons.geom.LineWCHeight;
/*     */ import com.funcom.commons.geom.RectangleWC;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import java.util.HashSet;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LineGrid
/*     */ {
/*  23 */   private volatile LineGridData gridData = new LineGridData(null, null, null);
/*     */ 
/*     */   
/*     */   public void put(LineWCHeight line) {
/*  27 */     CellId startId = new CellId(line.getWC1());
/*  28 */     CellId endId = new CellId(line.getWC2());
/*     */     
/*  30 */     this.gridData = this.gridData.ensureGridSize(startId, endId);
/*     */     
/*  32 */     this.gridData.putLineToGrid(line, startId, endId);
/*     */   }
/*     */   
/*     */   public Set<LineWCHeight> getLines(RectangleWC r) {
/*  36 */     IdentityHashMap<LineWCHeight, LineWCHeight> foundLines = this.gridData.getLines(r);
/*     */     
/*  38 */     if (foundLines != null) {
/*  39 */       LineWCHeight[] allLines = (LineWCHeight[])foundLines.keySet().toArray((Object[])new LineWCHeight[foundLines.size()]);
/*  40 */       for (LineWCHeight line : allLines) {
/*  41 */         if (!line.intersects(r)) {
/*  42 */           foundLines.remove(line);
/*     */         }
/*     */       } 
/*     */       
/*  46 */       return new HashSet<LineWCHeight>(foundLines.keySet());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  51 */     return new HashSet<LineWCHeight>(0);
/*     */   }
/*     */   
/*     */   private static class CellId {
/*     */     private final int x;
/*     */     private final int y;
/*     */     
/*     */     CellId(int x, int y) {
/*  59 */       this.x = x;
/*  60 */       this.y = y;
/*     */     }
/*     */     
/*     */     CellId(WorldCoordinate coordinate) {
/*  64 */       double offsetX = coordinate.getTileOffX();
/*  65 */       double offsetY = coordinate.getTileOffY();
/*     */       
/*  67 */       if (!validRange(offsetX)) {
/*  68 */         throw new IllegalArgumentException("offset is out of range, <-1, 1>: offsetX=" + offsetX);
/*     */       }
/*  70 */       if (!validRange(offsetY)) {
/*  71 */         throw new IllegalArgumentException("offset is out of range, <-1, 1>: offsetY=" + offsetY);
/*     */       }
/*     */       
/*  74 */       int x = coordinate.getTileX();
/*  75 */       int y = coordinate.getTileY();
/*  76 */       if (offsetX < 0.0D) {
/*  77 */         x--;
/*     */       }
/*  79 */       if (offsetY < 0.0D) {
/*  80 */         y--;
/*     */       }
/*     */       
/*  83 */       this.x = x;
/*  84 */       this.y = y;
/*     */     }
/*     */     
/*     */     private boolean validRange(double offset) {
/*  88 */       return (offset > -1.0D && offset < 1.0D);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  93 */       return "(" + this.x + "," + this.y + ')';
/*     */     }
/*     */     
/*     */     private static CellId evalNewOffset(CellId oldOffsetId, int minX, int minY) {
/*  97 */       if (oldOffsetId != null) {
/*  98 */         if (minX < oldOffsetId.x || minY < oldOffsetId.y) {
/*  99 */           return new CellId(Math.min(oldOffsetId.x, minX), Math.min(oldOffsetId.y, minY));
/*     */         }
/*     */         
/* 102 */         return null;
/*     */       } 
/*     */       
/* 105 */       return new CellId(minX, minY);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class LineGridData
/*     */   {
/*     */     private final LineGrid.CellId idOffset;
/*     */     
/*     */     private final LineGrid.Size gridSize;
/*     */     
/*     */     private final LineGridCell[] cells;
/*     */     private volatile boolean hasContent;
/*     */     
/*     */     public LineGridData(LineGrid.CellId idOffset, LineGrid.Size gridSize, LineGridCell[] cells) {
/* 120 */       this.idOffset = idOffset;
/* 121 */       this.gridSize = gridSize;
/* 122 */       this.cells = cells;
/*     */     }
/*     */     
/*     */     private LineGridData ensureGridSize(LineGrid.CellId startId, LineGrid.CellId endId) {
/* 126 */       int minX = Math.min(startId.x, endId.x);
/* 127 */       int minY = Math.min(startId.y, endId.y);
/* 128 */       int maxX = Math.max(startId.x, endId.x);
/* 129 */       int maxY = Math.max(startId.y, endId.y);
/*     */       
/* 131 */       LineGrid.CellId newIdOffset = LineGrid.CellId.evalNewOffset(this.idOffset, minX, minY);
/* 132 */       LineGrid.CellId recentIdOffset = (newIdOffset != null) ? newIdOffset : this.idOffset;
/*     */       
/* 134 */       if (recentIdOffset == null) {
/* 135 */         throw new RuntimeException("should have an offset");
/*     */       }
/*     */       
/* 138 */       LineGrid.Size newSize = null;
/* 139 */       int oldMaxX = 0;
/* 140 */       int oldMaxY = 0;
/*     */       
/* 142 */       if (this.idOffset != null) {
/* 143 */         oldMaxX = this.idOffset.x + this.gridSize.width - 1;
/* 144 */         oldMaxY = this.idOffset.y + this.gridSize.height - 1;
/*     */       } 
/*     */       
/* 147 */       if (maxX > oldMaxX || maxY > oldMaxY || newIdOffset != null)
/*     */       {
/*     */         
/* 150 */         newSize = new LineGrid.Size(Math.max(maxX, oldMaxX) - recentIdOffset.x + 1, Math.max(maxY, oldMaxY) - recentIdOffset.y + 1);
/*     */       }
/*     */       
/* 153 */       LineGrid.Size recentSize = (newSize != null) ? newSize : this.gridSize;
/*     */       
/* 155 */       if (newIdOffset != null || newSize != null) {
/* 156 */         LineGridCell[] tmp = new LineGridCell[recentSize.getCellCount()];
/*     */         
/* 158 */         if (this.cells != null) {
/* 159 */           copyArea(this.cells, this.gridSize, this.idOffset, tmp, recentSize, recentIdOffset);
/*     */         }
/*     */         
/* 162 */         return new LineGridData(recentIdOffset, recentSize, tmp);
/*     */       } 
/*     */       
/* 165 */       return this;
/*     */     }
/*     */     
/*     */     private void copyArea(LineGridCell[] src, LineGrid.Size srcSize, LineGrid.CellId srcOffset, LineGridCell[] dest, LineGrid.Size destSize, LineGrid.CellId destOffset) {
/* 169 */       int offsetDiffX = destOffset.x - srcOffset.x;
/* 170 */       int offsetDiffY = destOffset.y - srcOffset.y;
/*     */       
/* 172 */       for (int y = 0; y < srcSize.height; y++)
/*     */       {
/*     */ 
/*     */         
/* 176 */         System.arraycopy(src, y * srcSize.width, dest, (y - offsetDiffY) * destSize.width + -offsetDiffX, srcSize.width);
/*     */       }
/*     */     }
/*     */     
/*     */     private void putLineToGrid(LineWCHeight line, LineGrid.CellId startId, LineGrid.CellId endId) {
/* 181 */       int diffX = endId.x - startId.x;
/* 182 */       int diffY = endId.y - startId.y;
/*     */       
/* 184 */       int width = Math.abs(diffX) + 1;
/* 185 */       int height = Math.abs(diffY) + 1;
/*     */       
/* 187 */       int steps = Math.max(width, height);
/*     */       
/* 189 */       for (int i = 0; i < steps; i++) {
/* 190 */         int x = diffX * (i + 1) / steps + startId.x;
/* 191 */         int y = diffY * (i + 1) / steps + startId.y;
/*     */         
/* 193 */         checkAndPutLineToCells(line, x, y);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 198 */       this.hasContent = true;
/*     */     }
/*     */     
/*     */     private void checkAndPutLineToCells(LineWCHeight line, int baseX, int baseY) {
/* 202 */       RectangleWC tmpRect = new RectangleWC();
/* 203 */       for (int modY = -1; modY <= 1; modY++) {
/* 204 */         for (int modX = -1; modX <= 1; modX++) {
/* 205 */           int x = baseX + modX;
/* 206 */           int y = baseY + modY;
/* 207 */           if (isWithinGrid(x, y)) {
/* 208 */             tmpRect.getOrigin().set(x, y, 0.0D, 0.0D);
/* 209 */             tmpRect.getExtent().set(x + 1, y + 1, 0.0D, 0.0D);
/* 210 */             if (tmpRect.intersectsLine((LineWC)line)) {
/* 211 */               LineGridCell cell = ensureCellAt(x, y);
/* 212 */               cell.put(line);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private boolean isWithinGrid(int x, int y) {
/* 220 */       if (x < this.idOffset.x || y < this.idOffset.y || x >= this.idOffset.x + this.gridSize.width || y >= this.idOffset.y + this.gridSize.height)
/*     */       {
/*     */         
/* 223 */         return false;
/*     */       }
/* 225 */       return true;
/*     */     }
/*     */     
/*     */     private LineGridCell ensureCellAt(int x, int y) {
/* 229 */       int index = (y - this.idOffset.y) * this.gridSize.width + x - this.idOffset.x;
/*     */       
/* 231 */       LineGridCell cell = this.cells[index];
/*     */       
/* 233 */       if (cell == null) {
/* 234 */         cell = new LineGridCell();
/* 235 */         this.cells[index] = cell;
/*     */ 
/*     */         
/* 238 */         this.hasContent = true;
/*     */       } 
/*     */       
/* 241 */       return cell;
/*     */     }
/*     */     
/*     */     private LineGridCell getCellAt(int x, int y) {
/* 245 */       int index = (y - this.idOffset.y) * this.gridSize.width + x - this.idOffset.x;
/* 246 */       return this.cells[index];
/*     */     }
/*     */ 
/*     */     
/*     */     private IdentityHashMap<LineWCHeight, LineWCHeight> getLines(RectangleWC r) {
/* 251 */       if (this.hasContent) {
/* 252 */         IdentityHashMap<LineWCHeight, LineWCHeight> foundLines = null;
/*     */         
/* 254 */         LineGrid.CellId originId = new LineGrid.CellId(r.getOrigin());
/* 255 */         LineGrid.CellId extentId = new LineGrid.CellId(r.getExtent());
/*     */         
/* 257 */         int minX = Math.min(originId.x, extentId.x);
/* 258 */         int minY = Math.min(originId.y, extentId.y);
/* 259 */         int maxX = Math.max(originId.x, extentId.x);
/* 260 */         int maxY = Math.max(originId.y, extentId.y);
/*     */         
/* 262 */         int width = maxX - minX + 1;
/* 263 */         int height = maxY - minY + 1;
/*     */         
/* 265 */         for (int y = 0; y < height; y++) {
/* 266 */           int cellY = y + minY;
/* 267 */           for (int x = 0; x < width; x++) {
/* 268 */             int cellX = x + minX;
/* 269 */             if (isWithinGrid(cellX, cellY)) {
/* 270 */               LineGridCell cell = getCellAt(cellX, cellY);
/* 271 */               if (cell != null) {
/* 272 */                 if (foundLines == null) {
/* 273 */                   foundLines = new IdentityHashMap<LineWCHeight, LineWCHeight>();
/*     */                 }
/*     */                 
/* 276 */                 LineWCHeight[] lines = cell.getLines();
/* 277 */                 for (LineWCHeight line : lines) {
/* 278 */                   foundLines.put(line, line);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 285 */         return foundLines;
/*     */       } 
/* 287 */       return null;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Size {
/*     */     private final int width;
/*     */     private final int height;
/*     */     
/*     */     public Size(int width, int height) {
/* 296 */       this.width = width;
/* 297 */       this.height = height;
/*     */     }
/*     */     
/*     */     public int getCellCount() {
/* 301 */       return this.width * this.height;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 306 */       return "(" + this.width + " x " + this.height + ")";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\spatial\LineGrid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */