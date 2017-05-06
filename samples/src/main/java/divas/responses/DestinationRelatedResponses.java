package divas.responses;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;

import divas.DivasUtility;

public class DestinationRelatedResponses {

	public SpeechletResponse getPlanMyTripResponse() {
		String speechText = "You are going from MSP to MIA on May 15. This would be fun vacation!  With a flight, Disney Resort hotel and Magic Your Way Ticket package, you’ll receive a free Disney Dining Plan per booking. You can relax with your family, knowing your vacation meals are paid for in advance";

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("Your Trip Information");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		return SpeechletResponse.newTellResponse(speech, card);
	}

	public SpeechletResponse getOrderTransportation() {
		String speechText = "Would you like to order UBER ride?";
		String repromptText = "You can use my application to order UBER ride.  Would you like me to do that?";

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("Shall I order UBER");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		
		return SpeechletResponse.newTellResponse(speech);
		// return DivasUtility.newAskResponse(speechText,false,
		// repromptText,false);
	}
}
