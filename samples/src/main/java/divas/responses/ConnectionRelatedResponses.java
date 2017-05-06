package divas.responses;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;

public class ConnectionRelatedResponses {

	public SpeechletResponse getConnectingFlightDetailResponse() {
		String speechText = "Your next flight 1322 has a departure time of 3:10pm, 10 minutes later than scheduled.  The departing gate is at C10.  The boarding starts in 10 minutes.  So stay close!";

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("connecting flight info display");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		return SpeechletResponse.newTellResponse(speech, card);
	}
}
