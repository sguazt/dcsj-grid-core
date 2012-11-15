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
import java.util.List;

/**
 * Class for stage-in rules.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class StageInRule implements IStageInRule
{
	/** List of stage-in actions. */
	private List<IStageInAction> actions = new ArrayList<IStageInAction>();

	/** A constructor. */
	public StageInRule()
	{
		// empty
	}

	/** A constructor. */
	public StageInRule(Collection<IStageInAction> actions)
	{
		this.actions.addAll( actions );
	}

	/** A constructor. */
	public StageInRule(IStageInAction action)
	{
		this.actions.add( action );
	}

	/** Sets the list of stage-in actions. */
	public void setActions(Collection<IStageInAction> value)
	{
		this.actions.clear();
		this.actions.addAll( value );
	}

	/** Adds a stage-in action. */
	public void addAction(IStageInAction action)
	{
		this.actions.add( action );
	}

	/** Returns the list of stage-in actions. */
	public List<IStageInAction> getActions()
	{
		return this.actions;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		for ( IStageInAction action : this.getActions() )
		{
			if ( sb.length() > 0 )
			{
				sb.append("\n");
			}
			sb.append( action.toString() );
		}

		return sb.toString();
	}
}
