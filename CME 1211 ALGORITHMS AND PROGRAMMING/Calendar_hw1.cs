using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;

namespace Calendar_Trial1
{
    class Program
    {
        static void Main(string[] args)
        {
            int day_1 = 0, year_1 = 0, month_1_number = 0;
            string month_1, month_1_trim;
            bool monthnumber1 = false;
            int season_1 = 0;

            int day_2 = 0, year_2 = 0, month_2_number = 0;
            string month_2, month_2_trim;
            bool monthnumber2 = false;

            int early_date;
            int ti;
            int n;
            string ts;
            
            ConsoleKeyInfo keyInfo;


            while (true)//Infinite loop
            {
                try//Thanks to this code porgram cannot error.
                {
                    Console.Write("Please enter the day of first date: ");
                    day_1 = Convert.ToInt32(Console.ReadLine());
                    if (day_1 < 1 || day_1 > 31)
                    {
                        Console.WriteLine("Day is wrong. Please type anything to exit!");
                        continue;
                    }
                }
                catch
                {
                    Console.WriteLine("Day must be an number. Please type anything to exit!");
                    continue;
                }


                Console.Write("Please enter the month of first date: ");
                month_1 = Console.ReadLine();
                month_1_trim = month_1.Trim().Replace(" ", "");// To delete spaces.
                month_1 = month_1_trim.ToLower();
                monthnumber1 = month_1.Any(c => char.IsDigit(c));
                if (monthnumber1 == true)
                {
                    Console.WriteLine("Month must be written as word. Please type anything to exit!");
                    continue;
                }
                if ((month_1 != "january") && (month_1 != "february") && (month_1 != "march") && (month_1 != "april") && (month_1 != "may") && (month_1 != "june") && (month_1 != "july") && (month_1 != "august") && (month_1 != "september") && (month_1 != "october") && (month_1 != "november") && (month_1 != "december"))
                {
                    Console.WriteLine("Month is wrong. Please type anything to exit!");
                    continue;
                }


                try
                {
                    Console.Write("Please enter the year of first date: ");
                    year_1 = Convert.ToInt32(Console.ReadLine());
                    if (year_1 < 2015)
                    {
                        Console.WriteLine("Year must be grater than 2015. Please type anything to exit!");
                        continue;
                    }
                }
                catch
                {
                    Console.WriteLine("Year must be an integer!");
                    Console.WriteLine("Please type anything to exit!");
                    continue;
                }


                month_1_number = convert_month_number(month_1);//Checks date if exist.
                if (check_date(day_1, month_1_number, year_1) == false)
                {
                    Console.WriteLine("Day is wrong. Please type anything to exit!");
                    continue;
                }


                Console.WriteLine();


                try
                {
                    Console.Write("Please enter the day of second date: ");
                    day_2 = Convert.ToInt32(Console.ReadLine());
                    if (day_2 < 1 || day_2 > 31)
                    {
                        Console.WriteLine("Day is wrong. Please type anything to exit!");
                        continue;
                    }
                }
                catch
                {
                    Console.WriteLine("Day must be an number. Please type anything to exit!");
                    continue;
                }


                Console.Write("Please enter the month of first date: ");
                month_2 = Console.ReadLine();
                month_2_trim = month_2.Trim().Replace(" ", "");//To delete spaces.
                month_2 = month_2_trim.ToLower();
                monthnumber2 = month_2.Any(c => char.IsDigit(c));
                if (monthnumber2 == true)
                {
                    Console.WriteLine("Month must be written as word. Please type anything to exit!");
                    continue;
                }
                if ((month_2 != "january") && (month_2 != "february") && (month_2 != "march") && (month_2 != "april") && (month_2 != "may") && (month_2 != "june") && (month_2 != "july") && (month_2 != "august") && (month_2 != "september") && (month_2 != "october") && (month_2 != "november") && (month_2 != "december"))
                {
                    Console.WriteLine("Month is wrong. Please type anything to exit!");
                    continue;
                }


                try
                {
                    Console.Write("Please enter the year of second date: ");
                    year_2 = Convert.ToInt32(Console.ReadLine());
                    if (year_2 < 2015)
                    {
                        Console.WriteLine("Year must be grater than 2015. Please type anything to exit!");
                        continue;
                    }
                }
                catch
                {
                    Console.WriteLine("Year must be an integer!");
                    Console.WriteLine("Please type anything to exit!");
                    continue;
                }

                //If dates are same thanks to these code program will shut down.
                if ((year_1 == year_2) && (month_1 == month_2) && (day_1 == day_2))
                {
                    Console.WriteLine("Dates are same please write two different dates. Please type anything to exit!");
                    continue;
                }


                month_2_number = convert_month_number(month_2);//Checks date if exist.
                if (check_date(day_2, month_2_number, year_2) == false)
                {
                    Console.WriteLine("Day is wrong. Please type anything to exit!");
                    continue;
                }


                Console.WriteLine();


                try
                {
                    Console.Write("Please enter value of n: ");
                    n = Convert.ToInt32(Console.ReadLine());
                    if (n < 1)
                    {
                        Console.WriteLine("'n' must be an positive integer. Please type anything to exit!");
                        continue;
                    }
                }
                catch
                {
                    Console.WriteLine("'n' must be an positive integer. Please type anything to exit!");
                    continue;
                }


                early_date = earlier_date_find(day_1, month_1_number, year_1, day_2, month_2_number, year_2);
                if (early_date == 2)
                {
                    ti = day_1;
                    day_1 = day_2;
                    day_2 = ti;

                    ti = month_1_number;
                    month_1_number = month_2_number;
                    month_2_number = ti;

                    ti = year_1;
                    year_1 = year_2;
                    year_2 = ti;

                    ts = month_1;
                    month_1 = month_2;
                    month_2 = ts;
                }

                //These are the codes which detecs the first months season.
                if (month_1_number == 3 || month_1_number == 4 || month_1_number == 5)
                    season_1 = 1;
                else if (month_1_number == 6 || month_1_number == 7 || month_1_number == 8)
                    season_1 = 2;
                else if (month_1_number == 9 || month_1_number == 10 || month_1_number == 11)
                    season_1 = 3;
                else if (month_1_number == 12 || month_1_number == 1 || month_1_number == 2)
                    season_1 = 4;

                write_date(day_1, month_1_number, year_1, n, day_2, month_2_number, year_2, season_1);

                //If user wants to leave he can just press escape.
                Console.WriteLine("If you want to exit please press Escape!");
                keyInfo = Console.ReadKey(true);
                if (keyInfo.Key == ConsoleKey.Escape)
                    return;
            }
        }

