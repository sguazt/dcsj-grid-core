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
 * Base class for stage-in actions (inside a stage-in rule).
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public abstract class AbstractStageInAction implements IStageInAction
{
	/** The stage-in mode. */
	private StageInMode mode;

	/** The local resource name. */
	private String local;

	/** The remote resource name. */
	private String remote;

	/** The stage-in type. */
	private StageInType type;

	/** A constructor. */
	protected AbstractStageInAction()
	{
		this.mode = StageInMode.UNKNOWN;
		this.type = StageInType.UNKNOWN;
	}

	/** A constructor. */
	protected AbstractStageInAction(StageInMode mode, String local, String remote, StageInType type)
	{
		this.mode = mode;
		this.local = local;
		this.remote = remote;
		this.type = type;
	}

	//@{ IStageInAction implementation

	public void setMode(StageInMode value)
	{
		this.mode = value;
	}

	public StageInMode getMode()
	{
		return this.mode;
	}

	public void setLocalName(String value)
	{
		this.local = value;
	}

	public String getLocalName()
	{
		return this.local;
	}

	public void setRemoteName(String value)
	{
		this.remote = value;
	}

	public String getRemoteName()
	{
		return this.remote;
	}

	public void setType(StageInType value)
	{
		this.type = value;
	}

	public StageInType getType()
	{
		return this.type;
	}

	//@} IStageInAction implementation
}
