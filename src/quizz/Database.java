package quizz;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;


public class Database {
	static Connection con = null;
	static Statement stmt = null;
	static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/quizz?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	static final String USER = "root";
	static final String PASS = "root";
	
	public static ArrayList<Questionario> retrieveAllQuizz() throws SQLException, ClassNotFoundException{
		con = DriverManager.getConnection(DB_URL, USER, PASS);
		
		String query = "select * from quizz.quizz";
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		ArrayList<Questionario> listaQuestionarios = new ArrayList<Questionario>();
		while (rs.next()) {
			Questionario questionario = new Questionario();
			questionario.setId(rs.getInt("id"));
			questionario.setTitle(rs.getString("title"));
			questionario.setDataInicio(rs.getTimestamp("beginDate"));
			questionario.setDataFim(rs.getTimestamp("endDate"));
			questionario.setAvailable(rs.getInt("available"));
			listaQuestionarios.add(questionario);
		}
		stmt.close();
		con.close();
		return listaQuestionarios;
	}
	
	public static void exportaBD() throws SQLException, ClassNotFoundException, IOException, InterruptedException{
		String dbName = "quizz";
		String dbUser = "root";
		String dbPass = "root";
		String executeCmd = "";
		String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
		String fileName= "dbBackup"+timeStamp+".sql";
		executeCmd = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump -u "+dbUser+" -p"+dbPass+" "+dbName+" -r C:\\Users\\dmcorreia\\eclipse-workspace\\ProjetoJavaEE_FINAL\\dbBackup\\"+fileName;
		Process runtimeProcess =Runtime.getRuntime().exec(executeCmd);
		int processComplete = runtimeProcess.waitFor();
		if(processComplete == 0){
		System.out.println("Backup taken successfully");
		} else {
		System.out.println("Could not take mysql backup");
		}
	}
	
public static Boolean quizJaExiste(String title) throws SQLException, ClassNotFoundException{
		
		con = DriverManager.getConnection(DB_URL, USER, PASS);
		String query = "select * from quizz.quizz where title='"+title+"'";
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		if(!rs.next()) {
			stmt.close();
			con.close();
			return false;
		} else {
			stmt.close();
			con.close();
			return true;	
		}
	}
	
	public static Questionario retrieveQuizz(String title) throws SQLException, ClassNotFoundException{
		
		con = DriverManager.getConnection(DB_URL, USER, PASS);
		
		String query = "select * from quizz.quizz where title='"+title+"'";
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		rs.next();
			Questionario questionario = new Questionario();
			questionario.setId(rs.getInt("id"));
			questionario.setTitle(rs.getString("title"));
			questionario.setDataInicio(rs.getTimestamp("beginDate"));
			questionario.setDataFim(rs.getTimestamp("endDate"));
			questionario.setAvailable(rs.getInt("available"));
		stmt.close();
		con.close();
		return questionario;
	}
	
	public static int retrieveQuizzId(String title) throws SQLException, ClassNotFoundException{
		
		con = DriverManager.getConnection(DB_URL, USER, PASS);
		
		String query = "select id from quizz.quizz where title='"+title+"'";
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		rs.next();
		int idQuizz = rs.getInt("id");
		stmt.close();
		con.close();
		return idQuizz;
	}
	
	public static int retrieveQuestionId(String text, int quizz_id) throws SQLException, ClassNotFoundException{
		
		con = DriverManager.getConnection(DB_URL, USER, PASS);
		
		String query = "select id from quizz.quizz_question where text='"+text+"' AND quizz_id='"+quizz_id+"'";
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		rs.next();
		int idQuestion = rs.getInt("id");
		stmt.close();
		con.close();
		return idQuestion;
	}
		
	public static ArrayList<QuizzQuestion> retrieveQuizzQuestions(int quizzId) throws SQLException, ClassNotFoundException{

		con = DriverManager.getConnection(DB_URL, USER, PASS);
		
		String query = "select * from quizz.quizz_question where quizz_id='"+quizzId+"'";
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		ArrayList<QuizzQuestion> listaQuestoes = new ArrayList<QuizzQuestion>();
		while (rs.next()) {
			QuizzQuestion quizzQuestion = new QuizzQuestion();
			quizzQuestion.setId(rs.getInt("id"));
			quizzQuestion.setQuizz_id(rs.getInt("quizz_id"));
			quizzQuestion.setText(rs.getString("text"));
			quizzQuestion.setListaRespostas(retrieveQuizzQuestionsOptions(rs.getInt("id")));
			LinkedHashMap<String, Integer> opcoesResposta = new LinkedHashMap<String, Integer>();
			for (int i=0; i< quizzQuestion.getListaRespostas().size(); i++) {
				opcoesResposta.put(quizzQuestion.getListaRespostas().get(i).getText(), quizzQuestion.getListaRespostas().get(i).getId());	
			}
			quizzQuestion.setOpcoesResposta(opcoesResposta);
			listaQuestoes.add(quizzQuestion);
		}
		stmt.close();
		con.close();
		return listaQuestoes;
	}
	
