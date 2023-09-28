import org.apache.logging.log4j.message.StringMapMessage;

public class LoggInfo {



    public StringMapMessage toStringMapMessage() {
       return  new StringMapMessage()
                .with("message", "Hello!")
                .with("user.name", "jonsw")
                .with("event.action", "");

    }
}
