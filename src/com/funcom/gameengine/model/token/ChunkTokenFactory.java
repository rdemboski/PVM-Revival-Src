package com.funcom.gameengine.model.token;

import com.funcom.commons.jme.cpolygon.CPoint2D;
import com.funcom.gameengine.WorldCoordinate;
import com.funcom.gameengine.model.ResourceGetter;
import com.funcom.gameengine.model.props.InteractibleProp;
import com.funcom.gameengine.model.props.Prop;
import com.funcom.gameengine.view.water.WaterLineCoordinateSet;
import com.funcom.gameengine.view.water.WaterPondCoordinateSet;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import org.jdom.Element;

public interface ChunkTokenFactory {
  Token createLayeredTextureTileToken(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, TokenTargetNode paramTokenTargetNode, Point paramPoint, ResourceGetter paramResourceGetter);
  
  Token createStaticObjectToken(Prop paramProp, WorldCoordinate paramWorldCoordinate, float paramFloat1, float paramFloat2, float paramFloat3, String paramString, float[] paramArrayOffloat, TokenTargetNode paramTokenTargetNode, Point paramPoint, ResourceGetter paramResourceGetter);
  
  Token createInteractibleObjectToken(InteractibleProp paramInteractibleProp, String paramString1, WorldCoordinate paramWorldCoordinate, float paramFloat1, float paramFloat2, String paramString2, String paramString3, float[] paramArrayOffloat, TokenTargetNode paramTokenTargetNode, Point paramPoint, ResourceGetter paramResourceGetter, String paramString4);
  
  Token createMeshObjectToken(WorldCoordinate paramWorldCoordinate, float paramFloat1, float paramFloat2, float paramFloat3, String paramString1, String paramString2, float[] paramArrayOffloat, TokenTargetNode paramTokenTargetNode, Point paramPoint, ResourceGetter paramResourceGetter);
  
  Token createMergedMeshObjectToken(ArrayList<Element> paramArrayList, TokenTargetNode paramTokenTargetNode, ResourceGetter paramResourceGetter);
  
  @Deprecated
  Token createWaterLineToken(float paramFloat1, float paramFloat2, int paramInt1, int paramInt2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, List<WaterLineCoordinateSet> paramList, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, String paramString1, String paramString2, String paramString3, TokenTargetNode paramTokenTargetNode, Point paramPoint, ResourceGetter paramResourceGetter);
  
  @Deprecated
  Token createWaterPondToken(float paramFloat1, float paramFloat2, boolean paramBoolean, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, List<WaterPondCoordinateSet> paramList, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, String paramString1, String paramString2, String paramString3, TokenTargetNode paramTokenTargetNode, Point paramPoint, ResourceGetter paramResourceGetter);
  
  Token createDecalToken(Prop paramProp, WorldCoordinate paramWorldCoordinate, float paramFloat1, float paramFloat2, int paramInt, String paramString, float[] paramArrayOffloat, TokenTargetNode paramTokenTargetNode, Point paramPoint, ResourceGetter paramResourceGetter);
  
  Token createAreaToken(Prop paramProp, List<CPoint2D> paramList, TokenTargetNode paramTokenTargetNode, Point paramPoint, ResourceGetter paramResourceGetter);
  
  Token createParticleObjectToken(String paramString1, WorldCoordinate paramWorldCoordinate, float paramFloat1, float paramFloat2, float paramFloat3, String paramString2, TokenTargetNode paramTokenTargetNode, Point paramPoint, ResourceGetter paramResourceGetter);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\ChunkTokenFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */