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
 * Class for tasks of a Bag-of-Tasks job.
 *
 * Note: if more than one remote command is present, they will be executed
 * sequentially, according to the order of appearence inside the list.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class BotTask implements IBotTask
{
	/** The stage-in instructions. */
	private IStageIn stageIn;

	/** The remote commands. */
	private List<IRemoteCommand> commands = new ArrayList<IRemoteCommand>();

	/** The stage-out instructions. */
	private IStageOut stageOut;

	/** A constructor. */
	public BotTask()
	{
		// empty
	}

	/** A constructor. */
	public BotTask(IStageIn stageIn, List<IRemoteCommand> commands, IStageOut stageOut)
	{
		this.stageIn = stageIn;
		if ( commands != null )
		{
			this.commands = new ArrayList<IRemoteCommand>( commands );
		}
		this.stageOut = stageOut;
	}

	/** A constructor. */
	public BotTask(IBotTask t)
	{
		if ( t != null )
		{
			this.stageIn = t.getStageIn();
			if ( t.getCommands() != null )
			{
				this.commands = new ArrayList<IRemoteCommand>( t.getCommands() );
			}
			this.stageOut = t.getStageOut();
		}
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		sb.append( "Task:\n" );

		if ( this.stageIn != null )
		{
			sb.append( "\tStage-in: " + this.stageIn.toString() + "\n" );
		}
		if ( this.commands != null && this.commands.size() > 0 )
		{
			sb.append( "\tRemote commands:\n" );
			for ( IRemoteCommand com : this.commands )
			{
				sb.append( "\t\t" + com.toString() + "\n" );
			}
		}
		if ( this.stageOut != null )
		{
			sb.append( "\tStage-out: " + this.stageOut.toString() + "\n" );
		}

		return sb.toString();
	}

	//@{ IBotTask implementation

	public void setStageIn(IStageIn value)
	{
		this.stageIn = value;
	}

	public void removeStageIn()
	{
		this.stageIn = null;
	}

	public IStageIn getStageIn()
	{
		return this.stageIn;
	}

	public void setCommands(List<IRemoteCommand> value)
	{
		this.commands.clear();
		if ( value != null )
		{
			this.commands.addAll( value );
		}
	}

	public void removeCommands()
	{
		this.commands.clear();
	}

	public void addCommand(IRemoteCommand com)
	{
		if ( com != null )
		{
			this.commands.add( com );
		}
	}

	public List<IRemoteCommand> getCommands()
	{
		return this.commands;
	}

	public void setStageOut(IStageOut value)
	{
		this.stageOut = value;
	}

	public void removeStageOut()
	{
		this.stageOut = null;
	}

	public IStageOut getStageOut()
	{
		return this.stageOut;
	}

	//@} IBotTask implementation
}
