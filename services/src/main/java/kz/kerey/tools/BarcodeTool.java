package kz.kerey.tools;

import java.util.logging.Logger;

public abstract class BarcodeTool {

	final public static int barcodeMaxLength = 12;
	final public static int barcodeMinLength = 12;
	final public static String barcodeType = "EAN13";
	
	final private static Logger logger = Logger.getLogger(BarcodeTool.class.getName());
	
	public static String getBarcodeValue(Long number) {
		if (number==null) return null;
		String result = String.valueOf(number);
		while (result.length() < 12)
			result = "0" + result;
		return result;
	}
	
	public static Long getBarcodeNumber(String barcode) {
		try {
			Long result = Long.valueOf(barcode);
			return result;
		}
		catch (Exception ex) {
			logger.severe("Barcode identification error");
			ex.printStackTrace();
		}
		return null;
	}
	
}