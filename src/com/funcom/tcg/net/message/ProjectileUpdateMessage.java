/*     */ package com.funcom.tcg.net.message;
/*     */ 
/*     */ import com.funcom.commons.Vector2d;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.MessageUtils;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProjectileUpdateMessage
/*     */   implements Message
/*     */ {
/*  22 */   private List<NewProjectileData> newDataList = new ArrayList<NewProjectileData>();
/*  23 */   private List<RemovedProjectileData> removedDataList = new ArrayList<RemovedProjectileData>();
/*     */   private List<DebugProjectileData> debugDataList;
/*     */   
/*     */   public ProjectileUpdateMessage(ByteBuffer buffer) {
/*  27 */     this();
/*     */ 
/*     */     
/*  30 */     int count = buffer.getShort() & 0xFFFF; int i;
/*  31 */     for (i = 0; i < count; i++) {
/*  32 */       this.newDataList.add(new NewProjectileData(buffer));
/*     */     }
/*  34 */     count = buffer.getShort() & 0xFFFF;
/*  35 */     for (i = 0; i < count; i++) {
/*  36 */       this.removedDataList.add(new RemovedProjectileData(buffer));
/*     */     }
/*  38 */     count = buffer.getShort() & 0xFFFF;
/*  39 */     for (i = 0; i < count; i++) {
/*  40 */       if (this.debugDataList == null) {
/*  41 */         this.debugDataList = new ArrayList<DebugProjectileData>();
/*     */       }
/*  43 */       this.debugDataList.add(new DebugProjectileData(buffer));
/*     */     } 
/*  45 */     count = buffer.getShort() & 0xFFFF;
/*  46 */     for (i = 0; i < count; i++) {
/*  47 */       if (this.projectileUpdateDataList == null) {
/*  48 */         this.projectileUpdateDataList = new ArrayList<ProjectileUpdateData>();
/*     */       }
/*  50 */       this.projectileUpdateDataList.add(new ProjectileUpdateData(buffer));
/*     */     } 
/*     */   }
/*     */   
/*     */   private List<ProjectileUpdateData> projectileUpdateDataList;
/*     */   
/*     */   public ByteBuffer serialize(ByteBuffer buffer) {
/*  57 */     buffer.putShort((short)this.newDataList.size());
/*  58 */     int size = this.newDataList.size(); int i;
/*  59 */     for (i = 0; i < size; i++) {
/*  60 */       NewProjectileData projectileData = this.newDataList.get(i);
/*  61 */       projectileData.write(buffer);
/*     */     } 
/*     */     
/*  64 */     buffer.putShort((short)this.removedDataList.size());
/*  65 */     size = this.removedDataList.size();
/*  66 */     for (i = 0; i < size; i++) {
/*  67 */       RemovedProjectileData projectileData = this.removedDataList.get(i);
/*  68 */       projectileData.write(buffer);
/*     */     } 
/*     */     
/*  71 */     if (this.debugDataList != null) {
/*  72 */       buffer.putShort((short)this.debugDataList.size());
/*  73 */       size = this.debugDataList.size();
/*  74 */       for (i = 0; i < size; i++) {
/*  75 */         DebugProjectileData projectileData = this.debugDataList.get(i);
/*  76 */         projectileData.write(buffer);
/*     */       } 
/*     */     } else {
/*  79 */       buffer.putShort((short)0);
/*     */     } 
/*     */     
/*  82 */     if (this.projectileUpdateDataList != null) {
/*  83 */       buffer.putShort((short)this.projectileUpdateDataList.size());
/*  84 */       size = this.projectileUpdateDataList.size();
/*  85 */       for (i = 0; i < size; i++) {
/*  86 */         ProjectileUpdateData projectileData = this.projectileUpdateDataList.get(i);
/*  87 */         projectileData.write(buffer);
/*     */       } 
/*     */     } else {
/*  90 */       buffer.putShort((short)0);
/*     */     } 
/*     */     
/*  93 */     return buffer;
/*     */   }
/*     */   public ProjectileUpdateMessage() {}
/*     */   public short getMessageType() {
/*  97 */     return 224;
/*     */   }
/*     */   
/*     */   public Message toMessage(ByteBuffer buffer) {
/* 101 */     return new ProjectileUpdateMessage(buffer);
/*     */   }
/*     */   
/*     */   public int getSerializedSize() {
/* 105 */     return 2 + getSizeNewDataList() + 2 + getSizeRemovedDataList() + 2 + getSizeDebugDataList() + 2 + getSizeProjectileUpdateDataList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getSizeProjectileUpdateDataList() {
/* 112 */     int count = 0;
/* 113 */     if (this.projectileUpdateDataList != null) {
/* 114 */       int size = this.projectileUpdateDataList.size();
/* 115 */       for (int i = 0; i < size; i++) {
/* 116 */         (ProjectileUpdateData)this.projectileUpdateDataList.get(i); count += ProjectileUpdateData.getSize();
/*     */       } 
/*     */     } 
/* 119 */     return count;
/*     */   }
/*     */   
/*     */   private int getSizeDebugDataList() {
/* 123 */     int ret = 0;
/* 124 */     if (this.debugDataList != null) {
/* 125 */       int size = this.debugDataList.size();
/* 126 */       for (int i = 0; i < size; i++) {
/* 127 */         ret += ((DebugProjectileData)this.debugDataList.get(i)).getSize();
/*     */       }
/*     */     } 
/* 130 */     return ret;
/*     */   }
/*     */   
/*     */   private int getSizeRemovedDataList() {
/* 134 */     return this.removedDataList.size() * RemovedProjectileData.getSize();
/*     */   }
/*     */   
/*     */   private int getSizeNewDataList() {
/* 138 */     int counter = 0;
/*     */     
/* 140 */     int size = this.newDataList.size();
/* 141 */     for (int i = 0; i < size; i++) {
/* 142 */       counter += ((NewProjectileData)this.newDataList.get(i)).getSize();
/*     */     }
/*     */     
/* 145 */     return counter;
/*     */   }
/*     */   
/*     */   public void addNew(int id, int aliveTimerMillis, WorldCoordinate pos, Vector2d velocity, String projectileId) {
/* 149 */     this.newDataList.add(new NewProjectileData(id, aliveTimerMillis, pos, velocity, projectileId));
/*     */   }
/*     */   
/*     */   public void addRemoved(int id, boolean triggered) {
/* 153 */     this.removedDataList.add(new RemovedProjectileData(id, triggered));
/*     */   }
/*     */   
/*     */   public void addDebug(int id, WorldCoordinate pos, Vector2d[] localCorners) {
/* 157 */     if (this.debugDataList == null) {
/* 158 */       this.debugDataList = new ArrayList<DebugProjectileData>();
/*     */     }
/* 160 */     this.debugDataList.add(new DebugProjectileData(id, pos, localCorners));
/*     */   }
/*     */   
/*     */   public void addUpdate(int id, int aliveTimerMillis, WorldCoordinate targetPosition) {
/* 164 */     if (this.projectileUpdateDataList == null) {
/* 165 */       this.projectileUpdateDataList = new ArrayList<ProjectileUpdateData>();
/*     */     }
/* 167 */     this.projectileUpdateDataList.add(new ProjectileUpdateData(id, aliveTimerMillis, targetPosition));
/*     */   }
/*     */   
/*     */   public List<NewProjectileData> getNewDataList() {
/* 171 */     return this.newDataList;
/*     */   }
/*     */   
/*     */   public List<RemovedProjectileData> getRemovedDataList() {
/* 175 */     return this.removedDataList;
/*     */   }
/*     */   
/*     */   public List<DebugProjectileData> getDebugDataList() {
/* 179 */     return this.debugDataList;
/*     */   }
/*     */   
/*     */   public List<ProjectileUpdateData> getProjectileUpdateDataList() {
/* 183 */     return this.projectileUpdateDataList;
/*     */   }
/*     */   
/*     */   public static class NewProjectileData
/*     */   {
/*     */     private final int id;
/*     */     private final int aliveTimerMillis;
/*     */     private final WorldCoordinate pos;
/*     */     private final Vector2d velocity;
/*     */     private String projectileId;
/*     */     
/*     */     public NewProjectileData(int id, int aliveTimerMillis, WorldCoordinate pos, Vector2d velocity, String projectileId) {
/* 195 */       this.id = id;
/* 196 */       this.aliveTimerMillis = aliveTimerMillis;
/* 197 */       this.pos = pos;
/* 198 */       this.velocity = velocity;
/* 199 */       this.projectileId = projectileId;
/*     */     }
/*     */     
/*     */     public NewProjectileData(ByteBuffer buffer) {
/* 203 */       this.id = buffer.getInt();
/* 204 */       this.aliveTimerMillis = buffer.getInt();
/* 205 */       this.pos = MessageUtils.readWorldCoordinatePartial(buffer);
/* 206 */       this.velocity = MessageUtils.readVector2d(buffer);
/* 207 */       this.projectileId = MessageUtils.readStr(buffer);
/*     */     }
/*     */     
/*     */     public void write(ByteBuffer buffer) {
/* 211 */       buffer.putInt(this.id);
/* 212 */       buffer.putInt(this.aliveTimerMillis);
/* 213 */       MessageUtils.writeWorldCoordinatePartial(buffer, this.pos);
/* 214 */       MessageUtils.writeVector2d(buffer, this.velocity);
/* 215 */       MessageUtils.writeStr(buffer, this.projectileId);
/*     */     }
/*     */     
/*     */     public int getSize() {
/* 219 */       return MessageUtils.getSizeInt() + MessageUtils.getSizeInt() + MessageUtils.getSizeWorldCoordinate() + MessageUtils.getSizeVector2d() + MessageUtils.getSizeStr(this.projectileId);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getId() {
/* 227 */       return this.id;
/*     */     }
/*     */     
/*     */     public int getAliveTimerMillis() {
/* 231 */       return this.aliveTimerMillis;
/*     */     }
/*     */     
/*     */     public WorldCoordinate getPos() {
/* 235 */       return this.pos;
/*     */     }
/*     */     
/*     */     public Vector2d getVelocity() {
/* 239 */       return this.velocity;
/*     */     }
/*     */     
/*     */     public String getProjectileId() {
/* 243 */       return this.projectileId;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 247 */       return "ProjectileData{id=" + this.id + ", aliveTimerMillis=" + this.aliveTimerMillis + ", pos=" + this.pos + ", velocity=" + this.velocity + ", projectileId=" + this.projectileId + '}';
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class RemovedProjectileData
/*     */   {
/*     */     private final int id;
/*     */ 
/*     */     
/*     */     private final boolean triggered;
/*     */ 
/*     */     
/*     */     private RemovedProjectileData(int id, boolean triggered) {
/* 262 */       this.id = id;
/* 263 */       this.triggered = triggered;
/*     */     }
/*     */     
/*     */     public RemovedProjectileData(ByteBuffer buffer) {
/* 267 */       this.id = buffer.getInt();
/* 268 */       this.triggered = MessageUtils.readBoolean(buffer).booleanValue();
/*     */     }
/*     */     
/*     */     public void write(ByteBuffer buffer) {
/* 272 */       buffer.putInt(this.id);
/* 273 */       MessageUtils.writeBoolean(buffer, Boolean.valueOf(this.triggered));
/*     */     }
/*     */     
/*     */     public static int getSize() {
/* 277 */       return 4 + MessageUtils.getSizeBoolean();
/*     */     }
/*     */     
/*     */     public int getId() {
/* 281 */       return this.id;
/*     */     }
/*     */     
/*     */     public boolean isTriggered() {
/* 285 */       return this.triggered;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 289 */       return "RemovedProjectileData{id=" + this.id + ", triggered=" + this.triggered + '}';
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class ProjectileUpdateData
/*     */   {
/*     */     private final int id;
/*     */     
/*     */     private final int aliveTimerMillis;
/*     */     private final WorldCoordinate targetPostion;
/*     */     
/*     */     private ProjectileUpdateData(int id, int aliveTimerMillis, WorldCoordinate targetPostion) {
/* 302 */       this.id = id;
/* 303 */       this.aliveTimerMillis = aliveTimerMillis;
/* 304 */       this.targetPostion = targetPostion;
/*     */     }
/*     */     
/*     */     public ProjectileUpdateData(ByteBuffer buffer) {
/* 308 */       this.id = MessageUtils.readInt(buffer);
/* 309 */       this.aliveTimerMillis = MessageUtils.readInt(buffer);
/* 310 */       this.targetPostion = MessageUtils.readWorldCoordinatePartial(buffer);
/*     */     }
/*     */     
/*     */     public void write(ByteBuffer buffer) {
/* 314 */       MessageUtils.writeInt(buffer, this.id);
/* 315 */       MessageUtils.writeInt(buffer, this.aliveTimerMillis);
/* 316 */       MessageUtils.writeWorldCoordinatePartial(buffer, this.targetPostion);
/*     */     }
/*     */     
/*     */     public static int getSize() {
/* 320 */       return MessageUtils.getSizeInt() + MessageUtils.getSizeInt() + MessageUtils.getSizeWorldCoordinate();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getId() {
/* 326 */       return this.id;
/*     */     }
/*     */     
/*     */     public int getAliveTimerMillis() {
/* 330 */       return this.aliveTimerMillis;
/*     */     }
/*     */     
/*     */     public WorldCoordinate getTargetPostion() {
/* 334 */       return this.targetPostion;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class DebugProjectileData {
/*     */     private final int id;
/*     */     private final WorldCoordinate pos;
/*     */     private final Vector2d[] localCorners;
/*     */     
/*     */     public DebugProjectileData(int id, WorldCoordinate pos, Vector2d[] localCorners) {
/* 344 */       this.id = id;
/* 345 */       this.pos = pos;
/* 346 */       this.localCorners = localCorners;
/*     */     }
/*     */     
/*     */     public DebugProjectileData(ByteBuffer buffer) {
/* 350 */       this.id = buffer.getInt();
/* 351 */       this.pos = MessageUtils.readWorldCoordinatePartial(buffer);
/* 352 */       this.localCorners = readCorners(buffer);
/*     */     }
/*     */     
/*     */     public void write(ByteBuffer buffer) {
/* 356 */       buffer.putInt(this.id);
/* 357 */       MessageUtils.writeWorldCoordinatePartial(buffer, this.pos);
/* 358 */       writeCorners(buffer, this.localCorners);
/*     */     }
/*     */     
/*     */     private Vector2d[] readCorners(ByteBuffer buffer) {
/* 362 */       int count = buffer.getInt();
/* 363 */       Vector2d[] ret = new Vector2d[count];
/* 364 */       for (int i = 0; i < count; i++) {
/* 365 */         ret[i] = new Vector2d(buffer.getDouble(), buffer.getDouble());
/*     */       }
/* 367 */       return ret;
/*     */     }
/*     */     
/*     */     public int getSize() {
/* 371 */       return 4 + MessageUtils.getSizeWorldCoordinate() + getSizeCorners();
/*     */     }
/*     */     
/*     */     private int getSizeCorners() {
/* 375 */       return 4 + 16 * this.localCorners.length;
/*     */     }
/*     */     
/*     */     private void writeCorners(ByteBuffer buffer, Vector2d[] localCorners) {
/* 379 */       buffer.putInt(localCorners.length);
/* 380 */       int length = localCorners.length;
/* 381 */       for (int i = 0; i < length; i++) {
/* 382 */         Vector2d localCorner = localCorners[i];
/* 383 */         buffer.putDouble(localCorner.getX());
/* 384 */         buffer.putDouble(localCorner.getY());
/*     */       } 
/*     */     }
/*     */     
/*     */     public int getId() {
/* 389 */       return this.id;
/*     */     }
/*     */     
/*     */     public WorldCoordinate getPos() {
/* 393 */       return this.pos;
/*     */     }
/*     */     
/*     */     public Vector2d[] getLocalCorners() {
/* 397 */       return this.localCorners;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 401 */       return "DebugProjectileData{id=" + this.id + ", pos=" + this.pos + '}';
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\ProjectileUpdateMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */