
public class Car
{
	// private fields
	private int year;
	private String model;
	private String make;
	private int speed;
	private String direction = "north";
	private String north = "north";
	private String south = "south";
	private String east = "east";
	private String west = "west";
	private int accelerate;
	private int brake;


	/**The Car constructor performs initialization and setup operations for year
	 *, model, make, and speed. It uses input validation to prevent an invalid
	 *year to be used
	 *@param inputYear used to change car year  data
	 *@param inputModel used to change car model data
	 *@param inputMake used to change car make data*/
    public Car(int inputYear, String inputModel, String inputMake)
    {

		year = yearValidation(inputYear);
		model = inputModel;
		make = inputMake;
		speed = 0;

    }

    /**The Car default constructor performs initialization and setup for year,
     *model, make, and speed.*/
    public Car()
    {

    	year = 0;
    	model = "";
    	make = "";
    	speed = 0;

    }


	/**The getYear accessor method returns an integer value.
	 *@return a int datatype year */
	public int getYear()
	{

		return year;

	}


	/**The getModel accessor method returns an String value.
	 *@return a String datatype model */
	public String getModel()
	{

		return model;

	}


	/**The getMake accessor method returns an String value.
	 *@return a String datatype make */
	public String getMake()
	{

		return make;

	}


	/**The getSpeed accessor method returns an integer value.
	 *@return a int datatype speed */
	public int getSpeed()
	{

		return speed;

	}


	/**The accelerate method allows the user to change the speed data by adding
	 *5 to it. There is no verification to prevent excessive speed in case the
	 *user needs to avoid capture from persuant police chase or if they want
	 *to break a land speed record */
	public void accelerate()
	{

		speed+= 5;

	}


	/**The brake method allows the user to change the speed data by subtracting
	 *5 from it. It uses speed verification so the vehicle isn't permitted to
	 *be traveling at a speed of less than zero */
	public void brake()
	{

		speedValidation(speed);
		speed-= 5;

	}


	/**The currentSpeed method prints to the console what the current speed is
	 * */
	public void currentSpeed()
	{

		System.out.println("Current Speed is " + speed);

	}


	/**The rightTurn method changes the direction of the car */
	public void rightTurn()
	{

		if (direction.equals(north))
		{

			direction = "east";

		}

		else if (direction.equals(east))
		{

			direction = "south";

		}

		else if (direction.equals(south))
		{

			direction = "west";

		}

		else if (direction.equals(west))
		{

			direction = "north";

		}

	}
	/**The rightTurn method changes the direction of the car */
	public void leftTurn()
	{

		if (direction.equals(north))
		{

			direction = "west";

		}

		else if (direction.equals(east))
		{

			direction = "north";

		}

		else if (direction.equals(south))
		{

			direction = "east";

		}

		else if (direction.equals(west))
		{

			direction = "south";

		}

	}

	/**The getDirection method returns a String value direction
	 *@return a String value direction */
	public String getDirection()
	{
		return direction;
	}

	/**The yearValidation method checks to make sure that the data the user
	 *enters for the year of the car is a non-negative date.
	 *@return an integer data type date that is unchanged from what the user
	 *entered.
	 *@return an integer data type 99999 that indicates an error occurred with
	 *the input of the date and it did not pass verification */
	private int yearValidation(int data)
	{

		if (data < 0)
		{

			data = 99999;
			return data;
		}

		else
		{

			return data;

		}

	}
	/**The speedValidation method checks the speed for either being too fast or
	 *for having a speed of less than 5. It is intended to be used to prevent
	 *the speed to show has a negative number if the
	 *break is applied while the car has a speed of zero (standing still). */
	private void speedValidation(int theSpeed)
	{

		// a car cannot reduce speed below 0
		if(theSpeed < 5)
		{

			accelerate();

		}

	}

}