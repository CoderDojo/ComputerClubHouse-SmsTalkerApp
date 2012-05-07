/**
 * 
 */
package org.coderdojo.giftoftheirish;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.gsm.SmsMessage;

/**
 * SmsWatcher
 * 
 * The receiver who lisens for the Sms Messages to land on your phone
 * 
 * @author Noel King
 *
 */
public class SmsWatcher extends BroadcastReceiver {

	 
	/**
	 * When a text message is received is builds the SMS text and the number
	 * SMS was from, it then sends this information to the TalkActivity for
	 * speech functionality.
	 * 
	 */
    @Override
    public void onReceive(Context context, Intent intent) 
    {
        //---get the SMS message passed in---
        final Bundle bundle = intent.getExtras();        
        SmsMessage[] msgs = null;
        String message = "";   
        String number = "";
        
        
        //must be a bundle of information available
        if (bundle != null)
        {
            //---retrieve the SMS message received---
            final Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];            
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
                number += "SMS from " + msgs[i].getOriginatingAddress();                     
                
                message += msgs[i].getMessageBody().toString();
            }
            
            //send the txt message to Talk activity for talk
            final Intent myIntent = new Intent(context, TalkActivity.class);
            myIntent.putExtra("smsNumber", number);
            myIntent.putExtra("smsText", message);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(myIntent);
        }                         
    }
}