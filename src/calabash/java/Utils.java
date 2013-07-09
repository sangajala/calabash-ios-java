/**
 * 
 */
package calabash.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 *
 */
public final class Utils {

	public static String getStringFromJSON(JSONObject target, String key) {
		try {
			return target.getString(key);
		} catch (JSONException e) {
			return null;
		}
	}

	public static Integer getIntFromJSON(JSONObject target, String key) {
		try {
			return target.getInt(key);
		} catch (JSONException e) {
			return null;
		}
	}

	public static String loadPlaybackData(String recordingName)
			throws CalabashException {
		String os = Config.getOS();
		if (os == null) {
			os = "ios"
					+ CalabashRunner.getServerVersion().getiOSVersion().major();
		}

		File file = getEventFile(recordingName, os);
		if (!file.exists() && "ios6".equals(os))
			file = getEventFile(recordingName, "ios5");

		if (!file.exists())
			throw new CalabashException(String.format(
					"Can't load playback data. %s does not exists",
					file.getAbsolutePath()));

		try {
			return readFileAsString(file);
		} catch (IOException e) {
			throw new CalabashException(String.format(
					"Can't load playback data from %s. %s",
					file.getAbsolutePath(), e.getMessage()), e);
		}
	}

	private static File getEventFile(String recordingName, String os) {
		// TODO: Remove hardcoded paths
		File eventsDir = new File(
				"/Users/navaneeth/projects/calabash/calabash-ios-java/events");
		return new File(eventsDir, String.format("%s_%s_%s.base64",
				recordingName, os, Config.getDevice()));
	}

	private static String readFileAsString(File f) throws IOException {
		if (!f.exists())
			return null;

		FileInputStream stream = new FileInputStream(f);
		byte[] b = new byte[stream.available()];
		stream.read(b);
		stream.close();

		return new String(b, "UTF-8");
	}

}