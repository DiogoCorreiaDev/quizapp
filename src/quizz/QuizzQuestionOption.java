package quizz;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("respostasPossiveis")
@SessionScoped
public class QuizzQuestionOption implements Serializable, Comparable<QuizzQuestionOption> {
	private static final long serialVersionUID = 1L;
	private int id;
	private int quizz_question_id;
	private String text;
	private boolean is_correct;
	
	public QuizzQuestionOption(int id, int quizz_question_id, String text, boolean is_correct) {
		super();
		this.id = id;
		this.quizz_question_id = quizz_question_id;
		this.text = text;
		this.is_correct = is_correct;
	}

	public QuizzQuestionOption() {
		super();
	}

	public String getPercentageFromDB() {
		double nTotalRespostas = (double)Database.getTotalAnswersToQuestion(this.quizz_question_id);
		if (nTotalRespostas==0) {
			return "0";
		} else {
		double respostaOcorre = (double)Database.getAnswerOcurrence(this.id);
		double resultado = (respostaOcorre/nTotalRespostas)*100;
		//System.out.println("resposta ocorre: "+respostaOcorre+" respostastotais: "+nTotalRespostas+"resultado percentagem: "+resultado);
		return String.format("%.2f", resultado);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuizz_question_id() {
		return quizz_question_id;
	}

	public void setQuizz_question_id(int quizz_question_id) {
		this.quizz_question_id = quizz_question_id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isIs_correct() {
		return is_correct;
	}

	public void setIs_correct(boolean is_correct) {
		this.is_correct = is_correct;
	}


	

	@Override
	public String toString() {
		return "QuizzQuestionOption [id=" + id + ", quizz_question_id=" + quizz_question_id + ", text=" + text
				+ ", is_correct=" + is_correct + "]";
	}

	@Override
	public int compareTo(QuizzQuestionOption o) {
		return Integer.compare(this.id, o.id);
	}
	
	@Override
	public boolean equals(Object o) {
		return this.id==((QuizzQuestionOption)o).id;
	}
	

}
