/*    */ package com.funcom.gameengine.conanchat.packets2;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.datatypes.Data;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Endianess;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Integer32;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Integer40;
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
/*    */ public class GroupDataSet
/*    */   implements ChatMessage
/*    */ {
/*    */   private Integer40 groupid;
/*    */   private Integer32 flags;
/*    */   private Data extraData;
/*    */   
/*    */   public GroupDataSet() {}
/*    */   
/*    */   public GroupDataSet(Integer40 groupid, Integer32 flags, Data extraData) {
/* 29 */     this.groupid = groupid;
/* 30 */     this.flags = flags;
/* 31 */     this.extraData = extraData;
/*    */   }
/*    */   
/*    */   public GroupDataSet(ByteBuffer byteBuffer) {
/* 35 */     this.groupid = new Integer40(byteBuffer, Endianess.BIG_ENDIAN);
/* 36 */     this.flags = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/* 37 */     this.extraData = new Data(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 42 */     return 64;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 47 */     return new GroupDataSet(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 52 */     return this.groupid.getSizeInBytes() + this.flags.getSizeInBytes() + this.extraData.getSizeInBytes();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 59 */     this.groupid.toByteBuffer(buffer);
/* 60 */     this.flags.toByteBuffer(buffer);
/* 61 */     this.extraData.toByteBuffer(buffer);
/* 62 */     return buffer;
/*    */   }
/*    */   
/*    */   public Integer40 getGroupid() {
/* 66 */     return this.groupid;
/*    */   }
/*    */   
/*    */   public Integer32 getFlags() {
/* 70 */     return this.flags;
/*    */   }
/*    */   
/*    */   public Data getExtraData() {
/* 74 */     return this.extraData;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\GroupDataSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */