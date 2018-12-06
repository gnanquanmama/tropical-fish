package com.fastdev.command;


public interface ICommandInvoker {

	public <Result> Result invoke(ICommand<Result> command);
}
