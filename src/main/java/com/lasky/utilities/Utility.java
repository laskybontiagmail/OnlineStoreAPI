package com.lasky.utilities;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.mail.Message.RecipientType;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.springframework.data.domain.Sort.Direction;

//import com.lasky.constants.Common;
//import com.lasky.constants.GreenTreeCrm;
//import com.lasky.greentree.model.crm.Attachment;
//import com.lasky.greentree.model.crm.Attachments;
//import com.lasky.greentree.model.crm.servicerequests.CrmServiceRequest;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lasky.constants.Common;

public class Utility {
	private static Logger logger = LogManager.getLogger(Utility.class);
	private static Utility utility;
	
	private Utility() {
		
	}
	
	public static Utility getOnlyInstance() {
		if (Utility.utility == null) {
			Utility.utility = new Utility();
		}
		
		return Utility.utility;
	}
	
	public void logFormattedException(Logger logger, Exception exception) {
		if (logger != null) {
			logger.info("");
			logger.info("======================================================================");
			logger.info(exception.getMessage());
			logger.info("======================================================================");
			logger.info("");
		}
	}
	
	
	public void logFormattedExceptionStackTrace(Logger logger, Exception exception) {
		if (logger != null) {
			String stackTrace = ExceptionUtils.getStackTrace(exception);
			
			logger.info("");
			logger.info("======================================================================");
			logger.info(stackTrace);
			logger.info("======================================================================");
			logger.info("");
		}
	}
	
	@SuppressWarnings({ "unchecked" }) 
	public void logMethodSignature(Logger logger, Method method, String extraMessage, Pair<String, String>... parametersToLog) {
		if (method != null && logger != null) {
			StringBuffer stringBuffer = new StringBuffer();
			
			stringBuffer.append(method.getName() + "( ");
			
			if (this.isNotNullAndEmpty(parametersToLog)) {
				boolean firstParam = true;
				for (int index = 0; index < parametersToLog.length; index++) {
					if (!firstParam) {
						stringBuffer.append(" ,");
					} else {
						firstParam = false;
					}
					
					stringBuffer.append(parametersToLog[index].getKey() + ": ");
					stringBuffer.append(parametersToLog[index].getValue());
				}
			}
			
			stringBuffer.append(" )");
			if (this.isNotNullAndEmpty(extraMessage)) {
				stringBuffer.append(extraMessage);
			}
			logger.info(stringBuffer.toString());
		}
	}
	
	public void logMethodSignature(Logger logger, Method method, String extraMessage, List<Pair<String, String>> parametersToLog) {
		if (method != null && logger != null) {
			StringBuffer stringBuffer = new StringBuffer();
			
			stringBuffer.append(method.getName() + "( ");
			
			if (this.isNotNullAndEmpty(parametersToLog)) {
				boolean firstParam = true;
				for (int index = 0; index < parametersToLog.size(); index++) {
					if (!firstParam) {
						stringBuffer.append(" ,");
					} else {
						firstParam = false;
					}
					
					stringBuffer.append(parametersToLog.get(index).getKey() + ": ");
					stringBuffer.append(parametersToLog.get(index).getValue());
				}
			}
			
			stringBuffer.append(method.getName() + " )");
			if (this.isNotNullAndEmpty(extraMessage)) {
				stringBuffer.append(extraMessage);
			}
			logger.info(stringBuffer.toString());
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <Type> Type jsonStringToObject(String source, Class destionationClass) {
		try {
			if (source != null) {
				JsonFactory jsonFactory = new JsonFactory();
				ObjectMapper mapper = new ObjectMapper(jsonFactory);

				try {
					return (Type) mapper.readValue(source, destionationClass);
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}
	
	public <Type> String toJsonString(Type sourceObject) {
		JsonFactory jsonFactory = new JsonFactory();
		ObjectMapper mapper = new ObjectMapper(jsonFactory);
		try {
			return mapper.writeValueAsString(sourceObject);
		} catch (JsonProcessingException jpexc) {
			jpexc.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Make ValueType == String first, if value cannot not guaranteed to be convertible to string
	 * @param parameterList
	 * @param key
	 * @param value
	 */
	public <ValueType> void addToParameterList(List<Pair<String, String>> parameterList, String key, ValueType value) {
		if (parameterList != null && value != null && this.isNotNullAndEmpty(key)) {
			String valueString = "" + value;
			parameterList.add(Pair.<String, String>of(key, valueString));
		}
	}
	
	public boolean isEmptyOrNull(String string) {
		return (string == null || string.equals(""));
	}
	
	public boolean isEmptyOrNullIgnoreTrailingWhiteSpaces(String string) {
		boolean result = false;
		
		result = this.isEmptyOrNull(string);
		if (!result) {
			result = this.isEmptyOrNull(string.trim());
		}
		
		return result;
	}
	
	public boolean isNotNullAndEmpty(String string) {
		return (string != null && !string.equals(""));
	}
	
	public <AnyType> boolean isNotNullAndEmpty(Collection<AnyType> collection) {
		return (collection != null && !collection.isEmpty());
	}
	
	public <AnyType> boolean isNotNullAndEmpty(AnyType[] array) {
		return (array != null && array.length > 0);
	}
	
	public String getDateAndTimeInString() {
		String dateAndTime = "";
		
		Date date = new Date();
		dateAndTime = date.toString();
		
		return dateAndTime;
	}
	
	public String dateToStringWithoutTimeZone(Date date, String dateTimeFormat) {
		String convertedValue = null;
		
		if (date != null) {
			if (!this.isNotNullAndEmpty(dateTimeFormat)) {
				dateTimeFormat = Common.StringConstants.DateTimeWithoutTimeZoneStringFormat.value();
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateTimeFormat);
		    convertedValue = simpleDateFormat.format(date);
		}
		
		return convertedValue;
	}
	
	public Date convertDateValue(Date date, String timeZoneId) {
		Date convertedDate = null;
		
		if (date != null && this.isNotNullAndEmpty(timeZoneId)) {
			String dateTimeFormat = Common.StringConstants.DateTimeWithoutTimeZoneStringFormat.value();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateTimeFormat);
			String justTheValue = this.dateToStringWithoutTimeZone(date, dateTimeFormat);
			
			simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZoneId));
			try {
				convertedDate = simpleDateFormat.parse(justTheValue);
			} catch (Exception exception) {
				logger.error("Error converting date: " + date.toString() );
			}
		}
		
		return convertedDate;
	}
	
	//will return negative time values
	public long getTimeDifferenceInMilliseconds(Date timeBefore, Date timeAfter) {
		long timeDifference = 0;
		
		if (timeBefore != null && timeAfter != null) {
			long millisBefore = timeBefore.getTime();
			long millisAfter = timeAfter.getTime();
			
			timeDifference = millisAfter - millisBefore;
		}
		
		return timeDifference;
	}
	
	//will return negative time values
	public double getTimeDifferenceInSeconds(Date timeBefore, Date timeAfter) {
		double timeDifference = 0;
		double timeDifferenceInMillis = this.getTimeDifferenceInMilliseconds(timeBefore, timeAfter);
		
		if (timeDifferenceInMillis != 0) {
			timeDifference = timeDifferenceInMillis / Common.IntegerConstants.MillisecondsInASecond.value();
		}
		
		return timeDifference;
	}
	
	public double getTimeDifferenceInMinutes(Date timeBefore, Date timeAfter) {
		double timeDifference = 0;
		double timeDifferenceInSeconds = this.getTimeDifferenceInSeconds(timeBefore, timeAfter);
		
		if (timeDifferenceInSeconds != 0) {
			timeDifference = timeDifferenceInSeconds / Common.IntegerConstants.SecondsInAMinute.value();
		}
		
		return timeDifference;
	}
	
	public String generatePasswordDigest(String username, String password, Date date) {
		String passwordDigest = "";
		
		String encryptedPassword = generateSHA1(generateMD5(password));
		String created = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ")).format(date);
		String nonce = convertByteArrayToHexString( (generateSHA1(created + generateNonce(16))).getBytes());
		passwordDigest = Base64.getEncoder().encodeToString(generateSHA1(nonce + created + encryptedPassword).getBytes());
		
		return passwordDigest;
	}
	
	/**
	 * Coversion of byte array to hex string.
	 * @param arrayBytes - array of bytes
	 * @return {String}
	 */
	public String generateNonce(int length) {
		String nonceChars = "0123456789abcdef";
		StringBuffer stringBuffer = new StringBuffer();
		for (int ctr = 0; ctr < length; ctr++) {
			stringBuffer.append(nonceChars.charAt((int) (Math.floor(Math.random() * nonceChars.length())))); 
		}
		return stringBuffer.toString();
		//SecureRandom random = new SecureRandom();
		//return new BigInteger(130, random).toString(16);
		
//		byte[] nonce = new byte[16];
//		Random rand;
//		rand = SecureRandom.getInstance ("SHA1PRNG");
//		rand.getBytes (nonce);
	}
	
	/**
	 * Generate sha1.
	 * @param message - message
	 * @return {String} sha1
	 */
	public String generateSHA1(String message) {
		return hashString(message, "SHA-1");
	}
	
	/**
	 * Generate md5.
	 * @param message - message
	 * @return {String} md5
	 */
	public String generateMD5(String message) {
		return hashString(message, "MD5");
	}

	/**
	 * Generate sha256.
	 * @param message - message
	 * @return {String} sha256
	 */
	public String generateSHA256(String message) {
		return hashString(message, "SHA-256");
	}

	/**
	 * Generate a hash string.
	 * @param message - message
	 * @param algorithm - algorithm
	 * @return {String}
	 */
	private String hashString(String message, String algorithm) {

		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));

			return convertByteArrayToHexString(hashedBytes);
		} catch (Exception exc) {
			//logger.error(exc);
		}

		return null;
	}
	
	/**
	 * Coversion of byte array to hex string.
	 * @param arrayBytes - array of bytes
	 * @return {String}
	 */
	private String convertByteArrayToHexString(byte[] arrayBytes) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < arrayBytes.length; i++) {
			stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return stringBuffer.toString();
	}
	
