package gmbh.gil;

import java.io.Serializable;

import org.apache.wicket.util.cookies.CookieUtils;

public class LastUserIDValue implements Serializable {
	private static final long serialVersionUID = 5007638819051517903L;

	public String getSessionId() {
		  return org.apache.wicket.Session.get().getId();
	 }
	 
//	 WebResponse webResponse = (WebResponse)RequestCycle.get().getResponse(); 		might be needed for Cookies
	 
	 public final String CookieUtils() {
		 CookieUtils cookie = new CookieUtils();		
		 cookie.save("TestCookie", getSessionId());
		 return null;
	 }
	 
	 public final String AskCookie() {
		 CookieUtils askCookie = new CookieUtils();
		 return askCookie.load("TestCookie");
	 }
	 
	 public final void DeleteCookie() {
		 CookieUtils deleteCookie = new CookieUtils();
		 deleteCookie.remove("TestCookie");
	 }
	 
	 public final void DeleteSessionCookie() {
		 CookieUtils deleteSessionCookie = new CookieUtils();
		 deleteSessionCookie.remove("JSESSIONID");
	 }
}
