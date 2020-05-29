import org.json.JSONObject;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.*;

@ServerEndpoint("/action")
public class MyServer {
    private static final Set<MySession> sessions = Collections.synchronizedSet(new HashSet<>());

    private MySession searchSession(Session session) {
        for (MySession mySession : sessions) {
            if (mySession.session == session) {
                return mySession;
            }
        }
        return null;
    }

    @OnOpen
    public void open(Session session) {
        sessions.add(new MySession(session));
    }

    @OnMessage
    public void message(Session session, String msg) {
        System.out.println("Received message " + msg);
        MySession mySession = searchSession(session);
        if (mySession != null) {
            JSONObject json = new JSONObject(msg);
            int vFromId = json.getInt("from");
            int vToId = json.getInt("to");
            mySession.searchQuery(vFromId, vToId);
        }
    }

    @OnClose
    public void close(Session session) {
        MySession mySession = searchSession(session);
        if (mySession != null) {
            sessions.remove(mySession);
        }
    }
}
