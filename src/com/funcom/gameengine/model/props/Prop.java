/*     */ package com.funcom.gameengine.model.props;
/*     */ 
/*     */ import com.funcom.commons.Vector2d;
/*     */ import com.funcom.gameengine.Updated;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ public class Prop
/*     */   implements Updated {
/*  16 */   private static final Logger LOGGER = Logger.getLogger(Prop.class);
/*     */   
/*     */   private WorldCoordinate position;
/*     */   private String name;
/*     */   private boolean visible;
/*     */   private float rotation;
/*     */   private List<PropChangeListener> propChangeListeners;
/*     */   private Map<String, Object> userParameters;
/*     */   private boolean dead;
/*     */   
/*     */   public Prop(String name) {
/*  27 */     this(name, new WorldCoordinate(0, 0, 0.0D, 0.0D, "UNIDENTIFIED_MAP", 0));
/*     */   }
/*     */   
/*     */   public Prop(String name, WorldCoordinate position) {
/*  31 */     this.name = name;
/*  32 */     this.position = position;
/*  33 */     this.rotation = 0.0F;
/*  34 */     this.visible = true;
/*  35 */     this.dead = false;
/*     */   }
/*     */   
/*     */   public void addPropChangeListener(PropChangeListener positionListener) {
/*  39 */     if (this.propChangeListeners == null)
/*  40 */       this.propChangeListeners = new LinkedList<PropChangeListener>(); 
/*  41 */     this.propChangeListeners.add(positionListener);
/*     */   }
/*     */   
/*     */   public void removePropChangeListener(PropChangeListener positionListener) {
/*  45 */     if (this.propChangeListeners != null)
/*  46 */       this.propChangeListeners.remove(positionListener); 
/*     */   }
/*     */   
/*     */   private void firePositionChanged() {
/*  50 */     if (this.propChangeListeners != null)
/*  51 */       for (PropChangeListener listener : this.propChangeListeners)
/*  52 */         listener.positionChanged(this, this.position);  
/*     */   }
/*     */   
/*     */   private void fireRotationChanged() {
/*  56 */     if (this.propChangeListeners != null)
/*  57 */       for (PropChangeListener listener : this.propChangeListeners)
/*  58 */         listener.rotationChanged(this, this.rotation);  
/*     */   }
/*     */   
/*     */   private void fireVisibilityChanged() {
/*  62 */     if (this.propChangeListeners != null) {
/*  63 */       for (PropChangeListener listener : this.propChangeListeners) {
/*  64 */         listener.visibilityChanged(this, this.visible);
/*     */       }
/*  66 */       if (LOGGER.isEnabledFor((Priority)Level.DEBUG)) {
/*  67 */         LOGGER.info("Visibility change fired! visible: " + this.visible);
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
/*     */ 
/*     */   
/*     */   protected void firePropertyChangeListener(String propertyName, Object oldValue, Object newValue) {
/*  81 */     if (this.propChangeListeners != null) {
/*  82 */       for (PropChangeListener propChangeListener : this.propChangeListeners) {
/*  83 */         propChangeListener.propertyChanged(this, propertyName, newValue, oldValue);
/*     */       }
/*  85 */       if (LOGGER.isEnabledFor((Priority)Level.DEBUG))
/*  86 */         LOGGER.debug(String.format("Property change fired! name:'%s' | oldValue:'%s' | newValue:'%s'", new Object[] { propertyName, oldValue, newValue })); 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void fireNameChanged(String newName) {
/*  91 */     if (this.propChangeListeners != null) {
/*  92 */       for (PropChangeListener propChangeListener : this.propChangeListeners) {
/*  93 */         propChangeListener.nameChanged(this, newName);
/*     */       }
/*  95 */       if (LOGGER.isEnabledFor((Priority)Level.DEBUG))
/*  96 */         LOGGER.debug("Name change fired! newName:'" + newName + "'"); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addUserParameter(String key, Object value) {
/* 101 */     if (this.userParameters == null)
/* 102 */       this.userParameters = new HashMap<String, Object>(); 
/* 103 */     Object oldVal = this.userParameters.put(key, value);
/*     */     
/* 105 */     if (LOGGER.isEnabledFor((Priority)Level.DEBUG)) {
/* 106 */       LOGGER.debug(String.format("Parameter set! key:'%s' | oldValue:'%s' | newValue:'%s'", new Object[] { key, oldVal, value }));
/*     */     }
/* 108 */     firePropertyChangeListener(key, oldVal, value);
/*     */   }
/*     */   
/*     */   public Object getUserParameter(String key) {
/* 112 */     return (this.userParameters == null) ? null : this.userParameters.get(key);
/*     */   }
/*     */   
/*     */   public void setName(String newName) {
/* 116 */     boolean changed = !this.name.equals(newName);
/* 117 */     this.name = newName;
/* 118 */     if (changed)
/* 119 */       fireNameChanged(newName); 
/*     */   }
/*     */   
/*     */   public String getName() {
/* 123 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setPosition(WorldCoordinate position) {
/* 127 */     boolean changed = !this.position.equals(position);
/* 128 */     this.position.set(position);
/* 129 */     if (changed)
/* 130 */       firePositionChanged(); 
/*     */   }
/*     */   
/*     */   public WorldCoordinate getPosition() {
/* 134 */     return this.position;
/*     */   }
/*     */   
/*     */   public void translate(Vector2d translationVector) {
/* 138 */     this.position.addOffset(translationVector);
/* 139 */     firePositionChanged();
/*     */ 
/*     */     
/* 142 */     if (!translationVector.isNull())
/* 143 */       setRotation((float)translationVector.angle()); 
/*     */   }
/*     */   
/*     */   public boolean isVisible() {
/* 147 */     return this.visible;
/*     */   }
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 151 */     boolean changed = (this.visible != visible);
/* 152 */     this.visible = visible;
/* 153 */     if (changed) {
/* 154 */       fireVisibilityChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotation(float rotation) {
/* 163 */     boolean changed = (this.rotation != rotation);
/* 164 */     this.rotation = rotation;
/* 165 */     if (changed)
/* 166 */       fireRotationChanged(); 
/*     */   }
/*     */   
/*     */   public float getRotation() {
/* 170 */     return this.rotation;
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(float time) {}
/*     */   
/*     */   public String toString() {
/* 177 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getMappedDfx(String key) {
/* 181 */     return key;
/*     */   }
/*     */   
/*     */   public void setDead(boolean dead) {
/* 185 */     this.dead = dead;
/*     */   }
/*     */   
/*     */   public boolean isDead() {
/* 189 */     return this.dead;
/*     */   }
/*     */   
/*     */   public float angleTo(Prop prop) {
/* 193 */     return (float)getPosition().angleTo(prop.getPosition());
/*     */   }
/*     */   
/*     */   public float distanceTo(Prop prop) {
/* 197 */     return (float)getPosition().distanceTo(prop.getPosition());
/*     */   }
/*     */   
/*     */   public static abstract class PropChangeAdapter implements PropChangeListener {
/*     */     public void positionChanged(Prop prop, WorldCoordinate newPosition) {}
/*     */     
/*     */     public void rotationChanged(Prop prop, float newAngle) {}
/*     */     
/*     */     public void visibilityChanged(Prop prop, boolean visible) {}
/*     */     
/*     */     public void nameChanged(Prop prop, String newName) {}
/*     */     
/*     */     public void propertyChanged(Prop prop, String propertyName, Object newValue, Object oldValue) {}
/*     */   }
/*     */   
/*     */   public static interface PropChangeListener {
/*     */     void positionChanged(Prop param1Prop, WorldCoordinate param1WorldCoordinate);
/*     */     
/*     */     void rotationChanged(Prop param1Prop, float param1Float);
/*     */     
/*     */     void visibilityChanged(Prop param1Prop, boolean param1Boolean);
/*     */     
/*     */     void nameChanged(Prop param1Prop, String param1String);
/*     */     
/*     */     void propertyChanged(Prop param1Prop, String param1String, Object param1Object1, Object param1Object2);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\props\Prop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */