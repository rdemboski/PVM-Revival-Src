/*     */ package com.funcom.tcg.client.view.modular;
/*     */ 
/*     */ import com.funcom.gameengine.jme.modular.AbstractModularDescription;
/*     */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*     */ import com.funcom.rpgengine2.items.ItemType;
/*     */ import com.funcom.rpgengine2.items.PlayerDescription;
/*     */ import com.funcom.tcg.client.model.rpg.ClientItemVisual;
/*     */ import com.funcom.tcg.client.model.rpg.VisualRegistry;
/*     */ import com.funcom.tcg.client.view.modular.part.DefinedAnimation;
/*     */ import com.funcom.tcg.client.view.modular.part.DefinedPart;
/*     */ import com.funcom.tcg.client.view.modular.part.HeadPart;
/*     */ import com.funcom.tcg.client.view.modular.part.HiddenPart;
/*     */ import com.funcom.tcg.client.view.modular.part.ItemPart;
/*     */ import com.funcom.tcg.rpg.ClientDescriptionType;
/*     */ import com.funcom.tcg.rpg.ClientDescriptionVisual;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ public abstract class AbstractPlayerModularDescription
/*     */   extends AbstractModularDescription
/*     */ {
/*     */   public static final String MODELPART_LEGS = "legs";
/*     */   public static final String MODELPART_TORSO = "torso";
/*     */   public static final String MODELPART_HEAD = "head";
/*     */   public static final String MODELPART_HAIR = "hair";
/*  29 */   private static final String[] MODELPART_NAMES = new String[] { "legs", "torso", "head", "hair", "helmet", "back" };
/*     */   
/*     */   public static final String MODELPART_HELMET = "helmet";
/*     */   public static final String MODELPART_BACK = "back";
/*     */   private static final String HAIRTYPE_NORMAL = "normal";
/*     */   private static final float PLAYER_SCALE = 1.0F;
/*     */   protected final PlayerDescription playerDescription;
/*     */   protected final VisualRegistry visualRegistry;
/*     */   protected final PlayerVisualFactory playerVisualFactory;
/*     */   
/*     */   public AbstractPlayerModularDescription(PlayerDescription playerDescription, VisualRegistry visualRegistry, PlayerVisualFactory playerVisualFactory) {
/*  40 */     this.playerDescription = playerDescription;
/*  41 */     this.visualRegistry = visualRegistry;
/*  42 */     this.playerVisualFactory = playerVisualFactory;
/*     */   }
/*     */   
/*     */   public Set<ModularDescription.Part> getBodyParts() {
/*  46 */     Set<ModularDescription.Part> result = new HashSet<ModularDescription.Part>();
/*  47 */     for (String modelpartName : MODELPART_NAMES) {
/*  48 */       result.add(getBodyPart(modelpartName));
/*     */     }
/*  50 */     return result;
/*     */   }
/*     */   
/*     */   public Set<String> getBodyPartNames() {
/*  54 */     return new HashSet<String>(Arrays.asList(MODELPART_NAMES));
/*     */   }
/*     */   
/*     */   public ModularDescription.Part getBodyPart(String partName) {
/*  58 */     if ("legs".equals(partName)) {
/*  59 */       return (ModularDescription.Part)createItemPart(partName, ItemType.EQUIP_LEGS);
/*     */     }
/*     */     
/*  62 */     if ("torso".equals(partName)) {
/*  63 */       return (ModularDescription.Part)createItemPart(partName, ItemType.EQUIP_TORSO);
/*     */     }
/*     */     
/*  66 */     if ("head".equals(partName)) {
/*     */       
/*  68 */       ClientItemVisual itemVisual = this.playerVisualFactory.getItemVisual(ItemType.EQUIP_HEAD);
/*  69 */       if (itemVisual != null && 
/*  70 */         itemVisual.getShowHead() == ClientItemVisual.ShowState.HIDE) {
/*  71 */         return (ModularDescription.Part)new HiddenPart(partName);
/*     */       }
/*     */ 
/*     */       
/*  75 */       return (ModularDescription.Part)new HeadPart(this.playerDescription, this.visualRegistry);
/*     */     } 
/*     */     
/*  78 */     if ("hair".equals(partName)) {
/*     */       
/*  80 */       ClientItemVisual headVisual = this.playerVisualFactory.getItemVisual(ItemType.EQUIP_HEAD);
/*  81 */       String hairType = "normal";
/*  82 */       if (headVisual != null) {
/*  83 */         hairType = headVisual.getHairType();
/*     */         
/*  85 */         if (headVisual.getShowHair() == ClientItemVisual.ShowState.HIDE) {
/*  86 */           return (ModularDescription.Part)new HiddenPart(partName);
/*     */         }
/*     */       } 
/*     */       
/*  90 */       ClientDescriptionVisual hairVisual = this.visualRegistry.getDescriptionVisualForClassID(ClientDescriptionType.HAIR, this.playerDescription.getHairId() + "-" + hairType);
/*     */       
/*  92 */       ClientDescriptionVisual hairColorVisual = this.visualRegistry.getDescriptionVisualForClassID(ClientDescriptionType.HAIRCOLOR, this.playerDescription.getHairColorId());
/*     */ 
/*     */       
/*  95 */       String hairMesh = hairVisual.getMeshPath(this.playerDescription.getGender());
/*  96 */       String hairTexture = hairColorVisual.getTexturePath(this.playerDescription.getGender());
/*     */       
/*  98 */       return (ModularDescription.Part)new DefinedPart(partName, hairMesh, hairTexture, false);
/*     */     } 
/*     */     
/* 101 */     if ("helmet".equals(partName)) {
/* 102 */       return (ModularDescription.Part)createItemPart(partName, ItemType.EQUIP_HEAD);
/*     */     }
/*     */     
/* 105 */     if ("back".equals(partName)) {
/* 106 */       return (ModularDescription.Part)createItemPart(partName, ItemType.EQUIP_BACK);
/*     */     }
/*     */     
/* 109 */     throw new IllegalStateException("Unknown model part");
/*     */   }
/*     */   
/*     */   private ItemPart createItemPart(String partName, ItemType itemType) {
/* 113 */     ClientItemVisual itemVisual = this.playerVisualFactory.getItemVisual(itemType);
/* 114 */     return new ItemPart(partName, itemVisual, this.playerDescription.getGender());
/*     */   }
/*     */   
/*     */   public Set<ModularDescription.Animation> getAnimations() {
/* 118 */     Set<ModularDescription.Animation> animations = new HashSet<ModularDescription.Animation>();
/* 119 */     if (isCharacterMountedWithPet()) {
/* 120 */       return getPetAnimations();
/*     */     }
/* 122 */     Map<String, VisualRegistry.AnimationData> playerAnimations = this.visualRegistry.getDefaultAnimations();
/* 123 */     for (String animationName : playerAnimations.keySet()) {
/* 124 */       animations.add(new DefinedAnimation(animationName, ((VisualRegistry.AnimationData)playerAnimations.get(animationName)).getAnimation(this.playerDescription.getGender())));
/*     */     }
/*     */ 
/*     */     
/* 128 */     return animations;
/*     */   }
/*     */   
/*     */   public float getScale() {
/* 132 */     return 1.0F;
/*     */   }
/*     */   
/*     */   protected abstract Set<ModularDescription.Animation> getPetAnimations();
/*     */   
/*     */   protected abstract boolean isCharacterMountedWithPet();
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\view\modular\AbstractPlayerModularDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */