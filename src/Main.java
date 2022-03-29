import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.security.Guard;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        File fileName = new File ("input.txt");
        ArrayList<ArrayList<Double>> nums = new ArrayList<>();

        try{
            Scanner file = new Scanner(fileName);
            while (file.hasNext()){
                ArrayList<Double> tempArr = new ArrayList<>();
                String temp = (file.next() + " ");
                String[] numbers = temp.split(":");
                for (int i = 0; i < numbers.length; i++){
                    tempArr.add(Double.parseDouble(numbers[i]));
                }
                nums.add(tempArr);
            }
            ArrayList<Double> target = nums.remove(0);
            nums.add(target);
        }
        catch (FileNotFoundException f){
            System.out.println("FIle not found..");
        }
        ArrayList <ArrayList<Double>> matrix = new ArrayList<>();

        for (int i = 0; i < nums.get(0).size(); i ++){
            ArrayList<Double> tempArr = new ArrayList<>();

            for (int j = 0; j < nums.size() ; j++){
               tempArr.add(nums.get(j).get(i));
            }
            matrix.add(tempArr);
        }
        ArrayList<ArrayList<Double>> answer = Gaussian(matrix);
        System.out.println(answer);

        try {
            FileWriter output = new FileWriter("output.txt");
            double n = 0;
            for (ArrayList<Double> doubles : answer) {
                n = Math.round(doubles.get(answer.get(0).size() - 1));
                output.write((int)n + "\n");
            }
            output.close();
        }
        catch (IOException e){
            System.out.println("File already exists");
        }

    }
    public static ArrayList<ArrayList<Double>> Gaussian (ArrayList<ArrayList<Double>> aL) {
        int matrixSize = aL.get(0).size() - 1;

        for (int i = 0; i < matrixSize; i++){
            ArrayList<Double> arrIndex = aL.get(i);
            ArrayList<Double> rowOne = new ArrayList<>();
            double diagonalNum = aL.get(i).get(i);


            for (double getNum : arrIndex) {
                double calculation = getNum / diagonalNum;
                rowOne.add(calculation);
                aL.remove(i);
                aL.add(i, rowOne);

            }
            int numIndex = arrIndex.indexOf(diagonalNum);

            for (int k = 1 + i; k < matrixSize; k++){
                double oppositeDesiredNumber = aL.get(k).get(numIndex) * -1;
                ArrayList <Double> tempArr = new ArrayList<>();

                for (int l = 0; l < arrIndex.size(); l++){
                    double firstRowNumbers = aL.get(i).get(l);
                    double numberThatWillCancel = oppositeDesiredNumber * firstRowNumbers;


                    double product = numberThatWillCancel + aL.get(k).get(l);
                    tempArr.add(product);
                }
                aL.remove(k);
                aL.add(k,tempArr);
            }
        }
        for (int i = 0; i < matrixSize; i++){
            for (int j = i + 1; j < matrixSize; j++){
                double oppositeDesiredNum = aL.get(i).get(j) * -1;
                ArrayList<Double> tempArr = new ArrayList<>();
                for (int k = 0; k < aL.get(0).size(); k++){
                   double num = aL.get(j).get(k);
                   double respectiveRowOnes = aL.get(i).get(k);

                   double numberThatWillCancel = oppositeDesiredNum * num;
                   double answer = numberThatWillCancel + respectiveRowOnes;
                   tempArr.add(answer);
                }
                aL.remove(i);
                aL.add(i, tempArr);
            }
        }
        return(aL);
    }
}