	public static ArrayList<QuizzQuestionOption> retrieveQuizzQuestionsOptions(int questionId) throws SQLException, ClassNotFoundException{

		con = DriverManager.getConnection(DB_URL, USER, PASS);
		
		String query = "select * from quizz.quizz_question_option where quizz_question_id='"+questionId+"'";
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		ArrayList<QuizzQuestionOption> listaOpcoes = new ArrayList<QuizzQuestionOption>();
		while (rs.next()) {
			QuizzQuestionOption quizzQuestionOption = new QuizzQuestionOption();
			quizzQuestionOption.setId(rs.getInt("id"));
			quizzQuestionOption.setQuizz_question_id(rs.getInt("quizz_question_id"));
			quizzQuestionOption.setText(rs.getString("text"));
			
			listaOpcoes.add(quizzQuestionOption);
		}
		stmt.close();
		con.close();
		return listaOpcoes;
	}
	
	public static Boolean emailExists(String email) throws SQLException, ClassNotFoundException{

		con = DriverManager.getConnection(DB_URL, USER, PASS);
		String query = "select * from quizz.user where email='"+email+"'";
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		if(!rs.next()) {
			stmt.close();
			con.close();
			return false;
		} else {
			stmt.close();
			con.close();
			return true;	
		}
	}
	
	public static Boolean checkPass(String emailInserido, String passwordInserida) throws SQLException, ClassNotFoundException{

		con = DriverManager.getConnection(DB_URL, USER, PASS);
		String query = "select * from user where email='"+emailInserido+"'";
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		rs.next();
			if ((rs.getString("password")).equals(passwordInserida) && (rs.getString("email")).equals(emailInserido)) {
				stmt.close();
				con.close();
				return true;	
			} else {
				stmt.close();
				con.close();
				return false;
			}	
		 
	}
	public static User getUser(String emailInserido) throws SQLException, ClassNotFoundException{

		con = DriverManager.getConnection(DB_URL, USER, PASS);
		String query = "select * from quizz.user where email='"+emailInserido+"'";
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		rs.next();
		User utilizador = new User();
		
		utilizador.setIdUser(rs.getInt("iduser"));
		utilizador.setLastName(rs.getString("last_name"));
		utilizador.setFirstName(rs.getString("first_name"));
		utilizador.setEmail(rs.getString("email"));
		utilizador.setRole(rs.getString("role"));
		stmt.close();
		con.close();
		return utilizador;
	}
	
