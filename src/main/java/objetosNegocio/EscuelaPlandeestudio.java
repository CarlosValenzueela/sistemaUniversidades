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
@Table(name = "rel_escuelaplandeestudio")
public class EscuelaPlandeestudio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrel_escuelaplandeestudio")
    private Integer id;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "idescuela", nullable = false)
    private Escuela escuela;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idplandeestudio", nullable = false)
    private Plandeestudio plandeestudio;

    
    
    
    public EscuelaPlandeestudio() {
    }

    public EscuelaPlandeestudio(Integer id, Escuela escuela, Plandeestudio plandeestudio) {
        this.id = id;
        this.escuela = escuela;
        this.plandeestudio = plandeestudio;
    }

    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Escuela getEscuela() {
        return escuela;
    }

    public void setEscuela(Escuela escuela) {
        this.escuela = escuela;
    }

    public Plandeestudio getPlandeestudio() {
        return plandeestudio;
    }

    public void setPlandeestudio(Plandeestudio plandeestudio) {
        this.plandeestudio = plandeestudio;
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
        if (!(object instanceof EscuelaPlandeestudio)) {
            return false;
        }
        EscuelaPlandeestudio other = (EscuelaPlandeestudio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "objetosNegocio.EscuelaPlandeestudio[ id=" + id + " ]";
    }
    
}
