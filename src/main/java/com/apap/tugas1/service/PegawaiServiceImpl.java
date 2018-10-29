package com.apap.tugas1.service;

import java.text.SimpleDateFormat;
import java.util.Date;
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
	
	@Autowired
	private JabatanPegawaiDb jabatanPegawaiDb;
	
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
	
	@Override
	public List<PegawaiModel> getPegawaiByInstansi(InstansiModel Instansi) {
		// TODO Auto-generated method stub
		return PegawaiDb.findByInstansiOrderByTanggalLahirAsc(Instansi);
	}

	@Override
	public String makeNip(PegawaiModel Pegawai) {
		// TODO Auto-generated method stub
		String nip = "";
		nip += Pegawai.getInstansi().getId();
		Date date = Pegawai.getTanggalLahir();
		String[] tglLahir = (""+date).split("-");
		for (int i = tglLahir.length-1; i >= 0; i--) {
			int ukuranTgl = tglLahir[i].length();
			nip += tglLahir[i].substring(ukuranTgl-2, ukuranTgl);
		}
		nip += Pegawai.getTahunMasuk();
		
		List<PegawaiModel> listPegawai = PegawaiDb.findByTanggalLahirAndTahunMasukAndInstansi(Pegawai.getTanggalLahir(), Pegawai.getTahunMasuk(), Pegawai.getInstansi());
		
		int banyakPegawai = listPegawai.size();
		
		if (banyakPegawai >= 10) {
			nip += banyakPegawai;
		}
		else {
			nip += "0" + (banyakPegawai+1);
		}
		
		return nip;
	}
	
	@Override
	public List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi,
			Date tanggalLahir, String tahunMasuk) {
		// TODO Auto-generated method
		return PegawaiDb.findByInstansiAndTanggalLahirAndTahunMasuk(instansi, tanggalLahir, tahunMasuk);
	}

	@Override
	public void addPegawaiBaru(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		PegawaiDb.save(pegawai);
		
	}

	@Override
	public void update(PegawaiModel updatePegawai, PegawaiModel pegawaiSebelumUpdate) {
		// TODO Auto-generated method stub
		pegawaiSebelumUpdate.setInstansi(updatePegawai.getInstansi());
		pegawaiSebelumUpdate.setNama(updatePegawai.getNama());
		pegawaiSebelumUpdate.setNip(updatePegawai.getNip());
		pegawaiSebelumUpdate.setTahunMasuk(updatePegawai.getTahunMasuk());
		pegawaiSebelumUpdate.setTanggalLahir(updatePegawai.getTanggalLahir());
		pegawaiSebelumUpdate.setTempat_lahir(updatePegawai.getTempat_lahir());
		
		
		//update jabatan
		int jumlahList = pegawaiSebelumUpdate.getListJabatanPegawai().size();
		for(int i = 0; i<jumlahList; i++ ) {
			pegawaiSebelumUpdate.getListJabatanPegawai().get(i).setJabatan(updatePegawai.getListJabatanPegawai().get(i).getJabatan());
		}
		
		for (int i = jumlahList; i < updatePegawai.getListJabatanPegawai().size(); i++) {
			updatePegawai.getListJabatanPegawai().get(i).setPegawai(pegawaiSebelumUpdate);
			jabatanPegawaiDb.save(updatePegawai.getListJabatanPegawai().get(i));
		}
		
	}

}
