/*
 * Copyright 2010-2015 the original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
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

package de.schildbach.pte.live;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import de.schildbach.pte.NetworkProvider;
import de.schildbach.pte.NetworkProvider.Accessibility;
import de.schildbach.pte.NetworkProvider.WalkSpeed;
import de.schildbach.pte.dto.Location;
import de.schildbach.pte.dto.NearbyStationsResult;
import de.schildbach.pte.dto.Product;
import de.schildbach.pte.dto.QueryDeparturesResult;
import de.schildbach.pte.dto.QueryTripsContext;
import de.schildbach.pte.dto.QueryTripsResult;
import de.schildbach.pte.dto.SuggestLocationsResult;

/**
 * @author Andreas Schildbach
 */
public abstract class AbstractProviderLiveTest
{
	protected final NetworkProvider provider;

	public AbstractProviderLiveTest(final NetworkProvider provider)
	{
		this.provider = provider;
	}

	protected final void print(final NearbyStationsResult result)
	{
		System.out.println(result);
	}

	protected final void print(final QueryDeparturesResult result)
	{
		System.out.println(result);

		// for (final StationDepartures stationDepartures : result.stationDepartures)
		// for (final Departure departure : stationDepartures.departures)
		// System.out.println(departure);
	}

	protected final void print(final SuggestLocationsResult result)
	{
		System.out.println(result);
	}

	protected final void print(final QueryTripsResult result)
	{
		System.out.println(result);

		// for (final Trip trip : result.trips)
		// System.out.println(trip);
	}

	protected final NearbyStationsResult queryNearbyStations(final Location location) throws IOException
	{
		return queryNearbyStations(location, 0, 0);
	}

	protected final NearbyStationsResult queryNearbyStations(final Location location, final int maxDistance, final int maxStations)
			throws IOException
	{
		return provider.queryNearbyStations(location, maxDistance, maxStations);
	}

	protected final QueryDeparturesResult queryDepartures(final String stationId, final boolean equivs) throws IOException
	{
		return queryDepartures(stationId, 0, equivs);
	}

	protected final QueryDeparturesResult queryDepartures(final String stationId, final int maxDepartures, final boolean equivs) throws IOException
	{
		final QueryDeparturesResult result = provider.queryDepartures(stationId, new Date(), maxDepartures, equivs);

		// for (final StationDepartures stationDepartures : result.stationDepartures)
		// for (final Departure departure : stationDepartures.departures)
		// System.out.println(departure);

		if (result.status == QueryDeparturesResult.Status.OK)
		{
			if (equivs)
				assertTrue(result.stationDepartures.size() > 1);
			else
				assertTrue(result.stationDepartures.size() == 1);
		}

		return result;
	}

	protected final SuggestLocationsResult suggestLocations(final CharSequence constraint) throws IOException
	{
		return provider.suggestLocations(constraint);
	}

	protected final QueryTripsResult queryTrips(final Location from, final Location via, final Location to, final Date date, final boolean dep,
			final Collection<Product> products, final WalkSpeed walkSpeed, final Accessibility accessibility) throws IOException
	{
		return provider.queryTrips(from, via, to, date, dep, products, walkSpeed, accessibility, null);
	}

	protected final QueryTripsResult queryMoreTrips(final QueryTripsContext context, final boolean later) throws IOException
	{
		return provider.queryMoreTrips(context, later);
	}
}
