/*
 * Copyright (C) 2008  Distributed Computing System (DCS) Group, Computer
 * Science Department - University of Piemonte Orientale, Alessandria (Italy).
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.unipmn.di.dcs.grid.core.middleware.sched;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class for conditional stage-out rules.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class ConditionalStageOutRule implements IStageOutRule
{
	private IStagingCondition cond; /** The stage-out condition. */
	private IStageOutRule trueRule; /** The stage-out rule to be executed when the condition holds. */
	private IStageOutRule falseRule; /** The stage-out rule to be executed when the condition doesn't hold. */

	/** A constructor. */
	public ConditionalStageOutRule(IStagingCondition cond, IStageOutRule trueRule, IStageOutRule falseRule)
	{
		this.cond = cond;
		this.trueRule = trueRule;
		this.falseRule = falseRule;
	}

	/** A constructor. */
	public ConditionalStageOutRule(IStagingCondition cond, IStageOutRule trueRule)
	{
		this( cond, trueRule, null );
	}

	/** Sets the stage-out condition. */
	public void setCondition(IStagingCondition cond)
	{
		this.cond = cond;
	}

	/** Returns the stage-out condition. */
	public IStagingCondition getCondition()
	{
		return this.cond;
	}

	/** Sets the rule to be executed when the stage-out condition holds. */
	public void setTrueRule(IStageOutRule value)
	{
		this.trueRule = value;
	}

	/**
	 * Returns the rule to be executed when the stage-out condition
	 * holds.
	 */
	public IStageOutRule getTrueRule()
	{
		return this.trueRule;
	}

	/**
	 * Sets the rule to be executed when the stage-out condition doesn't
	 * hold.
	 */
	public void setFalseRule(IStageOutRule value)
	{
		this.falseRule = value;
	}

	/**
	 * Returns the rule to be executed when the stage-out condition doesn't
	 * hold.
	 */
	public IStageOutRule getFalseRule()
	{
		return this.falseRule;
	}

	//@{ IStageOutRule implementation

	public void setActions(Collection<IStageOutAction> value)
	{
		if ( this.getTrueRule() != null )
		{
			this.getTrueRule().setActions( value );
		}
		if ( this.getFalseRule() != null )
		{
			this.getFalseRule().setActions( value );
		}
	}

	public void addAction(IStageOutAction value)
	{
		if ( this.getTrueRule() != null )
		{
			this.getTrueRule().addAction( value );
		}
		if ( this.getFalseRule() != null )
		{
			this.getFalseRule().addAction( value );
		}
	}

	public List<IStageOutAction> getActions()
	{
		Set<IStageOutAction> actions = new HashSet<IStageOutAction>();

		if ( this.getTrueRule() != null )
		{
			actions.addAll( this.getTrueRule().getActions() );
		}
		if ( this.getFalseRule() != null )
		{
			actions.addAll( this.getFalseRule().getActions() );
		}

		return new ArrayList<IStageOutAction>( actions );
	}

	//@} IStageOutRule implementation

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		sb.append( "if (" + this.cond.toString() + ") then\n" );
		if ( this.trueRule != null )
		{
			sb.append( "\t" + this.trueRule.toString() + "\n" );
		}
		if ( this.falseRule != null )
		{
			sb.append( "else\n" );
			sb.append( "\t" + this.falseRule.toString() + "\n" );
		}
		sb.append( "endif\n" );

		return sb.toString();
	}
} 
