package com.game;

public class PuzzleTree
{
	private PuzzleTreeNode root,pattern,tail,solution;

	PuzzleTree(int a[],int b,int p[],int pb)
	{
		root=tail=new PuzzleTreeNode(a,b);
		pattern=new PuzzleTreeNode(p,pb);
	}

	public boolean search(PuzzleTreeNode tmp)
	{
		if(tmp!=null)
		{
			for(PuzzleTreeNode node=root;node!=null;node=node.next)
				if(node.compare(tmp))
					return true;
		}
		return false;
	}

	private boolean register(PuzzleTreeNode node,PuzzleTreeNode tmp)
	{
		if(tmp!=null)
		{
			tmp.prev=tail;
			tmp.parent=node;
			tail.next=tmp;
			tail=tmp;
			if(tmp.compare(pattern))
				return true;
			PuzzleTreeNode t=tmp.prev;
			t.next=null;
			if(search(tmp))
			{
				tail=tmp.prev;
				tail.next=null;
			}
			t.next=tmp;
		}
/*		if(tmp!=null&&!search(tmp))
		{
			tmp.prev=tail;
			tmp.parent=node;
			tail.next=tmp;
			tail=tmp;
			if(tmp.compare(pattern))
				return true;
		}*/
		return false;
	}

	public void makeTree()
	{
		PuzzleTreeNode node=root;
		if(node.compare(pattern))
			return;
		for(;node!=null;node=node.next)
		{
			if(register(node,node.moveLeft())||register(node,node.moveRight())||register(node,node.moveUp())||register(node,node.moveDown()))
			{
				break;
			}
		}
		for(node=tail;node.prev!=null;node=node.prev)
		{
			node.prev=node.parent;
			node.parent=null;
			node.prev.next=node;
		}
	}

	public PuzzleTreeNode getSolutionNext()
	{
		if(solution==null)
			solution=root;
		return solution=solution.next;
	}

	public PuzzleTreeNode getSolutionPrev()
	{
		if(solution==null)
			solution=tail;
		return solution=solution.prev;
	}
}