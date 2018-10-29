package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
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
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	
	@RequestMapping("/")
	private String home(Model model) {
		List<JabatanModel> listJabatan = jabatanService.getAll();
		List<InstansiModel> listInstansi = instansiService.getAll();
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listInstansi", listInstansi);
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
	
	@RequestMapping(value="/pegawai/termuda-tertua", method=RequestMethod.GET)
	public String lihatTermudaTertua(@RequestParam(value = "instansiId") String id, Model model) {
		InstansiModel instansi = instansiService.getIntansiById(Long.parseLong(id)).get();
		PegawaiModel pegawaiTermuda = pegawaiService.pegawaiTermuda(instansi);
		PegawaiModel pegawaiTertua = pegawaiService.pegawaiTertua(instansi);
		model.addAttribute("pegawaiTermuda", pegawaiTermuda);
		model.addAttribute("pegawaiTertua", pegawaiTertua);
		return "view-pegawai-tua-muda";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String addPegawai(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		List<JabatanPegawaiModel> listJabatanPegawai = new ArrayList<JabatanPegawaiModel>();
		pegawai.setListJabatanPegawai(listJabatanPegawai);
		
		JabatanPegawaiModel jabatanPegawai = new JabatanPegawaiModel();
		jabatanPegawai.setPegawai(pegawai);
		pegawai.getListJabatanPegawai().add(jabatanPegawai);
		List<InstansiModel> listInstansi = listProvinsi.get(0).getListInstansi();
		
		List<JabatanModel> listJabatan = jabatanService.getAll();
		
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("title", "Tambah Pegawai");
		model.addAttribute("pegawai", pegawai);
		return "add-pegawai";
		
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params= {"addJabatan"})
	public String tambahJabatan(@ModelAttribute PegawaiModel pegawai_new, Model model) {
		PegawaiModel pegawai = pegawai_new;
		
		JabatanPegawaiModel jabatanPegawai = new JabatanPegawaiModel();
		jabatanPegawai.setPegawai(pegawai);
		pegawai.getListJabatanPegawai().add(jabatanPegawai);
		
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		
		List<InstansiModel> listInstansi = new ArrayList<InstansiModel>();
		listInstansi = listProvinsi.get(0).getListInstansi();
	
		List<JabatanModel> listJabatan = jabatanService.getAll();
		
		model.addAttribute("title", "Tambah Pegawai");
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("pegawai", pegawai);
		return "add-pegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.POST)
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {

		String nip = pegawaiService.makeNip(pegawai);
		pegawai.setNip(nip);
		
		List<JabatanPegawaiModel> listJabatan = pegawai.getListJabatanPegawai();
		
		pegawai.setListJabatanPegawai(new ArrayList<JabatanPegawaiModel>());
		
		pegawaiService.addPegawai(pegawai);
		
		for (int i = 0; i < listJabatan.size(); i++) {
			listJabatan.get(i).setPegawai(pegawai);
			jabatanPegawaiService.addJabatanPegawai(listJabatan.get(i));
		}
		
		model.addAttribute("status", "SUKSES!");
		model.addAttribute("info", "Pegawai dengan NIP "+ nip +" berhasil ditambahkan");
		return "notifications";
		
	}
	
	@RequestMapping(value = "/pegawai/instansi", method = RequestMethod.GET)
	public @ResponseBody
	List<InstansiModel> findAllInstansi(
	        @RequestParam(value = "idProvinsi", required = true) Long idProvinsi) {
		ProvinsiModel provinsi = provinsiService.getDetailProvinsiById(idProvinsi);
	    return provinsi.getListInstansi();
	}
	
	@RequestMapping(value="/pegawai/ubah", method = RequestMethod.GET)
	private String updatePegawai(@RequestParam(name = "nip")String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiByNIP(Long.parseLong(nip)).get();
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		
		List<InstansiModel> listInstansi = pegawai.getInstansi().getProvinsi().getListInstansi();
		
		List<JabatanModel> listJabatan = jabatanService.getAll();
		
		model.addAttribute("title", "Ubah Pegawai");
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("pegawai", pegawai);
		return "update-pegawai";
		
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST, params= {"addJabatan"})
	public String rowJabatan(@ModelAttribute PegawaiModel pegawai_new, Model model) {
		PegawaiModel pegawai = pegawai_new;
		
		JabatanPegawaiModel jabatanPegawai = new JabatanPegawaiModel();
		jabatanPegawai.setPegawai(pegawai);
		pegawai.getListJabatanPegawai().add(jabatanPegawai);
		
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		
		List<InstansiModel> listInstansi = new ArrayList<InstansiModel>();
		listInstansi = listProvinsi.get(0).getListInstansi();
		
		List<JabatanModel> listJabatan = jabatanService.getAll();
		
		model.addAttribute("title", "Ubah Pegawai");
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("pegawai", pegawai);
		return "update-pegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", method=RequestMethod.POST)
	private String updatePegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		PegawaiModel pegawaiSebelumUpdate = pegawaiService.getPegawaiByNIP(Long.parseLong(pegawai.getNip())).get();
		String nip = pegawaiService.makeNip(pegawai);
		pegawai.setNip(nip);
		pegawaiService.update(pegawai, pegawaiSebelumUpdate);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("title", "Sukses");
		model.addAttribute("nipPegawai", nip);
		return "notifications";

	}
	
	@RequestMapping(path="/pegawai/cari", method = RequestMethod.GET)
	private String cariPegawai(Optional<String> idProvinsi, Optional<String> idInstansi, Optional<String> idJabatan, Model model) {
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getAll();
		model.addAttribute("listProvinsi",listProvinsi);
		model.addAttribute("listJabatan", listJabatan);
		
		List<PegawaiModel> pegawai= null;
		if(idProvinsi.isPresent()) {
			ProvinsiModel provinsi = provinsiService.getProvinsiById(Long.parseLong(idProvinsi.get())).get();
			if(idInstansi.isPresent()) {
				InstansiModel instansi = instansiService.getIntansiById(Long.parseLong(idInstansi.get())).get();
				if(idJabatan.isPresent()) {
					JabatanModel jabatan = jabatanService.getJabatanById(Long.parseLong(idJabatan.get())).get();
					pegawai = pegawaiService.findPegawaiByInstansiAndJabatan(instansi, jabatan);
				}
				else {
					pegawai = instansi.getPegawaiInstansi();
				}
			} else {
				List<InstansiModel> instansi = provinsi.getListInstansi();
				pegawai = instansi.get(0).getPegawaiInstansi();
				for(int x = 1;x < instansi.size();x++) {
					List<PegawaiModel> pegProv = instansi.get(x).getPegawaiInstansi();
					for(PegawaiModel peg:pegProv) {
						pegawai.add(peg);
					}
				}
				
				if (idJabatan.isPresent()) {
					JabatanModel jabatan = jabatanService.getJabatanById(Long.parseLong(idJabatan.get())).get();
					pegawai = pegawaiService.findPegawaiByProvinsiAndJabatan(pegawai, jabatan);
				}
			}
			
		} else {
			if(idJabatan.isPresent()) {
				JabatanModel jabatan = jabatanService.getJabatanById(Long.parseLong(idJabatan.get())).get();
				List<JabatanPegawaiModel> jabatanpeg = jabatan.getListJabatanPegawai();
				List<PegawaiModel> pegawailist = new ArrayList<>();
				for(JabatanPegawaiModel jabpeg: jabatanpeg) {
					pegawailist.add(jabpeg.getPegawai());
				}
				pegawai = pegawailist;
			}
			
		}
		model.addAttribute("listPencarian", pegawai);
		return "cari-pegawai";
	}

}
