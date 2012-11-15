/*
 * Copyright (C) 2008  Distributed Computing System (DCS) Group, Computer
 * Science Department - University of Piemonte Orientale, Alessandria (Italy).
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package test.unit;

import it.unipmn.di.dcs.common.text.*;

import it.unipmn.di.dcs.grid.core.middleware.sched.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.JUnitCore;

/**
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class TestJobSerialization
{
	private static final transient Logger Log = Logger.getLogger( TestJobSerialization.class.getName() );

	private static IJob CreateTestSingleJob()
	{
		SingleJob job = new SingleJob("single-job");
		job.setRequirements(
			new JobRequirements(
				new BinaryTextExpr(
					new TerminalTextExpr("blender"),
					new BinaryTextOp("==","Equality"),
					new TerminalTextExpr("true")
				)
			)
		);
		job.setStageIn(
			new StageIn(
				new StageInRule(
					new StageInAction(
						StageInMode.ALWAYS_OVERWRITE,
						"in-local",
						"in-remote",
						StageInType.VOLATILE
					)
				)
			)
		);
		job.addCommand(
			new RemoteCommand("blender in-remote")
		);
		job.setStageOut(
			new StageOut(
				new StageOutRule(
					new StageOutAction(
						StageOutMode.ALWAYS_OVERWRITE,
						"out-remote",
						"out-local"
					)
				)
			)
		);

		return job;
	}

	private static IBotJob CreateTestBotJob()
	{
		BotJob job = new BotJob("bot-job");

		job.setRequirements(
			new JobRequirements(
				new BinaryTextExpr(
					new TerminalTextExpr("blender"),
					new BinaryTextOp("==","Equality"),
					new TerminalTextExpr("true")
				)
			)
		);

		for (int i = 1; i <= 3; i++)
		{
			BotTask task = new BotTask();

			task.setStageIn(
				new StageIn(
					new StageInRule(
						new StageInAction(
							StageInMode.ALWAYS_OVERWRITE,
							"in-local-" + i,
							"in-remote-" + i,
							StageInType.VOLATILE
						)
					)
				)
			);
			task.addCommand(
				new RemoteCommand("blender in-remote")
			);
			task.setStageOut(
				new StageOut(
					new StageOutRule(
						new StageOutAction(
							StageOutMode.ALWAYS_OVERWRITE,
							"out-remote-" + i,
							"out-local-" + i + ".$JOB.$TASK"
						)
					)
				)
			);
			job.addTask( task );
		}

		return job;
	}

	@Before
	public void setUp()
	{
		Log.info("Setting-up test suite...");

		Log.info("Set-up test suite");
	}

	@Test
	public void testJobUtil()
	{
		Log.info("Entering the 'Job Util' test...");

		try
		{
			Log.info("Creating a testing BotJob...");

			final IJob job = CreateTestBotJob();
			Log.info("Created job: " + job);
			java.io.StringWriter swr = new java.io.StringWriter();
			it.unipmn.di.dcs.grid.core.format.jdf.JdfExporter exp;
			exp = new it.unipmn.di.dcs.grid.core.format.jdf.JdfExporter();
			exp.setStrictSyntax(false);
			exp.export((IBotJob)job,new java.io.PrintWriter(swr));
			swr.close();
			Log.info("JDF: " + swr.toString());

			Log.info("Writing job to memory...");

			ByteArrayOutputStream baos = null;
			ObjectOutputStream oos = null;
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			job.writeExternal(oos);
			oos.close();
			baos.close();

			byte[] buf = baos.toByteArray();

			oos = null;
			baos = null;

			Log.info("Reading job from memory...");

			ByteArrayInputStream bais = null;
			ObjectInputStream ois = null;
			bais = new ByteArrayInputStream(buf);
			ois = new ObjectInputStream(bais);

			Log.info("Read bytes: " + new String(buf));

			final IJob newJob = JobUtil.CreateJobFromExternalized(ois);

			ois.close();
			ois = null;
			bais.close();
			bais = null;

			Log.info("Comparing original job vs. deserialized job...");

			System.out.println( "JOB:");
			System.out.println( job.toString() );
			System.out.println( "NEW JOB:");
			System.out.println( newJob.toString() );
		}
		catch (Exception e)
		{
			Log.log(Level.SEVERE, "Caught exception.", e );
			assertTrue( false );
		}

		Log.info("Exiting the 'Job Util' test...");

		assertTrue( true );
	}

	@After
	public void tearDown()
	{
		Log.info("Tearing-down test suite...");

		Log.info("Torn-down test suite");
	}

	public static void main(String[] args)
	{
		org.junit.runner.JUnitCore.main( TestJobSerialization.class.getName() );
	}
}
