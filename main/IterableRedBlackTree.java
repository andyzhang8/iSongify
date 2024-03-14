import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Iterator;
import java.util.Stack;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;

public class IterableRedBlackTree<T extends Comparable<T>> extends RedBlackTree<T>
    implements IterableSortedCollection<T> {

  private Comparable<T> sentinel = (T o) -> -1;
  private Comparable<T> startPoint = sentinel;

  public void setIterationStartPoint(Comparable<T> startPoint) {
    this.startPoint = startPoint;
  }

  public Iterator<T> iterator() {
    return new RBTIterator<>(root, startPoint);
  }

  private static class RBTIterator<R> implements Iterator<R> {
    private Stack<Node<R>> stack;
    private Comparable<R> startPoint;

    public RBTIterator(Node<R> root, Comparable<R> startPoint) {
      stack = new Stack<>();
      this.startPoint = startPoint;
      buildStackHelper(root);
      
    }


    private void buildStackHelper(Node<R> node) {
      if (node == null) {
          return; 
      }
      if (startPoint != null && startPoint.compareTo(node.data) > 0) {
          buildStackHelper(node.down[1]);
      } 
      else {
          stack.push(node);
          buildStackHelper(node.down[0]); 
      }
  }






    public boolean hasNext() {
      return !stack.isEmpty();
    }

    public R next() {
      if (!hasNext())
        throw new NoSuchElementException();
      Node<R> node = stack.pop();
      buildStackHelper(node.down[1]);
      return node.data;
    }

  }


  //Checks functionality with Integers
  @Test
  public void testIteratorAscendingOrder() {
      IterableRedBlackTree<Integer> tree = new IterableRedBlackTree<>();
      tree.insert(4);
      tree.insert(2);
      tree.insert(6);
      tree.insert(1);
      tree.insert(3);
      tree.insert(5);
      tree.insert(7);

      Iterator<Integer> iterator = tree.iterator();
      int expected = 1;
      System.out.println(iterator.hasNext());
      while (iterator.hasNext()) {
          assertEquals(expected++, iterator.next());
      }
  }
  //Checks functionality with Strings and tests setIterationStartPoint
  @Test
  public void testIteratorWithStartPoint() {
      IterableRedBlackTree<String> tree = new IterableRedBlackTree<>();
      tree.insert("D");
      tree.insert("B");
      tree.insert("F");
      tree.insert("A");
      tree.insert("C");
      tree.insert("E");
      tree.insert("G");

      tree.setIterationStartPoint("C");
      Iterator<String> iterator = tree.iterator();
      char expected = 'C';
      System.out.println(iterator.hasNext());
      while (iterator.hasNext()) {
          assertEquals(expected++, iterator.next().charAt(0));
      }
      
  }
  //Checks functionality with duplicates
  @Test
  public void testIteratorWithDuplicates() {
      IterableRedBlackTree<Integer> tree = new IterableRedBlackTree<>();
      tree.insert(4);
      tree.insert(2);
      tree.insert(6);
      tree.insert(1);
      tree.insert(3);
      tree.insert(5);
      tree.insert(7);
      tree.insert(4);

      Iterator<Integer> iterator = tree.iterator();
      int[] expected = {1,2,3,4,4,5,6,7};
      int i = 0;
      System.out.println(iterator.hasNext());
      while (iterator.hasNext()) {
          assertEquals(expected[i++], iterator.next());
      }
  }

  /**
   * Performs a naive insertion into a binary search tree: adding the new node in a leaf position
   * within the tree. After this insertion, no attempt is made to restructure or balance the tree.
   * 
   * @param node the new node to be inserted
   * @return true if the value was inserted, false if is was in the tree already
   * @throws NullPointerException when the provided node is null
   */
  @Override
  protected boolean insertHelper(Node<T> newNode) throws NullPointerException {
    if (newNode == null)
      throw new NullPointerException("new node cannot be null");

    if (this.root == null) {
      // add first node to an empty tree
      root = newNode;
      size++;
      return true;
    } else {
      // insert into subtree
      Node<T> current = this.root;
      while (true) {
        int compare = newNode.data.compareTo(current.data);
        if (compare <= 0) {
          // insert in left subtree
          if (current.down[0] == null) {
            // empty space to insert into
            current.down[0] = newNode;
            newNode.up = current;
            this.size++;
            return true;
          } else {
            // no empty space, keep moving down the tree
            current = current.down[0];
          }
        } else {
          // insert in right subtree
          if (current.down[1] == null) {
            // empty space to insert into
            current.down[1] = newNode;
            newNode.up = current;
            this.size++;
            return true;
          } else {
            // no empty space, keep moving down the tree
            current = current.down[1];
          }
        }
      }
    }


  }
}
