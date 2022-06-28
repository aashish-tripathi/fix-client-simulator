package fix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quickfix.*;

public class FixOrderInitiator implements Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(FixOrderInitiator.class);

    public static volatile SessionID sessionID;

    public FixOrderInitiator() {
    }

    @Override
    public void onCreate(SessionID sessionID) {
        LOGGER.info("OnCreate sessionId {}",sessionID);
    }

    @Override
    public void onLogon(SessionID sessionID) {
        LOGGER.info("OnLogon sessionId {}",sessionID);
        FixOrderInitiator.sessionID = sessionID;
    }

    @Override
    public void onLogout(SessionID sessionID) {
        LOGGER.info("OnLogout sessionId {}",sessionID);
        FixOrderInitiator.sessionID = null;
    }

    @Override
    public void toAdmin(Message message, SessionID sessionID) {
        LOGGER.info("ToAdmin sessionId {}",sessionID);
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        LOGGER.info("FromAdmin sessionId {}",sessionID);
    }

    @Override
    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
        LOGGER.info("ToApp sessionId {}",sessionID);
    }

    @Override
    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        LOGGER.info("FromApp sessionId {}",sessionID);
    }
}
