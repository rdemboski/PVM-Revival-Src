/*    */ package com.funcom.gameengine.conanchat.packets2;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.datatypes.Endianess;
/*    */ import com.funcom.gameengine.conanchat.datatypes.Integer32;
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
/*    */ public class LoginResponseNick
/*    */   implements ChatMessage
/*    */ {
/*    */   private Integer32 protocolVersion;
/*    */   private StringDatatype clientnick;
/*    */   private Integer32 cookie;
/*    */   
/*    */   public LoginResponseNick() {}
/*    */   
/*    */   public LoginResponseNick(Integer32 protocolVersion, StringDatatype clientnick, Integer32 cookie) {
/* 28 */     this.protocolVersion = protocolVersion;
/* 29 */     this.clientnick = clientnick;
/* 30 */     this.cookie = cookie;
/*    */   }
/*    */   
/*    */   public LoginResponseNick(ByteBuffer byteBuffer) {
/* 34 */     this.protocolVersion = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/* 35 */     this.clientnick = new StringDatatype(byteBuffer, Endianess.BIG_ENDIAN);
/* 36 */     this.cookie = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 41 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 46 */     return new LoginResponseNick(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 51 */     return this.protocolVersion.getSizeInBytes() + this.clientnick.getSizeInBytes() + this.cookie.getSizeInBytes();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 58 */     this.protocolVersion.toByteBuffer(buffer);
/* 59 */     this.clientnick.toByteBuffer(buffer);
/* 60 */     this.cookie.toByteBuffer(buffer);
/* 61 */     return buffer;
/*    */   }
/*    */   
/*    */   public Integer32 getProtocolVersion() {
/* 65 */     return this.protocolVersion;
/*    */   }
/*    */   
/*    */   public StringDatatype getClientnick() {
/* 69 */     return this.clientnick;
/*    */   }
/*    */   
/*    */   public Integer32 getCookie() {
/* 73 */     return this.cookie;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\LoginResponseNick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */