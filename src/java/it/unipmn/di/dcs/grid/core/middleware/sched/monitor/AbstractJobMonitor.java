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

import it.unipmn.di.dcs.grid.core.middleware.sched.IJobHandle;
import it.unipmn.di.dcs.grid.core.middleware.sched.monitor.IJobMonitor;
import it.unipmn.di.dcs.grid.core.middleware.sched.monitor.IJobMonitorContext;
import it.unipmn.di.dcs.grid.core.middleware.sched.monitor.IJobMonitorEventDispatcher;
import it.unipmn.di.dcs.grid.core.middleware.sched.monitor.IJobMonitorEventInterceptor;
import it.unipmn.di.dcs.grid.core.middleware.sched.monitor.ITaskMonitorContext;
import it.unipmn.di.dcs.grid.core.middleware.sched.monitor.JobMonitorContext;
import it.unipmn.di.dcs.grid.core.middleware.sched.monitor.TaskMonitorContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for monitoring the executing of a specific job.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public abstract class AbstractJobMonitor implements IJobMonitor
{
	/** Number of milliseconds the running monitor will sleep. */
	private static final long DEFAULT_SLEEP_TIME = 1 * 1000;

	/** The handle of the monitored job. */
	private IJobHandle jobHnd;

	/** The running monitor. */
	private Thread monitor;

	/** The running monitor. */
	private IJobMonitorEventDispatcher dispatcher;

	/**
	 * A constructor.
	 */
	protected AbstractJobMonitor(IJobHandle job)
	{
		this.jobHnd = job;
		this.dispatcher = new AbstractJobMonitor.Dispatcher();
	}

	/**
	 * A constructor.
	 */
	protected AbstractJobMonitor(IJobHandle job, IJobMonitorEventDispatcher dispatcher)
	{
		this.jobHnd = job;
		this.dispatcher = dispatcher;
	}

	//@{ IJobMonitor implementation

	public abstract void run();

	public IJobHandle getJobHandle()
	{
		return this.jobHnd;
	}

	public IJobMonitorEventDispatcher getDispatcher()
	{
		return this.dispatcher;
	}

	//@} IJobMonitor implementation

	//@{ class AbstractJobMonitor.Dispatcher

	/**
	 * The default dispatcher of a job monintor.
	 */
	private final class Dispatcher implements IJobMonitorEventDispatcher
	{
		/** List of interceptors for job monitor events. */
		private List<IJobMonitorEventInterceptor> interceptors = new ArrayList<IJobMonitorEventInterceptor>();

		//@{ IJobMonitorEventDispatcher implementation

		public void addInterceptor(IJobMonitorEventInterceptor interceptor)
		{
			this.interceptors.add( interceptor );
		}

		public List<IJobMonitorEventInterceptor> getInterceptors()
		{
			return this.interceptors;
		}

		public void removeInterceptor(IJobMonitorEventInterceptor interceptor)
		{
			this.interceptors.remove( interceptor );
		}

		public void dispatchStartMonitoring(IJobMonitorContext jobCtx)
		{
			for (IJobMonitorEventInterceptor interceptor : this.interceptors)
			{
				interceptor.interceptStartMonitoring(jobCtx);
			}
		}

		public void dispatchJobStatusChange(IJobMonitorContext jobCtx)
		{
			for (IJobMonitorEventInterceptor interceptor : this.interceptors)
			{
				interceptor.interceptJobStatusChange(jobCtx);
			}
		}

		public void dispatchTaskStatusChange(ITaskMonitorContext taskCtx)
		{
			for (IJobMonitorEventInterceptor interceptor : this.interceptors)
			{
				interceptor.interceptTaskStatusChange(taskCtx);
			}
		}

		public void dispatchStopMonitoring(IJobMonitorContext jobCtx)
		{
			for (IJobMonitorEventInterceptor interceptor : this.interceptors)
			{
				interceptor.interceptStopMonitoring(jobCtx);
			}
		}

		//@} IJobMonitorEventDispatcher implementation
	}

	//@} class AbstractJobMonitor.Dispatcher
}
