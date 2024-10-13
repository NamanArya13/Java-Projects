import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Challenge {
    private static Random random=new Random();
    public static void main(String[] args) {
        String[] newStrings=new String[]{"Naman","BEN","jOhn","HughiE"};
        Arrays.setAll(newStrings,(i)->newStrings[i].toUpperCase());
        System.out.println(Arrays.toString(newStrings));

        List<String> newList=Arrays.asList(newStrings);
        newList.replaceAll((s -> s+=" "+getRandomChar('A','D')+"."));
        newList.forEach((s)-> System.out.println(s));

        newList.replaceAll(s->s+=" "+getReversedName(s.split(" ")[0]));
        Arrays.asList(newStrings).forEach(s-> System.out.println(s));

        List<String> list2=new ArrayList<>(List.of(newStrings));
        list2.removeIf(s -> s.substring(0,s.indexOf(" ")).equals(s.substring(s.lastIndexOf(" ")+1)));
        System.out.println(list2);


    }

    public static char getRandomChar(char startChar, char endChar){
        return (char)random.nextInt((int) startChar,(int) endChar+1);
    }

    private static String getReversedName(String firstName){
        StringBuilder stringBuilder=new StringBuilder(firstName);
        return stringBuilder.reverse().toString();
    }
}