        //Converts string to integer.
        public static int convert_month_number(string month)
        {
            int month_number = 0;
            switch (month)
            {
                case "january":
                    month_number = 1;
                    month = "January";
                    break;

                case "february":
                    month_number = 2;
                    month = "February";
                    break;

                case "march":
                    month_number = 3;
                    month = "March";
                    break;

                case "april":
                    month_number = 4;
                    month = "April";
                    break;

                case "may":
                    month_number = 5;
                    month = "May";
                    break;

                case "june":
                    month_number = 6;
                    month = "June";
                    break;

                case "july":
                    month_number = 7;
                    month = "July";
                    break;

                case "august":
                    month_number = 8;
                    month = "August";
                    break;

                case "september":
                    month_number = 9;
                    month = "September";
                    break;

                case "october":
                    month_number = 10;
                    month = "October";
                    break;

                case "november":
                    month_number = 11;
                    month = "November";
                    break;

                case "december":
                    month_number = 12;
                    month = "December";
                    break;
            }
            return month_number;
        }

        //Converts integer to string.
        public static string convert_number_month(int number)
        {
            string month = "";
            switch (number)
            {
                case 1:
                    month = "January";
                    break;

                case 2:
                    month = "February";
                    break;

                case 3:
                    month = "March";
                    break;

                case 4:
                    month = "April";
                    break;

                case 5:
                    month = "May";
                    break;

                case 6:
                    month = "June";
                    break;

                case 7:
                    month = "July";
                    break;

                case 8:
                    month = "August";
                    break;

                case 9:
                    month = "September";
                    break;

                case 10:
                    month = "October";
                    break;

                case 11:
                    month = "November";
                    break;

                case 12:
                    month = "December";
                    break;
            }
            return month;
        }

        //Checks date if exist.
        public static bool check_date(int day, int month, int year)
        {
            int year_type = 0;
            bool result = true;

            //common year == 0 leapyear == 1
            if (((year % 100) != 0 && (year % 100) % 4 == 0) || ((year % 100) == 0 && (year % 400 == 0) && (year % 4000 != 0)))
            {
                year_type = 1;
            }
            else if (((year % 100) != 0 && (year % 100) % 4 != 0) || ((year % 100) == 0 && (year % 400 != 0)) || (year % 4000 == 0))
            {
                year_type = 0;
            }

            //
            switch (month)
            {
                case 1:
                    if (day > 31)
                    {
                        result = false;
                    }
                    break;

                case 2:
                    if (year_type == 1 && day > 29)
                    {
                        result = false;
                    }
                    else if (year_type == 0 && day > 28)
                    {
                        result = false;
                    }
                    break;

                case 3:
                    if (day > 31)
                    {
                        result = false;
                    }
                    break;

                case 4:
                    if (day > 30)
                    {
                        result = false;
                    }
                    break;

                case 5:
                    if (day > 31)
                    {
                        result = false;
                    }
                    break;

                case 6:
                    if (day > 30)
                    {
                        result = false;
                    }
                    break;

                case 7:
                    if (day > 31)
                    {
                        result = false;
                    }
                    break;

                case 8:
                    if (day > 31)
                    {
                        result = false;
                    }
                    break;

                case 9:
                    if (day > 30)
                    {
                        result = false;
                    }
                    break;

                case 10:
                    if (day > 31)
                    {
                        result = false;
                    }
                    break;

                case 11:
                    if (day > 30)
                    {
                        result = false;
                    }
                    break;

                case 12:
                    if (day > 31)
                    {
                        result = false;
                    }
                    break;
            }
            if (result == false)
            {
                return result;
            }
            else
            {
                return result;
            }
        }

        //Finds earlier date.
        public static int earlier_date_find(int day_1, int month_1, int year_1, int day_2, int month_2, int year_2)
        {
            int earlier_date = 0;
            if (year_2 > year_1)
            {
                earlier_date = 1;
            }
            else if (year_1 > year_2)
            {
                earlier_date = 2;
            }
            else if (year_1 == year_2)
            {
                if (month_2 > month_1)
                {
                    earlier_date = 1;
                }
                else if (month_1 > month_2)
                {
                    earlier_date = 2;
                }
                else if (month_1 == month_2)
                {
                    if (day_2 > day_1)
                    {
                        earlier_date = 1;
                    }
                    else if (day_1 > day_2)
                    {
                        earlier_date = 2;
                    }
                }
            }
            return earlier_date;
        }

        //Writes everything.
        public static void write_date(int day_1, int month_1, int year_1, int n, int day_2, int month_2, int year_2, int season)
        {
            int day_number;
            int year_type = 0;
            int k = 0;
            string month_name;
            int counterspring, countersummer, counterautumn, counterwinter;
            counterspring = countersummer = counterautumn = counterwinter = 0;
            if (season == 1)
            {
                counterspring = 0;
                countersummer = counterautumn = counterwinter = 1;

            }
            else if (season == 2)
            {
                countersummer = 0;
                counterspring = counterautumn = counterwinter = 1;
            }
            else if (season == 3)
            {
                counterautumn = 0;
                counterspring = countersummer = counterwinter = 1;
            }
            else if (season == 4)
            {
                counterwinter = 0;
                counterspring = countersummer = counterautumn = 1;
            }
            while (earlier_date_find(day_1, month_1, year_1, day_2, month_2, year_2) == 1)
            {
                while (!check_date(day_1, month_1, year_1))
                {
                    //common year == 0 leapyear == 1
                    if (((year_1 % 100) != 0 && (year_1 % 100) % 4 == 0) || ((year_1 % 100) == 0 && (year_1 % 400 == 0) && (year_1 % 4000 != 0)))
                    {
                        year_type = 1;
                    }
                    else if (((year_1 % 100) != 0 && (year_1 % 100) % 4 != 0) || ((year_1 % 100) == 0 && (year_1 % 400 != 0)) || (year_1 % 4000 == 0))
                    {
                        year_type = 0;
                    }

                    
                    switch (month_1)
                    {
                        case 1:
                            if (day_1 > 31)
                            {
                                day_1 -= 31;
                                month_1++;
                            }
                            break;

                        case 2:
                            if (year_type == 0 && day_1 > 28)
                            {
                                day_1 -= 28;
                                month_1++;
                            }
                            else if (year_type == 1 && day_1 > 29)
                            {
                                day_1 -= 29;
                                month_1++;
                            }
                            break;

                        case 3:
                            if (day_1 > 31)
                            {
                                day_1 -= 31;
                                month_1++;
                            }
                            break;

                        case 4:
                            if (day_1 > 30)
                            {
                                day_1 -= 30;
                                month_1++;
                            }
                            break;

                        case 5:
                            if (day_1 > 31)
                            {
                                day_1 -= 31;
                                month_1++;
                            }
                            break;

                        case 6:
                            if (day_1 > 30)
                            {
                                day_1 -= 30;
                                month_1++;
                            }
                            break;

                        case 7:
                            if (day_1 > 31)
                            {
                                day_1 -= 31;
                                month_1++;
                            }
                            break;

                        case 8:
                            if (day_1 > 31)
                            {
                                day_1 -= 31;
                                month_1++;
                            }
                            break;

                        case 9:
                            if (day_1 > 30)
                            {
                                day_1 -= 30;
                                month_1++;
                            }
                            break;

                        case 10:
                            if (day_1 > 31)
                            {
                                day_1 -= 31;
                                month_1++;
                            }
                            break;

                        case 11:
                            if (day_1 > 30)
                            {
                                day_1 -= 30;
                                month_1++;
                            }
                            break;

                        case 12:
                            if (day_1 > 31)
                            {
                                day_1 -= 31;
                                month_1 = 1;
                                year_1++;
                            }
                            break;
                    }
                }

                //common year == 0 leapyear == 1
                if (((year_1 % 100) != 0 && (year_1 % 100) % 4 == 0) || ((year_1 % 100) == 0 && (year_1 % 400 == 0) && (year_1 % 4000 != 0)))
                {
                    year_type = 1;
                }
                else if (((year_1 % 100) != 0 && (year_1 % 100) % 4 != 0) || ((year_1 % 100) == 0 && (year_1 % 400 != 0)) || (year_1 % 4000 == 0))
                {
                    year_type = 0;
                }

                if (year_type == 0)
                {
                    if (month_1 == 1)
                        k = 0;
                    else if (month_1 == 2)
                        k = 3;
                    else if (month_1 == 3)
                        k = 3;
                    else if (month_1 == 4)
                        k = 6;
                    else if (month_1 == 5)
                        k = 6;
                    else if (month_1 == 6)
                        k = 4;
                    else if (month_1 == 7)
                        k = 6;
                    else if (month_1 == 8)
                        k = 2;
                    else if (month_1 == 9)
                        k = 5;
                    else if (month_1 == 10)
                        k = 0;
                    else if (month_1 == 11)
                        k = 3;
                    else if (month_1 == 12)
                        k = 5;
                }
                else if (year_type == 1)
                {
                    if (month_1 == 1)
                        k = 0;
                    else if (month_1 == 2)
                        k = 3;
                    else if (month_1 == 3)
                        k = 4;
                    else if (month_1 == 4)
                        k = 0;
                    else if (month_1 == 5)
                        k = 0;
                    else if (month_1 == 6)
                        k = 5;
                    else if (month_1 == 7)
                        k = 0;
                    else if (month_1 == 8)
                        k = 3;
                    else if (month_1 == 9)
                        k = 6;
                    else if (month_1 == 10)
                        k = 1;
                    else if (month_1 == 11)
                        k = 4;
                    else if (month_1 == 12)
                        k = 6;
                }

                day_number = ((k + day_1 + 5 * ((year_1 - 1) % 4) + 4 * ((year_1 - 1) % 100) + 6 * ((year_1 - 1) % 400))) % 7;

                string day_name = "";
                if (day_number == 0)
                    day_name = "Sunday";
                else if (day_number == 1)
                    day_name = "Monday";
                else if (day_number == 2)
                    day_name = "Tueseday";
                else if (day_number == 3)
                    day_name = "Wednesday";
                else if (day_number == 4)
                    day_name = "Thursday";
                else if (day_number == 5)
                    day_name = "Friday";
                else if (day_number == 6)
                    day_name = "Saturday";

                month_name = convert_number_month(month_1);

                if ((month_1 == 3 || month_1 == 4 || month_1 == 5) && counterspring == 0)
                {
                    Console.WriteLine("\nSpring!");
                    counterspring = 1;
                    countersummer = 0;
                }
                else if ((month_1 == 6 || month_1 == 7 || month_1 == 8) && countersummer == 0)
                {
                    Console.WriteLine("\nSummer!");
                    countersummer = 1;
                    counterautumn = 0;
                }

                else if ((month_1 == 9 || month_1 == 10 || month_1 == 11) && counterautumn == 0)
                {
                    Console.WriteLine("\nAutumn!");
                    counterautumn = 1;
                    counterwinter = 0;
                }

                else if ((month_1 == 12 || month_1 == 1 || month_1 == 2) && counterwinter == 0)
                {
                    Console.WriteLine("\nWinter!");
                    counterwinter = 1;
                    counterspring = 0;
                }


                Console.WriteLine("{0} {1} {2} {3}", day_1, month_name, year_1, day_name);
                day_1 += n;
            }
        }
    }

}