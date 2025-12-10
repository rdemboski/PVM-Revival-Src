/*    */ package com.funcom.gameengine.conanchat.packets2;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.datatypes.Endianess;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Integer32;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Integer8;
/*    */ import com.funcom.server.common.Message;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuddyAdded
/*    */   implements ChatMessage
/*    */ {
/*    */   private Integer32 clientid;
/*    */   private Integer8 onlineStatus;
/*    */   private Integer8 level;
/*    */   private Integer32 runningplayfieldlastonlinetime;
/*    */   private Integer8 clazz;
/*    */   
/*    */   public BuddyAdded() {}
/*    */   
/*    */   public BuddyAdded(Integer32 clientid, Integer8 onlineStatus, Integer8 level, Integer32 runningplayfieldlastonlinetime, Integer8 clazz) {
/* 30 */     this.clientid = clientid;
/* 31 */     this.onlineStatus = onlineStatus;
/* 32 */     this.level = level;
/* 33 */     this.runningplayfieldlastonlinetime = runningplayfieldlastonlinetime;
/* 34 */     this.clazz = clazz;
/*    */   }
/*    */   
/*    */   public BuddyAdded(ByteBuffer byteBuffer) {
/* 38 */     this.clientid = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/* 39 */     this.onlineStatus = new Integer8(byteBuffer, Endianess.BIG_ENDIAN);
/* 40 */     this.level = new Integer8(byteBuffer, Endianess.BIG_ENDIAN);
/* 41 */     this.runningplayfieldlastonlinetime = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/* 42 */     this.clazz = new Integer8(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 47 */     return 40;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 52 */     return new BuddyAdded(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 57 */     return this.clientid.getSizeInBytes() + this.onlineStatus.getSizeInBytes() + this.level.getSizeInBytes() + this.runningplayfieldlastonlinetime.getSizeInBytes() + this.clazz.getSizeInBytes();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 66 */     this.clientid.toByteBuffer(buffer);
/* 67 */     this.onlineStatus.toByteBuffer(buffer);
/* 68 */     this.level.toByteBuffer(buffer);
/* 69 */     this.runningplayfieldlastonlinetime.toByteBuffer(buffer);
/* 70 */     this.clazz.toByteBuffer(buffer);
/* 71 */     return buffer;
/*    */   }
/*    */   
/*    */   public Integer32 getClientid() {
/* 75 */     return this.clientid;
/*    */   }
/*    */   
/*    */   public Integer8 getOnlineStatus() {
/* 79 */     return this.onlineStatus;
/*    */   }
/*    */   
/*    */   public Integer8 getLevel() {
/* 83 */     return this.level;
/*    */   }
/*    */   
/*    */   public Integer32 getRunningplayfieldlastonlinetime() {
/* 87 */     return this.runningplayfieldlastonlinetime;
/*    */   }
/*    */   
/*    */   public Integer8 getClazz() {
/* 91 */     return this.clazz;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\BuddyAdded.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */