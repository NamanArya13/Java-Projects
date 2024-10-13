import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;

public class NewMain {
    public static void main(String[] args) {
        List<String> list=new ArrayList<>(Arrays.asList("alpha","bravo","charlie","delta"));
        for(String s:list){
            System.out.println(s);
        }

        System.out.println("-".repeat(30));
        list.forEach(military-> System.out.println(military));
        list.forEach((var military)-> System.out.println(military));

        System.out.println("-".repeat(50));
        String prefix="nato";
        // we cannot change value of prefix in our lambda expression  ,it should be final or effectively final
        list.forEach((var myString)->{
            char first=myString.charAt(0);
            System.out.println(prefix+" "+myString+" means "+first);
        });
//        prefix="NATO";
        // we cannot change after it too
        // all variables in lambda should be effectively final or final





//        String myString="enclosing method's mystring";
        //cannot have same name as lambda string
        list.forEach((var myString)->{
            char first=myString.charAt(0);
            System.out.println(prefix+" "+myString+" means "+first);
        });

//        ThirdMain<Integer> third=new ThirdMain<>();
//        int res=calculator(third, 5, 4);
//        System.out.println(res+"res");
//
//        Operation<Integer> ops=new ThirdMain<Integer>();
//        int nextRes=calculator(ops,5,5);


        int result=calculator((var a,var  b)->{return a-b;},5,2);
        System.out.println(result);
        var result2=calculator((var a,var b)->a/b,10.0, 2d);
        System.out.println(result2);
        double result3=calculator((Double a,Double b)->a*b, 5d,6d);
        System.out.println(result3);

        String result4=calculator((String a,String b)->a.toUpperCase()+" "+b.toUpperCase(),"Ralph","Kramden");
        System.out.println(result4);

        List<double[]> coords= Arrays.asList(new double[]{47.34,-97.33},new double[]{29.43,34.33},new double[]{456.43,-93.44});
        coords.forEach(s-> System.out.println(Arrays.toString(s)));

        //BI CONSUMER

        BiConsumer<Double,Double> p1=(lat,lng)-> System.out.printf("[lat:%.3f lon:%.3f]%n",lat,lng);

        BiConsumer<Double,Double> p3 = new BiConsumer<Double, Double>() {
            @Override
            public void accept(Double aDouble, Double aDouble2) {
                System.out.println(aDouble+aDouble2);
            }
        };

        var firstpoint=coords.get(0);
        processPoint(firstpoint[0],firstpoint[1],p1);

        BiConsumer<Integer,Integer> p2=(int1,int2)-> System.out.println(int1+int2);;

//        processPoint(1,2,p2);
        coords.forEach(s->{
            System.out.println("-".repeat(30));
            processPoint(s[0],s[1],p1);
        });

        System.out.println("-".repeat(30));
        coords.forEach(s->{
            processPoint(s[0],s[1],(lat,lng)-> System.out.printf("[lat:%.3f lon:%.3f]%n",lat,lng));
        });

        //PREDICATE

        ArrayList<Integer> intList=new ArrayList<>(Arrays.asList(1,2,3,4));

        list.removeIf(s->s.equalsIgnoreCase("bravo"));
        intList.removeIf(s->s==3);
        System.out.println();
        System.out.println(list);
        list.addAll(Arrays.asList("echo","easy","freddie"));
        list.forEach(s-> System.out.println(s));
        list.removeIf(s->s.startsWith("e"));
        System.out.println(list);


    }

//    public static <T> T calculator(Operation<T> function, T value1, T value2) {
//        T result=function.operate(value1,value2);
//        System.out.println("Result of operation: "+result);
//        return result;
//    }

    public static <T> T calculator(BinaryOperator<T> function, T value1, T value2) {
        T result=function.apply(value1,value2);
        System.out.println("Result of operation: "+result);
        return result;
    }

    public static <T> void processPoint(T t1, T t2, BiConsumer<T,T> consumer){
        consumer.accept(t1,t2);
    }


}
