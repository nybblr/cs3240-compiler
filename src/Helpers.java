public class Helpers {
	public static final int PRINTSTART = 32;
	public static final int PRINTEND   = 127;
	public static final int PRINTSIZE  = PRINTEND - PRINTSTART;
	
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
	
	public static char[] printable() {
		char[] p = new char[PRINTSIZE];
		
		// Printable is from 32 to 126, inclusive
		for (int i = PRINTSTART; i < PRINTEND; i++) {
			p[i - PRINTSTART] = (char)i;
		}
		
		return p;
	}
}
