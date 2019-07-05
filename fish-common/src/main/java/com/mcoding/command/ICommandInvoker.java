package com.mcoding.command;


public interface ICommandInvoker {

	public <Result> Result invoke(ICommand<Result> command);
}
