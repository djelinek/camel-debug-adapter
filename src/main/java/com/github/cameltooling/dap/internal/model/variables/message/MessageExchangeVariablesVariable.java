package com.github.cameltooling.dap.internal.model.variables.message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.camel.api.management.mbean.ManagedBacklogDebuggerMBean;
import org.eclipse.lsp4j.debug.SetVariableArguments;
import org.eclipse.lsp4j.debug.SetVariableResponse;
import org.eclipse.lsp4j.debug.Variable;

import com.github.cameltooling.dap.internal.IdUtils;
import com.github.cameltooling.dap.internal.types.ExchangeVariable;

public class MessageExchangeVariablesVariable extends Variable {

	private final List<ExchangeVariable> exchangeVariables;
	private final String breakpointId;

	public MessageExchangeVariablesVariable(int parentVariablesReference, List<ExchangeVariable> exchangeVariables, String breakpointId) {
		this.exchangeVariables = exchangeVariables;
		this.breakpointId = breakpointId;
		setName("Variables");
		setValue("");
		int headerVarRefId = IdUtils.getPositiveIntFromHashCode((parentVariablesReference+"@ExchangeVariables@").hashCode());
		setVariablesReference(headerVarRefId);
	}

	public Collection<Variable> createVariables() {
		Collection<Variable> variables = new ArrayList<>();
		if (exchangeVariables != null) {
			for (ExchangeVariable exchangeVariable : exchangeVariables) {
				variables.add(createVariable(exchangeVariable.getKey(), exchangeVariable.getValue()));
			}
		}
		return variables;
	}

	private Variable createVariable(String name, String value) {
		Variable variable = new Variable();
		variable.setName(name);
		variable.setValue(value);
		return variable ;
	}
	
	public SetVariableResponse setVariableIfInScope(SetVariableArguments args, ManagedBacklogDebuggerMBean debugger) {
		if (args.getVariablesReference() == getVariablesReference()) {
			debugger.setExchangePropertyOnBreakpoint(breakpointId, args.getName(), args.getValue());
			if (exchangeVariables != null) {
				for (ExchangeVariable exchangeVariable : exchangeVariables) {
					if (exchangeVariable.getKey().equals(args.getName())) {
						exchangeVariable.setValue(args.getValue());
					}
				}
			}
			SetVariableResponse response = new SetVariableResponse();
			response.setValue(args.getValue());
			return response;
		}
		return null;
	}

	public List<ExchangeVariable> getExchangeVariables() {
		return exchangeVariables;
	}
}
