/*
 * This file is part of Transitime.org
 * 
 * Transitime.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (GPL) as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * Transitime.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Transitime.org .  If not, see <http://www.gnu.org/licenses/>.
 */

package org.transitime.ipc.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

import org.transitime.ipc.data.IpcRoute;
import org.transitime.ipc.data.IpcRouteSummary;

/**
 *
 *
 * @author SkiBu Smith
 *
 */
public interface ConfigInterface extends Remote {

	/**
	 * Obtains list of routes configured.
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public Collection<IpcRouteSummary> getRoutes() throws RemoteException;

	/**
	 * Obtains data for single route.
	 * 
	 * @param routeShortName
	 *            Specifies which route to provide data for. routeShortName is
	 *            used instead of routeId since routeIds unfortunately often
	 *            change when there is a schedule change.
	 * @param stopId
	 *            If want UI to highlight the remaining stops and paths left in
	 *            trip then stopId is used to return which stops remain in trip.
	 *            If this additional info not needed for UI then null can be
	 *            specified.
	 * @param destinationName
	 *            If want UI to highlight the remaining stops and paths left in
	 *            trip then stopId is used to determine which trip pattern to
	 *            highlight. If this additional info not needed for UI then null
	 *            can be specified.
	 * @return
	 * @throws RemoteException
	 */
	public IpcRoute getRoute(String routeShortName, String stopId,
			String destinationName) throws RemoteException;
}
