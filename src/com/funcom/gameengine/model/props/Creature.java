/*     */ package com.funcom.gameengine.model.props;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.ai.Brain;
/*     */ import com.funcom.gameengine.ai.BrainControlled;
/*     */ import com.funcom.gameengine.model.command.Command;
/*     */ import com.funcom.gameengine.model.command.IdleCommand;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class Creature
/*     */   extends InteractibleProp
/*     */   implements BrainControlled
/*     */ {
/*  19 */   protected static final Logger LOG = Logger.getLogger(Creature.class.getName());
/*     */   
/*     */   private Map<String, String> dfxMap;
/*     */   
/*     */   private List<CommandListener> commandListeners;
/*     */   private LinkedList<Command> commands;
/*     */   private Command currentCommand;
/*     */   private Brain brain;
/*     */   private String monsterId;
/*     */   
/*     */   public Creature(int id, String name, String monsterId, WorldCoordinate position, double radius) {
/*  30 */     super(id, name, position, radius);
/*  31 */     this.monsterId = monsterId;
/*  32 */     this.commands = new LinkedList<Command>();
/*  33 */     this.currentCommand = Command.NOOP;
/*     */   }
/*     */   
/*     */   public void addCommandListener(CommandListener commandListener) {
/*  37 */     if (this.commandListeners == null)
/*  38 */       this.commandListeners = new LinkedList<CommandListener>(); 
/*  39 */     this.commandListeners.add(commandListener);
/*     */   }
/*     */   
/*     */   public void removeCommandListener(CommandListener commandListener) {
/*  43 */     if (this.commandListeners != null)
/*  44 */       this.commandListeners.remove(commandListener); 
/*     */   }
/*     */   
/*     */   private void fireCommand(Command oldCommand, Command newCommand) {
/*  48 */     if (this.commandListeners != null)
/*  49 */       for (CommandListener listener : this.commandListeners)
/*  50 */         listener.nextCommand(this, oldCommand, newCommand);  
/*  51 */     firePropertyChangeListener(newCommand.getName(), oldCommand.getName(), newCommand.getName());
/*     */   }
/*     */   
/*     */   public void clearQueue() {
/*  55 */     this.commands.clear();
/*     */   }
/*     */   
/*     */   public void immediateCommand(Command command) {
/*  59 */     this.commands.clear();
/*  60 */     this.currentCommand = Command.NOOP;
/*  61 */     this.commands.offer(command);
/*     */   }
/*     */   
/*     */   public void immediateCommands(List<? extends Command> commands) {
/*  65 */     this.commands.clear();
/*  66 */     this.currentCommand = Command.NOOP;
/*  67 */     this.commands.addAll(commands);
/*     */   }
/*     */   
/*     */   public void queueCommand(Command command) {
/*  71 */     this.commands.add(command);
/*  72 */     LOG.info("Queue command: " + command);
/*     */   }
/*     */   
/*     */   public void queueCommands(List<? extends Command> commands) {
/*  76 */     this.commands.addAll(commands);
/*     */   }
/*     */ 
/*     */   
/*     */   public void insertCommand(Command command) {
/*  81 */     int index = this.commands.indexOf(this.currentCommand);
/*  82 */     this.commands.add(index + 1, command);
/*     */   }
/*     */ 
/*     */   
/*     */   public void insertCommands(Command commands) {
/*  87 */     int index = this.commands.indexOf(this.currentCommand);
/*  88 */     this.commands.add(index + 1, commands);
/*     */   }
/*     */   
/*     */   public boolean isDoingAnything() {
/*  92 */     return (!Command.NOOP.equals(this.currentCommand) && !this.currentCommand.getName().equals("idle"));
/*     */   }
/*     */   
/*     */   public void stopCurrentWork() {
/*  96 */     this.currentCommand = Command.NOOP;
/*     */   }
/*     */   
/*     */   public void setBrain(Brain brain) {
/* 100 */     if (brain == null)
/* 101 */       throw new IllegalArgumentException("brain = null"); 
/* 102 */     this.brain = brain;
/* 103 */     brain.setControlled(this);
/*     */   }
/*     */   
/*     */   public Brain getBrain() {
/* 107 */     return this.brain;
/*     */   }
/*     */   
/*     */   public void update(float time) {
/* 111 */     super.update(time);
/* 112 */     if (this.brain != null) {
/* 113 */       this.brain.update(time);
/*     */     }
/* 115 */     updateCommandChain(time);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateCommandChain(float time) {
/* 120 */     if (Command.NOOP.equals(this.currentCommand) || this.currentCommand.isFinished()) {
/* 121 */       nextCommand();
/*     */     }
/* 123 */     if (this.currentCommand.isFailed()) {
/* 124 */       LOG.info("Command failed: " + this.currentCommand);
/* 125 */       this.commands.clear();
/* 126 */       Command oldCommand = this.currentCommand;
/* 127 */       this.currentCommand = (Command)new IdleCommand();
/* 128 */       fireCommand(oldCommand, this.currentCommand);
/*     */     } 
/*     */     
/* 131 */     this.currentCommand.setParentCreature(this);
/* 132 */     this.currentCommand.update(time);
/*     */   }
/*     */   
/*     */   private void nextCommand() {
/* 136 */     Command oldCommand = this.currentCommand;
/* 137 */     if (this.commands.isEmpty()) {
/* 138 */       this.currentCommand = Command.NOOP;
/*     */     } else {
/* 140 */       this.currentCommand = this.commands.poll();
/*     */     } 
/*     */     
/* 143 */     if (Command.NOOP.equals(this.currentCommand)) {
/* 144 */       this.currentCommand = (Command)new IdleCommand();
/*     */     }
/* 146 */     this.currentCommand.setParentCreature(this);
/* 147 */     fireCommand(oldCommand, this.currentCommand);
/*     */   }
/*     */   
/*     */   public boolean hasQueuedCommands() {
/* 151 */     return !this.commands.isEmpty();
/*     */   }
/*     */   
/*     */   public Command getCurrentCommand() {
/* 155 */     return this.currentCommand;
/*     */   }
/*     */   
/*     */   public boolean canMove() {
/* 159 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isDoingAnythingExceptMoving() {
/* 163 */     return (isDoingAnything() && !this.currentCommand.getName().equals("move"));
/*     */   }
/*     */   
/*     */   public float getSpeed() {
/* 167 */     throw new UnsupportedOperationException("Base Creatures should not move.");
/*     */   }
/*     */   
/*     */   public float getAccelerationSpeed() {
/* 171 */     throw new UnsupportedOperationException("Base Creatures should not move.");
/*     */   }
/*     */   
/*     */   public float getTurnSpeed() {
/* 175 */     throw new UnsupportedOperationException("Base Creatures should not move.");
/*     */   }
/*     */   
/*     */   public float getDrag() {
/* 179 */     throw new UnsupportedOperationException("Base Creatures should not move.");
/*     */   }
/*     */   
/*     */   public String getMonsterId() {
/* 183 */     return this.monsterId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void clearMappedDfx() {
/* 191 */     if (this.dfxMap != null && !this.dfxMap.isEmpty()) {
/* 192 */       this.dfxMap.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   public void addMappedDfx(String key, String value) {
/* 197 */     if (key == null || value == null || key.isEmpty() || value.isEmpty())
/*     */       return; 
/* 199 */     if (this.dfxMap == null)
/* 200 */       this.dfxMap = new HashMap<String, String>(); 
/* 201 */     this.dfxMap.put(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMappedDfx(String key) {
/* 206 */     if (this.dfxMap != null) {
/* 207 */       String dfx = this.dfxMap.get(key);
/* 208 */       return (dfx != null) ? dfx : key;
/*     */     } 
/* 210 */     return super.getMappedDfx(key);
/*     */   }
/*     */   
/*     */   public static interface CommandListener {
/*     */     void nextCommand(Creature param1Creature, Command param1Command1, Command param1Command2);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\props\Creature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */