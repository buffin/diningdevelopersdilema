package org.diningdevelopers.util;

import java.util.Calendar;
import java.util.Date;


public class DateHelper {
	
	public Date getDateForTodayWithNulledHoursMinutesAndMiliseconds() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

}
