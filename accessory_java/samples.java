package samples;

import accessory.generic;
import accessory.misc;

public abstract class samples
{
	public static final String APP = "whatevs";
	public static final String ID = "whichevs";
	public static final String USER = "whoevs";
	public static final String PLACEHOLDER = "wherevs";
	
	//Set this variable to true only if there is a valid DB setup in place (i.e., MySQL connector, valid DB name and valid credentials).
	public static final boolean USE_DB = false;
	
	//--- I would input my own values where these constants are used if I were you.
	public static final String PERFECT_NAME = PLACEHOLDER;
	public static final String PERFECT_LOCATION = PLACEHOLDER;
	public static final String PERFECT_USERNAME = PLACEHOLDER;
	public static final String PERFECT_PASSWORD = PLACEHOLDER;
	//---

	public static void print_error(String sample_id_) { print_message(sample_id_, null, false); }
	
	public static void print_message(String sample_id_, String message_, boolean is_ok_)
	{
		String message = sample_id_ + misc.SEPARATOR_CONTENT;
		
		if (is_ok_) message += message_; 
		else message += "ERROR";
		
		generic.to_screen(message);
	}
	
	public static void print_messages(String sample_id_, String[] messages_)
	{
		String message = sample_id_;

		for (String message2: messages_) { message += misc.SEPARATOR_CONTENT + message2; }
		
		generic.to_screen(message);
	}
	
	public static void print_end() { generic.to_screen("sample code completed successfully"); }
}