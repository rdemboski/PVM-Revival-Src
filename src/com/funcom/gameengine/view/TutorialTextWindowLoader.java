/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import org.jdom.Document;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TutorialTextWindowLoader
/*    */   extends TextWindowsLoader
/*    */ {
/*    */   public TutorialTextWindowLoader(ResourceManager resourceManager) {
/* 14 */     super(resourceManager);
/*    */   }
/*    */   
/*    */   public List<TriggeredDfxTextWindow> loadTutorialWindows(Document doc) {
/* 18 */     List<DfxTextWindow> textWindows = loadWindows(doc);
/* 19 */     List<TriggeredDfxTextWindow> retList = new LinkedList<TriggeredDfxTextWindow>();
/* 20 */     for (DfxTextWindow window : textWindows) {
/* 21 */       retList.add((TriggeredDfxTextWindow)window);
/*    */     }
/* 23 */     return retList;
/*    */   }
/*    */ 
/*    */   
/*    */   protected DfxTextWindow createWindow(String name, float screenX, float screenY, int numLines, float fadeIn, float stable, float fadeOut, float scale, float[] colorRBGA) {
/* 28 */     return DfxTextWindowManager.instance().createTutorialWindow(name, this.resourceManager, screenX, screenY, fadeIn, stable, fadeOut, numLines, colorRBGA, scale);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\TutorialTextWindowLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */