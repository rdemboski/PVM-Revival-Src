package com.funcom.gameengine.resourcemanager;

import java.util.Set;

public interface ResourceLoader {
  void init();
  
  void loadData(ManagedResource<?> paramManagedResource) throws LoadException;
  
  Class<?> getResourceType();
  
  void addAdditionalLoader(ResourceLoader paramResourceLoader);
  
  void removeAdditionalLoader(ResourceLoader paramResourceLoader);
  
  void addNeededLoaderType(Class<?> paramClass);
  
  void removeNeededLoaderType(Class<?> paramClass);
  
  Set<Class<?>> getNeededLoaderTypes();
  
  void setResourceManager(ResourceManager paramResourceManager);
  
  boolean isAsyncLoading();
  
  Object getUnloadInfo(ManagedResource<?> paramManagedResource);
  
  void unloadData(ManagedResource<?> paramManagedResource, Object paramObject);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\ResourceLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */