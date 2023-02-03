/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetosNegocio;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author angel
 */
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Integer id;

    @Column(name = "user", nullable = false, unique = true)
    private String user;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "curp", nullable = false, unique = true)
    private String curp;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "fecha", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @Column(name = "sexo", nullable = false)
    private String sexo;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @ManyToOne(optional = false, targetEntity = Escuela.class)
    @JoinColumn(name = "idescuela", nullable = false)
    private Escuela escuela;

    public Usuario() {
    }

    public Usuario(String user, String password, String curp, String nombre, String apellido, Date fecha, String sexo, Boolean activo, Escuela escuela) {
        this.user = user;
        this.password = password;
        this.curp = curp;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha = fecha;
        this.sexo = sexo;
        this.activo = activo;
        this.escuela = escuela;
    }

    public Usuario(Integer id, String nick, String password, String curp, String nombre, String apellido, Date fecha, String sexo, Boolean activo, Escuela escuela) {
        this.id = id;
        this.user = nick;
        this.password = password;
        this.curp = curp;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha = fecha;
        this.sexo = sexo;
        this.activo = activo;
        this.escuela = escuela;
    }

    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Escuela getEscuela() {
        return escuela;
    }

    public void setEscuela(Escuela escuela) {
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", user=" + user + ", password=" + password + ", curp=" + curp + ", nombre=" + nombre + ", apellido=" + apellido + ", fecha=" + fecha + ", sexo=" + sexo + ", activo=" + activo + ", escuela=" + escuela + '}';
    }

}
