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

/**
 * Class for stage-in instructions.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class StageIn extends AbstractStageIn
{
	/** A constructor. */
	public StageIn()
	{
		super();
	}

	/** A constructor. */
	public StageIn(IStageInRule rule)
	{
		super( rule );
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		for ( IStageInRule rule : this.getRules() )
		{
			if ( sb.length() > 0 )
			{
				sb.append("\n");
			}
			sb.append( rule.toString() );
		}

		return sb.toString();
	}
}
