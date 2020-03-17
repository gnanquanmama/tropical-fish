package com.mcoding.common.command;


public interface ICommandInvoker {

	public <Result> Result invoke(ICommand<Result> command);
}
