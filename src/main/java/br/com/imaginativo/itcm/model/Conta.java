package br.com.imaginativo.itcm.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "contas")
public class Conta extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2868440530093905024L;
	
	int numeroConta;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "administrador_id")
    private User administrador;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;
	
	@OneToMany(mappedBy = "conta")
    private List<Curso> cursos;
	
	@OneToMany(mappedBy = "conta")
    private List<Professor> professores;
	
	@OneToMany(mappedBy = "conta")
    private List<Turma> turmas;
	
	@OneToMany(mappedBy = "conta")
    private List<Aluno> alunos;
	
	@OneToMany(mappedBy = "conta")
    private List<Endereco> enderecos;
	
	@OneToMany(mappedBy = "conta")
    private List<Identificacao> identificacoes;
	
	@OneToMany(mappedBy = "conta")
    private List<Interesse> interesses;
	
	@OneToMany(mappedBy = "conta")
    private List<Perfil> usuarios;

	public int getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(int numeroConta) {
		this.numeroConta = numeroConta;
	}

	public User getAdministrador() {
		return administrador;
	}

	public void setAdministrador(User administrador) {
		this.administrador = administrador;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public List<Professor> getProfessores() {
		return professores;
	}

	public void setProfessores(List<Professor> professores) {
		this.professores = professores;
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public List<Identificacao> getIdentificacoes() {
		return identificacoes;
	}

	public void setIdentificacoes(List<Identificacao> identificacoes) {
		this.identificacoes = identificacoes;
	}

	public List<Interesse> getInteresses() {
		return interesses;
	}

	public void setInteresses(List<Interesse> interesses) {
		this.interesses = interesses;
	}

	public List<Perfil> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Perfil> usuarios) {
		this.usuarios = usuarios;
	}
}
