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

import it.unipmn.di.dcs.common.text.BinaryTextExpr;
import it.unipmn.di.dcs.common.text.BinaryTextOp;
import it.unipmn.di.dcs.common.text.ITextExpr;
import it.unipmn.di.dcs.common.text.ITextOp;
import it.unipmn.di.dcs.common.text.PrefixUnaryTextExpr;
import it.unipmn.di.dcs.common.text.TextOpType;
import it.unipmn.di.dcs.common.text.UnaryTextOp;

/**
 * Utility class for job requirements expressions.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class JobRequirementsUtil
{
	/** The singleton instance. */
	private static JobRequirementsUtil instance;

	/** A constructor. */
	private JobRequirementsUtil()
	{
		// empty
	}

	/** Returns the singleton instance. */
	public static synchronized JobRequirementsUtil GetInstance()
	{
		if ( JobRequirementsUtil.instance == null )
		{
			JobRequirementsUtil.instance = new JobRequirementsUtil();
		}

		return JobRequirementsUtil.instance;
	}

	/** Returns an instance of a unary textual expression. */
	public ITextExpr createUnaryTextExpr( ITextExpr expr, ITextOp op ) throws IllegalArgumentException
	{
		if ( this.sameTextOp( op, JobRequirementsOps.LOGICAL_NOT ) )
		{
			return new PrefixUnaryTextExpr( JobRequirementsOps.LOGICAL_NOT, expr );
		}

		throw new IllegalArgumentException( "Operator '" + op + "' is not a recognized unary operator." );
	}

	/** Returns an instance of a unary textual expression. */
	public ITextExpr createUnaryTextExpr( ITextExpr expr, UnaryTextOp op ) throws IllegalArgumentException
	{
		if ( this.sameTextOp( op, JobRequirementsOps.LOGICAL_NOT ) )
		{
			return new PrefixUnaryTextExpr( op, expr );
		}

		throw new IllegalArgumentException( "Operator '" + op + "' is not a recognized unary operator." );
	}

	/** Returns an instance of a binary textual expression. */
	public ITextExpr createBinaryTextExpr( ITextExpr left, ITextOp op, ITextExpr right ) throws IllegalArgumentException
	{
		if (
			!this.sameTextOp( op, JobRequirementsOps.LOGICAL_NOT )
			&& op.getType() == TextOpType.BINARY
		)
		{
			return new BinaryTextExpr( left, (BinaryTextOp) op, right );
		}

		throw new IllegalArgumentException( "Operator '" + op + "' is not a recognized binary operator." );
	}

	/** Returns an instance of a binary textual expression. */
	public ITextExpr createBinaryTextExpr( ITextExpr left, BinaryTextOp op, ITextExpr right ) throws IllegalArgumentException
	{
		if ( !this.sameTextOp( op, JobRequirementsOps.LOGICAL_NOT ) )
		{
			return new BinaryTextExpr( left, op, right );
		}

		throw new IllegalArgumentException( "Operator '" + op + "' is not a recognized binary operator." );
	}

	public boolean sameTextOp(ITextOp op1, ITextOp op2)
	{
		return op1 == op2;
	}
}
