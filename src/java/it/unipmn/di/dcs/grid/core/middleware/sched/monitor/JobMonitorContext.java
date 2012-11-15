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
import it.unipmn.di.dcs.grid.core.middleware.sched.IJobHandle;

/**
 * A concrete job monitor context.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */ 
public class JobMonitorContext implements IJobMonitorContext
{
	/** The handle of the monitored job. */
	private IJobHandle jobHnd;

	/** The old execution status of the monitored job. */
	private ExecutionStatus oldJobStatus;

	/** The current execution status of the monitored job. */
	private ExecutionStatus curJobStatus;

	/**
	 * A constructor.
	 */
	public JobMonitorContext(IJobHandle jobHnd)
	{
		this.jobHnd = jobHnd;
		this.oldJobStatus = ExecutionStatus.UNSTARTED;
		this.curJobStatus = ExecutionStatus.UNSTARTED;
	}

	/**
	 * Sets the current execution status of the monitored job.
	 */
	public void setCurJobStatus(ExecutionStatus value)
	{
		this.oldJobStatus = this.curJobStatus;
		this.curJobStatus = value;
	}

	//@{ IJobMonitorContext implementation

	public IJobHandle jobHandle()
	{
		return this.jobHnd;
	}

	public ExecutionStatus oldJobStatus()
	{
		return this.oldJobStatus;
	}

	public ExecutionStatus curJobStatus()
	{
		return this.curJobStatus;
	}

	//@} IJobMonitorContext implementation
}
