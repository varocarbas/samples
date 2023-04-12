package samples;

import java.util.HashMap;

import accessory.arrays;
import accessory.credentials;
import accessory.crypto;
import accessory.db;
import accessory.db_crypto;
import accessory.db_info;
import accessory.misc;
import accessory.paths;
import accessory.strings;

public class samples_encryption 
{	
	public static void main(String[] args) { run_encryption(); }
	
	public static void run_encryption()
	{
		samples_start.run_start();

		paths.update_dir(paths.DIR_CRYPTO, samples.PERFECT_LOCATION);

		//---
		String sample_id = "default";		
		
		if (!run_simple(sample_id)) return;
		//---

		//---		
		sample_id = "DESede";
		
		crypto.update_algo_cipher("DESede/CBC/PKCS5Padding");
		crypto.update_algo_key("DESede");
		
		if (!run_simple(sample_id)) return;		
		//---
		
		//---		
		sample_id = "credentials";
		
		paths.update_dir(paths.DIR_CREDENTIALS, samples.PERFECT_LOCATION);
		
		String username = samples.PERFECT_USERNAME;
		String password = samples.PERFECT_PASSWORD;
		
		if (!credentials.encrypt_username_password_file(samples.ID, samples.USER, username, password)) 
		{
			samples.print_error(sample_id);
			
			return;
		}
		
		HashMap<String, String> temp = credentials.get_username_password_file(samples.ID, samples.USER, true);
		if (!arrays.is_ok(temp)) 
		{
			samples.print_error(sample_id);
			
			return;
		}
		
		String[] messages = new String[] 
		{
			"path: " + credentials.get_path(samples.ID, samples.USER, true),
			"decrypted: " + strings.to_string(temp)
		};
		
		samples.print_messages(sample_id, messages);		
		//---
		
		if (samples.USE_DB) run_encryption_db();
		
		samples.print_end();
	}

	private static void run_encryption_db()
	{
		//---
		String sample_id = "db_crypto";
		
		db.create_table(db_crypto.get_source(), true);

		crypto.store_in_db();
		if (!run_simple(sample_id)) return;
		
		crypto.store_in_files();
		//---

		//---
		sample_id = "db_info";

		db.create_table(db_info.get_source(), true);
		
		HashMap<String, String> input = new HashMap<String, String>();
		input.put("enc1", "1");
		input.put("enc2", "2");
		
		if (!db_info.add(input, true, true, true)) 
		{
			samples.print_error(sample_id);
			
			return;
		}
		
		input = new HashMap<String, String>();
		input.put("plain1", "1");
		input.put("plain2", "2");
		
		if (!db_info.add(input, false, false, false))
		{
			samples.print_error(sample_id);
			
			return;
		}
		
		HashMap<String, String> encrypted = db_info.get_all(false);
		if (!arrays.is_ok(encrypted)) 
		{
			samples.print_error(sample_id);
			
			return;
		}
		
		HashMap<String, String> unencrypted = db_info.get_all(true);
		if (!arrays.is_ok(unencrypted)) 
		{
			samples.print_error(sample_id);
			
			return;
		}

		String[] messages = new String[] 
		{
			"encrypted: " + strings.to_string(encrypted),
			"unencrypted: " + strings.to_string(unencrypted)
		};
				
		samples.print_messages(sample_id, messages);
		//---	
	}
	
	private static boolean run_simple(String sample_id_)
	{
		boolean is_ok = false;
	
		String encrypted = crypto.encrypt(samples.PLACEHOLDER, samples.ID);
		String decrypted = crypto.decrypt(encrypted, samples.ID);
		
		String message = null;
		
		if (samples.PLACEHOLDER.equals(decrypted)) 
		{
			is_ok = true;
			
			message = (samples.PLACEHOLDER + misc.SEPARATOR_CONTENT + encrypted);
		}
	
		samples.print_message(sample_id_, message, is_ok);
		
		return is_ok;
	}
}
