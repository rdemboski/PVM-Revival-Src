/*     */ package com.funcom.tcg.net.message;
/*     */ 
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.MessageUtils;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ActivateWaypointMessage
/*     */   implements Message
/*     */ {
/*     */   private int propId;
/*     */   private String waypointID;
/*     */   private String[] destinationPortalsId;
/*     */   private boolean[] destianPortalsLockingStatus;
/*     */   
/*     */   public ActivateWaypointMessage() {}
/*     */   
/*     */   public ActivateWaypointMessage(int propId, String waypointID, String[] destinationPortalsId, boolean[] destianPortalsLockingStatus) {
/*  25 */     this.propId = propId;
/*  26 */     this.waypointID = waypointID;
/*  27 */     this.destinationPortalsId = destinationPortalsId;
/*  28 */     this.destianPortalsLockingStatus = destianPortalsLockingStatus;
/*     */   }
/*     */   
/*     */   public ActivateWaypointMessage(ByteBuffer buffer) {
/*  32 */     this.propId = MessageUtils.readInt(buffer);
/*  33 */     this.waypointID = MessageUtils.readStr(buffer);
/*     */     
/*  35 */     this.destinationPortalsId = MessageUtils.readStrArray(buffer);
/*  36 */     int listSize = this.destinationPortalsId.length;
/*     */     
/*  38 */     this.destianPortalsLockingStatus = new boolean[listSize];
/*  39 */     for (int i = 0; i < listSize; i++) {
/*  40 */       this.destianPortalsLockingStatus[i] = MessageUtils.readBoolean(buffer).booleanValue();
/*     */     }
/*     */   }
/*     */   
/*     */   public int getPropId() {
/*  45 */     return this.propId;
/*     */   }
/*     */   
/*     */   public String getWaypointID() {
/*  49 */     return this.waypointID;
/*     */   }
/*     */   
/*     */   public String[] getDestinationPortalsId() {
/*  53 */     return this.destinationPortalsId;
/*     */   }
/*     */   
/*     */   public boolean[] getDestianPortalsLockingStatus() {
/*  57 */     return this.destianPortalsLockingStatus;
/*     */   }
/*     */ 
/*     */   
/*     */   public short getMessageType() {
/*  62 */     return 47;
/*     */   }
/*     */ 
/*     */   
/*     */   public Message toMessage(ByteBuffer buffer) {
/*  67 */     return new ActivateWaypointMessage(buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/*  72 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeStr(this.waypointID) + MessageUtils.getSizeStrArray(this.destinationPortalsId) + getSizeDestianPortalsLockingStatus();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer serialize(ByteBuffer buffer) {
/*  77 */     MessageUtils.writeInt(buffer, this.propId);
/*  78 */     MessageUtils.writeStr(buffer, this.waypointID);
/*  79 */     MessageUtils.writeStrArray(buffer, this.destinationPortalsId);
/*  80 */     writeDestianPortalsLockingStatus(buffer);
/*     */     
/*  82 */     return buffer;
/*     */   }
/*     */   
/*     */   private void writeDestianPortalsLockingStatus(ByteBuffer buffer) {
/*  86 */     for (boolean destinationPortalsLockingStatus : this.destianPortalsLockingStatus) {
/*  87 */       MessageUtils.writeBoolean(buffer, Boolean.valueOf(destinationPortalsLockingStatus));
/*     */     }
/*     */   }
/*     */   
/*     */   public int getSizeDestianPortalsLockingStatus() {
/*  92 */     int size = 0;
/*  93 */     for (boolean destianPortalsLockingStatu : this.destianPortalsLockingStatus) {
/*  94 */       size += MessageUtils.getSizeBoolean();
/*     */     }
/*  96 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 101 */     return "ActivateWaypointMessage{propId=" + this.propId + ", waypointID='" + this.waypointID + '\'' + ", destinationPortalsId=" + ((this.destinationPortalsId == null) ? null : (String)Arrays.<String>asList(this.destinationPortalsId)) + ", destianPortalsLockingStatus=" + ((this.destianPortalsLockingStatus == null) ? null : (String)Arrays.<boolean[]>asList(new boolean[][] { this.destianPortalsLockingStatus })) + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\ActivateWaypointMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */