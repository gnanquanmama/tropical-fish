package com.mcoding.command;

public interface ICommand<Result> {

	Result execute(ICommandInvoker context);
}
