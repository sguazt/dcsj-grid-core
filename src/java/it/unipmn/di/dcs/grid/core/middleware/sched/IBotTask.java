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

import java.util.List;

/**
 * Interface for tasks of a Bag-of-Tasks job.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public interface IBotTask
{
	/** Sets the stage-in instructions. */
	void setStageIn(IStageIn value);

	/** Removes the stage-in instructions. */
	void removeStageIn();

	/** Returns the stage-in instructions. */
	IStageIn getStageIn();

	/** Sets (or resets) the list of remote commands. */
	void setCommands(List<IRemoteCommand> value);

	/** Removes (or resets) the list of remote commands. */
	void removeCommands();

	/** Adds a commands to be executed remotelly. */
	void addCommand(IRemoteCommand command);

	/** Returns the list of remote commands. */
	List<IRemoteCommand> getCommands();

	/** Sets the stage-out instructions. */
	void setStageOut(IStageOut value);

	/** Removes the stage-out instructions. */
	void removeStageOut();

	/** Returns the stage-out instructions. */
	IStageOut getStageOut();
}
