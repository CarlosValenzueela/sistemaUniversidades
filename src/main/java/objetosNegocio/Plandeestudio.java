/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetosNegocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author angel
 */
@Entity
@Table(name = "plandeestudio")
public class Plandeestudio implements Serializable {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idplandeestudio")
    private Integer id;
    
    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "plandeestudio")
    private List<MateriaPlandeestudio> materias;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "plandeestudio")
    private List<EscuelaPlandeestudio> escuela;
    
    public void addMateria(MateriaPlandeestudio materiaPlandeestudio) {
        materiaPlandeestudio.setPlandeestudio(this);
        this.materias.add(materiaPlandeestudio);
    }
    
    public void addEscuela(EscuelaPlandeestudio escuelaPlanDeEstudio) {
        escuelaPlanDeEstudio.setPlandeestudio(this);
        this.escuela.add(escuelaPlanDeEstudio);
    }
    
    

    public Plandeestudio() {
        this.escuela = new ArrayList<>();
        this.materias = new ArrayList<>();
    }

    public Plandeestudio(String nombre) {
        this.nombre = nombre;
    }

    public Plandeestudio(String nombre, List<MateriaPlandeestudio> materias) {
        this.nombre = nombre;
        this.materias = materias;
    }
    
    public Plandeestudio(Integer id, String nombre, List<MateriaPlandeestudio> materias, List<EscuelaPlandeestudio> escuela) {
        this.id = id;
        this.nombre = nombre;
        this.materias = materias;
        this.escuela = escuela;
    }

    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<MateriaPlandeestudio> getMaterias() {
        return materias;
    }

    public void setMaterias(List<MateriaPlandeestudio> materias) {
        this.materias = materias;
    }

    public List<EscuelaPlandeestudio> getEscuela() {
        return escuela;
    }

    public void setEscuela(List<EscuelaPlandeestudio> escuela) {
        this.escuela = escuela;
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
        if (!(object instanceof Plandeestudio)) {
            return false;
        }
        Plandeestudio other = (Plandeestudio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Plandeestudio{" + "id=" + id + ", nombre=" + nombre + ", materias=" + materias + ", escuela=" + escuela + '}';
    }


    
}
