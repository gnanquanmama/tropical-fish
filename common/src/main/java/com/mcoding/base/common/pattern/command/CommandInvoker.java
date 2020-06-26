package com.mcoding.base.common.pattern.command;

/**
 * @author wzt on 2019/11/20.
 * @version 1.0
 */
public class CommandInvoker implements ICommandInvoker {

    @Override
    public <Result> Result invoke(ICommand<Result> command) {
        return command.execute(this);
    }

}
