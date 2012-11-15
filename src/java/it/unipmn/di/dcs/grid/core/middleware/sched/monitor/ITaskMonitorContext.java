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

package it.unipmn.di.dcs.grid.core.middleware.sched.monitor;

import it.unipmn.di.dcs.grid.core.middleware.sched.ExecutionStatus;
import it.unipmn.di.dcs.grid.core.middleware.sched.ITaskHandle;

/**
 * Interfaces for context to be passed to dispatchers/interceptor objects
 * attached to a job monitor.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public interface ITaskMonitorContext
{
	/**
	 * Returns the handle of this task of the parent job.
	 */
	ITaskHandle taskHandle();

	/**
	 * Returns the context of the parent job.
	 */
	IJobMonitorContext jobContext();

	/**
	 * Returns the old execution status of this task of the parent job.
	 */
	ExecutionStatus oldTaskStatus();

	/**
	 * Returns the current execution status of this task of the parent job.
	 */
	ExecutionStatus curTaskStatus();
}
