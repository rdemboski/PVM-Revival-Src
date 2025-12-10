/*     */ package com.funcom.commons.configuration;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.Properties;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class ExtProperties
/*     */   extends Properties
/*     */ {
/*  12 */   private static final Logger LOGGER = Logger.getLogger(ExtProperties.class);
/*     */ 
/*     */   
/*     */   public ExtProperties() {}
/*     */   
/*     */   public ExtProperties(Properties defaults) {
/*  18 */     super(defaults);
/*     */   }
/*     */   
/*     */   public void setProperty(String key, int value) {
/*  22 */     setProperty(key, Integer.toString(value));
/*     */   }
/*     */   
/*     */   public int getInt(String key) {
/*  26 */     return Integer.parseInt(getProperty(key));
/*     */   }
/*     */   
/*     */   public int getInt(String key, int defaultValue) {
/*  30 */     String val = getProperty(key);
/*  31 */     if (val != null) {
/*  32 */       return Integer.parseInt(val.trim());
/*     */     }
/*     */     
/*  35 */     return defaultValue;
/*     */   }
/*     */   
/*     */   public long getLong(String key) {
/*  39 */     return Long.parseLong(getProperty(key));
/*     */   }
/*     */   
/*     */   public long getLong(String key, long defaultValue) {
/*  43 */     String val = getProperty(key);
/*  44 */     if (val != null) {
/*  45 */       return Long.parseLong(val);
/*     */     }
/*     */     
/*  48 */     return defaultValue;
/*     */   }
/*     */   
/*     */   public float getFloat(String key) {
/*  52 */     return Float.parseFloat(getProperty(key));
/*     */   }
/*     */   
/*     */   public float getFloat(String key, float defaultValue) {
/*  56 */     String val = getProperty(key);
/*  57 */     if (val != null) {
/*  58 */       return Float.parseFloat(val);
/*     */     }
/*     */     
/*  61 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getBoolean(String key, boolean def) {
/*  66 */     String val = getProperty(key);
/*  67 */     if (val != null) {
/*  68 */       return Boolean.parseBoolean(val);
/*     */     }
/*  70 */     return def;
/*     */   }
/*     */   
/*     */   public boolean getBoolean(String key) {
/*  74 */     String val = getProperty(key);
/*  75 */     if (val != null) {
/*  76 */       return Boolean.parseBoolean(val);
/*     */     }
/*  78 */     throw new IllegalArgumentException("cannot find property: " + key);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProperty(Enum anEnum, String value) {
/*  83 */     put(anEnum, value);
/*     */   }
/*     */   
/*     */   public String getProperty(Enum anEnum) {
/*  87 */     return (String)get(anEnum);
/*     */   }
/*     */   
/*     */   public int getInt(Enum anEnum) {
/*  91 */     return Integer.parseInt(getProperty(anEnum));
/*     */   }
/*     */   
/*     */   public int getInt(Enum anEnum, int defaultValue) {
/*  95 */     String val = getProperty(anEnum);
/*  96 */     if (val != null) {
/*  97 */       return Integer.parseInt(val);
/*     */     }
/*     */     
/* 100 */     return defaultValue;
/*     */   }
/*     */   
/*     */   public double getDouble(Enum anEnum) {
/* 104 */     return Double.parseDouble(getProperty(anEnum));
/*     */   }
/*     */   
/*     */   public long getLong(Enum anEnum) {
/* 108 */     return Long.parseLong(getProperty(anEnum));
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFloat(Enum anEnum) {
/* 113 */     return Float.parseFloat(getProperty(anEnum));
/*     */   }
/*     */   
/*     */   public void loadFromURL(URL propertiesUrl) throws IOException {
/* 117 */     InputStream inputStream = null;
/*     */     try {
/* 119 */       inputStream = propertiesUrl.openStream();
/* 120 */       load(inputStream);
/*     */     } finally {
/*     */       
/* 123 */       if (inputStream != null)
/*     */         try {
/* 125 */           inputStream.close();
/* 126 */         } catch (IOException e) {
/* 127 */           LOGGER.error("Failed to close stream!", e);
/*     */         }  
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\configuration\ExtProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */