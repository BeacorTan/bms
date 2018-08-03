package com.common.shiro;

import org.apache.shiro.session.Session;

import java.io.Serializable;

/**
 * @author
 * @create 2018-07-31 17:45
 **/
public final class ShiroSessionContext {

    private static ThreadLocal<UserSession> sessionContext = new ThreadLocal<UserSession>();

    private static ThreadLocal<Integer> UPDATE_SESSION_COUNT = new ThreadLocal<Integer>();

    public static boolean isUpdateSession() {
        Integer count = UPDATE_SESSION_COUNT.get();
        if (count == null) {
            UPDATE_SESSION_COUNT.set(0);
            return false;
        }
        if (count > 2) {
            UPDATE_SESSION_COUNT.set(0);
            return true;
        }
        return false;
    }

    public static void setUpdateCount() {
        UPDATE_SESSION_COUNT.set(UPDATE_SESSION_COUNT.get() + 1);
    }


    public static void setSession(Serializable sessionId, Session session) {
        if (sessionId == null) {
            return;
        }
        sessionContext.set(new UserSession(sessionId, session));
    }

    public static Session getSession(Serializable sessionId) {
        UserSession userSession = sessionContext.get();
        if (userSession != null) {
            Serializable userSessionId = userSession.getSessionId();
            if (sessionId == null) {
                return null;
            }
            if (userSessionId.equals(sessionId)) {
                return userSession.getSession();
            }
        }
        return null;
    }


//    public static boolean isBefore() {
//        UserSession userSession = sessionContext.get();
//        if (userSession == null) {
//            return false;
//        }
//        return userSession.getLastTime().isBefore(LocalDateTime.now().plusSeconds(-10));
//    }


//    public static Session getSession() {
//        return sessionContext.get().getSession();
//    }


    private ShiroSessionContext() {
    }

    static class UserSession {

        public UserSession() {

        }


//        public UserSession(Session session) {
//            this.session = session;
//            this.lastTime = LocalDateTime.now();
//        }

        public UserSession(Serializable sessionId, Session session) {
            this.session = session;
            this.sessionId = sessionId;
        }

        private Session session;

//        private LocalDateTime lastTime;

        private Serializable sessionId;

        public Serializable getSessionId() {
            return sessionId;
        }

        public void setSessionId(Serializable sessionId) {
            this.sessionId = sessionId;
        }

        public Session getSession() {
            return session;
        }

        public void setSession(Session session) {
            this.session = session;
        }

//        public LocalDateTime getLastTime() {
//            return lastTime;
//        }

//        public void setLastTime(LocalDateTime lastTime) {
//            this.lastTime = lastTime;
//        }

    }
}


