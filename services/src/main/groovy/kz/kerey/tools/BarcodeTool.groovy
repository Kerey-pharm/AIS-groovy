package kz.kerey.tools

import java.util.logging.Logger

abstract class BarcodeTool {

	def static barcodeMaxLength = 12
	def static barcodeMinLength = 12
	def static barcodeType = "EAN13"
	
	def static logger = Logger.getLogger(BarcodeTool.class.name)
	
	static def getBarcodeValue(Long number) {
		if (number==null) return null
		def result = String.valueOf(number)
		while (result.length() < 12)
			result = "0" + result
		result
	}
	
	static def getBarcodeNumber(String barcode) {
		try {
			def result = Long.valueOf(barcode)
			return result
		}
		catch (Exception ex) {
			logger.severe("Barcode identification error")
			ex.printStackTrace()
		}
		null
	}
	
}