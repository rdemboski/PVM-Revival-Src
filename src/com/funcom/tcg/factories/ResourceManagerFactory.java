/*    */ package com.funcom.tcg.factories;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.DefaultResourceManager;
/*    */ import com.funcom.gameengine.resourcemanager.ResourceLoader;
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.gameengine.resourcemanager.loaders.AwtFontLoader;
/*    */ import com.funcom.gameengine.resourcemanager.loaders.MapConnectivityGraphLoader;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class ResourceManagerFactory {
/*    */   public static ResourceManager createForClient() {
/* 15 */     Set<String> resourceRoots = new HashSet<String>(Arrays.asList(System.getProperties().getProperty("resconfig").split(",")));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 20 */     return createForClient(resourceRoots);
/*    */   }
/*    */   
/*    */   public static ResourceManager createForClient(Collection<String> resourceRoots) {
/* 24 */     DefaultResourceManager defaultResourceManager = new DefaultResourceManager();
/* 25 */     for (String resourceRoot : resourceRoots) {
/* 26 */       defaultResourceManager.addResourceRoot(resourceRoot);
/*    */     }
/*    */ 
/*    */     
/* 30 */     defaultResourceManager.addTypeLoader((ResourceLoader)new AwtFontLoader());
/* 31 */     defaultResourceManager.addTypeLoader((ResourceLoader)new MapConnectivityGraphLoader());
/*    */ 
/*    */     
/* 34 */     defaultResourceManager.initDefaultLoaders();
/*    */     
/* 36 */     return (ResourceManager)defaultResourceManager;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\factories\ResourceManagerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */