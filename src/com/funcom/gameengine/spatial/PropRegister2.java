/*     */ package com.funcom.gameengine.spatial;
/*     */ import com.funcom.commons.geom.RectangleWC;
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class PropRegister2 {
/*  12 */   private static final List<InteractibleProp> EMPTY_INTERACTIBLE_PROPS = Collections.unmodifiableList(new ArrayList<InteractibleProp>(0));
/*     */ 
/*     */ 
/*     */   
/*  16 */   public static final Logger LOGGER = Logger.getLogger(PropRegister2.class.getName());
/*     */ 
/*     */   
/*  19 */   private Map<Integer, InteractibleProp> propMap = new ConcurrentHashMap<Integer, InteractibleProp>();
/*  20 */   private ConcurrentLinkedQueue<InteractibleProp> propList = new ConcurrentLinkedQueue<InteractibleProp>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addProp(InteractibleProp prop) {
/*  30 */     boolean contains = this.propMap.containsKey(Integer.valueOf(prop.getId()));
/*     */     
/*  32 */     if (contains) {
/*     */       
/*  34 */       LOGGER.error("Prop id already exists: " + prop.getId());
/*     */       
/*     */       return;
/*     */     } 
/*  38 */     this.propMap.put(Integer.valueOf(prop.getId()), prop);
/*  39 */     this.propList.add(prop);
/*     */   }
/*     */   
/*     */   public InteractibleProp getProp(Integer id) {
/*  43 */     return this.propMap.get(id);
/*     */   }
/*     */   
/*     */   public List<InteractibleProp> getProps(RectangleWC rectangle) {
/*  47 */     List<InteractibleProp> result = null;
/*     */     
/*  49 */     for (InteractibleProp interactibleProp : this.propList) {
/*  50 */       if (rectangle.contains(interactibleProp.getPosition())) {
/*  51 */         if (result == null) {
/*  52 */           result = new ArrayList<InteractibleProp>();
/*     */         }
/*  54 */         result.add(interactibleProp);
/*     */       } 
/*     */     } 
/*     */     
/*  58 */     if (result != null) {
/*  59 */       return result;
/*     */     }
/*  61 */     return EMPTY_INTERACTIBLE_PROPS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<InteractibleProp> getAll() {
/*  71 */     return new ArrayList<InteractibleProp>(this.propList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeProp(InteractibleProp prop) {
/*  80 */     if (prop == null) {
/*     */       return;
/*     */     }
/*     */     
/*  84 */     this.propMap.remove(Integer.valueOf(prop.getId()));
/*  85 */     this.propList.remove(prop);
/*     */   }
/*     */   
/*     */   public void changePropIdentity(InteractibleProp prop, Integer id) {
/*  89 */     boolean contains = this.propMap.containsKey(Integer.valueOf(prop.getId()));
/*     */     
/*  91 */     if (contains) {
/*  92 */       throw new RuntimeException("Prop id already exists");
/*     */     }
/*     */     
/*  95 */     this.propMap.remove(Integer.valueOf(prop.getId()));
/*  96 */     prop.setId(id.intValue());
/*  97 */     this.propMap.put(id, prop);
/*     */   }
/*     */   
/*     */   public void clearProps() {
/* 101 */     this.propMap.clear();
/* 102 */     this.propList.clear();
/*     */   }
/*     */   
/*     */   public int getSize() {
/* 106 */     return this.propMap.size();
/*     */   }
/*     */   
/*     */   public Iterator<InteractibleProp> elements() {
/* 110 */     return this.propList.iterator();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\spatial\PropRegister2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */