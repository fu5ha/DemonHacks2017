package carrental;


import carrental.model.Car;
import carrental.model.RentalCar;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AddCarServlet
  extends HttpServlet
{
  private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

  public void init(ServletConfig config)
    throws ServletException
  {
    super.init(config);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {

    response.setContentType(CONTENT_TYPE);
    // get parameters from the form
    String name = request.getParameter("name");
    String milesString = request.getParameter("miles");
    //        String gallonsString = request.getParameter("gallons");
    // should really do some sanity check here for null  or bad values from form
    //double gallons = Double.parseDouble(gallonsString);
    double miles = Double.parseDouble(milesString);

    Car aNewCar = new RentalCar(name, miles); // create a new Car

    ServletContext context = this.getServletContext();
    List<Car> carList = (List<Car>) context.getAttribute("Cars");

    // first time we need to initialize the context
    if (carList == null)
    {
      carList = new ArrayList<Car>();
      context.setAttribute("Cars", carList);
    }

    carList.add(aNewCar); // and the newly created Car

    response.sendRedirect("listcars"); // redirect to list page

  }
}
