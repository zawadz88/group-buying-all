package pl.edu.pw.eiti.groupbuying.web.admin.util;


public class StringUtils {
	
	public String wrapNullInEmpty( String str ) {
		
		if ( str == null ) {
			return "";
		}
		
		return str;
		
	}
	
	public static boolean isEmpty( String str ) {
		if ( str == null || str.length() == 0 ) {
			return true;
		}
		
		return false;
	}
	
}
