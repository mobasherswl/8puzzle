package com.game;

public class PuzzleTreeNode
{
	public int data[],blankIdx;
	public PuzzleTreeNode parent,prev,next;

	PuzzleTreeNode(int a[],int b)
	{
		data=new int[9];
		parent=prev=next=null;
		for(int i=0;i<9;i++)
			data[i]=a[i];
		blankIdx=b;
	}

	boolean compare(PuzzleTreeNode ptn)
	{
		if(blankIdx==ptn.blankIdx)
		{
			data[blankIdx]=ptn.data[blankIdx];
			for(int i=0;i<9;i++)
				if(data[i]!=ptn.data[i])
					return false;
			return true;
		}
		return false;
	}

	PuzzleTreeNode moveLeft()
	{
		if(blankIdx%3!=0)
		{
			PuzzleTreeNode tmp=new PuzzleTreeNode(data,blankIdx-1);
			tmp.data[blankIdx]=data[blankIdx-1];
			return tmp;
		}
		return null;
	}

	PuzzleTreeNode moveRight()
	{
		if(blankIdx%3<2)
		{
			PuzzleTreeNode tmp=new PuzzleTreeNode(data,blankIdx+1);
			tmp.data[blankIdx]=data[blankIdx+1];
			return tmp;
		}
		return null;
	}

	PuzzleTreeNode moveUp()
	{
		if(blankIdx-3>0)
		{
			PuzzleTreeNode tmp=new PuzzleTreeNode(data,blankIdx-3);
			tmp.data[blankIdx]=data[blankIdx-3];
			return tmp;
		}
		return null;
	}

	PuzzleTreeNode moveDown()
	{
		if(blankIdx+3<9)
		{
			PuzzleTreeNode tmp=new PuzzleTreeNode(data,blankIdx+3);
			tmp.data[blankIdx]=data[blankIdx+3];
			return tmp;
		}
		return null;
	}

	void print()
	{
		for(int i=0;i<9;i++)
		{
			if(i%3==0)
				System.out.println();
			if(blankIdx!=i)
				System.out.print(data[i]+", ");
			else
				System.out.print("B, ");
		}
	}
}