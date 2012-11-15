/*
 * Copyright (C) 2009  Distributed Computing System (DCS) Group, Computer
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

package it.unipmn.di.dcs.grid.core.examples;

import it.unipmn.di.dcs.grid.core.format.*;
import it.unipmn.di.dcs.grid.core.format.jdf.*;
import it.unipmn.di.dcs.grid.core.middleware.sched.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.CharBuffer;

/**
 * @author<a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class JdfParser
{
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

	private static String ExportBotJob(IBotJob job) throws Exception
	{
		JdfExporter jdfExp = null;
		StringWriter swr = null;

		swr = new StringWriter();
		jdfExp = new JdfExporter();

		PrintWriter pwr = new PrintWriter(swr);

		jdfExp.export(job, pwr);

		pwr.close();
		swr.close();

		return swr.toString();
	}

	public static void main(String[] args)
	{
		InputStream is = null;

		if (args.length > 0)
		{
			try
			{
				is = new FileInputStream(args[0]);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		}
		else
		{
			is = System.in;
		}

		InputStreamReader isrd = null;
		String jdf = null;
		try
		{
			isrd = new InputStreamReader(
						new BufferedInputStream(is)
			);
			StringWriter swr = new StringWriter();

			char[] buffer = new char[2048];
			int nread = 0;
			while ((nread = isrd.read(buffer)) > 0)
			{
				swr.append(CharBuffer.wrap(buffer, 0, nread));
			}
			swr.close();
			buffer = null;

			jdf = swr.toString();
			swr = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		try
		{
			IBotJob job = null;
			job = ImportBotJob(jdf);
			System.out.println("Job successfully imported");
			System.out.println("BEGIN-TEXT-JOB>>");
			System.out.println(job);
			System.out.println("<<END-TEXT-JOB");
			System.out.println("BEGIN-JDF-JOB>>");
			System.out.println(ExportBotJob(job));
			System.out.println("<<END-JDF-JOB");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		if (args.length > 0)
		{
			try
			{
				is.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		}
		is = null;
	}
}
