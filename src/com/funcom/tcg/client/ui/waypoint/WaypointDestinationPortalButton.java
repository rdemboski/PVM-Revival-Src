/*     */ package com.funcom.tcg.client.ui.waypoint;
/*     */ 
/*     */ import com.funcom.commons.jme.bui.HighlightedContainer;
/*     */ import com.funcom.gameengine.resourcemanager.NoLocatorException;
/*     */ import com.funcom.rpgengine2.waypoints.WaypointDestinationPortalDescription;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.hud.PanelManager;
/*     */ import com.funcom.tcg.net.message.InteractiblePropActionInvokedMessage;
/*     */ import com.funcom.tcg.portals.InteractibleType;
/*     */ import com.jmex.bui.BClickthroughLabel;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BCustomButton;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.background.BBackground;
/*     */ import com.jmex.bui.background.ImageBackground;
/*     */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.BEvent;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.event.MouseEvent;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.layout.BorderLayout;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WaypointDestinationPortalButton
/*     */   extends BCustomButton
/*     */ {
/*     */   private static final int WINDOW_WIDTH = 310;
/*     */   private static final int WINDOW_HEIGHT = 92;
/*     */   private static final String STYLE_CLASS = "waypointwindow.button";
/*     */   private HighlightedContainer buttonContainer;
/*     */   
/*     */   public WaypointDestinationPortalButton(String waypointId, final String portalId, boolean unlocked, boolean isGoto) {
/*     */     BImage mapImage;
/*  48 */     WaypointDestinationPortalDescription waypointDestinationPortalDescription = TcgGame.getRpgLoader().getWaypointManager().getWaypointDestinationPortalDescription(portalId);
/*     */     
/*  50 */     unlocked = (unlocked && waypointDestinationPortalDescription.getRequiredLevel() <= MainGameState.getPlayerModel().getStatSum(Short.valueOf((short)20)).intValue() && (MainGameState.getPlayerModel().isSubscriber() || !waypointDestinationPortalDescription.isSubscriptionRequired() || hasAccess(waypointDestinationPortalDescription.getAccessKeys())));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  56 */     setLayoutManager((BLayoutManager)new AbsoluteLayout());
/*  57 */     setSize(310, 92);
/*     */     
/*  59 */     this.buttonContainer = new HighlightedContainer((BLayoutManager)new BorderLayout(1, 1));
/*  60 */     this.buttonContainer.setStyleClass("waypointwindow.button");
/*  61 */     this.buttonContainer.setHighlighted(isGoto);
/*     */     
/*  63 */     BLabel label = new BLabel(waypointDestinationPortalDescription.getName());
/*  64 */     label.setHoverEnabled(false);
/*  65 */     setEnabled(unlocked);
/*     */     
/*  67 */     addListener((ComponentListener)new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/*  69 */             TcgUI.getUISoundPlayer().play("ClickForward");
/*  70 */             PanelManager.getInstance().closeAll();
/*     */             try {
/*  72 */               NetworkHandler.instance().getIOHandler().send((Message)new InteractiblePropActionInvokedMessage(MainGameState.getPlayerModel().getId(), portalId, InteractibleType.WAYPOINT));
/*     */             
/*     */             }
/*  75 */             catch (InterruptedException ex) {
/*  76 */               throw new RuntimeException(ex);
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/*  81 */     this.buttonContainer.add((BComponent)label, BorderLayout.WEST);
/*     */     
/*  83 */     BClickthroughLabel bClickthroughLabel = new BClickthroughLabel("");
/*     */     
/*     */     try {
/*  86 */       mapImage = (BImage)TcgGame.getResourceManager().getResource(BImage.class, waypointDestinationPortalDescription.getMapImagePath());
/*     */     
/*     */     }
/*  89 */     catch (NoLocatorException e) {
/*  90 */       e.printStackTrace();
/*  91 */       throw new RuntimeException("Missing image for destination portal: " + waypointDestinationPortalDescription.getId());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  96 */     this.buttonContainer.setEnabled(unlocked);
/*     */ 
/*     */     
/*  99 */     ImageBackground imageBackground = new ImageBackground(ImageBackgroundMode.SCALE_XY, mapImage);
/* 100 */     bClickthroughLabel.setBackground(0, (BBackground)imageBackground);
/* 101 */     bClickthroughLabel.setBackground(1, (BBackground)imageBackground);
/* 102 */     this.buttonContainer.add((BComponent)bClickthroughLabel, BorderLayout.EAST);
/*     */     
/* 104 */     add((BComponent)this.buttonContainer, new Rectangle(0, 0, 310, 92));
/*     */ 
/*     */     
/* 107 */     if (waypointDestinationPortalDescription.isSubscriptionRequired() && !MainGameState.isPlayerSubscriber() && !hasAccess(waypointDestinationPortalDescription.getAccessKeys())) {
/*     */       
/* 109 */       BClickthroughLabel memberIcon = new BClickthroughLabel();
/* 110 */       String locale = (System.getProperty("tcg.locale") != null && !System.getProperty("tcg.locale").equals("")) ? System.getProperty("tcg.locale") : "en";
/*     */ 
/*     */       
/* 113 */       memberIcon.setStyleClass("waypointwindow.label.member." + locale);
/* 114 */       add((BComponent)memberIcon, new Rectangle(0, 0, 310, 92));
/* 115 */       this.buttonContainer.setEnabled(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean hasAccess(List<String> accessKeys) {
/* 120 */     List<String> haveKeys = MainGameState.getAccessKeys();
/* 121 */     List<String> keys = new ArrayList<String>(accessKeys);
/* 122 */     keys.retainAll(haveKeys);
/* 123 */     if (!keys.isEmpty())
/* 124 */       for (String key : keys) {
/* 125 */         long expireTime = ((Long)MainGameState.getAccessKeyExpireTimes().get(haveKeys.indexOf(key))).longValue();
/* 126 */         if (expireTime == 0L || expireTime >= System.currentTimeMillis()) {
/* 127 */           return true;
/*     */         }
/*     */       }  
/* 130 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean mouseExited(MouseEvent mev) {
/* 135 */     this.armed = false;
/*     */     
/* 137 */     return this.buttonContainer.dispatchEvent((BEvent)mev);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean mouseEntered(MouseEvent event) {
/* 143 */     this.armed = this.pressed;
/*     */     
/* 145 */     return this.buttonContainer.dispatchEvent((BEvent)event);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\waypoint\WaypointDestinationPortalButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */