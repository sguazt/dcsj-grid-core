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

package it.unipmn.di.dcs.grid.core.middleware;

import it.unipmn.di.dcs.common.text.ITextOp;
//import it.unipmn.di.dcs.grid.core.middleware.sched.IRequirementOp;
import it.unipmn.di.dcs.grid.core.middleware.sched.IScheduler;

import java.util.List;

/**
 * Interface for simplifying the access to the middleware services.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public interface IMiddlewareManager
{
	/**
	 * Returns the capabilities supported by this middleware.
	 */
	IMiddlewareCapabilities getMiddlewareCapabilities();

	/**
	 * Returns the local scheduler for this middleware.
	 */
	IScheduler getJobScheduler();

	/**
	 * Returns the properties associated to Grid Workers.
	 *
	 * A Grid Worker is a machine used for performing Grid computations.
	 */
	WorkerProperties getWorkerProperties();

	/**
	 * Returns a list of supported operators usable for specified job
	 * requirements.
	 */
	List<ITextOp> getJobRequirementOperators();
}
