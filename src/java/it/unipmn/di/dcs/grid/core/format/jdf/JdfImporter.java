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

import it.unipmn.di.dcs.grid.core.format.FormatImporterException;
import it.unipmn.di.dcs.grid.core.format.IFormatImporter;
import it.unipmn.di.dcs.grid.core.middleware.sched.IBotJob;
import it.unipmn.di.dcs.grid.core.middleware.sched.IJob;

import java.io.InputStream;
import java.io.Reader;

/**
 * Class for JDF format importer.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class JdfImporter implements IFormatImporter
{
	//@{ IFormatImporter implementation

	public IJob importJob(Reader rd) throws FormatImporterException
	{
		return this.importBotJob(rd);
	}

	public IJob importJob(InputStream is) throws FormatImporterException
	{
		return this.importBotJob(is);
	}

	public IJob importJob(InputStream is, String encoding) throws FormatImporterException
	{
		return this.importBotJob(is, encoding);
	}

	public IBotJob importBotJob(Reader rd) throws FormatImporterException
	{
		JdfGrammar parser = null;
		IBotJob job = null;

		try
		{
			parser = new JdfGrammar(rd);
			job = parser.Jdf();
		}
		catch (Exception e)
		{
			throw new FormatImporterException( "Error while importing JDF.", e );
		}

		return job;
	}

	public IBotJob importBotJob(InputStream is) throws FormatImporterException
	{
		return this.importBotJob( is, null );
	}

	public IBotJob importBotJob(InputStream is, String encoding) throws FormatImporterException
	{
		JdfGrammar parser = null;
		IBotJob job = null;

		try
		{
			parser = new JdfGrammar(is, encoding);
			job = parser.Jdf();
		}
		catch (Exception e)
		{
			throw new FormatImporterException( "Error while importing JDF.", e );
		}

		return job;
	}

	//@} IFormatImporter implementation
}
