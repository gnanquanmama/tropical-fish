package com.mcoding.base.common.pattern.command;


public interface ICommandInvoker {

	public <Result> Result invoke(ICommand<Result> command);
}
