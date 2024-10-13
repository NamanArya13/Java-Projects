import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class ChainingLambdas {
    public static void main(String[] args) {
        String name="Tim";
        Function<String,String> uCase=String::toUpperCase;
        System.out.println(uCase.apply(name));

        Function<String,String> lastName=s->s.concat(" Buchalka");
        Function<String,String> uCaseLastName=uCase.andThen(lastName);
        System.out.println(uCaseLastName.apply(name));

        uCaseLastName=uCase.compose(lastName);
        System.out.println(uCaseLastName.apply(name));

        Function<String,String[]> f0=uCase
                .andThen(s->s.concat(uCase.apply(" Buchalka")))
                .andThen(s->s.split(" "));

        System.out.println(Arrays.toString(f0.apply(name)));


        Function<String,String> f1=uCase
                .andThen(s->s.concat(" Buchalka"))
                .andThen(s->s.split(" "))
                .andThen(s->s[1].toUpperCase()+", "+s[0]);
        System.out.println(f1.apply(name));


        Function<String,Integer> f2=uCase
                .andThen(s->s.concat(" Buchalka"))
                .andThen(s->s.split(" "))
                .andThen(s->String.join(", ",s))
                .andThen(String::length);
        System.out.println(f2.apply(name));


        String[] names={"Ann","Bob","Carol"};
        Consumer<String> s0=s-> System.out.print(s.charAt(0));
        Consumer<String> s1=System.out::println;
        Arrays.asList(names).forEach(s0.andThen(s-> System.out.print(" - ")).andThen(s1));


        record Person(String firstName, String lastName){
//            @Override
//            public int compareTo(Person o) {
//                return this.firstName.compareTo(o.firstName);
//            }
        }

        List<Person> list=new ArrayList<>(Arrays.asList(new Person("Peter","Pan"),new Person("Peter","Pumpkin"),new Person("Minnie","Mouse"),new Person("Mickey","Mouse")));
//        list.sort(Comparator.naturalOrder());
//        System.out.println(list);

//        list.sort((o1,o2)->o1.lastName.compareTo(o2.lastName));
//        list.forEach(System.out::println);

//        list.sort(Comparator.comparing((o1)->o1.lastName));
//        list.forEach(System.out::println);

//        list.sort(Comparator.comparing(Person::lastName));
//        list.forEach(System.out::println);

        list.sort(Comparator.comparing(Person::lastName).thenComparing(Person::firstName).reversed());
        ListIterator<Person> iterator=list.listIterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
            System.out.println();
        }




    }
}
