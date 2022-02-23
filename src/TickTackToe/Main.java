package TickTackToe;

import java.util.*;

public class Main {

    static ArrayList<Integer> playerPositions = new ArrayList<Integer>();
    static ArrayList<Integer> cpuPositions = new ArrayList<Integer>();

    public static void main(String[] args) {
        char[][] GameBoard =   {{' ', ' ', ' '},
                                {' ', ' ', ' '},
                                {' ', ' ', ' '}};

        Scanner scanner = new Scanner(System.in);

        System.out.println("Добро пожаловать в крестики-нолики!");
        System.out.println("Для хода введите число от 1 до 9, соответствующее нужной клеточке");

        PrintGameBoard(GameBoard);

        boolean continueGame = true;                //переменная для остановки игры
        boolean isPlayerTurn = true;                //переменная для переключения ходов между игроками
        int pos = 10;                               //переменная, содержащая клетку, на которую игрок ставит метку

        while(continueGame) {
            System.out.print("Ход игрока ");        //Нет переноса строки, чтобы имя игрока ввелось на ней же
            if (isPlayerTurn) {                     //Если ход пользователя - то последовательность для пользователя
                System.out.println("Пользователь");
                while (true) {
                    try {
                        pos = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Вы ввели не число. Введите число от 1 до 9");
                        scanner.next();
                        continue;
                    }
                    if (pos <1 || pos > 9) {
                        System.out.println("Введено некорректное число. Требуется ввести число от 1 до 9.");
                    }
                    else {
                        if (checkForRepeat(pos)) {                          //проверяем, свободна ли клетка
                            placePiece(GameBoard, pos, "Player");      //если свободна - ставим метку
                            break;                                          //выходим из цикла
                        }
                        else System.out.println("Эта позиция уже занята. Повторите ввод.");
                    }
                }
            }
            else {                                  //иначе - последовательность для компьютера
                System.out.println("Компьютер");
                while (true) {
                    pos = (int) (Math.random() * 9) + 1;
                    if (checkForRepeat(pos)) {                      //проверяем, свободна ли клетка
                        placePiece(GameBoard, pos, "CPU");     //если свободна - ставим метку
                        break;                                      //выходим из цикла
                    }
                }
            }

            PrintGameBoard(GameBoard);                              //после совершения хода выводим обновленное поле
            continueGame = Boolean.parseBoolean(CheckWinner());          //проверяем, есть ли победитель
            /// функция CheckWinner() возвращает строку "true", если победителя нет
            /// только строка "true" и "TRUE" превращается в истину при использовании метода Boolean.valueOf(string)
            isPlayerTurn = !isPlayerTurn;                           //меняем игрока для следующего хода
        }

        switch (CheckWinner()) {
            case "player" -> System.out.println("Победа пользователя!");
            case "cpu" -> System.out.println("Победа компьютера!");
            case "even" -> System.out.println("Ничья!");
        }

        System.out.println("Конец игры");
    }


    public static void PrintGameBoard(char[][] GameBoard){
        char [][] GameBoardPrint = {{' ','|', ' ','|', ' '},
                                    {'-','+', '-','+', '-'},
                                    {' ','|', ' ','|', ' '},
                                    {'-','+', '-','+', '-'},
                                    {' ','|', ' ','|', ' '}};

        for (int i = 0; i <=2; i++) {
            for (int j = 0; j <= 2; j++) {
                GameBoardPrint[i * 2][j * 2] = GameBoard[i][j];     //заменяем пробелы на нужные символы
            }
        }

        for (char[] row : GameBoardPrint) {
            for (char c : row){
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static boolean checkForRepeat(int pos){
        if (cpuPositions.contains(pos) || playerPositions.contains(pos)) return false;
        else return true;
    }

    public static void placePiece(char[][] GameBoard, int pos, String User){
        char symbol = ' ';

        if (Objects.equals(User, "Player")) {
            symbol = 'x';
            playerPositions.add(pos);
        }
        else  if (Objects.equals(User, "CPU")){
            symbol = 'o';
            cpuPositions.add(pos);
        }

        switch (pos) {
            case 1 -> GameBoard[0][0] = symbol;
            case 2 -> GameBoard[0][1] = symbol;
            case 3 -> GameBoard[0][2] = symbol;
            case 4 -> GameBoard[1][0] = symbol;
            case 5 -> GameBoard[1][1] = symbol;
            case 6 -> GameBoard[1][2] = symbol;
            case 7 -> GameBoard[2][0] = symbol;
            case 8 -> GameBoard[2][1] = symbol;
            case 9 -> GameBoard[2][2] = symbol;
            default -> System.out.println("Введено некорректное число");
        }
    }

    public static String CheckWinner(){

        List<List> winningConditions = new ArrayList<List>();

        winningConditions.add(Arrays.asList(1,2,3));  //topRow
        winningConditions.add(Arrays.asList(4,5,6));  //midRow
        winningConditions.add(Arrays.asList(7,8,9));  //botRow

        winningConditions.add(Arrays.asList(1,4,7));  //leftCol
        winningConditions.add(Arrays.asList(2,5,8));  //midCol
        winningConditions.add(Arrays.asList(3,6,9));  //rightCol

        winningConditions.add(Arrays.asList(1,5,9));  //cross1
        winningConditions.add(Arrays.asList(3,5,7)); //cross2

        for (List l: winningConditions) {
            if (playerPositions.containsAll(l)) return "player";
            if (cpuPositions.containsAll(l)) return "cpu";
        }

        if (playerPositions.size() + cpuPositions.size() == 9) return "even";   //если поле заполнено - ничья

        return "true";                                                          //иначе игра продолжается
    }

}
