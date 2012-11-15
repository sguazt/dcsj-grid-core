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
 * Class representing a local grid scheduler.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class Scheduler implements IScheduler
{
	private ISchedulerDriver driver; /** Thr real scheduler implementation. */

	//public Scheduler()
	//{
	//	// Initialize with default driver
	//	this.driver = new SchedulerDriverFactory().getDriver();
	//}

	/** A constructor. */
	public Scheduler(ISchedulerDriverFactory factory)
	{
		this.driver = factory.getDriver();
	}

	/** A constructor. */
	public Scheduler(ISchedulerDriver driver)
	{
		this.driver = driver;
	}

	//@{ IScheduler implementation

	public void abortJob(IJobHandle job) throws SchedulerException
	{
		this.driver.abortJob(job);
	}

	public void cancelJob(IJobHandle job) throws SchedulerException
	{
		this.driver.cancelJob(job);
	}

	public String createJobUniqueFileName(String fileNamePrefix) throws SchedulerException
	{
		return this.driver.createJobUniqueFileName(fileNamePrefix);
	}

	public String createTaskUniqueFileName(String fileNamePrefix) throws SchedulerException
	{
		return this.driver.createTaskUniqueFileName(fileNamePrefix);
	}

	public boolean existsJob(String jobId) throws SchedulerException
	{
		return this.driver.existsJob(jobId);
	}

	public IJobHandle getJobHandle(String jobId) throws SchedulerException
	{
		return this.driver.getJobHandle(jobId);
	}

	public IJobMonitor getJobMonitor(IJobHandle job) throws SchedulerException
	{
		return this.driver.getJobMonitor(job);
	}

	public ExecutionStatus getJobStatus(IJobHandle job) throws SchedulerException
	{
		return this.driver.getJobStatus(job);
	}

	public boolean isRunning()
	{
		return this.driver.isRunning();
	}

	public void purgeJob(IJobHandle job) throws SchedulerException
	{
		this.driver.purgeJob(job);
	}

	public IJobHandle submitJob(IJob job) throws SchedulerException
	{
		return this.driver.submitJob(job);
	}

	public IJobHandle submitJob(IBotJob job) throws SchedulerException
	{
		return this.driver.submitJob(job);
	}

	//@} IScheduler implementation
}
