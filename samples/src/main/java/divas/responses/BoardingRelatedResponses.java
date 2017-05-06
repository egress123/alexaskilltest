package divas.responses;

import java.time.Duration;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;

public class BoardingRelatedResponses {
	/**
	 * The Boarding Time slot.
	 */
	private static final String SLOT_BOARDING_TIME_REMINDER_DURATION = "BoardingTimeReminderDuration";

	public SpeechletResponse getUpgradeListResponse() {
		String speechText = "There are 3 seats remaining in First Class.  You are 4th out of 23 people on the upgrade list.  Good luck!";

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("Upgrade List Display");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		return SpeechletResponse.newTellResponse(speech, card);
	}

	public SpeechletResponse getBoardingTimeReminder(final Intent intent, final Session session) {
		String speechText1 = "Sure.  I will remind you ";
		String speechText2 = " minutes before the boarding time";

		Slot boardingTimeReminderDuration = intent.getSlot(SLOT_BOARDING_TIME_REMINDER_DURATION);
		String boardingTimeStr = boardingTimeReminderDuration.getValue();
		Duration boardingTimeDuration = Duration.parse(boardingTimeStr);

		StringBuffer speechTextBuffer = new StringBuffer();
		speechTextBuffer.append(speechText1);
		speechTextBuffer.append(boardingTimeDuration.getSeconds() / 60);
		speechTextBuffer.append(speechText2);

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("Boarding Time Reminder Display");
		card.setContent(speechTextBuffer.toString());

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechTextBuffer.toString());

		// return SpeechletResponse.newTellResponse(speech, card);
		// // Create the Simple card content.
		// SimpleCard card = new SimpleCard();
		// card.setTitle("Boarding Time Reminder");
		// card.setContent(speechText1);
		//
		// // Create the plain text output.
		// PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		// speech.setText(speechText1);
		//
		return SpeechletResponse.newTellResponse(speech, card);
	}
	
	public SpeechletResponse getWheelchariResponse() {
		String speechText = "Sure.  I will add wheel chair request to your trip";

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("wheelchair requested");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		return SpeechletResponse.newTellResponse(speech, card);
	}
}
