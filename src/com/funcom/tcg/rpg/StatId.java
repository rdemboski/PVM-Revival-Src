/*     */ package com.funcom.tcg.rpg;
/*     */ 
/*     */ import com.funcom.commons.IdManager;
/*     */ import com.funcom.rpgengine2.Stat;
/*     */ import com.funcom.rpgengine2.combat.Element;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StatId
/*     */ {
/*  19 */   private static final Logger LOG = Logger.getLogger(StatId.class.getName());
/*     */   
/*  21 */   private static final IdManager ID_MANAGER = new IdManager();
/*     */   
/*     */   @PersistentStat
/*     */   public static final short XP = 0;
/*     */   
/*     */   @PersistentStat
/*     */   public static final short LIVES = 1;
/*     */   
/*     */   public static final short SPEED = 10;
/*     */   
/*     */   public static final short MAX_HEALTH = 11;
/*     */   
/*     */   public static final short HEALTH = 12;
/*     */   
/*     */   public static final short MAX_MANA = 13;
/*     */   
/*     */   public static final short MANA = 14;
/*     */   
/*     */   public static final short MAX_LIVES = 15;
/*     */   
/*     */   public static final short MAX_SLOTS_INVENTORY = 16;
/*     */   
/*     */   public static final short CRIT = 17;
/*     */   
/*     */   public static final short CRIT_MULTIPLIER = 18;
/*     */   
/*     */   public static final short MAX_XP = 19;
/*     */   public static final short ACCELERATION = 23;
/*     */   public static final short TURNSPEED = 24;
/*     */   public static final short DRAG = 25;
/*     */   public static final short FACTION = 26;
/*     */   public static final short BASE_RESISTANCE = 27;
/*     */   public static final short RADIUS = 28;
/*     */   public static final short ATTACK = 29;
/*     */   public static final short LEVEL = 20;
/*     */   public static final short LEVEL_START_XP = 21;
/*     */   public static final short LEVEL_END_XP = 22;
/*     */   private static final short __RESISTANCE_START = 100;
/*     */   private static List<Short> persistentIds;
/*     */   private static List<Short> runtimeIds;
/*     */   private static boolean registered;
/*     */   
/*     */   static {
/*  64 */     registerIds();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void registerIds() {
/*  73 */     if (registered) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  79 */       ID_MANAGER.register(StatId.class);
/*     */       
/*  81 */       ID_MANAGER.registerSet(Element.class, (short)100);
/*     */       
/*  83 */       List<IdManager.AnnotatedType> persistentVals = ID_MANAGER.values(PersistentStat.class);
/*  84 */       List<Short> tmp = new ArrayList<Short>();
/*  85 */       for (IdManager.AnnotatedType val : persistentVals) {
/*  86 */         tmp.add(val.getType());
/*     */       }
/*  88 */       persistentIds = Collections.unmodifiableList(tmp);
/*     */       
/*  90 */       tmp = new ArrayList<Short>(ID_MANAGER.values());
/*  91 */       tmp.removeAll(persistentIds);
/*  92 */       runtimeIds = Collections.unmodifiableList(tmp);
/*     */       
/*  94 */       if (LOG.isEnabledFor((Priority)Level.INFO)) {
/*  95 */         List<Short> ids = new ArrayList<Short>(ID_MANAGER.values());
/*  96 */         Collections.sort(ids);
/*  97 */         int size = ids.size();
/*  98 */         String infoStr = "List of stat ids:\n--- Stat Ids ---\n";
/*  99 */         for (int i = 0; i < size; i++) {
/* 100 */           Short id = ids.get(i);
/* 101 */           infoStr = infoStr + id + ": " + ID_MANAGER.getName(id) + "\n";
/*     */         } 
/* 103 */         LOG.info(infoStr + "----------------");
/*     */       } 
/*     */       
/* 106 */       registered = true;
/* 107 */     } catch (IllegalAccessException e) {
/* 108 */       throw new RuntimeException("problem registering constants", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Short getId(Object nameRepresentation) {
/* 113 */     return ID_MANAGER.getType(nameRepresentation);
/*     */   }
/*     */   
/*     */   public static Object getName(Short id) {
/* 117 */     return ID_MANAGER.getName(id);
/*     */   }
/*     */   
/*     */   public static List<Short> getAllIds() {
/* 121 */     return ID_MANAGER.values();
/*     */   }
/*     */   
/*     */   public static List<Short> getPersistentIds() {
/* 125 */     return persistentIds;
/*     */   }
/*     */   
/*     */   public static List<Stat> createEmptyPersistentStats() {
/* 129 */     List<Stat> statList = new ArrayList<Stat>(persistentIds.size());
/* 130 */     for (Short persistentId : persistentIds) {
/* 131 */       statList.add(new Stat(persistentId, 0));
/*     */     }
/* 133 */     return statList;
/*     */   }
/*     */   
/*     */   public static List<Short> getRuntimeIds() {
/* 137 */     return runtimeIds;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\StatId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */