package quizz;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;


@Named("questionario")
@SessionScoped
public class Questionario implements Comparable<Questionario>, Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private Timestamp dataInicio;
	private Timestamp dataFim;	
	private int available;
	private ArrayList<QuizzQuestion> listaPerguntas;
	private ArrayList<Questionario> listaQuestionarios;
	
	public Questionario() {
		super();
	}
	
	public String verificaRespondeu() {
		FacesContext context2 = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context2.getExternalContext().getSession(false);
		int userid = (Integer) session.getAttribute("idUser");
			try {
				if(Database.respondeuAoQuizz(userid, this.id)) {
					String casoTenhaRespondido = Database.respostasPrevias(userid, this.id);
					return casoTenhaRespondido;
				} else {
					return "É a primeira vez que está a realizar este inquérito. Escolhas as suas respostas abaixo.";
				}
			} catch (ClassNotFoundException | SQLException e) {
				return "Ocorreu um erro a determinar se utilizador já havia respondido ao inquérito.";
			}
	}
	public void estatisticaComTitle() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        try {
			Questionario questionarioSelecionado = Database.retrieveQuizz(this.title);
			this.id = questionarioSelecionado.getId();
			this.dataFim = questionarioSelecionado.getDataFim();
			this.listaPerguntas = Database.retrieveQuizzQuestions(this.id);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Ocorreu um erro a gerar questionário.");
		}
        try {
        	if(this.listaPerguntas.size()<1) {
        		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O quiz em questão não tem perguntas disponíveis. Por favor escolha outro quiz.", null));
        	}else {
			context.getExternalContext().redirect("Estatistica.xhtml");
        	}
		} catch (IOException e) {
			System.out.println("Ocorreu um erro a redirecionar para a estatística em questão.");
		}
    }

	public void criaQuizComTitle() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        try {
			Questionario questionarioSelecionado = Database.retrieveQuizz(this.title);
			this.id = questionarioSelecionado.getId();
			this.dataFim = questionarioSelecionado.getDataFim();
			this.listaPerguntas = Database.retrieveQuizzQuestions(this.id);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Ocorreu um erro a gerar questionário.");
		}
        
        try {
        	Timestamp agora = new Timestamp(System.currentTimeMillis());
        	if(this.dataFim.before(agora)) {
        		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O quiz em questão já se encontra expirado.", null));
        	} else if(this.listaPerguntas.size()<1) {
        		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O quiz em questão não tem perguntas disponíveis. Por favor escolha outro quiz.", null));
        	}else {
			context.getExternalContext().redirect("questionarios.xhtml");
        	}
		} catch (IOException e) {
			System.out.println("Ocorreu um erro a redirecionar para o quiz em questão.");
		}
    }
	
	public ArrayList<Questionario> getListaQuestionarios() {
		try {
			listaQuestionarios = Database.retrieveAllQuizz();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Ocorreu um erro a encontrar os questionários na base de dados.");
		}
		return listaQuestionarios;
	}

	public void setListaQuestionarios(ArrayList<Questionario> listaQuestionarios) {
		this.listaQuestionarios = listaQuestionarios;
	}

	public ArrayList<QuizzQuestion> getListaPerguntas() {
		return listaPerguntas;
	}

	public void setListaPerguntas(ArrayList<QuizzQuestion> listaPerguntas) {
		this.listaPerguntas = listaPerguntas;
	}

	public Timestamp getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Timestamp dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Timestamp getDataFim() {
		return dataFim;
	}
	public void setDataFim(Timestamp dataFim) {
		this.dataFim = dataFim;
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() throws ClassNotFoundException, SQLException {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String toString() {
		return title;
	}
	@Override
	public int compareTo(Questionario o) {
		return Integer.compare(this.id, o.id);
	}
	@Override
	public boolean equals(Object o) {
		return this.id==((Questionario)o).id;
	}

}
