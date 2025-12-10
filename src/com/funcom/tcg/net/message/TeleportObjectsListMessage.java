/*     */ package com.funcom.tcg.net.message;
/*     */ 
/*     */ import com.funcom.gameengine.view.TeleportMapObject;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.MessageUtils;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TeleportObjectsListMessage
/*     */   implements Message
/*     */ {
/*     */   private int id;
/*     */   private List<TeleportMapObject> mapObjectList;
/*     */   
/*     */   public TeleportObjectsListMessage() {}
/*     */   
/*     */   public TeleportObjectsListMessage(int id, List<TeleportMapObject> mapObjectList) {
/*  22 */     this.id = id;
/*  23 */     this.mapObjectList = mapObjectList;
/*     */   }
/*     */   
/*     */   public TeleportObjectsListMessage(ByteBuffer buffer) {
/*  27 */     this.id = MessageUtils.readInt(buffer);
/*  28 */     this.mapObjectList = readMapObjects(buffer);
/*     */   }
/*     */   
/*     */   public int getId() {
/*  32 */     return this.id;
/*     */   }
/*     */   
/*     */   public List<TeleportMapObject> getMapObjectList() {
/*  36 */     return this.mapObjectList;
/*     */   }
/*     */ 
/*     */   
/*     */   public short getMessageType() {
/*  41 */     return 62;
/*     */   }
/*     */ 
/*     */   
/*     */   public Message toMessage(ByteBuffer buffer) {
/*  46 */     return new TeleportObjectsListMessage(buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/*  51 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeInt() + getSizeMapObjects();
/*     */   }
/*     */   
/*     */   private int getSizeMapObjects() {
/*  55 */     int size = 0;
/*  56 */     int count = this.mapObjectList.size();
/*  57 */     for (int i = 0; i < count; i++) {
/*  58 */       TeleportMapObject mapObject = this.mapObjectList.get(i);
/*  59 */       size += MessageUtils.getSizeStr(mapObject.getDescriptionId());
/*  60 */       size += MessageUtils.getSizeWorldCoordinate();
/*  61 */       size += MessageUtils.getSizeStr(mapObject.getImagePath());
/*  62 */       size += MessageUtils.getSizeListStr(mapObject.getDestinationMaps());
/*     */     } 
/*  64 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer serialize(ByteBuffer buffer) {
/*  69 */     MessageUtils.writeInt(buffer, this.id);
/*  70 */     writeMapObjects(buffer);
/*  71 */     return buffer;
/*     */   }
/*     */   
/*     */   private void writeMapObjects(ByteBuffer buffer) {
/*  75 */     int size = this.mapObjectList.size();
/*  76 */     buffer.putInt(size);
/*  77 */     for (int j = 0; j < size; j++) {
/*  78 */       TeleportMapObject mapObject = this.mapObjectList.get(j);
/*  79 */       MessageUtils.writeStr(buffer, mapObject.getDescriptionId());
/*  80 */       MessageUtils.writeWorldCoordinatePartial(buffer, mapObject.getWorldCoordinate());
/*  81 */       MessageUtils.writeStr(buffer, mapObject.getImagePath());
/*  82 */       MessageUtils.writeListStr(buffer, mapObject.getDestinationMaps());
/*     */     } 
/*     */   }
/*     */   
/*     */   private List<TeleportMapObject> readMapObjects(ByteBuffer buffer) {
/*  87 */     int size = buffer.getInt();
/*  88 */     List<TeleportMapObject> mapObjects = new ArrayList<TeleportMapObject>(size);
/*  89 */     for (int i = 0; i < size; i++) {
/*  90 */       TeleportMapObject mapObject = new TeleportMapObject(MessageUtils.readStr(buffer), MessageUtils.readWorldCoordinatePartial(buffer), MessageUtils.readStr(buffer));
/*  91 */       List<String> destinations = MessageUtils.readListStr(buffer);
/*  92 */       for (String dest : destinations)
/*  93 */         mapObject.addDestination(dest); 
/*  94 */       mapObjects.add(mapObject);
/*     */     } 
/*  96 */     return mapObjects;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 101 */     return "TeleportObjectsListMessage{id=" + this.id + ", mapObjectList=" + this.mapObjectList + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\TeleportObjectsListMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */