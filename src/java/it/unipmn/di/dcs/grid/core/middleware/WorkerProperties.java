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

package it.unipmn.di.dcs.grid.core.middleware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class for setting/retrieving a worker properties.
 *
 * A <em>worker</em> is a remote machine used for GRID computation.
 *
 * @author <a href="mailto:marco.guazzone@gmail.com">Marco Guazzone</a>
 */
public class WorkerProperties
{
	private Map<String,List<String>> props;

	public WorkerProperties()
	{
		this.props = new HashMap<String,List<String>>();
	}

	public Set<String> getPropertyNames()
	{
		return this.props.keySet();
	}

	public void addProperty(String name, List<String> values)
	{
		this.props.put( name, new ArrayList<String>(values) );
	}

	public void addToProperty(String name, String value)
	{
		if ( !this.props.containsKey(name) )
		{
			this.props.put( name, new ArrayList<String>() );
		}
		this.props.get( name ).add( value );
	}

	public List<String> getProperty(String name)
	{
		return this.props.get(name);
	}
}
