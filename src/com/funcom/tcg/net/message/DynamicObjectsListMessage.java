/*     */ package com.funcom.tcg.net.message;
/*     */ 
/*     */ import com.funcom.gameengine.view.MapObject;
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
/*     */ public class DynamicObjectsListMessage
/*     */   implements Message
/*     */ {
/*     */   private int id;
/*     */   private List<MapObject> mapObjectList;
/*     */   
/*     */   public DynamicObjectsListMessage() {}
/*     */   
/*     */   public DynamicObjectsListMessage(int id, List<MapObject> mapObjectList) {
/*  27 */     this.id = id;
/*  28 */     this.mapObjectList = mapObjectList;
/*     */   }
/*     */   
/*     */   public DynamicObjectsListMessage(ByteBuffer buffer) {
/*  32 */     this.id = MessageUtils.readInt(buffer);
/*  33 */     this.mapObjectList = readMapObjects(buffer);
/*     */   }
/*     */   
/*     */   public int getId() {
/*  37 */     return this.id;
/*     */   }
/*     */   
/*     */   public List<MapObject> getMapObjectList() {
/*  41 */     return this.mapObjectList;
/*     */   }
/*     */ 
/*     */   
/*     */   public short getMessageType() {
/*  46 */     return 35;
/*     */   }
/*     */ 
/*     */   
/*     */   public Message toMessage(ByteBuffer buffer) {
/*  51 */     return new DynamicObjectsListMessage(buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/*  56 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeInt() + getSizeMapObjects();
/*     */   }
/*     */   
/*     */   private int getSizeMapObjects() {
/*  60 */     int size = 0;
/*  61 */     int count = this.mapObjectList.size();
/*  62 */     for (int i = 0; i < count; i++) {
/*  63 */       MapObject mapObject = this.mapObjectList.get(i);
/*  64 */       size += MessageUtils.getSizeStr(mapObject.getDescriptionId());
/*  65 */       size += MessageUtils.getSizeWorldCoordinate();
/*  66 */       size += MessageUtils.getSizeStr(mapObject.getImagePath());
/*     */     } 
/*  68 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer serialize(ByteBuffer buffer) {
/*  73 */     MessageUtils.writeInt(buffer, this.id);
/*  74 */     writeMapObjects(buffer);
/*  75 */     return buffer;
/*     */   }
/*     */   
/*     */   private void writeMapObjects(ByteBuffer buffer) {
/*  79 */     int size = this.mapObjectList.size();
/*  80 */     buffer.putInt(size);
/*  81 */     for (int j = 0; j < size; j++) {
/*  82 */       MapObject mapObject = this.mapObjectList.get(j);
/*  83 */       MessageUtils.writeStr(buffer, mapObject.getDescriptionId());
/*  84 */       MessageUtils.writeWorldCoordinatePartial(buffer, mapObject.getWorldCoordinate());
/*  85 */       MessageUtils.writeStr(buffer, mapObject.getImagePath());
/*     */     } 
/*     */   }
/*     */   
/*     */   private List<MapObject> readMapObjects(ByteBuffer buffer) {
/*  90 */     int size = buffer.getInt();
/*  91 */     List<MapObject> mapObjects = new ArrayList<MapObject>(size);
/*  92 */     for (int i = 0; i < size; i++) {
/*  93 */       mapObjects.add(new MapObject(MessageUtils.readStr(buffer), MessageUtils.readWorldCoordinatePartial(buffer), MessageUtils.readStr(buffer)));
/*     */     }
/*  95 */     return mapObjects;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 100 */     return "DynamicObjectsListMessage{id=" + this.id + ", mapObjectList=" + this.mapObjectList + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\DynamicObjectsListMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */