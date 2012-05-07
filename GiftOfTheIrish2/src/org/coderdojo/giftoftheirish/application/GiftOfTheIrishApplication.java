/**
 * 
 */
package org.coderdojo.giftoftheirish.application;

import android.app.Application;

/**
 * GiftOfTheIrishApplication.java
 * 
 * This class provides talk state for the application, enabling a global value for whether to talk or now.  Each activtiy
 * has access to the single instance of this class.
 * 
 * To find out is the SMS should be read isReadSmsOutLoud()
 * 
 * @author Noel King
 *
 */
public class GiftOfTheIrishApplication extends Application {
	
	/** Should the application read the Sms Out Loud */
	private boolean readSmsOutLoud = true;

	/**
	 * 
	 * Should the SMS be read
	 * 
	 * @return the readSmsOutLoud
	 */
	public boolean isReadSmsOutLoud() {
		return readSmsOutLoud;
	}

	/**
	 * Set the value for the SMS to be read
	 * 
	 * @param readSmsOutLoud true reads out text, false stops it
	 */
	public void setReadSmsOutLoud(boolean readSmsOutLoud) {
		this.readSmsOutLoud = readSmsOutLoud;
	}

	
	
}
