package dev.lpa;

import java.util.Arrays;
import java.util.Objects;

//RECORDS DONT GIVE DEFENSIVE COPIES IN GETTERS, OR IF WE CHANGE KIDS ARRAY OF THE RECORD, IT CAN BE REFLECTED IN THE RECORD, SO ITS NOT IMMUTABLE
public record PersonRecord(String name, String dob, PersonRecord[] kids) {
    public PersonRecord(String name, String dob) {
        this(name, dob, new PersonRecord[20]);
    }

    @Override
    public String toString() {
        String kidString="n/a";
        if (kids!=null){
            String[] names=new String[kids.length];
            Arrays.setAll(names, i->names[i]=kids[i]==null?"":kids[i].name);
            kidString=String.join(",",names);
        }
        return name+", dob = "+dob+", kids = "+kidString;
    }

    @Override
    public PersonRecord[] kids() {
        return kids==null?null:Arrays.copyOf(kids,kids.length);
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (this==obj){
//            return true;
//        }
//        if(obj instanceof PersonRecord p){
//            if (Objects.equals(this.name, p.name())){
//                return true;
//            }
//        }
//        return false;
//
//    }
//    private static int i=1;
//    @Override
//    public int hashCode() {
//        return Objects.hash(name);
//    }
}
