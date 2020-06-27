// 循环队列

public class LoopQueue<E> implements Queue<E> {

    private  E[] data;
    private int front,tail;// 指向队首和队尾
    private int size; // 实际元素个数 capacity是这个容器长度

    public  LoopQueue(int capacity){
        data = (E [])new Object[capacity + 1]; // 保留一个额外的空间
        front = 0;
        tail = 0;
        size = 0; // size
    }

    public LoopQueue(){
        this(10);
    }

    public  int getCapacity(){ // 真正能承载的数量是整个数组的长度-1
        return data.length -1;
    }

    @Override
    public  boolean isEmpty(){
        return front == tail;
    }

    @Override
    public int getSize(){
        return size;
    }

    @Override
    public void enqueue(E e) {
            // 看下一个tail的值是否等于front 如果等于那么说明满了
        if ((tail + 1 ) % data.length == front)
            resize(getCapacity() * 2);


        data[tail]  = e;
        // tail本应++ 但是循环队列，所以我们做一下求余操作
        tail = (tail +1) % data.length;
        size++;
    }


    // 出队
    @Override
    public E dequeue(){
        if(isEmpty())
            throw new IllegalArgumentException("can not deque from an empty queue");

        E ret = data[front];
        data[front] = null;

        // font++ 因为是循环的所以做一下求余操作
        front  = (front + 1) % data.length;
        size--;

        if(size == getCapacity() / 4 && getCapacity()/2 !=0){
            resize(getCapacity()/2);
        }
        return ret;
    }

    @Override
    public E getFront(){
        if(isEmpty())
            throw new IllegalArgumentException("Queue is empty");
        return data[front];
    }


    // resize方法
    private void resize(int newCapacity){

     E[] newData= (E[])new Object[newCapacity +1];

     for(int i = 0;i<size;i++){
         // 我们要把之前的队列中的元素放入新的从0到data.lenth-1的位置
         // newData[0] 是data[front]
         // newData[1] 是data[front+1]
         // 偏移是i+front
         // 因为可能越界从头开始，
         // 所以我们要对data.length进行一下求余
         newData[i] = data[(i+front) % data.length];
     }

     data = newData;
     front = 0;
     tail = size;
    }



    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append(java.lang.String.format("Queue Size=%d  , capacity = %d\n",size,getCapacity()));
        res.append("front [");

        for(int i = front;i !=tail;i =(i+1) % data.length){
            res.append(data[i]);
            if( (i+1) % data.length != tail)
                res.append(", ");
        }
        res.append("] tail");
        return res.toString();
    }


    public static void main(String[] args){
        LoopQueue<Integer> queue = new LoopQueue<>();
        for(int i = 0;i<10;i++){
            queue.enqueue(i);
            System.out.println(queue);

            if(i%3 == 2){
                queue.dequeue();
                System.out.println(queue);
            }
        }

    }
}