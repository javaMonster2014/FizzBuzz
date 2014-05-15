import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    /* returns "F" if i%A==0; returns "B" if i%B==0; 
        or, returns FB if i%A && i%B */
    private static enum divisibility 
    {
        F{public String toString() {return "F";}},
        B{public String toString() {return "B";}},
        FB{public String toString() {return "FB";}}
    }
    
    //stores all instances of Storage that were used in retrieving data from file
    private static ArrayList<Storage> data = new ArrayList<Storage>();

    private class Storage
    {
        private int A,B,N;
        private String[] data; //container for output string
        
        //default constructor: Sotrage class
        public Storage(final int A, final int B, final int N)
        {
            this.A=A; this.B=B; this.N=N; //init class variables
            data=new String[N]; //set size of String[] data
            
           checkDivisibility();
        }//end default constructor
        
        //belongs to Storage class
        //checks the dividibility of i
        private void checkDivisibility()
        {
            boolean a=false,b=false; //divisibility flags: init

            try
            {
            for(int i=1;i<N+1;i++)
                {


                    if (i%A==0)
                    {
                       a=true; //sets flag to true: if modulus==0
                       data[i-1]= divisibility.F.toString();
                    } 
                    if(i%B==0)
                    {
                       b=true; //sets flag to true: if modulus==0
                       data[i-1]= divisibility.B.toString();
                    }
                    else if(a==false && b==false) //check to see if both A && B divisible
                    data[i-1]= String.valueOf(i);   
                    
                    if (a==true && b==true) //check to see if both A && B divisible
                    {
                      data[i-1]= divisibility.FB.toString();  
                    }

                    a=false; b=false; //reset flags
                    
                }//end for
            } //end try
            catch(ArithmeticException e){}
         }//Storage::checkDivisibility end
        
        //return a character at position i, in String[] data
        private String getDataElement(int i)
        { return data[i];}
  
    }//Storage class:end
    

    //default constructor: Main class
    public Main(final int A,final int B,final int N) 
    {
        boolean ERROR=false; //used as error flag: init to false
        
            if((A> 20 || B>20) || (A< 1 || B< 1)) //checks range for A && B
            {
                System.err.println("A and B must have range 1-20");
                ERROR=true; //sets ERROR flag
            }
            if(N <21 || N > 100 ) //checks range N: no. of iterations
            {
                System.err.println("N must have range 21-100");
                ERROR=true;
            }
            //only calls Storage::store
            else if (ERROR==false) {Storage store=new Storage(A,B,N); data.add(store);}
    } //constructor:end
    
    //belongs to Main class
    private static void outputResults()
    {
       for(Storage s:data) //iterates over ArrayList<Storage> data
       {
           String temp=""; //intitializes temp: container for final string for output
                     
           //appends each string in Storage::String[] data: makes one string
           for(int i=0;i<s.N;i++){temp=temp + s.getDataElement(i) +" ";}
           
          //get rid of whitespace at the end of string
          temp=temp.substring(0, temp.lastIndexOf(" "));
          System.out.println(temp);
       }
    }//outputResults:end
    
    public static void main(String[] args) throws Exception 
    {
       File file = new File(args[0]); //opens the file passed via args[0]
       String line; //stores a line of input from file
       int A=0,B=0,iterations=0;
       BufferedReader cin = new BufferedReader(new FileReader(file));
       Main fb;
       
       //regular expression for one or more white spaces: used to split line
       final String REGEX = "[ ]+";

       try 
        { //retrieves data from file until null or line with whitespace  
            while((line=cin.readLine())!=null && !line.equals(REGEX))
            {
              
               try
               {
                    //separate line into individual strings
                    String[] data=line.split(REGEX);
              
                    //convert strings to ints
                    A=Integer.parseInt(data[0]);
                    B=Integer.parseInt(data[1]);
                    iterations=Integer.parseInt(data[2]);
                   
                    //pass A,B and N (iterations) to Main's constructor
                    fb=new Main(A,B,iterations); 
                    
               } //try:end
               catch(ArrayIndexOutOfBoundsException e){}
               catch(NumberFormatException e){}

            } //while:end
            
            cin.close();
            Main.outputResults();
            System.exit(0);
          
       } //try:end
       catch(IOException e){}
       catch(ArrayIndexOutOfBoundsException e){}
       catch(NumberFormatException e){}

    }//main():end
}//Main:end
