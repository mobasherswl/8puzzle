package com.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game implements ActionListener
{
	JFrame frame;
	JButton btn[],btnPlay[];
	JMenuBar mBar;
	JMenu mOption,mHelp;
	JMenuItem mAbout;
	JRadioButtonMenuItem mOptionHuman,mOptionComputer;
	JPanel pnlBtn,pnlPlay;
	private ButtonGroup bgPlayer;
	PuzzleTree tree;
	PuzzleTreeNode pattern;
	int blankIdx;

	Game()
	{
		int[] data={1,2,3,8,9,4,7,6,5};
		blankIdx=4;
		pattern=new PuzzleTreeNode(data,blankIdx);

		frame=new JFrame("Puzzle");
		frame.setResizable(false);
		frame.setSize(390,400);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pnlBtn=new JPanel(new GridLayout(3,3));
		pnlPlay=new JPanel();

		btn=new JButton[9];
		btnPlay=new JButton[3];

		for(int i=0;i<3;i++)
		{
			btnPlay[i]=new JButton();
			pnlPlay.add(btnPlay[i]);
			btnPlay[i].addActionListener(this);
			btnPlay[i].setEnabled(false);
		}

		btnPlay[0].setText("Back");
		btnPlay[1].setText("Start");
		btnPlay[2].setText("Next");

		mOption=new JMenu("Option");
		mOptionHuman=new JRadioButtonMenuItem("Human",true);
		mOptionComputer=new JRadioButtonMenuItem("Computer");
		mHelp=new JMenu("Help");
		mAbout=new JMenuItem("About");

		bgPlayer=new ButtonGroup();
		bgPlayer.add(mOptionHuman);
		bgPlayer.add(mOptionComputer);

		mBar=new JMenuBar();
		mBar.add(mOption);
		mBar.add(mHelp);

		mOption.add(mOptionComputer);
		mOption.add(mOptionHuman);
		mHelp.add(mAbout);

		mOptionHuman.addActionListener(this);
		mOptionComputer.addActionListener(this);
		mAbout.addActionListener(this);

		for(int i=0;i<9;i++)
		{
			btn[i]=new JButton(""+pattern.data[i]);
			btn[i].addActionListener(this);
			pnlBtn.add(btn[i]);
		}
		btn[blankIdx].setText("");
		btn[blankIdx].setEnabled(false);

		frame.add(mBar,BorderLayout.NORTH);
		frame.add(pnlBtn,BorderLayout.CENTER);
		frame.add(pnlPlay,BorderLayout.SOUTH);
		frame.setVisible(true);
	}

	private int selectPattern(int ary[])
	{
		int sum=0,i,j;
		for(i=0;i<ary.length;i++)
		{
			for(j=i+1;j<ary.length;j++)
				if(ary[j]<ary[i])
					sum++;
		}
		return sum;
	}

	public void move(int i)
	{
		btn[blankIdx].setText(btn[i].getText());
		btn[blankIdx].setEnabled(true);
		blankIdx=i;
		btn[blankIdx].setText("");
		btn[blankIdx].setEnabled(false);
	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==mOptionHuman)
		{
			tree=null;
			for(int i=0;i<btnPlay.length;i++)
				btnPlay[i].setEnabled(false);
		}
		else if(ae.getSource()==mOptionComputer)
		{
			btnPlay[1].setEnabled(true);
		}
		else if(ae.getSource()==mAbout)
		{
			JOptionPane.showMessageDialog(frame,"Basic 8-Puzzle com.game.Game solver by Mobasher: mobasherswl@yahoo.com","Puzzle",JOptionPane.INFORMATION_MESSAGE);
		}
		else if(ae.getSource()==btnPlay[1])
		{
			frame.setTitle("Puzzle - Analysing, please wait...");
			int ary[]=new int[9];
			for(int i=0;i<9;i++)
				if(i!=blankIdx)
					ary[i]=Integer.parseInt(btn[i].getText());
			tree=new PuzzleTree(ary,blankIdx,pattern.data,pattern.blankIdx);
			tree.makeTree();
			btnPlay[1].setEnabled(false);
			btnPlay[2].setEnabled(true);
			frame.setTitle("Puzzle - Click Next");
		}
		else if(ae.getSource()==btnPlay[2])
		{
			PuzzleTreeNode node=tree.getSolutionNext();
			if(node!=null)
			{
				move(node.blankIdx);
				btnPlay[0].setEnabled(true);
				if(node.next==null)
				{
					btnPlay[2].setEnabled(false);
					frame.setTitle("Puzzle");
				}
			}
		}
		else if(ae.getSource()==btnPlay[0])
		{
			PuzzleTreeNode node=tree.getSolutionPrev();
			if(node!=null)
			{
				move(node.blankIdx);
				btnPlay[2].setEnabled(true);
				if(node.prev==null)
				{
					btnPlay[0].setEnabled(false);
					frame.setTitle("Puzzle");
				}
			}
		}
		else if(mOptionHuman.isSelected())
		{
			JButton b=(JButton)ae.getSource();
			int i;
			for(i=0;i<9;i++)
				if(btn[i]==b)
					break;
			if(i+1==blankIdx&&blankIdx%3!=0)
				move(i);
			else if(i-1==blankIdx&&blankIdx%3<2)
				move(i);
			else if(i+3==blankIdx&&blankIdx-3>=0)
				move(i);
			else if(i-3==blankIdx&&blankIdx+3<9)
				move(i);
		}
	}

	public static void main(String arg[])
	{
		Game g=new Game();
	}
}