#include <iostream>
#include "garage.h"
#include "car.h"
using namespace std;

garage::garage()
{
	garage::setcars_in_garage();
	garage::setfull_customers_list();
	garage::setcustomers_in_garage();
	garage::setfunds_start(0);
	garage::sethow_many_in_garage_start(0);
}
garage::~garage()
{
	cout << "canceling the garage" << endl;
}

void garage::setcars_in_garage()
{
	car* cars_in_garage[amount_of_clients_from_file] = { NULL };
	
	if (cars_in_garage == NULL)
	{
		cout << "out of memory" << endl;
	}
}

void garage::setfull_customers_list()
{
	customer* full_customers_list[amount_of_clients_from_file] = { NULL };
	if (full_customers_list == NULL)
	{
		cout << "out of memory" << endl;
	}
}

void garage::setcustomers_in_garage()
{
	customer* customers_in_garage[amount_of_clients_from_file] = { NULL };
	if (customers_in_garage == NULL)
	{
		cout << "out of memory" << endl;
	}
}

void garage::setfunds(double funds)
{
	double new_funds = getfunds_garage();
	new_funds = new_funds + funds;
	this->funds = new_funds;
}
void garage::sethow_many_in_garage_start(int how_many_in_garage)
{
	this->how_many_in_garage = how_many_in_garage;

}
void garage::sethow_many_in_garage(int to_change)
{
	this->how_many_in_garage = how_many_in_garage + (to_change);
}
void garage::setfunds_start(double funds)
{
	this->funds = funds;
}

void garage::set_create_full_customers_list(customer* to_add)
{
	static int where_to_add = 0;
	if (where_to_add == amount_of_clients_from_file)
	{
		 cout << "the list is full" << endl;
	}
	
	full_customers_list[where_to_add] = to_add;
	++where_to_add;
}

bool garage::addcustomer(customer& cr)
{
	if (&cr == NULL)return false;
	if (gethow_many_in_garage()-1 == amount_of_clients_from_file)
	{
		cout << "error - the list is full" << endl;
		return false;

	}
	else
	{
		customers_in_garage[gethow_many_in_garage()] = &cr;
		garage::sethow_many_in_garage(1);
		garage::checkincar(cr.getcar());
		cr.setcurrentcar(NULL);
		return true;
	}
}

bool garage::checkincar(car* cr)
{
	if (&cr == NULL)return false;
	if (gethow_many_in_garage()-1 == amount_of_clients_from_file)
	{
		cout << "the list is full" << endl;
		return false;

	}
	else
	{
		cars_in_garage[gethow_many_in_garage() - 1] = cr;
		cr->setinshop(true);
		return true;
	}
}


int garage::which_car_is_mine(customer& cst)
{
	int i = 0;
	for (i = 0; i < amount_of_clients_from_file; ++i)
	{
		if (cst.getid() == cars_in_garage[i]->getcarid())
		{
			return i;
		}
	}
	return (i = -1);
}


