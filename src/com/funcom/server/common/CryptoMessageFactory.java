/*     */ package com.funcom.server.common;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
import java.util.List;

/*     */ import javax.crypto.BadPaddingException;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.IllegalBlockSizeException;
/*     */ import javax.crypto.NoSuchPaddingException;
/*     */ import javax.crypto.ShortBufferException;

import org.apache.log4j.Logger;
/*     */ 
/*     */ public class CryptoMessageFactory extends BaseMessageFactory {
/*  14 */   private static final Logger LOGGER = Logger.getLogger(CryptoMessageFactory.class);
/*     */   
/*     */   private static final String ALGORITHM = "RSA";
/*     */   
/*     */   private static final int ALGORITHM_LIMIT = 245;
/*     */   
/*     */   private static final int ALGORITHM_BUFFER_SIZE = 256;
/*     */   
/*     */   private final CryptoRole role;
/*     */   private final PrivateKey privateKey;
/*     */   private Cipher inst;
/*     */   
/*     */   public CryptoMessageFactory(IOBufferManager bufferManager, int bufferSize, List<Class<? extends Message>> messageClasses, CryptoRole role, PrivateKey privateKey, PublicKey publicKey) {
/*  27 */     super(bufferManager, bufferSize, messageClasses);
/*  28 */     this.role = role;
/*  29 */     this.privateKey = privateKey;
/*     */     
/*  31 */     if (role == CryptoRole.CLIENT) {
/*     */       try {
/*  33 */         this.inst = Cipher.getInstance("RSA");
/*  34 */         this.inst.init(1, publicKey);
/*  35 */       } catch (NoSuchAlgorithmException e) {
/*  36 */         throw new IllegalStateException(e);
/*  37 */       } catch (NoSuchPaddingException e) {
/*  38 */         throw new IllegalStateException(e);
/*  39 */       } catch (InvalidKeyException e) {
/*  40 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected Message toCustomMessage(short msgID, ByteBuffer buffer) {
/*  47 */     Message prototype = (Message)this.prototypes.get(msgID);
/*     */     
/*  49 */     if (prototype != null) {
/*  50 */       if (prototype.getClass().isAnnotationPresent((Class)EncryptedByClientHint.class) && 
/*  51 */         this.role == CryptoRole.SERVER) {
/*  52 */         if (LOGGER.isDebugEnabled()) {
/*  53 */           LOGGER.debug("decrypting: messageClass=" + prototype.getClass());
/*     */         }
/*  55 */         buffer = decrypt(buffer);
/*     */       } 
/*     */       
/*  58 */       return prototype.toMessage(buffer);
/*     */     } 
/*     */     
/*  61 */     return null;
/*     */   }
/*     */   
/*     */   private ByteBuffer decrypt(ByteBuffer inBuffer) {
/*  65 */     ByteBuffer out = ByteBuffer.allocate(256);
/*     */     try {
/*  67 */       Cipher inst = Cipher.getInstance("RSA");
/*  68 */       inst.init(2, this.privateKey);
/*  69 */       inst.doFinal(inBuffer, out);
/*  70 */       out.flip();
/*     */       
/*  72 */       return out;
/*  73 */     } catch (NoSuchAlgorithmException e) {
/*  74 */       throw new IllegalStateException(e);
/*  75 */     } catch (NoSuchPaddingException e) {
/*  76 */       throw new IllegalStateException(e);
/*  77 */     } catch (InvalidKeyException e) {
/*  78 */       throw new IllegalStateException(e);
/*  79 */     } catch (ShortBufferException e) {
/*  80 */       throw new IllegalStateException(e);
/*  81 */     } catch (IllegalBlockSizeException e) {
/*  82 */       throw new IllegalStateException(e);
/*  83 */     } catch (BadPaddingException e) {
/*  84 */       throw new IllegalStateException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected ByteBuffer append(Message message, int size) {
/*  90 */     if (message.getClass().isAnnotationPresent((Class)EncryptedByClientHint.class) && 
/*  91 */       this.role == CryptoRole.CLIENT) {
/*  92 */       if (LOGGER.isDebugEnabled()) {
/*  93 */         LOGGER.debug("encrypting: messageClass=" + message.getClass());
/*     */       }
/*  95 */       return encrypt(message, size);
/*     */     } 
/*     */ 
/*     */     
/*  99 */     return super.append(message, size);
/*     */   }
/*     */   
/*     */   public ByteBuffer encrypt(Message message, int size) {
/* 103 */     if (size > 245) {
/* 104 */       throw new RuntimeException("Cannot encrypt message, too large: class=" + message.getClass() + " toString=" + message);
/*     */     }
/*     */     
/* 107 */     ByteBuffer out = getWriteBuffer(258);
/* 108 */     out.putShort(message.getMessageType());
/*     */     
/* 110 */     ByteBuffer tmp = ByteBuffer.allocate(size);
/* 111 */     tmp = message.serialize(tmp);
/* 112 */     tmp.flip();
/*     */     
/*     */     try {
/* 115 */       this.inst.doFinal(tmp, out);
/*     */       
/* 117 */       return out;
/* 118 */     } catch (ShortBufferException e) {
/* 119 */       throw new IllegalStateException(e);
/* 120 */     } catch (IllegalBlockSizeException e) {
/* 121 */       throw new IllegalStateException(e);
/* 122 */     } catch (BadPaddingException e) {
/* 123 */       throw new IllegalStateException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public enum CryptoRole {
/* 128 */     SERVER, CLIENT;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\common\CryptoMessageFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */