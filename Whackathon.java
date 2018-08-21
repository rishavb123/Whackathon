import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
public class Whackathon extends JPanel implements KeyListener,Runnable
{

	/*  1-->right
		2-->left
		3-->down
		4-->up
	*/
	JFrame frame;
	char[][] mazeBoard=new char[151][64];
	Thread delay;
	String mazeDesign;
	int[] you={255,255,255};
	int p1life=2;
	int bob4=0;
	boolean power=false;
	int invin=0;
	int bob=0;
	int boxBreaker=0;
	int bob3=0;
	boolean there=false;
	int p2life=2;
	boolean colordown=false,colorup=true;
	boolean p1left=false,p2left=false,p1right=false,p2right=false,p1up=false,p2up=false,p1down=false,p2down=false;
	int win=0;
	int x=10;
	int y=10;
	boolean extraBullet=false;
	int bob2=0;
	int[] color={0,200,0};
	boolean heart1=false;
	int direction=1;
	int direction2=2;
	int x2=1480;
	int y2=620;
	int[][] p1shots=new int[5][3];
	int[][] p2shots=new int[5][3];
	int moves=0;
	boolean key1=false;
	boolean key2=false;
	public Whackathon()
	{
		setBoard();
		frame=new JFrame();
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1550,800);
		frame.setVisible(true);
		frame.addKeyListener(this);
		delay=new Thread(this);
		delay.start();
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0,0,1550,800);
		g.setFont(new Font("Times New Roman",Font.BOLD,18));
		for(int e=0;e<p1shots.length;e++)
		{
			g.setColor(new Color(255,105,180));
			g.fillRect(p1shots[e][0],p1shots[e][1],5,5);
		}
		for(int e=0;e<p2shots.length;e++)
		{
			g.setColor(Color.YELLOW);
			g.fillRect(p2shots[e][0],p2shots[e][1],5,5);
		}

		g.drawString("P2 lives: "+p2life,300,700);
		g.setColor(Color.CYAN);
		g.drawString("P1 lives: "+p1life,0,700);
		for(int b=0;b<64;b++)
		{
			color[1]=200;
			color[2]=0;
			for(int a=0;a<151;a++)
			{
				g.setColor(new Color(color[0],color[1]+55,color[2]+55));
				color[1]--;
				color[2]++;
				if(mazeBoard[a][b]==35)
					g.fillRect(10*a,10*b,10,10);
			}
		}
		if(extraBullet)
		{
			g.setColor(Color.CYAN);
			g.fillOval(1120,310,10,10);
		}
		if(heart1)
		{
			g.setColor(Color.RED);
			g.fillOval(370,310,10,10);
		}

		if(there)
		{
			g.setColor(Color.WHITE);
			g.fillOval(70,200,10,10);
		}
		if(power)
		{
			g.setColor(new Color(255,0,127));
			g.fillOval(810,540,10,10);
		}
		g.setColor(new Color(255,105,180));
		g.fillRect(x,y,10,10);
		g.setColor(Color.YELLOW);
		g.fillRect(x2,y2,10,10);
		if(boxBreaker==1)
		{
			g.setColor(new Color(255,165,0));
			g.fillRect(x,y,10,10);
		}
		else if(boxBreaker==2)
		{
			g.setColor(new Color(255,165,0));
			g.fillRect(x2,y2,10,10);
		}
		if(invin==1)
		{
			g.setColor(new Color(you[0],you[1],you[2]));
			g.fillRect(x,y,10,10);
		}
		else if(invin==2)
		{
			g.setColor(new Color(you[0],you[1],you[2]));
			g.fillRect(x2,y2,10,10);
		}
		if(win!=0)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Times New Roman", Font.BOLD,200));
			g.drawString("Player "+win+" wins",200,350);
			g.setFont(new Font("Times New Roman", Font.BOLD,50));
			g.drawString("Click Space to restart",300,450);
		}
	}
	public void setBoard()
	{
		for(int q=0;q<5;q++)
		{
			for(int w=0;w<3;w++)
			{
				p1shots[q][w]=-123;
				p2shots[q][w]=-123;
			}
		}
		for(int e=0;e<5;e++)
		{
			p1shots[e][2]=0;
			p2shots[e][2]=0;
		}
		File name=new File("Bob.txt");
		int r=0;
		try
		{
			BufferedReader input = new BufferedReader(new FileReader(name));
			StringBuffer buffer = new StringBuffer();
			String text;
			while((text=input.readLine())!=null)
			{
				for(int x=0;x<text.length();x++)
					mazeBoard[x][r]=(char)text.charAt(x);
				r++;
			}
		}
		catch(IOException io)
		{
			System.err.println("BOBOBOBOBOBOBOBOBOB SAYS FILE IS TOO SPMEXY FOR HIS TASTES");
		}
	}
	public void run()
	{
		while(true)
		{
			if(color[0]==0)
			{
				colordown=false;
				colorup=true;
			}
			if(color[0]==255)
			{
				colordown=true;
				colorup=false;
			}
			if(colordown)
				color[0]-=1;
			if(colorup)
				color[0]+=1;
			for(int u=0;u<3;u++)
				you[u]=(int)(Math.random()*255);
			if(win==0)
			{
				if(x==370&&y==310&&heart1)
				{
					p1life++;
					heart1=false;
					bob=0;
				}
				if(x2==370&&y2==310&&heart1)
				{
					p2life++;
					heart1=false;
					bob=0;
				}
				if(x==1120&&y==310&&extraBullet)
				{
					bob2=0;
					extraBullet=false;
					int[][] temp=new int[p1shots.length+3][3];
					for(int ya=0;ya<temp.length;ya++)
						for(int ay=0;ay<temp[ya].length;ay++)
							temp[ya][ay]=-123;
					for(int ya=0;ya<p1shots.length;ya++)
						for(int ay=0;ay<p1shots[ya].length;ay++)
							temp[ya][ay]=p1shots[ya][ay];
					p1shots=temp;
				}
				if(x2==1120&&y2==310&&extraBullet)
				{
					bob2=0;
					extraBullet=false;
					int[][] temp=new int[p2shots.length+3][3];
					for(int ya=0;ya<temp.length;ya++)
						for(int ay=0;ay<temp[ya].length;ay++)
							temp[ya][ay]=-123;
					for(int ya=0;ya<p2shots.length;ya++)
						for(int ay=0;ay<p2shots[ya].length;ay++)
							temp[ya][ay]=p2shots[ya][ay];
					p2shots=temp;
				}
				if(x==70&&there&&y==200)
				{
					bob3=0;
					boxBreaker=1;
					there=false;
				}
				if(x2==70&&there&&y2==200)
				{
					bob3=0;
					boxBreaker=2;
					there=false;
				}
				if(x==810&&y==540)
				{
					invin=1;
					bob4=0;
					power=false;
				}
				if(x2==810&&y2==540)
				{
					invin=2;
					bob4=0;
					power=false;
				}
				if(bob3==1200)
					boxBreaker=0;
				if(bob4==500)
					invin=0;
				bob++;
				bob2++;
				bob3++;
				bob4++;
				if(bob==3000)
					heart1=true;
				if(bob2==2000)
					extraBullet=true;
				if(bob3==4000)
					there=true;
				if(bob4==4500)
					power=true;
				if(p1right)
				{
					p1left=false;
					p1down=false;
					p1up=false;
				}
				if(p1left)
				{
					p1right=false;
					p1down=false;
					p1up=false;
				}
				if(p1down)
				{
					p1up=false;
					p1right=false;
					p1left=false;
				}
				if(p1up)
				{
					p1down=false;
					p1right=false;
					p1left=false;
				}
				if(p2right)
				{
					p2left=false;
					p2down=false;
					p2up=false;
				}
				if(p2left)
				{
					p2right=false;
					p2down=false;
					p2up=false;
				}
				if(p2down)
				{
					p2up=false;
					p2right=false;
					p2left=false;
				}
				if(p2up)
				{
					p2down=false;
					p2right=false;
					p2left=false;
				}
				//murdering stuff
				for(int ob=0;ob<p1shots.length;ob++)
				{
					if(((p1shots[ob][0]>x2 && p1shots[ob][0]<x2+10 && p1shots[ob][1]>y2 && p1shots[ob][1]<y2+10)||(p1shots[ob][0]+10>x2 && p1shots[ob][0]+10<x2+10 && p1shots[ob][1]>y2 && p1shots[ob][1]<y2+10)||(p1shots[ob][0]-10>x2 && p1shots[ob][0]-10<x2+10 && p1shots[ob][1]>y2 && p1shots[ob][1]<y2+10)||(p1shots[ob][0]>x2 && p1shots[ob][0]<x2+10 && p1shots[ob][1]+10>y2 && p1shots[ob][1]+10<y2+10)||(p1shots[ob][0]>x2 && p1shots[ob][0]<x2+10 && p1shots[ob][1]-10>y2 && p1shots[ob][1]-10<y2+10))&&invin!=2)
					{
						p1shots[ob][0]=-123;
						p1shots[ob][1]=-123;
						p1shots[ob][2]=0;
						p2life--;
					}
				}
				for(int ob=0;ob<p2shots.length;ob++)
				{
					if(((p2shots[ob][0]>x && p2shots[ob][0]<x+10 && p2shots[ob][1]>y && p2shots[ob][1]<y+10)||(p2shots[ob][0]+10>x && p2shots[ob][0]+10<x+10 && p2shots[ob][1]>y && p2shots[ob][1]<y+10)||(p2shots[ob][0]-10>x && p2shots[ob][0]-10<x+10 && p2shots[ob][1]>y && p2shots[ob][1]<y+10)||(p2shots[ob][0]>x && p2shots[ob][0]<x+10 && p2shots[ob][1]+10>y && p2shots[ob][1]+10<y+10)||(p2shots[ob][0]>x && p2shots[ob][0]<x+10 && p2shots[ob][1]-10>y && p2shots[ob][1]-10<y+10))&&invin!=1)
					{
						p2shots[ob][0]=-123;
						p2shots[ob][1]=-123;
						p2shots[ob][2]=0;
						p1life--;
					}
				}
				//winning stuff
				if(p1life<=0)
					win=2;
				if(p2life<=0)
					win=1;
				//shooting stuff
				for(int u=0;u<p1shots.length;u++)
				{
					if(p1shots[u][0]>0&&p1shots[u][0]<1500&&p1shots[u][1]>0&&p1shots[u][1]<640)
					{
						if(p1shots[u][2]==1)
							if((mazeBoard[(p1shots[u][0]+5)/10][(p1shots[u][1]+5)/10]!=32)||(mazeBoard[(p1shots[u][0]+15)/10][(p1shots[u][1]+5)/10]!=32))
							{
								p1shots[u][0]=-123;
								p1shots[u][1]=-123;
								p1shots[u][2]=0;
							}
						if(p1shots[u][2]==2)
							if((mazeBoard[(p1shots[u][0]+5)/10][(p1shots[u][1]+5)/10]!=32)||(mazeBoard[(p1shots[u][0]-5)/10][(p1shots[u][1]+5)/10]!=32))
							{
								p1shots[u][0]=-123;
								p1shots[u][1]=-123;
								p1shots[u][2]=0;
							}
						if(p1shots[u][2]==3)
							if((mazeBoard[(p1shots[u][0]+5)/10][(p1shots[u][1]+5)/10]!=32)||(mazeBoard[(p1shots[u][0]+5)/10][(p1shots[u][1]+15)/10]!=32))
							{
								p1shots[u][0]=-123;
								p1shots[u][1]=-123;
								p1shots[u][2]=0;
							}
						if(p1shots[u][2]==4)
							if((mazeBoard[(p1shots[u][0]+5)/10][(p1shots[u][1]+5)/10]!=32)||(mazeBoard[(p1shots[u][0]+5)/10][(p1shots[u][1]-5)/10]!=32))
							{
								p1shots[u][0]=-123;
								p1shots[u][1]=-123;
								p1shots[u][2]=0;
							}
					}
				}
				for(int u=0;u<p2shots.length;u++)
				{
					if(p2shots[u][0]>0&&p2shots[u][0]<1500&&p2shots[u][1]>0&&p2shots[u][1]<640)
					{
						if(p2shots[u][2]==1)
							if((mazeBoard[(p2shots[u][0]+5)/10][(p2shots[u][1]+5)/10]!=32)||(mazeBoard[(p2shots[u][0]+15)/10][(p2shots[u][1]+5)/10]!=32))
							{
								p2shots[u][0]=-123;
								p2shots[u][1]=-123;
								p2shots[u][2]=0;
							}
						if(p2shots[u][2]==2)
							if((mazeBoard[(p2shots[u][0]+5)/10][(p2shots[u][1]+5)/10]!=32)||(mazeBoard[(p2shots[u][0]-5)/10][(p2shots[u][1]+5)/10]!=32))
							{
								p2shots[u][0]=-123;
								p2shots[u][1]=-123;
								p2shots[u][2]=0;
							}
						if(p2shots[u][2]==3)
							if((mazeBoard[(p2shots[u][0]+5)/10][(p2shots[u][1]+5)/10]!=32)||(mazeBoard[(p2shots[u][0]+5)/10][(p2shots[u][1]+15)/10]!=32))
							{
								p2shots[u][0]=-123;
								p2shots[u][1]=-123;
								p2shots[u][2]=0;
							}
						if(p2shots[u][2]==4)
							if((mazeBoard[(p2shots[u][0]+5)/10][(p2shots[u][1]+5)/10]!=32)||(mazeBoard[(p2shots[u][0]+5)/10][(p2shots[u][1]-5)/10]!=32))
							{
								p2shots[u][0]=-123;
								p2shots[u][1]=-123;
								p2shots[u][2]=0;
							}
					}
				}
				for(int u=0;u<p1shots.length;u++)
				{
					switch(p1shots[u][2])
					{
						case 1:
							p1shots[u][0]+=20;
								break;
						case 2:
							p1shots[u][0]-=20;
								break;
						case 3:
							p1shots[u][1]+=20;
								break;
						case 4:
							p1shots[u][1]-=20;
								break;
					}
				}
				for(int u=0;u<p2shots.length;u++)
				{
					switch(p2shots[u][2])
					{
						case 1:
							p2shots[u][0]+=20;
								break;
						case 2:
							p2shots[u][0]-=20;
								break;
						case 3:
							p2shots[u][1]+=20;
								break;
						case 4:
							p2shots[u][1]-=20;
								break;
					}
				}
				//breaking stuff
				if(boxBreaker==1)
				{
					mazeBoard[x/10][y/10]=(char)32;
				}
				if(boxBreaker==2)
				{
					mazeBoard[x2/10][y2/10]=(char)32;
				}
				//moving stuff
				if(p1right)
				{
					if(x<1480)
					if(mazeBoard[x/10+1][y/10]!=35||(boxBreaker==1))
					{
						x+=10;
						if(direction!=1)
							direction=1;
						moves++;
					}
				}
				if(p2up)
				{
					if(y2>10)
					if(mazeBoard[x2/10][y2/10-1]!=35||(boxBreaker==2))
					{
						y2-=10;
						if(direction2!=4)
							direction2=4;
						moves++;
					}
				}
				if(p2right)
				{
					if(x2>10)
					if(mazeBoard[x2/10-1][y2/10]!=35||(boxBreaker==2))
					{
						x2-=10;
						if(direction2!=2)
							direction2=2;
						moves++;
					}
				}
				if(p1down)
				{
					if(y<620)
					if(mazeBoard[x/10][y/10+1]!=35||(boxBreaker==1))
					{
						y+=10;
						if(direction!=3)
							direction=3;
						moves++;
					}
				}
				if(p2left)
				{
					if(x2<1480)
					if(mazeBoard[x2/10+1][y2/10]!=35||(boxBreaker==2))
					{
						x2+=10;
						if(direction2!=1)
							direction2=1;
						moves++;
					}
				}
				if(p1left)
				{
					if(x>10)
						if(mazeBoard[x/10-1][y/10]!=35||boxBreaker==1)
						{
							x-=10;
							if(direction!=2)
								direction=2;
							moves++;
						}
				}
				if(p2down)
				{
					if(y2<620)
					if(mazeBoard[x2/10][y2/10+1]!=35||(boxBreaker==2))
					{
						y2+=10;
						if(direction2!=3)
							direction2=3;
						moves++;
					}
				}
				if(p1up)
				{
					if(y>10)
					if(mazeBoard[x/10][y/10-1]!=35||(boxBreaker==1))
					{
						y-=10;
						if(direction!=4)
							direction=4;
						moves++;
					}
				}
			}
			repaint();

			try
			{
				delay.sleep(50);
			}catch(InterruptedException e)
			{

			}
		}


	}
	public int find(int[][] arr)
	{
		for(int o=0;o<arr.length;o++)
			if(arr[o][0]==-123)
			{
				return o;
			}
		return -1;
	}

	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case 32:
				break;
			case 37:
				p1left=true;
				break;
			case 38:
				p1up=true;
				break;
			case 39:
				p1right=true;
				break;
			case 40:
				p1down=true;
				break;
			case 65:
				p2right=true;
				break;
			case 87:
				p2up=true;
				break;
			case 68:
				p2left=true;
				break;
			case 83:
				p2down=true;
				break;
		}
	}
	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case 32:
				if(win!=0)
				{
					win=0;
					p1life=2;
					p2life=2;
					x=10;
					y=10;
					x2=1480;
					y2=620;
					heart1=false;
					bob=0;
					bob4=0;
					invin=0;
					power=false;
					boxBreaker=0;
					there=false;
					extraBullet=false;
					bob2=0;
					bob3=0;
					setBoard();
					int[][]temp = new int[5][3];
					for(int q=0;q<5;q++)
					{
						for(int w=0;w<3;w++)
						{
							temp[q][w]=-123;
						}
					}
					for(int eu=0;eu<5;eu++)
					{
						temp[eu][2]=0;
					}
					int[][]temp2 = new int[5][3];
					for(int q=0;q<5;q++)
					{
						for(int w=0;w<3;w++)
						{
							temp2[q][w]=-123;
						}
					}
					for(int eu=0;eu<5;eu++)
					{
						temp2[eu][2]=0;
					}
					p1shots=temp;
					p2shots=temp2;
				}
				break;
			case 77:
				int a=find(p1shots);
				if(a!=-1)
				{
					p1shots[a][0]=x+3;
					p1shots[a][1]=y+3;
					p1shots[a][2]=direction;
				}
				break;
			case 81:
				int b=find(p2shots);
				if(b!=-1)
				{
					p2shots[b][0]=x2+3;
					p2shots[b][1]=y2+3;
					p2shots[b][2]=direction2;
				}
				break;
			case 37:
				p1left=false;
				break;
			case 38:
				p1up=false;
				break;
			case 39:
				p1right=false;
				break;
			case 40:
				p1down=false;
				break;
			case 65:
				p2right=false;
				break;
			case 87:
				p2up=false;
				break;
			case 68:
				p2left=false;
				break;
			case 83:
				p2down=false;
				break;
		}

	}
	public void keyTyped(KeyEvent e)
	{
	}
	public static void main(String args[])
	{
		Whackathon app=new Whackathon();
	}
}