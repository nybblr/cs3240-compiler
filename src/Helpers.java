
public class Helpers {
	public static String niceCharToString(Character c) {
		String string;
		if (c == null)
			string = "eps";
		else
			string = c.toString();
		// Non printable?
		if (!string.equals(string.replaceAll("\\p{C}", "?"))) {
			string = "0x"+Integer.toHexString((int)string.charAt(0)).toUpperCase();
		}
		return string;
	}
}
