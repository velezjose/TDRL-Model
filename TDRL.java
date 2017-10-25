/** jvelez6  Neuroeconomics  10/23/2017 
 *
 *  Temporal Dependence Reinforcement Learning (TDRL Model)
 *  Written by: Jose X. Velez
 */
 
 import java.util.Scanner;
 import java.text.DecimalFormat;
 
 public class TDRL {
 
   final static int TOTAL_TIME = 10;
 
   /** Class that takes user input and predicts the value of 
       future events following using Barto and Sutton's formula 
       (in its own method): 
       V(S_t)_new = V(S_t)_old + alpha(r_t + gamma * V(S_t+1)
                   - V(S_t)_old)  
       Prints V1, V2, ..., V10, and PE1, PE2, ..., PE10 (V =
       value; PE = prediction error). 
   */
   public static void main(String[] args) {
      double a = 0;   // alpha value
      double g = 0;   // gamma value
      int tone = 0;
      double reward = 0; // current reward value dealt with
      double[] v = new double[TOTAL_TIME];   // reward values array 
      double[] v_old = new double[TOTAL_TIME]; // old reward values array
      double[] pe = new double[TOTAL_TIME];   // R. prediction errors array
      int t = 0;      // time of reward
      int trial = 0;
      
      // reward array which contains reward at time given
      
      double[] r = new double[TOTAL_TIME];  
      Scanner scnr = new Scanner(System.in);
      DecimalFormat value = new DecimalFormat("0.00");
    
      System.out.println("Welcome to the TDRL Model!");
      System.out.print("Enter alpha value from 0-1: ");
      
      a = scnr.nextDouble();
      scnr.nextLine();
      
      while (a < 0 || a > 1) {
         System.out.println("Invalid alpha value input!");
         System.out.print("***Enter alpha between 0-1: ");
         a = scnr.nextDouble();
         scnr.nextLine();
      }
         
      System.out.print("Enter gamma value from 0-1: ");
      
      g = scnr.nextDouble();
      scnr.nextLine();
      
      while (g < 0 || g > 1) {
         System.out.println("Invalid gamma value input!");
         System.out.print("***Enter gamma between 0-1: ");
         g = scnr.nextDouble();
         scnr.nextLine();
      }
      
      System.out.print("Enter time when tone is played: ");
      tone = scnr.nextInt();
      scnr.nextLine();
      
      System.out.print("Enter reward value (integer amount 0-9): ");
      
      reward = scnr.nextDouble();
      scnr.nextLine();
      
      System.out.print("Enter time when reward is delivered (from 1-" 
         + TOTAL_TIME + "): ");
      
      t = scnr.nextInt() - 1;
      scnr.nextLine();
      
      r[t] = reward;
       
      System.out.print("Enter trial you want to know Values and RPEs: ");
      trial = scnr.nextInt();
      System.out.println();
            
      for (int i = 0; i < v.length; i++) {
         int num = i + 1;
         System.out.print("S" + num + "     ");
      }
      
      System.out.println();
      
      v = ValueCalculator(trial, v, a, g, r, tone);
      
      if (trial > 1) {
         v_old = ValueCalculator(trial - 1, v_old, a, g, r, tone);
      }
      
      for (double num: v) {
         System.out.print(value.format(num) + "   ");
      }
      
      System.out.println();
      
      for (int j = 0; j < v.length; j++) {
         
         if (j >= 9) {
            pe[j] = r[j] - v_old[j];
         }
         else {
            pe[j] = r[j] + g * v_old[j + 1] - v_old[j];
         }
         
         double num = pe[j];
         System.out.print(value.format(num) + "   ");
      }

      System.out.println();
   }
   
   
   public static double[] ValueCalculator(int trial, double[] v, double a,
      double g, double[] r, int tone) {
      
      for (int i = 1; i <= trial; i++) {
         
         for (int j = tone; j < v.length; j++) {
            
            double[] pe = RPE(v, g, j, r);
            v[j] = v[j] + a * pe[j];
         }
      }
               
      return v;
   }

   public static double[] RPE(double[] v, double g, int j, double[] r) {
      double[] pe = new double[TOTAL_TIME];
      
      for (int i = 0; i < pe.length; i++) {
         
         if (i >= 9) {
            
            pe[9] = r[9] - v[9];
         }
         else {
            
            pe[i] = r[i] + g * v[i + 1] - v[i];
         }
      }
      
      
      return pe;
   }
   
   
}