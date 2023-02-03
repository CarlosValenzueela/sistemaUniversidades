/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetosNegocio;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author angel
 */
@Entity
@Table(name = "escuela")
public class Escuela implements Serializable {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idescuela")
    private Integer id;
    
    @Column(name = "clave", nullable = false, unique = true)
    private String clave;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Lob
    @Column(name = "logotipo", nullable = false)
    private byte[] logotipo;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "escuela")
    private List<EscuelaPlandeestudio> planesDeEstudio;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "escuela")
    private List<Usuario> usuarios;
    
    public void addPlandeestudio(EscuelaPlandeestudio escuelaPlanDeEstudio) {
        escuelaPlanDeEstudio.setEscuela(this);
        this.planesDeEstudio.add(escuelaPlanDeEstudio);
    }
    
    public void addUsuario(Usuario usuario) {
        usuario.setEscuela(this);
        this.usuarios.add(usuario);
    }

    public Escuela() {
    }

    public Escuela(String clave, String nombre, byte[] logotipo) {
        this.clave = clave;
        this.nombre = nombre;
        this.logotipo = logotipo;
    }
    
    public Escuela(String clave, String nombre, byte[] logotipo, List<EscuelaPlandeestudio> planesDeEstudio, List<Usuario> usuarios) {
        this.clave = clave;
        this.nombre = nombre;
        this.logotipo = logotipo;
        this.planesDeEstudio = planesDeEstudio;
        this.usuarios = usuarios;
    }
    
    public Escuela(Integer id, String clave, String nombre, byte[] logotipo, List<EscuelaPlandeestudio> planesDeEstudio, List<Usuario> usuarios) {
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
        this.logotipo = logotipo;
        this.planesDeEstudio = planesDeEstudio;
        this.usuarios = usuarios;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getLogotipo() {
        return logotipo;
    }

    public void setLogotipo(byte[] logotipo) {
        this.logotipo = logotipo;
    }

    public List<EscuelaPlandeestudio> getPlanesDeEstudio() {
        return planesDeEstudio;
    }

    public void setPlanesDeEstudio(List<EscuelaPlandeestudio> planesDeEstudio) {
        this.planesDeEstudio = planesDeEstudio;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
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
        if (!(object instanceof Escuela)) {
            return false;
        }
        Escuela other = (Escuela) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    
}
