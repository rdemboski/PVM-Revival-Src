/*     */ package com.funcom.gameengine.view;
/*     */ 
/*     */ import com.funcom.commons.jme.md5importer.JointAnimation;
/*     */ import com.funcom.commons.jme.md5importer.ModelNode;
/*     */ import com.funcom.commons.jme.md5importer.resource.mesh.Joint;
/*     */ import com.funcom.commons.jme.md5importer.resource.mesh.Mesh;
/*     */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*     */ import com.funcom.gameengine.model.GraphicsConfig;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.jme.bounding.BoundingVolume;
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.math.Quaternion;
/*     */ import com.jme.math.TransformMatrix;
/*     */ import com.jme.scene.Geometry;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.TriMesh;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class ModularNode
/*     */   extends Node
/*     */   implements ModularDescription.ModularChangedListener {
/*     */   public static boolean projectTechdemo = false;
/*  36 */   private static final Logger LOGGER = Logger.getLogger(ModularNode.class.getName());
/*     */   public static final String MAPPING_PREFIX = "MAP.";
/*     */   private SortedMap<Integer, Map<String, ModelNode>> modelQueues;
/*     */   protected Map<String, JointAnimation> animations;
/*     */   protected ModelNode playerModel;
/*     */   protected ModularDescription modularDescription;
/*     */   protected AnimationMapper animationMapper;
/*     */   protected ResourceManager resourceManager;
/*     */   protected float scale;
/*     */   private TextureLoaderManager textureLoaderManager;
/*     */   private Map<Object, Object> textureLoaderRuntimeParams;
/*     */   
/*     */   public ModularNode(ModularDescription modularDescription, AnimationMapper animationMapper, Quaternion rotation, float scale, ResourceManager resourceManager) {
/*  49 */     super("ModularNode");
/*  50 */     this.modelQueues = new TreeMap<Integer, Map<String, ModelNode>>();
/*  51 */     this.animations = new HashMap<String, JointAnimation>();
/*  52 */     animationMapper.setAnimationPlayer(new ModularAnimationPlayer());
/*  53 */     setLocalRotation(rotation);
/*  54 */     modularDescription.addChangedListener(this);
/*  55 */     this.scale = scale;
/*  56 */     this.animationMapper = animationMapper;
/*  57 */     this.modularDescription = modularDescription;
/*  58 */     this.resourceManager = resourceManager;
/*     */   }
/*     */   
/*     */   public ModelNode getPlayerModel() {
/*  62 */     return this.playerModel;
/*     */   }
/*     */   
/*     */   public ModelNode getPetModel() {
/*  66 */     return null;
/*     */   }
/*     */   
/*     */   public void setPart(String partName, ModelNode modelNode, int queue) {
/*  70 */     LOGGER.debug("Set part: " + partName + " - " + modelNode);
/*  71 */     if (modelNode == null) {
/*  72 */       removePart(partName);
/*     */       
/*     */       return;
/*     */     } 
/*  76 */     for (Map<String, ModelNode> map : this.modelQueues.values()) {
/*  77 */       map.remove(partName);
/*     */     }
/*     */     
/*  80 */     Map<String, ModelNode> modelNodes = this.modelQueues.get(Integer.valueOf(queue));
/*  81 */     if (modelNodes == null) {
/*  82 */       modelNodes = new HashMap<String, ModelNode>();
/*  83 */       this.modelQueues.put(Integer.valueOf(queue), modelNodes);
/*     */     } 
/*     */     
/*  86 */     modelNodes.put(partName, modelNode);
/*     */   }
/*     */   
/*     */   public ModelNode getPart(String partName) {
/*  90 */     for (Map<String, ModelNode> modelNodes : this.modelQueues.values()) {
/*  91 */       ModelNode modelNode = modelNodes.get(partName);
/*  92 */       if (modelNode != null) {
/*  93 */         return modelNode;
/*     */       }
/*     */     } 
/*  96 */     return null;
/*     */   }
/*     */   
/*     */   public Set<String> getCurrentPartNames() {
/* 100 */     Set<String> result = new HashSet<String>();
/* 101 */     for (Map<String, ModelNode> modelNodes : this.modelQueues.values()) {
/* 102 */       for (String partName : modelNodes.keySet()) {
/* 103 */         result.add(partName);
/*     */       }
/*     */     } 
/*     */     
/* 107 */     return result;
/*     */   }
/*     */   
/*     */   public void removePart(String partName) {
/* 111 */     LOGGER.debug("Remove part: " + partName);
/*     */     
/* 113 */     for (Map<String, ModelNode> modelNodes : this.modelQueues.values()) {
/* 114 */       modelNodes.remove(partName);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeAllParts() {
/* 119 */     this.modelQueues.clear();
/* 120 */     detachAllChildren();
/*     */   }
/*     */ 
/*     */   
/*     */   public void rebuildModel() {
/* 125 */     detachAllChildren();
/* 126 */     if (this.playerModel != null) {
/* 127 */       this.playerModel.detachAllDependent();
/* 128 */       this.playerModel.setLocalRotation(new Quaternion());
/*     */     } 
/*     */     
/* 131 */     if (this.modelQueues.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 135 */     Iterator<Map<String, ModelNode>> queueIterator = this.modelQueues.values().iterator();
/* 136 */     Iterator<ModelNode> partIterator = ((Map)queueIterator.next()).values().iterator();
/* 137 */     this.playerModel = partIterator.next();
/* 138 */     attachChild((Spatial)this.playerModel);
/* 139 */     LOGGER.debug("Base model part: " + this.playerModel);
/*     */     
/* 141 */     while (partIterator.hasNext()) {
/* 142 */       ModelNode modelPart = partIterator.next();
/* 143 */       this.playerModel.attachDependent(modelPart);
/*     */       
/* 145 */       LOGGER.debug("Subset model part: " + modelPart);
/*     */       
/* 147 */       if (!partIterator.hasNext() && queueIterator.hasNext()) {
/* 148 */         partIterator = ((Map)queueIterator.next()).values().iterator();
/*     */       }
/*     */     } 
/*     */     
/* 152 */     for (JointAnimation animation : this.animations.values()) {
/* 153 */       this.playerModel.addAnimation(animation);
/*     */     }
/*     */     
/* 156 */     updateRenderState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocalScale(float localScale) {
/* 163 */     super.setLocalScale(localScale * this.modularDescription.getScale() * this.scale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void startTiming() {}
/*     */ 
/*     */ 
/*     */   
/*     */   private static void endTiming() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void reloadCharacter() {
/* 178 */     Set<ModularDescription.Part> parts = this.modularDescription.getBodyParts();
/* 179 */     for (ModularDescription.Part part : parts) {
/* 180 */       reloadPart(part);
/*     */     }
/* 182 */     rebuildModel();
/* 183 */     reloadAnimations();
/*     */   }
/*     */   
/*     */   private void reloadPart(ModularDescription.Part part) {
/* 187 */     String partName = part.getPartName();
/* 188 */     String meshPath = part.getMeshPath();
/* 189 */     LOGGER.debug("Reload part: " + partName + " - " + meshPath);
/*     */     
/* 191 */     if (meshPath == null && part.isVisible()) {
/* 192 */       meshPath = getPathFromMeshData(partName);
/*     */     }
/*     */     
/* 195 */     if (meshPath == null && getPart(partName) != null) {
/* 196 */       removePart(partName);
/*     */     } else {
/* 198 */       ModelNode modelNode = loadPart(meshPath, part);
/* 199 */       boolean transparent = hasTransparency(part);
/* 200 */       setPart(partName, modelNode, transparent ? 1 : 0);
/*     */     } 
/*     */     
/* 203 */     updateRenderState();
/*     */   }
/*     */ 
/*     */   
/*     */   protected ModelNode loadPart(String meshPath, ModularDescription.Part part) {
/* 208 */     if (meshPath != null) {
/* 209 */       ModelNode modelNode; String partName = part.getPartName();
/*     */ 
/*     */ 
/*     */       
/* 213 */       startTiming();
/* 214 */       if (projectTechdemo) {
/* 215 */         modelNode = (ModelNode)this.resourceManager.getResource(ModelNode.class, meshPath, CacheType.NOT_CACHED);
/*     */       } else {
/* 217 */         modelNode = ((ModelNode)this.resourceManager.getResource(ModelNode.class, meshPath, CacheType.CACHE_TEMPORARILY)).clone();
/* 218 */       }  endTiming();
/*     */       
/* 220 */       modelNode.setName(modelNode.getName() + "." + partName);
/* 221 */       if (this.textureLoaderManager != null) {
/* 222 */         this.textureLoaderManager.replaceTexture(modelNode.getMeshes(), part, this.textureLoaderRuntimeParams);
/*     */       }
/*     */       
/* 225 */       GraphicsConfig config = GraphicsConfig.getModelConfig(this.resourceManager, meshPath);
/* 226 */       modelNode.setRenderState(config.getBlendMode().getRenderState());
/*     */       
/* 228 */       assignTextures(modelNode, part.getTextureParts());
/* 229 */       return modelNode;
/*     */     } 
/*     */     
/* 232 */     return null;
/*     */   }
/*     */   
/*     */   protected String getPathFromMeshData(String partName) {
/* 236 */     return null;
/*     */   }
/*     */   
/*     */   private boolean hasTransparency(ModularDescription.Part part) {
/* 240 */     for (ModularDescription.TexturePart texturePart : part.getTextureParts()) {
/* 241 */       if (texturePart.isTransparent()) {
/* 242 */         return true;
/*     */       }
/*     */     } 
/* 245 */     return false;
/*     */   }
/*     */   
/*     */   public void reloadPart(String partName) {
/* 249 */     reloadPart(this.modularDescription.getBodyPart(partName));
/*     */   }
/*     */   
/*     */   public void reloadAnimations() {
/* 253 */     Set<ModularDescription.Animation> animations = this.modularDescription.getAnimations();
/* 254 */     for (ModularDescription.Animation animation : animations) {
/* 255 */       JointAnimation playerAnimation = getAnimation(animation, animation.getPlayerAnimationPath());
/* 256 */       int initialFrameRate = animation.getPlayerFrameRate();
/* 257 */       if (initialFrameRate > 0)
/* 258 */         playerAnimation.setInitialFrameRate(initialFrameRate); 
/* 259 */       this.animations.put(playerAnimation.getName(), playerAnimation);
/*     */       try {
/* 261 */         this.playerModel.addAnimation(playerAnimation);
/* 262 */       } catch (Exception e) {
/* 263 */         throw new IllegalStateException("Error loading animation: " + playerAnimation, e);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 268 */     this.animationMapper.playAnimation(this.animationMapper.getPlayingAnimationName(), true);
/* 269 */     updateGeometricState(0.0F, true);
/*     */   }
/*     */   
/*     */   protected JointAnimation getAnimation(ModularDescription.Animation animation, String playerAnimationPath) {
/* 273 */     JointAnimation jointAnimationPrototype = (JointAnimation)this.resourceManager.getResource(JointAnimation.class, playerAnimationPath, CacheType.CACHE_TEMPORARILY);
/* 274 */     JointAnimation jointAnimation = jointAnimationPrototype.clone();
/*     */     
/* 276 */     jointAnimation.setName(animation.getAnimationName());
/* 277 */     return jointAnimation;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void assignTextures(ModelNode modelNode, List<ModularDescription.TexturePart> textureParts) {
/* 283 */     if (textureParts == null || textureParts.isEmpty()) {
/*     */       return;
/*     */     }
/* 286 */     for (Mesh mesh : modelNode.getMeshes()) {
/* 287 */       for (ModularDescription.TexturePart texturePart : textureParts) {
/* 288 */         if (texturePart.getTextureMap() == null || texturePart.getTextureMap().equals(getMeshMapping(mesh))) {
/*     */           
/* 290 */           if (mesh.getTrimesh() == null)
/*     */             continue; 
/* 292 */           setupTexureCoordinates((Geometry)mesh.getTrimesh(), texturePart.getTextureLayerCount());
/* 293 */           TextureState textureState = getBatchTextureState(mesh.getTrimesh());
/* 294 */           startTiming();
/* 295 */           for (int i = 0; i < texturePart.getTextureLayerCount(); i++) {
/* 296 */             String textureName = texturePart.getTextureLayers().get(i);
/* 297 */             Texture texture = (Texture)this.resourceManager.getResource(Texture.class, textureName, CacheType.CACHE_TEMPORARILY);
/* 298 */             textureState.setTexture(texture, i);
/*     */           } 
/* 300 */           endTiming();
/* 301 */           if (texturePart.isTransparent()) {
/* 302 */             mesh.getTrimesh().setRenderState((RenderState)TransparentAlphaState.get());
/* 303 */             mesh.getTrimesh().setRenderQueueMode(3);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void printTextureParts(ModelNode modelNode, List<ModularDescription.TexturePart> textureParts) {
/* 311 */     System.out.println("--- ModularNode.assignTextures ---");
/* 312 */     StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
/* 313 */     for (int i = 1; i < Math.min(10, stackTraceElements.length); i++) {
/* 314 */       System.out.println(stackTraceElements[i].toString());
/*     */     }
/* 316 */     System.out.println("Name: " + modelNode.getName() + "\nParts: " + textureParts.size());
/*     */     
/* 318 */     for (int i1 = 0; i1 < textureParts.size(); i1++) {
/* 319 */       ModularDescription.TexturePart texturePart = textureParts.get(i1);
/* 320 */       System.out.println("  --- Part " + i1 + " ---");
/* 321 */       System.out.println("  Class: " + texturePart.getClass());
/* 322 */       System.out.println("  Map: " + texturePart.getTextureMap() + "\n  Layers: " + texturePart.getTextureLayerCount());
/*     */       
/* 324 */       for (String layer : texturePart.getTextureLayers()) {
/* 325 */         System.out.println("    '" + layer + "'");
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setupTexureCoordinates(Geometry batch, int minimumUnits) {
/* 331 */     if (batch.getNumberOfUnits() == 0) {
/* 332 */       throw new IllegalStateException("Batch needs to have at least one texture layer.");
/*     */     }
/*     */     
/* 335 */     for (int i = batch.getNumberOfUnits(); i < minimumUnits; i++) {
/* 336 */       batch.setTextureCoords(batch.getTextureCoords());
/*     */     }
/*     */   }
/*     */   
/*     */   private TextureState getBatchTextureState(TriMesh batch) {
/* 341 */     TextureState textureState = (TextureState)batch.getRenderState(5);
/* 342 */     if (textureState == null) {
/* 343 */       textureState = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/* 344 */       batch.setRenderState((RenderState)textureState);
/*     */     } 
/* 346 */     return textureState;
/*     */   }
/*     */   
/*     */   private String getMeshMapping(Mesh mesh) {
/* 350 */     String texture = mesh.getTexture();
/* 351 */     if (texture != null && texture.startsWith("MAP.")) {
/* 352 */       return texture.substring("MAP.".length());
/*     */     }
/* 354 */     return null;
/*     */   }
/*     */   
/*     */   public void partChanged(String partName) {
/* 358 */     reloadPart(partName);
/* 359 */     rebuildModel();
/* 360 */     reloadAnimations();
/*     */   }
/*     */   
/*     */   public void dispose() {
/* 364 */     this.modularDescription.removeChangedListener(this);
/* 365 */     this.modularDescription.dispose();
/*     */   }
/*     */   
/*     */   public ModularDescription getModularDescription() {
/* 369 */     return this.modularDescription;
/*     */   }
/*     */   
/*     */   public FollowJoint getFollowJoint(String jointName) {
/* 373 */     int index = getPlayerModel().getJointIndex(jointName);
/* 374 */     TransformMatrix transformMatrix = new TransformMatrix();
/* 375 */     if (index >= 0) {
/* 376 */       Joint joint = getPlayerModel().getJoint(jointName);
/* 377 */       transformMatrix = joint.getTransform();
/*     */     } 
/* 379 */     return new FollowJoint(transformMatrix, getLocalRotation());
/*     */   }
/*     */   
/*     */   public BoundingVolume getModelBounds() {
/* 383 */     BoundingVolume ret = getUntransformedModelBounds();
/* 384 */     ret = ret.transform(getLocalRotation(), getLocalTranslation(), getLocalScale());
/* 385 */     return ret;
/*     */   }
/*     */   
/*     */   protected BoundingVolume getUntransformedModelBounds() {
/* 389 */     return this.playerModel.getModelBounds(null);
/*     */   }
/*     */   
/*     */   public void setTextureLoaderFactoryManager(TextureLoaderManager textureLoaderManager) {
/* 393 */     this.textureLoaderManager = textureLoaderManager;
/*     */   }
/*     */   
/*     */   public void setTextureLoaderRuntimeParams(Map<Object, Object> textureLoaderRuntimeParams) {
/* 397 */     this.textureLoaderRuntimeParams = textureLoaderRuntimeParams;
/*     */   }
/*     */   
/*     */   protected class ModularAnimationPlayer implements AnimationPlayer {
/*     */     private String currentAnimName;
/*     */     protected static final float FADING_TIME = 0.05F;
/*     */     
/*     */     public void play(String animationName, boolean override) {
/* 405 */       if (animationName != null && (!animationName.equals(this.currentAnimName) || override))
/*     */       {
/* 407 */         runAnimation(animationName);
/*     */       }
/*     */     }
/*     */     
/*     */     protected void runAnimation(String animationName) {
/* 412 */       if (ModularNode.this.playerModel.getAnimationNames().contains(animationName)) {
/* 413 */         ModularNode.this.playerModel.getController().setActiveAnimation(animationName, 0.05F);
/*     */       }
/*     */       
/* 416 */       this.currentAnimName = animationName;
/*     */     }
/*     */     
/*     */     public Set<String> getAnimationNames() {
/* 420 */       if (ModularNode.this.playerModel != null)
/* 421 */         return ModularNode.this.playerModel.getAnimationNames(); 
/* 422 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\ModularNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */