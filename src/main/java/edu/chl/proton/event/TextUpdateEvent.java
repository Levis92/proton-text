package edu.chl.proton.event;

/**
 * Created by ludvig on 2017-05-19.
 */
public class TextUpdateEvent {
        //Should be final?
        public final String message;


        public TextUpdateEvent(String message) {
            this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
