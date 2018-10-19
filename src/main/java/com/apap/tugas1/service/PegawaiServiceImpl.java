package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.*;
@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {

	@Autowired
	private PegawaiDb PegawaiDb;
	
	@Override
	public void addPegawai(PegawaiModel Pegawai) {
		// TODO Auto-generated method stub
		PegawaiDb.save(Pegawai);
		
	}

	@Override
	public Optional<PegawaiModel> getPegawaiByNIP(Long NIP) {
		// TODO Auto-generated method stub
		return PegawaiDb.findByNip(String.valueOf(NIP));
	}
	
	@Override
	public PegawaiModel pegawaiTermuda(InstansiModel instansi) {
		List<PegawaiModel> listTermuda = PegawaiDb.findByInstansiOrderByTanggalLahirAsc(instansi);
		return listTermuda.get(listTermuda.size()-1);
	}
	
	@Override
	public PegawaiModel pegawaiTertua(InstansiModel instansi) {
		List<PegawaiModel> listTertua = PegawaiDb.findByInstansiOrderByTanggalLahirAsc(instansi);
		return listTertua.get(0);
	}

}
