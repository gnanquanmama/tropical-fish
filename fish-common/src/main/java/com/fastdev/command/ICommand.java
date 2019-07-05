package com.fastdev.command;

public interface ICommand<Result> {

	Result execute(ICommandInvoker context);
}
