/*    */ package com.funcom.gameengine.spatial;
/*    */ 
/*    */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*    */ import com.funcom.gameengine.model.World;
/*    */ import com.funcom.gameengine.model.props.MultipleTargetsModel;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.gameengine.view.RepresentationalNode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BeaconFactory
/*    */ {
/*    */   public static final String BEACON_DFX = "xml/dfx/beacon.xml";
/*    */   private MultipleTargetsModel model;
/*    */   private MultipleTargetsModel.SwitchTargetListener listener;
/*    */   private World world;
/*    */   private PropNode beacon;
/*    */   private DireEffectDescriptionFactory direEffectDescriptionFactory;
/*    */   
/*    */   public BeaconFactory(DireEffectDescriptionFactory direEffectDescriptionFactory, World world) {
/* 22 */     if (world == null)
/* 23 */       throw new IllegalArgumentException("world=null"); 
/* 24 */     if (direEffectDescriptionFactory == null) {
/* 25 */       throw new IllegalArgumentException("direEffectDescriptionFactory=null");
/*    */     }
/* 27 */     this.direEffectDescriptionFactory = direEffectDescriptionFactory;
/* 28 */     this.world = world;
/*    */     
/* 30 */     this.listener = new MultipleTargetsModel.SwitchTargetListener()
/*    */       {
/*    */         public void targetSwitched(MultipleTargetsModel model, MultipleTargetsModel.TargetData currentTarget) {
/* 33 */           if (BeaconFactory.this.beacon != null) {
/* 34 */             BeaconFactory.this.world.removeObject((RepresentationalNode)BeaconFactory.this.beacon);
/*    */           }
/* 36 */           if (currentTarget == null || !currentTarget.show()) {
/* 37 */             BeaconFactory.this.beacon = null;
/*    */             
/*    */             return;
/*    */           } 
/* 41 */           BeaconFactory.this.beacon = new PropNode(new Beacon(currentTarget), 20, "", BeaconFactory.this.direEffectDescriptionFactory);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */           
/* 47 */           BeaconFactory.this.beacon.playDfx("xml/dfx/beacon.xml");
/* 48 */           BeaconFactory.this.world.addObject((RepresentationalNode)BeaconFactory.this.beacon);
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   public void setModel(MultipleTargetsModel model) {
/* 54 */     if (this.model != null) {
/* 55 */       this.model.removeSwitchTargetListener(this.listener);
/*    */     }
/* 57 */     this.model = model;
/* 58 */     model.addSwitchTargetListener(this.listener);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\spatial\BeaconFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */