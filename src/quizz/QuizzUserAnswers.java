package quizz;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("respostasUtilizador")
@SessionScoped
public class QuizzUserAnswers implements Serializable, Comparable<QuizzUserAnswers> {

	private static final long serialVersionUID = 1L;
	private int idUser;
	private int quizzQuestionId;
	private int quizzQuestionOptionId;
	private String quizzQuestionOptionText;
	private Timestamp answerTime;
	
	public String getQuizzQuestionOptionText() {
		return quizzQuestionOptionText;
	}

	public void setQuizzQuestionOptionText(String quizzQuestionOptionText) {
		this.quizzQuestionOptionText = quizzQuestionOptionText;
		this.quizzQuestionOptionId = Integer.parseInt(quizzQuestionOptionText);
		QuizzQuestion.getRespostasEscolhidas().add(this.quizzQuestionOptionId);		
	}

	

	public QuizzUserAnswers() {
		super();
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public int getQuizzQuestionId() {
		return quizzQuestionId;
	}

	public void setQuizzQuestionId(int quizzQuestionId) {
		this.quizzQuestionId = quizzQuestionId;
	}

	public int getQuizzQuestionOptionId() {
		return quizzQuestionOptionId;
	}

	public void setQuizzQuestionOptionId(int quizzQuestionOptionId) {
		this.quizzQuestionOptionId = quizzQuestionOptionId;
	}

	public Timestamp getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(Timestamp answerTime) {
		this.answerTime = answerTime;
	}

	@Override
	public String toString() {
		return "QuizzUserAnswers [idUser=" + idUser + ", quizzQuestionId=" + quizzQuestionId
				+ ", quizzQuestionOptionId=" + quizzQuestionOptionId + ", answerTime=" + answerTime + "]";
	}

	@Override
	public int compareTo(QuizzUserAnswers o) {
		return Integer.compare(this.quizzQuestionId, o.quizzQuestionId);
	}

	@Override
	public boolean equals(Object o) {
		return this.quizzQuestionOptionId==((QuizzUserAnswers)o).quizzQuestionOptionId;
	}
	
}
