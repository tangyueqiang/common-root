package org.micro.los.common.cmd;

import org.micro.los.common.protocol.BaseLog;

import java.util.HashSet;
import java.util.Set;

public class PkgCommand extends LogCommand {

    private static final long serialVersionUID = 1L;
    private static final String CMD = "pkg";
    /**
     * 打包字段，默认"title"
     */
    private String uks = "buzz, title";

    /**
     * 打包后是否清除，默认true
     */
    private boolean clean = true;

    /**
     * 打包日志类型
     */
    private Set<String> types;

    public PkgCommand() {
        super();
        setCmd(CMD);
        this.types = new HashSet<>();
    }

    @SafeVarargs
    public PkgCommand(String buzz, String title, Class<? extends BaseLog>... types) {
        this();
        this.getArgs().put("buzz", buzz);
        this.getArgs().put("title", title);
        for (Class<? extends BaseLog> type : types) {
            this.types.add(type.getName());
        }
    }

    public String getUks() {
        return uks;
    }

    public void setUks(String uks) {
        this.uks = uks;
    }

    public boolean isClean() {
        return clean;
    }

    public void setClean(boolean clean) {
        this.clean = clean;
    }

    public Set<String> getTypes() {
        return types;
    }

    public void setTypes(Set<String> types) {
        this.types = types;
    }
}
