/*
 * @ (#) SentenceRepository.java  1.0 7/3/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.suspectservice.repository;


import com.backend.suspectservice.model.Sentence;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/3/2025
 * @version:    1.0
 */
@RepositoryRestResource
@Hidden
public interface SentenceRepository extends JpaRepository<Sentence,String> {
}
