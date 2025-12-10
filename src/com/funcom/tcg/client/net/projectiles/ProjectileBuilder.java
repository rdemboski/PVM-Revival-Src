package com.funcom.tcg.client.net.projectiles;

import com.funcom.gameengine.view.Projectile;
import com.funcom.tcg.net.message.ProjectileUpdateMessage;

public interface ProjectileBuilder {
  Projectile loadAndFireProjectile(ProjectileUpdateMessage.NewProjectileData paramNewProjectileData);
  
  void killProjectile(ProjectileUpdateMessage.RemovedProjectileData paramRemovedProjectileData);
  
  void updateProjectile(ProjectileUpdateMessage.ProjectileUpdateData paramProjectileUpdateData);
  
  boolean isRunning(int paramInt);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\projectiles\ProjectileBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */