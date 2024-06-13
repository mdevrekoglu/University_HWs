using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Threading;
using System.Collections;

namespace Cengers
{
    class Program
    {
        static void Main(string[] args)
        {
            //Game matrice
            char[,] board = new char[,]
            {
                {'O', 'O', 'O', '·', '·', '·', '·', '·'},
                {'O', 'O', 'O', '·', '·', '·', '·', '·'},
                {'O', 'O', 'O', '·', '·', '·', '·', '·'},
                {'·', '·', '·', '·', '·', '·', '·', '·'},
                {'·', '·', '·', '·', '·', '·', '·', '·'},
                {'·', '·', '·', '·', '·', 'X', 'X', 'X'},
                {'·', '·', '·', '·', '·', 'X', 'X', 'X'},
                {'·', '·', '·', '·', '·', 'X', 'X', 'X'},
            };
            int[,] flag = new int[8, 8];

            //Variables that we use for creating game
            ConsoleKeyInfo keyInfo;
            int cx = 6, cy = 3, temp_cx, temp_cy;
            int round = 1;
            int type_X, type_O, max, max_x, max_y, max_type;
            string result, max_way;

            //It prints the game area
            Console.ForegroundColor = ConsoleColor.Cyan;
            Console.WriteLine();
            Console.WriteLine("      1 2 3 4 5 6 7 8");
            Console.WriteLine("     +---------------+");
            Console.WriteLine("    1|               |");
            Console.WriteLine("    2|               |");
            Console.WriteLine("    3|               |");
            Console.WriteLine("    4|               |");
            Console.WriteLine("    5|               |");
            Console.WriteLine("    6|               |");
            Console.WriteLine("    7|               |");
            Console.WriteLine("    8|               |");
            Console.WriteLine("     +---------------+");
            Console.ForegroundColor = ConsoleColor.Gray;
            board_printer(board, cx, cy);//It fills the game area

            //These are the codes that we actually use to play game
            while (true)
            {
                result = max_way = "";//It resets the best way for computer
                type_X = type_O = temp_cx = temp_cy = 0;//It resets the type of move for player
                max_x = max_y = max_type = max = 0;//It resets the type of move for computer

                //Prints the round and the turn
                Console.SetCursorPosition(25, 2);
                Console.Write("Round: " + round);
                Console.SetCursorPosition(25, 4);
                Console.Write("Turn: X");
                Console.SetCursorPosition(cx, cy);
                Console.CursorVisible = true;//It makes cursor visible

                //The part of human
                do
                {
                    //This one takes input from user.                   
                    keyInfo = Console.ReadKey(true);

                    switch (keyInfo.Key)//This one provides the cursor movements
                    {
                        case ConsoleKey.UpArrow:
                            if (cy > 3)
                            {
                                cy -= 1;
                                Console.SetCursorPosition(cx, cy);
                            }
                            break;

                        case ConsoleKey.DownArrow:
                            if (cy < 10)
                            {
                                cy += 1;
                                Console.SetCursorPosition(cx, cy);
                            }
                            break;

                        case ConsoleKey.RightArrow:
                            if (cx < 20)
                            {
                                cx += 2;
                                Console.SetCursorPosition(cx, cy);
                            }
                            break;

                        case ConsoleKey.LeftArrow:
                            if (cx > 6)
                            {
                                cx -= 2;
                                Console.SetCursorPosition(cx, cy);
                            }
                            break;

                        case ConsoleKey.Z:
                            if (board[cy - 3, cx / 2 - 3] != 'X' || can_move(board, cx, cy, type_X, keyInfo) != true)
                                break;//If the choosen 'X' is not able to move it works and stops the remaining code
                            Console.CursorVisible = false;//It hides the cursor
                            do
                            {
                                do
                                {
                                    keyInfo = Console.ReadKey(true);//This one tkes input from user
                                    Console.ForegroundColor = ConsoleColor.Gray;
                                    board_printer(board, cx, cy);
                                    Console.ForegroundColor = ConsoleColor.Red;

                                    if (keyInfo.Key == ConsoleKey.UpArrow && can_move(board, cx, cy, type_X, keyInfo)
                                        && board[cy - 4, cx / 2 - 3] == '·')//Step up
                                    {
                                        temp_cx = cx;
                                        temp_cy = cy - 1;
                                    }
                                    else if (keyInfo.Key == ConsoleKey.UpArrow && can_move(board, cx, cy, type_X, keyInfo)
                                        && board[cy - 4, cx / 2 - 3] != '·' && board[cy - 5, cx / 2 - 3] == '·')//Jump up
                                    {
                                        temp_cx = cx;
                                        temp_cy = cy - 2;
                                    }
                                    else if (keyInfo.Key == ConsoleKey.DownArrow && can_move(board, cx, cy, type_X, keyInfo)
                                        && board[cy - 2, cx / 2 - 3] == '·')//Step down
                                    {
                                        temp_cx = cx;
                                        temp_cy = cy + 1;
                                    }
                                    else if (keyInfo.Key == ConsoleKey.DownArrow && can_move(board, cx, cy, type_X, keyInfo)
                                        && board[cy - 2, cx / 2 - 3] != '·' && board[cy - 1, cx / 2 - 3] == '·')//Jump down
                                    {
                                        temp_cx = cx;
                                        temp_cy = cy + 2;
                                    }
                                    else if (keyInfo.Key == ConsoleKey.RightArrow && can_move(board, cx, cy, type_X, keyInfo)
                                        && board[cy - 3, cx / 2 - 2] == '·')//Step right
                                    {
                                        temp_cx = cx + 2;
                                        temp_cy = cy;
                                    }
                                    else if (keyInfo.Key == ConsoleKey.RightArrow && can_move(board, cx, cy, type_X, keyInfo)
                                        && board[cy - 3, cx / 2 - 2] != '·' && board[cy - 3, cx / 2 - 1] == '·')//Jump right
                                    {
                                        temp_cx = cx + 4;
                                        temp_cy = cy;
                                    }
                                    else if (keyInfo.Key == ConsoleKey.LeftArrow && can_move(board, cx, cy, type_X, keyInfo)
                                        && board[cy - 3, cx / 2 - 4] == '·')//Step left
                                    {
                                        temp_cx = cx - 2;
                                        temp_cy = cy;
                                    }
                                    else if (keyInfo.Key == ConsoleKey.LeftArrow && can_move(board, cx, cy, type_X, keyInfo)
                                        && board[cy - 3, cx / 2 - 4] != '·' && board[cy - 3, cx / 2 - 5] == '·')//Jump left
                                    {
                                        temp_cx = cx - 4;
                                        temp_cy = cy;
                                    }
                                    else if (keyInfo.Key != ConsoleKey.X && !can_move(board, cx, cy, type_X, keyInfo))
                                    {
                                        temp_cx = cx;
                                        temp_cy = cy;
                                    }

                                    //If the unvisible cursor is not on 'X' it prints a red dot.
                                    if (can_move(board, cx, cy, type_X, keyInfo))
                                    {
                                        Console.SetCursorPosition(temp_cx, temp_cy);
                                        Console.Write('·');
                                    }
                                } while (
                                        //Checks if any jump happened
                                        !((keyInfo.Key == ConsoleKey.C && type_X == 2)
                                        //Checks if cursor on the right place
                                        || (keyInfo.Key == ConsoleKey.X && (temp_cx == cx ^ temp_cy == cy)))
                                        );
                                
                                Console.ForegroundColor = ConsoleColor.Gray;
                                if (keyInfo.Key == ConsoleKey.X)
                                {
                                    //Selects the type of move
                                    if (temp_cx == cx + 4 || temp_cx == cx - 4 || temp_cy == cy + 2 || temp_cy == cy - 2)
                                        type_X = 2;
                                    else
                                        type_X = 1;
                                    board[cy - 3, cx / 2 - 3] = '·';
                                    cx = temp_cx;
                                    cy = temp_cy;
                                    board[cy - 3, cx / 2 - 3] = 'X';
                                    board_printer(board, cx, cy);
                                }
                            } while (!((keyInfo.Key == ConsoleKey.C && type_X == 2) || type_X == 1));
                            //Checks if player wants to leave
                            break;
                    }
                } while (type_X == 0);//It works the user area until there is a move

                //Checks if the game has been finished
                if (game_checker(board) != ' ')
                    break;

                //Prints the turn
                Console.SetCursorPosition(25, 4);
                Console.Write("Turn: O");
                Console.ForegroundColor = ConsoleColor.Red;
                board_printer(board, cx, cy);
                Thread.Sleep(1000);

                //This is the code that finds 'O'
                for (int m = 0; m < 8; m++)
                    for (int d = 0; d < 8; d++)
                        if (board[m, d] == 'O')//If there is a 'O' we should check it if it can move
                        {
                            //This is the computer part this function returns the best path the 'O' should go
                            result = stock_sharp(board, ref flag, m, d, ref type_O);

                            //It calculates the profit of the way and compares it with old max value
                            if (score_calculator(board, type_O, result, m, d) + 15 - m * 3 / 2 - d * 3 / 2 > max)
                            {
                                max_x = m;//It stores the x value of the tile
                                max_y = d;//It stores the y value of the tile
                                max_type = type_O;//It stores the move type of the tile
                                //It stores the profit of the move
                                max = score_calculator(board, type_O, result, m, d) + 15 - m * 3 / 2 - d * 3 / 2;
                                max_way = result;//It stores the way tile should go
                            }

                            //Resets the move type of the 'O'
                            type_O = 0;

                            //We are using the flag to not visit the places that we have already visit
                            //Resets the flag
                            for (int k = 0; k < 8; k++)
                                for (int l = 0; l < 8; l++)
                                    flag[k, l] = 0;
                        }
                //This is the place that we move the best tile for 'O'
                for (int i = 0; i < max_way.Length; i++)//This loop provides us to use chars of tile
                {
                    if (max_type == 2)//If it is jump tile moves 2 step
                    {
                        board[max_x, max_y] = '·';
                        if (max_way[i] == 'r')
                            max_y += 2;
                        else if (max_way[i] == 'd')
                            max_x += 2;
                        else if (max_way[i] == 'l')
                            max_y -= 2;
                        else if (max_way[i] == 'u')
                            max_x -= 2;
                        board[max_x, max_y] = 'O';
                        Console.Beep(350, 300);
                        board_printer(board, cx, cy);//We are changing the board and printing the new board to see the movement
                        Thread.Sleep(700);
                    }
                    else if (max_type == 1)//If it is jump tile moves 1 step
                    {
                        board[max_x, max_y] = '·';
                        if (max_way[i] == 'r')
                            max_y++;
                        else if (max_way[i] == 'd')
                            max_x++;
                        else if (max_way[i] == 'l')
                            max_y--;
                        else if (max_way[i] == 'u')
                            max_x--;
                        board[max_x, max_y] = 'O';
                        Console.Beep(350, 300);
                        board_printer(board, cx, cy);//We are changing the board and printing the new board to see the movement
                        Thread.Sleep(700);
                    }
                }
                Console.ForegroundColor = ConsoleColor.Gray;
                board_printer(board, cx, cy);

                //Checks if the game has been finished
                if (game_checker(board) != ' ')
                    break;
                round++;
            }

            if (game_checker(board) == 'X')
            {
                Console.SetCursorPosition(0, 15);
                Console.WriteLine(@"
                          \                 \
               \         ..      \
                \       /  `-.--.___ __.-.___
        `-.      \     /  #   `-._.-'    \   `--.__
           `-.        /  ####    /   ###  \        `.
        ________     /  #### ############  |       _|           .'
                    |\ #### ##############  \__.--' |    /    .'
                    | ####################  |       |   /   .'
                    | #### ###############  |       |  /
                    | #### ###############  |      /|      ----
                  . | #### ###############  |    .'<    ____
                .'  | ####################  | _.'-'\|
              .'    |   ##################  |       |
                     `.   ################  |       |
                       `.    ############   |       | ----
                      ___`.     #####     _..____.-'     .
                     |`-._ `-._       _.-'    \\\         `.
                  .'`-._  `-._ `-._.-'`--.___.-' \          `.
                .' .. . `-._  `-._        ___.---'|   \   \
              .' .. . .. .  `-._  `-.__.-'        |    \   \
             |`-. . ..  . .. .  `-._|             |     \   \
             |   `-._ . ..  . ..   .'            _|
              `-._   `-._ . ..   .' |      __.--'
                  `-._   `-._  .' .'|__.--'
                      `-._   `' .'
                          `-._.'
                ");
                Console.SetCursorPosition(70, 30);
                Console.Write("You broke the computer");
            }
            else if (game_checker(board) == 'O')
            {
                Console.SetCursorPosition(0, 15);
                Console.WriteLine(@"
                .---
               / # o
               \,__>
             .o-'-'--._
           / |\_      '.
          |  |  \   -,  \
          \  /   \__| ) |
          '|_____[)) |,/
             |===H=|\ >>
             \  __,| \_\
              \/   \  \_\
              |\    |  \/
              | \   \   \\
              |  \   |   \\
              |__|\ ,-ooD \\
              |--\_(\.-'   \o
              '-.__)
                ");

                Console.SetCursorPosition(30, 25);
                Console.Write("Computer broke your arm and leg");
            }
            Console.ReadLine();
        }

        //This one prints the whole board and sets the cursor the old place
        static public void board_printer(char[,] board, int sx, int sy)
        {
            for (int m = 0; m < 8; m++)
            {
                Console.SetCursorPosition(6, 3 + m);
                for (int d = 0; d < 8; d++)
                {
                    if (d != 7)
                        Console.Write(board[m, d] + " ");
                    else
                        Console.Write(board[m, d]);
                }
            }
            Console.SetCursorPosition(sx, sy);
        }

        //It checks if the choosen tile can move or can it move towards one specific direction
        static public bool can_move(char[,] board, int cx, int cy, int type_X, ConsoleKeyInfo keyInfo)
        {
            if (type_X == 0 && (keyInfo.Key == ConsoleKey.Z || keyInfo.Key == ConsoleKey.RightArrow)
                && cx / 2 - 2 <= 7 && board[cy - 3, cx / 2 - 2] == '·')
                return true;//If the tile can step towards right it returns true
            else if (type_X == 0 && (keyInfo.Key == ConsoleKey.Z || keyInfo.Key == ConsoleKey.LeftArrow)
                && cx / 2 - 4 >= 0 && board[cy - 3, cx / 2 - 4] == '·')
                return true;//If the tile can step towards left it returns true
            else if (type_X == 0 && (keyInfo.Key == ConsoleKey.Z || keyInfo.Key == ConsoleKey.UpArrow)
                && cy - 4 >= 0 && board[cy - 4, cx / 2 - 3] == '·')
                return true;//If the tile can step towards up it returns true
            else if (type_X == 0 && (keyInfo.Key == ConsoleKey.Z || keyInfo.Key == ConsoleKey.DownArrow)
                && cy - 2 <= 7 && board[cy - 2, cx / 2 - 3] == '·')
                return true;//If the tile can step towards down it returns true
            else if (type_X != 1 && (keyInfo.Key == ConsoleKey.Z || keyInfo.Key == ConsoleKey.RightArrow)
                && cx / 2 - 1 <= 7 && board[cy - 3, cx / 2 - 2] != '·' && board[cy - 3, cx / 2 - 1] == '·')
                return true;//If the tile can jump towards right it returns true
            else if (type_X != 1 && (keyInfo.Key == ConsoleKey.Z || keyInfo.Key == ConsoleKey.LeftArrow)
                && cx / 2 - 5 >= 0 && board[cy - 3, cx / 2 - 4] != '·' && board[cy - 3, cx / 2 - 5] == '·')
                return true;//If the tile can jump towards left it returns true
            else if (type_X != 1 && (keyInfo.Key == ConsoleKey.Z || keyInfo.Key == ConsoleKey.UpArrow)
                && cy - 5 >= 0 && board[cy - 4, cx / 2 - 3] != '·' && board[cy - 5, cx / 2 - 3] == '·')
                return true;//If the tile can jump towards up it returns true
            else if (type_X != 1 && (keyInfo.Key == ConsoleKey.Z || keyInfo.Key == ConsoleKey.DownArrow)
                && cy - 1 <= 7 && board[cy - 2, cx / 2 - 3] != '·' && board[cy - 1, cx / 2 - 3] == '·')
                return true;//If the tile can jump towards down it returns true
            else
                return false;//If the tile can't jump or step towards anywhere it returns false
        }

        //Checks if the game has been finished
        static public char game_checker(char[,] board)
        {
            int counter_x = 0, counter_o = 0;//Variables that we use to save the number of 'X' or 'O' on the specific places.

            //Checks the specific places on the board
            for (int m = 0; m < 3; m++)
            {
                for (int d = 0; d < 3; d++)
                {
                    if (board[m, d] == 'X')
                        counter_x++;
                    if (board[7 - m, 7 - d] == 'O')
                        counter_o++;
                }
            }

            if (counter_x == 9)
                return 'X';//If the player wins it returns 'X'
            else if (counter_o == 9)
                return 'O';//If the computer wins it returns 'O'
            else
                return ' ';//If the game haven't finished this function returns space
        }

        //This is the function that calculates the profit of movement
        //Also it provides us to finish game
        static public int score_calculator(char[,] board, int type_O, string result, int tempx, int tempy)
        {
            int score = 0, target_x = -1, target_y = -1;//Variables that we need
            bool stop = false;//To stop loops

            if (
                (tempx == 5 || tempx == 6 || tempx == 7) && (tempy == 5 || tempy == 6 || tempy == 7)
                && !((type_O == 0 && tempy + 1 <= 7 && board[tempx, tempy + 1] == '·')//right step
                || (type_O != 1 && tempy + 2 <= 7 && board[tempx, tempy + 1] != '·' && board[tempx, tempy + 2] == '·')//right jump
                || (type_O == 0 && tempx + 1 <= 7 && board[tempx + 1, tempy] == '·')//down step
                || (type_O != 1 && tempx + 2 <= 7 && board[tempx + 1, tempy] != '·' && board[tempx + 2, tempy] == '·'))//down jump
               )//This condition checks if the tile is on the right position and if it is on the right position it can't move
                return -999;

            //If the tile is able to move we have to choose a target to place the tile therefore we are using nested loop
            for (int m = 7; m >= 5; m--)
            {
                for (int d = 7; d >= 5; d--)
                {
                    if (board[m, d] != 'O')
                    {
                        target_x = m;
                        target_y = d;
                        stop = true;
                        break;
                    }
                }
                if (stop)//If we chose a tile it stops the first for loop
                    break;
            }


            if (type_O == 2)//Then if it is a jump this part works  
            {
                for (int i = 0; i < result.Length; i++)
                {
                    if (result[i] == 'r')//If it is right then we should compare it with the target to see if it is a gain
                    {
                        if (target_y >= tempy + 2)
                            score += 10;
                        else
                            score -= 10;
                        tempx += 0;
                        tempy += 2;
                    }
                    else if (result[i] == 'd')// If it is down then we should compare it with the target to see if it is a gain
                    {
                        if (target_x >= tempx + 2)
                            score += 10;
                        else
                            score -= 10;
                        tempx += 2;
                        tempy += 0;
                    }
                    else if (result[i] == 'l')// If it is left then we should compare it with the target to see if it is a gain
                    {
                        if (target_y <= tempy - 2)
                            score += 10;
                        else
                            score -= 10;
                        tempx += 0;
                        tempy -= 2;
                    }
                    else if (result[i] == 'u')// If it is up then we should compare it with the target to see if it is a gain
                    {
                        if (target_x <= tempx - 2)
                            score += 10;
                        else
                            score -= 10;
                        tempx -= 2;
                        tempy += 0;
                    }
                }
            }
            else//Then if it is a step this part works
            {
                if (result == "r")
                {
                    if (target_y >= tempy + 1)// If it is right then we should compare it with the target to see if it is a gain
                        score += 5;
                    else
                        score -= 5;
                    tempx += 0;
                    tempy += 1;
                }
                else if (result == "d")// If it is down then we should compare it with the target to see if it is a gain
                {
                    if (target_x >= tempx + 1)
                        score += 5;
                    else
                        score -= 5;
                    tempx += 1;
                    tempy += 0;
                }
                else if (result == "l")// If it is left then we should compare it with the target to see if it is a gain
                {
                    if (target_y <= tempy - 1)
                        score += 5;
                    else
                        score -= 5;
                    tempx += 0;
                    tempy -= 1;
                }
                else if (result == "u")// If it is down then we should compare it with the target to see if it is a gain
                {
                    if (target_x <= tempx - 1)
                        score += 5;
                    else
                        score -= 5;
                    tempx -= 1;
                    tempy += 0;
                }
            }
            return score;//It returns the score
        }

        //This is the Computer (maybe a bit AI) part
        //We named it stock_sharp
        static public string stock_sharp(char[,] board, ref int[,] flag, int tempx, int tempy, ref int type_O)
        {
            string result = "";//This is the main result string
            flag[tempx, tempy] = 1;//We are marking this martrice to not visit the same position.
            int[] type_Os = new int[4];//There can be maximum 4 move right-left-up-down there for we are crating 4 type_O
            int[] scores = new int[4];//There can be maximum 4 move right-left-up-down there for we are crating 4 score
            string[] results = new string[4];//There can be maximum 4 move right-left-up-down there for we are crating 4 result
            int index = 0;//We are using a variable to store the index

            //We have to do that because there may not be 1 jump so every time if jump happens the move type must be 2
            for (int i = 0; i < 4; i++)
                type_Os[i] = type_O;

            //The main goal is finding the best way for tile
            //We can create a tree model to see all the ways and their profits
            //Then we can choose the best way

            //If the tile can jump towards the right this one works
            if (type_O != 1 && tempy + 2 <= 7 && flag[tempx, tempy + 2] == 0
                && board[tempx, tempy + 1] != '·' && board[tempx, tempy + 2] == '·')
            {
                type_Os[0] = 2;
                results[0] += 'r' + stock_sharp(board, ref flag, tempx + 0, tempy + 2, ref type_Os[0]);
                scores[0] = score_calculator(board, type_Os[0], results[0], tempx, tempy);
            }
            //If the tile can jump towards the down this one works
            if (type_O != 1 && tempx + 2 <= 7 && flag[tempx + 2, tempy] == 0
                && board[tempx + 1, tempy] != '·' && board[tempx + 2, tempy] == '·')
            {
                type_Os[1] = 2;
                results[1] = 'd' + stock_sharp(board, ref flag, tempx + 2, tempy + 0, ref type_Os[1]);
                scores[1] = score_calculator(board, type_Os[1], results[1], tempx, tempy);
            }
            //If the tile can jump towards the left this one works
            if (type_O != 1 && tempy - 2 >= 0 && flag[tempx, tempy - 2] == 0
                && board[tempx, tempy - 1] != '·' && board[tempx, tempy - 2] == '·')
            {
                type_Os[2] = 2;
                results[2] = 'l' + stock_sharp(board, ref flag, tempx + 0, tempy - 2, ref type_Os[2]);
                scores[2] = score_calculator(board, type_Os[2], results[2], tempx, tempy);
            }
            //If the tile can jump towards the up this one works
            if (type_O != 1 && tempx - 2 >= 0 && flag[tempx - 2, tempy] == 0
                && board[tempx - 1, tempy] != '·' && board[tempx - 2, tempy] == '·')//üst jump
            {
                type_Os[3] = 2;
                results[3] = 'u' + stock_sharp(board, ref flag, tempx - 2, tempy + 0, ref type_Os[3]);
                scores[3] = score_calculator(board, type_Os[3], results[3], tempx, tempy);
            }
            //If the tile can step towards the right this one works
            if (type_O == 0 && tempy + 1 <= 7 && flag[tempx, tempy + 1] == 0 && board[tempx, tempy + 1] == '·')
            {
                type_Os[0] = 1;
                results[0] += 'r';
                scores[0] = score_calculator(board, type_Os[0], results[0], tempx, tempy);
            }
            //If the tile can step towards the down this one works
            if (type_O == 0 && tempx + 1 <= 7 && flag[tempx + 1, tempy] == 0 && board[tempx + 1, tempy] == '·')
            {
                type_Os[1] = 1;
                results[1] += 'd';
                scores[1] = score_calculator(board, type_Os[1], results[1], tempx, tempy);
            }
            //If the tile can step towards the left this one works
            if (type_O == 0 && tempy - 1 >= 0 && flag[tempx, tempy - 1] == 0 && board[tempx, tempy - 1] == '·')
            {
                type_Os[2] = 1;
                results[2] += 'l';
                scores[2] = score_calculator(board, type_Os[2], results[2], tempx, tempy);
            }
            //If the tile can step towards the up this one works
            if (type_O == 0 && tempx - 1 >= 0 && flag[tempx - 1, tempy] == 0 && board[tempx - 1, tempy] == '·')//üst step
            {
                type_Os[3] = 1;
                results[3] += 'u';
                scores[3] = score_calculator(board, type_Os[3], results[3], tempx, tempy);
            }

            //If the tile can't move anymore this part works and compares the scores then finds the best way's index
            if (scores != null && scores.Length > 0)
                index = Array.IndexOf(scores, scores.Max());
            result += results[index];//After all it sum's it with the best way
            type_O = type_Os[index];//And changes the type of move
            return result;//Finally returns the result
        }
    }
}
