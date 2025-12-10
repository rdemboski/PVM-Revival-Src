/*     */ package com.funcom.gameengine.resourcemanager.loaders;
/*     */ import com.funcom.gameengine.resourcemanager.LoadException;
/*     */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ import java.util.zip.ZipInputStream;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Priority;
/*     */ import org.jibx.runtime.IUnmarshallingContext;
/*     */ import org.jibx.runtime.JiBXException;
/*     */ import org.softmed.jops.Generator;
/*     */ import org.softmed.jops.GeneratorBehaviour;
/*     */ import org.softmed.jops.ParticleBehaviour;
/*     */ import org.softmed.jops.ParticleRender;
/*     */ import org.softmed.jops.ParticleSystem;
/*     */ import org.softmed.jops.PositionAnimator;
/*     */ import org.softmed.jops.SpaceAnimator;
/*     */ import org.softmed.jops.fileloading.DataFormatException;
/*     */ import org.softmed.jops.modifiers.Modifier;
/*     */ import org.softmed.jops.modifiers.PointMass;
/*     */ import org.softmed.jops.space.GeneratorSpace;
/*     */ 
/*     */ public class JopsParticleLoader extends AbstractLoader {
/*     */   public JopsParticleLoader() {
/*  25 */     super(ParticleSystem.class);
/*  26 */     setup();
/*     */   }
/*     */   private IBindingFactory bfact;
/*     */   protected void setup() {
/*     */     try {
/*  31 */       this.bfact = BindingDirectory.getFactory(ParticleSystem.class);
/*  32 */     } catch (JiBXException e) {
/*  33 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadData(ManagedResource<?> managedResource) throws LoadException {
/*  39 */     InputStream inputStream = null;
/*     */     try {
/*  41 */       inputStream = getFileInputStream("particles/jops/systems/" + managedResource.getName());
/*  42 */       ZipInputStream zipStream = new ZipInputStream(inputStream);
/*  43 */       zipStream.getNextEntry();
/*  44 */       ParticleSystem system = getParticleSystemFromInputStream(zipStream);
/*  45 */       managedResource.setResource(system);
/*  46 */     } catch (Exception e) {
/*  47 */       throw new LoadException(getResourceManager(), managedResource, e);
/*     */     } finally {
/*  49 */       closeSafely(inputStream, managedResource);
/*     */     } 
/*     */   }
/*     */   
/*     */   private ParticleSystem getParticleSystemFromInputStream(InputStream input) throws IOException, DataFormatException {
/*     */     try {
/*  55 */       IUnmarshallingContext uctx = this.bfact.createUnmarshallingContext();
/*     */       
/*  57 */       ParticleSystem obj = (ParticleSystem)uctx.unmarshalDocument(input, null);
/*     */ 
/*     */       
/*  60 */       setObjectReferences(obj);
/*     */       
/*  62 */       input.close();
/*  63 */       return obj;
/*     */     }
/*  65 */     catch (JiBXException e) {
/*  66 */       e.printStackTrace();
/*  67 */       throw new DataFormatException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void setObjectReferences(ParticleSystem ps) {
/*  72 */     List<Modifier> modifiers = ps.getModifiers();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     for (Modifier modifier : modifiers) {
/*  78 */       if (modifier instanceof PointMass) {
/*  79 */         PointMass mass = (PointMass)modifier;
/*  80 */         int index = mass.getPositionAnimatorIndex();
/*  81 */         if (index > -1) {
/*  82 */           PositionAnimator panimator = ps.getPanimators().get(index);
/*  83 */           mass.setPositionAnimator(panimator);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  88 */     List<Generator> gens = ps.getGenerators();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     for (Generator generator : gens) {
/*     */       
/*  99 */       int index = generator.getParticleBehaviourIndex();
/* 100 */       if (index > -1) {
/* 101 */         ParticleBehaviour behaviour = ps.getBehaviours().get(index);
/* 102 */         generator.setPb(behaviour);
/*     */       } else {
/* 104 */         generator.setPb(null);
/*     */       } 
/* 106 */       index = generator.getGeneratorBehaviourIndex();
/* 107 */       if (index > -1) {
/* 108 */         GeneratorBehaviour genBehhaviour = ps.getGenBehaviours().get(index);
/* 109 */         generator.setGb(genBehhaviour);
/*     */       } else {
/* 111 */         generator.setGb(null);
/*     */       } 
/* 113 */       index = generator.getRenderIndex();
/* 114 */       if (index > -1) {
/* 115 */         ParticleRender render = ps.getRenders().get(index);
/* 116 */         generator.setRender(render);
/*     */       } else {
/* 118 */         generator.setRender(null);
/*     */       } 
/* 120 */       index = generator.getSpaceIndex();
/* 121 */       if (index > -1) {
/* 122 */         GeneratorSpace space = ps.getSpaces().get(index);
/* 123 */         generator.setSpace(space);
/*     */       } else {
/* 125 */         generator.setSpace(null);
/*     */       } 
/* 127 */       index = generator.getSpaceAnimatorIndex();
/* 128 */       if (index > -1) {
/* 129 */         SpaceAnimator animator = ps.getAnimators().get(index);
/* 130 */         generator.setAnimator(animator);
/*     */       } else {
/* 132 */         generator.setAnimator(null);
/*     */       } 
/* 134 */       index = generator.getPositionAnimatorIndex();
/* 135 */       LOGGER.log((Priority)Level.INFO, "PositionAnimator Index ->" + index);
/* 136 */       if (index > -1) {
/* 137 */         PositionAnimator panimator = ps.getPanimators().get(index);
/* 138 */         LOGGER.log((Priority)Level.INFO, "Setting a PositionAnimator ->" + panimator);
/* 139 */         generator.setPositionAnimator(panimator);
/*     */       } else {
/* 141 */         generator.setPositionAnimator(null);
/*     */       } 
/*     */ 
/*     */       
/* 145 */       List<Integer> indexes = generator.getModifierIndexes();
/* 146 */       if (indexes != null) {
/* 147 */         for (Integer integer : indexes) {
/* 148 */           Modifier modifier = ps.getModifiers().get(integer.intValue());
/* 149 */           generator.getModifiers().add(modifier);
/*     */         } 
/*     */         
/* 152 */         generator.setModifierIndexes(null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\JopsParticleLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */