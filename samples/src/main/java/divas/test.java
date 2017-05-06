package divas;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import com.amazon.speech.slu.Slot;

public class test {

	public static void main(String[] args) {
		
    	Duration boardingTimeDuration = Duration.parse("PT10M");
    	
    	System.out.println(boardingTimeDuration.getSeconds()/60);
    	
	}

}
