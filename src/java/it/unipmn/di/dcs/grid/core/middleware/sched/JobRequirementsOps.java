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

import it.unipmn.di.dcs.common.text.BinaryTextOp;
import it.unipmn.di.dcs.common.text.ITextOp;
import it.unipmn.di.dcs.common.text.UnaryTextOp;

/**
 * Utility class for retrieving operators usable inside a staging condition.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class JobRequirementsOps
{
	/** Logical NOT. */
	public static final UnaryTextOp LOGICAL_NOT = new UnaryTextOp( "not", "logical not" );

	/** Logical AND. */
	public static final BinaryTextOp LOGICAL_AND = new BinaryTextOp( "and", "logical and" );

	/** Logical OR. */
	public static final BinaryTextOp LOGICAL_OR = new BinaryTextOp( "or", "logical or" );

	/** 'Less than' relational operator. */
	public static final BinaryTextOp LT = new BinaryTextOp( "<", "less than" );

	/** 'Greater than' relational operator. */
	public static final BinaryTextOp GT = new BinaryTextOp( ">", "greater than" );

	/** 'Less than or equal to' relational operator. */
	public static final BinaryTextOp LE = new BinaryTextOp( "<=", "less than or equal to" );

	/** 'Greater than or equal to' relational operator. */
	public static final BinaryTextOp GE = new BinaryTextOp( ">=", "greater than or equal to" );

	/** Equality operator. */
	public static final BinaryTextOp EQ = new BinaryTextOp( "==", "equal to" );

	/** Non-equality operator. */
	public static final BinaryTextOp NE = new BinaryTextOp( "!=", "not equal to" );
}
