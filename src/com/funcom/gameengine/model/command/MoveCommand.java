/*     */ package com.funcom.gameengine.model.command;
/*     */ 
/*     */ import com.funcom.commons.MathUtils;
/*     */ import com.funcom.commons.PerformanceGraphNode;
/*     */ import com.funcom.commons.Vector2d;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.collisiondetection.CollisionDetection;
/*     */ import com.funcom.gameengine.collisiondetection.CollisionResult;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import com.funcom.gameengine.spatial.LineNode;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ public class MoveCommand
/*     */   extends Command
/*     */ {
/*     */   public static final String COMMAND_NAME = "move";
/*  30 */   private double accelerationSpeed = 20.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   private double diffRatio = 1.0471975511965976D;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double DEC_MINIMUM = 0.001D;
/*     */ 
/*     */   
/*     */   private static final double SPEED_TOLERANCE = 0.01D;
/*     */ 
/*     */   
/*     */   public static final double TARGET_TOLERANCE = 0.15D;
/*     */ 
/*     */   
/*     */   private Destination destination;
/*     */ 
/*     */   
/*     */   private boolean finished;
/*     */ 
/*     */   
/*     */   private double currentSpeed;
/*     */ 
/*     */   
/*     */   private LineNode rootLine;
/*     */ 
/*     */   
/*     */   private boolean smoothPath;
/*     */ 
/*     */ 
/*     */   
/*     */   public MoveCommand(LineNode rootCollisionLine, Destination destination) {
/*  66 */     this.rootLine = rootCollisionLine;
/*  67 */     this.destination = destination;
/*  68 */     this.currentSpeed = 0.0D;
/*  69 */     this.smoothPath = true;
/*     */   }
/*     */   
/*     */   public MoveCommand(LineNode collisionRoot, Destination destination, double currentSpeed) {
/*  73 */     this.rootLine = collisionRoot;
/*  74 */     this.destination = destination;
/*  75 */     this.currentSpeed = currentSpeed;
/*  76 */     this.smoothPath = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParentCreature(Creature parentCreature) {
/*  81 */     super.setParentCreature(parentCreature);
/*     */   }
/*     */   
/*     */   public void setFinished(boolean finished) {
/*  85 */     this.finished = finished;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  90 */     return "move";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFinished() {
/*  95 */     return this.finished;
/*     */   }
/*     */   
/*     */   public boolean meetsFinishedCondition() {
/*  99 */     double destDistance = getParentCreature().getPosition().distanceTo(this.destination.getTarget());
/* 100 */     double currAngle = getParentCreature().getRotation();
/* 101 */     double preferredAngle = this.destination.getPreferredAngle();
/* 102 */     double diff = MathUtils.getAngleDiff(currAngle, preferredAngle);
/* 103 */     return (destDistance - 0.15D < 0.0D && (!this.destination.isAngleDependent() || Math.abs(diff) == 0.0D));
/*     */   }
/*     */   
/*     */   public void setSmoothPath(boolean smoothPath) {
/* 107 */     this.smoothPath = smoothPath;
/*     */   }
/*     */   
/*     */   public boolean isSmoothPath() {
/* 111 */     return this.smoothPath;
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(float time) {
/* 116 */     if (Math.abs(getParentCreature().getSpeed()) < 0.001D || !getParentCreature().canMove()) {
/* 117 */       turnToTarget(time);
/*     */       
/*     */       return;
/*     */     } 
/* 121 */     if (this.currentSpeed < getParentCreature().getSpeed() || this.currentSpeed > getParentCreature().getSpeed()) {
/* 122 */       this.currentSpeed = getParentCreature().getSpeed();
/*     */     }
/*     */ 
/*     */     
/* 126 */     double moveDistance = this.currentSpeed * time;
/* 127 */     WorldCoordinate target = this.destination.getTarget();
/* 128 */     double destDistance = getParentCreature().getPosition().distanceTo(target);
/* 129 */     moveDistance = Math.min(moveDistance, destDistance);
/*     */ 
/*     */     
/* 132 */     if (!this.destination.isAtDestination(destDistance)) {
/* 133 */       moveToTarget(time, moveDistance, target);
/* 134 */     } else if (this.destination.isAngleDependent()) {
/* 135 */       turnToTarget(time);
/* 136 */       setFinished(true);
/*     */     } else {
/* 138 */       setFinished(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void moveToTarget(float time, double moveDistance, WorldCoordinate target) {
/* 143 */     PerformanceGraphNode.startTiming(PerformanceGraphNode.TrackingStat.MOVETO.statType);
/*     */     
/* 145 */     WorldCoordinate targetVector = (new WorldCoordinate(target)).subtract(getParentCreature().getPosition());
/* 146 */     Vector2d vec = new Vector2d((targetVector.getTileCoord()).x + targetVector.getTileOffset().getX(), (targetVector.getTileCoord()).y + targetVector.getTileOffset().getY());
/*     */     
/* 148 */     if (this.smoothPath) {
/* 149 */       vec = smoothMovement(time, moveDistance, vec);
/*     */     } else {
/* 151 */       vec.normalize();
/* 152 */       vec.mult(moveDistance);
/*     */     } 
/*     */ 
/*     */     
/* 156 */     CollisionResult result = CollisionDetection.instance().checkCollision((InteractibleProp)getParentCreature(), vec, this.rootLine, getParentCreature().getRadius());
/* 157 */     if (result.isFailed()) {
/* 158 */       double currAngle = getParentCreature().getRotation();
/* 159 */       double vecAngle = vec.angle();
/* 160 */       double diff = MathUtils.getAngleDiff(currAngle, vecAngle);
/* 161 */       if (Math.abs(diff) < (getParentCreature().getTurnSpeed() * time)) {
/* 162 */         getParentCreature().setRotation((float)vecAngle);
/* 163 */         fail();
/*     */       } else {
/*     */         double angle;
/* 166 */         if (diff < 0.0D) {
/* 167 */           angle = currAngle - (getParentCreature().getTurnSpeed() * time);
/*     */         } else {
/* 169 */           angle = currAngle + (getParentCreature().getTurnSpeed() * time);
/*     */         } 
/* 171 */         getParentCreature().setRotation((float)angle);
/*     */       } 
/*     */     } else {
/* 174 */       vec = result.getVector();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 179 */       getParentCreature().translate(vec);
/*     */     } 
/* 181 */     PerformanceGraphNode.endTiming(PerformanceGraphNode.TrackingStat.MOVETO.statType);
/*     */   }
/*     */   
/*     */   private Vector2d smoothMovement(float time, double moveDistance, Vector2d vec) {
/* 185 */     vec.normalize();
/* 186 */     double vecAngle = vec.angle();
/* 187 */     vec.mult(moveDistance);
/*     */ 
/*     */     
/* 190 */     CollisionResult result = CollisionDetection.instance().checkCollision((InteractibleProp)getParentCreature(), vec, this.rootLine, getParentCreature().getRadius());
/* 191 */     if (result.isFailed()) {
/* 192 */       vec.set(0.0D, 0.0D);
/* 193 */       fail();
/* 194 */       return vec;
/*     */     } 
/*     */     
/* 197 */     vec = result.getVector();
/*     */     
/* 199 */     double currAngle = getParentCreature().getRotation();
/* 200 */     double diff = MathUtils.getAngleDiff(currAngle, vecAngle);
/*     */ 
/*     */     
/* 203 */     if (Math.abs(diff) > (getParentCreature().getTurnSpeed() * time)) {
/*     */       double angle;
/* 205 */       if (diff < 0.0D) {
/* 206 */         angle = currAngle - (getParentCreature().getTurnSpeed() * time);
/*     */       } else {
/* 208 */         angle = currAngle + (getParentCreature().getTurnSpeed() * time);
/*     */       } 
/*     */       
/* 211 */       vec.set(Math.cos(angle), Math.sin(angle));
/* 212 */       vec.mult(moveDistance);
/*     */       
/* 214 */       double decSpeed = this.currentSpeed * Math.abs(diff) * this.diffRatio * time + 0.001D;
/* 215 */       this.currentSpeed = Math.max(0.0D, this.currentSpeed - decSpeed);
/*     */ 
/*     */       
/* 218 */       if (this.currentSpeed < 0.01D) {
/* 219 */         getParentCreature().setRotation((float)angle);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 224 */       if (this.currentSpeed < getParentCreature().getSpeed()) {
/* 225 */         this.currentSpeed = Math.min(getParentCreature().getSpeed(), this.currentSpeed + (getParentCreature().getAccelerationSpeed() * time));
/*     */       }
/*     */       
/* 228 */       vec.set(Math.cos(vecAngle), Math.sin(vecAngle));
/* 229 */       vec.mult(moveDistance);
/*     */     } 
/*     */     
/* 232 */     return vec;
/*     */   }
/*     */   
/*     */   private void turnToTarget(float time) {
/* 236 */     PerformanceGraphNode.startTiming(PerformanceGraphNode.TrackingStat.TURNTO.statType);
/* 237 */     double currAngle = getParentCreature().getRotation();
/* 238 */     double preferredAngle = this.destination.getPreferredAngle();
/* 239 */     double diff = MathUtils.getAngleDiff(currAngle, preferredAngle);
/* 240 */     if (Math.abs(diff) < (getParentCreature().getTurnSpeed() * time)) {
/* 241 */       getParentCreature().setRotation((float)preferredAngle);
/* 242 */       setFinished(true);
/*     */     } else {
/*     */       double angle;
/* 245 */       if (diff < 0.0D) {
/* 246 */         angle = currAngle - (getParentCreature().getTurnSpeed() * time);
/*     */       } else {
/* 248 */         angle = currAngle + (getParentCreature().getTurnSpeed() * time);
/*     */       } 
/* 250 */       getParentCreature().setRotation((float)angle);
/*     */     } 
/* 252 */     PerformanceGraphNode.endTiming(PerformanceGraphNode.TrackingStat.TURNTO.statType);
/*     */   }
/*     */   
/*     */   public Destination getDestination() {
/* 256 */     return this.destination;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<MoveCommand> route(LineNode lineRootNode, WorldCoordinate[] destination, Creature creature) {
/* 261 */     List<MoveCommand> commands = new LinkedList<MoveCommand>();
/* 262 */     for (WorldCoordinate worldCoordinate : destination)
/* 263 */       commands.add(new MoveCommand(lineRootNode, new FixedDestination(worldCoordinate, creature))); 
/* 264 */     return commands;
/*     */   }
/*     */   
/*     */   public static List<MoveCommand> route(LineNode lineRootNode, Collection<WorldCoordinate> destination, Creature creature) {
/* 268 */     return route(lineRootNode, destination.<WorldCoordinate>toArray(new WorldCoordinate[destination.size()]), creature);
/*     */   }
/*     */   
/*     */   public double getCurrentSpeed() {
/* 272 */     return this.currentSpeed;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\command\MoveCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */