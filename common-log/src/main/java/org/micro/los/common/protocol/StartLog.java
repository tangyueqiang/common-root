package org.micro.los.common.protocol;

import org.micro.los.common.cmd.PkgCommand;
import org.slf4j.event.Level;

public class StartLog extends BaseLog {

    private static final long serialVersionUID = 1L;
    private static final long MORE = -1L;
    private PkgCommand cmd;

    public StartLog() {
        super();
        setTimestamp(getTimestamp() + MORE);
    }

    public StartLog(String title, String content) {
        super(title, content);
        setTimestamp(getTimestamp() + MORE);
    }

    public StartLog(String buzz, String title, String content) {
        super(buzz, title, content);
        setTimestamp(getTimestamp() + MORE);
    }

    public StartLog(Level level, String title, String content) {
        super(level, title, content);
        setTimestamp(getTimestamp() + MORE);
    }

    public StartLog(Level level, String buzz, String title, String content) {
        super(level, buzz, title, content);
        setTimestamp(getTimestamp() + MORE);
    }

    public StartLog(String title, String content, PkgCommand cmd) {
        super(title, content);
        setTimestamp(getTimestamp() + MORE);
        this.cmd = cmd;
    }

    public StartLog(String buzz, String title, String content, PkgCommand cmd) {
        super(buzz, title, content);
        setTimestamp(getTimestamp() + MORE);
        this.cmd = cmd;
    }

    public PkgCommand getCmd() {
        return cmd;
    }

    public void setCmd(PkgCommand cmd) {
        this.cmd = cmd;
    }

}
