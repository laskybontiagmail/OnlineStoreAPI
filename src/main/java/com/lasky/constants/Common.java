package com.lasky.constants;

public class Common {
	//just this class for now
	public static enum StringConstants {
		UrlStartOfQueryCharacter("?")
		,UrlParameterDelimiter("&")
		,UrlParameterAssignmentOperator("=")
		,DomainNameSeparator(".")
		,LDAPSearchAttributesFilterUsernameVariable("%userName%")
		,LDAPSearchAttributesFilter("(&(objectCategory=person)(objectClass=user)(SAMAccountName=" + LDAPSearchAttributesFilterUsernameVariable.value() + "))")
		,LDAPSearchDelimiter(":")
		,EmailAtSignDelimeter("@")
		,DateTimeWithoutTimeZoneStringFormat("yyyy-MM-dd HH:mm:ss.SSS")
		,UTCTimeZoneId("UTC")
		,AucklandNewZealandTimeZoneId("Pacific/Auckland")
//		,CardinalWindowsDomain("cfd.co.nz")
//		,UserLogInActive("active")
//		,UserLogInExpired("expired")
//		,EmailRecipientTo("to")
//		,EmailRecipientCc("cc")
//		,EmailRecipientBcc("bcc")
//		,EmailEmbeddedLogoName("cardi_logo")
//		,SettingsLiteNameExternalEmailAllowed("Allow External Emails")
//		,SettingsLiteTypeExternalEmailAllowed("boolean")
//		,InternalEmailDNSStringConstantName("Internal Email DNS")
		;
		
		private String value;
		
		private StringConstants(String value) {
			this.value = value;
		}
		
		public String value() {
			return this.value;
		}
		
		
	}
	
	public static enum IntegerConstants {
		MaxUserSessionTimeInSeconds(3 * 60 * 60), //3 hours
		//MaxUserSessionTimeInSeconds(30 * 60), //30 minutes
		//MaxUserSessionTimeInSeconds(10), //10 seconds
		//MaxUserSessionTimeInSeconds(5 * 60), //5 minutes
		//MaxUserSessionTimeInSeconds(2 * 60), //2 minutes
		MillisecondsInASecond(1000),
		SecondsInAMinute(60),
		MaxPageSize(1000),
		PageOne(1),
		SystemDaemonRegularIntervalMillisTime(2 * 60 * 1000), //2 minutes
		//SystemDaemonRegularIntervalMillisTime(30 * 1000), //30 seconds
		//SystemDaemonRegularIntervalMillisTime(10 * 1000), //30 seconds
		//CacheDaemonFetchInterval(1), //30 seconds
		//CacheDaemonCleanInterval(5), //2.5 minutes
		LogHttpRequestsToGreenTree(0),
		One(1);
		
		private Integer value;
		
		private IntegerConstants(Integer value) {
			this.value = value;
		}
		
		public Integer value() {
			return value;
		}
	}
	
	public static enum BooleanConstants {
		CRMIncludeAttachmentTrue(true);
		
		private Boolean value;
		
		private BooleanConstants(Boolean value) {
			this.value = value;
		}
		
		public Boolean value() {
			return value;
		}
	}
}

