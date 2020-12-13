import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class View extends Controller{
	@SuppressWarnings("serial")
	private class Label extends JLabel{
		private Label() {
			this.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 40));
		}
		private Label(String text) {
			super(text);
			this.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 40));
		}
	}
	@SuppressWarnings("serial")
	private class Button extends JButton{
		private Button() {
			this.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 30));
		}
		private Button(String text) {
			super(text);
			this.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 30));
		}
	}
	static JFrame frame = new JFrame("Millitary operation");
	static Controller c=new Controller(1);
	static View v;
    public View() throws IOException {
    	super(1);
	}
    private static void create_MB() {
    	JMenuBar mb=new JMenuBar();
	        JMenu menu=new JMenu("File");
	        	JMenuItem mi=new JMenuItem("Open new txt-file");
	        	mi.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e){
						try {
							v.battlefield_read();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
	        	});
	        menu.add(mi);
	        	mi=new JMenuItem("Save");
	        	mi.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							c.battlefield_write();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
	        	});
	        menu.add(mi);
	        	mi=new JMenuItem("Save as...");
	        	mi.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							v.battlefield_write();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
	        	});
	        menu.add(mi);
	    mb.add(menu);
	        menu=new JMenu("Edit file");
	        	mi=new JMenuItem("Insert new border row");
	        	mi.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						v.insert_row();
					}
	        	});
	        menu.add(mi);
	    		mi=new JMenuItem("Update row");
	    		mi.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						v.update_row();
					}
	    		});
	        menu.add(mi);
	    mb.add(menu);
		    menu=new JMenu("Barracks");
		    	mi=new JMenuItem("Output the number of barracks");
		    	mi.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						v.count_barracks();
					}
		    	});
		    menu.add(mi);
				mi=new JMenuItem("Output space for soldiers in barracks");
				mi.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						v.find_free_space();
					}
				});
		    menu.add(mi);
				mi=new JMenuItem("Distribute soldiers in barracks");
				mi.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							soldiers_distribution();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
		    menu.add(mi);
		        mb.add(menu);
		        menu=new JMenu("Info");
		    	mi=new JMenuItem("Screenshot of task");
		    	mi.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							task();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
		    	});
		    menu.add(mi);
		mb.add(menu);	
        frame.setJMenuBar(mb);
    }
    private static void task() throws IOException  {
    	JFrame frame=new JFrame("Task");
    	frame.setIconImage(new ImageIcon("logo.png").getImage());
        
        File pathToFile = new File("task.png");
        BufferedImage image=ImageIO.read(pathToFile);
        @SuppressWarnings("serial")
		class MyPanel extends JPanel
        {
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
            }
            
        }
        MyPanel panel=new MyPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.add(panel);
        frame.pack();
    }
    private static void matrix_out() {
    	JPanel panel=new JPanel();
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    	JPanel panel1=new JPanel();
    	panel1.setBackground(Color.LIGHT_GRAY);
    	panel1.setMaximumSize(new Dimension(m*40+80, 42));
    	Label label = v.new Label("  ");
    	panel1.add(label);
    	for(int j=0;j<m;j++) {
			label=v.new Label(Integer.toString(j+1)+" ");
			panel1.add(label);
		}
		panel.add(panel1);
    	for(int i=0;i<n;i++) {
        	panel1=new JPanel();
        	panel1.setBackground(Color.LIGHT_GRAY);
            panel1.setMaximumSize(new Dimension(m*40+80,42));
            label = v.new Label(Integer.toString(i+1)+" ");
            panel1.add(label);
			for(int j=0;j<m;j++) {
				label=v.new Label("█");
				if(battlefield[i][j]==1)
					label.setForeground(Color.BLACK); 
				else
					label.setForeground(Color.WHITE); 
				panel1.add(label);
			}
			panel.add(panel1);
		}

		panel1=new JPanel();
    	panel1.setBackground(Color.LIGHT_GRAY);
        panel1.setMaximumSize(new Dimension(m*40+80,42));
        label = v.new Label("  ");
        panel1.add(label);
		for(int j=0;j<m;j++) {
			label=v.new Label("  ");
			panel1.add(label);
		}
		panel.add(panel1);
    	panel1=new JPanel();
    	panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
    	JButton button=v.new Button("Top squads");
    	button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				v.top_squads();
			}
    	});
    	panel1.add(button);
    	panel1.setMaximumSize(new Dimension(400,50));
    	panel.add(panel1);
    	panel1=new JPanel();
    	button=v.new Button("Find square with only 0 under gen. diagonal");
    	button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				v.max_square();
			}
    	});
    	panel1.add(button);
    	panel1.setMaximumSize(new Dimension(600,50));
    	panel.add(panel1);
    	frame.add(panel);
    }
    @SuppressWarnings("static-access")
	public void battlefield_read() throws IOException {
    	JFileChooser f = new JFileChooser(); 
    	f.setAcceptAllFileFilterUsed(false); 
        f.addChoosableFileFilter(new FileNameExtensionFilter(".txt", "txt")); 
        String path = null;
    	if (f.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
            path=f.getSelectedFile().getAbsolutePath();  
	    	BufferedReader reader = new BufferedReader (new FileReader(path));
	        String[] params = reader.readLine().split(" ");
	        n=Integer.parseInt(params[0]);
	        m=Integer.parseInt(params[1]);
	        k=Integer.parseInt(params[2]);
	        battlefield=new int[n][m];
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
			reset_frame();
    	}
	}
    public void battlefield_write() throws IOException{
    	JFileChooser f = new JFileChooser(); 
    	String path = null;
    	if (f.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) { 
            path=f.getSelectedFile().getAbsolutePath();  
	    	BufferedWriter writer=new BufferedWriter(new FileWriter(path));
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
    }
    public void insert_row() {
    	JFrame frame = new JFrame("Insert new row");
    	frame.setIconImage(new ImageIcon("logo.png").getImage());
    	JPanel panel = new JPanel();
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    	JPanel panel1=new JPanel();
    	panel1.add(v.new Label("Choose the side of new row:"));
    	panel1.setMaximumSize(new Dimension(460,50));
    	panel.add(panel1);
    	panel1=new JPanel();
    	JButton button=v.new Button("South");
    	button.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			menu=1;
    			frame.setVisible(false);
    			creating_row();
    		}
    	});
    	panel1.add(button);
    	panel1.setMaximumSize(new Dimension(320,60));
    	panel.add(panel1);
    	panel1=new JPanel();
    	button=v.new Button("East");
    	button.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			menu=2;
    			frame.setVisible(false);
    			creating_row();
    		}
    	});
    	panel1.add(button);
    	panel1.setMaximumSize(new Dimension(320,60));
    	panel.add(panel1);
    	panel1=new JPanel();
    	button=v.new Button("West");
    	button.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			menu=3;
    			frame.setVisible(false);
    			creating_row();
    		}
    	});
    	panel1.add(button);
    	panel1.setMaximumSize(new Dimension(320,60));
    	panel.add(panel1);
    	panel1=new JPanel();
    	button=v.new Button("North");
    	button.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			menu=4;
    			frame.setVisible(false);
    			creating_row();
    		}
    	});
    	panel1.add(button);
    	panel1.setMaximumSize(new Dimension(320,60));
    	panel.add(panel1);
    	frame.add(panel);
    	frame.setPreferredSize(new Dimension(550, 360));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private void creating_row() {
    	JFrame frame=new JFrame("Creating new row");
    	frame.setIconImage(new ImageIcon("logo.png").getImage());
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel panel1=new JPanel();
        panel1.add(v.new Label("Press button to invert your cell."));
        panel1.setMaximumSize(new Dimension(1100, 50));
        panel.add(panel1);
        panel1=new JPanel();
        panel1.add(v.new Label("Color represent cell value(Black:wall, white:empty space)"));
        panel1.setMaximumSize(new Dimension(1100, 50));
        panel.add(panel1);
        int [] row;
        frame.setVisible(true);
        frame.add(panel);
        @SuppressWarnings("serial") 
        class BattlefieldButton extends Button{
        	public int i;
        	public BattlefieldButton(int i) {
        		this.i=i;
        	}
        }
        BattlefieldButton []button;
        if(menu==1 || menu==4) {
        	button=new BattlefieldButton[m];	
        	row=new int[m];
        	panel1=new JPanel();
        	for(int i=0;i<m;i++) {
        		BattlefieldButton button1=new BattlefieldButton(i);
        		button1.setText(" ");
        		button1.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(button1.getBackground()==Color.BLACK) {
							button1.setBackground(Color.WHITE);
							row[button1.i]=0;
						}
						else {
							button1.setBackground(Color.BLACK);
							row[button1.i]=1;
						}
						button[button1.i]=button1;
					}
        		});
        		button[i]=button1;
        		panel1.add(button[i]);
        		panel1.setMaximumSize(new Dimension(m*50, 50));
        	}
        	panel.add(panel1);
        	
        }
        else {
        	button=new BattlefieldButton[n];
        	row=new int[n];
        	for(int i=0;i<n;i++) {
        		panel1=new JPanel();
        		BattlefieldButton button1=new BattlefieldButton(i);
        		button1.setText(" ");
        		button1.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(button1.getBackground()==Color.BLACK) {
							button1.setBackground(Color.WHITE);
							row[button1.i]=0;
						}
						else {
							button1.setBackground(Color.BLACK);
							row[button1.i]=1;
						}
						button[button1.i]=button1;
					}
        		});
        		button[i]=button1;
        		panel1.add(button[i]);
        		panel1.setMaximumSize(new Dimension(50,50*n));
        		panel.add(panel1);
        	}
        }
        	
        JButton button1=v.new Button("Save row");
        button1.addActionListener(new ActionListener() {
    		@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
    			frame.setVisible(false);
    			switch(menu) {
	            	case 1:
	            		n++;
	    				Arrays.copyOf(battlefield, n);
	    				for(int i=0;i<m;i++) {
	    					System.out.println("battlefield["+n+"]"+"["+(i+1)+"]="+row[i]);
	    					battlefield[n-1][i]=row[i];
	    				}
	            		break;
	            	case 2:
	            		m++;
	    				Arrays.copyOf(battlefield[0], m);//меняем размерность матрицы,прибавляя количество столбцов
	    				for(int i=0;i<n;i++) {//цикл,в котором мы проходим все строки в последнем добавленном столбце
	    					System.out.println("battlefield["+(i+1)+"]"+"["+m+"]="+row[i]);
	    					battlefield[i][m-1]=row[i];
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
	    					System.out.println("battlefield["+(i+1)+"]"+"["+1+"]="+row[i]);
	    					battlefield[i][0]=row[i];
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
	    					System.out.println("battlefield["+1+"]"+"["+(i+1)+"]="+row[i]);
	    					battlefield[0][i]=row[i];
	    				}
	    				break;
    			}
    			reset_frame();
    		}
    		
    	});
        button1.setMaximumSize(new Dimension(200,50));
        panel.add(button1);
        frame.pack();
    }
    private static void reset_frame() {
    	frame.setVisible(false);
    	frame=new JFrame("Millitry operation(created by Borodai Andrei)");
    	frame.setIconImage(new ImageIcon("logo.png").getImage());
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	create_MB();
    	matrix_out();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public void update_row() {
    	JFrame frame = new JFrame("Insert new row");
    	frame.setIconImage(new ImageIcon("logo.png").getImage());
    	JPanel panel = new JPanel();
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    	JPanel panel1=new JPanel();
    	panel1.add(v.new Label("Choose what kind of row you want to update :"));
    	panel1.setMaximumSize(new Dimension(750,60));
    	panel.add(panel1);
    	panel1=new JPanel();
    	JButton button=v.new Button("Column");
    	button.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			menu=1;
    			frame.setVisible(false);
    			choosing_row();
    		}
    	});
    	panel1.add(button);
    	panel1.setMaximumSize(new Dimension(320,60));
    	panel.add(panel1);
    	panel1=new JPanel();
    	button=v.new Button("Line");
    	button.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			menu=2;
    			frame.setVisible(false);
    			choosing_row();
    		}
    	});
    	panel1.add(button);
    	panel1.setMaximumSize(new Dimension(320,60));
    	panel.add(panel1);
    	frame.add(panel);
    	frame.setPreferredSize(new Dimension(760, 250));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private void choosing_row() {
    	JFrame frame=new JFrame("Choosing row");
    	frame.setIconImage(new ImageIcon("logo.png").getImage());
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel panel1=new JPanel();
        panel1.add(v.new Label("Press button to choose row's number"));
        panel1.setMaximumSize(new Dimension(650, 50));
        panel.add(panel1);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.add(panel);
        @SuppressWarnings("serial")
		class BattlefieldButton extends Button{
        	public int i;
        	public BattlefieldButton(int i, String label) {
        		super(label);
        		this.i=i;
        	}
        }
        if(menu==1) {
        	panel1=new JPanel();
        	for(int i=0;i<m;i++) {
        		BattlefieldButton button1=new BattlefieldButton(i,Integer.toString(i+1));
        		button1.setMaximumSize(new Dimension(100,100));
        		button1.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						frame.setVisible(false);
						updating_row(button1.i);
						
					}
        		});
        		panel1.add(button1);
        	}
        	panel.add(panel1);
        	
        }
        else {
        	for(int i=0;i<n;i++) {
        		panel1=new JPanel();
        		BattlefieldButton button1=new BattlefieldButton(i, Integer.toString(i+1));
        		button1.setMaximumSize(new Dimension(100,100));
        		button1.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						frame.setVisible(false);
						updating_row(button1.i);
					}
        		});
        		panel1.add(button1);
        		panel.add(panel1);
        	}
        }
        frame.pack();
	}
	private void updating_row(int x) {
    	JFrame frame=new JFrame("Updating row");
    	frame.setIconImage(new ImageIcon("logo.png").getImage());
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel panel1=new JPanel();
        panel1.add(v.new Label("Press button to invert your cell."));
        panel1.setMaximumSize(new Dimension(1100, 50));
        panel.add(panel1);
        panel1=new JPanel();
        panel1.add(v.new Label("Color represent cell value(Black:wall, white:empty space)"));
        panel1.setMaximumSize(new Dimension(1100, 50));
        panel.add(panel1);
        int [] row;
        frame.setVisible(true);
        frame.add(panel);
        @SuppressWarnings("serial")
		class BattlefieldButton extends Button{
        	public int i;
        	public BattlefieldButton(int i) {
        		this.i=i;
        	}
        }
        BattlefieldButton []button;
        if(menu==2) {
        	button=new BattlefieldButton[m];	
        	row=new int[m];
        	for(int i=0;i<m;i++) {
        		row[i]=battlefield[x][i];
        	}
        	panel1=new JPanel();
        	for(int i=0;i<m;i++) {
        		BattlefieldButton button1=new BattlefieldButton(i);
        		button1.setMaximumSize(new Dimension(100,100));
        		button1.setText(" ");
        		if(row[i]==1) {
        			button1.setBackground(Color.BLACK);
        		}
        		else {
        			button1.setBackground(Color.WHITE);
        		}
        		button1.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(button1.getBackground()==Color.BLACK) {
							button1.setBackground(Color.WHITE);
							row[button1.i]=0;
						}
						else {
							button1.setBackground(Color.BLACK);
							row[button1.i]=1;
						}
						button[button1.i]=button1;
					}
        		});
        		button[i]=button1;
        		panel1.add(button[i]);
        	}
        	panel.add(panel1);
        	
        }
        else {
        	button=new BattlefieldButton[n];
        	row=new int[n];
        	for(int i=0;i<n;i++) {
        		row[i]=battlefield[i][x];
        	}
        	for(int i=0;i<n;i++) {
        		panel1=new JPanel();
        		BattlefieldButton button1=new BattlefieldButton(i);
        		button1.setText(" ");
        		if(row[i]==1) {
        			button1.setBackground(Color.BLACK);
        		}
        		else {
        			button1.setBackground(Color.WHITE);
        		}
        		button1.setMaximumSize(new Dimension(100,100));
        		button1.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(button1.getBackground()==Color.BLACK) {
							button1.setBackground(Color.WHITE);
							row[button1.i]=0;
						}
						else {
							button1.setBackground(Color.BLACK);
							row[button1.i]=1;
						}
						button[button1.i]=button1;
					}
        		});
        		button[i]=button1;
        		panel1.add(button[i]);
        		panel.add(panel1);
        	}
        }
        	
        JButton button1=v.new Button("Save row");
        button1.addActionListener(new ActionListener() {
    		@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
    			frame.setVisible(false);
    			switch(menu) {
	            	case 1:
	            		for(int i=0;i<n;i++) {
							System.out.println("battlefield["+(i+1)+"]"+"["+(x+1)+"]="+(battlefield[i][x]=row[i]));
						}
	            		break;
	            	case 2:
	            		for(int i=0;i<m;i++) {
							System.out.print("battlefield["+(x+1)+"]"+"["+(i+1)+"]="+(battlefield[x][i]=row[i]));
						}
	            		break;
    			}
    			reset_frame();
    		}
    	});
        panel.add(button1);
        frame.pack();
	}
	public void count_barracks() {
		c.count_barracks();
		JFrame frame=new JFrame("Number of barracks");
		frame.setIconImage(new ImageIcon("logo.png").getImage());
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	JPanel panel=new JPanel();
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    	JPanel panel1=new JPanel();
    	panel1.add(v.new Label("Number of barracks : "+num_barracks));
    	panel.add(panel1);
    	JButton button=v.new Button("Ok");
    	button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
    	});
    	panel.add(button);
    	frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}
	public void find_free_space() {
		c.find_free_space();
		JFrame frame=new JFrame("Free space");
		frame.setIconImage(new ImageIcon("logo.png").getImage());
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	JPanel panel=new JPanel();
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    	JPanel panel1=new JPanel();
    	panel1.add(v.new Label("Free space for soldiers in barracks : "+free_space));
    	panel.add(panel1);
    	JButton button=v.new Button("Ok");
    	button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
    	});
    	panel.add(button);
    	frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}
	private static void soldiers_distribution() throws IOException {
		JFrame frame=new JFrame("Soldier distribution");
		frame.setIconImage(new ImageIcon("logo.png").getImage());
    	JPanel panel=new JPanel();
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    	JPanel panel1;
    	if(c.soldier_distribution()) {
    		 panel1=new JPanel();
    		 panel1.add(v.new Label("You can distribute soldiers in that way : "));
    		 panel.add(panel1);
    	     for(int i=0;i<k;i++) {
    	    	 panel1=new JPanel();
    	    	 panel1.add(v.new Label("Squad №"+(i+1)+" are going to barrack at these coords:"+"( y - "+(virtual_barracks[i][1]+1)+",x - "+(virtual_barracks[i][2]+1)+")"));
    	    	 panel.add(panel1);
    	     }
    	}
    	else {
    		panel1=new JPanel();
	    	panel1.add(v.new Label("You can't distribute soldiers"));
	    	panel.add(panel1);
    	}
    	JButton button=v.new Button("Ok");
    	button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
    	});
    	panel.add(button);
    	frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
		
		
	}
	public void top_squads() {
		c.top_squads();
		JFrame frame=new JFrame("Top squads");
		frame.setIconImage(new ImageIcon("logo.png").getImage());
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	JPanel panel=new JPanel();
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    	JPanel panel1;
    	
    	for(int i=0;i<k;i++) {
    		panel1=new JPanel();
    		panel1.add(v.new Label((i+1)+". "+virtualsquads[i]));
    		panel1.setMaximumSize(new Dimension(100,50));
    		panel.add(panel1);
    	}
    	JButton button=v.new Button("Ok");
    	button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
    	});
    	panel.add(button);
    	frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}
	public void max_square() {
		c.max_square();
		JFrame frame=new JFrame("Maximum square");
		frame.setIconImage(new ImageIcon("logo.png").getImage());
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	JPanel panel=new JPanel();
    	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    	JPanel panel1;
		
		if(max_square.length==3) {
			panel1=new JPanel();
			panel1.add(v.new Label("Area of square with only 0 under gen diagonal="+max_square[0]));
			panel.add(panel1);
			panel1=new JPanel();
			panel1.add(v.new Label("Length of square with only 0 under gen diagonal="+(int)Math.sqrt(max_square[0])));
			panel.add(panel1);
			panel1=new JPanel();
			panel1.add(v.new Label("X-coordinate of square with only 0 under gen diagonal="+max_square[1]));
			panel.add(panel1);
			panel1=new JPanel();
			panel1.add(v.new Label("Y-coordinate of square with only 0 under gen diagonal="+max_square[2]));
			panel.add(panel1);
		}
		else {
			panel1=new JPanel();
			panel1.add(v.new Label("There is no such square"));
			panel1.setMaximumSize(new Dimension(200,50));
			panel.add(panel1);
		}
		JButton button=v.new Button("Ok");
    	button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
    	});
    	panel.add(button);
    	frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}
	public static void main(String[] args) throws IOException { 
		System.out.println("What kind of interface do you want to use?");
		System.out.println("1 - graphic");
		System.out.println("2 - console");
		String menu1;
		while(true) {
			System.out.print(" Menu-");
			menu1=in.nextLine();
			try {
				if(Integer.parseInt(menu1)>=0 && Integer.parseInt(menu1)<=2) {
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
		if(menu==1) {
			v=new View();
			try {
				c.battlefield_read();
			}
			catch(Exception e){
				System.out.println("File not found");
			}
	        reset_frame(); 
		}
		else {
			c=new Controller();
		}
    }
}
