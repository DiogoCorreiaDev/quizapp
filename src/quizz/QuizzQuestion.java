package quizz;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;


@Named("questoes")
@SessionScoped
public class QuizzQuestion implements Serializable, Comparable<QuizzQuestion> {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private int quizz_id;
	private String text;
	private ArrayList<QuizzQuestionOption> listaRespostas;
	private Map<String, Integer> opcoesResposta;
	private static ArrayList<Integer> respostasEscolhidas = new ArrayList<Integer>();

	
	public static ArrayList<Integer> getRespostasEscolhidas() {
		return respostasEscolhidas;
	}

	public static void setRespostasEscolhidas(ArrayList<Integer> respostasEscolhidas) {
		QuizzQuestion.respostasEscolhidas = respostasEscolhidas;
	}

	public void submeterRespostas() {
		FacesContext context = FacesContext.getCurrentInstance();
		for (int i=0; i<respostasEscolhidas.size(); i++) {
			int perguntaCorrespondente = 0;
			try {
				perguntaCorrespondente = Database.getQuestionId(respostasEscolhidas.get(i));
			} catch (ClassNotFoundException | SQLException e) {
				System.out.println("Erro a buscar pergunta correspondente à resposta da base de dados.");
			}
			FacesContext context2 = FacesContext.getCurrentInstance();
	        HttpSession session = (HttpSession) context2.getExternalContext().getSession(false);
			int userid = (Integer) session.getAttribute("idUser");
			
			try {
				if(Database.verificaSeRespondeu(userid, perguntaCorrespondente)) {
					//System.out.println("userid:"+userid);
					//System.out.println("question id:"+perguntaCorrespondente);
					Database.apagaResposta(userid, perguntaCorrespondente);
					Database.insereResposta(userid,perguntaCorrespondente,respostasEscolhidas.get(i));
				} else {
				Database.insereResposta(userid,perguntaCorrespondente,respostasEscolhidas.get(i));
				}
			} catch (SQLException e) {
				System.out.println("Ocorreu um erro a verificar se o utilizador já respondeu a esse quizz");
			}
		}
		context.addMessage(null, new FacesMessage("As suas respostas foram submetidas com sucesso!"));
	}

	public QuizzQuestion() {
		super();
	}
	
	public Map<String, Integer> getOpcoesResposta() {
		return opcoesResposta;
	}

	public void setOpcoesResposta(Map<String, Integer> opcoesResposta) {
		this.opcoesResposta = opcoesResposta;
	}

	public ArrayList<QuizzQuestionOption> getListaRespostas() {
		return listaRespostas;
	}

	public void setListaRespostas(ArrayList<QuizzQuestionOption> listaRespostas) {
		this.listaRespostas = listaRespostas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuizz_id() {
		return quizz_id;
	}

	public void setQuizz_id(int quizz_id) {
		this.quizz_id = quizz_id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "QuizzQuestion [id=" + id + ", quizz_id=" + quizz_id + ", text=" + text + "]";
	}

	@Override
	public boolean equals(Object o) {
		return this.id==((QuizzQuestion)o).id;
	}

	@Override
	public int compareTo(QuizzQuestion o) {
		return Integer.compare(this.id, o.id);
	}
	

}
