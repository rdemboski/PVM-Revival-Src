/*    */ package com.funcom.gameengine.conanchat.packets2;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.datatypes.Endianess;
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
/*    */ public class AdmGroupDelete
/*    */   implements ChatMessage
/*    */ {
/*    */   private Integer40 groupid;
/*    */   
/*    */   public AdmGroupDelete() {}
/*    */   
/*    */   public AdmGroupDelete(Integer40 groupid) {
/* 25 */     this.groupid = groupid;
/*    */   }
/*    */   
/*    */   public AdmGroupDelete(ByteBuffer byteBuffer) {
/* 29 */     this.groupid = new Integer40(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 34 */     return 1021;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 39 */     return new AdmGroupDelete(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 44 */     return this.groupid.getSizeInBytes();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 49 */     this.groupid.toByteBuffer(buffer);
/* 50 */     return buffer;
/*    */   }
/*    */   
/*    */   public Integer40 getGroupid() {
/* 54 */     return this.groupid;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\AdmGroupDelete.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */