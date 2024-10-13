import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

class PlainOld{
    private static int last_id=1;
    private int id;

    public PlainOld(){
        id=PlainOld.last_id++;
        System.out.println("Creating a plainOld object: id = "+id);
    }

    @Override
    public String toString() {
        return "PlaidOld"+id;
    }

}
public class MethodReferences {
    public static void main(String[] args) {
        List<String> list=new ArrayList<>(List.of("Anna","Bob","Chuck","Dave"));
        list.forEach(System.out::println);

        calculator(Integer::sum,10,20);
        calculator(Double::sum,2.5,7.5);

        Supplier<PlainOld> plainOldSupplier=()->new PlainOld();

        Supplier<PlainOld> plainOldSupplier1= PlainOld::new;
        System.out.println(plainOldSupplier1.get().toString());

        System.out.println("Getting array");
        PlainOld[] pojo1=seedArray(PlainOld::new,10);
        System.out.println(Arrays.toString(pojo1));

        calculator((s1,s2)->s1+s2,"Hello ","World");
        System.out.println("-".repeat(30));
        calculator(String::concat,"Hello","World");

        BinaryOperator<String> b1=(s1,s2)->s1.concat(s2);
        BiFunction<String,String,String> b2=(s1,s2)->s1.concat(s2);
        UnaryOperator<String> u1= String::toUpperCase;

        System.out.println(b1.apply("Hello","World"));
        System.out.println(b2.apply("Hello","World"));
        System.out.println(u1.apply("Hello"));

        String result="Hello".transform(u1);
        System.out.println("Result = "+result);
    }

    private static <T> void calculator(BinaryOperator<T> function,T value1,T value2){
        T result=function.apply(value1,value2);
        System.out.println("Result of operation: "+result);
    }

    private static PlainOld[] seedArray(Supplier<PlainOld> reference,int count){
        PlainOld[] array=new PlainOld[count];
        Arrays.setAll(array,i->reference.get());
        return array;
    }
}
