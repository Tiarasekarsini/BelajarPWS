/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.a.Belajar;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "sayuran")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sayuran.findAll", query = "SELECT s FROM Sayuran s"),
    @NamedQuery(name = "Sayuran.findById", query = "SELECT s FROM Sayuran s WHERE s.id = :id"),
    @NamedQuery(name = "Sayuran.findByNamaSayur", query = "SELECT s FROM Sayuran s WHERE s.namaSayur = :namaSayur"),
    @NamedQuery(name = "Sayuran.findByHarga", query = "SELECT s FROM Sayuran s WHERE s.harga = :harga")})
public class Sayuran implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nama_sayur")
    private String namaSayur;
    @Column(name = "harga")
    private String harga;

    public Sayuran() {
    }

    public Sayuran(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamaSayur() {
        return namaSayur;
    }

    public void setNamaSayur(String namaSayur) {
        this.namaSayur = namaSayur;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sayuran)) {
            return false;
        }
        Sayuran other = (Sayuran) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ws.a.Belajar.Sayuran[ id=" + id + " ]";
    }
    
}
