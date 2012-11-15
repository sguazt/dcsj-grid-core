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

import it.unipmn.di.dcs.grid.core.format.*;
import it.unipmn.di.dcs.grid.core.format.jdf.*;
import it.unipmn.di.dcs.grid.core.middleware.sched.*;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.JUnitCore;

/**
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public final class TestJdfParsification
{
	private static final transient Logger Log = Logger.getLogger( TestJdfParsification.class.getName() );

	private static IJob ImportJob(String jdf) throws Exception
	{
		JdfImporter jdfImp = null;
		IJob job = null;
		StringReader srd = null;

		srd = new StringReader( jdf );
		jdfImp = new JdfImporter();
		job = jdfImp.importJob(srd);

		srd.close();

		return job;
	}

	private static IBotJob ImportBotJob(String jdf) throws Exception
	{
		JdfImporter jdfImp = null;
		IBotJob job = null;
		StringReader srd = null;

		srd = new StringReader( jdf );
		jdfImp = new JdfImporter();
		job = jdfImp.importBotJob(srd);

		srd.close();

		return job;
	}

	@Before
	public void setUp()
	{
		Log.info("Setting-up test suite...");

		Log.info("Set-up test suite");
	}

	@Test
	public void testImportEchoJob()
	{
		Log.info("Entering the 'JDF Import Echo Job' test...");

		IBotJob job = null;

		try
		{
			job = ImportBotJob(
				"job :\n"
				+ "label   : EchoJob\n"
				+ "\n"
				+ "task :\n"
				+ "init    :\n"
				+ "remote  : echo abc\n"
				+ "final   :\n"
			);

			Log.info( "Job: " + job );
		}
		catch (Exception e)
		{
			Log.log(Level.SEVERE, "Caught exception.", e );
			assertTrue( false );
		}

		Log.info("Exiting the 'JDF Import Echo Job' test...");

		assertTrue( job != null );
	}

	@Test
	public void testImportHostnameJob()
	{
		Log.info("Entering the 'JDF Import Hostname Job' test...");

		IBotJob job = null;

		try
		{
			job = ImportBotJob(
				"job :\n"
				+ "\tlabel : hostname\n"
				+ "task :\n"
				+ "\tinit: put mytext.txt  mytext.txt\n"
				+ "\tremote: cat mytext.txt > output-$JOB.$TASK && ls >> output-$JOB.$TASK\n"
				+ "\tfinal: get output-$JOB.$TASK output-$JOB.$TASK\n"
				+ "task :\n"
				+ "\tremote : hostname > output-$JOB.$TASK\n"
				+ "\tfinal : get output-$JOB.$TASK output-$JOB.$TASK\n"
				+ "task :\n"
				+ "\tremote : hostname > output-$JOB.$TASK\n"
				+ "\tfinal : get output-$JOB.$TASK output-$JOB.$TASK\n"
			);

			Log.info( "Job: " + job );
		}
		catch (Exception e)
		{
			Log.log(Level.SEVERE, "Caught exception.", e );
			assertTrue( false );
		}

		Log.info("Exiting the 'JDF Import Hostname Job' test...");

		assertTrue( job != null );
	}

	@Test
	public void testImportJobWithCompoundRemote1()
	{
		Log.info("Entering the 'JDF Import CompoundRemote1 Job' test...");

		IBotJob job = null;

		try
		{
			job = ImportBotJob(
				"job:\n"
				+ "\tlabel: compound-remote1\n"
				+ "\n"
				+ "task:\n"
				+ "\tremote:\n"
				+ "\t\tmkdir foodir/; foobin -i fooin -d foodir/  -a; zip -r -9 -q foo.zip foodir\n"
			);

			Log.info( "Job: " + job );
		}
		catch (Exception e)
		{
			Log.log(Level.SEVERE, "Caught exception.", e );
			assertTrue( false );
		}

		Log.info("Exiting the 'JDF Import CompoundRemote1 Job' test...");

		assertTrue( job != null );
	}

	@Test
	public void testImportJobWithCompoundRemote2()
	{
		Log.info("Entering the 'JDF Import CompoundRemote2 Job' test...");

		IBotJob job = null;

		try
		{
			job = ImportBotJob(
				"job:\n"
				+ "\tlabel: compound-remote2\n"
				+ "\n"
				+ "task:\n"
				+ "\tremote:\n"
				+ "\t\tmkdir foodir/ && foobin -i fooin -d foodir/  -a && zip -r -9 -q foo.zip foodir\n"
			);

			Log.info( "Job: " + job );
		}
		catch (Exception e)
		{
			Log.log(Level.SEVERE, "Caught exception.", e );
			assertTrue( false );
		}

		Log.info("Exiting the 'JDF Import CompoundRemote2 Job' test...");

		assertTrue( job != null );
	}

	@Test
	public void testImportGenericJob1()
	{
		Log.info("Entering the 'JDF Import Generic Job#1' test...");

		IBotJob job = null;

		try
		{
			job = ImportBotJob(
				"job:\n"
				+ "\tlabel: bot-job\n"
				+ "\t\trequirements: blender == true\n"
				+ "\n"
				+ "task:\n"
				+ "\tinit: \n"
				+ "\t\tput in-local-1 in-remote-1\n"
				+ "\n"
				+ "\tremote: blender in-remote-1\n"
				+ "\tfinal:\n"
				+ "\t\tget out-remote-1 out-local-1.$JOB.$TASK\n"
				+ "\n"
				+ "\n"
				+ "task:\n"
				+ "\tinit: \n"
				+ "\t\tput in-local-2 in-remote-2\n"
				+ "\n"
				+ "\tremote: blender in-remote-2\n"
				+ "\tfinal:\n"
				+ "\t\tget out-remote-2 out-local-2.$JOB.$TASK\n"
				+ "\n"
				+ "\n"
				+ "task:\n"
				+ "\tinit: \n"
				+ "\t\tput in-local-3 in-remote-3\n"
				+ "\n"
				+ "\tremote: blender in-remote-3\n"
				+ "\tfinal:\n"
				+ "\t\tget out-remote-3 out-local-3.$JOB.$TASK\n"
			);

			Log.info( "Job: " + job );
		}
		catch (Exception e)
		{
			Log.log(Level.SEVERE, "Caught exception.", e );
			assertTrue( false );
		}

		Log.info("Exiting the 'JDF Import Generic Job#1' test...");

		assertTrue( job != null );
	}

	@Test
	public void testImportGenericJob2()
	{
		Log.info("Entering the 'JDF Import Generic Job#2' test...");

		IBotJob job = null;

		try
		{
			job = ImportBotJob(
				"job:\n"
				+ "\tlabel: Octave_MandelBrot\n"
				+ "\trequirements: ( octave == true )\n"
				+ "\tinit:\n"
				+ "\t\tput mandelbrot.m mandelbrot.m\n"
				+ "\tremote: octave --eval \"mandelbrot(3000,50,'mandel','png')\"\n"
				+ "\tfinal:\n"
				+ "\t\tget mandel.png mandel.png\n"
				+ "\n"
				+ "task:\n"
			);

			Log.info( "Job: " + job );
		}
		catch (Exception e)
		{
			Log.log(Level.SEVERE, "Caught exception.", e );
			assertTrue( false );
		}

		Log.info("Exiting the 'JDF Import Generic Job#2' test...");

		assertTrue( job != null );
	}

	@Test
	public void testImportGenericJob3()
	{
		Log.info("Entering the 'JDF Import Generic Job#3' test...");

		IBotJob job = null;

		try
		{
			job = ImportBotJob(
				"job:\n"
				+ "\tlabel: Blender_ArrayDemo\n"
				+ "\trequirements: ( blender == true )\n"
				+ "\tinit:\n"
				+ "\t\tput ArrayDemo.blend ArrayDemo.blend\n"
				+ "\n"
				+ "task:\n"
				+ "\tremote: mkdir ArrayDemo.blend-rendered/; blender -b ArrayDemo.blend -s 1 -e 50 -o ArrayDemo.blend-rendered/  -a; zip -r -9 -q ArrayDemo.blend-rendered-1_50.zip ArrayDemo.blend-rendered\n"
				+ "\tfinal: get ArrayDemo.blend-rendered-1_50.zip ArrayDemo.blend-rendered-1_50-$JOB.$TASK.zip\n"
				+ "\n"
				+ "task:\n"
				+ "\tremote: mkdir ArrayDemo.blend-rendered/; blender -b ArrayDemo.blend -s 51 -e 100 -o ArrayDemo.blend-rendered/  -a; zip -r -9 -q ArrayDemo.blend-rendered-51_100.zip ArrayDemo.blend-rendered\n"
				+ "\tfinal: get ArrayDemo.blend-rendered-51_100.zip ArrayDemo.blend-rendered-51_100-$JOB.$TASK.zip\n"
				+ "\n"
				+ "task:\n"
				+ "\tremote: mkdir ArrayDemo.blend-rendered/; blender -b ArrayDemo.blend -s 101 -e 150 -o ArrayDemo.blend-rendered/  -a; zip -r -9 -q ArrayDemo.blend-rendered-101_150.zip ArrayDemo.blend-rendered\n"
				+ "\tfinal: get ArrayDemo.blend-rendered-101_150.zip ArrayDemo.blend-rendered-101_150-$JOB.$TASK.zip\n"
				+ "\n"
				+ "task:\n"
				+ "\tremote: mkdir ArrayDemo.blend-rendered/; blender -b ArrayDemo.blend -s 151 -e 200 -o ArrayDemo.blend-rendered/  -a; zip -r -9 -q ArrayDemo.blend-rendered-151_200.zip ArrayDemo.blend-rendered\n"
				+ "\tfinal: get ArrayDemo.blend-rendered-76_100.zip ArrayDemo.blend-rendered-151_200-$JOB.$TASK.zip\n"
				+ "\n"
				+ "task:\n"
				+ "\tremote: mkdir ArrayDemo.blend-rendered/; blender -b ArrayDemo.blend -s 201 -e 250 -o ArrayDemo.blend-rendered/  -a; zip -r -9 -q ArrayDemo.blend-rendered-201_250.zip ArrayDemo.blend-rendered\n"
				+ "\tfinal: get ArrayDemo.blend-rendered-201_250.zip ArrayDemo.blend-rendered-201_250-$JOB.$TASK.zip\n"
				+ "\n"
				+ "task:\n"
				+ "\tremote: mkdir ArrayDemo.blend-rendered/; blender -b ArrayDemo.blend -s 251 -e 300 -o ArrayDemo.blend-rendered/  -a; zip -r -9 -q ArrayDemo.blend-rendered-251_300.zip ArrayDemo.blend-rendered\n"
				+ "\tfinal: get ArrayDemo.blend-rendered-251_300.zip ArrayDemo.blend-rendered-251_300-$JOB.$TASK.zip\n"
				+ "\n"
				+ "task:\n"
				+ "\tremote: mkdir ArrayDemo.blend-rendered/; blender -b ArrayDemo.blend -s 301 -e 350 -o ArrayDemo.blend-rendered/  -a; zip -r -9 -q ArrayDemo.blend-rendered-301_350.zip ArrayDemo.blend-rendered\n"
				+ "\tfinal: get ArrayDemo.blend-rendered-301_350.zip ArrayDemo.blend-rendered-301_350-$JOB.$TASK.zip\n"
				+ "\n"
				+ "task:\n"
				+ "\tremote: mkdir ArrayDemo.blend-rendered/; blender -b ArrayDemo.blend -s 351 -e 400 -o ArrayDemo.blend-rendered/  -a; zip -r -9 -q ArrayDemo.blend-rendered-351_400.zip ArrayDemo.blend-rendered\n"
				+ "\tfinal: get ArrayDemo.blend-rendered-351_400.zip ArrayDemo.blend-rendered-351_400-$JOB.$TASK.zip\n"
				+ "\n"
				+ "task:\n"
				+ "\tremote: mkdir ArrayDemo.blend-rendered/; blender -b ArrayDemo.blend -s 401 -e 450 -o ArrayDemo.blend-rendered/  -a; zip -r -9 -q ArrayDemo.blend-rendered-401_450.zip ArrayDemo.blend-rendered\n"
				+ "\tfinal: get ArrayDemo.blend-rendered-401_450.zip ArrayDemo.blend-rendered-401_450-$JOB.$TASK.zip\n"
				+ "\n"
				+ "task:\n"
				+ "\tremote: mkdir ArrayDemo.blend-rendered/; blender -b ArrayDemo.blend -s 451 -e 500 -o ArrayDemo.blend-rendered/  -a; zip -r -9 -q ArrayDemo.blend-rendered-451_500.zip ArrayDemo.blend-rendered\n"
				+ "\tfinal: get ArrayDemo.blend-rendered-451_500.zip ArrayDemo.blend-rendered-451_500-$JOB.$TASK.zip\n"
				+ "\n"
				+ "task:\n"
				+ "\tremote: mkdir ArrayDemo.blend-rendered/; blender -b ArrayDemo.blend -s 501 -e 550 -o ArrayDemo.blend-rendered/  -a; zip -r -9 -q ArrayDemo.blend-rendered-501_550.zip ArrayDemo.blend-rendered\n"
				+ "\tfinal: get ArrayDemo.blend-rendered-501_550.zip ArrayDemo.blend-rendered-501_550-$JOB.$TASK.zip\n"
				+ "\n"
				+ "task:\n"
				+ "\tremote: mkdir ArrayDemo.blend-rendered/; blender -b ArrayDemo.blend -s 551 -e 600 -o ArrayDemo.blend-rendered/  -a; zip -r -9 -q ArrayDemo.blend-rendered-551_600.zip ArrayDemo.blend-rendered\n"
				+ "\tfinal: get ArrayDemo.blend-rendered-551_600.zip ArrayDemo.blend-rendered-551_600-$JOB.$TASK.zip\n"
				+ "\n"
				+ "task:\n"
				+ "\tremote: mkdir ArrayDemo.blend-rendered/; blender -b ArrayDemo.blend -s 601 -e 650 -o ArrayDemo.blend-rendered/  -a; zip -r -9 -q ArrayDemo.blend-rendered-601_650.zip ArrayDemo.blend-rendered\n"
				+ "\tfinal: get ArrayDemo.blend-rendered-601_650.zip ArrayDemo.blend-rendered-601_650-$JOB.$TASK.zip\n"
				+ "\n"
				+ "task:\n"
				+ "\tremote: mkdir ArrayDemo.blend-rendered/; blender -b ArrayDemo.blend -s 651 -e 700 -o ArrayDemo.blend-rendered/  -a; zip -r -9 -q ArrayDemo.blend-rendered-651_700.zip ArrayDemo.blend-rendered\n"
				+ "\tfinal: get ArrayDemo.blend-rendered-651_700.zip ArrayDemo.blend-rendered-651_700-$JOB.$TASK.zip\n"
				+ "\n"
				+ "task:\n"
				+ "\tremote: mkdir ArrayDemo.blend-rendered/; blender -b ArrayDemo.blend -s 701 -e 737 -o ArrayDemo.blend-rendered/  -a; zip -r -9 -q ArrayDemo.blend-rendered-701_737.zip ArrayDemo.blend-rendered\n"
				+ "\tfinal: get ArrayDemo.blend-rendered-701_737.zip ArrayDemo.blend-rendered-701_737-$JOB.$TASK.zip\n"
			);

			Log.info( "Job: " + job );
		}
		catch (Exception e)
		{
			Log.log(Level.SEVERE, "Caught exception.", e );
			assertTrue( false );
		}

		Log.info("Exiting the 'JDF Import Generic Job#3' test...");

		assertTrue( job != null );
	}

	@Test
	public void testImportGenericJob4()
	{
		Log.info("Entering the 'JDF Import Generic Job#4' test...");

		IBotJob job = null;

		try
		{
			job = ImportBotJob(
				"job:\n"
				+ "\tlabel: Octave_Kronecker\n"
				+ "\trequirements: ( octave == true )\n"
				+ "\tinit:\n"
				+ "\t\tput Octave_Kronecker.sh Octave_Kronecker.sh\n"
				+ "\t\tput grid_kronprod.m grid_kronprod.m\n"
				+ "\t\tput matrix1.dat matrix1.dat\n"
				+ "\t\tput matrix2.dat matrix2.dat\n"
				+ "#\tremote: octave --silent --eval \"M = grid_kronprod($TASK,'matrix1.dat','matrix2.dat'); save('-ascii', 'matrixRes.$TASK.dat', 'M');\"\n"
				+ "#\tremote: Octave_Kronecker.sh $TASK\n"
				+ "\tremote: octave --silent --eval \"M = grid_kronprod(1,'matrix1.dat','matrix2.dat'); save('-ascii', 'matrixRes.1.dat', 'M');\"\n"
				+ "\tfinal:\n"
				+ "\t\tget matrixRes.$TASK.dat matrixRes.$TASK.dat\n"
				+ "\n"
				+ "task:\n"
				+ "task:\n"
				+ "task:\n"
				+ "task:\n"
				+ "task:\n"
			);

			Log.info( "Job: " + job );
		}
		catch (Exception e)
		{
			Log.log(Level.SEVERE, "Caught exception.", e );
			assertTrue( false );
		}

		Log.info("Exiting the 'JDF Import Generic Job#4' test...");

		assertTrue( job != null );
	}

	@After
	public void tearDown()
	{
		Log.info("Tearing-down test suite...");

		Log.info("Torn-down test suite");
	}

	public static void main(String[] args)
	{
		org.junit.runner.JUnitCore.main( TestJdfParsification.class.getName() );
	}
}
