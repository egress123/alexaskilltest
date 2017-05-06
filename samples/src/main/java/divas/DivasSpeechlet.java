/**
    Copyright 2014-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.

    Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with the License. A copy of the License is located at

        http://aws.amazon.com/apache2.0/

    or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package divas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

import divas.responses.BoardingRelatedResponses;
import divas.responses.ConnectionRelatedResponses;
import divas.responses.DestinationRelatedResponses;
import divas.responses.FlightRelatedResponses;

/**
 * This sample shows how to create a simple speechlet for handling speechlet
 * requests.
 */
public class DivasSpeechlet implements Speechlet {
	private static final Logger log = LoggerFactory.getLogger(DivasSpeechlet.class);

	@Override
	public void onSessionStarted(final SessionStartedRequest request, final Session session) throws SpeechletException {
		log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
		// any initialization logic goes here
	}

	@Override
	public SpeechletResponse onLaunch(final LaunchRequest request, final Session session) throws SpeechletException {
		log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
		return getWelcomeResponse();
	}

	@Override
	public SpeechletResponse onIntent(final IntentRequest request, final Session session) throws SpeechletException {
		log.info("onIntent requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());

		Intent intent = request.getIntent();
		String intentName = (intent != null) ? intent.getName() : null;

		if ("HelloWorldIntent".equals(intentName)) {
			return getHelloResponse();
		} else if ("AMAZON.HelpIntent".equals(intentName)) {
			return getHelpResponse();
		} else if ("UpgradeListIntent".equals(intentName)) {
			// return getWaitListResponse();
			return new BoardingRelatedResponses().getUpgradeListResponse();
		} else if ("BoardingTimeReminderIntent".equals(intentName)) {
			return new BoardingRelatedResponses().getBoardingTimeReminder(intent, session);
		} else if ("PlanMyTripIntent".equals(intentName)) {
			return new DestinationRelatedResponses().getPlanMyTripResponse();
		} else if ("FlightStatusIntent".equals(intentName)) {
			return getFlightStatusResponse();
		} else if ("TSAStatusIntent".equals(intentName)) {
			return getTsaStatusResponse(intent, session);
		} else if ("OrderRideIntent".equals(intentName)){
			return new DestinationRelatedResponses().getOrderTransportation();
		} else if("SSRIntent".equals(intentName)){
			return new BoardingRelatedResponses().getWheelchariResponse();
		} else if("TrackFlightIntent".equals(intentName)) {
			return new FlightRelatedResponses().getFlightStatus(intent, session);
		} else if("TrackConnectingFlightIntent".equals(intentName)){
			return new ConnectionRelatedResponses().getConnectingFlightDetailResponse();
		} else {
			throw new SpeechletException("Invalid Intent");
		}
	}

	@Override
	public void onSessionEnded(final SessionEndedRequest request, final Session session) throws SpeechletException {
		log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
		// any cleanup logic goes here
	}

	/**
	 * Creates and returns a {@code SpeechletResponse} with a welcome message.
	 *
	 * @return SpeechletResponse spoken and visual response for the given intent
	 */
	private SpeechletResponse getWelcomeResponse() {
		String speechText = "Welcome to the Delta Lina, an interactive Delta Air Lines Voice Assistance Program.  How may I help you?";

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("HelloWorld");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		// Create reprompt
		Reprompt reprompt = new Reprompt();
		reprompt.setOutputSpeech(speech);

		return SpeechletResponse.newAskResponse(speech, reprompt, card);
	}

	/**
	 * Creates a {@code SpeechletResponse} for the hello intent.
	 *
	 * @return SpeechletResponse spoken and visual response for the given intent
	 */
	private SpeechletResponse getHelloResponse() {
		String speechText = "Hello, Michael, Brent, Christoph, and Barry";

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("Welcome to Delta Lina");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		return SpeechletResponse.newTellResponse(speech, card);
	}

	//
	// private SpeechletResponse getWaitListResponse(){
	// String speechText = "There are 20 people in the upgrade list. You are on
	// number 3 in the upgrade list";
	//
	// // Create the Simple card content.
	// SimpleCard card = new SimpleCard();
	// card.setTitle("Wait List Display");
	// card.setContent(speechText);
	//
	// // Create the plain text output.
	// PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
	// speech.setText(speechText);
	//
	// return SpeechletResponse.newTellResponse(speech, card);
	// }
	//
	private SpeechletResponse getConnectionInfoResponse() {
		String speechText = "Your next flight 3301 has a departure time of 3:10pm, 10 minutes later than scheduled.  Your departure gate is C10.";

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("Connection Information");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		return SpeechletResponse.newTellResponse(speech, card);
	}

	// ================Christophe=======================
	

	private SpeechletResponse getFlightStatusResponse() {
		String speechText = "Your flight DL3301 From MSP is scheduled On Time. Departure City Minneapolis/St Paul (MSP) Terminal 1. Scheduled at 10:05PM, May 05. Estimated On Time. Arrival City Green Bay, WI (GRB). Scheduled at 11:07PM, May 05. Estimated On Time.";
		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("Your Flight Information");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		return SpeechletResponse.newTellResponse(speech, card);
	}

	private SpeechletResponse getTsaStatusResponse(final Intent intent, final Session session) {
		String speechText1 = "TSA Security wait times is approximately 35 minutes. 5 minutes for TSA Pre-Check";

		SimpleCard card = new SimpleCard();
		card.setTitle("TSA security line information");
		card.setContent(speechText1);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText1);

		return SpeechletResponse.newTellResponse(speech, card);
	}

	//
	// private SpeechletResponse getBoardingTimeReminder(final Intent intent,
	// final Session session){
	// String speechText1 = "Sure. I will remind you ";
	// String speechText2 = " minutes before the boarding time";
	//
	// Slot boardingTimeReminderDuration =
	// intent.getSlot(SLOT_BOARDING_TIME_REMINDER_DURATION);
	// String boardingTimeStr = boardingTimeReminderDuration.getValue();
	// Duration boardingTimeDuration = Duration.parse(boardingTimeStr);
	//
	// StringBuffer speechTextBuffer = new StringBuffer();
	// speechTextBuffer.append(speechText1);
	// speechTextBuffer.append(boardingTimeDuration.getSeconds()/60);
	// speechTextBuffer.append(speechText2);
	//
	// // Create the Simple card content.
	// SimpleCard card = new SimpleCard();
	// card.setTitle("Boarding Time Reminder Display");
	// card.setContent(speechTextBuffer.toString());
	//
	// // Create the plain text output.
	// PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
	// speech.setText(speechTextBuffer.toString());
	//
	//// return SpeechletResponse.newTellResponse(speech, card);
	//// // Create the Simple card content.
	//// SimpleCard card = new SimpleCard();
	//// card.setTitle("Boarding Time Reminder");
	//// card.setContent(speechText1);
	////
	//// // Create the plain text output.
	//// PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
	//// speech.setText(speechText1);
	////
	// return SpeechletResponse.newTellResponse(speech, card);
	// }

	/**
	 * Creates a {@code SpeechletResponse} for the help intent.
	 *
	 * @return SpeechletResponse spoken and visual response for the given intent
	 */
	private SpeechletResponse getHelpResponse() {
		String speechText = "You can say hello to me, Divas!";

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("HelloWorld");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		// Create reprompt
		Reprompt reprompt = new Reprompt();
		reprompt.setOutputSpeech(speech);

		return SpeechletResponse.newAskResponse(speech, reprompt, card);
	}
}
