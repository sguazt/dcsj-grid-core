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
package it.unipmn.di.dcs.grid.core.examples;

import it.unipmn.di.dcs.common.conversion.Convert;

import it.unipmn.di.dcs.grid.core.format.*;
import it.unipmn.di.dcs.grid.core.format.jdf.*;
import it.unipmn.di.dcs.grid.core.middleware.sched.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Simple job serializer application.
 *
 * @author<a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public final class JobSerializer
{
	public static byte[] Serialize(IJob job, boolean encode)
	{
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		byte[] serJob = null;

		try
		{
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			job.writeExternal(oos);
			if ( encode )
			{
				serJob = Convert.BytesToBase64( baos.toString().getBytes() ).getBytes();
			}
			else
			{
				serJob = baos.toString().getBytes();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			serJob = null;
		}
		finally
		{
			if ( oos != null )
			{
				try { oos.close(); } catch (Exception e) { /* ignore */ }
				oos = null;
			}
			if ( baos != null )
			{
				try { baos.close(); } catch (Exception e) { /* ignore */ }
				baos = null;
			}
		}

		return serJob;
	}

	public static IJob Deserialize(byte[] serJob, boolean decode)
	{
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;

		IJob job = null;

		try
		{
			if ( decode )
			{
				serJob = Convert.Base64ToBytes( serJob );
System.err.println("DECODE: " + new String(serJob));//XXX
			}

			bais = new ByteArrayInputStream(serJob);
			ois = new ObjectInputStream(bais);

			job = JobUtil.CreateJobFromExternalized(ois);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			job = null;
		}
		finally
		{
			if  (ois != null)
			{
				try { ois.close(); } catch (Exception e) { /* ignore */ }
				ois = null;
			}
			if ( bais != null)
			{
				try { bais.close(); } catch (Exception e) { /* ignore */ }
				bais = null;
			}
		}

		return job;
	}

	public static void main(String[] args)
	{
		boolean serialize = false;
		boolean encdec = false;
		String inFile = null;

		if ( args.length == 0 )
		{
			System.err.println("Missing arguments");
			System.exit(1);
		}

		for (int narg = 0; narg < args.length; narg++)
		{
			if ( "-s".equals(args[narg]) )
			{
				serialize = true;
			}
			else if ( "-d".equals(args[narg]) )
			{
				serialize = false;
			}
			else if ( "-k".equals(args[narg]) )
			{
				encdec = true;
			}
			else if ( "-f".equals(args[narg]) )
			{
				inFile = args[++narg];
			}
		}

		InputStream is = null;
		if ( inFile != null )
		{
			try
			{
				is = new FileInputStream(inFile);
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

		if ( serialize )
		{

			IJob job = null;

			IFormatImporter importer = null;

			byte[] data = null;

			try
			{
				importer = new JdfImporter();

				job = importer.importJob(is);

				data = JobSerializer.Serialize( job, encdec );
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.exit(1);
			}

			System.out.println( new String(data) );
		}
		else
		{
			byte[] data = null;

			ByteArrayOutputStream baos = null;
			try
			{
				baos = new ByteArrayOutputStream();

				byte[] buf = new byte[1024];
				int nread = 0;

				while ( (nread = is.read(buf)) > 0 )
				{
					baos.write( buf );
				}

				data = baos.toByteArray();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.exit(1);
			}
			finally
			{
				if ( baos != null )
				{
					try { baos.close(); } catch (Exception e) { /* ignore */ }
					baos = null;
				}
			}
				
			IJob job = null;

System.err.println("READ: " + new String(data));//XXX
			job = JobSerializer.Deserialize( data, encdec );

			System.out.println( new String(data) );
		}

		if ( inFile != null && is != null )
		{
			try { is.close(); } catch (Exception e) { /* ignore */ }
			is = null;
		}
	}
}
