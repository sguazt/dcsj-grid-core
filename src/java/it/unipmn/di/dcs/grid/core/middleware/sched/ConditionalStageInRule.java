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
 * Class for conditional stage-in rules.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class ConditionalStageInRule implements IStageInRule
{
	private IStagingCondition cond; /** The stage-in condition. */
	private IStageInRule trueRule; /** The stage-in rule to be executed when the condition holds. */
	private IStageInRule falseRule; /** The stage-in rule to be executed when the condition doesn't hold. */

	/** A constructor. */
	public ConditionalStageInRule(IStagingCondition cond, IStageInRule trueRule, IStageInRule falseRule)
	{
		this.cond = cond;
		this.trueRule = trueRule;
		this.falseRule = falseRule;
	}

	/** A constructor. */
	public ConditionalStageInRule(IStagingCondition cond, IStageInRule trueRule)
	{
		this( cond, trueRule, null );
	}

	/** Sets the stage-in condition. */
	public void setCondition(IStagingCondition cond)
	{
		this.cond = cond;
	}

	/** Returns the stage-in condition. */
	public IStagingCondition getCondition()
	{
		return this.cond;
	}

	/** Sets the stage-in rule to be executed when the condition holds. */
	public void setTrueRule(IStageInRule value)
	{
		this.trueRule = value;
	}

	/**
	 * Returns the stage-in rule to be executed when the condition
	 * holds.
	 */
	public IStageInRule getTrueRule()
	{
		return this.trueRule;
	}

	/**
	 * Sets the stage-in rule to be executed when the condition doesn't
	 * hold.
	 */
	public void setFalseRule(IStageInRule value)
	{
		this.falseRule = value;
	}

	/**
	 * Returns the stage-in rule to be executed when the condition
	 * doesn't hold.
	 */
	public IStageInRule getFalseRule()
	{
		return this.falseRule;
	}

	//@{ IStageInRule implementation

	public void setActions(Collection<IStageInAction> value)
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

	public void addAction(IStageInAction value)
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

	public List<IStageInAction> getActions()
	{
		Set<IStageInAction> actions = new HashSet<IStageInAction>();

		if ( this.getTrueRule() != null )
		{
			actions.addAll( this.getTrueRule().getActions() );
		}
		if ( this.getFalseRule() != null )
		{
			actions.addAll( this.getFalseRule().getActions() );
		}

		return new ArrayList<IStageInAction>( actions );
	}

	//@} IStageInRule implementation

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
