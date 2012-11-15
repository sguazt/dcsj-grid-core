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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class for single jobs.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class SingleJob implements IJob, Externalizable
{
	private static final long serialVersionUID = 1L;

	private String name; /** Name of the job. */
	private IJobRequirements reqs; /** Requirements for the job. */
	private IStageIn stageIn; /** Stage-in part. */
	private List<IRemoteCommand> commands; /** Remote commands. */
	private IStageOut stageOut; /** Stage-out part. */

	/** A constructor. */
	public SingleJob()
	{
		// needed for Externalizable

		this.commands = new ArrayList<IRemoteCommand>();
	}

	/** A constructor. */
	public SingleJob(String name)
	{
		this();

		this.name = name;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		sb.append( "Name: " + this.name + "\n" );
		if ( this.reqs != null )
		{
			sb.append( "Requirements: " + this.reqs.toString() + "\n" );
		}
		if ( this.stageIn != null )
		{
			sb.append( "Stage-in: " + this.stageIn.toString() + "\n" );
		}
		if ( this.commands != null && this.commands.size() > 0 )
		{
			sb.append( "Remote commands: " + "\n" );
			for ( IRemoteCommand cmd : this.commands )
			{
				sb.append( "\t" + cmd.toString() + "\n" );
			}
		}
		if ( this.stageOut != null )
		{
			sb.append( "Stage-out: " + this.stageOut.toString() + "\n" );
		}

		return sb.toString();
	}

	//@{ IJob implementation

	public void setName(String value)
	{
		this.name = value;
	}

	public String getName()
	{
		return this.name;
	}

	public void setRequirements(IJobRequirements value)
	{
		this.reqs = value;
	}

	public void removeRequirements()
	{
		this.reqs = null;
	}

	public IJobRequirements getRequirements()
	{
		return this.reqs;
	}

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

	public void setCommands(Collection<IRemoteCommand> value)
	{
		this.commands.clear();
		this.commands.addAll(value);
	}

	public void removeCommands()
	{
		this.commands.clear();
	}

	public void addCommand(IRemoteCommand value)
	{
		this.commands.add( value );
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

	public JobType getType()
	{
		return JobType.SINGLE;
	}

	//@{ Externalizable implementation /////////////////////////////////////

	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
	{
/*
		long ver = in.readLong();
		if ( ver >= 1L )
		{
			String rawString = (String) in.readObject();

			if ( rawString != null )
			{
				IFormatImporter importer = null;
				Reader rd = null;

				importer = new JdfImporter();
				rd = new StringReader( rawString);

				IBotJob job = null;
				job = importer.importBotJob( rd );
				this.setName( job.getName() );
				this.setRequirements( job.getRequirements() );
				this.setStageIn( job.getStageIn() );
				this.setCommands( job.getCommands() );
				this.setStageOut( job.getStageOut() );
				this.setTasks( job.getTasks() );

				rd.close();
			}
		}
		else
		{
			this.name = (String) in.readObject();
			this.reqs = (IJobRequirements) in.readObject();
			this.stageIn = (IStageIn) in.readObject();
			int ncommands = in.readInt();
			if ( ncommands > 0 )
			{
				for (int i = 0; i < ncommands; i++)
				{
					this.addCommand( (IRemoteCommand) in.readObject() );
				}
			}
			this.stageOut = (IStageOut) in.readObject();
			int ntasks = in.readInt();
			if ( ntasks > 0 )
			{
				for (int i = 0; i < ntasks; i++)
				{
					this.addTask( (IBotTask) in.readObject() );
				}
			}
		}
*/
	}

	public void writeExternal(ObjectOutput out) throws IOException
	{
/*
		out.writeLong(serialVersionUID);
		if ( serialVersionUID >= 1L )
		{
			IFormatExporter exporter = null;
			StringWriter swr = null;

			swr = new StringWriter();

			exporter = new JdfExporter();
			exporter.exportBotJob(this, new PrintWriter( swr ) );

			out.writeObject( swr.toString() );

			swr.close();
		}
		else
		{
			out.writeObject(this.name);
			out.writeObject(this.reqs);
			out.writeObject(this.stageIn);
			if (this.commands != null)
			{
				out.writeInt(this.commands.size());
				for (IRemoteCommand command : this.commands)
				{
					out.writeObject(command);
				}
			}
			else
			{
				out.writeInt(0);
			}
			out.writeObject(this.stageOut);
			if (this.tasks != null)
			{
				out.writeInt(this.tasks.size());
				for (IBotTask task : this.tasks)
				{
					out.writeObject(task);
				}
			}
			else
			{
				out.writeInt(0);
			}
		}
*/
	}

	//@} Externalizable implementation /////////////////////////////////////

	//@} IJob implementation
}
