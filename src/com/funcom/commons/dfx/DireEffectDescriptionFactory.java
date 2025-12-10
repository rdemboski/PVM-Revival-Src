/*     */ package com.funcom.commons.dfx;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.jdom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DireEffectDescriptionFactory
/*     */ {
/*  14 */   public static final DireEffectDescription EMPTY_DFX = new DireEffectDescription("DO_NOTHING");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean cacheDFX = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  26 */   private static Map<String, String> builtinActionDFX = new HashMap<String, String>(); public static final String EMPTY_DFX_REF = "DO_NOTHING"; private DireEffectResourceLoader resourceFetcher; static {
/*  27 */     builtinActionDFX.put("idle", "xml/dfx/idle.xml");
/*  28 */     builtinActionDFX.put("move", "xml/dfx/move.xml");
/*  29 */     builtinActionDFX.put("interact", "xml/dfx/interact.xml");
/*  30 */     builtinActionDFX.put("die", "xml/dfx/die.xml");
/*  31 */     builtinActionDFX.put("rotate", "DO_NOTHING");
/*  32 */     builtinActionDFX.put("use-item", "DO_NOTHING");
/*  33 */     builtinActionDFX.put("zone", "DO_NOTHING");
/*  34 */     builtinActionDFX.put("loot", "DO_NOTHING");
/*  35 */     builtinActionDFX.put("invoke-dfx", "DO_NOTHING");
/*  36 */     builtinActionDFX.put("open-vendor-gui", "DO_NOTHING");
/*  37 */     builtinActionDFX.put("quest-window", "DO_NOTHING");
/*  38 */     builtinActionDFX.put("talk", "DO_NOTHING");
/*  39 */     builtinActionDFX.put("pickup-pickedup", "DO_NOTHING");
/*  40 */     builtinActionDFX.put("pickup-spawn", "DO_NOTHING");
/*     */   }
/*     */   private EffectDescriptionFactory descriptionFactory; private Map<String, DireEffectDescription> runtimeDescriptions; private Map<String, DireEffectDescription> defaultDescriptions;
/*     */   public DireEffectDescriptionFactory(DireEffectResourceLoader resourceFetcher) {
/*  44 */     this.resourceFetcher = resourceFetcher;
/*  45 */     this.runtimeDescriptions = new HashMap<String, DireEffectDescription>();
/*  46 */     this.defaultDescriptions = new HashMap<String, DireEffectDescription>();
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
/*     */   
/*     */   public DireEffectDescription getDireEffectDescription(String dfxScript, boolean impact) throws NoSuchDFXException {
/*  59 */     DireEffectDescription dfxDescription = this.defaultDescriptions.get(dfxScript);
/*  60 */     if (dfxDescription != null) {
/*  61 */       return dfxDescription;
/*     */     }
/*     */     
/*  64 */     return getRuntimeDireEffectDescription(dfxScript, impact);
/*     */   }
/*     */   
/*     */   private DireEffectDescription getRuntimeDireEffectDescription(String dfxScript, boolean impact) throws NoSuchDFXException {
/*  68 */     DireEffectDescription dfxDescription = this.runtimeDescriptions.get(dfxScript);
/*  69 */     if (dfxDescription == null) {
/*     */       
/*  71 */       if (builtinActionDFX.containsKey(dfxScript)) {
/*  72 */         dfxScript = builtinActionDFX.get(dfxScript);
/*     */       }
/*     */ 
/*     */       
/*  76 */       if (dfxScript == "DO_NOTHING") {
/*  77 */         dfxDescription = EMPTY_DFX;
/*     */       } else {
/*  79 */         Element effectRootElement = this.resourceFetcher.getDireEffectData(dfxScript, cacheDFX());
/*  80 */         dfxDescription = getDireEffectDescription(effectRootElement, dfxScript, impact);
/*     */       }
/*     */     
/*  83 */     } else if (!cacheDFX()) {
/*  84 */       removeRuntimeDireEffectDescription(dfxDescription);
/*  85 */       getDireEffectDescription(dfxScript, impact);
/*     */     } 
/*     */     
/*  88 */     if (dfxDescription == null) {
/*  89 */       throw new NoSuchDFXException(dfxScript);
/*     */     }
/*     */     
/*  92 */     return dfxDescription;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkDFXDescription(DireEffectDescription ret, boolean impact) {}
/*     */ 
/*     */   
/*     */   public void putDefaultDireEffectDescription(DireEffectDescription dfxDescription) {
/* 100 */     this.defaultDescriptions.put(dfxDescription.getId(), dfxDescription);
/*     */   }
/*     */   
/*     */   private void removeRuntimeDireEffectDescription(DireEffectDescription dfxDescription) {
/* 104 */     this.runtimeDescriptions.remove(dfxDescription.getId());
/*     */   }
/*     */   
/*     */   protected DireEffectDescription newDFXDescription(String dfxScript, boolean impact) {
/* 108 */     return new DireEffectDescription(dfxScript);
/*     */   }
/*     */   
/*     */   public void setDescriptionFactory(EffectDescriptionFactory descriptionFactory) {
/* 112 */     this.descriptionFactory = descriptionFactory;
/*     */   }
/*     */   
/*     */   public void setCacheDFX(boolean cacheDFX) {
/* 116 */     this.cacheDFX = cacheDFX;
/*     */   }
/*     */   
/*     */   public boolean cacheDFX() {
/* 120 */     return this.cacheDFX;
/*     */   }
/*     */   
/*     */   public DireEffectDescription getDireEffectDescription(Element effectRootElement, String id, boolean impact) {
/* 124 */     DireEffectDescription dfxDescription = null;
/* 125 */     if (effectRootElement != null) {
/* 126 */       dfxDescription = newDFXDescription(id, impact);
/*     */       
/* 128 */       List<Element> children = effectRootElement.getChildren("Effect");
/* 129 */       for (Element childElement : children) {
/* 130 */         EffectDescription singleEffectDesc = this.descriptionFactory.createEffect(childElement, this.cacheDFX);
/* 131 */         if (singleEffectDesc != null) {
/* 132 */           singleEffectDesc.setResourceFetcher(this.resourceFetcher);
/* 133 */           dfxDescription.addEffect(singleEffectDesc);
/*     */         } 
/*     */       } 
/*     */       
/* 137 */       checkDFXDescription(dfxDescription, impact);
/*     */       
/* 139 */       if (cacheDFX()) {
/* 140 */         putRuntimeDireEffectDescription(dfxDescription);
/*     */       }
/*     */     } 
/* 143 */     return dfxDescription;
/*     */   }
/*     */   
/*     */   private void putRuntimeDireEffectDescription(DireEffectDescription dfxDescription) {
/* 147 */     this.runtimeDescriptions.put(dfxDescription.getId(), dfxDescription);
/*     */   }
/*     */   
/*     */   public void clearRuntimeDescriptions() {
/* 151 */     this.runtimeDescriptions.clear();
/*     */   }
/*     */   
/*     */   public Map<String, String> getBuiltInDfxs() {
/* 155 */     return Collections.unmodifiableMap(builtinActionDFX);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\dfx\DireEffectDescriptionFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */