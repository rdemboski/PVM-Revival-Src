package com.funcom.gameengine.resourcemanager;

import com.funcom.gameengine.utils.LoadingScreenListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ResourceManager extends LoadingScreenListener {
  void initDefaultLoaders();
  
  void addTypeLoader(ResourceLoader paramResourceLoader);
  
  void removeTypeLoader(Class<?> paramClass);
  
  Object getDummyResource(Class<?> paramClass);
  
  DummyResourceFactory getDummyResourceFactory();
  
  void setDummyResourceFactory(DummyResourceFactory paramDummyResourceFactory);
  
  <T> T getResource(Class<T> paramClass, String paramString) throws ResourceManagerException;
  
  <T> ManagedResource<T> getManagedResource(Class<T> paramClass, String paramString) throws ResourceManagerException;
  
  <T> T getResource(Class<T> paramClass, String paramString, CacheType paramCacheType) throws ResourceManagerException;
  
  <T> T getResourceByLocale(Class<T> paramClass, String paramString, CacheType paramCacheType) throws ResourceManagerException;
  
  <T> ManagedResource<T> getManagedResource(Class<T> paramClass, String paramString, CacheType paramCacheType) throws ResourceManagerException;
  
  <T> T getResource(Class<T> paramClass, String paramString, CacheType paramCacheType, Map<Object, Object> paramMap) throws ResourceManagerException;
  
  <T> ManagedResource<T> getManagedResource(String paramString, CacheType paramCacheType, Map<Object, Object> paramMap, Class<T> paramClass) throws ResourceManagerException;
  
  <T> T getResourceCopy(Class<T> paramClass, String paramString) throws ResourceManagerException;
  
  <T> ManagedResource<T> getManagedResourceCopy(Class<T> paramClass, String paramString) throws ResourceManagerException;
  
  <T> T getResourceCopy(Class<T> paramClass, String paramString, Map<Object, Object> paramMap) throws ResourceManagerException;
  
  <T> ManagedResource<T> getManagedResourceCopy(Class<T> paramClass, String paramString, Map<Object, Object> paramMap) throws ResourceManagerException;
  
  <T> void getManagedResourceAsync(Class<T> paramClass, String paramString, ResourceListener<T> paramResourceListener) throws ResourceManagerException;
  
  <T> void getManagedResourceAsync(Class<T> paramClass, String paramString, ResourceListener<T> paramResourceListener, CacheType paramCacheType) throws ResourceManagerException;
  
  <T> void getManagedResourceAsync(Class<T> paramClass, String paramString, ResourceListener<T> paramResourceListener, CacheType paramCacheType, Map<Object, Object> paramMap) throws ResourceManagerException;
  
  boolean isResourceLoaded(ResourceTypeKey paramResourceTypeKey);
  
  void update();
  
  void clearUnusedResources(CacheType paramCacheType);
  
  void clearAllUnusedResources();
  
  boolean shouldClear(ManagedResource<?> paramManagedResource);
  
  void clearResource(String paramString, Class paramClass);
  
  boolean isMapLoadingHint();
  
  boolean isAsyncLoading();
  
  boolean addResourceRoot(String paramString);
  
  boolean remove(String paramString);
  
  Set<String> getResourceRoots();
  
  void notifyLoadingScreenStarted(String paramString);
  
  void notifyLoadingScreenFinished(String paramString);
  
  boolean isUseJars();
  
  boolean exists(String paramString);
  
  void shutdown();
  
  List<String> listFiles(String paramString1, String paramString2);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\ResourceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */