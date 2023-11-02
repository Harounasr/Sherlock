package de.ssherlock.control.notification;

import java.util.logging.Logger;

public class Notification {

    //parameters
    private String text;
    private NotificationType type;
    private Logger logger;

    private Notification(NotificationBuilder builder) {
        this.text=builder.text;
        this.type=builder.type;
    }

    //Builder Class
    public static class NotificationBuilder{

        //parameters
        private String text;
        private NotificationType type;

        public NotificationBuilder(String text, NotificationType type){
            this.text = text;
            this.type = type;
        }
        public Notification build(){
            return new Notification(this);
        }

    }

}