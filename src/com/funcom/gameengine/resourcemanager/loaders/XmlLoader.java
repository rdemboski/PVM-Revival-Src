/*     */ package com.funcom.gameengine.resourcemanager.loaders;
/*     */ import com.funcom.gameengine.resourcemanager.LoadException;
/*     */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.JDOMException;
import org.jdom.input.BuilderErrorHandler;
/*     */ import org.jdom.input.SAXBuilder;
/*     */ import org.jdom.input.SAXHandler;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.DTDHandler;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.XMLReader;
/*     */ 
/*     */ public class XmlLoader extends AbstractLoader {
/*  21 */   private static final Logger LOG = Logger.getLogger(XmlLoader.class.getName());
/*     */   
/*  23 */   private static final ThreadLocal<SAXBuilder> localBuilder = new ThreadLocal<SAXBuilder>();
/*     */   
/*     */   public XmlLoader() {
/*  26 */     super(Document.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
            @SuppressWarnings("unchecked")
            public void loadData(ManagedResource<?> managedResource) throws LoadException {
                InputStream inputStream = null;
                try {
                    inputStream = getFileInputStream(managedResource.getName());
                    Document document = createDocument(inputStream);
                    ((ManagedResource<Document>) managedResource).setResource(document);
                } catch (IOException e) {
                    throw new LoadException(getResourceManager(), managedResource, e);
                } catch (JDOMException e) {
                    throw new LoadException(getResourceManager(), managedResource, e);
                } finally {
                    closeSafely(inputStream, managedResource);
                } 
            }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Document createDocument(InputStream inputStream) throws IOException, JDOMException {
/*  57 */     SAXBuilder saxBuilder = localBuilder.get();
/*  58 */     if (saxBuilder == null) {
/*  59 */       saxBuilder = new SimpleSAXBuilder();
/*  60 */       saxBuilder.setValidation(false);
/*  61 */       saxBuilder.setReuseParser(true);
/*  62 */       localBuilder.set(saxBuilder);
/*     */     } 
/*  64 */     return saxBuilder.build(inputStream);
/*     */   }
/*     */   
/*     */   private static class SimpleSAXBuilder extends SAXBuilder { private SimpleSAXBuilder() {}
/*     */     
/*     */     protected void configureParser(XMLReader parser, SAXHandler contentHandler) throws JDOMException {
/*  70 */       parser.setContentHandler((ContentHandler)contentHandler);
/*     */       
/*  72 */       if (getEntityResolver() != null) {
/*  73 */         parser.setEntityResolver(getEntityResolver());
/*     */       }
/*     */       
/*  76 */       if (getDTDHandler() != null) {
/*  77 */         parser.setDTDHandler(getDTDHandler());
/*     */       } else {
/*  79 */         parser.setDTDHandler((DTDHandler)contentHandler);
/*     */       } 
/*     */       
/*  82 */       if (getErrorHandler() != null) {
/*  83 */         parser.setErrorHandler(getErrorHandler());
/*     */       } else {
/*  85 */         parser.setErrorHandler((ErrorHandler)new BuilderErrorHandler());
/*     */       } 
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws Exception {}
/*     */ 
/*     */ 
/*     */   
/*     */   private static void test() throws IOException, JDOMException {
/*  97 */     File file = null;
/*     */ 
/*     */ 
/*     */     
/* 101 */     long len = file.length();
/* 102 */     byte[] data = new byte[(int)len];
/* 103 */     DataInputStream in = new DataInputStream(new FileInputStream(file));
/* 104 */     in.readFully(data);
/* 105 */     in.close();
/* 106 */     ByteArrayInputStream bin = new ByteArrayInputStream(data);
/*     */ 
/*     */ 
/*     */     
/* 110 */     System.out.println("Warmup...");
/* 111 */     int loops = 5000;
/* 112 */     for (int i = 0; i < loops; i++) {
/* 113 */       bin.reset();
/* 114 */       Document document = testOld(bin);
/* 115 */       bin.reset();
/* 116 */       document = testNew(bin);
/*     */     } 
/* 118 */     System.out.println("...finish warmup...");
/*     */ 
/*     */     
/* 121 */     long start = System.nanoTime();
/* 122 */     for (int j = 0; j < loops; j++) {
/* 123 */       bin.reset();
/* 124 */       Document document = testOld(bin);
/*     */     } 
/* 126 */     long oldUsed = System.nanoTime() - start;
/* 127 */     System.out.println("old: " + (oldUsed / 1.0E9D / loops));
/*     */ 
/*     */     
/* 130 */     start = System.nanoTime();
/* 131 */     for (int k = 0; k < loops; k++) {
/* 132 */       bin.reset();
/* 133 */       Document document = testNew(bin);
/*     */     } 
/* 135 */     long newUsed = System.nanoTime() - start;
/* 136 */     System.out.println("new: " + (newUsed / 1.0E9D / loops));
/*     */   }
/*     */   
/*     */   private static Document testNew(InputStream bin) throws JDOMException, IOException {
/* 140 */     return createDocument(bin);
/*     */   }
/*     */   
/*     */   private static Document testOld(InputStream bin) throws JDOMException, IOException {
/* 144 */     SAXBuilder saxBuilder = new SAXBuilder();
/* 145 */     return saxBuilder.build(bin);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\XmlLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */