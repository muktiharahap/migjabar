package com.github.muktiharahap.migjabar.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.github.muktiharahap.migjabar.domain.enumeration.Sow;
import com.github.muktiharahap.migjabar.domain.enumeration.Mig;
import com.github.muktiharahap.migjabar.domain.enumeration.Status;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * A Job.
 */
@Entity
@Table(name = "job")
@EntityListeners(AuditingEntityListener.class)
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "uuid_mig")
    private String uuidMig;

    @Size(max = 15)
    @Column(name = "notel", length = 15)
    private String notel;

    @Enumerated(EnumType.STRING)
    @Column(name = "sow")
    private Sow sow;

    @Enumerated(EnumType.STRING)
    @Column(name = "mig")
    private Mig mig;

    @Size(max = 20)
    @Column(name = "sc", length = 20)
    private String sc;

    @Size(max = 5)
    @Column(name = "sto", length = 5)
    private String sto;

    @Size(max = 15)
    @Column(name = "odp", length = 15)
    private String odp;

    @Size(max = 4)
    @Column(name = "port", length = 4)
    private String port;

    @Size(max = 3)
    @Column(name = "dc", length = 3)
    private String dc;

    @Size(max = 25)
    @Column(name = "scan_sn", length = 25)
    private String scanSn;

    @Size(max = 60)
    @Column(name = "kategori", length = 60)
    private String kategori;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Size(max = 60)
    @Column(name = "nospeedy", length = 60)
    private String nospeedy;

    @Size(max = 60)
    @Column(name = "dckabel", length = 60)
    private String dckabel;

    @Size(max = 60)
    @Column(name = "sn", length = 60)
    private String sn;

    @Column(name = "created_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    @JsonIgnore
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    /**
     * A relationship
     */
    @OneToMany(mappedBy = "job")
    @JsonIgnore
    private Set<Attachment> attachments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuidMig() {
        return uuidMig;
    }

    public void setUuidMig(String uuidMig) {
        this.uuidMig = uuidMig;
    }

    public String getNotel() {
        return notel;
    }

    public void setNotel(String notel) {
        this.notel = notel;
    }

    public Sow getSow() {
        return sow;
    }

    public void setSow(Sow sow) {
        this.sow = sow;
    }

    public Mig getMig() {
        return mig;
    }

    public void setMig(Mig mig) {
        this.mig = mig;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getSto() {
        return sto;
    }

    public void setSto(String sto) {
        this.sto = sto;
    }

    public String getOdp() {
        return odp;
    }

    public void setOdp(String odp) {
        this.odp = odp;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public String getScanSn() {
        return scanSn;
    }

    public void setScanSn(String scanSn) {
        this.scanSn = scanSn;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getNospeedy() {
        return nospeedy;
    }

    public void setNospeedy(String nospeedy) {
        this.nospeedy = nospeedy;
    }

    public String getDckabel() {
        return dckabel;
    }

    public void setDckabel(String dckabel) {
        this.dckabel = dckabel;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "Job{" +
                "uuidMig='" + uuidMig + '\'' +
                ", notel='" + notel + '\'' +
                ", sow=" + sow +
                ", mig=" + mig +
                ", sc='" + sc + '\'' +
                ", sto='" + sto + '\'' +
                ", odp='" + odp + '\'' +
                ", port='" + port + '\'' +
                ", dc='" + dc + '\'' +
                ", scanSn='" + scanSn + '\'' +
                ", kategori='" + kategori + '\'' +
                ", status=" + status +
                ", nospeedy='" + nospeedy + '\'' +
                ", dckabel='" + dckabel + '\'' +
                ", sn='" + sn + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
