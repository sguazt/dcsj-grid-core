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

package it.unipmn.di.dcs.grid.core.format.jdf;

import it.unipmn.di.dcs.common.annotation.TODO;
import it.unipmn.di.dcs.common.text.BinaryTextExpr;
import it.unipmn.di.dcs.common.text.ITextExpr;
import it.unipmn.di.dcs.common.text.ITextOp;
import it.unipmn.di.dcs.common.text.ParenthesizedTextExpr;
import it.unipmn.di.dcs.common.text.PostfixUnaryTextExpr;
import it.unipmn.di.dcs.common.text.PrefixUnaryTextExpr;
import it.unipmn.di.dcs.common.text.TerminalTextExpr;
import it.unipmn.di.dcs.common.text.TextExprs;
import it.unipmn.di.dcs.common.util.Strings;
import it.unipmn.di.dcs.common.util.collection.Collections;

import it.unipmn.di.dcs.grid.core.format.FormatExporterException;
import it.unipmn.di.dcs.grid.core.format.IFormatExporter;
import it.unipmn.di.dcs.grid.core.middleware.sched.ConditionalStageInRule;
import it.unipmn.di.dcs.grid.core.middleware.sched.ConditionalStageOutRule;
import it.unipmn.di.dcs.grid.core.middleware.sched.IBotJob;
import it.unipmn.di.dcs.grid.core.middleware.sched.IBotTask;
import it.unipmn.di.dcs.grid.core.middleware.sched.IJob;
import it.unipmn.di.dcs.grid.core.middleware.sched.IRemoteCommand;
import it.unipmn.di.dcs.grid.core.middleware.sched.IStageIn;
import it.unipmn.di.dcs.grid.core.middleware.sched.IStageInAction;
import it.unipmn.di.dcs.grid.core.middleware.sched.IStageInRule;
import it.unipmn.di.dcs.grid.core.middleware.sched.IStageOut;
import it.unipmn.di.dcs.grid.core.middleware.sched.IStageOutAction;
import it.unipmn.di.dcs.grid.core.middleware.sched.IStageOutRule;
import it.unipmn.di.dcs.grid.core.middleware.sched.StageInRule;
import it.unipmn.di.dcs.grid.core.middleware.sched.StageOutRule;

import java.io.PrintWriter;
import java.util.List;

