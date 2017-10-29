#include "fileParser.h"
#include <iostream>
#include <stdio.h>
#pragma warning(disable:4996)
using namespace std;

fileParser::fileParser(){}
fileParser::~fileParser(){}
customer* fileParser::parsecustomerfile(char* file_name)
{
	FILE* fp;
	static int which_row_to_read = 0;
	customer* to_ctr;
	char temp1[150];
	int i = 0, z = 0;
	char temp_customer_id[30], temp_customer_funds[30], first_customer_name[30], last_customer_name[30];
	char* full_customer_name;
	int customer_id = 0;
	float customer_funds = 0.0;
	fp = fopen(file_name, "r");
	if (fp == NULL)
	{
		cout << "the file cannot be opened" << endl;
		return 0;
	}
	
	if (which_row_to_read >=0)
	{
		for (int temp = 0; temp < which_row_to_read; temp++)
		{
			while (fgets(temp1, 200, fp))
			{
				if (strcmp(temp1, "\n") == 1) break;
			}
		}
		fscanf(fp, "%s %s %s %s", first_customer_name, last_customer_name, temp_customer_id, temp_customer_funds);
		which_row_to_read++;
		fclose(fp);
	}

	last_customer_name[strlen(last_customer_name) - 1] = '\0';
	int len = (strlen(first_customer_name) + strlen(last_customer_name) + 2);
	full_customer_name = new char[len];
	for (i = 0; i<strlen(first_customer_name); i++)
	{
		full_customer_name[i] = first_customer_name[i];
	}
	full_customer_name[i] = ' ';
	for (int i = (strlen(first_customer_name)+1); i<len;i++)
	{
		full_customer_name[i] = last_customer_name[z];
		z++;
	}
	full_customer_name[strlen(first_customer_name)] = ' ';
	customer_id = atoi(temp_customer_id);
	customer_funds = atof(temp_customer_funds);	
	//cout << "first name is - " << full_customer_name << endl << "and id name is - " << customer_id << endl << "and the funds are - " << customer_funds<< endl;
	to_ctr = new customer(full_customer_name, customer_id, customer_funds, NULL);
	return to_ctr;
}


car* fileParser::parsecarfile(char* file_name)
{
	char temp1[120];
	static int which_row_to_read = 0;
	car* ptr_to_car = NULL;
	FILE* fpp;
	//static int i = 0;
	char answer_to_brand[30], answer_to_model[30], answer_to_lights_temp[30], answer_to_volume[30], answer_to_maxfuel[30], answer_to_currentfuel[30], answer_to_malfunction_temp[30], answer_to_psi1[30], answer_to_radius1[30], answer_to_flat1[30], answer_to_psi2[30], answer_to_radius2[30], answer_to_flat2[30], answer_to_psi3[30], answer_to_radius3[30], answer_to_flat3[30], answer_to_psi4[30], answer_to_radius4[30], answer_to_flat4[30], answer_to_id2[30];
	bool answer_to_lights = false;
	bool answer_to_malfunction = false;
	bool answer_to_flat1a = false;
	bool answer_to_flat2a = false;
	bool answer_to_flat3a = false;
	bool answer_to_flat4a = false;
	//
	fpp = fopen(file_name, "r");

	if (fpp == NULL)
	{
		cout << "The Car file could not be opened" << endl;
		return 0;
	}
	
	if (which_row_to_read >=0)
	{
		for (int temp = 0; temp < which_row_to_read; temp++)
		{
			while (fgets(temp1, 200, fpp))
			{
				if (strcmp(temp1, "\n") == 1) break;
			}
		}
		fscanf(fpp, "%s %s %s %s %s %s %s %s %s %s %s %s %s %s %s %s %s %s %s %s", answer_to_brand, answer_to_model, answer_to_lights_temp, answer_to_volume, answer_to_maxfuel, answer_to_currentfuel, answer_to_malfunction_temp, answer_to_psi1, answer_to_radius1, answer_to_flat1, answer_to_psi2, answer_to_radius2, answer_to_flat2, answer_to_psi3, answer_to_radius3, answer_to_flat3, answer_to_psi4, answer_to_radius4, answer_to_flat4, answer_to_id2);
		which_row_to_read++;
		fclose(fpp);
	}

	int x = strlen(answer_to_brand);
	answer_to_brand[x - 1] = '\0';
	x = strlen(answer_to_model);
	answer_to_model[x - 1] = '\0';
	if (answer_to_lights_temp[0] == 't') answer_to_lights = true;
	float answer_to_volume2 = atof(answer_to_volume);
	float answer_to_maxfuel2 = atof(answer_to_maxfuel);
	float answer_to_currentfuel2 = atof(answer_to_currentfuel);
	if (answer_to_malfunction_temp[0] == 't')answer_to_malfunction = true;
	if (answer_to_flat1[0] == 't')answer_to_flat1a = true;
	if (answer_to_flat2[0] == 't')answer_to_flat2a = true;
	if (answer_to_flat3[0] == 't')answer_to_flat3a = true;
	if (answer_to_flat4[0] == 't')answer_to_flat4a = true;
	int answer_to_psi1a = atoi(answer_to_psi1);
	int answer_to_psi2a = atoi(answer_to_psi2);
	int answer_to_psi3a = atoi(answer_to_psi3);
	int answer_to_psi4a = atoi(answer_to_psi4);
	float answer_to_radius1a = atof(answer_to_radius1);
	float answer_to_radius2a = atof(answer_to_radius2);
	float answer_to_radius3a = atof(answer_to_radius3);
	float answer_to_radius4a = atof(answer_to_radius4);
	int answer_to_id2a = atoi(answer_to_id2);

	ptr_to_car = new car(answer_to_brand, answer_to_model, answer_to_lights, answer_to_volume2, answer_to_maxfuel2, answer_to_currentfuel2, answer_to_malfunction, answer_to_psi1a, answer_to_radius1a, answer_to_flat1a, answer_to_psi2a, answer_to_radius2a, answer_to_flat2a, answer_to_psi3a, answer_to_radius3a, answer_to_flat3a, answer_to_psi4a, answer_to_radius4a, answer_to_flat4a, answer_to_id2a);

	return ptr_to_car;
	}