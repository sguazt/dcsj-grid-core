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

import it.unipmn.di.dcs.grid.core.middleware.sched.JobType;
import it.unipmn.di.dcs.grid.core.middleware.sched.StageInMode;
import it.unipmn.di.dcs.grid.core.middleware.sched.StageOutMode;

/**
 * Interface for getting information about capabilities supported by a
 * middleware.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public interface IMiddlewareCapabilities
{
	/**
	 * Returns {@code true} if the middleware support the given job
	 * {@code type}.
	 */
	boolean supportJobType(JobType type);

	/**
	 * Returns {@code true} if the middleware support the given stage-in
	 * transfer {@code mode}.
	 */
	boolean supportStageInMode(StageInMode mode);

	/**
	 * Returns {@code true} if the middleware support the given stage-out
	 * transfer {@code mode}.
	 */
	boolean supportStageOutMode(StageOutMode mode);

	/**
	 * Returns {@code true} if the middleware support conditional stage-in
	 * transfers.
	 */
	boolean supportConditionalStageIn();

	/**
	 * Returns {@code true} if the middleware support conditional remote
	 * execution.
	 */
	boolean supportConditionalRemoteCommand();

	/**
	 * Returns {@code true} if the middleware support conditional stage-out
	 * transfers.
	 */
	boolean supportConditionalStageOut();
}
