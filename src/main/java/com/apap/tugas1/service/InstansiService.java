package com.apap.tugas1.service;

import java.util.Optional;

import com.apap.tugas1.model.InstansiModel;
public interface InstansiService {
	
	Optional<InstansiModel> getIntansiById(Long id);


}
