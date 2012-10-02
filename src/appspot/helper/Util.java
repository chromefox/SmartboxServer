package appspot.helper;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

public class Util {

	public static StringBuilder parseJSON(HttpServletRequest request) throws IOException {
		StringBuilder sb = new StringBuilder();
		String s;
		while ((s = request.getReader().readLine()) != null) {
			sb.append(s);
		}
		return sb;
	}
}