	public static void actualizaRole(String role, int id) {
		try {
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			String query = "UPDATE quizz.user SET role = '"+role+"' WHERE idUser='"+id+"';";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(query);
			
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

	}
	
	public static void insereUser(String email, String firstName, String lastName, String password, String role) {
		try {
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			
			PreparedStatement pstmt = con.prepareStatement("insert into user (email, first_name, last_name, password, role)"
					+ " values (?, ?, ?, ?, ?)");
			
			pstmt.setString(1, email);
			pstmt.setString(2, firstName);
			pstmt.setString(3, lastName);
			pstmt.setString(4, password);
			pstmt.setString(5, role);
			
			pstmt.executeUpdate();
			
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

	}
	
	public static Integer getQuestionId(int optionId) throws SQLException, ClassNotFoundException{

		con = DriverManager.getConnection(DB_URL, USER, PASS);
		String query = "select * from quizz.quizz_question_option where id='"+optionId+"'";
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		rs.next();
		int perguntaId = rs.getInt("quizz_question_id");
		stmt.close();
		con.close();
		return perguntaId;
	}
	
	public static void insereResposta(int idUser, int questionID, int AnswerID) {
		try {
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			PreparedStatement pstmt = con.prepareStatement("insert into quizz_user_answer (id_user, quizz_question_id, quizz_question_option_id, answerTime)"
					+ " values (?, ?, ?, ?)");
			
			pstmt.setInt(1, idUser);
			pstmt.setInt(2, questionID);
			pstmt.setInt(3, AnswerID);
			pstmt.setTimestamp(4, timestamp);
			
			pstmt.executeUpdate();
			
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

	}
	
	public static Boolean verificaSeRespondeu(int idUser, int questionID) throws SQLException {
		con = DriverManager.getConnection(DB_URL, USER, PASS);
		String query = "SELECT * FROM quizz.quizz_user_answer where id_user='"+idUser+"' AND quizz_question_id='"+questionID+"'";
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		if(!rs.next()) {
			stmt.close();
			con.close();
			return false;
		} else {
			stmt.close();
			con.close();
			return true;	
		}

	}
	
	public static void apagaResposta(int idUser, int questionID) {
		try {
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			
			String query = "DELETE FROM quizz.quizz_user_answer where id_user='"+idUser+"' AND quizz_question_id='"+questionID+"'";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(query);
			
			
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

	}

	public static Boolean respondeuAoQuizz(int userId, int quizzId) throws SQLException, ClassNotFoundException{
		
		con = DriverManager.getConnection(DB_URL, USER, PASS);
		
		String query = "SELECT DISTINCT quizz_id FROM quizz.quizz_question INNER JOIN quizz.quizz_user_answer ON quizz.quizz_question.id = quizz.quizz_user_answer.quizz_question_id WHERE id_user='"+userId+"' AND quizz_id='"+quizzId+"'";
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		if(!rs.next()) {
			stmt.close();
			con.close();
			return false;
		} else {
			stmt.close();
			con.close();
			return true;	
		}
	}
	
	public static String respostasPrevias(int userId, int quizzId) {
		try {
		con = DriverManager.getConnection(DB_URL, USER, PASS);
		String resultado = "Já respondeu a este inquérito.<br> Responda novamente e submeta caso pretenda alterar as suas respostas.<br> Anteriormente respondeu: <br>";
		String query = "SELECT quizz.quizz_question_option.text FROM quizz.quizz_question_option INNER JOIN quizz.quizz_question ON quizz.quizz_question_option.quizz_question_id = quizz.quizz_question.id INNER JOIN quizz.quizz_user_answer ON quizz.quizz_question_option.id=quizz.quizz_user_answer.quizz_question_option_id WHERE id_user='"+userId+"' AND quizz_id='"+quizzId+"'";
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		int contador = 1;
		while (rs.next()) {
			resultado+=contador+" - '"+rs.getString("text")+"'.<br>";
			contador++;
		}
			stmt.close();
			con.close();
			return resultado;
		} catch(SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return "Ocorreu um erro a determinar se já respondeu a este questionário"; 
	}
	
	public static void insereQuestionario(String title) {
		try {
			con = DriverManager.getConnection(DB_URL, USER, PASS);
			
			Timestamp beginDate = new Timestamp(System.currentTimeMillis());
			Timestamp endDate = new Timestamp(System.currentTimeMillis());
			final Long duration = (long) 172800000; // dois dias!
			endDate.setTime(endDate.getTime() + duration);
			System.out.println(beginDate);
			System.out.println(endDate);
			
			PreparedStatement pstmt = con.prepareStatement("insert into quizz.quizz (title, beginDate, endDate)"
					+ " values (?, ?, ?)");
			
			pstmt.setString(1, title);
			pstmt.setTimestamp(2, beginDate);
			pstmt.setTimestamp(3, endDate);
			pstmt.executeUpdate();
			
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

	}
	public static void inserePergunta(String text, int quizz_id) {
		try {
			con = DriverManager.getConnection(DB_URL, USER, PASS);
					
			PreparedStatement pstmt = con.prepareStatement("insert into quizz.quizz_question (quizz_id, text)"
					+ " values (?, ?)");
			
			pstmt.setInt(1, quizz_id);
			pstmt.setString(2, text);
			pstmt.executeUpdate();
			
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

	}
	
	public static void insereRespostaOpcao(String text, int quizz_question_id) {
		try {
			con = DriverManager.getConnection(DB_URL, USER, PASS);
					
			PreparedStatement pstmt = con.prepareStatement("insert into quizz.quizz_question_option (quizz_question_id, text)"
					+ " values (?, ?)");
			
			pstmt.setInt(1, quizz_question_id);
			pstmt.setString(2, text);
			pstmt.executeUpdate();
			
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

	}
	public static int getTotalAnswersToQuestion(int quizz_question_id) {
		System.out.println("id questao: "+quizz_question_id);
		int totalAnswersToQuestion = 0;
		try {
			con = DriverManager.getConnection(DB_URL, USER, PASS);
					
			String query = "SELECT COUNT(*) FROM quizz.quizz_user_answer WHERE quizz_question_id='"+quizz_question_id+"'";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			totalAnswersToQuestion = rs.getInt("COUNT(*)");
			
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return totalAnswersToQuestion;
	}
	
	public static int getAnswerOcurrence(int id) {
		int answerOcurrence = 0;
		try {
			con = DriverManager.getConnection(DB_URL, USER, PASS);
					
			String query = "SELECT COUNT(*) FROM quizz.quizz_user_answer WHERE quizz_question_option_id='"+id+"'";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			answerOcurrence = rs.getInt("COUNT(*)");
			
			
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return answerOcurrence;

	}
	
	public static ArrayList<User> retrieveAllUsers() throws SQLException, ClassNotFoundException{
		con = DriverManager.getConnection(DB_URL, USER, PASS);
		
		String query = "select * from quizz.user";
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		ArrayList<User> listaUsers = new ArrayList<User>();
		while (rs.next()) {
			User user = new User();
			user.setIdUser(rs.getInt("iduser"));
			user.setFirstName(rs.getString("first_name"));
			user.setLastName(rs.getString("last_name"));
			user.setEmail(rs.getString("email"));
			user.setRole(rs.getString("role"));
			listaUsers.add(user);
		}
		stmt.close();
		con.close();
		return listaUsers;
	}
}
