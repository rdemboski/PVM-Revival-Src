/*    */ package com.funcom.tcg.client.dfx;
/*    */ 
/*    */ import com.funcom.commons.dfx.DireEffectResourceLoader;
/*    */ import com.funcom.commons.jme.md5importer.JointAnimation;
/*    */ import com.funcom.commons.jme.md5importer.ModelNode;
/*    */ import com.funcom.gameengine.resourcemanager.CacheType;
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.turborilla.jops.jme.ParticleProcessor;
/*    */ import org.jdom.Document;
/*    */ import org.jdom.Element;
/*    */ import org.softmed.jops.ParticleSystem;
/*    */ import org.softmed.jops.cloner.Cloner;
/*    */ import org.softmed.jops.cloner.DefaultCloner;
/*    */ 
/*    */ public class ClientDFXResourceLoader
/*    */   implements DireEffectResourceLoader
/*    */ {
/* 18 */   private Cloner cloner = (Cloner)new DefaultCloner();
/*    */   private final ResourceManager resourceManager;
/*    */   private final ParticleProcessor particleProcessor;
/*    */   
/*    */   public ClientDFXResourceLoader(ResourceManager resourceManager, ParticleProcessor particleProcessor) {
/* 23 */     this.resourceManager = resourceManager;
/* 24 */     this.particleProcessor = particleProcessor;
/*    */   }
/*    */   
/*    */   public Document getXmlDocument(String documentId, boolean cacheDFX) {
/* 28 */     if (!cacheDFX) {
/* 29 */       this.resourceManager.clearResource(documentId, Document.class);
/*    */     }
/* 31 */     return (Document)this.resourceManager.getResource(Document.class, documentId, cacheDFX ? CacheType.CACHE_TEMPORARILY : CacheType.NOT_CACHED);
/*    */   }
/*    */   
/*    */   public Element getDireEffectData(String dfxScriptId, boolean cacheDFX) {
/* 35 */     return getXmlDocument(dfxScriptId, cacheDFX).getRootElement();
/*    */   }
/*    */   
/*    */   public Object getParticleSystem(String particleId, boolean cacheDFX) {
/* 39 */     if (!cacheDFX) {
/* 40 */       this.resourceManager.clearResource(particleId, ParticleSystem.class);
/*    */     }
/*    */     
/* 43 */     ParticleSystem system = (ParticleSystem)this.resourceManager.getResource(ParticleSystem.class, particleId);
/* 44 */     system = this.cloner.getStandaloneCopy(system);
/* 45 */     this.particleProcessor.addSystem(system);
/* 46 */     return system;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getModelNode(String resource) {
/* 52 */     return ((ModelNode)this.resourceManager.getResource(ModelNode.class, resource, CacheType.CACHE_TEMPORARILY)).clone();
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getJointAnimation(String resource) {
/* 57 */     return ((JointAnimation)this.resourceManager.getResource(JointAnimation.class, resource, CacheType.CACHE_TEMPORARILY)).clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\dfx\ClientDFXResourceLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */