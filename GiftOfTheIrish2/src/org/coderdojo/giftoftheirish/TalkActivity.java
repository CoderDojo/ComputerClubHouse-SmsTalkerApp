/**
 * 
 */
package org.coderdojo.giftoftheirish;

import org.coderdojo.giftoftheirish.application.GiftOfTheIrishApplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.PhoneLookup;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;

/**
 * TalkActivity
 * 
 * This activity is handles the talking when an SMS arrives
 * 
 * @author Noel King
 *
 */
public class TalkActivity extends Activity  implements OnInitListener {

	private TextToSpeech tts;
	static final int TTS_CHECK_CODE = 0;
	
	/** Application context used to hold application state */
	private GiftOfTheIrishApplication application;
	
	private String wordsToTalk = null;
	private String sender = null;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //set the sms text
        final Bundle extras = getIntent().getExtras();
		if (extras != null) {
			wordsToTalk = extras.getString("smsText");
			sender = getContactName(extras.getString("smsNumber"));;
		}
		
		application = (GiftOfTheIrishApplication)getApplicationContext();
        
        tts = new TextToSpeech(this, this);
    }
    
    /**
     * Gets the contact name for the phone number
     * 
     * @param phoneNumber - looks for this in phone book
     * @return Name or phone number if no name found
     */
    private String getContactName(String phoneNumber) {
    	
    	//setup to find the contacts from the phone book
    	final Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
    	final Cursor cursor = getApplicationContext().getContentResolver().query(uri, new String[]{PhoneLookup.DISPLAY_NAME},null,null,null);
    	
    	//if values found then return the name
    	if (cursor.moveToFirst()) {
    		final String contactName = cursor.getString(0);
    		return contactName;
     	}
    	return phoneNumber;
    }
 
    /**
     * Init method to insure we can talk
     */
	@Override
	public void onInit(int initStatus) {
		
		//can only speak if speach setup and application set to read out loud
		if (initStatus == TextToSpeech.SUCCESS && application.isReadSmsOutLoud())
		{
			speak(" Text message from "  + sender + " says " + wordsToTalk);
		}
		
		//always go back to start whether it talks or not
		goBackToStart();
	}
	
	/**  
	 * Goes back to the start screen
	 */
	private void goBackToStart() {
		 final Intent myIntent = new Intent(this, GiftOfTheIrish2Activity.class);
         myIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
         startActivity(myIntent);
	}
	
	/**
	 * Talks out loud
	 * @param textToTalk
	 */
	private void speak(String textToTalk) {
			tts.speak(textToTalk, TextToSpeech.QUEUE_FLUSH, null);
	}
}
