package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanPegawaiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;


@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanPegawaiService jabatanPegawaiService;
	
/*	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;*/
	
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping("/")
	private String home(Model model) {
		List<JabatanModel> listJabatan = jabatanService.getAll();
		model.addAttribute("listJabatan", listJabatan);
		return "home";
	}
	
	@RequestMapping(value="/pegawai")
	private String viewPegawai(@RequestParam("pegawaiNip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiByNIP(Long.parseLong(nip)).get();
		List<JabatanPegawaiModel> jabatanPegawai = jabatanPegawaiService.getJabatanByNip(nip).get();
		double gaji = 0.0;
		for(JabatanPegawaiModel jabatand : jabatanPegawai) {
			if (jabatand.getJabatan().getGaji_pokok() > gaji) {
				gaji=jabatand.getJabatan().getGaji_pokok();
			}
		}
		gaji += pegawai.getInstansi().getProvinsi().getPresentase_tunjangan()/100 * gaji;
		model.addAttribute("gaji", (long)gaji);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("jabatanPegawai", jabatanPegawai);
		return "view-pegawai";
	}
	
	
	


	
	
	
	

}
