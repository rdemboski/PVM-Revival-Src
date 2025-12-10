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
/*    */ public class AdmGroupPart
/*    */   implements ChatMessage
/*    */ {
/*    */   private Integer40 groupid;
/*    */   private Integer32 clientid;
/*    */   
/*    */   public AdmGroupPart() {}
/*    */   
/*    */   public AdmGroupPart(Integer40 groupid, Integer32 clientid) {
/* 27 */     this.groupid = groupid;
/* 28 */     this.clientid = clientid;
/*    */   }
/*    */   
/*    */   public AdmGroupPart(ByteBuffer byteBuffer) {
/* 32 */     this.groupid = new Integer40(byteBuffer, Endianess.BIG_ENDIAN);
/* 33 */     this.clientid = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 38 */     return 1025;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 43 */     return new AdmGroupPart(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 48 */     return this.groupid.getSizeInBytes() + this.clientid.getSizeInBytes();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 54 */     this.groupid.toByteBuffer(buffer);
/* 55 */     this.clientid.toByteBuffer(buffer);
/* 56 */     return buffer;
/*    */   }
/*    */   
/*    */   public Integer40 getGroupid() {
/* 60 */     return this.groupid;
/*    */   }
/*    */   
/*    */   public Integer32 getClientid() {
/* 64 */     return this.clientid;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\AdmGroupPart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */