/*     */ package com.funcom.commons.configuration;
/*     */ 
/*     */ import java.beans.PropertyEditor;
/*     */ import java.beans.PropertyEditorManager;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class FieldProperties
/*     */ {
/*     */   private final File file;
/*     */   private final Object target;
/*     */   private String lastHashWhenRead;
/*     */   
/*     */   public FieldProperties(File file, Object target) {
/*  20 */     this.file = file;
/*  21 */     this.target = target;
/*     */   }
/*     */   
/*     */   public synchronized void refresh() throws PropertyException {
/*  25 */     FileReader reader = null;
/*     */     try {
/*  27 */       this.lastHashWhenRead = makeHash(this.file);
/*     */       
/*  29 */       reader = new FileReader(this.file);
/*     */       
/*  31 */       Properties tmp = new Properties();
/*  32 */       tmp.load(reader);
/*  33 */       parseTargetProperties(tmp);
/*  34 */       assignTargetProperties(tmp);
/*  35 */     } catch (Exception e) {
/*  36 */       throw new PropertyException(e);
/*     */     } finally {
/*  38 */       close(reader);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String makeHash(File location) {
/*  43 */     return Long.toHexString(location.length()) + "." + Long.toHexString(location.lastModified());
/*     */   }
/*     */ 
/*     */   
/*     */   private void parseTargetProperties(Properties src) throws IllegalAccessException {
/*  48 */     processTargetProperties(false, src);
/*     */   }
/*     */ 
/*     */   
/*     */   private void assignTargetProperties(Properties src) throws IllegalAccessException {
/*  53 */     processTargetProperties(true, src);
/*     */   }
/*     */   
/*     */   private void processTargetProperties(boolean write, Properties src) throws IllegalAccessException {
/*  57 */     Class<?> nextClass = this.target.getClass();
/*     */     
/*  59 */     while (nextClass != null && nextClass != Object.class) {
/*  60 */       processByClass(write, src, nextClass);
/*  61 */       nextClass = nextClass.getSuperclass();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processByClass(boolean write, Properties src, Class nextClass) throws IllegalAccessException {
/*  66 */     Field[] declaredFields = nextClass.getDeclaredFields();
/*  67 */     for (Field declaredField : declaredFields) {
/*  68 */       if (declaredField.isAnnotationPresent((Class)InjectFromProperty.class)) {
/*  69 */         InjectFromProperty injectFromProperty = declaredField.<InjectFromProperty>getAnnotation(InjectFromProperty.class);
/*     */         
/*  71 */         String propName = injectFromProperty.value();
/*  72 */         String propValue = src.getProperty(propName);
/*     */         
/*  74 */         if (propValue == null) {
/*  75 */           if (injectFromProperty.isRequired()) {
/*  76 */             throwException("Cannot find property value", declaredField, propName, propValue);
/*     */           }
/*     */         } else {
/*  79 */           PropertyEditor propertyEditor = PropertyEditorManager.findEditor(declaredField.getType());
/*     */           
/*     */           try {
/*  82 */             propertyEditor.setAsText(propValue);
/*  83 */           } catch (IllegalArgumentException e) {
/*  84 */             throwException("Cannot parse value for property", declaredField, propName, propValue);
/*     */           } 
/*  86 */           if (write) {
/*  87 */             Object value = propertyEditor.getValue();
/*  88 */             if (value instanceof String) {
/*  89 */               String s = (String)value;
/*  90 */               if (isLinuxEnv(s)) {
/*  91 */                 value = readLinuxEnvValue(s);
/*  92 */               } else if (isWindowsEnv(s)) {
/*  93 */                 value = readWindowsEnvValue(s);
/*     */               } 
/*     */             } 
/*  96 */             declaredField.setAccessible(true);
/*  97 */             declaredField.set(this.target, value);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String readWindowsEnvValue(String varName) {
/* 106 */     varName = varName.substring(1, varName.lastIndexOf('%'));
/* 107 */     return System.getenv(varName);
/*     */   }
/*     */ 
/*     */   
/*     */   private String readLinuxEnvValue(String varName) {
/* 112 */     return System.getenv(varName.substring(1));
/*     */   }
/*     */   
/*     */   private boolean isWindowsEnv(String s) {
/* 116 */     return (s.startsWith("%") && s.endsWith("%"));
/*     */   }
/*     */   
/*     */   private boolean isLinuxEnv(String s) {
/* 120 */     return s.startsWith("$");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void throwException(String msg, Field declaredField, String propName, String propValue) throws IllegalStateException {
/* 126 */     String className = declaredField.getDeclaringClass().getName();
/* 127 */     throw new IllegalStateException(msg + ":" + " property={" + propName + ((propValue != null) ? ("=" + propValue) : "") + "}" + " field=" + className + "#" + declaredField.getName() + ":" + declaredField.getType().getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasChanged() {
/* 135 */     return !makeHash(this.file).equals(this.lastHashWhenRead);
/*     */   }
/*     */   
/*     */   public File getFile() {
/* 139 */     return this.file;
/*     */   }
/*     */   
/*     */   public Properties getFileProperties() throws PropertyException {
/* 143 */     Reader in = null;
/*     */     try {
/* 145 */       Properties ret = new Properties();
/*     */       
/* 147 */       in = new BufferedReader(new FileReader(this.file));
/* 148 */       ret.load(in);
/*     */       
/* 150 */       return ret;
/* 151 */     } catch (IOException e) {
/* 152 */       throw new PropertyException(e);
/*     */     } finally {
/* 154 */       close(in);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void close(Reader in) {
/* 159 */     if (in != null)
/*     */       try {
/* 161 */         in.close();
/* 162 */       } catch (IOException ignore) {} 
/*     */   }
/*     */   
/*     */   public static class PropertyException
/*     */     extends Exception
/*     */   {
/*     */     public PropertyException(Exception e) {
/* 169 */       super(e);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\configuration\FieldProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */