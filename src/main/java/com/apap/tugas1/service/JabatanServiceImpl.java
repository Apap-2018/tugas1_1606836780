package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService{
	
	@Autowired
	private JabatanDb JabatanDb;

	@Override
	public Optional<JabatanModel> getJabatanById(Long id) {
		
		return JabatanDb.findById(id);
	}

	@Override
	public void addJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		JabatanDb.save(jabatan);
		
	}

	@Override
	public void deleteJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		JabatanDb.delete(jabatan);
	}

	@Override
	public List<JabatanModel> getAll() {
		// TODO Auto-generated method stub
		return JabatanDb.findAll();
	}
	
	@Override
	public List<JabatanModel> getAllJabatan() {
		// TODO Auto-generated method stub
		return JabatanDb.findAll();
	}
	@Override
	public void updateJabatan(JabatanModel jabatan, Long jabatanId) {
		JabatanModel jabatanUpdate = JabatanDb.getOne(jabatanId);
		jabatanUpdate.setNama(jabatan.getNama());
		jabatanUpdate.setDeskripsi(jabatan.getDeskripsi());
		jabatanUpdate.setGaji_pokok(jabatan.getGaji_pokok());
		JabatanDb.save(jabatanUpdate);
	}

}
