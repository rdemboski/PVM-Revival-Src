/*     */ package com.funcom.gameengine.resourcemanager.loaders;
/*     */ 
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.jdom.Content;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BinaryLoader
/*     */ {
/*  25 */   private static final Logger LOG = Logger.getLogger(BinaryLoader.class.getName());
/*     */   
/*  27 */   private static ArrayList<String> binaryIds = new ArrayList<String>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Document convertBlobToMap(ByteBuffer blob, ResourceGetter resourceGetter) {
/*  33 */     LOG.debug("Loading binary XML...");
/*  34 */     ByteArrayInputStream bais = null;
/*     */     try {
/*  36 */       loadBinaryTags(resourceGetter);
/*  37 */       bais = new ByteArrayInputStream(blob.array());
/*  38 */       DataInputStream dataInputStream = new DataInputStream(bais);
/*  39 */       return createDocument(dataInputStream);
/*  40 */     } catch (IOException e) {
/*  41 */       throw new IllegalStateException("Failed to load binary XML!", e);
/*     */     } finally {
/*     */       try {
/*  44 */         if (bais != null)
/*  45 */           bais.close(); 
/*  46 */       } catch (IOException e) {
/*  47 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadBinaryTags(ResourceGetter resourceGetter) throws IOException {
/*  53 */     if (binaryIds.isEmpty()) {
/*  54 */       ByteBuffer blob = resourceGetter.getBlob("binary/binaryIds.bunk");
/*     */ 
/*     */ 
/*     */       
/*  58 */       ByteArrayInputStream bais = new ByteArrayInputStream(blob.array());
/*  59 */       DataInputStream dataInputStream = new DataInputStream(bais);
/*     */       
/*  61 */       SortedMap<Integer, String> sortedIds = new TreeMap<Integer, String>();
/*     */       
/*  63 */       int binaryIdSize = dataInputStream.readInt();
/*  64 */       for (int i = 0; i < binaryIdSize; i++) {
/*  65 */         String tagName = dataInputStream.readUTF();
/*  66 */         int id = dataInputStream.readInt();
/*  67 */         sortedIds.put(Integer.valueOf(id), tagName);
/*     */       } 
/*  69 */       binaryIds.addAll(sortedIds.values());
/*  70 */       dataInputStream.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Document createDocument(DataInputStream dataInputStream) throws IOException {
/*  75 */     int rootElementTagId = dataInputStream.readInt();
/*  76 */     String rootTag = binaryIds.get(rootElementTagId);
/*  77 */     Element rootElement = new Element(rootTag);
/*  78 */     Document document = new Document(rootElement);
/*     */     
/*  80 */     int numberOfElementChildren = dataInputStream.readInt();
/*  81 */     if (numberOfElementChildren > 0) {
/*  82 */       readChildData(dataInputStream, rootElement, numberOfElementChildren);
/*     */     }
/*  84 */     return document;
/*     */   }
/*     */   
/*     */   private static void readChildData(DataInputStream dataInputStream, Element parentElement, int numberOfChildElements) throws IOException {
/*     */     try {
/*  89 */       for (int a = 0; a < numberOfChildElements; a++) {
/*  90 */         int childTagId = dataInputStream.readInt();
/*  91 */         String childTag = binaryIds.get(childTagId);
/*  92 */         Element childElement = new Element(childTag);
/*  93 */         parentElement.addContent((Content)childElement);
/*     */         
/*  95 */         int numberOfChildAttributes = dataInputStream.readInt();
/*  96 */         if (numberOfChildAttributes > 0) {
/*  97 */           for (int j = 0; j < numberOfChildAttributes; j++) {
/*  98 */             int attributeId = dataInputStream.readInt();
/*  99 */             String attributeName = binaryIds.get(attributeId);
/* 100 */             String attributeValue = dataInputStream.readUTF();
/* 101 */             childElement.setAttribute(attributeName, attributeValue);
/*     */           } 
/*     */         }
/*     */         
/* 105 */         int numberOfContentElements = dataInputStream.readInt();
/* 106 */         if (numberOfContentElements > 0) {
/* 107 */           for (int k = 0; k < numberOfContentElements; k++) {
/* 108 */             String content = dataInputStream.readUTF();
/* 109 */             childElement.setText(content);
/*     */           } 
/*     */         }
/*     */         
/* 113 */         int numberOfElementChilds = dataInputStream.readInt();
/* 114 */         if (numberOfElementChilds > 0) {
/* 115 */           readChildData(dataInputStream, childElement, numberOfElementChilds);
/*     */         }
/*     */       }
/*     */     
/* 119 */     } catch (EOFException e) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\BinaryLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */