package org.micro.los.common.protocol;

import org.micro.los.common.cmd.PkgCommand;
import org.slf4j.event.Level;

public class FinishedLog extends StartLog {

    private static final long serialVersionUID = 1L;
    private static final long MORE = 2L;

    public FinishedLog() {
        super();
        setTimestamp(getTimestamp() + MORE);
    }

    public FinishedLog(String title, String content) {
        super(title, content);
        setTimestamp(getTimestamp() + MORE);
    }

    public FinishedLog(String buzz, String title, String content) {
        super(buzz, title, content);
        setTimestamp(getTimestamp() + MORE);
    }

    public FinishedLog(Level level, String title, String content) {
        super(level, title, content);
        setTimestamp(getTimestamp() + MORE);
    }

    public FinishedLog(Level level, String buzz, String title, String content) {
        super(level, buzz, title, content);
        setTimestamp(getTimestamp() + MORE);
    }

    public FinishedLog(String title, String content, PkgCommand cmd) {
        super(title, content, cmd);
        setTimestamp(getTimestamp() + MORE);
    }

    public FinishedLog(String buzz, String title, String content, PkgCommand cmd) {
        super(buzz, title, content, cmd);
        setTimestamp(getTimestamp() + MORE);
    }


}
