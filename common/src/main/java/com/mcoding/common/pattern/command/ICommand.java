package com.mcoding.common.pattern.command;

public interface ICommand<Result> {

	Result execute(ICommandInvoker context);
}
