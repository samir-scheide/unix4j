package org.unix4j.gen;//FIXME remove gen package when ready to replace Unix4j in org.unix4j

import org.unix4j.builder.gen.Unix4jCommandBuilder;//FIXME remove gen package when ready

<#function hasNoInputMethod def>
	<#foreach method in def.methods>
		<#if !method.input>
			<#return true>
		</#if>
	</#foreach>
	<#return false>
</#function>

<#foreach def in commandDefs>
<#if hasNoInputMethod(def)>
import ${def.command.pkg.name}.${def.command.simpleName};
</#if>
</#foreach>

/**
 * Utility class with static methods serving as starting point to create a
 * command or build a command chain joining several commands.
 * <p> 
 * Every method returns a new builder instance. For more information and a 
 * detailed description of command building and chaining, see 
 * {@link Unix4jCommandBuilder}.
 */
public final class Unix4j {

	/**
	 * Returns a builder to create a command or command chain providing no
	 * input to the first command.
	 * 
	 * @return the builder to create the command or command chain
	 */
	public static Unix4jCommandBuilder builder() {
		return new Unix4jCommandBuilder();
	}

<#foreach def in commandDefs>
<#foreach method in def.methods>
<#if !method.input>
	/**
	 * ${method.desc}
	 * <p>
	 * Note that the method returns the command builder to allow for command 
	 * chaining. The command itself is returned by the {@link Unix4jCommandBuilder#build() build()} method
	 * of the returned builder (see {@link Unix4jCommandBuilder} for more information).
	 *
<#foreach arg in method.args>
	 * @param ${arg} ${def.operands[arg].desc}
</#foreach>
	 * @return	the command builder to allow for method chaining. Method
	 * 			chaining is used here to create command chains. Adding a command 
	 * 			to the chain usually means that the previous command <i>pipes</i> 
	 * 			its output to the added command (the pipe symbol in unix)
	 */
	public static Unix4jCommandBuilder ${method.name}(<#foreach arg in method.args>${def.operands[arg].type} ${arg}<#if arg_has_next>, </#if></#foreach>) {
		return builder().${method.name}(<#foreach arg in method.args>${arg}<#if arg_has_next>, </#if></#foreach>);
	}
</#if>
</#foreach>
</#foreach>
	
	// no instances
	private Unix4j() {
		super();
	}
}