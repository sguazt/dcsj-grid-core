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
 * Class for stage-out rules.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class StageOutRule implements IStageOutRule
{
	/** List of stage-out actions. */
	private List<IStageOutAction> actions = new ArrayList<IStageOutAction>();

	/** A constructor. */
	public StageOutRule()
	{
		// empty
	}

	/** A constructor. */
	public StageOutRule(Collection<IStageOutAction> actions)
	{
		this.actions.addAll( actions );
	}

	/** A constructor. */
	public StageOutRule(IStageOutAction action)
	{
		this.actions.add( action );
	}
 
	/** Sets the list of stage-out actions. */
	public void setActions(Collection<IStageOutAction> value)
	{
		this.actions.clear();
		this.actions.addAll( value );
	}

	/** Adds a stage-out action. */
	public void addAction(IStageOutAction action)
	{
		this.actions.add( action );
	}

	/** Returns the list of stage-out actions. */
	public List<IStageOutAction> getActions()
	{
		return this.actions;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		for ( IStageOutAction action : this.getActions() )
		{
			if ( sb.length() > 0 )
			{
				sb.append("\n");
			}
			sb.append( action );
		}

		return sb.toString();
	}
}
