package quizz;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;


@Named("criarQuizz")
@SessionScoped
public class CriarQuizz implements Serializable {
	private static final long serialVersionUID = 1L;
	private String quizTitle;
	private int numeroQuestoes;
	private int numeroRespostas;
	private int numeroTotalRespostas;
	private ArrayList<Integer> listaNumeroPerguntas;
	private ArrayList<Integer> listaNumeroRespostas;
	private String[] listaPerguntas;
	private String[] listaRespostas;

	
	public Boolean temPerguntasRepetidas(String[] listaPerguntas) {
		String teste = "";
		for (int i=0; i<listaPerguntas.length; i++) {
			teste = listaPerguntas[i];
			for (int j=i+1; j<listaPerguntas.length; j++) {
				if (teste.equals(listaPerguntas[j])) {
					return true;
				}
			}
		}
		return false;
	}	
	
	public Boolean temRespostasRepetidas(String[] listaRespostas) {
		
		int respostasPorPergunta= numeroTotalRespostas/numeroQuestoes;
		String teste="";
		for (int i=0; i < numeroQuestoes; i++) {
			for (int j=0; j < respostasPorPergunta-1; j++) {
				teste=listaRespostas[i*respostasPorPergunta+j];
				for (int k=j+1; k < respostasPorPergunta; k++) {
					if (teste.equals(listaRespostas[i*respostasPorPergunta+k])) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void criarQuiz() {
		
		try {
			if(Database.quizJaExiste(quizTitle)) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O título que escolheu já não está disponível. Por favor altere o título.", null));
			} else {
			if (temPerguntasRepetidas(listaPerguntas)) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O quiz não pode ter perguntas repetidas!", null));
			} else {
			if (temRespostasRepetidas(listaRespostas)) {
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O quiz não pode ter respostas repetidas para a mesma pergunta!", null));
				} else {	
			
				Database.insereQuestionario(quizTitle);
				int respostasPorPergunta= numeroTotalRespostas/numeroQuestoes;
				int idQuiz = Database.retrieveQuizzId(quizTitle);
				for (int i=0; i < listaPerguntas.length; i++) {
					Database.inserePergunta(listaPerguntas[i], idQuiz);
					int idPergunta = Database.retrieveQuestionId(listaPerguntas[i],idQuiz);
					for (int j=0; j < respostasPorPergunta; j++) {
						Database.insereRespostaOpcao(listaRespostas[i*respostasPorPergunta+j], idPergunta);
					}
				}
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("O seu quiz foi criado com sucesso!"));
				}}}} catch (ClassNotFoundException | SQLException e) {
				System.out.println("Ocorreu um erro a adicionar quiz à BD.");
			}
	}
	
	public void criaTemplate() {
        
        listaPerguntas = new String[numeroQuestoes];
        this.numeroTotalRespostas = numeroQuestoes * numeroRespostas;
        listaRespostas = new String[numeroTotalRespostas];
    	listaNumeroPerguntas = new ArrayList<Integer>();
    	listaNumeroRespostas = new ArrayList<Integer>();
		for (int i = 0; i < numeroQuestoes; i++) {
			listaNumeroPerguntas.add(i);
		}
		for (int i = 0; i < numeroRespostas; i++) {
			listaNumeroRespostas.add(i);
		}
        FacesContext context = FacesContext.getCurrentInstance();
        try {
			context.getExternalContext().redirect("CriarQuizz.xhtml");
		} catch (IOException e) {
			System.out.println("Ocorreu um erro a redirecionar a página!");
		}
    }

	public int getNumeroTotalRespostas() {
		return numeroTotalRespostas;
	}

	public void setNumeroTotalRespostas(int numeroTotalRespostas) {
		this.numeroTotalRespostas = numeroTotalRespostas;
	}

	public String[] getListaPerguntas() {
		return listaPerguntas;
	}

	public void setListaPerguntas(String[] listaPerguntas) {
		this.listaPerguntas = listaPerguntas;
	}

	public String[] getListaRespostas() {
		return listaRespostas;
	}

	public void setListaRespostas(String[] listaRespostas) {
		this.listaRespostas = listaRespostas;
	}

	public String getQuizTitle() {
		return quizTitle;
	}

	public void setQuizTitle(String quizTitle) {
		this.quizTitle = quizTitle;
	}
	public ArrayList<Integer> getListaNumeroPerguntas() {
		return listaNumeroPerguntas;
	}

	public void setListaNumeroPerguntas(ArrayList<Integer> listaNumeroPerguntas) {
		this.listaNumeroPerguntas = listaNumeroPerguntas;
	}

	public ArrayList<Integer> getListaNumeroRespostas() {
		return listaNumeroRespostas;
	}

	public void setListaNumeroRespostas(ArrayList<Integer> listaNumeroRespostas) {
		this.listaNumeroRespostas = listaNumeroRespostas;
	}

	public CriarQuizz() {
		super();
	}

	public int getNumeroQuestoes() {
		return numeroQuestoes;
	}

	public int getNumeroRespostas() {
		return numeroRespostas;
	}

	@Override
	public String toString() {
		return "CriarQuizz [numeroQuestoes=" + numeroQuestoes + ", numeroRespostas=" + numeroRespostas
				+ ", listaNumeroPerguntas=" + listaNumeroPerguntas + ", listaNumeroRespostas=" + listaNumeroRespostas
				+ "]";
	}

	public void setNumeroQuestoes(int numeroQuestoes) {
		this.numeroQuestoes = numeroQuestoes;
	}

	public void setNumeroRespostas(int numeroRespostas) {
		this.numeroRespostas = numeroRespostas;
	}

	
	

}
