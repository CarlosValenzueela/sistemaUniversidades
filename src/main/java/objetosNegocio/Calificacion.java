/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetosNegocio;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author angel
 */
@Entity
@Table(name = "calificacion")
public class Calificacion implements Serializable {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcalificacion")
    private Integer id;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "idmateria", nullable = false)
    private Materia materia;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idalumno", nullable = false)
    private Alumno alumno;
    
    @Column(name = "nota", nullable = false)
    private Integer nota;

    public Calificacion() {
    }

    public Calificacion(Materia materia, Alumno alumno, Integer nota) {
        this.materia = materia;
        this.alumno = alumno;
        this.nota = nota;
    }

    public Calificacion(Integer id, Materia materia, Alumno alumno, Integer nota) {
        this.id = id;
        this.materia = materia;
        this.alumno = alumno;
        this.nota = nota;
    }    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
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
        if (!(object instanceof Calificacion)) {
            return false;
        }
        Calificacion other = (Calificacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Calificacion{" + "id=" + id + ", materia=" + materia + ", alumno=" + alumno + ", nota=" + nota + '}';
    }
    
}
