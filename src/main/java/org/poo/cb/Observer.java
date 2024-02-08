package org.poo.cb;

public class Observer {
    Cont subject;
    public Observer(Cont subject) {
        this.subject = subject;
        subject.attach(this);
    }
    public void update() {
        subject.getOwner().addNotification("You have recived a notification");
    }
}
