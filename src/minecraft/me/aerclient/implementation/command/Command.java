package me.aerclient.implementation.command;


import me.aerclient.implementation.utils.Utilities;

/**
 * Base class for all Commands
 */
public abstract class Command implements Utilities {

	protected String alias;
	protected String description;
	protected String syntax;

	public Command(String alias, String description, String syntax) {
		this.alias = alias;
		this.description = description;
		this.syntax = syntax;
	}


	/**
	 * Gets the string that has to be input to call this command
	 */
	public String getAlias() {
		return this.alias;
	}

	/**
	 * Gets the description of this command
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Gets the syntax of this command
	 */
	public String getSyntax() {
		return this.syntax;
	}

	/**
	 * Runs this command, taking in the string that triggered it, and the args of the command
	 */
	public abstract void onCommand(String command, String[] args) throws Exception;

}
