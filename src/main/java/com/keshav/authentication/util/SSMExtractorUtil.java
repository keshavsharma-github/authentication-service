package com.keshav.authentication.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult;

@Component
public class SSMExtractorUtil {

	@Autowired
	private AWSSimpleSystemsManagement ssmClient;

	public String getParameterValue(String name) {
		return getParameterValue(name, false);
	}

	public String getParameterValue(String name, boolean decryption) {
		GetParameterRequest request = new GetParameterRequest().withName(name).withWithDecryption(decryption);
		GetParameterResult response = ssmClient.getParameter(request);
		return response.getParameter().getValue();
	}
}