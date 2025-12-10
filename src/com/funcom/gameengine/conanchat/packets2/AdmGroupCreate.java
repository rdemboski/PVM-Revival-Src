/*    */ package com.funcom.gameengine.conanchat.packets2;
/*    */ 
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
/*    */ public class AdmGroupCreate
/*    */   implements ChatMessage
/*    */ {
/*    */   private Integer40 groupid;
/*    */   private StringDatatype groupname;
/*    */   private Integer32 groupflags;
/*    */   
/*    */   public AdmGroupCreate() {}
/*    */   
/*    */   public AdmGroupCreate(Integer40 groupid, StringDatatype groupname, Integer32 groupflags) {
/* 29 */     this.groupid = groupid;
/* 30 */     this.groupname = groupname;
/* 31 */     this.groupflags = groupflags;
/*    */   }
/*    */   
/*    */   public AdmGroupCreate(ByteBuffer byteBuffer) {
/* 35 */     this.groupid = new Integer40(byteBuffer, Endianess.BIG_ENDIAN);
/* 36 */     this.groupname = new StringDatatype(byteBuffer, Endianess.BIG_ENDIAN);
/* 37 */     this.groupflags = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 42 */     return 1020;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 47 */     return new AdmGroupCreate(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 52 */     return this.groupid.getSizeInBytes() + this.groupname.getSizeInBytes() + this.groupflags.getSizeInBytes();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 59 */     this.groupid.toByteBuffer(buffer);
/* 60 */     this.groupname.toByteBuffer(buffer);
/* 61 */     this.groupflags.toByteBuffer(buffer);
/* 62 */     return buffer;
/*    */   }
/*    */   
/*    */   public Integer40 getGroupid() {
/* 66 */     return this.groupid;
/*    */   }
/*    */   
/*    */   public StringDatatype getGroupname() {
/* 70 */     return this.groupname;
/*    */   }
/*    */   
/*    */   public Integer32 getGroupflags() {
/* 74 */     return this.groupflags;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\AdmGroupCreate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */