package com.apap.tugas1.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "jabatan_pegawai")
public class JabatanPegawaiModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pegawai", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private PegawaiModel pegawai;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_jabatan", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private JabatanModel jabatan;

	public JabatanModel getJabatan() {
		// TODO Auto-generated method stub
		return jabatan;
	}
	
	public void setJabatan(JabatanModel jabatan) {
		this.jabatan=jabatan;
	}
	
	public PegawaiModel getPegawai() {
		return pegawai;
	}
	
	public void setPegawai(PegawaiModel pegawai) {
		this.pegawai=pegawai;
	}

}
