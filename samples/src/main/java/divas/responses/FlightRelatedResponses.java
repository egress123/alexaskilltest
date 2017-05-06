package divas.responses;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;

public class FlightRelatedResponses {
	private static final String SLOT_DELTA_FLIGHT_NUMBER = "DeltaFlightNumber";
	public SpeechletResponse getFlightStatus(final Intent intent, final Session session) {
		Slot deltaFlightNumber = intent.getSlot(SLOT_DELTA_FLIGHT_NUMBER);
		String deltaFlightNumberString = deltaFlightNumber.getValue();
		String speechText = "Your flight is on time";
		
		if ("1105".equals(deltaFlightNumberString)) {
			speechText = "Delta flight " +deltaFlightNumberString+ " is scheduled to depart Seattle at 11:16PM on time";
		} else if("1205".equals(deltaFlightNumberString)){
			speechText = "Delta flight " +deltaFlightNumberString+ " arriving Los Angeles is currently delayed.  The estimated arrival time is 5:23pm.";
		} else if("1305".equals(deltaFlightNumberString)){
			speechText = "Delta flight " +deltaFlightNumberString+ " arrived Boston at 6:33pm.";
		} else {
		    speechText = "Delta Flight is on time.";	
		}
		
		//In Route response: "Delta flight 1231 is currently delayed.  The estimated arrival time is at 5:23pm."
		
		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("Your Trip Information");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		return SpeechletResponse.newTellResponse(speech, card);
	}
}
