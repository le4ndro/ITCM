package br.com.imaginativo.itcm.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "contatos")
public class Contato extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -6318800181920725435L;

    private String tel1;

    private String tel2;

    private String tel3;

    private String tel4;

    private String movel;

    private String email;

    private String fax;

    private String site;

    private String skype;

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getTel3() {
        return tel3;
    }

    public void setTel3(String tel3) {
        this.tel3 = tel3;
    }

    public String getTel4() {
        return tel4;
    }

    public void setTel4(String tel4) {
        this.tel4 = tel4;
    }

    public String getMovel() {
        return movel;
    }

    public void setMovel(String movel) {
        this.movel = movel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

}
