import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class FunctionInterface {
    public static void main(String[] args) {
        List<String> list=new ArrayList<>(Arrays.asList("alpha","bravo","charlie","delta"));
        for(String s:list){
            System.out.println(s);
        }
        list.addAll(Arrays.asList("echo","easy","freddie"));

        list.replaceAll(s->s.charAt(0)+"-"+s.toUpperCase());
        System.out.println(list);

        String[] emptyStrings=new String[10];
        System.out.println(Arrays.toString(emptyStrings));
        Arrays.fill(emptyStrings,"");
        Arrays.setAll(emptyStrings,i->emptyStrings[i]+"d");
        System.out.println(Arrays.toString(emptyStrings));

        String result=function(5, 6,(a,b)-> String.valueOf(a)+ b);
        System.out.println(result);

        Arrays.setAll(emptyStrings,(i)->""+(i+1)+"."+switch (i){
            case 0->"one";
            case 1->"two";
            case 2->"three";
            default -> "";
        });
        System.out.println(Arrays.toString(emptyStrings));

        String[] names={"Ann","Bob","Carol","David","Ed","Fred"};
        String[] randomList=randomlySelected(15,names,()-> new Random().nextInt(0, names.length));
        System.out.println(Arrays.toString(randomList));

        Consumer<String> printWords=s -> {
            String[] parts=s.split(" ");
            Arrays.asList(parts).forEach(System.out::println);
        };

        printWords.accept("Hey how you doing");
        System.out.println("-".repeat(30));

        Consumer<String> printWordsConcise=s -> {
            Arrays.asList(s.split(" ")).forEach(sentence-> System.out.println(sentence));
        };
        printWordsConcise.accept("Hey how are you");

        String prefix="Naan";
        UnaryOperator<String> unaryOperator=(s -> {
            StringBuilder returnVal=new StringBuilder();
            for(int i=0;i<s.length();i++){
                if(i%2==1){
                    returnVal.append(s.charAt(i));
                }
            }
            return returnVal.toString();
        });

        System.out.println(unaryOperator.apply("1234567890"));
        System.out.println("-".repeat(50));
        System.out.println(everySecondChar("123456789",(s -> {
            StringBuilder returnVal=new StringBuilder();
            for(int i=0;i<s.length();i++){
                if(i%2==1){
                    returnVal.append(s.charAt(i));
                }
            }
            return returnVal.toString();
        })));

        Supplier<String> stringSupplier=()-> {return "I Love Java";};
        String iLoveJava=stringSupplier.get();
        System.out.println(iLoveJava);




    }

    public static <T,U,R> R function(T t1, U u1, BiFunction<T,U,R> biFunction){
        R result=biFunction.apply(t1,u1);
        return result;

    }

    public static String[] randomlySelected(int count, String[] values, Supplier<Integer> s){
        String[] selectedValues=new String[count];
        for(int i=0;i<count;i++){
            selectedValues[i]=values[s.get()];
        }
        return selectedValues;
    }

    public static <T> T everySecondChar(T t1,UnaryOperator<T> unaryOperator){
        return unaryOperator.apply(t1);
    }


}
