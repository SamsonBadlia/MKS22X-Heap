import java.util.Arrays;
import java.util.Random;

public class MyHeap{

  public static void main(String[]args){
  Random rn = new Random();
  System.out.println("Size\t\tMax Value\tquick/builtin ratio ");
  int[]MAX_LIST = {1000000000,500,10,5};
  for(int MAX : MAX_LIST){
    for(int size = 31250; size < 2000001; size*=2){
      long qtime=0;
      long btime=0;
      //average of 5 sorts.
      for(int trial = 0 ; trial <=20; trial++){
        int []data1 = new int[size];
        int []data2 = new int[size];
        for(int i = 0; i < data1.length; i++){
          data1[i] = (int)(Math.random()*MAX);
          data2[i] = data1[i];
        }
        long t1,t2;
        t1 = System.currentTimeMillis();
        MyHeap.heapsort(data2);
        t2 = System.currentTimeMillis();
        qtime += t2 - t1;
        t1 = System.currentTimeMillis();
        Arrays.sort(data1);
        t2 = System.currentTimeMillis();
        btime+= t2 - t1;
        if(!Arrays.equals(data1,data2)){
          System.out.println("FAIL TO SORT!");
          System.exit(0);
          }
        }
        System.out.println(size +"\t\t"+MAX+"\t"+1.0*qtime/btime);
      }
      System.out.println();
    }
  }

  private static void pushDown(int[]  data , int size,int index){

    int current = index;
    //children root idx
    index = 2 * index + 1;

    while (index < size){
      //left child
      int left = data[index];
      int max = left;

      if(index + 1 < size){
        //right child
        int right = data[index + 1];
        //compares children
        if(right > left){
          max = right;
          index++;
        }
      }
      //compares parent and better child
      if (data[current] < max){
        swap(data,current,index);
        current = index;
      }
      else return;
      index = 2 * current + 1;
    }

  }

  private static void pushUp(int[] data , int index){

  }

  public static void heapify(int[] data){
    int size = data.length - 1;
    //calculates number of branches in heap
    int branches;
    if (size == 0) branches = 1;
    else{
      int levels = 0;
      int counter = 1;
      while (size > 0){
        levels++;
        size -= counter;
        counter *= 2;
      }
      branches = levels;
    }
    //goes through every level of the heap and pushes down if possible
    int current = branches - 1;
    while (current > 0){
      //checks maximum and minimum levels of pushing down
      int min = (int)Math.pow(2 , current-1) - 1;
      int max = (int)Math.pow(2 , current) - 2;

      for(int i = max; i >= min; i--){
        pushDown(data , data.length , i);
      }
      current--;
    }
  }


  public static void heapsort(int[] data){
    //converts into a heap
    heapify(data);

    int size = data.length-1;
    //goes through the array
    while (size > 0){
      //swaps elements and pushes down as needed
      swap(data , 0 , size);
      pushDown(data , size , 0);
      size--;
    }

  }


  private static void swap(int[] data, int a, int b){
    int temp = data[a];
    data[a] = data[b];
    data[b] = temp;
  }

}
