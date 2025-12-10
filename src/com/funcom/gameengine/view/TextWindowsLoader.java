/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import org.jdom.Document;
/*    */ import org.jdom.Element;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextWindowsLoader
/*    */ {
/*    */   protected ResourceManager resourceManager;
/*    */   
/*    */   public TextWindowsLoader(ResourceManager resourceManager) {
/* 16 */     this.resourceManager = resourceManager;
/*    */   }
/*    */   
/*    */   public List<DfxTextWindow> loadWindows(Document doc) {
/* 20 */     Element root = doc.getRootElement();
/* 21 */     List<Element> windows = root.getChildren("window");
/* 22 */     List<DfxTextWindow> textWindows = new LinkedList<DfxTextWindow>();
/* 23 */     for (Element window : windows) {
/* 24 */       String name = window.getAttributeValue("name");
/* 25 */       float screenX = Float.parseFloat(window.getChild("screen-x").getValue());
/* 26 */       float screenY = Float.parseFloat(window.getChild("screen-y").getValue());
/* 27 */       int numLines = Integer.parseInt(window.getChild("numlines").getValue());
/* 28 */       float fadeIn = Float.parseFloat(window.getChild("fade-in").getValue());
/* 29 */       float stable = Float.parseFloat(window.getChild("stable").getValue());
/* 30 */       float fadeOut = Float.parseFloat(window.getChild("fade-out").getValue());
/* 31 */       float scale = Float.parseFloat(window.getChild("scale").getValue());
/* 32 */       String[] colorRBGAString = window.getChild("color").getValue().split(",");
/*    */       
/* 34 */       float[] colorRBGA = new float[4];
/* 35 */       for (int i = 0; i < colorRBGAString.length; i++) {
/* 36 */         colorRBGA[i] = Integer.parseInt(colorRBGAString[i]) / 255.0F;
/* 37 */         if (colorRBGA[i] < 0.0F || colorRBGA[i] > 1.0F) {
/* 38 */           throw new RuntimeException("color component out of range for game window: id=" + name);
/*    */         }
/*    */       } 
/*    */ 
/*    */ 
/*    */       
/* 44 */       DfxTextWindow textWindow = createWindow(name, screenX, screenY, numLines, fadeIn, stable, fadeOut, scale, colorRBGA);
/* 45 */       textWindows.add(textWindow);
/*    */     } 
/* 47 */     return textWindows;
/*    */   }
/*    */   
/*    */   protected DfxTextWindow createWindow(String name, float screenX, float screenY, int numLines, float fadeIn, float stable, float fadeOut, float scale, float[] colorRBGA) {
/* 51 */     return DfxTextWindowManager.instance().createWindow(name, this.resourceManager, screenX, screenY, fadeIn, stable, fadeOut, numLines, colorRBGA, scale);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\TextWindowsLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */