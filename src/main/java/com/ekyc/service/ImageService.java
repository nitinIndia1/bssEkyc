package com.ekyc.service;

import org.springframework.http.ResponseEntity;

import com.ekyc.beans.IdRequest;
import com.ekyc.beans.Image_;
import com.ekyc.model.Image;
import com.ekyc.utils.CoreResponseHandler;

public interface ImageService {

	
	Image saveImage(Image_ image_);
	Image updateImageWithSelfie(Image image, Image_ image_);
	void deleteDuplicateCustomerImage(Image image);
	
}
