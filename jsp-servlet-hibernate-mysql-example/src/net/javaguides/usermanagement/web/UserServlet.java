package net.javaguides.usermanagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.SystemException;

import net.javaguides.usermanagement.User;
import net.javaguides.usermanagement.dao.UserDAO;


@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDao;
	
	public void init() {
        userDao = new UserDAO();
    }
       
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getServletPath();
		System.out.println(action);
		try {
			
			switch (action) {
				case "/new":
					showNewform(request, response);
					break;
				case "/insert":
                    insertUser(request, response);
                    break;
                case "/delete":
                    deleteUser(request, response);
                    break;
                case "/edit":
                    showEditform(request, response);
                    break;
                case "/update":
                    updateUser(request, response);
                    break;
                default:
                    listUser(request, response);
                    break;
					
			}
			
		}catch (ServletException | IllegalStateException | SystemException | SQLException ex) {
			throw new ServletException(ex);
		}
			
	}
	// when user click lets say show user list, then this action should be used to 
	private void listUser(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, SystemException, ServletException, IOException {
		List < User > listUser = userDao.getAllUser();
		request.setAttribute("listUser", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("List-user.jsp");
		dispatcher.forward(request, response);
		
	}
	// when you want to show the new create user form
	private void showNewform(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		dispatcher.forward(request, response);
	}
	
	// when you want to click the edit button this is for displaying the edit form for an existing user.
	private void showEditform(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, SystemException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		User existingUser = userDao.getUser(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		//allows the "user-form.jsp" page to access the user object and display its details in the form fields.
		request.setAttribute("user", existingUser);
		dispatcher.forward(request, response);
	}
	
	private void insertUser(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, SystemException, ServletException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		User newUser = new User(name, email, country);
		userDao.saveUser(newUser);
		response.sendRedirect("list");		
	}
	
	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, SystemException, ServletException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		// after display the the user you want to edit in the user form page ,and then use updateUser to update
		User user = new User(name, email, country);
		userDao.updateUser(user);
		// that is the defalt pahe
        response.sendRedirect("list");
	}
	
	private void deleteUser(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, IOException, IllegalStateException, SystemException {
		        int id = Integer.parseInt(request.getParameter("id"));
		        userDao.deleteUser(id);
		        response.sendRedirect("list");
		    }

}
