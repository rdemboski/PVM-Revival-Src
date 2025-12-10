/*     */ package com.funcom.gameengine.pathfinding2;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.errorhandling.AttributedException;
/*     */ import com.funcom.gameengine.resourcemanager.DefaultResourceManager;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.CSVData;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.xml.DOMConfigurator;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MapConnectivityGraphBuilder
/*     */ {
/*  30 */   private static final Logger LOGGER = Logger.getLogger(MapConnectivityGraphBuilder.class);
/*  31 */   private static final Pattern NAME_EXTRACTOR = Pattern.compile("(.+?)\\([0-9]+?\\)");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static File[] getPortalDefinitionFiles(String portalRootDirectory) {
/*  37 */     File portalsDirectory = new File(portalRootDirectory);
/*  38 */     if (!portalsDirectory.isDirectory() || !portalsDirectory.exists())
/*  39 */       throw new IllegalStateException("Set path is not a directory, or doesn't exist."); 
/*  40 */     return portalsDirectory.listFiles(new RegexFileFilter("^.+?_to_.+?$"));
/*     */   }
/*     */   
/*     */   public static File[] getMapDefinitionFiles(String mapsRootDirectory) {
/*  44 */     File mapsDirectory = new File(mapsRootDirectory);
/*  45 */     if (!mapsDirectory.isDirectory() || !mapsDirectory.exists())
/*  46 */       throw new IllegalStateException("Set path is not a directory, or doesn't exist."); 
/*  47 */     return mapsDirectory.listFiles(new RegexFileFilter("^[0-9]{3}.+?$"));
/*     */   }
/*     */   
/*     */   public static PortalDefinition loadPortal(ResourceManager resourceManager, File portalXmlFile) {
/*  51 */     Document portalDoc = (Document)resourceManager.getResource(Document.class, portalXmlFile.getPath());
/*  52 */     Element actionEl = portalDoc.getRootElement().getChild("action");
/*     */     
/*  54 */     String portalId = actionEl.getChildTextTrim("id");
/*  55 */     String destX = actionEl.getChild("destination").getAttributeValue("x");
/*  56 */     String destY = actionEl.getChild("destination").getAttributeValue("y");
/*  57 */     String destXOffset = actionEl.getChild("destination").getAttributeValue("x-offset");
/*  58 */     String destYOffset = actionEl.getChild("destination").getAttributeValue("y-offset");
/*  59 */     String mapFilename = actionEl.getChildTextTrim("filename");
/*  60 */     String portalFilename = portalXmlFile.getName().substring(0, portalXmlFile.getName().lastIndexOf('.'));
/*     */     
/*  62 */     PortalDefinition portalDefinition = new PortalDefinition(portalId);
/*  63 */     portalDefinition.setPortalFilename(portalFilename);
/*  64 */     portalDefinition.setDestination(new WorldCoordinate(Integer.parseInt(destX), Integer.parseInt(destY), Double.parseDouble(destXOffset), Double.parseDouble(destYOffset), mapFilename, 0));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     return portalDefinition;
/*     */   }
/*     */   
/*     */   public static void fillPortalPositions(ResourceManager resourceManager, File mapRootDirectory, Set<PortalDefinition> portalDefinitions) {
/*  73 */     if (mapRootDirectory == null || !mapRootDirectory.isDirectory() || !mapRootDirectory.exists())
/*  74 */       throw new IllegalArgumentException("mapRootDirectory = null, doesn't exist or is not a directory."); 
/*  75 */     if (portalDefinitions == null || portalDefinitions.isEmpty()) {
/*  76 */       throw new IllegalArgumentException("portalDefinitions = null or empty.");
/*     */     }
/*     */     try {
/*  79 */       Document minimapObjectsDocument = (Document)resourceManager.getResource(Document.class, (new File(mapRootDirectory, "mapobjects.chunk")).getPath());
/*  80 */       List<Element> staticObjectElements = minimapObjectsDocument.getRootElement().getChildren("map_static_object");
/*  81 */       for (Element staticObjectElement : staticObjectElements)
/*     */       {
/*  83 */         String objectFilename = convertToObjectFilename(staticObjectElement.getAttributeValue("name"));
/*  84 */         if (objectFilename == null) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*  90 */         PortalDefinition portal = findPortalByFilename(objectFilename, portalDefinitions);
/*  91 */         if (portal == null) {
/*  92 */           LOGGER.warn("No portal definition for filename: " + objectFilename);
/*     */           
/*     */           continue;
/*     */         } 
/*  96 */         fillPositionForPortal(staticObjectElement, portal, mapRootDirectory);
/*     */       }
/*     */     
/*     */     }
/* 100 */     catch (Exception e) {
/* 101 */       AttributedException attributedException = AttributedException.wrapOrConvert(e);
/* 102 */       attributedException.put("mapRootDirectory", mapRootDirectory);
/* 103 */       attributedException.put("portalDefinitions", portalDefinitions);
/* 104 */       throw attributedException;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void fillHubCityPortalPositions(ResourceManager resourceManager, Set<PortalDefinition> portalDefinitions) {
/* 109 */     CSVData hubcityWaypointsCSV = (CSVData)resourceManager.getResource(CSVData.class, "rpg/*.waypoints.csv");
/*     */ 
/*     */     
/* 112 */     Map<String, WorldCoordinate> waypoints = new HashMap<String, WorldCoordinate>();
/* 113 */     for (String[] data : hubcityWaypointsCSV) {
/* 114 */       String waypointId = data[0];
/* 115 */       String mapId = data[2];
/* 116 */       int x = Integer.parseInt(data[3]);
/* 117 */       int y = Integer.parseInt(data[4]);
/* 118 */       WorldCoordinate worldCoordinate = new WorldCoordinate(x, y, 0.0D, 0.0D, mapId, 0);
/* 119 */       waypoints.put(waypointId, worldCoordinate);
/*     */     } 
/*     */ 
/*     */     
/* 123 */     CSVData csvData = (CSVData)resourceManager.getResource(CSVData.class, "rpg/*.waypointDestinationPortals.csv");
/* 124 */     for (String[] data : csvData) {
/* 125 */       String destPortal = data[0];
/* 126 */       String mapId = data[2];
/* 127 */       int x = Integer.parseInt(data[3]);
/* 128 */       int y = Integer.parseInt(data[4]);
/* 129 */       String waypointId = data[12];
/*     */       
/* 131 */       WorldCoordinate hubCityCoord = waypoints.get(waypointId);
/* 132 */       WorldCoordinate zoneCoord = new WorldCoordinate(x, y, 0.0D, 0.0D, mapId, 0);
/*     */       
/* 134 */       PortalDefinition fromHubCityToTheZone = new PortalDefinition(waypointId, hubCityCoord, zoneCoord, "RPG_STYLESHEET");
/* 135 */       PortalDefinition fromZoneToTheHubCity = new PortalDefinition(destPortal, zoneCoord, hubCityCoord, "RPG_STYLESHEET");
/*     */       
/* 137 */       portalDefinitions.add(fromHubCityToTheZone);
/* 138 */       portalDefinitions.add(fromZoneToTheHubCity);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String convertToObjectFilename(String nameAttributeValue) throws AttributedException {
/* 143 */     Matcher matcher = NAME_EXTRACTOR.matcher(nameAttributeValue);
/* 144 */     if (matcher.matches()) {
/* 145 */       return matcher.group(1);
/*     */     }
/* 147 */     return null;
/*     */   }
/*     */   
/*     */   private static void fillPositionForPortal(Element staticObjectElement, PortalDefinition portal, File mapRootDirectory) {
/* 151 */     String sourceX = staticObjectElement.getAttributeValue("x");
/* 152 */     String sourceY = staticObjectElement.getAttributeValue("y");
/* 153 */     String sourceXOffset = staticObjectElement.getAttributeValue("x-offset");
/* 154 */     String sourceYOffset = staticObjectElement.getAttributeValue("y-offset");
/* 155 */     String sourceMap = mapRootDirectory.getName();
/*     */     
/* 157 */     WorldCoordinate position = new WorldCoordinate(Integer.parseInt(sourceX), Integer.parseInt(sourceY), Double.parseDouble(sourceXOffset), Double.parseDouble(sourceYOffset), sourceMap, 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 163 */     portal.addPosition(position);
/*     */   }
/*     */   
/*     */   private static PortalDefinition findPortalByFilename(String objectFilename, Set<PortalDefinition> portalDefinitions) {
/* 167 */     for (PortalDefinition portalDefinition : portalDefinitions) {
/* 168 */       if (portalDefinition.getPortalFilename().equals(objectFilename))
/* 169 */         return portalDefinition; 
/*     */     } 
/* 171 */     return null;
/*     */   }
/*     */   
/*     */   public static MapConnectivityGraph createMapConnectivityGraph(Set<PortalDefinition> portalDefinitions) {
/* 175 */     MapConnectivityGraph connectivityGraph = new MapConnectivityGraph();
/*     */     
/* 177 */     for (PortalDefinition portalDefinition : portalDefinitions) {
/* 178 */       for (WorldCoordinate source : portalDefinition.getPositions()) {
/* 179 */         MapConnectionNode sourceNode = findOrCreateContainerNode(connectivityGraph, source);
/* 180 */         MapConnectionNode destinationNode = findOrCreateContainerNode(connectivityGraph, portalDefinition.getDestination());
/* 181 */         sourceNode.addNeighbor(destinationNode);
/* 182 */         sourceNode.definePortal(destinationNode, portalDefinition);
/*     */       } 
/*     */     } 
/*     */     
/* 186 */     return connectivityGraph;
/*     */   }
/*     */   
/*     */   private static MapConnectionNode findOrCreateContainerNode(MapConnectivityGraph connectivityGraph, WorldCoordinate position) {
/* 190 */     MapConnectionNode node = connectivityGraph.toGraphNode(position);
/* 191 */     if (node == null) {
/* 192 */       node = new MapConnectionNode(position.getMapId());
/* 193 */       connectivityGraph.addNode(node);
/*     */     } 
/*     */     
/* 196 */     return node;
/*     */   }
/*     */   
/*     */   public static void assertPortalDefinitions(Set<PortalDefinition> portalDefinitions, boolean removeInvalids) {
/* 200 */     StringBuilder builder = new StringBuilder();
/* 201 */     Iterator<PortalDefinition> iterator = portalDefinitions.iterator();
/* 202 */     int removedCount = 0;
/* 203 */     while (iterator.hasNext()) {
/* 204 */       PortalDefinition portalDefinition = iterator.next();
/*     */ 
/*     */ 
/*     */       
/* 208 */       boolean portalHasError = checkForNull(builder, portalDefinition.getId(), portalDefinition, "- Portal has no ID: ");
/* 209 */       portalHasError |= checkForNull(builder, portalDefinition.getPortalFilename(), portalDefinition, "- Portal has no filename: ");
/* 210 */       portalHasError |= checkForEmpty(builder, portalDefinition.getPositions(), portalDefinition, "- Portal has no position: ");
/* 211 */       portalHasError |= checkForNull(builder, portalDefinition.getDestination(), portalDefinition, "- Portal has no destination: ");
/* 212 */       portalHasError |= checkForSameMap(builder, portalDefinition);
/*     */       
/* 214 */       if (portalHasError && removeInvalids) {
/* 215 */         iterator.remove();
/* 216 */         removedCount++;
/*     */       } 
/*     */     } 
/*     */     
/* 220 */     if (builder.length() != 0) {
/* 221 */       builder.insert(0, "Portal errors, checked: '" + (portalDefinitions.size() + removedCount) + "', removed: '" + removedCount + "'\n");
/* 222 */       LOGGER.error(builder.toString());
/*     */     } else {
/* 224 */       LOGGER.info("Portals asserted, everything OK!");
/*     */     } 
/*     */   }
/*     */   private static boolean checkForSameMap(StringBuilder builder, PortalDefinition portalDefinition) {
/* 228 */     if (portalDefinition.getPositions().isEmpty() || portalDefinition.getDestination() == null)
/* 229 */       return false; 
/* 230 */     List<WorldCoordinate> coordinateList = portalDefinition.getPositions();
/* 231 */     for (WorldCoordinate position : coordinateList) {
/* 232 */       if (position.getMapId().equals(portalDefinition.getDestination().getMapId())) {
/* 233 */         builder.append("- Portal position and destination lie on the same map: ").append(portalDefinition).append("\n");
/* 234 */         return true;
/*     */       } 
/* 236 */     }  return false;
/*     */   }
/*     */   
/*     */   private static boolean checkForNull(StringBuilder builder, Object variableToCheck, PortalDefinition portalDefinition, String errorMessage) {
/* 240 */     if (variableToCheck == null) {
/* 241 */       builder.append(errorMessage).append(portalDefinition).append("\n");
/* 242 */       return true;
/*     */     } 
/* 244 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean checkForEmpty(StringBuilder builder, List variableToCheck, PortalDefinition portalDefinition, String errorMessage) {
/* 248 */     if (variableToCheck.isEmpty()) {
/* 249 */       builder.append(errorMessage).append(portalDefinition).append("\n");
/* 250 */       return true;
/*     */     } 
/* 252 */     return false;
/*     */   }
/*     */   
/*     */   private static class RegexFileFilter implements FileFilter {
/*     */     private Pattern regex;
/*     */     
/*     */     private RegexFileFilter(String regex) {
/* 259 */       this.regex = Pattern.compile(regex);
/*     */     }
/*     */     
/*     */     public boolean accept(File pathname) {
/* 263 */       return this.regex.matcher(pathname.getName()).matches();
/*     */     }
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
/*     */   public static void main(String[] args) throws IOException {
/* 341 */     if (args.length < 1) {
/* 342 */       System.out.println("Usage information: <cmd> <portal-definitions-directory> <maps-root-directory>");
/* 343 */       System.out.println(String.format("- %-25s %s", new Object[] { "<cmd>", "command used to launch this script" }));
/* 344 */       System.out.println(String.format("- %-25s %s", new Object[] { "<resources-directory>", "root resources directory" }));
/* 345 */       System.out.println("");
/* 346 */       System.out.println("Map connectivity graph will be saved inside maps root directory as 'map_connectivity_graph.pthgrph");
/* 347 */       System.exit(1);
/*     */     } 
/*     */     
/* 350 */     DOMConfigurator.configure(ClassLoader.getSystemResource("com/funcom/gameengine/pathfinding2/map_builder_logging.xml"));
/*     */     
/* 352 */     System.out.println("Working...");
/* 353 */     String resourcesDir = args[0];
/* 354 */     File mapsRootDirectoryFile = new File(resourcesDir, "xml");
/* 355 */     File portalsRootDirectoryFile = new File(resourcesDir, "map_elements/interactive_objects");
/*     */     
/* 357 */     DefaultResourceManager defaultResourceManager = new DefaultResourceManager();
/* 358 */     defaultResourceManager.addResourceRoot("../resources");
/* 359 */     defaultResourceManager.addResourceRoot(".");
/* 360 */     defaultResourceManager.initDefaultLoaders();
/*     */     
/* 362 */     File[] mapFiles = getMapDefinitionFiles(mapsRootDirectoryFile.getPath());
/* 363 */     File[] portalFiles = getPortalDefinitionFiles(portalsRootDirectoryFile.getPath());
/*     */ 
/*     */     
/* 366 */     Set<PortalDefinition> portalDefinitions = new HashSet<PortalDefinition>();
/* 367 */     for (File portalFile : portalFiles) {
/* 368 */       portalDefinitions.add(loadPortal((ResourceManager)defaultResourceManager, portalFile));
/*     */     }
/*     */     
/* 371 */     for (File mapFile : mapFiles) {
/* 372 */       fillPortalPositions((ResourceManager)defaultResourceManager, mapFile, portalDefinitions);
/*     */     }
/*     */     
/* 375 */     fillHubCityPortalPositions((ResourceManager)defaultResourceManager, portalDefinitions);
/*     */ 
/*     */     
/* 378 */     assertPortalDefinitions(portalDefinitions, true);
/*     */ 
/*     */     
/* 381 */     MapConnectivityGraph mapConnectivityGraph = createMapConnectivityGraph(portalDefinitions);
/*     */     
/* 383 */     BufferedOutputStream fos = null;
/*     */     try {
/* 385 */       fos = new BufferedOutputStream(new FileOutputStream(new File("../resources/configuration/map_connectivity_graph.pthgrph")));
/*     */ 
/*     */       
/* 388 */       MapConnectivityGraphSerializer.save(mapConnectivityGraph, fos);
/*     */     } finally {
/* 390 */       if (fos != null)
/* 391 */         fos.close(); 
/*     */     } 
/* 393 */     System.out.println("Done!");
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\pathfinding2\MapConnectivityGraphBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */