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

package it.unipmn.di.dcs.grid.core.format;

import it.unipmn.di.dcs.grid.core.middleware.sched.IBotJob;
import it.unipmn.di.dcs.grid.core.middleware.sched.IJob;

import java.io.InputStream;
import java.io.Reader;

/**
 * Interface for format importers.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public interface IFormatImporter
{
	IJob importJob(Reader rd) throws FormatImporterException;

	IJob importJob(InputStream is) throws FormatImporterException;

	IJob importJob(InputStream is, String encoding) throws FormatImporterException;

	IBotJob importBotJob(Reader rd) throws FormatImporterException;

	IBotJob importBotJob(InputStream is) throws FormatImporterException;

	IBotJob importBotJob(InputStream is, String encoding) throws FormatImporterException;
}
