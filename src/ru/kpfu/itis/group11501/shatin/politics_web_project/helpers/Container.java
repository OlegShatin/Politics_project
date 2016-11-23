package ru.kpfu.itis.group11501.shatin.politics_web_project.helpers;

import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Candidate;
import ru.kpfu.itis.group11501.shatin.politics_web_project.models.Message;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class Container {

        public Message getMessage() {
            return message;
        }

        public Candidate getPerson() {
            return candidate;
        }

        private Message message;

        public Container(Message message, Candidate candidate) {
            this.message = message;
            this.candidate = candidate;
        }

        private Candidate candidate;


}
