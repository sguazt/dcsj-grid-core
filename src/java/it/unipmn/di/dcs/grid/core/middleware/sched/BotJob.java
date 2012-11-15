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

import it.unipmn.di.dcs.common.util.collection.Collections;

import it.unipmn.di.dcs.grid.core.format.IFormatExporter;
import it.unipmn.di.dcs.grid.core.format.IFormatImporter;
import it.unipmn.di.dcs.grid.core.format.jdf.JdfExporter;
import it.unipmn.di.dcs.grid.core.format.jdf.JdfImporter;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class for Bag-of-Tasks jobs.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class BotJob implements IBotJob, Externalizable
{
	private static final long serialVersionUID = 100000000L;

	private String name; /** Name of the job. */
	private IJobRequirements reqs; /** Requirements for the job. */
	private IStageIn stageIn; /** Default stage-in part. */
	private List<IRemoteCommand> commands; /** Default remote commands. */
	private IStageOut stageOut; /** Default stage-out part. */
	private List<IBotTask> tasks; /** Task list. */

	/** A constructor. */
	public BotJob()
	{
		// needed for Externalizable

		this.tasks = new ArrayList<IBotTask>();
		this.commands = new ArrayList<IRemoteCommand>();
	}

	/** A constructor. */
	public BotJob(String name)
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
			sb.append( "Default Stage-in: " + this.stageIn.toString() + "\n" );
		}
		if ( !Collections.IsNullOrEmpty( this.commands ) )
		{
			sb.append( "Default Remote commands:\n" );
			for ( IRemoteCommand cmd : this.commands )
			{
				sb.append( "\t" + cmd.toString() + "\n" );
			}
		}
		if ( this.stageOut != null )
		{
			sb.append( "Default Stage-out: " + this.stageOut.toString() + "\n" );
		}
		if ( !Collections.IsNullOrEmpty( this.tasks ) )
		{
			sb.append( "Tasks: " + "\n" );
			for ( IBotTask task : this.tasks )
			{
				sb.append( "\t" + task.toString() + "\n" );
/*
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
*/
			}
		}

		return sb.toString();
	}

	//@{ IBotJob implementation

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
		if ( value != null )
		{
			this.commands.addAll(value);
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

	public void setTasks(Collection<IBotTask> value)
	{
		this.tasks.clear();
		if ( value != null )
		{
			this.tasks.addAll( value );
		}
	}

	public void removeTasks()
	{
		this.tasks.clear();
	}

	public void addTask(IBotTask task)
	{
		if ( task != null )
		{
			this.tasks.add( task );
		}
	}

	public List<IBotTask> getTasks()
	{
		return this.tasks;
	}

	public JobType getType()
	{
		return JobType.BOT;
	}

	//@{ Externalizable implementation /////////////////////////////////////

	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
	{
		long ver = in.readLong();
		int type = in.readInt();

		// sanity check on job type
		if (this.getType() != JobUtil.IntToJobType(type) )
		{
			throw new IOException("Bad job type for importing.");
		}

		if ( ver >= 100000000L )
		{
			String rawString = (String) in.readObject();

			if ( rawString != null )
			{
				IFormatImporter importer = null;
				Reader rd = null;

				importer = new JdfImporter();
				rd = new StringReader(rawString);

				IBotJob job = null;
				try
				{
					job = importer.importBotJob( rd );
				}
				catch (Exception e)
				{
					throw new IOException("Bad object format for importing.",e);
				}
				finally
				{
					try { rd.close(); } catch (Exception ex) { /* ignore */ }
					rd = null;
				}

System.err.println("Imported Job: " + job );//XXX
				// Clone the imported job
				this.setName( job.getName() );
				this.setRequirements( job.getRequirements() );
				this.setStageIn( job.getStageIn() );
				this.setCommands( job.getCommands() );
				this.setStageOut( job.getStageOut() );
				this.setTasks( job.getTasks() );
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
	}

	public void writeExternal(ObjectOutput out) throws IOException
	{
		out.writeLong( serialVersionUID );
		out.writeInt( JobUtil.JobTypeToInt(this.getType()) );

		if ( serialVersionUID >= 100000000L )
		{
			/*
			 * Version >= 1 format:
			 *   [version_number]
			 *   [job_type]
			 *   [jdf_string]
			 */

			IFormatExporter exporter = null;
			StringWriter swr = null;

			swr = new StringWriter();

			exporter = new JdfExporter();
			try
			{
				exporter.export( this, new PrintWriter( swr ) );
			}
			catch (Exception e)
			{
				throw new IOException("Bad object format to be exported.",e);
			}

			out.writeObject( swr.toString() );

			swr.close();
		}
		else
		{
			/*
			 * Version < 1 format:
			 *   [version_number]
			 *   [job_type]
			 *   [job_name]
			 *   [job_reqs]
			 *   [job_stageIn]
			 *   [job_commandsNumber]
			 *   [job_command1]
			 *   ...
			 *   [job_commandN]
			 *   [job_stageOut]
			 *   [job_tasksNumber]
			 *   [job_task1]
			 *   ...
			 *   [job_taskM]
			 *
			 * If [job_commandsNumber] == 0 there will be no [job_command] entry.
			 * If [job_tasksNumber] == 0 there will be no [job_task] entry.
			 */

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
	}

	//@} Externalizable implementation /////////////////////////////////////

	//@} IBotJob implementation
}
