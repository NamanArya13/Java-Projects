package dev.atomic;

import java.util.concurrent.atomic.AtomicReference;

class AtomicObject<T>{

    private final AtomicReference<T> atomicReference;

    public AtomicObject(){
        atomicReference = new AtomicReference<>();
    }

    public AtomicReference<T> getAtomicReference(){
        return this.atomicReference;
    }

    public T getValue(){
        return atomicReference.get();
    }

    public void setValue(T value){
        atomicReference.set(value);
    }
}

public class AtomicObjectMain {
    public static void main(String[] args) throws InterruptedException {

        AtomicObject<String> atomicObject = new AtomicObject<>();

        atomicObject.setValue("Naman Arya");
        System.out.println(atomicObject.getValue());

        Runnable task = ()->{
            for(int i = 0;i<10001;i++) {
                String curVal = atomicObject.getValue();
                String newVal = curVal.equals("Naman Arya")?"Naman":"Naman Arya";
                atomicObject.getAtomicReference().compareAndSet(curVal,newVal);
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(atomicObject.getValue());
    }
}
