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
import java.util.Collection;
import java.util.List;

/**
 * Interface for a generic job.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public interface IJob
{
	/** Sets the job name. */
	void setName(String value);

	/** Returns the job name. */
	String getName();

	/** Sets the job requirements. */
	void setRequirements(IJobRequirements req);

	/** Removes the job requirements. */
	void removeRequirements();

	/** Returns the job requirements. */
	IJobRequirements getRequirements();

	/** Sets the stage-in instructions. */
	void setStageIn(IStageIn value);

	/** Removes the stage-in instructions. */
	void removeStageIn();

	/** Returns the stage-in instructions. */
	IStageIn getStageIn();

	/** Sets (or resets) the list of remote commands. */
	void setCommands(Collection<IRemoteCommand> value);

	/** Add a new remote command. */
	void addCommand(IRemoteCommand com);

	/** Removes the list of remote commands. */
	void removeCommands();

	/** Returns the list of remote commands. */
	List<IRemoteCommand> getCommands();

	/** Sets the stage-out instructions. */
	void setStageOut(IStageOut value);

	/** Remove the stage-out instructions. */
	void removeStageOut();

	/** Returns the stage-out instructions. */
	IStageOut getStageOut();

	/** Returns the type of the job. */
	JobType getType();

	/**
	 * The object implements the writeExternal method to save its contents
	 * by calling the methods of DataOutput for its primitive values or
	 * calling the writeObject method of ObjectOutput for objects, strings,
	 * and arrays.
	 *
	 * @see java.io.Externalizable
	 */
	void writeExternal(ObjectOutput out) throws IOException;

	/**
	 * The object implements the readExternal method to restore its contents
	 * by calling the methods of DataInput for primitive types and
	 * readObject for objects, strings and arrays.
	 *
	 * @see java.io.Externalizable
	 */
	void readExternal(ObjectInput in) throws IOException, ClassNotFoundException;
}
