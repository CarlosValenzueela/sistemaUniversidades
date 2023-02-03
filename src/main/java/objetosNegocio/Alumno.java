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
@Table(name = "alumno")
public class Alumno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idalumno")
    private Integer id;
    
    @Column(name = "matricula", nullable = false, unique = true)
    private String matricula;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(name = "curp", nullable = false, unique = true)
    private String curp;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alumno")
    private List<Calificacion> calificaciones;
    
    @Column(name = "numPlan", nullable = false)
    private Integer numeroPlan;
    
    public void addCalificacion(Calificacion calificacion) {
        calificacion.setAlumno(this);
        this.calificaciones.add(calificacion);
    }
  
    
    public Alumno() {
        this.calificaciones = new ArrayList<>();
    }

    public Alumno(String matricula, String nombre, String curp) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.curp = curp;
    }

    public Alumno(String matricula, String nombre, String curp, List<Calificacion> calificaciones) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.curp = curp;
        this.calificaciones = calificaciones;
    }

    public Alumno(String matricula, String nombre, String curp, List<Calificacion> calificaciones, Integer numeroPlan) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.curp = curp;
        this.calificaciones = calificaciones;
        this.numeroPlan = numeroPlan;
    }

    public Alumno(Integer id, String matricula, String nombre, String curp, List<Calificacion> calificaciones, Integer numeroPlan) {
        this.id = id;
        this.matricula = matricula;
        this.nombre = nombre;
        this.curp = curp;
        this.calificaciones = calificaciones;
        this.numeroPlan = numeroPlan;
    }
    
    public Alumno(Integer id, String matricula, String nombre, String curp, List<Calificacion> calificaciones) {
        this.id = id;
        this.matricula = matricula;
        this.nombre = nombre;
        this.curp = curp;
        this.calificaciones = calificaciones;
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public List<Calificacion> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<Calificacion> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public Integer getNumeroPlan() {
        return numeroPlan;
    }

    public void setNumeroPlan(Integer numeroPlan) {
        this.numeroPlan = numeroPlan;
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
        if (!(object instanceof Alumno)) {
            return false;
        }
        Alumno other = (Alumno) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Alumno{" + "id=" + id + ", matricula=" + matricula + ", nombre=" + nombre + ", curp=" + curp + '}';
    }


    
}
