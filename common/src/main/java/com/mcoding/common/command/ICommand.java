package com.mcoding.common.command;

public interface ICommand<Result> {

	Result execute(ICommandInvoker context);
}
