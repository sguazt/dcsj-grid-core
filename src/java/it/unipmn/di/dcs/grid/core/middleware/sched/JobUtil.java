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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectInput;
import java.io.ObjectOutputStream;
import java.io.IOException;

/**
 * Utility class for jobs.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class JobUtil
{
	public static IJob CreateJobFromExternalized(ObjectInput in) throws IOException, ClassNotFoundException
	{
		// Read ver and type for determing the job class
		long ver = in.readLong();
		int type = in.readInt();
		IJob job = null;

		// Choose the right job implementation class
		switch ( IntToJobType(type) )
		{
			case BOT:
				job = new BotJob();
				break;
			case SINGLE:
				job = new SingleJob();
				break;
			case UNKNOWN:
			default:
				throw new IOException("Input stream is not a known job.");
		}

		// Read the rest of bytes into a byte array
		// This is primarly done because the
		// ObjectInput does not guarantee to be reset
		// to the start of the stream.

		String raw = (String) in.readObject();
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;

		baos = new ByteArrayOutputStream();
		oos = new ObjectOutputStream(baos);
		oos.writeLong(ver);
		oos.writeInt(type);
		oos.writeObject(raw);

/*
		int buflen = 2048;
		byte[] buf = new byte[buflen];
		int nread = 0;
		while ((nread = in.read(buf, 0, buflen)) != -1)
		{
			oos.write(buf, 0, nread);
		}
*/
		oos.close();
		oos = null;
		byte[] buf = baos.toByteArray();
		baos.close();
		baos = null;

		// Write back the byte array into a byte
		// stream and use this as the source for
		// an object input stream. With this last
		// stream, the job can be deserialized.

		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		bais = new ByteArrayInputStream( buf );
		ois = new ObjectInputStream(bais);
		job.readExternal(ois);
		ois.close();
		ois = null;
		bais.close();
		bais = null;

		return job;
	}

	/** Returns an integer value for the given job type. */
	public static int JobTypeToInt(JobType type)
	{
		switch (type)
		{
			case BOT:
				return 2;
			case SINGLE:
				return 1;
			case UNKNOWN:
			default:
				return 0;
		}
	}

	/** Returns the job type related to the given integer value. */
	public static JobType IntToJobType(int type)
	{
		switch (type)
		{
			case 1:
				return JobType.SINGLE;
			case 2:
				return JobType.BOT;
			case 0:
			default:
				return JobType.UNKNOWN;
		}
	}
}
