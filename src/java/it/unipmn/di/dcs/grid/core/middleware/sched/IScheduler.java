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

import it.unipmn.di.dcs.grid.core.middleware.sched.monitor.IJobMonitor;

/**
 * Interface for local grid schedulers.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public interface IScheduler
{
	/** Abort the schedulation/execution of the given job. */
	void abortJob(IJobHandle job) throws SchedulerException;

	/** Cancel the schedulation/execution of the given job. */
	void cancelJob(IJobHandle job) throws SchedulerException;

	/**
	 * Returns a unique file name for a job.
	 *
	 * @throws SchedulerException if the operation is not supported or if
	 * something goes wrong.
	 */
	String createJobUniqueFileName(String fileNamePrefix) throws SchedulerException;

	/** Returns a unique file name for a task.
	 *
	 * @throws SchedulerException if the operation is not supported or if
	 * something goes wrong.
	 */
	String createTaskUniqueFileName(String fileNamePrefix) throws SchedulerException;

	/** Returns {@code true} if the given job exists; {@code false} otherwise. */
	boolean existsJob(String jobId) throws SchedulerException;

	/** Returns the job handle associated to the given job identifier. */
	IJobHandle getJobHandle(String jid) throws SchedulerException;

	/** Returns an object for monitoring the execution of the given job. */
	IJobMonitor getJobMonitor(IJobHandle job) throws SchedulerException;

	/** Returns the status of execution of the given job. */
	ExecutionStatus getJobStatus(IJobHandle job) throws SchedulerException;

	/** Tells if this scheduler is currently running. */
	boolean isRunning();

	/**
	 * Purge the given job from the scheduler queue.
	 * The job must have a final execution status (i.e. ABORTED, CANCELLED,
	 * FAILED, FINISHED), otherwise an exception will be thrown.
	 */
	void purgeJob(IJobHandle job) throws SchedulerException;

	/** Submits the given job the scheduler queue. */
	IJobHandle submitJob(IJob job) throws SchedulerException;

	/** Submits the given BoT job the scheduler queue. */
	IJobHandle submitJob(IBotJob job) throws SchedulerException;
}
