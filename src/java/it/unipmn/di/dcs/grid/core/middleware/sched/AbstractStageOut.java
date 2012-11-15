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
import java.util.List;

/**
 * Base class for stage-out instructions.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public abstract class AbstractStageOut implements IStageOut
{
	private List<IStageOutRule> rules; /** The embedded stage-out rules. */

	/** A constructor. */
	protected AbstractStageOut()
	{
		this.rules = new ArrayList<IStageOutRule>();
	}

	/** A constructor. */
	protected AbstractStageOut(IStageOutRule rule)
	{
		this();
		this.addRule( rule );
	}

	//@{ IStageOut implementation

	public void addRule(IStageOutRule rule)
	{
		this.rules.add( rule );
	}

	public List<IStageOutRule> getRules()
	{
		return this.rules;
	}

	//@} IStageOut implementation
}
