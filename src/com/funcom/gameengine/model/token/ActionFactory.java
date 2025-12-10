package com.funcom.gameengine.model.token;

import com.funcom.gameengine.model.action.Action;

public interface ActionFactory {
  Action createAction(String paramString, Object paramObject) throws ActionFactoryException;
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\ActionFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */