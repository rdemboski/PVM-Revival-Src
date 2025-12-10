package com.funcom.rpgengine2.creatures;

import com.funcom.rpgengine2.quests.Quest;

public interface QuestSupportable extends BasicQuestSupportable {
  void addQuest(Quest paramQuest);
  
  Object completeQuest(String paramString);
  
  long getQuestCompletionTime(String paramString);
  
  Quest getCurrentQuest(String paramString);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\QuestSupportable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */