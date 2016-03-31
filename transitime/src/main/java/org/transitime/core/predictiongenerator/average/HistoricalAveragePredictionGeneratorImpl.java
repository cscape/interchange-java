package org.transitime.core.predictiongenerator.average;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.transitime.config.IntegerConfigValue;
import org.transitime.core.Indices;
import org.transitime.core.VehicleState;
import org.transitime.core.dataCache.TripDataHistoryCache;
import org.transitime.core.dataCache.VehicleDataCache;
import org.transitime.core.dataCache.VehicleStateManager;
import org.transitime.core.predictiongenerator.KalmanPredictionGeneratorImpl;
import org.transitime.core.predictiongenerator.PredictionGenerator;
import org.transitime.db.structs.AvlReport;

/**
 * @author Sean Og Crudden
 *	This is a prediction generator that uses a an average of the last x days journeys.
 *	It uses the default prediction method of transiTime while it generates enough data to support
 *  this method of prediction. 
 *  
 *  TODO 
 *  The data could be by two paramaters. 
 *  One is the last X number of weeks and the other a bitmap for which days of each week to include.
 *  
 *  Eg. 
 *  2 0x01 would be Each sunday of the last two weeks.
 *  3 0x02 woud be Each monday of the last two weeks.
 *  4 0x03 would be each sunday and monday of the last two weeks.
 *  
 *  The method of setting which days to use will probably expand. Also may weight 
 *  particular days such as today as it will more reflect the current situaton on the ground.
 */
public class HistoricalAveragePredictionGeneratorImpl extends
		KalmanPredictionGeneratorImpl implements PredictionGenerator {
	
	private static final IntegerConfigValue minDays = new IntegerConfigValue(
			"transitime.prediction.data.average.mindays",
			new Integer(2),
			"Min number of days trip data that needs to be available before historical average prediciton is used instead of default transiTime prediction.");

	private static final IntegerConfigValue maxDays = new IntegerConfigValue(
			"transitime.prediction.data.average.maxdays",
			new Integer(3),
			"Max number of historical days trips to include in average calculation.");

	private static final IntegerConfigValue maxDaysToSearch = new IntegerConfigValue(
			"transitime.prediction.data.average.maxdaystoseach",
			new Integer(21),
			"Max number of days to look back for data. This will also be effected by how old the data in the cache is.");
	
	private static final Logger logger = LoggerFactory
			.getLogger(HistoricalAveragePredictionGeneratorImpl.class);

	/* (non-Javadoc)
	 * @see org.transitime.core.predictiongenerator.KalmanPredictionGeneratorImpl#getTravelTimeForPath(org.transitime.core.Indices, org.transitime.db.structs.AvlReport)
	 */
	@Override
	public long getTravelTimeForPath(Indices indices, AvlReport avlReport) {

		TripDataHistoryCache tripCache = TripDataHistoryCache.getInstance();
		
		VehicleStateManager vehicleStateManager = VehicleStateManager
				.getInstance();

		VehicleState currentVehicleState = vehicleStateManager
				.getVehicleState(avlReport.getVehicleId());
		
		Date nearestDay = DateUtils.truncate(Calendar.getInstance()
				.getTime(), Calendar.DAY_OF_MONTH);

		List<Integer> lastDaysTimes = lastDaysTimes(tripCache,
				currentVehicleState.getTrip().getId(),
				indices.getStopPathIndex(), nearestDay,
				currentVehicleState.getTrip().getStartTime(),
				maxDaysToSearch.getValue(),
				minDays.getValue());
		/*
		 * if we have enough data start using historucal averageotherwise
		 * revert to default. This does not mean that this method of
		 * prediction is better than the default.
		 */

		if (lastDaysTimes != null
				&& lastDaysTimes.size() >= minDays.getValue()
						.intValue()) {

			logger.debug("Generating prediction based on basic historical average.");
			
			long total=0;
			
			int counter=0;
						
			for(Integer time:lastDaysTimes)
			{
				if(counter<maxDays.getValue())
				{				
					total=total+time;
					counter++;
				}
			}
			if(counter>0)
			{
				long average = total/counter;
				return average;
			}
						
		}
		/* default to transiTime method if not enough data. This will be based on schedule if UpdateTravelTimes has not been called. */
		return super.getTravelTimeForPath(indices, avlReport);
	}
	
}