/**
 * Class for JDF format exporters
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class JdfExporter implements IFormatExporter
{
	protected static final String CommandSeparator = " && ";
	protected static final String JobIdVar = "$JOB";
	protected static final String JobTaskIdVar = "$TASK";
	protected static final String RemotePersistentPathVar = "$STORAGE";
	protected static final String RemoteProcessorVar = "$PROC";
	protected static final String RemoteVolatilePathVar = "$PLAYPEN";

	private boolean strictSyntax = false;

	public static String CommandSeparator()
	{
		return JdfExporter.CommandSeparator;
	}

	public static String JobIdVar()
	{
		return JdfExporter.JobIdVar;
	}

	public static String JobTaskIdVar()
	{
		return JdfExporter.JobTaskIdVar;
	}

	public static String RemotePersistentPathVar()
	{
		return JdfExporter.RemotePersistentPathVar;
	}

	public static String RemoteProcessorVar()
	{
		return JdfExporter.RemoteProcessorVar;
	}

	public static String RemoteVolatilePathVar()
	{
		return JdfExporter.RemoteVolatilePathVar;
	}

	public void setStrictSyntax(boolean value)
	{
		this.strictSyntax = value;
	}

	public boolean isStrictSyntax()
	{
		return this.strictSyntax;
	}

	//@{ IFormatExporter implementation

	public void export(IJob job, PrintWriter wr) throws FormatExporterException
	{
		wr.println( "job:" );

		// Exports the job name
		if ( !Strings.IsNullOrEmpty( job.getName() ) )
		{
			wr.println( "\tlabel: " + job.getName() );
		}
		// Exports the job requirements
		if (
			job.getRequirements() != null
			&& job.getRequirements().getExpr() != TextExprs.EMPTY_EXPR
		)
		{
			wr.println( "\trequirements:" );
			this.exportTextExpr( job.getRequirements().getExpr(), wr );
			wr.println();
			
		}
		// Exports the job stage-in statements
		if ( job.getStageIn() != null )
		{
			this.exportStageIn( job.getStageIn(), wr, true );
		}
		// Exports the job remote commands
		if ( !Collections.IsNullOrEmpty( job.getCommands() ) )
		{
			this.exportRemoteCommands( job.getCommands(), wr, true );
		}
		// Exports the job stage-out statements
		if ( job.getStageOut() != null )
		{
			this.exportStageOut( job.getStageOut(), wr, true );
		}
	}

	@TODO("Handle conditional remote commands")
	public void export(IBotJob job, PrintWriter wr) throws FormatExporterException
	{
		wr.println( "job:" );

		// Exports the job name
		if ( !Strings.IsNullOrEmpty( job.getName() ) )
		{
			wr.println( "\tlabel: " + job.getName() );
		}
		// Exports the job requirements
		if (
			job.getRequirements() != null
			&& job.getRequirements().getExpr() != TextExprs.EMPTY_EXPR
		)
		{
			wr.print( "\trequirements: " );
			this.exportTextExpr( job.getRequirements().getExpr(), wr );
			wr.println();
			
		}
		// Exports tasks
		if ( !Collections.IsNullOrEmpty( job.getTasks() ) )
		{
			// NOTE: JDF format does not support both global and
			// per task information.
			// So, if a job has both global remote-commands and
			// per-task remote-commands, we must copy the global
			// remote-commands into remote-commands for each task
			// and then remove the global remote-commands.
			// Same thing for stage-in and stage-out.

			boolean copyJobStageIn = false;
			boolean copyJobCommand = false;
			boolean copyJobStageOut = false;
			boolean jobStageInExist = false;
			boolean jobStageOutExist = false;
			boolean jobCommandExist = false;

			if ( job.getStageIn() != null && !Collections.IsNullOrEmpty( job.getStageIn().getRules() ) )
			{
				jobStageInExist = true;
			}
			if ( !Collections.IsNullOrEmpty( job.getCommands() ) )
			{
				jobCommandExist = true;
			}
			if ( job.getStageOut() != null && !Collections.IsNullOrEmpty( job.getStageOut().getRules() ) )
			{
				jobStageOutExist = true;
			}

			for ( IBotTask task : job.getTasks() )
			{
				// Check stage-in
				if (
					jobStageInExist
					&& !copyJobStageIn
					&& (
						task.getStageIn() != null
						&& !Collections.IsNullOrEmpty( task.getStageIn().getRules() )
					)
				)
				{
					copyJobStageIn = true;
				}
				// Check remote commands
				if (
					jobCommandExist
					&& !copyJobCommand
					&& !Collections.IsNullOrEmpty( task.getCommands() )
				)
				{
					copyJobCommand = true;
				}
				// Check stage-out
				if (
					jobStageOutExist
					&& !copyJobStageOut
					&& (
						task.getStageOut() != null
						&& !Collections.IsNullOrEmpty( task.getStageOut().getRules() )
					)
				)
				{
					copyJobStageOut = true;
				}
			}

			// Exports the job stage-in statements
			if ( jobStageInExist && !copyJobStageIn )
			{
				this.exportStageIn( job.getStageIn(), wr, true );
			}
			// Exports the job remote commands
			if ( jobCommandExist && !copyJobCommand )
			{
				this.exportRemoteCommands( job.getCommands(), wr, true );
			}
			// Exports the job stage-out statements
			if ( jobStageOutExist && !copyJobStageOut )
			{
				this.exportStageOut( job.getStageOut(), wr, true );
			}

			for ( IBotTask task : job.getTasks() )
			{
				wr.println();
				wr.println( "task:" );

				if (
//					( job.getStageIn() != null && !Collections.IsNullOrEmpty( job.getStageIn().getRules() ) )
//					|| ( task.getStageIn() != null && !Collections.IsNullOrEmpty( task.getStageIn().getRules() ) )
					copyJobStageIn
					|| ( task.getStageIn() != null && !Collections.IsNullOrEmpty( task.getStageIn().getRules() ) )
				) {
					wr.println( "\tinit: " );

					// Exports the job stage-in statements
					if ( copyJobStageIn )
					//if ( job.getStageIn() != null && !Collections.IsNullOrEmpty( job.getStageIn().getRules() ) )
					{
						this.exportStageIn( job.getStageIn(), wr, false );
					}
					// Exports task stage-in
					if ( task.getStageIn() != null && !Collections.IsNullOrEmpty( task.getStageIn().getRules() ) )
					{
						this.exportStageIn( task.getStageIn(), wr, false );
					}
				}
//				if ( !Collections.IsNullOrEmpty( job.getCommands() ) || !Collections.IsNullOrEmpty( task.getCommands() ) )
				if (
					copyJobCommand
					|| !Collections.IsNullOrEmpty( task.getCommands() )
				) {
					boolean writeSep = false;

					wr.print( "\tremote:" );

					// Exports the job remote commands
					//if ( !Collections.IsNullOrEmpty( job.getCommands() ) )
					if ( copyJobCommand )
					{
						this.exportRemoteCommands( job.getCommands(), wr, false );
						writeSep = true;
					}
					// Exports task stage-in
					if ( !Collections.IsNullOrEmpty( task.getCommands() ) )
					{
						if ( writeSep )
						{
							wr.print( JdfExporter.CommandSeparator );
						}
						this.exportRemoteCommands( task.getCommands(), wr, false );
					}
					wr.println();
				}
				if (
//					( job.getStageOut() != null && !Collections.IsNullOrEmpty( job.getStageOut().getRules() ) )
//					|| ( task.getStageOut() != null && !Collections.IsNullOrEmpty( task.getStageOut().getRules() ) )
					copyJobStageOut
					|| ( task.getStageOut() != null && !Collections.IsNullOrEmpty( task.getStageOut().getRules() ) )
				) {
					wr.println( "\tfinal:" );

					// Exports the job stage-out statements
					//if ( job.getStageOut() != null && !Collections.IsNullOrEmpty( job.getStageOut().getRules() ) )
					if ( copyJobStageOut )
					{
						this.exportStageOut( job.getStageOut(), wr , false);
					}
					// Exports task stage-out
					if ( task.getStageOut() != null && !Collections.IsNullOrEmpty( task.getStageOut().getRules() ) )
					{
						this.exportStageOut( task.getStageOut(), wr, false );
					}
				}
			}
		}
		else
		{
			// Exports the job stage-in statements
			if ( job.getStageIn() != null && !Collections.IsNullOrEmpty( job.getStageIn().getRules() ) )
			{
				this.exportStageIn( job.getStageIn(), wr, true );
			}
			// Exports the job remote commands
			if ( !Collections.IsNullOrEmpty( job.getCommands() ) )
			{
				this.exportRemoteCommands( job.getCommands(), wr, true );
			}
			// Exports the job stage-out statements
			if ( job.getStageOut() != null && !Collections.IsNullOrEmpty( job.getStageOut().getRules() ) )
			{
				this.exportStageOut( job.getStageOut(), wr, true );
			}
		}
	}

	//@} IFormatExporter implementation

	/**
	 * Exports a {@code ITextExpr} object into a JDF expression.
	 */
	protected void exportTextExpr(ITextExpr e, PrintWriter wr) throws FormatExporterException
	{
		if ( e instanceof TerminalTextExpr )
		{
			wr.print( e );
		}
		else if ( e instanceof ParenthesizedTextExpr )
		{
			wr.print( "(" );
			this.exportTextExpr( ((ParenthesizedTextExpr) e).getExpr(), wr );
			wr.print( ")" );
		}
		else if ( e instanceof BinaryTextExpr )
		{
			BinaryTextExpr be = (BinaryTextExpr) e;

			exportTextExpr( be.getLeft(), wr );
			wr.print( " " );
			this.exportTextOp( be.getOp(), wr );
			wr.print( " " );
			this.exportTextExpr( be.getRight(), wr );
		}
		else if ( e instanceof PrefixUnaryTextExpr )
		{
			PrefixUnaryTextExpr pre = (PrefixUnaryTextExpr) e;

			this.exportTextOp( pre.getOp(), wr );
			this.exportTextExpr( pre.getExpr(), wr );
		}
		else if ( e instanceof PostfixUnaryTextExpr )
		{
			PostfixUnaryTextExpr poe = (PostfixUnaryTextExpr) e;

			this.exportTextExpr( poe.getExpr(), wr );
			this.exportTextOp( poe.getOp(), wr );
		}
		else
		{
			throw new FormatExporterException( "Unknown expression type: '" + e.getClass().toString() + "' for JDF export" );
		}
	}

	/**
	 * Exports a {@code ITextOp} object into a JDF operator.
	 */
	protected void exportTextOp(ITextOp op, PrintWriter wr) throws FormatExporterException
	{
		wr.print( op.getSymbol() );
	}

	/**
	 * Exports a {@code IStageIn} object into a JDF stage-in block.
	 */
	protected void exportStageIn(IStageIn in, PrintWriter wr, boolean writeHeader) throws FormatExporterException
	{
		if ( in.getRules() == null || in.getRules().size() == 0 )
		{
			return;
		}

		if ( writeHeader )
		{
			wr.println( "\tinit:" );
		}
		for ( IStageInRule rule : in.getRules() )
		{
			this.exportStageInRule( rule, wr );
		}
		wr.println();
	}

	/**
	 * Exports a {@code IStageInRule} object into a JDF stage-in rule.
	 */
	protected void exportStageInRule(IStageInRule rule, PrintWriter wr) throws FormatExporterException
	{
		wr.print( "\t\t" );

		if ( rule instanceof ConditionalStageInRule )
		{
			ConditionalStageInRule cr = (ConditionalStageInRule) rule;

			wr.print( "if (" );
			wr.print( cr.getCondition() );
			wr.println( ") then" );
			this.exportStageInRule( cr.getTrueRule(), wr );
			wr.println();
			if ( cr.getFalseRule() != null && !cr.getFalseRule().getActions().isEmpty() )
			{
				wr.println( "else" );
				this.exportStageInRule( cr.getFalseRule(), wr );
				wr.println();
			}
			wr.println( "endif" );
		}
		else if ( rule instanceof StageInRule )
		{
			StageInRule r = (StageInRule) rule;

			if ( !Collections.IsNullOrEmpty( r.getActions() ) )
			{
				for ( IStageInAction action : r.getActions() )
				{
					switch ( action.getMode() )
					{
						case ALWAYS_OVERWRITE:
							wr.print( "put" );
							break;
						case DIFF_OVERWRITE:
							wr.print( "store" );
							break;
					}
					wr.print( " " + action.getLocalName() + " " );
					switch ( action.getType() )
					{
						case PERSISTENT:
							wr.print( JdfExporter.RemotePersistentPathVar + "/" + action.getRemoteName() );
							break;
						case VOLATILE:
							if ( this.isStrictSyntax() )
							{
								wr.print( JdfExporter.RemoteVolatilePathVar + "/" + action.getRemoteName() );
							}
							else
							{
								wr.print( action.getRemoteName() );
							}
							//wr.print( action.getRemoteName() );
							break;
					}
					wr.println();
				}
			}
		}
		else
		{
			throw new FormatExporterException( "Unknown rule type: '" + rule.getClass().toString() + "' for JDF export" );
		}
	}

	/**
	 * Exports a list of {@code IRemoteCommand} objects into a JDF remote
	 * command statement.
	 */
	protected void exportRemoteCommands(List<IRemoteCommand> coms, PrintWriter wr, boolean writeHeader)
	{
		if ( Collections.IsNullOrEmpty( coms ) )
		{
			return;
		}

		if ( writeHeader )
		{
			wr.print( "\tremote:" );
		}
		boolean first = true;
		for ( IRemoteCommand com : coms )
		{
			if ( first )
			{
				first = false;
			}
			else
			{
				wr.print( JdfExporter.CommandSeparator );
			}
			wr.print( " " + com.getCommand() );
		}
		if ( writeHeader )
		{
			wr.println();
		}
	}

	/**
	 * Exports a {@code IStageOut} object into a JDF stage-out block.
	 */
	protected void exportStageOut(IStageOut out, PrintWriter wr, boolean writeHeader) throws FormatExporterException
	{
		if ( Collections.IsNullOrEmpty( out.getRules() ) )
		{
			return;
		}

		if ( writeHeader )
		{
			wr.println( "\tfinal:" );
		}
		for ( IStageOutRule rule : out.getRules() )
		{
			this.exportStageOutRule( rule, wr );
		}
		wr.println();
	}

	/**
	 * Exports a {@code IStageOutRule} object into a JDF stage-out rule.
	 */
	protected void exportStageOutRule(IStageOutRule rule, PrintWriter wr) throws FormatExporterException
	{
		wr.print( "\t\t" );

		if ( rule instanceof ConditionalStageOutRule )
		{
			ConditionalStageOutRule cr = (ConditionalStageOutRule) rule;

			wr.print( "if (" );
			wr.print( cr.getCondition() );
			wr.println( ") then" );
			this.exportStageOutRule( cr.getTrueRule(), wr );
			wr.println();
			if ( cr.getFalseRule() != null && !cr.getFalseRule().getActions().isEmpty() )
			{
				wr.println( "else" );
				this.exportStageOutRule( cr.getFalseRule(), wr );
				wr.println();
			}
			wr.println( "endif" );
		}
		else if ( rule instanceof StageOutRule )
		{
			StageOutRule r = (StageOutRule) rule;

			if ( !Collections.IsNullOrEmpty( r.getActions() ) )
			{
				for ( IStageOutAction action : r.getActions() )
				{
					switch ( action.getMode() )
					{
						case ALWAYS_OVERWRITE:
							wr.print( "get" );
							break;
					}
					//wr.println( " " + action.getRemoteName() + " " + action.getLocalName() );
					if ( this.isStrictSyntax() )
					{
						wr.println( " " + JdfExporter.RemoteVolatilePathVar + "/" + action.getRemoteName() + " " + action.getLocalName() );
					}
					else
					{
						wr.println( " " + action.getRemoteName() + " " + action.getLocalName() );
					}
				}
			}
		}
		else
		{
			throw new FormatExporterException( "Unknown rule type: '" + rule.getClass().toString() + "' for JDF export" );
		}
	}
}
