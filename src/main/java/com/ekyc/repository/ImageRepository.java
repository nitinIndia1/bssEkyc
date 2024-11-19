package com.ekyc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ekyc.model.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {

}
