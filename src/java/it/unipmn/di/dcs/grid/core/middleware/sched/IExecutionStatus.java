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
 * Interface for jobs/tasks execution status.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
@Deprecated
public interface IExecutionStatus
{
	/**
	 * Returns true if the state is ABORTED
	 */
	boolean isAborted();

	/**
	 * Returns true if the state is CANCELLED
	 */
	boolean isCancelled();

	/**
	 * Returns true if the state is FAILED
	 */
	boolean isFailed();

	/**
	 * Returns true if the state is FINISHED
	 */
	boolean isFinished();

	/**
	 * Returns true if the state is RUNNING
	 */
	boolean isRunning();

	/**
	 * Returns true if the state is UNSTARTED (or READY)
	 */
	boolean isUnstarted();
}