	/**
	 * This function is required by GreenTree, for some reason they don't handle the CR/LF well
	 * @param multilineString
	 * @return
	 */
	public String convertLineFeedOrCarriageReturnToHtmlVersion(String multilineString) {
		String convertedString = null;
		
		if (this.isNotNullAndEmpty(multilineString)) {
			StringBuffer stringBuffer = new StringBuffer();
				
			//	// thanks to this link: https://gist.github.com/mathiasbynens/1243213
			//	// the following works
			//	// stringBuffer.append('\12'); //equivalent to &#x0a; or '\n'
			//	// stringBuffer.append('\15'); //equivalent to &#x0d; or '\r'
			
			char theChar;
			for (int index = 0; index < multilineString.length(); index++) {
				theChar = multilineString.charAt(index);
				if (theChar == '\n') {
					stringBuffer.append('\15');
				} else if (theChar == '\r') {
					//skip and do nothing
					continue;
				} else {
					stringBuffer.append(theChar);
				}
			}
			
			convertedString = stringBuffer.toString();
		}
		
		return convertedString;
	}
	
	public boolean saveFileBytesViaNio(byte[] fileBytes, String fullFileName) {
		boolean result = false;
        try {
            Path path = Paths.get(fullFileName);
            Files.write(path, fileBytes);
            result = true;
        } catch (IOException ioException) {
        	this.logFormattedException(logger, ioException);
        }
        
        return result;
    }
	
//	public Direction getSortDirection(String direction) {
//		Direction sortDirection = null;
//		
//		if (this.isEmptyOrNullIgnoreTrailingWhiteSpaces(direction)) {
//			switch (direction.toLowerCase()) {
//				case "ascending":
//				case "asc":
//				case "ascend":
//					sortDirection = Direction.
//					break;
//				case "descending":
//				case "desc":
//				case "descend":	
//					break;
//				default:
//					break;
//			}
//		}
//		
//		return sortDirection;
//	}
	
}




