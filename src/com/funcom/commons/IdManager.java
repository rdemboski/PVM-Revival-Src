/*     */ package com.funcom.commons;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IdManager
/*     */ {
/*  16 */   private final Map<Short, Object> typeToName = new HashMap<Short, Object>();
/*  17 */   private final Map<Object, Short> nameToType = new HashMap<Object, Short>();
/*  18 */   private final Map<Class<? extends Annotation>, List<AnnotatedType>> typeByAnnotation = new HashMap<Class<? extends Annotation>, List<AnnotatedType>>();
/*     */   
/*  20 */   private List<Short> allIDs = Collections.unmodifiableList(new ArrayList<Short>());
/*     */ 
/*     */   
/*     */   public synchronized void register(Class typeClass) throws IllegalAccessException {
/*  24 */     Field[] fields = typeClass.getFields();
/*     */     
/*  26 */     for (Field field : fields) {
/*  27 */       int modifiers = field.getModifiers();
/*     */       
/*  29 */       String nameRep = field.getName();
/*  30 */       if (((modifiers & 0x10) != 0 && (modifiers & 0x8) != 0 && (modifiers & 0x1) != 0 && field.getType() == short.class) || (field.getType() == Short.class && !nameRep.startsWith("_"))) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  36 */         Short typeID = (Short)field.get((Object)null);
/*  37 */         register(nameRep, field.getAnnotations(), typeID);
/*     */       } 
/*     */     } 
/*     */     
/*  41 */     resetAllIds();
/*     */   }
/*     */   
/*     */   public synchronized void registerSet(Class<? extends Enum> enumClass, short idStart) {
/*  45 */     short nextId = idStart;
/*     */     
/*     */     try {
/*  48 */       Enum[] values = enumClass.getEnumConstants();
/*  49 */       for (Enum nameRepresentation : values) {
/*  50 */         Field enumField = enumClass.getField(nameRepresentation.name());
/*     */         
/*  52 */         nextId = (short)(nextId + 1); register(nameRepresentation, enumField.getAnnotations(), Short.valueOf(nextId));
/*     */       } 
/*  54 */     } catch (NoSuchFieldException e) {
/*  55 */       throw new RuntimeException("Should not happen, cannot find enum field from name list retreived from enum class", e);
/*     */     } 
/*     */     
/*  58 */     resetAllIds();
/*     */   }
/*     */   
/*     */   private void resetAllIds() {
/*  62 */     List<Short> ids = new ArrayList<Short>(this.typeToName.size());
/*     */     
/*  64 */     for (Short key : this.typeToName.keySet()) {
/*  65 */       ids.add(key);
/*     */     }
/*     */     
/*  68 */     this.allIDs = Collections.unmodifiableList(ids);
/*     */   }
/*     */   
/*     */   private void register(Object nameRep, Annotation[] annotations, Short typeID) {
/*  72 */     Object existingName = this.typeToName.get(typeID);
/*  73 */     if (existingName != null && !existingName.equals(nameRep))
/*     */     {
/*  75 */       throw new RuntimeException("Duplicate type id: id=" + typeID + " nameExisting=" + existingName + "(" + existingName.getClass().getName() + ")" + " nameNew=" + nameRep + "(" + nameRep.getClass().getName() + ")");
/*     */     }
/*     */ 
/*     */     
/*  79 */     this.typeToName.put(typeID, nameRep);
/*  80 */     this.nameToType.put(nameRep, typeID);
/*     */     
/*  82 */     if (annotations != null) {
/*  83 */       for (Annotation annotation : annotations) {
/*  84 */         List<AnnotatedType> existingList = this.typeByAnnotation.get(annotation.annotationType());
/*  85 */         List<AnnotatedType> newList = new ArrayList<AnnotatedType>();
/*  86 */         if (existingList != null) {
/*  87 */           newList.addAll(existingList);
/*     */         }
/*     */         
/*  90 */         newList.add(new AnnotatedType(annotation, typeID));
/*     */         
/*  92 */         this.typeByAnnotation.put(annotation.annotationType(), Collections.unmodifiableList(newList));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Short> values() {
/* 104 */     return this.allIDs;
/*     */   }
/*     */   
/*     */   public Object getName(Short id) {
/* 108 */     return this.typeToName.get(id);
/*     */   }
/*     */   
/*     */   public Short getType(Object nameRepresentation) {
/* 112 */     Short type = this.nameToType.get(nameRepresentation);
/* 113 */     if (type == null) {
/* 114 */       throw new NoSuchElementException("stat type not found: statStr=" + nameRepresentation);
/*     */     }
/* 116 */     return type;
/*     */   }
/*     */   
/*     */   public List<AnnotatedType> values(Class<? extends Annotation> annotationClass) {
/* 120 */     return this.typeByAnnotation.get(annotationClass);
/*     */   }
/*     */   
/*     */   public static class AnnotatedType {
/*     */     private Annotation annotation;
/*     */     private Short type;
/*     */     
/*     */     public AnnotatedType(Annotation annotation, Short type) {
/* 128 */       this.annotation = annotation;
/* 129 */       this.type = type;
/*     */     }
/*     */     
/*     */     public Annotation getAnnotation() {
/* 133 */       return this.annotation;
/*     */     }
/*     */     
/*     */     public Short getType() {
/* 137 */       return this.type;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\IdManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */