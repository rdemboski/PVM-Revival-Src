/*     */ package com.funcom.tcg.client.model;
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.util.DebugManager;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class PropNodeRegister {
/*  12 */   private static final Logger LOG = Logger.getLogger(PropNodeRegister.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  17 */   private Map<Integer, PropNode> propMap = new ConcurrentHashMap<Integer, PropNode>();
/*  18 */   private List<PropNode> propList = Collections.synchronizedList(new ArrayList<PropNode>(256));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPropNode(PropNode propNode) {
/*  28 */     InteractibleProp intProp = (InteractibleProp)propNode.getProp();
/*  29 */     if (this.propMap.containsKey(Integer.valueOf(intProp.getId()))) {
/*  30 */       String message = "Prop id already exists: " + intProp.getId() + " for prop name: " + intProp.getName() + " Prop: " + ((PropNode)this.propMap.get(Integer.valueOf(intProp.getId()))).getName() + " already exists";
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  35 */       DebugManager.getInstance().warn(message);
/*  36 */       PropNode tmp = this.propMap.get(Integer.valueOf(intProp.getId()));
/*  37 */       tmp.removeFromParent();
/*     */     } 
/*     */     
/*  40 */     this.propMap.put(Integer.valueOf(intProp.getId()), propNode);
/*  41 */     this.propList.add(propNode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PropNode getPropNode(Integer id) {
/*  52 */     return this.propMap.get(id);
/*     */   }
/*     */   
/*     */   public PropNode getPropNodeByName(String name) {
/*  56 */     if (name == null) {
/*  57 */       LOG.error("Given name is NULL!");
/*  58 */       return null;
/*     */     } 
/*     */     
/*  61 */     for (Map.Entry<Integer, PropNode> entry : this.propMap.entrySet()) {
/*  62 */       PropNode propNode = entry.getValue();
/*  63 */       if (name.equals(propNode.getProp().getName())) {
/*  64 */         return propNode;
/*     */       }
/*     */     } 
/*  67 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public PropNode getPropNode(InteractibleProp interactibleProp) {
/*  72 */     return getPropNode(Integer.valueOf(interactibleProp.getId()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePropNode(PropNode prop) {
/*  81 */     if (prop == null) {
/*     */       return;
/*     */     }
/*     */     
/*  85 */     this.propMap.remove(Integer.valueOf(((InteractibleProp)prop.getProp()).getId()));
/*  86 */     this.propList.remove(prop);
/*     */   }
/*     */   
/*     */   public void removeAllFromParents() {
/*  90 */     for (PropNode propNode : this.propList) {
/*  91 */       propNode.removeFromParent();
/*     */     }
/*     */     
/*  94 */     this.propMap.clear();
/*  95 */     this.propList.clear();
/*     */   }
/*     */   
/*     */   public Map<Integer, PropNode> getPropMap() {
/*  99 */     return this.propMap;
/*     */   }
/*     */   
/*     */   public List<PropNode> getPropList() {
/* 103 */     return this.propList;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\PropNodeRegister.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */