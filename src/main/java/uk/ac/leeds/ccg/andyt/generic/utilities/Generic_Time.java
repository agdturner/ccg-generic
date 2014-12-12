/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.andyt.generic.utilities;

/**
 *
 * @author geoagdt
 */
public class Generic_Time {
    public static final int SecondsInMinute = 60;
    public static final int MinutesInHour = 60;
    public static final int SecondsInHour = SecondsInMinute * MinutesInHour;
    public static final int MilliSecondsInSecond = 1000;
    public static final int MilliSecondsInHour = MilliSecondsInSecond * SecondsInHour;
    
    public static void printTime(long millis) {
        int MilliSecondsInDay = 24 * MilliSecondsInHour;
        int MilliSecondsInMinute = 60000; 
        long millis2 = millis;
        int days = 0;
        while (millis2 > MilliSecondsInDay) {
            days ++;
            millis2 -= MilliSecondsInDay;
        }
        int hours = 0;
        while (millis2 > MilliSecondsInHour) {
            hours ++;
            millis2 -= MilliSecondsInHour;
        }
        int minutes = 0;
        while (millis2 > MilliSecondsInMinute) {
            minutes ++;
            millis2 -= MilliSecondsInMinute;
        }
        int seconds = 0;
        while (millis2 > 1000) {
            seconds ++;
            millis2 -= MilliSecondsInSecond;            
        }
        System.out.println("" + days + " day(s) " +
                hours + " hour(s) " +
                minutes + " minute(s) " +
                seconds + " second(s) " +
                millis2 + " millisecond(s)");
    }
}
