package francis.ciruy.lincolnct.com.infintefoldingview.entity;

import java.util.ArrayList;
import java.util.List;

public class BaseContactEntity extends BaseEntity {
    public boolean isSelected;
    public boolean isLast;
    public boolean isFirst;
    public List<BaseContactEntity> subDepartment = new ArrayList<>();

    public String getName(){
        return name;
    }

    public String getId(){
        return id;
    }
}
