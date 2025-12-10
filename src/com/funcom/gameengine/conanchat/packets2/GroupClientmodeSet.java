/*    */ package com.funcom.gameengine.conanchat.packets2;
/*    */ 
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
/*    */ public class GroupClientmodeSet
/*    */   implements ChatMessage
/*    */ {
/*    */   private Integer40 groupid;
/*    */   private Integer32 clientid;
/*    */   private Integer32 adminLevel;
/*    */   private Integer32 orflags;
/*    */   private Integer32 andflags;
/*    */   
/*    */   public GroupClientmodeSet() {}
/*    */   
/*    */   public GroupClientmodeSet(Integer40 groupid, Integer32 clientid, Integer32 adminLevel, Integer32 orflags, Integer32 andflags) {
/* 30 */     this.groupid = groupid;
/* 31 */     this.clientid = clientid;
/* 32 */     this.adminLevel = adminLevel;
/* 33 */     this.orflags = orflags;
/* 34 */     this.andflags = andflags;
/*    */   }
/*    */   
/*    */   public GroupClientmodeSet(ByteBuffer byteBuffer) {
/* 38 */     this.groupid = new Integer40(byteBuffer, Endianess.BIG_ENDIAN);
/* 39 */     this.clientid = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/* 40 */     this.adminLevel = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/* 41 */     this.orflags = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/* 42 */     this.andflags = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 47 */     return 66;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 52 */     return new GroupClientmodeSet(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 57 */     return this.groupid.getSizeInBytes() + this.clientid.getSizeInBytes() + this.adminLevel.getSizeInBytes() + this.orflags.getSizeInBytes() + this.andflags.getSizeInBytes();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 66 */     this.groupid.toByteBuffer(buffer);
/* 67 */     this.clientid.toByteBuffer(buffer);
/* 68 */     this.adminLevel.toByteBuffer(buffer);
/* 69 */     this.orflags.toByteBuffer(buffer);
/* 70 */     this.andflags.toByteBuffer(buffer);
/* 71 */     return buffer;
/*    */   }
/*    */   
/*    */   public Integer40 getGroupid() {
/* 75 */     return this.groupid;
/*    */   }
/*    */   
/*    */   public Integer32 getClientid() {
/* 79 */     return this.clientid;
/*    */   }
/*    */   
/*    */   public Integer32 getAdminLevel() {
/* 83 */     return this.adminLevel;
/*    */   }
/*    */   
/*    */   public Integer32 getOrflags() {
/* 87 */     return this.orflags;
/*    */   }
/*    */   
/*    */   public Integer32 getAndflags() {
/* 91 */     return this.andflags;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\GroupClientmodeSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */