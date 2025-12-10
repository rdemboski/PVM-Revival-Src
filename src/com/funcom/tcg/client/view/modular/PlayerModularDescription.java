/*     */ package com.funcom.tcg.client.view.modular;
/*     */ 
/*     */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*     */ import com.funcom.rpgengine2.items.ItemType;
/*     */ import com.funcom.tcg.client.model.rpg.ClientEquipDoll;
/*     */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*     */ import com.funcom.tcg.client.model.rpg.ClientItemVisual;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*     */ import com.funcom.tcg.client.model.rpg.EquipChangeListener;
/*     */ import com.funcom.tcg.client.model.rpg.PlayerEventsAdapter;
/*     */ import com.funcom.tcg.client.model.rpg.PlayerEventsListener;
/*     */ import com.funcom.tcg.client.model.rpg.VisualRegistry;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class PlayerModularDescription
/*     */   extends AbstractPlayerModularDescription {
/*     */   public static final String MODELPART_LEGS = "legs";
/*     */   public static final String MODELPART_TORSO = "torso";
/*  20 */   private static final String[] MODELPART_NAMES = new String[] { "legs", "torso", "head", "hair", "helmet", "back" }; public static final String MODELPART_HEAD = "head"; public static final String MODELPART_HAIR = "hair";
/*     */   public static final String MODELPART_HELMET = "helmet";
/*     */   public static final String MODELPART_BACK = "back";
/*     */   private static final String HAIRTYPE_NORMAL = "normal";
/*     */   private static final float PLAYER_SCALE = 1.0F;
/*     */   protected final ClientPlayer clientPlayer;
/*     */   private final ModularEventListener eventListener;
/*     */   
/*     */   public PlayerModularDescription(ClientPlayer clientPlayer, VisualRegistry visualRegistry) {
/*  29 */     this(clientPlayer, visualRegistry, new DefaultPlayerVisualFactory(clientPlayer, visualRegistry, null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerModularDescription(ClientPlayer clientPlayer, VisualRegistry visualRegistry, PlayerVisualFactory playerVisualFactory) {
/*  36 */     super(clientPlayer.getPlayerDescription(), visualRegistry, playerVisualFactory);
/*     */ 
/*     */     
/*  39 */     this.clientPlayer = clientPlayer;
/*     */     
/*  41 */     this.eventListener = new ModularEventListener();
/*  42 */     clientPlayer.addPlayerEventsListener((PlayerEventsListener)this.eventListener);
/*  43 */     clientPlayer.getEquipDoll().addChangeListener(this.eventListener);
/*     */   }
/*     */   
/*     */   public void dispose() {
/*  47 */     clearChangedListeners();
/*  48 */     this.clientPlayer.removePlayerEventsListener((PlayerEventsListener)this.eventListener);
/*  49 */     this.clientPlayer.getEquipDoll().removeChangeListener(this.eventListener);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModularDescription.Part getPetModel() {
/*  54 */     ClientPet activePet = this.clientPlayer.getActivePet();
/*  55 */     if (activePet != null) {
/*  56 */       return activePet.getPetDescription().getBodyParts().iterator().next();
/*     */     }
/*  58 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Set<ModularDescription.Animation> getPetAnimations() {
/*  63 */     return this.clientPlayer.getActivePet().getPetDescription().getAnimations();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isCharacterMountedWithPet() {
/*  68 */     return this.clientPlayer.isCharacterMountedWithPet();
/*     */   }
/*     */   
/*     */   private class ModularEventListener extends PlayerEventsAdapter implements EquipChangeListener { private ModularEventListener() {}
/*     */     
/*     */     private void translatePartChanged(int placementId) {
/*  74 */       if (placementId == ItemType.EQUIP_LEGS.getEquipValue())
/*  75 */         PlayerModularDescription.this.firePartChanged("legs"); 
/*  76 */       if (placementId == ItemType.EQUIP_TORSO.getEquipValue())
/*  77 */         PlayerModularDescription.this.firePartChanged("torso"); 
/*  78 */       if (placementId == ItemType.EQUIP_BACK.getEquipValue())
/*  79 */         PlayerModularDescription.this.firePartChanged("back"); 
/*  80 */       if (placementId == ItemType.EQUIP_HEAD.getEquipValue()) {
/*  81 */         PlayerModularDescription.this.firePartChanged("head");
/*  82 */         PlayerModularDescription.this.firePartChanged("hair");
/*  83 */         PlayerModularDescription.this.firePartChanged("helmet");
/*     */       } 
/*     */     }
/*     */     
/*     */     public void itemEquipped(ClientEquipDoll clientEquipDoll, int placementId, ClientItem newItem, ClientItem oldItem) {
/*  88 */       translatePartChanged(placementId);
/*     */     }
/*     */     
/*     */     public void itemUnequipped(ClientEquipDoll clientEquipDoll, int placementId, ClientItem oldItem) {
/*  92 */       translatePartChanged(placementId);
/*     */     } }
/*     */ 
/*     */   
/*     */   private static class DefaultPlayerVisualFactory
/*     */     implements PlayerVisualFactory {
/*     */     private final ClientPlayer clientPlayer;
/*     */     private final VisualRegistry visualRegistry;
/*     */     
/*     */     private DefaultPlayerVisualFactory(ClientPlayer clientPlayer, VisualRegistry visualRegistry) {
/* 102 */       this.clientPlayer = clientPlayer;
/* 103 */       this.visualRegistry = visualRegistry;
/*     */     }
/*     */ 
/*     */     
/*     */     public ClientItemVisual getItemVisual(ItemType itemType) {
/* 108 */       ClientItem clientItem = this.clientPlayer.getEquipDoll().getItem(itemType.getEquipValue());
/*     */       
/* 110 */       if (clientItem != null) {
/* 111 */         return this.visualRegistry.getItemVisualForClassID(clientItem.getVisualId());
/*     */       }
/*     */ 
/*     */       
/* 115 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\view\modular\PlayerModularDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */