package org.micro.los.common.cmd;

/**
 * 命令执行器
 */
public interface CmdExecutor {

    /**
     * 执行命令
     *
     * @param cmd 命令
     */
    void execute(LogCommand cmd);

}
