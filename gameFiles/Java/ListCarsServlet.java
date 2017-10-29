package carrental;


import carrental.helper.CarRentalLogger;

import carrental.helper.XMLHelper;
import carrental.model.Car;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ListCarsServlet
  extends HttpServlet
{
  private static final String CONTENT_TYPE = "text/html; charset=windows-1252";


  public void init(ServletConfig config)
    throws ServletException
  {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setContentType(CONTENT_TYPE);
    CarRentalLogger carLogger = new CarRentalLogger();
    PrintWriter out = response.getWriter();
    ServletContext globalData = this.getServletContext();
    XMLHelper myXMLHelper = new XMLHelper();
    try
    {
      List carsList = (List) globalData.getAttribute("Cars");
      out.println("<html>");
      out.println("<head><title>ListCarsServlet</title></head>");
      out.println("<link type=\"text/css\" rel=\"stylesheet\" href=\"css/jdeveloper.css\"/>");
      out.println("<body bgcolor='white'>");
      out.println("<h1>List Servlet Context Cars</h1>");
      out.println("<h3><a href=\"ClearCarListServlet\"> Clear Car List </a></h3>");
      out.println("<p>You currently have <b>" + carsList.size() + "</b> Cars in your Fleet:</p><br/>");
      out.println("<table class=\"borders\" cellspacing=\"2\" cellpadding=\"3\" border=\"1\">");
      out.println("<tr>");
      out.println("    <td>Car Name</th>");
      out.println("    <td>Car Miles</th>");
      out.println("</tr>");

      Iterator it = carsList.iterator();
      while (it.hasNext())
      {
        Car item = (Car) it.next();
        out.println("<tr>");
        out.println("    <td>" + item.getName() + "</td>");
        out.println("    <td>" + item.getMiles() + "</td>");
        out.println("</tr>");
      }
      out.println("</table>");
    }
    catch (NullPointerException e)
    {
      out.println("<code>Exception: " + e + "</code><br/><br/>");
      out.println("<code>No cars found!</code><br/><br/>");
      carLogger.Logger("NullPointerException : " + e, e);
    }
    catch (Exception e)
    {
      out.println("<code>Exception: " + e + "</code><br/><br/>");
      carLogger.Logger("Exception: " + e, e);
    }
    out.println("<h3><a href=\"home.jsp\"> Home </a></h3>");
    out.println("<h3><a href=\"addCar.jsp\"> Add Rental Car </a></h3>");
    out.println("<h3><a href=\"configDb.jsp\"> Configure Database Connectivity </a></h3>");
    out.println("</body></html>");
    out.close();
  }
}

