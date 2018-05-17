package idv.jindotw.concurrency;

import java.io.IOException;
import java.util.Scanner;

import idv.jindotw.util.Exercise;
import idv.jindotw.util.ExerciseCollUtil;
import idv.jindotw.util.ExerciseIdException;

public class Start {

    public static void main(String[] args) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException, ExerciseIdException {
        ExerciseCollUtil util = ExerciseCollUtil.getInstance("idv.jindotw.concurrency.exercise");
        int minId = util.getMinId();
        int maxId = util.getMaxId();

        Scanner sc = new Scanner(System.in);
        final String desc = String.format("See README.txt for a complete exercise guide\nInput your choice (%d-%d) or 0 to quit: ", minId, maxId);
        while (true) {
            System.out.print(desc);
            if (sc.hasNextLine()) {
                String line = sc.nextLine();
                try {
                    int choice = Integer.parseInt(line);
                    if (choice == 0) {
                        break;
                    }

                    if (choice < minId || choice > maxId) {
                        continue;
                    }

                    Exercise ex = util.getExercise(choice);
                    if (ex == null) {
                        System.out.println("Exercise no #" + choice + " is not in the Exercise Map");
                        continue;
                    }
                    System.out.println("[" + ex.getTitle() + "]");
                    System.out.println(ex.getDesc());
                    System.out.println();
                    ex.doExercise();
                } catch (NumberFormatException e) {
                }
            }
        }

        System.out.println("See ya");
        sc.close();
    }
}
