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
@Table(name = "materia")
public class Materia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmateria")
    private Integer id;
    
    @Column(name = "clave", nullable = false, unique = true)
    private String clave;
    
    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materia")
    private List<Calificacion> calificaciones;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materia")
    private List<MateriaPlandeestudio> planesDeEstudio;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materia")
    private List<MateriasSerializacion> materias;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materiaSeriada")
    private List<MateriasSerializacion> materiasSeriadas;
    
    
    
    public void addCalificacion(Calificacion calificacion) {
        calificacion.setMateria(this);
        this.calificaciones.add(calificacion);
    }

    public void addPlanDeEstudio(MateriaPlandeestudio planDeEstudio) {
        planDeEstudio.setMateria(this);
        this.planesDeEstudio.add(planDeEstudio);
    }

    public void addMateria(MateriasSerializacion materia) {
        materia.setMateria(this);
        this.materias.add(materia);
    }
    
    public void addMateriaSeriada(MateriasSerializacion materiaSeriada) {
        materiaSeriada.setMateriaSeriada(this);
        this.materiasSeriadas.add(materiaSeriada);
    }
    

    
    public Materia() {
        this.calificaciones = new ArrayList<>();
        this.planesDeEstudio = new ArrayList<>();
        this.materias = new ArrayList<>();
        this.materiasSeriadas = new ArrayList<>();
    }

    public Materia(Integer id, String clave, String nombre, List<Calificacion> calificaciones, List<MateriaPlandeestudio> planesDeEstudio, List<MateriasSerializacion> materias, List<MateriasSerializacion> materiasSeriadas) {
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
        this.calificaciones = calificaciones;
        this.planesDeEstudio = planesDeEstudio;
        this.materias = materias;
        this.materiasSeriadas = materiasSeriadas;
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

    public List<Calificacion> getCalificaciones() {
        return calificaciones;
    }

    public List<MateriaPlandeestudio> getPlanesDeEstudio() {
        return planesDeEstudio;
    }

    public void setCalificaciones(List<Calificacion> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public void setPlanesDeEstudio(List<MateriaPlandeestudio> planesDeEstudio) {
        this.planesDeEstudio = planesDeEstudio;
    }

    public List<MateriasSerializacion> getMaterias() {
        return materias;
    }

    public List<MateriasSerializacion> getMateriasSeriadas() {
        return materiasSeriadas;
    }

    public void setMaterias(List<MateriasSerializacion> materias) {
        this.materias = materias;
    }

    public void setMateriasSeriadas(List<MateriasSerializacion> materiasSeriadas) {
        this.materiasSeriadas = materiasSeriadas;
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
        if (!(object instanceof Materia)) {
            return false;
        }
        Materia other = (Materia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "objetosNegocio.Materia[ nombre=" + this.nombre + " ]";
    }
    
}
