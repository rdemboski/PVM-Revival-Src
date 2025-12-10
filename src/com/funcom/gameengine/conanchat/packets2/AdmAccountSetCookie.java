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
/*    */ public class AdmAccountSetCookie
/*    */   implements ChatMessage
/*    */ {
/*    */   private Integer32 clientid;
/*    */   private Integer32 cookie;
/*    */   private Integer32 clientip;
/*    */   private StringDatatype nickname;
/*    */   private StringDatatype eMail;
/*    */   
/*    */   public AdmAccountSetCookie() {}
/*    */   
/*    */   public AdmAccountSetCookie(Integer32 clientid, Integer32 cookie, Integer32 clientip, StringDatatype nickname, StringDatatype eMail) {
/* 30 */     this.clientid = clientid;
/* 31 */     this.cookie = cookie;
/* 32 */     this.clientip = clientip;
/* 33 */     this.nickname = nickname;
/* 34 */     this.eMail = eMail;
/*    */   }
/*    */   
/*    */   public AdmAccountSetCookie(ByteBuffer byteBuffer) {
/* 38 */     this.clientid = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/* 39 */     this.cookie = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/* 40 */     this.clientip = new Integer32(byteBuffer, Endianess.BIG_ENDIAN);
/* 41 */     this.nickname = new StringDatatype(byteBuffer, Endianess.BIG_ENDIAN);
/* 42 */     this.eMail = new StringDatatype(byteBuffer, Endianess.BIG_ENDIAN);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 47 */     return 1009;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatMessage toMessage(ByteBuffer buffer) {
/* 52 */     return new AdmAccountSetCookie(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 57 */     return this.clientid.getSizeInBytes() + this.cookie.getSizeInBytes() + this.clientip.getSizeInBytes() + this.nickname.getSizeInBytes() + this.eMail.getSizeInBytes();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 66 */     this.clientid.toByteBuffer(buffer);
/* 67 */     this.cookie.toByteBuffer(buffer);
/* 68 */     this.clientip.toByteBuffer(buffer);
/* 69 */     this.nickname.toByteBuffer(buffer);
/* 70 */     this.eMail.toByteBuffer(buffer);
/* 71 */     return buffer;
/*    */   }
/*    */   
/*    */   public Integer32 getClientid() {
/* 75 */     return this.clientid;
/*    */   }
/*    */   
/*    */   public Integer32 getCookie() {
/* 79 */     return this.cookie;
/*    */   }
/*    */   
/*    */   public Integer32 getClientip() {
/* 83 */     return this.clientip;
/*    */   }
/*    */   
/*    */   public StringDatatype getNickname() {
/* 87 */     return this.nickname;
/*    */   }
/*    */   
/*    */   public StringDatatype getEMail() {
/* 91 */     return this.eMail;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\packets2\AdmAccountSetCookie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */