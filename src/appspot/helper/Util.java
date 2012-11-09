package appspot.helper;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

public class Util {

	public static StringBuilder parseJSON(HttpServletRequest request)
			throws IOException {
		StringBuilder sb = new StringBuilder();
		String s;
		while ((s = request.getReader().readLine()) != null) {
			sb.append(s);
		}
		return sb;
	}

	/**
	 * Used for parsing through Singapore mobile number
	 * @param rawNumber raw phone number
	 * @return
	 */
	public static String parseMobileNumber(String rawNumber) {
		String parsedNumber = rawNumber.replaceAll("\\s", "");
		// Basic transformation
		if (parsedNumber.length() > 8) {
			parsedNumber = parsedNumber.substring(3);
		}
		return parsedNumber;
	}
}
