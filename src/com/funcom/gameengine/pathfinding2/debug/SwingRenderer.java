/*    */ package com.funcom.gameengine.pathfinding2.debug;
/*    */ 
/*    */ import com.funcom.commons.geom.RectangleWC;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.pathfinding2.WorldBoundsGraphNode;
/*    */ import java.awt.Color;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Image;
/*    */ import java.awt.Rectangle;
/*    */ import java.util.Random;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SwingRenderer
/*    */   extends JPanel
/*    */   implements DebugListener
/*    */ {
/*    */   private static final int TILE_PIXEL_RATIO = 1;
/*    */   private Image backbuffer;
/*    */   private static final int PANEL_WIDTH = 1024;
/*    */   private static final int PANEL_HEIGHT = 768;
/* 23 */   private Random random = new Random(1L);
/*    */ 
/*    */ 
/*    */   
/*    */   protected void paintComponent(Graphics g) {
/* 28 */     if (this.backbuffer == null) {
/* 29 */       this.backbuffer = createImage(1024, 768);
/*    */     }
/* 31 */     g.clearRect(0, 0, 1024, 768);
/* 32 */     g.drawImage(this.backbuffer, 0, 0, null);
/*    */   }
/*    */ 
/*    */   
/*    */   public void quadAdded(WorldBoundsGraphNode node) {
/* 37 */     if (this.backbuffer == null) {
/*    */       return;
/*    */     }
/* 40 */     Graphics g = this.backbuffer.getGraphics();
/*    */     
/* 42 */     g.setColor(new Color(this.random.nextInt(256), this.random.nextInt(256), this.random.nextInt(256)));
/* 43 */     Rectangle rect = convert(node.getBounds());
/* 44 */     g.fillRect(rect.x, rect.y, rect.width, rect.height);
/*    */     
/* 46 */     repaint();
/*    */   }
/*    */   
/*    */   public void merge(WorldBoundsGraphNode node) {
/* 50 */     if (this.backbuffer == null) {
/*    */       return;
/*    */     }
/* 53 */     Graphics g = this.backbuffer.getGraphics();
/*    */     
/* 55 */     Rectangle rect = convert(node.getBounds());
/*    */ 
/*    */     
/* 58 */     g.setColor(Color.yellow);
/* 59 */     g.drawRect(rect.x, rect.y, rect.width, rect.height);
/*    */     
/* 61 */     g.setColor(new Color(this.random.nextInt(256), this.random.nextInt(256), this.random.nextInt(256)));
/* 62 */     g.fillRect(rect.x, rect.y, rect.width, rect.height);
/*    */     
/* 64 */     repaint();
/*    */   }
/*    */   
/*    */   private Rectangle convert(RectangleWC bounds) {
/* 68 */     WorldCoordinate origin = bounds.getOrigin();
/* 69 */     WorldCoordinate extent = bounds.getExtent();
/*    */     
/* 71 */     int x = origin.getTileX() * 1 + (int)Math.ceil(origin.getTileOffX() * 1.0D);
/* 72 */     int y = origin.getTileY() * 1 + (int)Math.ceil(origin.getTileOffY() * 1.0D);
/* 73 */     int x2 = extent.getTileX() * 1 + (int)Math.ceil(extent.getTileOffX() * 1.0D);
/* 74 */     int y2 = extent.getTileY() * 1 + (int)Math.ceil(extent.getTileOffY() * 1.0D);
/* 75 */     return new Rectangle(x, y, x2 - x, y2 - y);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\pathfinding2\debug\SwingRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */