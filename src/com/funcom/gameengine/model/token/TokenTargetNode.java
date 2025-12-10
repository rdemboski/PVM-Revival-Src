package com.funcom.gameengine.model.token;

import com.funcom.gameengine.view.OptimizedNode;
import com.jme.scene.Spatial;

@Deprecated
public interface TokenTargetNode extends OptimizedNode {
  public static class Dummy implements TokenTargetNode {
    public void attachStaticChild(Spatial child) {}
    
    public void attachAnimatedChild(Spatial child) {}
    
    public void updateRenderState() {}
  }
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\TokenTargetNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */