/*     */ package com.funcom.tcg.client.state;
/*     */ 
/*     */ import com.funcom.gameengine.model.input.Cursor;
/*     */ import com.funcom.gameengine.model.input.MouseCursorSetter;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.jme.image.Image;
/*     */ import com.jme.input.MouseInput;
/*     */ import com.jme.math.Vector2f;
/*     */ import com.jmex.bui.dragndrop.BDragEvent;
/*     */ import com.jmex.bui.dragndrop.BDragNDrop;
/*     */ import com.jmex.bui.dragndrop.BDropEvent;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
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
/*     */ 
/*     */ public class TCGCursorSetter
/*     */   implements MouseCursorSetter, BDragNDrop.DragNDropListener
/*     */ {
/*     */   private boolean dragging = false;
/*     */   
/*     */   public void setWalkCursor() {
/*  31 */     if (!this.dragging) {
/*  32 */       setCursor(BuiltinCursors.CURSOR_WALK);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setDragCursor() {
/*  37 */     setCursor(BuiltinCursors.CURSOR_DRAG);
/*     */   }
/*     */   
/*     */   public void setDefaultCursor() {
/*  41 */     setWalkCursor();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCursor(Cursor cursor) {
/*  46 */     if (cursor.equals(BuiltinCursors.CURSOR_WALK) && 
/*  47 */       TcgGame.isStartDuelMode()) {
/*  48 */       cursor = BuiltinCursors.CURSOR_DUEL;
/*     */     }
/*     */ 
/*     */     
/*  52 */     setCursor(cursor.getId(), (cursor.getHotspot()).x, (cursor.getHotspot()).y);
/*     */   }
/*     */   
/*     */   public void setCursor(String mouseOverCursorId) {
/*  56 */     Image image = (Image)TcgGame.getResourceManager().getResource(Image.class, mouseOverCursorId);
/*  57 */     setCursor(image, 0.5F, 0.5F);
/*     */   }
/*     */   
/*     */   public void setCursor(String mouseOverCursorId, float hotspotX, float hotspotY) {
/*  61 */     setCursor((Image)TcgGame.getResourceManager().getResource(Image.class, mouseOverCursorId), hotspotX, hotspotY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setCursor(Image cursor, float hotspotX, float hotspotY) {
/*     */     try {
/*  70 */       MouseInput.get().setHardwareCursor(new URL("file:" + cursor.toString()), new Image[] { cursor }, new int[] { 0 }, (int)(cursor.getWidth() * hotspotX), (int)(cursor.getHeight() * hotspotY));
/*  71 */     } catch (MalformedURLException e) {
/*  72 */       throw new IllegalStateException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void dragInitiated(BDragNDrop container, BDragEvent eventB) {
/*  77 */     setDragCursor();
/*  78 */     this.dragging = true;
/*     */   }
/*     */   
/*     */   public void dropped(BDragNDrop container, BDropEvent eventB) {
/*  82 */     this.dragging = false;
/*  83 */     setDefaultCursor();
/*     */   }
/*     */   
/*     */   public enum BuiltinCursors implements Cursor {
/*  87 */     CURSOR_USE("gui/cursor/pointer_interact.png", new Vector2f(0.0F, 1.0F)),
/*  88 */     CURSOR_BUY("gui/cursor/pointer_interact.png", new Vector2f(0.0F, 1.0F)),
/*  89 */     CURSOR_ATTACK("gui/cursor/pointer_attack_red.png", new Vector2f(0.5F, 0.5F)),
/*  90 */     CURSOR_QUEST_NOT_STARTED("gui/cursor/pointer_quest.png", new Vector2f(0.5F, 0.5F)),
/*  91 */     CURSOR_QUEST_COMPLETE("gui/cursor/pointer_quest.png", new Vector2f(0.5F, 0.5F)),
/*  92 */     CURSOR_WALK("gui/cursor/pointer_arrow.png", new Vector2f(0.0F, 1.0F)),
/*  93 */     CURSOR_DRAG("gui/cursor/pointer_interact.png", new Vector2f(0.0F, 1.0F)),
/*  94 */     CURSOR_DRAG_CLOSE("gui/cursor/pointer_interact_pressed.png", new Vector2f(0.0F, 1.0F)),
/*  95 */     CURSOR_QUEST_INTERACT("gui/cursor/pointer_interact.png", new Vector2f(0.0F, 1.0F)),
/*  96 */     CURSOR_ADD_FRIEND("gui/cursor/pointer_add_friend.png", new Vector2f(0.0F, 1.0F)),
/*  97 */     CURSOR_BLOCK_PLAYER("gui/cursor/pointer_block_player.png", new Vector2f(0.0F, 1.0F)),
/*  98 */     CURSOR_TEXT("gui/cursor/pointer_text.png", new Vector2f(0.0F, 1.0F)),
/*  99 */     CURSOR_DUEL("gui/cursor/pointer_duel.png", new Vector2f(0.5F, 0.5F));
/*     */     
/*     */     public Vector2f hotspot;
/*     */     
/*     */     public String id;
/*     */     
/*     */     BuiltinCursors(String id, Vector2f hotspot) {
/* 106 */       this.hotspot = hotspot;
/* 107 */       this.id = id;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getId() {
/* 112 */       return this.id;
/*     */     }
/*     */ 
/*     */     
/*     */     public Vector2f getHotspot() {
/* 117 */       return this.hotspot;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\state\TCGCursorSetter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */