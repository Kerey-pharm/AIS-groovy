package kz.kerey.services.ws.handlers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;


public class WebServiceHandler implements SOAPHandler {

	@Override
	public void close(MessageContext context) {
	}

	@Override
	public boolean handleFault(MessageContext context) {
		return false;
	}

	@Override
	public boolean handleMessage(MessageContext context) {
		Boolean isRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		SOAPMessageContext ctx = (SOAPMessageContext) context;
		if (!isRequest) {
			ByteArrayOutputStream str = new ByteArrayOutputStream();
			try {
				ctx.getMessage().writeTo(str);
				System.out.println(str.toString());
			} catch (SOAPException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public Set getHeaders() {
		return null;
	}

	private void attacheErrorMessage(SOAPMessage errorMessage, String cause) {
		try {
			SOAPBody soapBody = errorMessage.getSOAPPart().getEnvelope().getBody();
			SOAPFault soapFault = soapBody.addFault();
			soapFault.setFaultString(cause);
			throw new SOAPFaultException(soapFault);
		} catch (SOAPException e) {
		}
	}
	
}
