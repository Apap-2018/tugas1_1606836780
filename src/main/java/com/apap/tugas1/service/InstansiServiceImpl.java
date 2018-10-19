package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.*;
@Service
public class InstansiServiceImpl implements InstansiService{
	
	@Autowired
	private InstansiDb InstansiDb;

	@Override
	public Optional<InstansiModel> getIntansiById(Long id) {
		// TODO Auto-generated method stub
		return InstansiDb.findById(id);
	}
	
	@Override
	public List<InstansiModel> getAll(){
		return InstansiDb.findAll();
	}

	@Override
	public List<InstansiModel> getAllInstansiByProvinsi(ProvinsiModel provinsi) {
		// TODO Auto-generated method stub
		return InstansiDb.findAllInstansiByProvinsi(provinsi);
	}
	
	@Override
	public InstansiModel getDetailInstansiByProvinsi(ProvinsiModel provinsi) {
		// TODO Auto-generated method stub
		return InstansiDb.findInstansiByProvinsi(provinsi);
	}

}
