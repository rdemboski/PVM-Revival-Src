/*     */ package com.funcom.rpgengine2.creatures;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.rpgengine2.abilities.SourceProvider;
/*     */ import com.funcom.rpgengine2.abilities.projectile.Projectile;
/*     */ import com.funcom.rpgengine2.abilities.projectile.ProjectileQueue;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProjectileSupport
/*     */   implements RpgQueryableSupport, RpgUpdateable, ProjectileQueue
/*     */ {
/*  17 */   private static final int[] UPDATE_PRIORITY = new int[] { 500000 };
/*     */   
/*     */   private final List<Projectile> pendingProjectiles;
/*     */   
/*     */   private final List<Projectile> projectiles;
/*     */   private final List<RedirectProjectileContainer> pendingStopProjectiles;
/*     */   private int modCount;
/*     */   private RpgObject owner;
/*     */   
/*     */   public ProjectileSupport(RpgObject owner) {
/*  27 */     this.owner = owner;
/*  28 */     this.pendingProjectiles = Collections.synchronizedList(new ArrayList<Projectile>());
/*  29 */     this.projectiles = Collections.synchronizedList(new ArrayList<Projectile>());
/*  30 */     this.pendingStopProjectiles = Collections.synchronizedList(new ArrayList<RedirectProjectileContainer>());
/*     */   }
/*     */   
/*     */   public void add(Projectile projectile) {
/*  34 */     this.pendingProjectiles.add(projectile);
/*     */   }
/*     */   
/*     */   public int[] getUpdatePriorities() {
/*  38 */     return UPDATE_PRIORITY;
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(int priority, long updateMillis) {
/*  43 */     synchronized (this.pendingStopProjectiles) {
/*  44 */       Iterator<RedirectProjectileContainer> redirectProjectileContainerIterator = this.pendingStopProjectiles.iterator();
/*  45 */       while (redirectProjectileContainerIterator.hasNext()) {
/*  46 */         RedirectProjectileContainer redirectProjectileContainer = redirectProjectileContainerIterator.next();
/*  47 */         Projectile projectile = redirectProjectileContainer.getProjectile();
/*     */         
/*  49 */         Projectile projectileCopy = projectile.copy();
/*  50 */         projectileCopy.initRedirect(redirectProjectileContainer.getReflectionValue());
/*     */         
/*  52 */         projectile.stop(true);
/*     */         
/*  54 */         RpgEntity reflectorSource = redirectProjectileContainer.getReflectorOwner().getSourceObject();
/*  55 */         ((ProjectileSupport)reflectorSource.<ProjectileSupport>getSupport(ProjectileSupport.class)).enqueueProjectile(projectileCopy, redirectProjectileContainer.getStartPosition(), redirectProjectileContainer.getNewAngle());
/*     */         
/*  57 */         redirectProjectileContainerIterator.remove();
/*     */       } 
/*     */     } 
/*     */     
/*  61 */     synchronized (this.pendingProjectiles) {
/*  62 */       if (!this.pendingProjectiles.isEmpty()) {
/*  63 */         int size = this.pendingProjectiles.size();
/*  64 */         for (int i = 0; i < size; i++) {
/*  65 */           this.projectiles.add(this.pendingProjectiles.get(i));
/*  66 */           this.modCount++;
/*     */         } 
/*  68 */         this.pendingProjectiles.clear();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  73 */     synchronized (this.projectiles) {
/*  74 */       int size = this.projectiles.size();
/*  75 */       for (int i = size - 1; i >= 0; i--) {
/*  76 */         Projectile projectile = this.projectiles.get(i);
/*  77 */         projectile.update(updateMillis);
/*     */         
/*  79 */         if (projectile.isDestroyed()) {
/*  80 */           this.projectiles.remove(i);
/*  81 */           this.modCount++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<Projectile> getProjectiles() {
/*  88 */     return new ArrayList<Projectile>(this.projectiles);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getModCount() {
/*  97 */     return this.modCount;
/*     */   }
/*     */   
/*     */   public void enqueueProjectile(Projectile projectile, WorldCoordinate startPosition, float startAngle) {
/* 101 */     projectile.setOwner(this.owner);
/* 102 */     projectile.start(startPosition, startAngle);
/* 103 */     add(projectile);
/*     */   }
/*     */   
/*     */   public void enqueueStopAndRedirectProjectile(Projectile projectile, SourceProvider reflectorOwner, float newAngle, WorldCoordinate startPosition, float reflectionValue) {
/* 107 */     this.pendingStopProjectiles.add(new RedirectProjectileContainer(projectile, reflectorOwner, newAngle, startPosition, reflectionValue));
/*     */   }
/*     */   
/*     */   private static class RedirectProjectileContainer {
/*     */     private final Projectile projectile;
/*     */     private final SourceProvider reflectorOwner;
/*     */     private final float newAngle;
/*     */     private final WorldCoordinate startPosition;
/*     */     private final float reflectionValue;
/*     */     
/*     */     public RedirectProjectileContainer(Projectile projectile, SourceProvider reflectorOwner, float newAngle, WorldCoordinate startPosition, float reflectionValue) {
/* 118 */       this.projectile = projectile;
/* 119 */       this.reflectorOwner = reflectorOwner;
/* 120 */       this.newAngle = newAngle;
/* 121 */       this.startPosition = startPosition;
/* 122 */       this.reflectionValue = reflectionValue;
/*     */     }
/*     */     
/*     */     public Projectile getProjectile() {
/* 126 */       return this.projectile;
/*     */     }
/*     */     
/*     */     public SourceProvider getReflectorOwner() {
/* 130 */       return this.reflectorOwner;
/*     */     }
/*     */     
/*     */     public float getNewAngle() {
/* 134 */       return this.newAngle;
/*     */     }
/*     */     
/*     */     public WorldCoordinate getStartPosition() {
/* 138 */       return this.startPosition;
/*     */     }
/*     */     
/*     */     public float getReflectionValue() {
/* 142 */       return this.reflectionValue;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\ProjectileSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */