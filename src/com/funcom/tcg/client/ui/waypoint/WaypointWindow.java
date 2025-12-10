/*     */ package com.funcom.tcg.client.ui.waypoint;
/*     */ 
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import com.funcom.gameengine.model.props.UpdateListener;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.rpgengine2.waypoints.WaypointDescription;
/*     */ import com.funcom.rpgengine2.waypoints.WaypointDestinationPortalDescription;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.AbstractTcgWindow;
/*     */ import com.funcom.tcg.client.ui.BScrollPaneTcg;
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.funcom.tcg.client.ui.CloseWindowListener;
/*     */ import com.funcom.tcg.client.ui.vendor.TcgGridLayout;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BoundedRangeModel;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WaypointWindow
/*     */   extends AbstractTcgWindow
/*     */ {
/*     */   private static final int WINDOW_WIDTH = 400;
/*     */   private static final int WINDOW_HEIGHT = 600;
/*     */   public static final String STYLECLASS_CONTAINER = "waypointwindow.container";
/*     */   private CloseWindowListener closeListener;
/*     */   private BContainer portalContainer;
/*     */   private WaypointModel waypointModel;
/*     */   private BLabel portalName;
/*     */   private InteractibleProp waypointProp;
/*     */   private BScrollPaneTcg bScrollPaneTcg;
/*  43 */   private int gotoScroll = -1;
/*     */   
/*     */   public WaypointWindow() {
/*  46 */     super(TcgGame.getResourceManager());
/*  47 */     this._style = BuiUtils.createMergedClassStyleSheets(WaypointWindow.class, new BananaResourceProvider(this.resourceManager));
/*     */     
/*  49 */     setBounds(0, 110, 400, 600);
/*  50 */     layoutComponents();
/*     */   }
/*     */   
/*     */   private void layoutComponents() {
/*  54 */     this.portalContainer = new BContainer((BLayoutManager)new TcgGridLayout(1, 5, 6, 0));
/*  55 */     this.portalContainer.setStyleClass("waypointwindow.container");
/*  56 */     this.bScrollPaneTcg = new BScrollPaneTcg((BComponent)this.portalContainer, true, 92);
/*     */     
/*  58 */     this.portalName = new BLabel("");
/*  59 */     this.portalName.setStyleClass("waypointwindow.label.title");
/*     */ 
/*     */     
/*  62 */     getClientArea().add((BComponent)this.bScrollPaneTcg, new Rectangle(0, 60, 370, 460));
/*  63 */     getClientArea().add((BComponent)this.portalName, new Rectangle(0, 520, 370, 80));
/*     */     
/*  65 */     addDefaultCloseButton(AbstractTcgWindow.CloseButtonPosition.TOP_RIGHT);
/*     */   }
/*     */   
/*     */   public void setWaypointModel(WaypointModel waypointModel) {
/*  69 */     this.waypointModel = waypointModel;
/*     */     
/*  71 */     this.waypointProp = (InteractibleProp)TcgGame.getWaypointRegister().getPropNodeByName(waypointModel.getWaypointDescription().getId()).getProp();
/*     */     
/*  73 */     this.closeListener = new CloseWindowListener(this.waypointProp, WaypointWindow.class);
/*  74 */     this.waypointProp.addUpdateListener((UpdateListener)this.closeListener);
/*     */     
/*  76 */     this.portalContainer.removeAll();
/*  77 */     addButtons();
/*     */   }
/*     */   
/*     */   public WaypointModel getWaypointModel() {
/*  81 */     return this.waypointModel;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderComponent(Renderer renderer) {
/*  86 */     BoundedRangeModel vModel = this.bScrollPaneTcg.getVerticalViewportModel();
/*  87 */     super.renderComponent(renderer);
/*  88 */     if (this.gotoScroll > -1) {
/*  89 */       vModel.setValue(this.gotoScroll * this.bScrollPaneTcg.getVerticalViewportModel().getScrollIncrement());
/*  90 */       this.gotoScroll = -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addButtons() {
/*  95 */     WaypointDescription waypointDescription = this.waypointModel.getWaypointDescription();
/*  96 */     List<WaypointDestinationPortalDescription> destinationPortalDescriptions = waypointDescription.getSortedDestinationPortalsByLevel();
/*     */     
/*  98 */     for (WaypointDestinationPortalDescription destinationPortalDescription : destinationPortalDescriptions) {
/*  99 */       boolean isGoto = false;
/*     */       
/* 101 */       if (MainGameState.getBreadcrumbManager() != null) {
/* 102 */         String nextMap = MainGameState.getBreadcrumbManager().getNextMap();
/* 103 */         isGoto = destinationPortalDescription.getArrivalCoordinate().getMapId().equals(nextMap);
/*     */       } 
/*     */       
/* 106 */       this.portalContainer.add((BComponent)new WaypointDestinationPortalButton(this.waypointModel.getWaypointDescription().getId(), destinationPortalDescription.getId(), this.waypointModel.getUnlockStatus(destinationPortalDescription.getId()), isGoto));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 112 */       if (isGoto) {
/* 113 */         this.gotoScroll = destinationPortalDescriptions.indexOf(destinationPortalDescription);
/*     */       }
/*     */     } 
/* 116 */     this.portalName.setText(waypointDescription.getName());
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\waypoint\WaypointWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */