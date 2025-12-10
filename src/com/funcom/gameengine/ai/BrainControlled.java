package com.funcom.gameengine.ai;

import com.funcom.gameengine.model.command.Command;
import java.util.List;

public interface BrainControlled {
  void immediateCommand(Command paramCommand);
  
  void immediateCommands(List<? extends Command> paramList);
  
  void queueCommand(Command paramCommand);
  
  void queueCommands(List<? extends Command> paramList);
  
  void insertCommand(Command paramCommand);
  
  void insertCommands(Command paramCommand);
  
  boolean isDoingAnything();
  
  boolean hasQueuedCommands();
  
  Command getCurrentCommand();
  
  void clearQueue();
  
  void stopCurrentWork();
  
  boolean isDoingAnythingExceptMoving();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\ai\BrainControlled.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */