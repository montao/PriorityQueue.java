public class PriorityQueue {

  private final int INITIALSIZE = 64 - 1;
  private int noOfElements = 0;

  private Object[] theQueue = new Object[INITIALSIZE];
  private double[] theTime = new double[INITIALSIZE];

    /**
     * Insert `element' in queue with priority `when'.
     * Low `when'-values are inserted ahead of higher values.
     * @param element Object to instert into queue.
     * @param when The associated priority.
     */
  synchronized public void enqueue( Object element, double when ) {
    ensureSpace( noOfElements + 1 );
    theQueue[ noOfElements ] = element;
    theTime[ noOfElements ] = when;
    noOfElements++;
    heapbubble();
  }


    /**
     * Remove first object from the queue.
     *
     * @throws IndexOutOfBoundsException if the queue is empty.
     */
  synchronized public void dequeue() {
    if ( noOfElements == 0 )
      throw new IndexOutOfBoundsException( "Priority queue empty" );

    noOfElements--;
    theQueue[ 0 ] = theQueue[ noOfElements ];
    theTime[ 0 ] = theTime[ noOfElements ];
    heapify();
  }


    /** Return first object in queue without removing it.
     *
     *@return The first object in the queue.
     */
  synchronized public Object getFirst() {
    if ( noOfElements == 0 )
      return null;
    else
      return theQueue[ 0 ];
  }


    /** 
     * Return priority value of first object in queue.
     *
     * The priority of the first object.
     */
  synchronized public double getFirstTime() {
    if ( noOfElements == 0 )
      return Double.POSITIVE_INFINITY;
    else {
      return theTime[ 0 ];
    }
  }


    /*
     * Empty state of the queue.
     *
     * @return True if queue is empty; false otherwise.
     */
  synchronized public boolean isEmpty() {
    return noOfElements == 0;
  }



    // Below are private methods for the administration of the queue
  // Enlarge the arrays if necessary
  private void ensureSpace( int space ) {
    if ( space > theTime.length ) {
      int newSize = (theQueue.length+1)*2 - 1;
      Object[] newQueue = new Object[ newSize ];
      double[] newTime = new double[ newSize ];

      for (int i=0; i<theQueue.length; i++) {
	newQueue[i] = theQueue[i];
	newTime[i] = theTime[i];
      }

      theQueue = newQueue;
      theTime = newTime;
    }
  }


  // Restore the heap property with new element inserted at top
  private void heapify() {
    int me = 0;

    while (true) {
      int lson = (me+1) * 2 - 1;
      int rson = (me+1) * 2;
      int firstToMove = me;

      if ( lson < noOfElements &&
	   theTime[ lson ] < theTime[ firstToMove ] )
	firstToMove = lson;

      if ( rson < noOfElements &&
	   theTime[ rson ] < theTime[ firstToMove ] )
	firstToMove = rson;

      if ( firstToMove == me )
	break;

      swap( me, firstToMove );
      me = firstToMove;
    }
  }


  // Restore the heap property with new element inserted at the bottom
  private void heapbubble() {
    int me = noOfElements-1;

    while (me > 0) {
      int father = (me+1) / 2 - 1;
      if ( theTime[ me ] >= theTime[ father ] )
	break;

      swap( me, father );
      me = father;
    }
  }


  // Swap contents of two slots in both arrays
  private void swap( int ix1, int ix2 ) {
    double tmp = theTime[ ix1 ];
    theTime[ ix1 ] = theTime[ ix2 ];
    theTime[ ix2 ] = tmp;

    Object tmpObj = theQueue[ ix1 ];
    theQueue[ ix1 ] = theQueue[ ix2 ];
    theQueue[ ix2 ] = tmpObj;
  }

}
