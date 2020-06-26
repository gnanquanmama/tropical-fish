package com.mcoding.base.common.pattern.command;

public interface ICommand<Result> {

	Result execute(ICommandInvoker context);
}
