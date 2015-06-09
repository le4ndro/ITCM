package br.com.imaginativo.itcm.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.validator.constraints.NotEmpty;

@MappedSuperclass
public class NamedEntity extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -36060922987655185L;
    @Column(name = "nome")
    @NotEmpty
    private String name;

    public NamedEntity() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