bool garage::checkoutcar(car& cr)
{
	int i = 0;
	while (cr.getcarid() != customers_in_garage[i]->getid()) i++;
	customers_in_garage[i]->setcurrentcar(&cr);
	garage::checkout(*customers_in_garage[i]);

	if ((cr.getmotor().getmalfunction() == true) || (cr.getmotor().getmaxfuel() <= 20) || (cr.getmotor().getfuel() <= 0) || (cr.getmotor().getvolume() <= 1300))
	{
		cr.getmotor().setvolume(1350);
		cr.getmotor().setfuel(100);
		cr.getmotor().setmaxfuel(1000);
		cr.getmotor().setmalfunction(false);
		cr.getmotor().activate(false);

	}
	if (cr.getlights().getmalfunction() == true)
	{
		cr.getlights().setlights_sending(true);
		cr.getlights().setactive(false);
		cr.getlights().setmalfunction(false);
	}
	if ((cr.getwheel1()->getflat() == true) || (cr.getwheel1()->getpsi() <= 30) || (cr.getwheel1()->getradius() <= 5))
	{
		cr.getwheel1()->setradius(16);
		cr.getwheel1()->setpsi(32);
		cr.getwheel1()->setflat(false);
	}
	if ((cr.getwheel2()->getflat() == true) || (cr.getwheel2()->getpsi() <= 30) || (cr.getwheel2()->getradius() <= 5))
	{
		cr.getwheel2()->setradius(16);
		cr.getwheel2()->setpsi(32);
		cr.getwheel2()->setflat(false);
	}
	if ((cr.getwheel3()->getflat() == true) || (cr.getwheel3()->getpsi() <= 30) || (cr.getwheel3()->getradius() <= 5))
	{
		cr.getwheel3()->setradius(16);
		cr.getwheel3()->setpsi(32);
		cr.getwheel3()->setflat(false);
	}
	if ((cr.getwheel4()->getflat() == true) || (cr.getwheel4()->getpsi() <= 30) || (cr.getwheel4()->getradius() <= 5))
	{
		cr.getwheel4()->setradius(16);
		cr.getwheel4()->setpsi(32);
		cr.getwheel4()->setflat(false);
	}
	cout << "the car has been fixed and restored to his owner" << endl;
	i = 0;
	while (cr.getcarid() != cars_in_garage[i]->getcarid())++i;

	if (gethow_many_in_garage() > 1)
	{
		for (i; i < gethow_many_in_garage(); i++)
		{
			cars_in_garage[i] = cars_in_garage[i+1];
		}
	}

	else if (gethow_many_in_garage() == 1)
	{
		cars_in_garage[0] = NULL;
	}
	sethow_many_in_garage(-1);

	return true;

}

bool garage::checkout(customer& cst)
{
	if (&cst == NULL)return false;
	garage::setfunds(calculateprice(cst.getcar()));
	cst.setfunds(cst.getfunds() - calculateprice(cst.getcar()));
	int i = 0;
	while (cst.getid() != customers_in_garage[i]->getid())++i;
	if (gethow_many_in_garage() > 1)
	{
		for (i; i < gethow_many_in_garage(); i++)
		{
			customers_in_garage[i] = customers_in_garage[i + 1];
		}
	}
	else if (gethow_many_in_garage() == 1)
	{
		customers_in_garage[0] = NULL;
	}
	return true;
}

double garage::calculateprice(car* cr)
{
	int how_much_to_fix = 0, price_to_fix=5, total_price=0;
	if (cr->getmotor().getmalfunction() == true) how_much_to_fix++;
	if (cr->getlights().getmalfunction() == true) how_much_to_fix++;
	if (cr->getwheel1()->getflat() == true)	how_much_to_fix++;
	if (cr->getwheel2()->getflat() == true) how_much_to_fix++;
	if (cr->getwheel3()->getflat() == true) how_much_to_fix++;
	if (cr->getwheel4()->getflat() == true) how_much_to_fix++;
	total_price = how_much_to_fix*price_to_fix;
	//cout << "the price of the fix is - " << total_price << endl;
	return total_price;
}
bool garage::print_cars_in_garage()
{
	if (gethow_many_in_garage() == 0)
	{
		cout<< "The list is empty, There are no cars in the garage so we have nothing to display" << endl;
		return false;
	}
	else
	{
		for (int i = 0; i < gethow_many_in_garage(); ++i)
		{
			cout << i + 1 << ".\t" << cars_in_garage[i]->getbrand() << "  " << cars_in_garage[i]->getmodel() << endl;
		}
		return true;
	}
}

bool garage::print_customers_in_garage()
{
	if (gethow_many_in_garage() == 0)
	{
		cout<< "The list is empty, There are no customers in the garageso we have nothing to display" << endl;
		return false;
	}
	else 
	{
		for (int i = 0; i < gethow_many_in_garage(); ++i)
		{
			cout << i + 1 << ".\t" << customers_in_garage[i]->getfullname() << endl;
		}
		return true;
	}

}
car* garage::getcars_in_garage(int which_one)
{
	return cars_in_garage[which_one];
}
customer* garage::getcustomers_in_garage(int which_one)
{
	return customers_in_garage[which_one];
}