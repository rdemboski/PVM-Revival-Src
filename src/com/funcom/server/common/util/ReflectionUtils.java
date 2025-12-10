/*     */ package com.funcom.server.common.util;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReflectionUtils
/*     */ {
/*     */   public static <V> V newInstance(Class<V> clazz, Object... availableArgs) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
/*  29 */     Class[] types = new Class[availableArgs.length];
/*  30 */     for (int i = 0; i < availableArgs.length; i++) {
/*  31 */       Object availableArg = availableArgs[i];
/*  32 */       if (availableArg != null) {
/*  33 */         types[i] = availableArg.getClass();
/*     */       } else {
/*  35 */         types[i] = Object.class;
/*     */       } 
/*     */     } 
/*  38 */     return newInstance(clazz, types, availableArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> V newInstance(Class<V> clazz, Class[] availableTypes, Object[] availableArgs) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
/*  46 */     Constructor[] constructors = (Constructor[])clazz.getConstructors();
/*     */     
/*  48 */     Constructor<V> constructor = null;
/*  49 */     for (Constructor constr : constructors) {
/*     */       
/*  51 */       if (hasAllArgs(constr, availableTypes) && (
/*  52 */         constructor == null || (constr.getParameterTypes()).length > (constructor.getParameterTypes()).length))
/*     */       {
/*  54 */         constructor = constr;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  59 */     if (constructor != null) {
/*     */       
/*  61 */       Object[] args = collectArgs(constructor, availableTypes, availableArgs);
/*     */       
/*  63 */       return constructor.newInstance(args);
/*     */     } 
/*     */     
/*  66 */     throw new NoSuchMethodException("No (public) constructor found");
/*     */   }
/*     */   
/*     */   private static Object[] collectArgs(Constructor constructor, Class[] availableTypes, Object[] availableArgs) {
/*  70 */     Class[] paramTypes = constructor.getParameterTypes();
/*  71 */     Object[] ret = new Object[paramTypes.length];
/*     */     
/*  73 */     for (int i = 0; i < paramTypes.length; i++) {
/*  74 */       Class paramType = paramTypes[i];
/*     */       
/*  76 */       if (i < availableTypes.length && paramType.isAssignableFrom(availableTypes[i])) {
/*  77 */         ret[i] = availableArgs[i];
/*     */       }
/*     */       else {
/*     */         
/*  81 */         for (int a = 0; a < availableTypes.length; a++) {
/*  82 */           if (paramType.isAssignableFrom(availableTypes[a])) {
/*  83 */             ret[i] = availableArgs[a];
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*  88 */     return ret;
/*     */   }
/*     */   
/*     */   private static boolean hasAllArgs(Constructor constr, Class[] availableTypes) {
/*  92 */     Class[] constrParams = constr.getParameterTypes();
/*     */     
/*  94 */     for (Class constrParam : constrParams) {
/*  95 */       boolean found = false;
/*  96 */       for (Class<?> availableType : availableTypes) {
/*  97 */         if (constrParam.isAssignableFrom(availableType)) {
/*  98 */           found = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 103 */       if (!found) {
/* 104 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 108 */     return true;
/*     */   }
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static <V> List<Class<? extends V>> getClasses(Class<V> targetSuperClass, InputStream propertiesStream) throws ClassNotFoundException {
/* 124 */     List<Class<? extends V>> ret = new ArrayList<Class<? extends V>>();
/*     */     
/* 126 */     Properties props = new Properties();
/*     */     try {
/* 128 */       props.load(new InputStreamReader(propertiesStream));
/* 129 */     } catch (Exception e) {
/* 130 */       throw new RuntimeException("unable to load: " + targetSuperClass.getSimpleName() + "(s)", e);
/*     */     } 
/*     */     
/* 133 */     Set<String> packagePrefixes = props.stringPropertyNames();
/* 134 */     for (String packagePrefix : packagePrefixes) {
/* 135 */       String classListStr = props.getProperty(packagePrefix).trim();
/*     */       
/* 137 */       String[] simpleClassNames = classListStr.split("\\s*,\\s*");
/* 138 */       for (String simpleClassName : simpleClassNames) {
/* 139 */         String className = packagePrefix + "." + simpleClassName.trim();
/* 140 */         Class<?> clazz = Class.forName(className);
/*     */         
/* 142 */         if (clazz != null && targetSuperClass.isAssignableFrom(clazz)) {
/*     */ 
/*     */ 
/*     */           
/* 146 */           ret.add(clazz);
/*     */         } else {
/* 148 */           throw new RuntimeException("Class is not a " + targetSuperClass.getSimpleName() + ": " + clazz);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 153 */     return ret;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\commo\\util\ReflectionUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */