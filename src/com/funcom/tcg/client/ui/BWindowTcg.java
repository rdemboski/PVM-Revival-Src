/*     */ package com.funcom.tcg.client.ui;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.Spacer;
/*     */ import com.jmex.bui.event.BEvent;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.event.MouseEvent;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.layout.BorderLayout;
/*     */ import com.jmex.bui.layout.TableLayout;
/*     */ import com.jmex.bui.util.Point;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class BWindowTcg extends BWindow {
/*     */   protected BContainer clientArea;
/*     */   
/*     */   public BWindowTcg() {
/*  23 */     super(null, (BLayoutManager)new BorderLayout(0, 0));
/*  24 */     BananaResourceProvider buiResourceProvider = new BananaResourceProvider(TcgGame.getResourceManager());
/*  25 */     this._style = BuiUtils.createMergedClassStyleSheets(getClass(), buiResourceProvider);
/*  26 */     this.windowListeners = new ArrayList<WindowClosedListener>();
/*     */   }
/*     */   private BLabel titleLabel; protected List<WindowClosedListener> windowListeners;
/*     */   public BWindowTcg(String title) {
/*  30 */     this();
/*  31 */     layoutBasicGui(title);
/*     */   }
/*     */   
/*     */   public void addWindowClosedListener(WindowClosedListener listener) {
/*  35 */     this.windowListeners.add(listener);
/*     */   }
/*     */   
/*     */   public void removeWindowClosedListener(WindowClosedListener listener) {
/*  39 */     this.windowListeners.remove(listener);
/*     */   }
/*     */ 
/*     */   
/*     */   public void dismiss() {
/*  44 */     super.dismiss();
/*  45 */     fireWindowDismissed();
/*     */   }
/*     */   
/*     */   private void fireWindowDismissed() {
/*  49 */     if (!this.windowListeners.isEmpty()) {
/*  50 */       List<WindowClosedListener> tmp = new ArrayList<WindowClosedListener>(this.windowListeners);
/*  51 */       for (WindowClosedListener windowListener : tmp) {
/*  52 */         windowListener.windowClosed(this);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public BContainer getClientArea() {
/*  58 */     return this.clientArea;
/*     */   }
/*     */   
/*     */   private void layoutBasicGui(String title) {
/*  62 */     BContainer windowContainer = createWindowContainer(title);
/*  63 */     add((BComponent)windowContainer, BorderLayout.CENTER);
/*     */   }
/*     */   
/*     */   public void setTitle(String title) {
/*  67 */     this.titleLabel.setText("   " + title);
/*     */   }
/*     */   
/*     */   private BContainer createWindowContainer(String title) {
/*  71 */     BContainer windowContainer = new BContainer((BLayoutManager)new BorderLayout());
/*  72 */     windowContainer.setStyleClass("bwindowtcg");
/*     */     
/*  74 */     BContainer titleContainer = createTitleContainer(title);
/*  75 */     windowContainer.add((BComponent)titleContainer, BorderLayout.NORTH);
/*     */     
/*  77 */     BContainer bottomContainer = new BContainer((BLayoutManager)new BorderLayout(0, 0));
/*  78 */     bottomContainer.add((BComponent)new Spacer(7, 7), BorderLayout.NORTH);
/*  79 */     bottomContainer.add((BComponent)new Spacer(7, 7), BorderLayout.SOUTH);
/*  80 */     bottomContainer.add((BComponent)new Spacer(7, 7), BorderLayout.EAST);
/*  81 */     bottomContainer.add((BComponent)new Spacer(7, 7), BorderLayout.WEST);
/*  82 */     bottomContainer.add((BComponent)createClientArea(), BorderLayout.CENTER);
/*  83 */     windowContainer.add((BComponent)bottomContainer, BorderLayout.CENTER);
/*     */     
/*  85 */     return windowContainer;
/*     */   }
/*     */   
/*     */   private BContainer createClientArea() {
/*  89 */     this.clientArea = new BContainer();
/*  90 */     this.clientArea.setStyleClass("bwindowtcg.clientarea");
/*  91 */     TableLayout tableLayout = new TableLayout(1, 0, 0);
/*  92 */     tableLayout.setHorizontalAlignment(TableLayout.CENTER);
/*  93 */     tableLayout.setVerticalAlignment(TableLayout.CENTER);
/*  94 */     this.clientArea.setLayoutManager((BLayoutManager)tableLayout);
/*  95 */     return this.clientArea;
/*     */   }
/*     */   
/*     */   private BContainer createTitleContainer(String title) {
/*  99 */     BContainer titleContainer = new BContainer((BLayoutManager)new BorderLayout());
/* 100 */     titleContainer.add((BComponent)new Spacer(10, 10), BorderLayout.NORTH);
/*     */     
/* 102 */     titleContainer.add((BComponent)new Spacer(10, 10), BorderLayout.EAST);
/* 103 */     titleContainer.add((BComponent)new Spacer(10, 10), BorderLayout.WEST);
/* 104 */     titleContainer.add((BComponent)createTitleCenterContainer(title), BorderLayout.CENTER);
/* 105 */     return titleContainer;
/*     */   }
/*     */   
/*     */   private BContainer createTitleCenterContainer(String title) {
/* 109 */     BContainer titleContainer = new BContainer((BLayoutManager)new HGroupLayout(Justification.LEFT, Policy.STRETCH));
/*     */     
/* 111 */     MoveWindowListener windowMovementListener = new MoveWindowListener(this);
/* 112 */     this.titleLabel = new BLabel("");
/* 113 */     setTitle(title);
/* 114 */     this.titleLabel.addListener((ComponentListener)windowMovementListener);
/* 115 */     this.titleLabel.setStyleClass("bwindowtcg.titlelabel");
/* 116 */     titleContainer.add((BComponent)this.titleLabel, new GroupLayout.Constraints(100));
/*     */     
/* 118 */     BButton closeButton = new BButton("");
/* 119 */     closeButton.setStyleClass("close-button");
/* 120 */     closeButton.addListener((ComponentListener)new ActionListener() {
/*     */           public void actionPerformed(ActionEvent event) {
/* 122 */             BWindowTcg.this.dismiss();
/*     */           }
/*     */         });
/* 125 */     closeButton.setPreferredSize(24, 24);
/* 126 */     titleContainer.add((BComponent)closeButton, new GroupLayout.Constraints(true));
/* 127 */     return titleContainer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean dispatchEvent(BEvent event) {
/* 146 */     super.dispatchEvent(event);
/* 147 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class MoveWindowListener
/*     */     extends MouseAdapter
/*     */   {
/*     */     private BWindow window;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Point displacement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MoveWindowListener(BWindow window) {
/* 174 */       this.window = window;
/* 175 */       this.displacement = new Point();
/*     */     }
/*     */ 
/*     */     
/*     */     public void mousePressed(MouseEvent event) {
/* 180 */       int[] loc = this.window.getLocation();
/* 181 */       this.displacement.x = event.getX() - loc[0];
/* 182 */       this.displacement.y = event.getY() - loc[1];
/*     */     }
/*     */ 
/*     */     
/*     */     public void mouseDragged(MouseEvent event) {
/* 187 */       int locX = event.getX() - this.displacement.x;
/* 188 */       int locY = event.getY() - this.displacement.y;
/* 189 */       int maxWidth = DisplaySystem.getDisplaySystem().getWidth();
/* 190 */       int maxHeight = DisplaySystem.getDisplaySystem().getHeight();
/*     */       
/* 192 */       if (locX + this.window.getWidth() >= maxWidth || locX < 0 || locY < 0 || locY + this.window.getHeight() >= maxHeight) {
/*     */         return;
/*     */       }
/*     */       
/* 196 */       this.window.setLocation(locX, locY);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\BWindowTcg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */