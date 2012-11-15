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
 * Base class for stage-out instructions.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public abstract class AbstractStageOutAction implements IStageOutAction
{
	private StageOutMode mode; /** The stage-out transfer mode. */
	private String remote; /** The remote resource. */
	private String local; /** The local resource. */

	/** A constructor. */
	protected AbstractStageOutAction()
	{
		this.mode = StageOutMode.UNKNOWN;
	}

	/** A constructor. */
	protected AbstractStageOutAction(StageOutMode mode, String remote, String local)
	{
		this.mode = mode;
		this.remote = remote;
		this.local = local;
	}

	//@{ IStageOutAction implementation

	public void setMode(StageOutMode value)
	{
		this.mode = value;
	}

	public StageOutMode getMode()
	{
		return this.mode;
	}

	public void setRemoteName(String value)
	{
		this.remote = value;
	}

	public String getRemoteName()
	{
		return this.remote;
	}

	public void setLocalName(String value)
	{
		this.local = value;
	}

	public String getLocalName()
	{
		return this.local;
	}

	//@} IStageOutAction implementation
}
