package com.apap.tugas1.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	
	void addPegawai (PegawaiModel Pegawai);
	Optional<PegawaiModel> getPegawaiByNIP(Long NIP);
	PegawaiModel pegawaiTermuda(InstansiModel instansi);
	PegawaiModel pegawaiTertua(InstansiModel instansi);
	List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk);
	String makeNip(PegawaiModel pegawai);
	void addPegawaiBaru(PegawaiModel pegawai);
	List<PegawaiModel> getPegawaiByInstansi(InstansiModel Instansi);

}
