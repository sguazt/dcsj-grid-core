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
 * Interface for stage-out actions.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public interface IStageOutAction
{
	/** Sets the transfer mode. */
	void setMode(StageOutMode value);

	/** Returns the transfer mode. */
	StageOutMode getMode();

	/** Sets the resource remote name. */
	void setRemoteName(String value);

	/** Returns the resource remote name. */
	String getRemoteName();

	/** Sets the resource local name. */
	void setLocalName(String value);

	/** Returns the resource local name. */
	String getLocalName();

//NOT USED FOR THE MOMENT:
//	/** Sets the storage type. */
//	void setType(StageOutType value);
//
//	/** Returns the storage type. */
//	StageOutType getType();
}
