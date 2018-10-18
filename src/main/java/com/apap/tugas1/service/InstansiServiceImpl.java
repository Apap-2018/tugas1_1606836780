package com.apap.tugas1.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.apap.tugas1.model.InstansiModel;

import com.apap.tugas1.repository.*;

public class InstansiServiceImpl implements InstansiService{
	
	@Autowired
	private InstansiDb InstansiDb;

	@Override
	public Optional<InstansiModel> getIntansiById(Long id) {
		// TODO Auto-generated method stub
		return InstansiDb.findById(id);
	}

}
