package org.micro.los.common.cmd;

import org.micro.commons.basic.beans.BaseBean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * 日志命令
 */
public class LogCommand extends BaseBean {

    private static final long serialVersionUID = 1L;

    private String cmd;

    private Map<String, Serializable> args;

    public LogCommand() {
        super();
        this.args = new HashMap<>();
    }

    public LogCommand(String cmd) {
        this();
        this.cmd = cmd;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public Map<String, Serializable> getArgs() {
        return args;
    }

    public void setArgs(Map<String, Serializable> args) {
        this.args = args;
    }


}
