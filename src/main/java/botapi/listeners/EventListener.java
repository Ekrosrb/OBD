package botapi.listeners;

import botapi.entity.utilEntity.IUpdate;

public interface EventListener {
    void onEventListener(IUpdate update);
}
