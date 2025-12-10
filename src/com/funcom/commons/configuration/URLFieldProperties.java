/*     */ package com.funcom.commons.configuration;
/*     */ 
/*     */ import java.beans.PropertyEditor;
/*     */ import java.beans.PropertyEditorManager;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.lang.reflect.Field;
/*     */ import java.net.URL;
/*     */ import java.util.Properties;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ public class URLFieldProperties {
/*  17 */   private static final Logger LOGGER = Logger.getLogger(URLFieldProperties.class);
/*     */   
/*     */   private final URL url;
/*     */   private final Class<?> target;
/*     */   
/*     */   public URLFieldProperties(URL url, Class<?> target) {
/*  23 */     this.url = url;
/*  24 */     this.target = target;
/*     */     try {
/*  26 */       refresh();
/*  27 */     } catch (PropertyException e) {
/*  28 */       throw new IllegalStateException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized void refresh() throws PropertyException {
/*  33 */     BufferedReader reader = null;
/*     */     try {
/*  35 */       reader = new BufferedReader(new InputStreamReader(this.url.openStream()));
/*     */       
/*  37 */       Properties tmp = new Properties();
/*  38 */       tmp.load(reader);
/*  39 */       if (LOGGER.isEnabledFor((Priority)Level.DEBUG)) {
/*  40 */         LOGGER.debug("Properties loaded");
/*     */       }
/*  42 */       parseTargetProperties(tmp);
/*  43 */       assignTargetProperties(tmp);
/*  44 */       if (LOGGER.isEnabledFor((Priority)Level.INFO))
/*  45 */         LOGGER.info("Properties refreshed: " + this.target.getName()); 
/*  46 */     } catch (Exception e) {
/*  47 */       throw new PropertyException(e);
/*     */     } finally {
/*  49 */       close(reader);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void parseTargetProperties(Properties src) throws IllegalAccessException {
/*  55 */     processTargetProperties(false, src);
/*     */   }
/*     */ 
/*     */   
/*     */   private void assignTargetProperties(Properties src) throws IllegalAccessException {
/*  60 */     processTargetProperties(true, src);
/*     */   }
/*     */   
/*     */   private void processTargetProperties(boolean write, Properties src) throws IllegalAccessException {
/*  64 */     Class<?> nextClass = this.target;
/*     */     
/*  66 */     while (nextClass != null && nextClass != Object.class) {
/*  67 */       processByClass(write, src, nextClass);
/*  68 */       nextClass = nextClass.getSuperclass();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processByClass(boolean write, Properties src, Class nextClass) throws IllegalAccessException {
/*  73 */     Field[] declaredFields = nextClass.getDeclaredFields();
/*  74 */     for (Field declaredField : declaredFields) {
/*  75 */       if (declaredField.isAnnotationPresent((Class)InjectFromProperty.class)) {
/*  76 */         InjectFromProperty injectFromProperty = declaredField.<InjectFromProperty>getAnnotation(InjectFromProperty.class);
/*     */         
/*  78 */         String propName = injectFromProperty.value();
/*  79 */         if (propName.equals("FIELD_NAME"))
/*  80 */           propName = String.format("%s.%s", new Object[] { nextClass.getSimpleName(), declaredField.getName() }); 
/*  81 */         String propValue = src.getProperty(propName);
/*     */         
/*  83 */         if (propValue == null) {
/*  84 */           if (injectFromProperty.isRequired()) {
/*  85 */             throwException("Cannot find property value", declaredField, propName, propValue);
/*     */           }
/*     */         } else {
/*     */           
/*  89 */           PropertyEditor propertyEditor = PropertyEditorManager.findEditor(declaredField.getType());
/*     */           
/*     */           try {
/*  92 */             propertyEditor.setAsText(propValue);
/*  93 */           } catch (IllegalArgumentException e) {
/*  94 */             throwException("Cannot parse value for property", declaredField, propName, propValue);
/*     */           } 
/*  96 */           if (write) {
/*  97 */             declaredField.setAccessible(true);
/*  98 */             declaredField.set(this.target, propertyEditor.getValue());
/*  99 */             if (LOGGER.isEnabledFor((Priority)Level.DEBUG)) {
/* 100 */               LOGGER.debug(String.format("Field value injected:'%s'='%s'", new Object[] { declaredField.getName(), propValue }));
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void throwException(String msg, Field declaredField, String propName, String propValue) throws IllegalStateException {
/* 110 */     String className = declaredField.getDeclaringClass().getName();
/* 111 */     throw new IllegalStateException(msg + ":" + " property={" + propName + ((propValue != null) ? ("=" + propValue) : "") + "}" + " field=" + className + "#" + declaredField.getName() + ":" + declaredField.getType().getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void close(Reader in) {
/* 119 */     if (in != null)
/*     */       try {
/* 121 */         in.close();
/* 122 */         if (LOGGER.isEnabledFor((Priority)Level.DEBUG))
/* 123 */           LOGGER.debug("Property stream closed."); 
/* 124 */       } catch (IOException ignore) {} 
/*     */   }
/*     */   
/*     */   public static class PropertyException
/*     */     extends Exception
/*     */   {
/*     */     public PropertyException(Exception e) {
/* 131 */       super(e);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\configuration\URLFieldProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */