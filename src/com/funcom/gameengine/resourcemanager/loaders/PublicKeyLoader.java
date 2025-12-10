/*    */ package com.funcom.gameengine.resourcemanager.loaders;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.LoadException;
/*    */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.security.KeyFactory;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ import java.security.PublicKey;
/*    */ import java.security.spec.InvalidKeySpecException;
/*    */ import java.security.spec.X509EncodedKeySpec;
/*    */ 
/*    */ 
/*    */ public class PublicKeyLoader
/*    */   extends AbstractLoader
/*    */ {
/*    */   public PublicKeyLoader() {
/* 19 */     super(PublicKey.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadData(ManagedResource<?> managedResource) throws LoadException {
/*    */     try {
/* 25 */       InputStream inputStream = getFileInputStream(managedResource);
/* 26 */       byte[] encodedPublicKey = getByteArray(inputStream);
/*    */       
/* 28 */       KeyFactory keyFactory = KeyFactory.getInstance("RSA");
/* 29 */       X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
/* 30 */       PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
/* 31 */       managedResource.setResource(publicKey);
/* 32 */     } catch (IOException e) {
/* 33 */       e.printStackTrace();
/* 34 */     } catch (InvalidKeySpecException e) {
/* 35 */       e.printStackTrace();
/* 36 */     } catch (NoSuchAlgorithmException e) {
/* 37 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public byte[] getByteArray(InputStream inputStream) throws IOException {
/* 42 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
/* 43 */     byte[] buffer = new byte[1024];
/*    */     
/*    */     int numberOfBytesRead;
/*    */     
/* 47 */     while ((numberOfBytesRead = inputStream.read(buffer)) > 0) {
/* 48 */       byteArrayOutputStream.write(buffer, 0, numberOfBytesRead);
/*    */     }
/*    */     
/* 51 */     byte[] returnArray = byteArrayOutputStream.toByteArray();
/* 52 */     byteArrayOutputStream.close();
/*    */     
/* 54 */     return returnArray;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\PublicKeyLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */