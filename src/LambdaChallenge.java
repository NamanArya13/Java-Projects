import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class LambdaChallenge {
    public static void main(String[] args) {
        String[] names=new String[]{"Naman","Dinesh","Manu","DAULAT","Akbar"};

        Function<String,String> uCase=(String::toUpperCase);
//        for(int i=0;i<names.length;i++){
//            names[i]=uCase.apply(names[i]);
//        }
        System.out.println(Arrays.toString(names));

        char[] charArray=new char[]{'A','B','C','D','E'};
        Random random=new Random();

        Function<String,String> randomInitial=uCase.andThen((s ->s+" "+charArray[random.nextInt(0,charArray.length)]+"."));
//        for(int i=0;i<names.length;i++){
//            names[i]=randomInitial.apply(names[i]);
//        }
        System.out.println(Arrays.toString(names));

        Function<String,String> reverseLastName=uCase.andThen(randomInitial).andThen((s)->{
                String[] temp=s.split(" ");
                StringBuilder stringBuilder=new StringBuilder(temp[0]);
                stringBuilder.reverse();
                return s+stringBuilder.toString();
        });

//        for(int i=0;i<names.length;i++){
//            names[i]=reverseLastName.apply(names[i]);
//        }
        System.out.println(Arrays.toString(names));


        List<Function<String,String>> functions=new ArrayList<>(List.of(reverseLastName));
        functions.forEach((f)->{
            for(int i=0;i<names.length;i++){
                names[i]=f.apply(names[i]);
            }
        });
        System.out.println("-".repeat(30));
        System.out.println(Arrays.toString(names));
    }
}
