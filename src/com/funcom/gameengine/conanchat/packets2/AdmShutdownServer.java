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
/*    */ public class AdmShutdownServer
/*    */   implements ChatMessage
/*    */ {
/*    */   private Integer32 shutdown;
/*    */   
/*    */   public AdmShutdownServer() {}
/*    */   
/*    */   public AdmShutdownServer(Integer32 shutdown) {
/* 25 */     this.shutdown = shutdown;
/*    */   }
/*    */   
/*    */   public AdmShutdownServer(ByteBuffer byteBuffer) {
/* 29 */     this.shutdown = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 34 */     return 1300;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 39 */     return new AdmShutdownServer(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 44 */     return this.shutdown.getSizeInBytes();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 49 */     this.shutdown.toByteBuffer(buffer);
/* 50 */     return buffer;
/*    */   }
/*    */   
/*    */   public Integer32 getShutdown() {
/* 54 */     return this.shutdown;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\AdmShutdownServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */