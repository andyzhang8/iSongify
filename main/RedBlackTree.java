import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {

  protected static class RBTNode<T> extends Node<T> {
    public boolean isBlack = false;

    public RBTNode(T data) {
      super(data);
    }

    public RBTNode<T> getUp() {
      return (RBTNode<T>) this.up;
    }

    public RBTNode<T> getDownLeft() {
      return (RBTNode<T>) this.down[0];
    }

    public RBTNode<T> getDownRight() {
      return (RBTNode<T>) this.down[1];
    }
  }

  protected void enforceRBTreePropertiesAfterInsert(RBTNode<T> newNode) {
    if (newNode == root) {
      newNode.isBlack = true;
      return;
    }
    if (!(newNode.getUp()).isBlack && newNode.getUp() != null && newNode.getUp().getUp() != null) {
      RBTNode<T> grandparent = (newNode.getUp()).getUp();
      RBTNode<T> uncle = (newNode.up == grandparent.getDownLeft()) ? grandparent.getDownRight()
          : grandparent.getDownLeft();

      if (uncle != null && !uncle.isBlack) {
        newNode.getUp().isBlack = true;
        uncle.isBlack = true;
        grandparent.isBlack = false;
        enforceRBTreePropertiesAfterInsert(grandparent);
      } else {
        if (newNode == (newNode.getUp()).getDownRight()
            && newNode.getUp() == grandparent.getDownLeft() || newNode == (newNode.getUp()).getDownLeft()
                && newNode.getUp() == grandparent.getDownRight()) {
          rotate(newNode, newNode.getUp());
          rotate(newNode, newNode.getUp());
          newNode.isBlack = true;
          newNode.getDownLeft().isBlack = false;
          newNode.getDownRight().isBlack = false;
          newNode = newNode.getDownRight();

        } else if (newNode == (newNode.getUp()).getDownLeft()
            && newNode.getUp() == grandparent.getDownRight()) {
          rotate(newNode, newNode.getUp());
          rotate(newNode, newNode.getUp());
          newNode.isBlack = true;
          newNode.getDownLeft().isBlack = false;
          newNode.getDownRight().isBlack = false;
          newNode = newNode.getDownLeft();
        } else if (newNode == (newNode.getUp()).getDownLeft()) {
          (newNode.getUp()).isBlack = true;
          grandparent.isBlack = false;
          rotate(newNode.getUp(), newNode.getUp().getUp());
        } else {

          (newNode.getUp()).isBlack = true;
          grandparent.isBlack = false;
          rotate(newNode.getUp(), newNode.getUp().getUp());
        }
      }
    }
  }


  @Override
  public boolean insert(T data) throws NullPointerException {
    if (data == null) {
      throw new NullPointerException("Cannot insert data value null into the tree.");
    }
    RBTNode<T> newNode = new RBTNode<>(data);
    boolean inserted = this.insertHelper(newNode);
    if (inserted) {
      enforceRBTreePropertiesAfterInsert(newNode);
      ((RBTNode<T>) root).isBlack = true;
    }
    return inserted;
  }

  /**
   * Test case 1: Inserting a black node as the root.
   * 
   */
  @Test
  public void tester1() {
    RedBlackTree<Integer> tree = new RedBlackTree<>();
    tree.insert(0);
    RBTNode<Integer> testIfBlack = ((RBTNode<Integer>) (tree.root));
    assertTrue(testIfBlack.isBlack);
  }

  /**
   * Test case 2: Insert node that requires right rotation
   */
  @Test
  public void tester2() {
    RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
    tree.insert(3);
    tree.insert(2);
    tree.insert(1);
    String actual = tree.toLevelOrderString();
    String expected = "[ 2, 1, 3 ]";
    assertTrue(expected.equals(actual)
        && (!(((RBTNode<Integer>) (tree.root)).getDownLeft()).isBlack)
        && (((RBTNode<Integer>) (tree.root)).isBlack)
        && (!(((RBTNode<Integer>) (tree.root)).getDownRight()).isBlack));
  }

  /**
   * Test case 3: Inserting a red node as the right child of a black parent.
   * 
   */
  @Test
  public void tester3() {
    RedBlackTree<Integer> tree = new RedBlackTree<>();
    tree.insert(10);
    tree.insert(15);
    RBTNode<Integer> testIfBlack = ((RBTNode<Integer>) (tree.root.down[1]));
    assertTrue(!testIfBlack.isBlack);
  }

}
