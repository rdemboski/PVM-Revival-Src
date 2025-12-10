/*    */ package com.funcom.gameengine.conanchat.packets2;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.datatypes.Endianess;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Integer32;
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
/*    */ public class AdmAccountDisable
/*    */   implements ChatMessage
/*    */ {
/*    */   private Integer32 clientid;
/*    */   
/*    */   public AdmAccountDisable() {}
/*    */   
/*    */   public AdmAccountDisable(Integer32 clientid) {
/* 25 */     this.clientid = clientid;
/*    */   }
/*    */   
/*    */   public AdmAccountDisable(ByteBuffer byteBuffer) {
/* 29 */     this.clientid = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 34 */     return 1004;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 39 */     return new AdmAccountDisable(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 44 */     return this.clientid.getSizeInBytes();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 49 */     this.clientid.toByteBuffer(buffer);
/* 50 */     return buffer;
/*    */   }
/*    */   
/*    */   public Integer32 getClientid() {
/* 54 */     return this.clientid;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\AdmAccountDisable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */