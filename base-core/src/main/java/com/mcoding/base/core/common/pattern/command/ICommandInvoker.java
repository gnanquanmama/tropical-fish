package com.mcoding.base.core.common.pattern.command;


public interface ICommandInvoker {

	public <Result> Result invoke(ICommand<Result> command);
}
