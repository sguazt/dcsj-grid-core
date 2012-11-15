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
 * A concrete job task monitor context.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */ 
public class TaskMonitorContext implements ITaskMonitorContext
{
	/** The handle of this task of the parent job. */
	private ITaskHandle taskHnd;

	/** The context of a the parent job. */
	private IJobMonitorContext jobCtx;

	/** The old execution status of this task of the parent job. */
	private ExecutionStatus oldTaskStatus;

	/** The current execution status of this task of the parent job. */
	private ExecutionStatus curTaskStatus;

	/**
	 * A constructor.
	 */
	public TaskMonitorContext(ITaskHandle taskHnd, JobMonitorContext jobCtx)
	{
		this.taskHnd = taskHnd;
		this.jobCtx = jobCtx;
		this.oldTaskStatus = ExecutionStatus.UNSTARTED;
		this.curTaskStatus = ExecutionStatus.UNSTARTED;
	}

	/**
	 * Sets the current execution status of this task of the parent job.
	 */
	public void setCurTaskStatus(ExecutionStatus value)
	{
		this.oldTaskStatus = this.curTaskStatus;
		this.curTaskStatus = value;
	}

	//@{ ITaskMonitorContext implementation

	public ITaskHandle taskHandle()
	{
		return this.taskHnd;
	}

	public IJobMonitorContext jobContext()
	{
		return this.jobCtx;
	}

	public ExecutionStatus oldTaskStatus()
	{
		return this.oldTaskStatus;
	}

	public ExecutionStatus curTaskStatus()
	{
		return this.curTaskStatus;
	}

	//@} ITaskMonitorContext implementation
}
