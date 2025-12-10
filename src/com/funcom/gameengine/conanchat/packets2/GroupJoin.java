/*    */ package com.funcom.gameengine.conanchat.packets2;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.datatypes.Data;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Endianess;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Integer32;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Integer40;
/*    */ import com.funcom.gameengine.conanchat.datatypes.StringDatatype;
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
/*    */ public class GroupJoin
/*    */   implements ChatMessage
/*    */ {
/*    */   private Integer40 groupid;
/*    */   private StringDatatype groupname;
/*    */   private Integer32 groupflags;
/*    */   private Data extraData;
/*    */   
/*    */   public GroupJoin() {}
/*    */   
/*    */   public GroupJoin(Integer40 groupid, StringDatatype groupname, Integer32 groupflags, Data extraData) {
/* 31 */     this.groupid = groupid;
/* 32 */     this.groupname = groupname;
/* 33 */     this.groupflags = groupflags;
/* 34 */     this.extraData = extraData;
/*    */   }
/*    */   
/*    */   public GroupJoin(ByteBuffer byteBuffer) {
/* 38 */     this.groupid = new Integer40(byteBuffer, Endianess.BIG_ENDIAN);
/* 39 */     this.groupname = new StringDatatype(byteBuffer, Endianess.BIG_ENDIAN);
/* 40 */     this.groupflags = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/* 41 */     this.extraData = new Data(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 46 */     return 60;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 51 */     return new GroupJoin(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 56 */     return this.groupid.getSizeInBytes() + this.groupname.getSizeInBytes() + this.groupflags.getSizeInBytes() + this.extraData.getSizeInBytes();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 64 */     this.groupid.toByteBuffer(buffer);
/* 65 */     this.groupname.toByteBuffer(buffer);
/* 66 */     this.groupflags.toByteBuffer(buffer);
/* 67 */     this.extraData.toByteBuffer(buffer);
/* 68 */     return buffer;
/*    */   }
/*    */   
/*    */   public Integer40 getGroupid() {
/* 72 */     return this.groupid;
/*    */   }
/*    */   
/*    */   public StringDatatype getGroupname() {
/* 76 */     return this.groupname;
/*    */   }
/*    */   
/*    */   public Integer32 getGroupflags() {
/* 80 */     return this.groupflags;
/*    */   }
/*    */   
/*    */   public Data getExtraData() {
/* 84 */     return this.extraData;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\GroupJoin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */