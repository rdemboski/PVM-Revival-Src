/*     */ package com.funcom.tcg.net.message;
/*     */ 
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.MessageUtils;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
/*     */ import javax.crypto.BadPaddingException;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.IllegalBlockSizeException;
/*     */ import javax.crypto.NoSuchPaddingException;
/*     */ import javax.crypto.ShortBufferException;
/*     */ 
/*     */ public class CreateCharacterRequestMessage implements Message {
/*     */   private static final String ALGORITHM = "RSA";
/*  18 */   private String nick = "";
/*  19 */   private String plainPassword = "";
/*  20 */   private String email = "";
/*     */   
/*     */   private String locale;
/*     */   private ByteBuffer encodedBuffer;
/*     */   
/*     */   public CreateCharacterRequestMessage() {}
/*     */   
/*     */   public CreateCharacterRequestMessage(ByteBuffer buffer) {
/*  28 */     this.encodedBuffer = ByteBuffer.allocate(512);
/*  29 */     this.encodedBuffer.put(buffer);
/*  30 */     this.encodedBuffer.flip();
/*     */   }
/*     */   
/*     */   public CreateCharacterRequestMessage(String nick, String plainPassword, String email, String locale) {
/*  34 */     this.nick = nick;
/*  35 */     this.plainPassword = plainPassword;
/*  36 */     this.email = email;
/*  37 */     this.locale = locale;
/*     */   }
/*     */   
/*     */   public Message toMessage(ByteBuffer buffer) {
/*  41 */     return new CreateCharacterRequestMessage(buffer);
/*     */   }
/*     */   
/*     */   public String getNick() {
/*  45 */     return this.nick;
/*     */   }
/*     */   
/*     */   public String getPlainPassword() {
/*  49 */     return this.plainPassword;
/*     */   }
/*     */   
/*     */   public String getEmail() {
/*  53 */     return this.email;
/*     */   }
/*     */   
/*     */   public String getLocale() {
/*  57 */     return this.locale;
/*     */   }
/*     */   
/*     */   public ByteBuffer getEncodedBuffer() {
/*  61 */     return this.encodedBuffer;
/*     */   }
/*     */   
/*     */   public short getMessageType() {
/*  65 */     return 3;
/*     */   }
/*     */   
/*     */   public void encrypt(PublicKey publicKey) {
/*  69 */     ByteBuffer temp = ByteBuffer.allocate(512);
/*  70 */     this.encodedBuffer = ByteBuffer.allocate(512);
/*     */     
/*  72 */     MessageUtils.writeStr(temp, this.nick);
/*  73 */     MessageUtils.writeStr(temp, this.plainPassword);
/*  74 */     MessageUtils.writeStr(temp, this.email);
/*  75 */     MessageUtils.writeStr(temp, this.locale);
/*  76 */     temp.flip();
/*     */     
/*     */     try {
/*  79 */       Cipher inst = Cipher.getInstance("RSA");
/*  80 */       inst.init(1, publicKey);
/*  81 */       inst.doFinal(temp, this.encodedBuffer);
/*  82 */       this.encodedBuffer.flip();
/*     */     }
/*  84 */     catch (NoSuchAlgorithmException e) {
/*  85 */       throw new IllegalStateException(e);
/*  86 */     } catch (NoSuchPaddingException e) {
/*  87 */       throw new IllegalStateException(e);
/*  88 */     } catch (InvalidKeyException e) {
/*  89 */       throw new IllegalStateException(e);
/*  90 */     } catch (ShortBufferException e) {
/*  91 */       throw new IllegalStateException(e);
/*  92 */     } catch (IllegalBlockSizeException e) {
/*  93 */       throw new IllegalStateException(e);
/*  94 */     } catch (BadPaddingException e) {
/*  95 */       throw new IllegalStateException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void decrypt(PrivateKey privateKey) {
/* 100 */     ByteBuffer temp = ByteBuffer.allocate(512);
/*     */     
/*     */     try {
/* 103 */       Cipher inst = Cipher.getInstance("RSA");
/* 104 */       inst.init(2, privateKey);
/* 105 */       inst.doFinal(this.encodedBuffer, temp);
/* 106 */       temp.flip();
/*     */       
/* 108 */       this.nick = MessageUtils.readStr(temp);
/* 109 */       this.plainPassword = MessageUtils.readStr(temp);
/* 110 */       this.email = MessageUtils.readStr(temp);
/* 111 */       this.locale = MessageUtils.readStr(temp);
/*     */     }
/* 113 */     catch (NoSuchAlgorithmException e) {
/* 114 */       throw new IllegalStateException(e);
/* 115 */     } catch (NoSuchPaddingException e) {
/* 116 */       throw new IllegalStateException(e);
/* 117 */     } catch (InvalidKeyException e) {
/* 118 */       throw new IllegalStateException(e);
/* 119 */     } catch (ShortBufferException e) {
/* 120 */       throw new IllegalStateException(e);
/* 121 */     } catch (IllegalBlockSizeException e) {
/* 122 */       throw new IllegalStateException(e);
/* 123 */     } catch (BadPaddingException e) {
/* 124 */       throw new IllegalStateException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getSerializedSize() {
/* 129 */     if (this.encodedBuffer == null) {
/* 130 */       throw new IllegalArgumentException("encodedBuffer = null, message has to be encrypted.");
/*     */     }
/* 132 */     return this.encodedBuffer.limit();
/*     */   }
/*     */   
/*     */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 136 */     buffer.put(this.encodedBuffer);
/* 137 */     return buffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 142 */     return "CreateCharacterRequestMessage{nick='" + this.nick + '\'' + ", plainPassword='" + this.plainPassword + '\'' + ", email='" + this.email + '\'' + ", locale='" + this.locale + '\'' + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\CreateCharacterRequestMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */