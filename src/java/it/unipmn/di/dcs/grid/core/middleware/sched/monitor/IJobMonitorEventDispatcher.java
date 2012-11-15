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

import java.util.List;

/**
 * Interface for dispatchers of JobMonitor events.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public interface IJobMonitorEventDispatcher
{
        /**
	 * Register the given interceptor for events dispatched by this job
	 * monitor.
	 */
	void addInterceptor(IJobMonitorEventInterceptor i);

        /**
	 * Returns the interceptors for events dispatched by this job monitor.
	 */
	List<IJobMonitorEventInterceptor> getInterceptors();

        /**
	 * Deregister the given interceptor for events dispatched by this
	 * monitor..
	 */
	void removeInterceptor(IJobMonitorEventInterceptor i);

	/**
	 * Dispatches the StartMonitoring event to interceptors.
	 */
	void dispatchStartMonitoring(IJobMonitorContext jobCtx);

	/**
	 * Dispatches the JobStatusChange event to interceptors.
	 */
	void dispatchJobStatusChange(IJobMonitorContext jobCtx);

	/**
	 * Dispatches the TaskStatusChange event to interceptors.
	 */
	void dispatchTaskStatusChange(ITaskMonitorContext jobCtx);

	/**
	 * Dispatches the StopMonitoring event to interceptors.
	 */
	void dispatchStopMonitoring(IJobMonitorContext jobCtx);
}
