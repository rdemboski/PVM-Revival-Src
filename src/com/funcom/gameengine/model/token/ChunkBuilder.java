package com.funcom.gameengine.model.token;

import com.funcom.commons.jme.cpolygon.CPoint2D;
import com.funcom.gameengine.WorldCoordinate;
import com.funcom.gameengine.model.ResourceGetter;
import com.funcom.gameengine.model.chunks.ChunkNode;
import com.funcom.gameengine.model.props.InteractibleProp;
import com.funcom.gameengine.model.props.Prop;
import com.funcom.gameengine.spatial.LineNode;
import com.funcom.gameengine.view.water.WaterLineCoordinateSet;
import com.funcom.gameengine.view.water.WaterPondCoordinateSet;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jdom.Element;

public interface ChunkBuilder {
  void createLayeredTextureTile(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, TokenTargetNode paramTokenTargetNode, Point paramPoint, ResourceGetter paramResourceGetter);
  
  void createStaticObject(Prop paramProp, WorldCoordinate paramWorldCoordinate, float paramFloat1, float paramFloat2, float paramFloat3, String paramString, float[] paramArrayOffloat, TokenTargetNode paramTokenTargetNode, Point paramPoint, ResourceGetter paramResourceGetter);
  
  void createInteractibleObject(InteractibleProp paramInteractibleProp, String paramString1, WorldCoordinate paramWorldCoordinate, float paramFloat1, float paramFloat2, String paramString2, String paramString3, float[] paramArrayOffloat, TokenTargetNode paramTokenTargetNode, Point paramPoint, ResourceGetter paramResourceGetter, String paramString4);
  
  void createMeshObject(WorldCoordinate paramWorldCoordinate, float paramFloat1, float paramFloat2, float paramFloat3, String paramString1, String paramString2, float[] paramArrayOffloat, TokenTargetNode paramTokenTargetNode, Point paramPoint, ResourceGetter paramResourceGetter);
  
  void createMergedMeshObject(ArrayList<Element> paramArrayList, TokenTargetNode paramTokenTargetNode, ResourceGetter paramResourceGetter);
  
  void createWaterLine(float paramFloat1, float paramFloat2, int paramInt1, int paramInt2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, List<WaterLineCoordinateSet> paramList, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, String paramString1, String paramString2, String paramString3, TokenTargetNode paramTokenTargetNode, Point paramPoint, ResourceGetter paramResourceGetter);
  
  void createWaterPond(float paramFloat1, float paramFloat2, boolean paramBoolean, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, List<WaterPondCoordinateSet> paramList, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, String paramString1, String paramString2, String paramString3, TokenTargetNode paramTokenTargetNode, Point paramPoint, ResourceGetter paramResourceGetter);
  
  void createDecal(Prop paramProp, WorldCoordinate paramWorldCoordinate, float paramFloat1, float paramFloat2, int paramInt, String paramString, float[] paramArrayOffloat, TokenTargetNode paramTokenTargetNode, Point paramPoint, ResourceGetter paramResourceGetter);
  
  void createCollisionNode(WorldCoordinate paramWorldCoordinate, String paramString, TokenTargetNode paramTokenTargetNode, Point paramPoint, ResourceGetter paramResourceGetter);
  
  void createCollisionLine(String paramString1, String paramString2, double paramDouble, LineNode paramLineNode, TokenTargetNode paramTokenTargetNode, Point paramPoint, ResourceGetter paramResourceGetter);
  
  void createArea(Prop paramProp, List<CPoint2D> paramList, TokenTargetNode paramTokenTargetNode, Point paramPoint, ResourceGetter paramResourceGetter);
  
  void createSpawnPoint(WorldCoordinate paramWorldCoordinate, float paramFloat, String paramString1, String paramString2, TokenTargetNode paramTokenTargetNode, Point paramPoint, int paramInt1, boolean paramBoolean1, int paramInt2, String paramString3, Map<String, Object> paramMap, boolean paramBoolean2);
  
  void createVendor(WorldCoordinate paramWorldCoordinate, String paramString1, String paramString2, TokenTargetNode paramTokenTargetNode, Point paramPoint, float paramFloat);
  
  void createPatrolNode(WorldCoordinate paramWorldCoordinate, String paramString1, String paramString2, float paramFloat, TokenTargetNode paramTokenTargetNode, Point paramPoint, ResourceGetter paramResourceGetter);
  
  void createPatrolLine(String paramString1, String paramString2, LineNode paramLineNode, String paramString3, TokenTargetNode paramTokenTargetNode, Point paramPoint, ResourceGetter paramResourceGetter);
  
  void createTeleportProp(String paramString1, WorldCoordinate paramWorldCoordinate1, String paramString2, WorldCoordinate paramWorldCoordinate2, String paramString3);
  
  void createParticleObject(String paramString1, WorldCoordinate paramWorldCoordinate, float paramFloat1, float paramFloat2, float paramFloat3, String paramString2, TokenTargetNode paramTokenTargetNode, Point paramPoint, ResourceGetter paramResourceGetter);
  
  void createCheckpoint(String paramString, WorldCoordinate paramWorldCoordinate, ChunkNode paramChunkNode, Point paramPoint, float paramFloat1, float paramFloat2, float paramFloat3);
  
  void createQuestGoToProp(String paramString, WorldCoordinate paramWorldCoordinate, ChunkNode paramChunkNode, Point paramPoint, float paramFloat, boolean paramBoolean);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\ChunkBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */