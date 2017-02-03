package com.viperpvp.core.game;

import com.viperpvp.core.Core;
import org.bukkit.event.Event;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

import javax.swing.tree.ExpandVetoException;

/**
 * Created by Matt on 05/09/2016.
 */
public abstract class EventListener<T extends Event> implements Subscription {

    public abstract void call(T event);

    private boolean isActive = true;

    private Observable<Event> observable;

    public EventListener() {
        observable = Core.getInstance().observeEvent(this.getClass().asSubclass(Event.class));
        observable.subscribe(item -> {
            if (!isActive) return;
            call((T) item);
        });
    }

    @Override
    public void unsubscribe() {
        isActive = false;
    }

    @Override
    public boolean isUnsubscribed() {
        return isActive;
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
