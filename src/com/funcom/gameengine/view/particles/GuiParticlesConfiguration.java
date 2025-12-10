/*    */ package com.funcom.gameengine.view.particles;
/*    */ 
/*    */ import com.funcom.commons.configuration.BeanConfigurator;
/*    */ import java.io.File;
/*    */ import java.io.FileReader;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiParticlesConfiguration
/*    */ {
/*    */   private static final String RELATIVE_PROPERTIES_PATH = ".funcom/pvm/gui_particles_setup.properties";
/* 22 */   private float baseScale = 0.2F;
/*    */   
/*    */   private boolean particlesLit = false;
/*    */   
/*    */   public float getBaseScale() {
/* 27 */     return this.baseScale;
/*    */   }
/*    */   
/*    */   public void setBaseScale(float baseScale) {
/* 31 */     this.baseScale = baseScale;
/*    */   }
/*    */   
/*    */   public boolean isParticlesLit() {
/* 35 */     return this.particlesLit;
/*    */   }
/*    */   
/*    */   public void setParticlesLit(boolean particlesLit) {
/* 39 */     this.particlesLit = particlesLit;
/*    */   }
/*    */   
/*    */   public static GuiParticlesConfiguration read() {
/* 43 */     BeanConfigurator bc = new BeanConfigurator();
/* 44 */     setPropertiesInput(bc);
/* 45 */     GuiParticlesConfiguration guiParticlesConfiguration = new GuiParticlesConfiguration();
/* 46 */     bc.configure(guiParticlesConfiguration);
/* 47 */     return guiParticlesConfiguration;
/*    */   }
/*    */   
/*    */   private static void setPropertiesInput(BeanConfigurator bc) {
/* 51 */     FileReader fis = null;
/*    */     
/*    */     try {
/*    */       File file;
/* 55 */       if (System.getProperty("tcg.plugin.path") != null) {
/* 56 */         file = new File(System.getProperty("tcg.plugin.path"), ".funcom/pvm/gui_particles_setup.properties");
/*    */       } else {
/* 58 */         file = new File(System.getProperty("user.home"), ".funcom/pvm/gui_particles_setup.properties");
/* 59 */       }  if (!file.exists()) {
/* 60 */         File parentFile = file.getParentFile();
/* 61 */         if (parentFile != null) {
/* 62 */           parentFile.mkdirs();
/*    */         }
/* 64 */         file.createNewFile();
/*    */       } 
/*    */ 
/*    */       
/* 68 */       fis = new FileReader(file);
/* 69 */       bc.setProperties(fis);
/* 70 */     } catch (IOException e) {
/* 71 */       throw new IllegalStateException(e);
/*    */     } finally {
/* 73 */       if (fis != null)
/*    */         try {
/* 75 */           fis.close();
/* 76 */         } catch (IOException e) {
/* 77 */           System.err.println("Failed to close input stream (GuiParticlesConfiguration.read())!");
/*    */         }  
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\particles\GuiParticlesConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */