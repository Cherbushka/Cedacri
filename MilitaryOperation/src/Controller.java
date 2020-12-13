import java.io.BufferedReader;//подключаем все нужные библиотеки
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Controller extends Model{//контроллер наследует переменные из класса модели,где находятся все необходимые переменные для данного задания
	protected static Scanner in=new Scanner(System.in);
	static int menu;//создаём поля, необходимые в нескольких функциях,чтобы не передавать их ,как аргумент, через ссылки
	static int num_barracks=0;
	static int free_space=0;
	static int outside[][]=new int[50][50], soldier_space[][]=new int[49][49], barracks[][]=new int[49*49][3], virtual_barracks[][], virtualsquads[], max_square[];//матрицам сразу задаём необходимую размерность
	 public void battlefield_read() throws IOException{//стандартная функция чтения из файла с использованием класса buffered reader
		BufferedReader reader = new BufferedReader (new FileReader("Cazemate.in.txt"));
        String[] params = reader.readLine().split(" ");
        n=Integer.parseInt(params[0]);
        m=Integer.parseInt(params[1]);
        k=Integer.parseInt(params[2]);
        String[] field;
        for(int i=0;i<n;i++){
        	field = reader.readLine().split(" ");
            for(int j=0;j<m;j++){
            	battlefield[i][j]=Integer.parseInt(field[j]);
            }
        }
        params = reader.readLine().split(" ");
        squads=new int[k];
        for(int i=0;i<k;i++) {
        	squads[i]=Integer.parseInt(params[i]);
        }
		reader.close();
		System.out.println("Successfully read from the file.");
	}
	 void battlefield_write() throws IOException{//стандартная функция записи в файл с использованием класса buffered writer
		BufferedWriter writer=new BufferedWriter(new FileWriter("Cazemate.in.txt"));
		writer.write(n+" "+m+" "+k+"\n");
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {
				writer.write(battlefield[i][j]+" ");
			}
			writer.write("\n");
		}
		for(int i=0;i<k;i++) {
			writer.write(squads[i]+" ");
		}
		writer.close();
		System.out.println("Successfully written to the file.");
		System.out.println();
	}
	 void battlefield_cout(){//функция вывода матрицы с использованием условий для текстовой ASCII-графики в консоли
		System.out.print(" ");
		for(int j=0;j<m;j++) {
			System.out.print(" "+(j+1));
		}
		System.out.println();
		for(int i=0;i<n;i++) {
			System.out.print((i+1)+" ");
			for(int j=0;j<m;j++) {
				if(battlefield[i][j]==1) 
					System.out.print("█ ");
				else
					System.out.print("- ");
			}
			System.out.println();
		}
	}
	 void insert_row() {//функция вставки пограничного столбца/строки
		do {//стандартная менюшка в цикле для выбора нужной стороны для добавления
			System.out.println("Input insert menu number");
			if(m<50) System.out.println("1 - insert new east column");
			if(n<50) System.out.println("2 - insert new south line");
			if(m<50) System.out.println("3 - insert new west column");
			if(n<50) System.out.println("4 - insert new north line");
			System.out.println("0 - quit");
			String menu1;
			while(true) {
				System.out.print(" Menu-");
				menu1=in.nextLine();
				try {
					if(menu1.equals("0")) {
						return;
					}
					else if((n<50) && (Integer.parseInt(menu1)==4 || Integer.parseInt(menu1)==2)) {
						menu=Integer.parseInt(menu1);
						break;
					}
					else if((m<50) && (Integer.parseInt(menu1)==1 || Integer.parseInt(menu1)==3)) {
						menu=Integer.parseInt(menu1);
						break;
					}
					else {
						System.out.println("Invalid menu number");
					}
				}
				catch(Exception e) {
					System.out.println("Invalid menu number");
				}
			}
			switch(menu) {
				case 1://в зависимости от выбора пользователя можно вставлять восточный столбец,северную строку,западный столбец и южную строку, рассмотрим вствку восточного столбца,т.к. остальные типы вствки делаются аналогично
					m++;
					Arrays.copyOf(battlefield[0], m);//меняем размерность матрицы,прибавляя количество столбцов
					for(int i=0;i<n;i++) {//цикл,в котором мы проходим все строки в последнем добавленном столбце
						String field;
						while(true) {
							System.out.print("battlefield["+(i+1)+"]"+"["+m+"]=");
							field=in.nextLine();
							try {
								if(Integer.parseInt(field)>=0 && Integer.parseInt(field)<=1) {
									battlefield[i][m-1]=Integer.parseInt(field);
									break;
								}
								else {
									System.out.println("Invalid number");
								}
							}
							catch(Exception e) {
								System.out.println("Invalid number");
							}
						}
					}
					break;
				case 2:
					n++;
					Arrays.copyOf(battlefield, n);
					for(int i=0;i<m;i++) {
						String field;
						while(true) {
							System.out.print("battlefield["+n+"]"+"["+(i+1)+"]=");
							field=in.nextLine();
							try {
								if(Integer.parseInt(field)>=0 && Integer.parseInt(field)<=1) {
									battlefield[n-1][i]=Integer.parseInt(field);
									break;
								}
								else {
									System.out.println("Invalid number");
								}
							}
							catch(Exception e) {
								System.out.println("Invalid number");
							}
						}
					}
					break;
				case 3:
					m++;
					Arrays.copyOf(battlefield[0], m);
					for(int i=0;i<n;i++) {//тут мы просто переносим всю матрицу на 1 столбец вперёд,чтобы потом у нас получилось 2 одинаковых первых столбца,первому из которых мы ставим новые значения с клавиатуры
						for(int j=m-1;j>0;j--) {
							battlefield[i][j]=battlefield[i][j-1];
						}
					}
					for(int i=0;i<n;i++) {
						String field;
						while(true) {
							System.out.print("battlefield["+(i+1)+"]"+"["+1+"]=");
							field=in.nextLine();
							try {
								if(Integer.parseInt(field)>=0 && Integer.parseInt(field)<=1) {
									battlefield[i][0]=Integer.parseInt(field);
									break;
								}
								else {
									System.out.println("Invalid number");
								}
							}
							catch(Exception e) {
								System.out.println("Invalid number");
							}
							
						}
					}
					break;
				case 4:
					n++;
					Arrays.copyOf(battlefield, n);
					for(int j=0;j<m;j++) {
						for(int i=n-1;i>0;i--) {
							battlefield[i][j]=battlefield[i-1][j];
						}
					}
					for(int i=0;i<m;i++) {	
						String field;
						while(true) {
							System.out.print("battlefield["+1+"]"+"["+(i+1)+"]=");
							field=in.nextLine();
							try {
								if(Integer.parseInt(field)>=0 && Integer.parseInt(field)<=1) {
									battlefield[0][i]=Integer.parseInt(field);
									break;
								}
								else {
									System.out.println("Invalid number");
								}
							}
							catch(Exception e) {
								System.out.println("Invalid number");
							}
						}
					}
					break;
				default:
					break;
			}
		}while(menu!=0);
		menu=1;//
	}
	 void update_row(){//функция обновления столбцов/сток по выбору пользователя
		do {
			System.out.println("Input insert menu number");
			System.out.println("1 - update column");
			System.out.println("2 - update line");
			System.out.println("0 - quit");
			String menu1;
			while(true) {
				System.out.print(" Menu-");
				menu1=in.nextLine();
				try {
					if(Integer.parseInt(menu1)==0) {
						return;
					}
					else if(Integer.parseInt(menu1)==1 || Integer.parseInt(menu1)==2) {
						menu=Integer.parseInt(menu1);
						break;
					}
					else {
						System.out.println("Invalid menu number");
					}
				}
				catch(Exception e) {
					System.out.println("Invalid menu number");
				}
			}
			int row_number;
			while(true) {
				System.out.print("Input a row's number:");//пользователь выбирает вначале строку или столбец, а уже потом вводит номер строки/столбца
				menu1=in.nextLine();
				try {
					if(menu==1 && Integer.parseInt(menu1)>=1 && Integer.parseInt(menu1)<=m) {
						row_number=Integer.parseInt(menu1)-1;
						break;
					}
					else if(menu==2 && Integer.parseInt(menu1)>=1 && Integer.parseInt(menu1)<=n) {
						row_number=Integer.parseInt(menu1)-1;
						break;
					}
					else {
						System.out.println("Invalid number");
					}
				}
				catch(Exception e) {
					System.out.println("Invalid number");
				}
			}
			switch(menu) {
				case 1://меняем через цикл всю строку по номеру столбца
					for(int i=0;i<n;i++) {
						String field;
						while(true) {
							System.out.print("battlefield["+(i+1)+"]"+"["+(row_number+1)+"]=");
							field=in.nextLine();
							try {
								if(Integer.parseInt(field)>=0 && Integer.parseInt(field)<=1) {
									battlefield[i][row_number]=Integer.parseInt(field);
									break;
								}
								else {
									System.out.println("Invalid number");
								}
							}
							catch(Exception e) {
								System.out.println("Invalid number");
							}
						}
					}
					break;
				case 2:
					for(int i=0;i<m;i++) {
						String field;
						while(true) {
							System.out.print("battlefield["+(row_number+1)+"]"+"["+(i+1)+"]=");
							field=in.nextLine();
							try {
								if(Integer.parseInt(field)>=0 && Integer.parseInt(field)<=1) {
									battlefield[row_number][i]=Integer.parseInt(field);
									break;
								}
								else {
									System.out.println("Invalid number");
								}
							}
							catch(Exception e) {
								System.out.println("Invalid number");
							}
						}
					}
					break;
				default:
					break;
			}
		}while(menu!=0);
		menu=1;//
	}
	 @SuppressWarnings("static-access")
	 void top_squads() {//функция сортировки отрядов методом выбора и вывода количества солдат уже отсортированных отрядов 
		int [] virtualsquads=Arrays.copyOf(squads, squads.length);//создаём временный массив,так как порядковые номера отрядов нам пригодятся в другой функции
		for (int i=0;i<k-1;i++){
			int maxI=i;
			for(int j=i+1;j<k;j++){
				if(virtualsquads[j]>virtualsquads[maxI])
					maxI=j;
			}
			int t=virtualsquads[i]; 
			virtualsquads[i]=virtualsquads[maxI];
			virtualsquads[maxI]=t;
			this.virtualsquads=Arrays.copyOf(virtualsquads, virtualsquads.length);
			System.out.println((i+1)+". "+virtualsquads[i]);//сразу во время сортировки выводим все отряды, кроме последнего
		}
		System.out.println((k)+". "+virtualsquads[k-1]);//выводим последний отряд
	}
	 @SuppressWarnings("static-access")
	 void max_square() {//функция поиска квадрата максимальной площади, которая выводит данные об этом квадрате
		int square_area,square_x = 0,square_y=0;
		this.max_square=new int[3];
		if(m<n) //необходимо обозначить максимальную площадь квадрата, которая равна квадрату меньшей стороны матрицы
			square_area=m*m;
		else
			square_area=n*n;
		
		Boolean flag=false;//сразу обозначаем переменную типа Boolean, которая позволит выйти из всех вложенных циклов,когда квдрат будет уже найден
		do {//первый цикл нужен, чтобы менять площадь квадрата, именно тут переменная,отвечающая за перемещение квадрата по вертикали возвращается на начальную позицию
			square_y=0;
			do {//второй цикл нужен,чтобы менять положение квадрата по вертикали, тут переменная ,отвечающая за перемещение квадрата по горизонтали возвращается в начальное положение
				square_x=0;
				do {//третий цикл нужен, чтобы менять положение квадрата по горизонтали, тут мы проверяем квадрат на удовлетворение условиям через специальную функцию типа Bollean
					if(square_check((int) Math.sqrt(square_area), square_x, square_y)) {
						flag=true;
						break;
					}
					square_x++;//перемещение квадрата по горизонтали вперёд
				}while(square_x+Math.sqrt(square_area)<=m);//до тех пор, когда правая сторона кввадрата не достигнет правого края матрицы
				if(flag) break;
				square_y++;//перемещение квадрата по вертикали вниз
			}while(square_y+Math.sqrt(square_area)<=n);//до тех пор, когда нижняя сторона кввадрата не достигнет нижнего края матрицы
			if(flag) break;
			square_area=(int) ((Math.sqrt(square_area)-1)*(Math.sqrt(square_area)-1));//(сторона квадрата-1)^2, мы находим меньший по площади квадрат
		}while(square_area>=4);//если квадрат будет по площади в 1 ячейку, то у него не будет существовать ячеек под главной диагональю, поэтому и проверять квадрат с такой площадью не будет смысла
		if(square_area>=4) {//возвращаем массив, только если квадрат будет как минимум 4 ячейки по площади
			int [] square= {square_area, square_x+1, (int) (square_y+Math.sqrt(square_area))};//массив будет содержать следующие значения (площадь квадрата,x-кордината левого угла,y-кордината нижнего угла, если брать за точку отсчёта координат левый нижний угол матрицы)
			this.max_square=Arrays.copyOf(square, square.length);
			System.out.println("Area of square with only 0 under gen diagonal="+square[0]);
			System.out.println("Length of square with only 0 under gen diagonal="+(int)Math.sqrt(square[0]));
			System.out.println("X-coordinate of square with only 0 under gen diagonal="+square[1]);
			System.out.println("Y-coordinate of square with only 0 under gen diagonal="+square[2]);
		}
		else {//если квадрат не нашёлся, то выводим сообщение об ошибке
			this.max_square=new int[0];
			System.out.println("There is no such square");
		}
	}
	 Boolean square_check(int length,int square_x,int square_y) {//функция проверки найденного квадрата на условия задания
		for(int i=square_y+1;i<length+square_y;i++) {//проверяем все строки внутри квдрата начиная со 2ой,т.к на первой строке находится главная диагональ
			for(int j=square_x;j<i-square_y+square_x;j++) {//проверяем все столбцы в кадрате до главной диагонали,поэтому количество проверок в строке увеличивается с номером строки 
				if(battlefield[i][j]!=0) {
					return false;
				}
			}
		}
		return true;
	}
	 void count_barracks() {//функция подсчёта казарм,нужна также для следующих функций
		num_barracks=0;
		barracks=new int[49*49][3];
		outside=new int[50][50];
		soldier_space=new int[49][49]; 
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {
				if(battlefield[i][j]==0 && outside[i][j]!=1) {//в двойном цикле проверяем все нулевые элементы матрицы, которые мы ещё не проверяли,записывая уже проверенные в матрице outside
					if(check_barrack(i,j)) {//если функция находит казарму,то присваивает значения координат одной из ячеек казармы в соответствующий столбец матрицы, таким образом матрица barracks будет содержать такие столбцы(количество свободных мест в казарме,y-координата одной из ячеек,x-координата этой ячейки)
						barracks[num_barracks][1]=i;
						barracks[num_barracks][2]=j;
						num_barracks++;//увеличение числа казарм
					}
				}
			}
		}
		System.out.println("Number of barracks="+num_barracks);
	}
	 Boolean check_barrack(int y,int x){//функция проверки принадлежности ячейки к казарме
		if(x==0 || x==m-1 || y==0 || y==n-1) return false;//если ячейка находится на границе матрицы, то она не принадлежит казарме
		if(battlefield[y][x+1]==0 && outside[y][x+1]!=1) {//4 if нужны, чтобы проверить каждую соседнюю ячейку в этой же функции, таким образом функция проверяет все соседние нулевые ячейки 
			outside[y][x+1]=1;//записываем в другую матрицу, что мы уже проверили эту ячейку 
			if(!check_barrack(y,x+1)) return false;//рекурсивно проверяем уже соседнюю ячейку и её соседние ячейки, если функция вызвана рекурсивно и вернула false, то функция, вызавшая её тоже вернёт false, потому что все ячейки внутри казармы должны соответствовать условиям
		}
		if(battlefield[y][x-1]==0 && outside[y][x-1]!=1) {//аналогично проверяем и для остальных соседних ячеек
			outside[y][x-1]=1;
			if(!check_barrack(y,x-1)) return false;
		}
		if(battlefield[y+1][x]==0 && outside[y+1][x]!=1) {
			outside[y+1][x]=1;
			if(!check_barrack(y+1,x)) return false;
		}
		if(battlefield[y-1][x]==0 && outside[y-1][x]!=1) {
			outside[y-1][x]=1;
			if(!check_barrack(y-1,x)) return false;
		}
		if(soldier_space[y][x]!=1) {//также уже в другой матрице обозначаем ячейку, которую мы уже использовали для увеличения свободного места в казарме
			soldier_space[y][x]=1;
			barracks[num_barracks][0]++;
		}
		return true;
	}
	 void find_free_space() {//функция подсчёта свободного места во всех казармах
		count_barracks();
		free_space=0;
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {
				if(soldier_space[i][j]==1) {//для этого и нужно было в предыдущей функции создавать новую матрицу для свободного места в казармах
					free_space++;
				}
			}
		}
		System.out.println("Free space="+free_space);
	}
	 Boolean soldier_distribution() throws IOException {//функция для проверки возможности распределения солдат в казармах
		count_barracks();
		if(squads.length>num_barracks) {//если количество отрядов больше количества казарм, то функция сразу вернёт false
			System.out.println("You can't distribure soldiers");
			return false;
		}
	    for (int i=0;i<num_barracks-1;i++)  //сортируем казармы по количеству свободного места для солдат     
		    for(int j=0; j<num_barracks-i-1;j++)  
		        if(barracks[j][0] < barracks[j+1][0]) {
		        	int []temp = barracks[j];  
		        	barracks[j] = barracks[j+1];  
		        	barracks[j+1] = temp;   
		        }
	    int [] virtualsquads=Arrays.copyOf(squads,squads.length);//создём временный массив для отрядов, так как изначальный порядок солдат в отряде нужен будет для другой функции
	    for (int i=0;i<k-1;i++)//временный массив сортируем по количеству солдат в отряде       
		    for(int j=0; j<k-i-1;j++)  
		        if(virtualsquads[j] < virtualsquads[j+1]) {
		        	int temp = virtualsquads[j];  
		        	virtualsquads[j] = virtualsquads[j+1];  
		        	virtualsquads[j+1] = temp;   
		        }
		for(int i=0;i<k;i++) {//если в максимально большой незанятой казарме на данный момент не хватит места для самого большого на данный момент отряда не будет хватать мест, то функция вернёт false
			if(virtualsquads[i]>barracks[i][0]) {
				System.out.println("You can't distribure soldiers");
				return false;
			}
		}
		System.out.println("You can distribure soldiers in that way:");
		write_soldiers_in_file();
		return true;
	}
	 @SuppressWarnings("static-access")
	 void write_soldiers_in_file() throws IOException {//функция для записи распределения солдат в файл Soldat.out.txt
		int [][] virtualsquads=new int[k][2], virtualbarracks=new int[num_barracks][4];//создаём временные массивы для отрядов(столбцы:количество солдат,порядковый номер,данный в исходном массиве) и для казарм(столбцы:количество свободных мест, y-кордината одной из ячеек,x-координата этой ячейки, порядковый номер отряда,который будет располагаться в этой казарме)
		for(int i=0;i<k;i++) {//задаём значения для временного массива отрядов
			virtualsquads[i][0]=squads[i];
			virtualsquads[i][1]=i;
		}
		
		for (int i=0;i<k-1;i++) //сортируем массив по количеству солдат в отряде    
		    for(int j=0; j<k-i-1;j++)  
		        if(virtualsquads[j][0] < virtualsquads[j+1][0]) {
		        	int[] temp = virtualsquads[j];  
		        	virtualsquads[j] = virtualsquads[j+1];  
		        	virtualsquads[j+1] = temp;   
		        }
		for(int i=0;i<k;i++) {//задаём значения для временного массива казарм
			virtualbarracks[i][0]=barracks[i][0];
			virtualbarracks[i][1]=barracks[i][1];
			virtualbarracks[i][2]=barracks[i][2];
			virtualbarracks[i][3]=virtualsquads[i][1];
		}
		for (int i=0;i<k-1;i++)//сортируем казармы по порядковому номеру отряда, проживающего в нём       
		    for(int j=0; j<k-i-1;j++)  
		        if(virtualbarracks[j][3] > virtualbarracks[j+1][3]) {
		        	int[] temp = virtualbarracks[j];  
		        	virtualbarracks[j] = virtualbarracks[j+1];  
		        	virtualbarracks[j+1] = temp;   
		        }
		this.virtual_barracks=Arrays.copyOf(virtualbarracks,virtualbarracks.length);
		BufferedWriter writer=new BufferedWriter(new FileWriter("Soldat.out.txt"));
		for(int i=0;i<k;i++) {//запись в файл и вывод на экран матрицы(столбцы: порядковый номер отряда, y-кордината ячейки матрицы,x-кордината этой ячейки)
			writer.write((i+1)+" "+(virtualbarracks[i][1]+1)+" "+(virtualbarracks[i][2]+1)+"\n");
			System.out.println("Squad №"+(i+1)+" are going to barrack at these coords:"+"( y - "+(virtualbarracks[i][1]+1)+",x - "+(virtualbarracks[i][2]+1)+")");
		}
		writer.close();
		System.out.println("Successfully wrote to the file.");
		System.out.println();
	}
	 public Controller() throws IOException {//функция Main
		do {//стандартное меню в цикле
			System.out.println("_________________________________________________");
			System.out.println("| Input menu number                             |");
			System.out.println("| 1 - read battlefield from file                |");
			System.out.println("| 2 - write battlefield in file                 |");
			System.out.println("| 3 - output battlefield                        |");
			System.out.println("| 4 - insert new row                            |");
			System.out.println("| 5 - update row                                |");
			System.out.println("| 6 - top squads ordered by number of soldiers  |");
			System.out.println("| 7 - find square which has 0 under gen.dia     |");
			System.out.println("| 8 - find barracks number                      |");
			System.out.println("| 9 - find the free space in barracks           |");
			System.out.println("| 10 - find if you can distribute soldiers      |");
			System.out.println("| 0 - quit                                      |");
			System.out.println("|                                               |");
			String menu1;
			while(true) {
				System.out.print(" Menu-");
				menu1=in.nextLine();
				try {
					if(Integer.parseInt(menu1)>=0 && Integer.parseInt(menu1)<=10) {
						menu=Integer.parseInt(menu1);
						break;
					}
					else {
						System.out.println("Invalid menu number");
					}
				}
				catch(Exception e) {
					System.out.println("Invalid menu number");
				}
			}
			System.out.println("|_______________________________________________|");
			if(n==0 || m==0) battlefield_read();
			switch(menu) {
				case 1:
					battlefield_read();
					break;
				case 2:
					battlefield_write();
					break;
				case 3:
					battlefield_cout();
					break;
				case 4:
					insert_row();
					break;
				case 5:
					update_row();
					break;
				case 6:
					top_squads();
					break;
				case 7:
					max_square();
					break;
				case 8:
					count_barracks();
					break;
				case 9:
					find_free_space();
					break;
				case 10:
					soldier_distribution();
					break;
			}
			
		}while(menu!=0);
	}
	 public Controller(int i) {}
	 
}

