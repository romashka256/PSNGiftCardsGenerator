package gnnsnowszerro.com.generate;

/**
 * Created by Roma on 16.06.2017.
 */

public class ValidetedData {
    boolean isMailValid(String string){
        if(string.contains("@")){
            return true;
        }else{
            return false;
        }
    }

    boolean isMailBlank(String string){
        if(string.equals("")){
            return true;
        }else if(string.equals("Input your email")){
            return true;
        }else{
            return false;
        }
    }

    boolean isEnough(int count,int expectedcount){
        if(count >= expectedcount){
            return true;
        }else{
            return false;
        }
    }
}
