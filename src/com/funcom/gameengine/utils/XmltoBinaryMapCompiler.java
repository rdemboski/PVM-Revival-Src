/*     */ package com.funcom.gameengine.utils;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ import org.jdom.JDOMException;
/*     */ 
/*     */ 
/*     */ public class XmltoBinaryMapCompiler
/*     */ {
/*  18 */   private static final Logger LOGGER = Logger.getLogger(XmltoBinaryMapCompiler.class);
/*     */   
/*     */   private XmlToBinaryWriter xmlToBinaryWriter;
/*  21 */   public static final String CHUNK_DIR = System.getProperty("tcg.resourcepath") + "/xml/";
/*     */   
/*     */   public XmltoBinaryMapCompiler() {
/*  24 */     this.xmlToBinaryWriter = new XmlToBinaryWriter();
/*  25 */     this.xmlToBinaryWriter.init();
/*     */   }
/*     */   
/*     */   public boolean checkFileAge(String mapDir) throws JDOMException, IOException {
/*  29 */     boolean filesOk = true;
/*  30 */     File binaryDir = getBinaryDir(mapDir);
/*  31 */     File originalDir = getOriginalDir(mapDir);
/*  32 */     LOGGER.info("Checking binary file age on this directory: " + binaryDir);
/*     */     
/*  34 */     File[] originalFiles = originalDir.listFiles();
/*  35 */     if (originalDir != null && originalFiles != null && originalFiles.length > 0) {
/*  36 */       for (File originalFile : originalFiles) {
/*  37 */         if (isXml(originalFile)) {
/*  38 */           File checkBinaryFile = toBinaryFile(originalFile);
/*     */           
/*  40 */           boolean isOutdated = (originalFile.lastModified() != checkBinaryFile.lastModified());
/*  41 */           if (!checkBinaryFile.exists() || isOutdated) {
/*     */             
/*  43 */             LOGGER.info("Different binary files, server is recompiling......");
/*  44 */             compileMapDir(originalDir);
/*  45 */             return checkFileAge(mapDir);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/*  50 */       LOGGER.info("Missing binary files, server is recompiling......");
/*  51 */       compileMapDir(originalDir);
/*  52 */       return checkFileAge(mapDir);
/*     */     } 
/*     */     
/*  55 */     LOGGER.info("Done!");
/*  56 */     return filesOk;
/*     */   }
/*     */   
/*     */   private File toBinaryFile(File originalFile) {
/*  60 */     String filename = originalFile.getName();
/*     */     
/*  62 */     int dotIndex = filename.lastIndexOf('.');
/*  63 */     if (dotIndex >= 0) {
/*  64 */       filename = filename.substring(0, dotIndex) + ".bunk";
/*     */     }
/*  66 */     String mapId = originalFile.getParentFile().getName();
/*  67 */     return new File(XmlToBinaryWriter.BINARY_ID_FILE_LOCATION + mapId + "/" + filename);
/*     */   }
/*     */   
/*     */   private File getBinaryDir(String mapDir) {
/*  71 */     return new File(XmlToBinaryWriter.BINARY_ID_FILE_LOCATION + mapDir);
/*     */   }
/*     */ 
/*     */   
/*     */   private File getOriginalDir(String mapDir) {
/*  76 */     return new File(CHUNK_DIR, mapDir);
/*     */   }
/*     */   
/*     */   public boolean mapExists(String mapId) {
/*  80 */     return getOriginalDir(mapId).exists();
/*     */   }
/*     */   
/*     */   public void compileMapDir(File dir) throws JDOMException, IOException {
/*  84 */     if (dir.isDirectory()) {
/*  85 */       File[] files = dir.listFiles();
/*  86 */       if (files != null) {
/*  87 */         for (File file : files) {
/*     */           
/*  89 */           if (isXml(file)) {
/*  90 */             BufferedInputStream bfStream = new BufferedInputStream(new FileInputStream(file));
/*  91 */             File binaryFile = this.xmlToBinaryWriter.saveBinaryFromXml(bfStream, file);
/*  92 */             boolean setModifiedTimeSuccessful = binaryFile.setLastModified(file.lastModified());
/*  93 */             if (!setModifiedTimeSuccessful) {
/*  94 */               LOGGER.warn("cannot set last modified time for file: " + binaryFile.getAbsolutePath());
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } else {
/*  99 */         throw new RuntimeException("Incorrect mapname. Please check your spelling: " + dir.getName());
/*     */       } 
/*     */       
/* 102 */       if (copyFile(dir, "pathgraph.pthgph")) {
/* 103 */         LOGGER.info("Pathgraph copied");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean copyFile(File dir, String tailFilename) {
/* 110 */     File src = new File(dir, tailFilename);
/* 111 */     File dst = new File(getBinaryDir(dir.getName()), tailFilename);
/* 112 */     InputStream in = null;
/* 113 */     OutputStream out = null;
/*     */ 
/*     */     
/*     */     try {
/* 117 */       if (dst.exists() && !dst.delete()) {
/* 118 */         throw new IllegalStateException("Failure to create destination file, already exists and can't be deleted: " + dst);
/*     */       }
/* 120 */       if (!dst.createNewFile()) {
/* 121 */         throw new IllegalStateException("Failure to create destination file: " + dst);
/*     */       }
/*     */       
/* 124 */       in = new FileInputStream(src);
/* 125 */       out = new FileOutputStream(dst);
/* 126 */       byte[] buf = new byte[1024];
/*     */       int len;
/* 128 */       while ((len = in.read(buf)) > 0) {
/* 129 */         out.write(buf, 0, len);
/*     */       }
/* 131 */       return true;
/*     */     }
/* 133 */     catch (IOException e) {
/* 134 */       LOGGER.error("Failed to load '" + tailFilename + "', feature will be unavailable.", e);
/*     */     } finally {
/*     */       try {
/* 137 */         if (in != null)
/* 138 */           in.close(); 
/* 139 */         if (out != null)
/* 140 */           out.close(); 
/* 141 */       } catch (IOException e) {
/* 142 */         LOGGER.warn("Failed to close file copy streams!");
/*     */       } 
/*     */     } 
/* 145 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isXml(File file) {
/* 149 */     String filename = file.getName();
/* 150 */     return (file.isFile() && (filename.endsWith(".chunk") || filename.endsWith(".xml")));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws IllegalAccessException {
/* 156 */     XmltoBinaryMapCompiler xmltoBinaryMapCompiler = new XmltoBinaryMapCompiler();
/* 157 */     String firstArg = args[0];
/* 158 */     if (firstArg.equalsIgnoreCase("doALL")) {
/* 159 */       String buildDir = args[1];
/* 160 */       LOGGER.log((Priority)Level.ERROR, "buildDir: " + buildDir);
/* 161 */       File dir = new File(buildDir);
/* 162 */       File[] subDirs = dir.listFiles();
/* 163 */       LOGGER.log((Priority)Level.ERROR, "No arguments given, compiling subdirs:");
/* 164 */       if (subDirs != null) {
/* 165 */         for (File subDir : subDirs) {
/* 166 */           if (subDir != null && 
/* 167 */             !subDir.getName().equalsIgnoreCase("dfx") && !subDir.getName().equalsIgnoreCase(".svn")) {
/*     */             try {
/* 169 */               LOGGER.log((Priority)Level.ERROR, "Compiling subdir:" + buildDir + subDir.getName());
/* 170 */               xmltoBinaryMapCompiler.compileMapDir(new File(buildDir + subDir.getName()));
/* 171 */             } catch (JDOMException e) {
/* 172 */               LOGGER.error(e);
/* 173 */             } catch (IOException e) {
/* 174 */               LOGGER.error(e);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       }
/*     */     } else {
/*     */       
/* 181 */       for (String arg : args) {
/*     */         try {
/* 183 */           xmltoBinaryMapCompiler.compileMapDir(new File(arg));
/* 184 */         } catch (IOException e) {
/* 185 */           LOGGER.error(e);
/* 186 */         } catch (JDOMException e) {
/* 187 */           LOGGER.error(e);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengin\\utils\XmltoBinaryMapCompiler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */