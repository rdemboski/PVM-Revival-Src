/*     */ package com.funcom.gameengine.model.props;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.model.Identity;
/*     */ import com.funcom.gameengine.model.action.Action;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class InteractibleProp
/*     */   extends Prop
/*     */   implements Identity
/*     */ {
/*     */   private int id;
/*     */   private Set<ActionListener> actionListeners;
/*     */   private Set<UpdateListener> updateListeners;
/*     */   private Set<Action> actions;
/*     */   private Set<InvokedAction> runningActions;
/*  19 */   private Set<UpdateListener> removedUpdateListeners = new HashSet<UpdateListener>();
/*  20 */   private Set<ActionListener> removedActionListeners = new HashSet<ActionListener>();
/*  21 */   private double radius = -1.0D;
/*     */   
/*     */   public InteractibleProp(int id, String name, double radius) {
/*  24 */     super(name);
/*  25 */     this.id = id;
/*  26 */     this.radius = radius;
/*  27 */     this.runningActions = new HashSet<InvokedAction>();
/*     */   }
/*     */   
/*     */   public InteractibleProp(int id, String name, WorldCoordinate position, double radius) {
/*  31 */     super(name, position);
/*  32 */     this.id = id;
/*  33 */     this.radius = radius;
/*  34 */     this.runningActions = new HashSet<InvokedAction>();
/*     */   }
/*     */   
/*     */   public void addAction(Action action) {
/*  38 */     if (this.actions == null)
/*  39 */       this.actions = new HashSet<Action>(); 
/*  40 */     this.actions.add(action);
/*     */   }
/*     */   
/*     */   public void removeAction(Action action) {
/*  44 */     if (this.actions != null)
/*  45 */       this.actions.remove(action); 
/*     */   }
/*     */   
/*     */   public Set<Action> getActions() {
/*  49 */     if (this.actions == null)
/*  50 */       this.actions = new HashSet<Action>(); 
/*  51 */     return this.actions;
/*     */   }
/*     */   
/*     */   public <T extends Action> T getActionByClass(Class<T> aClass) {
/*  55 */     for (Action action : getActions()) {
/*  56 */       if (action.getClass().equals(aClass)) {
/*  57 */         return (T)action;
/*     */       }
/*     */     } 
/*  60 */     throw new IllegalStateException("No action found, type: " + aClass);
/*     */   }
/*     */   
/*     */   public <T extends Action> boolean hasAction(Class<T> aClass) {
/*  64 */     for (Action action : getActions()) {
/*  65 */       if (action.getClass().equals(aClass)) {
/*  66 */         return true;
/*     */       }
/*     */     } 
/*  69 */     return false;
/*     */   }
/*     */   
/*     */   public Action getActionByName(String name) {
/*  73 */     for (Action action : this.actions) {
/*  74 */       if (action.getName().equals(name))
/*  75 */         return action; 
/*  76 */     }  throw new IllegalStateException("No action defined: " + name);
/*     */   }
/*     */   
/*     */   public boolean hasAction(String name) {
/*  80 */     for (Action action : getActions()) {
/*  81 */       if (action.getName().equals(name)) {
/*  82 */         return true;
/*     */       }
/*     */     } 
/*  85 */     return false;
/*     */   }
/*     */   
/*     */   public void clearActions() {
/*  89 */     if (this.actions != null) {
/*  90 */       this.actions.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   public void invokeAction(String name, InteractibleProp invoker, String visual) {
/*  95 */     invokeAction(getActionByName(name), invoker, visual);
/*     */   }
/*     */ 
/*     */   
/*     */   public void invokeAction(Action action, InteractibleProp invoker, String visual) {
/* 100 */     this.runningActions.add(new InvokedAction(action, invoker));
/* 101 */     fireActionInvoked(action, visual);
/*     */   }
/*     */   
/*     */   public void addActionListener(ActionListener actionListener) {
/* 105 */     if (this.actionListeners == null)
/* 106 */       this.actionListeners = new HashSet<ActionListener>(); 
/* 107 */     this.actionListeners.add(actionListener);
/*     */   }
/*     */   
/*     */   public void removeActionListener(ActionListener actionListener) {
/* 111 */     this.removedActionListeners.add(actionListener);
/*     */   }
/*     */ 
/*     */   
/*     */   private void fireActionInvoked(Action action, String visual) {
/* 116 */     if (this.actionListeners != null)
/* 117 */       for (ActionListener actionListener : this.actionListeners)
/* 118 */         actionListener.actionInvoked(action);  
/* 119 */     firePropertyChangeListener(action.getName(), visual, visual);
/*     */   }
/*     */   
/*     */   public void addUpdateListener(UpdateListener actionListener) {
/* 123 */     if (this.updateListeners == null)
/* 124 */       this.updateListeners = new HashSet<UpdateListener>(); 
/* 125 */     this.updateListeners.add(actionListener);
/*     */   }
/*     */   
/*     */   public void removeUpdateListener(UpdateListener actionListener) {
/* 129 */     this.removedUpdateListeners.add(actionListener);
/*     */   }
/*     */   
/*     */   private void fireUpdated() {
/* 133 */     if (this.updateListeners != null)
/* 134 */       for (UpdateListener updateListener : this.updateListeners)
/* 135 */         updateListener.updated();  
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/* 139 */     this.id = id;
/*     */   }
/*     */   
/*     */   public int getId() {
/* 143 */     return this.id;
/*     */   }
/*     */   
/*     */   public void update(float time) {
/* 147 */     updateActions();
/* 148 */     fireUpdated();
/* 149 */     cleanup();
/*     */   }
/*     */   
/*     */   private void cleanup() {
/* 153 */     if (this.updateListeners != null) {
/* 154 */       this.updateListeners.removeAll(this.removedUpdateListeners);
/*     */     }
/* 156 */     this.removedUpdateListeners.clear();
/* 157 */     if (this.actionListeners != null) {
/* 158 */       this.actionListeners.removeAll(this.removedActionListeners);
/*     */     }
/* 160 */     this.removedActionListeners.clear();
/*     */   }
/*     */   
/*     */   private void updateActions() {
/* 164 */     if (this.runningActions.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 168 */     Iterator<InvokedAction> it = this.runningActions.iterator();
/* 169 */     while (it.hasNext()) {
/* 170 */       InvokedAction invokedAction = it.next();
/* 171 */       invokedAction.action.setParent(this);
/* 172 */       invokedAction.action.perform(invokedAction.invoker);
/* 173 */       it.remove();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getRadius() {
/* 182 */     if (this.radius < 0.0D)
/* 183 */       throw new IllegalStateException("Radius not set!"); 
/* 184 */     return this.radius;
/*     */   }
/*     */   
/*     */   private static class InvokedAction {
/*     */     Action action;
/*     */     InteractibleProp invoker;
/*     */     
/*     */     private InvokedAction(Action action, InteractibleProp invoker) {
/* 192 */       this.action = action;
/* 193 */       this.invoker = invoker;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\props\InteractibleProp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */