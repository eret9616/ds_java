import com.sun.org.apache.xpath.internal.operations.String;

public class Array<E> {
    private E[] data;
    private int size;


    // 构造函数,传入数组的容量capacity构造Array
    public  Array(int capacity){
        data = (E[])new Object[capacity]; // java语法
        size = 0;
    }

    // 无参构造函数，默认数组的容量capacity=10
    public Array(){
        this(10);
    }

    // 获取数组中的元素个数
    public int getSize(){
        return size;
    }

    // 获取容量
    public int getCapacity(){
        return data.length;
    }

    // 返回判断数组是否为空
    public boolean isEmpty(){
        return size == 0;
    }

    // 向所有元素后添加一个元素
    public  void addLast(E e){
        add(size,e);
    }

    // 在所有元素前添加一个新元素
    public  void addFirst(E e){
        add(0,e);
    }

    // 在index位置插入一个新元素e
    public  void add(int index,E e){


        if(index <0 || index >size)
            throw  new IllegalArgumentException("add Failed,require index >=0 and < size");

        if(size == data.length)
            resize(2 * data.length);

        // 从最后一个开始，每个都向后移位
        for(int i = size-1;i>=index;i--)
            data[i+1] = data[i];

        data[index] = e;
        size++;
    }


    // 获取index索引位置的元素
    E get(int index){
        if(index <0 || index >= size)
            throw  new IllegalArgumentException("Get failed , Index is Illegal");
        return data[index];
    }
    
    public E getLast(){
        return get(size-1);
    }

    public  E getFirst(){
        return get(0);
    }

    // 修改index索引位置的元素为e
    void set(int index,E e ){
        data[index] = e;
    }

    // 查找数组中是否有元素e
    public boolean contains(E e){
      for(int i = 0;i<size;i++){
          if(data[i].equals(e)){ // java语法 == equals 引用比较与值比较
              return true;
          }
      }
      return false;
    }


    // 查找数组中元素e所在的索引，如果不存在元素e，则返回-1
    public int find(E e){
        for(int i = 0;i<size;i++){
            if(data[i].equals(e)){
                return i;
            }
        }
        return -1;
    }

    // 删除某个元素
    public  E remove(int index){

        if(index<0 || index >=size)
            throw  new IllegalArgumentException(("remove failed,index is illegal"));
         E ret = data[index];

         for(int i = index+1;i<size;i++)
             data[i-1] = data[i];

         size--;
         data[size] = null; // loitering objects != memory leak

        if(size == data.length /4  &&  data.length/2 != 0) // 防止复杂度震荡
            resize(data.length/2);


         return ret;
    }

    // 从数组中删除第一个元素 返回删除的元素
    public E removeFirst(){
        return remove(0);
    }

    // 从数组中删除最后一个元素，返回删除的元素
    public E removeLast(){
        return remove(size-1);
    }


    //  从数组中删除元素e
    public void removeElement(E e){
        int index = find(e);
        if(index != -1)
            remove(index);
    }



    @Override
    public java.lang.String toString(){
        StringBuilder res = new StringBuilder();
        res.append(java.lang.String.format("Arr Size=%d  , capacity = %d\n",size,data.length));
        res.append('[');

        for(int i = 0;i<size;i++){
                res.append(data[i]);
                if(i !=size -1)
                    res.append(", ");
        }
        res.append(']');
        return res.toString();
    }


    private void resize(int newCapacity)
    {
        E[] newData = (E[])new Object[newCapacity];

        for(int i = 0;i<size;i++){
            newData[i] = data[i];
        }
        data = newData;

    }
}
