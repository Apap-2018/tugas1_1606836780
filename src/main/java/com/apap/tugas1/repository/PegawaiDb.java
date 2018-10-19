package com.apap.tugas1.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;


@Repository
public interface PegawaiDb extends JpaRepository<PegawaiModel, Long> {
	
	List<PegawaiModel> findByInstansi(InstansiModel Instansi);
	
	Optional<PegawaiModel> findByNip(String Nip);
	
	List<PegawaiModel> findByInstansiOrderByTanggalLahirAsc (InstansiModel instansi);
	
	List<PegawaiModel> findByTanggalLahirAndTahunMasukAndInstansi(Date tanggalLahir, String tahunMasuk, InstansiModel instansi);

	List<PegawaiModel> findByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir,
			String tahunMasuk);
}
