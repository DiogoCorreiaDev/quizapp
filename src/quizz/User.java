package quizz;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;


@Named("user") 
@SessionScoped
public class User implements Serializable, Comparable<User> {
	private static final long serialVersionUID = 1L;
	private int idUser;
	private String firstName;
	private String lastName;
	private String email;
	private String password2;
	private String password;
	private String role;
	private ArrayList<User> listaUsers;
	
	public User() {
		super();
	}
	
	public void mudaRole(ValueChangeEvent event) {
		 int userId = (int) ((UIInput)event.getSource()).getAttributes().get("myId");
		 //System.out.println(userId);
		 String newRole = event.getNewValue().toString();
		 String oldRole = (String) ((UIInput)event.getSource()).getAttributes().get("oldRole");
		 if (!newRole.equals(oldRole)) {
			 Database.actualizaRole(newRole, userId);
			 FacesContext context = FacesContext.getCurrentInstance();
			 context.addMessage(null, new FacesMessage("O role do utilizador foi alterado com sucesso."));
		 }	 
	}
	

	public void registarUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
			if(Database.emailExists(this.email)) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de registo. O email inserido já existe na base de dados.", null));
			} else if(!this.password.equals(this.password2)) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "As password inseridas não são iguais!", null));
			} else {
			    Database.insereUser(this.email, this.firstName, this.lastName, this.password, "user");
			    context.addMessage(null, new FacesMessage("O seu registo foi efectuado com sucesso."));
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Erro classe não encontrada");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	
	
	
	public ArrayList<User> getListaUsers() {
		
		try {
			listaUsers = Database.retrieveAllUsers();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Erro classe não encontrada");
		}
		return listaUsers;
	}

	public void setListaUsers(ArrayList<User> listaUsers) {
		this.listaUsers = listaUsers;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public void login() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        try {
			if(!Database.emailExists(this.email)) {
				context.addMessage(null, new FacesMessage("Erro de autenticação. O email inserido não existe na base de dados."));
			} else {
			    
			    if (Database.checkPass(this.email, this.password)) {
			    	User utilizador = Database.getUser(this.email);
			    	context.getExternalContext().getSessionMap().put("idUser", utilizador.getIdUser());
			    	context.getExternalContext().getSessionMap().put("firstName", utilizador.getFirstName());
			    	context.getExternalContext().getSessionMap().put("lastName", utilizador.getLastName());
			        context.getExternalContext().getSessionMap().put("email", email);
			        context.getExternalContext().getSessionMap().put("role", utilizador.getRole());
			        try {
						context.getExternalContext().redirect("Perfil.xhtml");
					} catch (IOException e) {
						System.out.println("Ocorreu um erro a direcionar para perfil do utilizador.");
					}
			    }
			    else  {
			        context.addMessage(null, new FacesMessage("Erro de autenticação. A combinação inserida de email/password não existe na base de dados."));
			    }
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Erro classe não encontrada");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    public void logout() {
    	FacesContext context = FacesContext.getCurrentInstance();
    	context.getExternalContext().invalidateSession();
        try {
			context.getExternalContext().redirect("Login.xhtml");
		} catch (IOException e) {
			System.out.println("Ocorreu um erro de IOException ao fazer logout.");
		}
    }
	
	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().put("email", email);
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", role=" + role + "]";
	}

	@Override
	public int compareTo(User o) {
		return Integer.compare(this.idUser, o.idUser);
	}

	@Override
	public boolean equals(Object o) {
		return this.idUser==((User)o).idUser;
	}

	
	
}
