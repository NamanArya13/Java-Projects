public class ThirdMain<T extends Integer> implements Operation<T>{


    @Override
    public T operate(T value1, T value2) {
        if (value1 != null){
            try {
                return (T) (Integer)(value2.intValue() +(value1.intValue()));
            }catch (ClassCastException c){
                System.out.println(c.getMessage());
            }

        }
        throw new ClassCastException();
    }
}


