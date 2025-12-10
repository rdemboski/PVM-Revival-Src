/*     */ package com.funcom.gameengine.model;
/*     */ 
/*     */ import com.funcom.commons.Vector2d;
/*     */ import com.funcom.commons.geom.RectangleWC;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.collisiondetection.CollisionDataProvider;
/*     */ import com.funcom.gameengine.collisiondetection.CollisionDetection;
/*     */ import com.funcom.gameengine.collisiondetection.CollisionResult;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class NewtonianMovement
/*     */ {
/*     */   private Vector2d velocity;
/*     */   private Vector2d acceleration;
/*     */   private Creature mover;
/*     */   private CollisionDataProvider collisionDataProvider;
/*     */   private PropProvider propProvider;
/*  21 */   private static final Logger LOGGER = Logger.getLogger(NewtonianMovement.class.getName());
/*     */   private static final double PLAYER_COLLISION_MAX_SIZE = 2.0D;
/*     */   
/*     */   public NewtonianMovement(Creature mover, CollisionDataProvider collisionDataProvider, PropProvider propProvider) {
/*  25 */     this.mover = mover;
/*  26 */     this.collisionDataProvider = collisionDataProvider;
/*  27 */     this.propProvider = propProvider;
/*  28 */     this.velocity = new Vector2d();
/*  29 */     this.acceleration = new Vector2d();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(float dT) {
/*  36 */     dT = Math.min(dT, 0.1F);
/*  37 */     updateVelocity(dT);
/*     */ 
/*     */     
/*  40 */     WorldCoordinate orgPos = new WorldCoordinate(this.mover.getPosition());
/*  41 */     Vector2d offset = this.velocity.multRet(dT);
/*     */ 
/*     */     
/*  44 */     if (this.velocity.lengthSq() < 1.0E-5D) {
/*     */       return;
/*     */     }
/*  47 */     CollisionResult collisionResult = CollisionDetection.instance().checkCollision((InteractibleProp)this.mover, offset, this.collisionDataProvider.getCollisionRoot(), this.mover.getRadius());
/*  48 */     if (collisionResult.getType() == CollisionResult.Type.STOPPED) {
/*  49 */       this.velocity.setNull();
/*     */       return;
/*     */     } 
/*  52 */     if (collisionResult.getType() == CollisionResult.Type.BLOCKED) {
/*  53 */       offset = collisionResult.getVector();
/*     */     }
/*     */     
/*  56 */     WorldCoordinate newPos = (new WorldCoordinate(orgPos)).addOffset(offset);
/*  57 */     InteractibleProp collision = playerCollision(newPos, orgPos);
/*  58 */     if (collision != null) {
/*  59 */       WorldCoordinate toCollision = (new WorldCoordinate(collision.getPosition())).subtract(this.mover.getPosition());
/*  60 */       double angle = toCollision.angle();
/*  61 */       offset.decompose(angle + 1.5707963267948966D);
/*  62 */       newPos = (new WorldCoordinate(orgPos)).addOffset(offset);
/*     */ 
/*     */ 
/*     */       
/*  66 */       collisionResult = CollisionDetection.instance().checkCollision((InteractibleProp)this.mover, offset, this.collisionDataProvider.getCollisionRoot(), this.mover.getRadius());
/*  67 */       InteractibleProp newCollision = playerCollision(newPos, orgPos);
/*  68 */       if (collisionResult.getType() != CollisionResult.Type.NONE || newCollision != null) {
/*  69 */         this.velocity.setNull();
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/*  75 */     this.mover.setPosition(newPos);
/*     */   }
/*     */   
/*     */   private InteractibleProp playerCollision(WorldCoordinate newPos, WorldCoordinate oldPos) {
/*  79 */     WorldCoordinate origin = (new WorldCoordinate(newPos)).addOffset(-2.0D, -2.0D);
/*  80 */     WorldCoordinate extent = (new WorldCoordinate(newPos)).addOffset(2.0D, 2.0D);
/*  81 */     RectangleWC targetArea = new RectangleWC(origin, extent);
/*  82 */     Set<InteractibleProp> possibleCollisions = this.propProvider.getProps(targetArea);
/*  83 */     for (InteractibleProp possibleCollision : possibleCollisions) {
/*  84 */       if (possibleCollision.getId() == this.mover.getId()) {
/*     */         continue;
/*     */       }
/*  87 */       double distance = possibleCollision.getPosition().distanceTo(newPos);
/*  88 */       if (distance <= possibleCollision.getRadius() + this.mover.getRadius() && distance < possibleCollision.getPosition().distanceTo(oldPos))
/*     */       {
/*  90 */         return possibleCollision;
/*     */       }
/*     */     } 
/*  93 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateVelocity(float dT) {
/*  98 */     double speed = this.velocity.length();
/*  99 */     double dragFactor = this.mover.getDrag();
/* 100 */     Vector2d drag = this.velocity.normalizeRet().multRet(-speed * dragFactor);
/*     */     
/* 102 */     Vector2d currentAcceleration = new Vector2d(this.acceleration);
/* 103 */     if (!this.mover.canMove()) {
/* 104 */       currentAcceleration.setNull();
/*     */     }
/* 106 */     currentAcceleration.add(drag);
/* 107 */     this.velocity.add(currentAcceleration.multRet(dT));
/*     */   }
/*     */   
/*     */   public void setAcceleration(Vector2d acceleration) {
/* 111 */     this.acceleration = acceleration;
/*     */   }
/*     */   
/*     */   public void setControlled(Creature mover) {
/* 115 */     this.mover = mover;
/*     */   }
/*     */   
/*     */   public Vector2d getVelocity() {
/* 119 */     return this.velocity;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\NewtonianMovement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */