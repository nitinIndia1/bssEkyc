package com.ekyc.service;

import com.ekyc.beans.Document_;
import com.ekyc.beans.Image_;
import com.ekyc.model.Document;
import com.ekyc.model.Image;

public interface DocumentService {

	
	Document saveDocument(Document document, Document_ document_);
	Document updateDocument(Document document, Document_ document_);
	void deleteDuplicateCustomerDocument(Document document);
	
	
	
}
